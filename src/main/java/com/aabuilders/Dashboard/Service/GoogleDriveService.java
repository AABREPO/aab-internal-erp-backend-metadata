package com.aabuilders.Dashboard.Service;

import com.aabuilders.Dashboard.Entity.Res;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.UUID;

@Service
public class GoogleDriveService {

    // ✅ You control folder ID here
    private static final String FOLDER_ID = "1j8zZ8bYiLj5iXDyJ3SXcE_l5EbiA0fLH";

    public String uploadFiles(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        File tempFile = File.createTempFile("upload-", ".pdf");
        file.transferTo(tempFile);

        String boundary = UUID.randomUUID().toString();
        String CRLF = "\r\n";
        String mimeType = "application/pdf";

        // Prepare form-data payload
        StringBuilder builder = new StringBuilder();
        builder.append("--").append(boundary).append(CRLF)
                .append("Content-Disposition: form-data; name=\"folder_id\"").append(CRLF).append(CRLF)
                .append(FOLDER_ID).append(CRLF);

        builder.append("--").append(boundary).append(CRLF)
                .append("Content-Disposition: form-data; name=\"file_name\"").append(CRLF).append(CRLF)
                .append(filename).append(CRLF);

        builder.append("--").append(boundary).append(CRLF)
                .append("Content-Disposition: form-data; name=\"file\"; filename=\"").append(filename).append("\"").append(CRLF)
                .append("Content-Type: ").append(mimeType).append(CRLF).append(CRLF);

        byte[] preFileData = builder.toString().getBytes(StandardCharsets.UTF_8);
        byte[] fileBytes = Files.readAllBytes(tempFile.toPath());
        byte[] postFileData = (CRLF + "--" + boundary + "--" + CRLF).getBytes(StandardCharsets.UTF_8);

        byte[] requestBody = concat(preFileData, fileBytes, postFileData);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:5000/upload"))
                .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                .POST(HttpRequest.BodyPublishers.ofByteArray(requestBody))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new IOException("Failed to contact Flask server: " + e.getMessage(), e);
        }

        tempFile.delete();

        if (response.statusCode() == 200) {
            String body = response.body();
            String fileId = extractDriveFileId(body); // or file_url
            return fileId;
        } else {
            throw new IOException("Flask server error: " + response.statusCode() + " - " + response.body());
        }
    }

    private byte[] concat(byte[] a, byte[] b, byte[] c) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(a);
        outputStream.write(b);
        outputStream.write(c);
        return outputStream.toByteArray();
    }

    private String extractDriveFileId(String json) {
        try {
            int start = json.indexOf("/d/") + 3;
            int end = json.indexOf("/view");
            if (start > 2 && end > start) {
                return json.substring(start, end);
            }
        } catch (Exception ignored) {}
        return null;
    }
}
