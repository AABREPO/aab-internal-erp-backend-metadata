package com.aabuilders.Dashboard.Service;

import com.aabuilders.Dashboard.Entity.Res;
import com.aabuilders.Dashboard.Repository.DailyChecklistEntryRepo;
import com.aabuilders.Dashboard.Util.OAuth2Util;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.UUID;

import static com.google.common.primitives.Bytes.concat;

@Service
public class EntryCheckListGoogleDriveUploadService {

    @Autowired
    private DailyChecklistEntryRepo dailyChecklistEntryRepo;

    public int getNextChecklistNumber() {
        Integer max = dailyChecklistEntryRepo.findMaxChecklistNumber();
        return (max != null ? max : 836) + 1;
    }

    public Res uploadPdfToDrive(InputStream inputStream, String filename) {
        Res res = new Res();
        try {
            // Save InputStream to a temporary file
            File tempFile = File.createTempFile("upload-", ".pdf");
            Files.copy(inputStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            String boundary = UUID.randomUUID().toString();
            String CRLF = "\r\n";
            String mimeType = "application/pdf";
            String folderId = "1f5ZrmSnDxfT34y8hZcXu1StVBRi1HciX"; // 💡 optional but passed to Flask

            // Multipart form-data parts
            StringBuilder builder = new StringBuilder();
            builder.append("--").append(boundary).append(CRLF)
                    .append("Content-Disposition: form-data; name=\"folder_id\"").append(CRLF).append(CRLF)
                    .append(folderId).append(CRLF);

            builder.append("--").append(boundary).append(CRLF)
                    .append("Content-Disposition: form-data; name=\"file_name\"").append(CRLF).append(CRLF)
                    .append(filename).append(CRLF);

            builder.append("--").append(boundary).append(CRLF)
                    .append("Content-Disposition: form-data; name=\"file\"; filename=\"").append(filename).append("\"").append(CRLF)
                    .append("Content-Type: ").append(mimeType).append(CRLF).append(CRLF);

            byte[] fileBytes = Files.readAllBytes(tempFile.toPath());
            byte[] preFileData = builder.toString().getBytes(StandardCharsets.UTF_8);
            byte[] postFileData = (CRLF + "--" + boundary + "--" + CRLF).getBytes(StandardCharsets.UTF_8);

            byte[] requestBody = concat(preFileData, fileBytes, postFileData);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:5000/upload"))
                    .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                    .POST(HttpRequest.BodyPublishers.ofByteArray(requestBody))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            tempFile.delete();

            if (response.statusCode() == 200) {
                String body = response.body();
                String url = extractUrlFromJson(body);
                res.setStatus(200);
                res.setUrl(url);
                res.setMessage("✅ Uploaded via Flask to Drive");
            } else {
                res.setStatus(response.statusCode());
                res.setMessage("❌ Flask server error: " + response.body());
            }

        } catch (Exception e) {
            res.setStatus(500);
            res.setMessage("🚨 Exception: " + e.getMessage());
        }
        return res;
    }

    private String extractUrlFromJson(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(json);
            return node.has("file_url") ? node.get("file_url").asText() : null;
        } catch (Exception e) {
            return null;
        }
    }

}
