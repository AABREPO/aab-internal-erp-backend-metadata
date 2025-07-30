package com.aabuilders.Dashboard.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.UUID;

@RestController
public class FileForwardController {

    @PostMapping("/forward")
    public ResponseEntity<String> forwardFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("file_name") String fileName // ✅ new param from Postman
    ) throws IOException, InterruptedException {

        File tempFile = File.createTempFile("upload-", "-" + file.getOriginalFilename());
        file.transferTo(tempFile);

        String boundary = UUID.randomUUID().toString();
        String CRLF = "\r\n";
        String mimeType = file.getContentType();
        String folderId = "1mJVXHZ-PTqYDSgakdVe4bbqf3H_z-qmy";

        // Part: folder_id
        String partFolderId = "--" + boundary + CRLF +
                "Content-Disposition: form-data; name=\"folder_id\"" + CRLF + CRLF +
                folderId + CRLF;

        // Part: file_name
        String partFileName = "--" + boundary + CRLF +
                "Content-Disposition: form-data; name=\"file_name\"" + CRLF + CRLF +
                fileName + CRLF;

        // Part: file
        String partFileHeader = "--" + boundary + CRLF +
                "Content-Disposition: form-data; name=\"file\"; filename=\"" + fileName + "\"" + CRLF +
                "Content-Type: " + mimeType + CRLF + CRLF;

        String endBoundary = CRLF + "--" + boundary + "--" + CRLF;

        byte[] fileBytes = Files.readAllBytes(tempFile.toPath());
        byte[] multipartBody = concat(
                partFolderId.getBytes(StandardCharsets.UTF_8),
                partFileName.getBytes(StandardCharsets.UTF_8),
                partFileHeader.getBytes(StandardCharsets.UTF_8),
                fileBytes,
                endBoundary.getBytes(StandardCharsets.UTF_8)
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:5000/upload"))
                .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                .POST(HttpRequest.BodyPublishers.ofByteArray(multipartBody))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        tempFile.delete();
        return ResponseEntity.status(response.statusCode()).body(response.body());
    }

    private byte[] concat(byte[]... arrays) {
        int totalLength = 0;
        for (byte[] arr : arrays) totalLength += arr.length;
        byte[] result = new byte[totalLength];
        int currentPos = 0;
        for (byte[] arr : arrays) {
            System.arraycopy(arr, 0, result, currentPos, arr.length);
            currentPos += arr.length;
        }
        return result;
    }
}
