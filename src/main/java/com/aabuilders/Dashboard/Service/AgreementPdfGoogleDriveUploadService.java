package com.aabuilders.Dashboard.Service;

import com.aabuilders.Dashboard.Entity.Res;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.UUID;

import static com.google.common.primitives.Bytes.concat;

@Service
public class AgreementPdfGoogleDriveUploadService {

    public Res uploadPdfToDrive(File file, String filename){
        Res res = new Res();

        try{
            String boundary = UUID.randomUUID().toString();
            String CRLF = "\r\n";
            String mimeType = "application/pdf";
            String folderId = "1EBpwcifBGw87uvowEIZuIXvTCpiEdqkQ";

            // Prepare multipart/form-data body
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

            byte[] fileBytes = Files.readAllBytes(file.toPath());
            byte[] preFileData = builder.toString().getBytes(StandardCharsets.UTF_8);
            byte[] postFileData = (CRLF + "--" + boundary + "--" + CRLF).getBytes(StandardCharsets.UTF_8);

            byte[] requestBody = concat(preFileData, fileBytes, postFileData);

            // Send POST request to Flask
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:5000/upload"))
                    .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                    .POST(HttpRequest.BodyPublishers.ofByteArray(requestBody))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            file.delete(); // Clean up temp file

            if (response.statusCode() == 200) {
                String body = response.body();
                String url = extractUrlFromJson(body);
                res.setStatus(200);
                res.setUrl(url);
                res.setMessage("✅ Uploaded via Flask to Drive");
            } else {
                res.setStatus(response.statusCode());
                res.setMessage("❌ Flask upload failed: " + response.body());
            }

        } catch (Exception e) {
            e.printStackTrace();
            res.setStatus(500);
            res.setMessage("🚨 Upload exception: " + e.getMessage());
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
