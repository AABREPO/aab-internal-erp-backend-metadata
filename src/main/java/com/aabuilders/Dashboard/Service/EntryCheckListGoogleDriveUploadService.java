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

import java.io.ByteArrayOutputStream;
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

    public Res uploadPdfToR2ViaApi(byte[] pdfData, String fileName) {
        Res res = new Res();

        try {
            String boundary = UUID.randomUUID().toString();
            String CRLF = "\r\n";

            ByteArrayOutputStream body = new ByteArrayOutputStream();

            // file part
            body.write(("--" + boundary + CRLF).getBytes());
            body.write(("Content-Disposition: form-data; name=\"files\"; filename=\"" + fileName + "\"" + CRLF).getBytes());
            body.write(("Content-Type: application/pdf" + CRLF + CRLF).getBytes());
            body.write(pdfData);
            body.write(CRLF.getBytes());

            // folder
            body.write(("--" + boundary + CRLF).getBytes());
            body.write(("Content-Disposition: form-data; name=\"folder\"" + CRLF + CRLF).getBytes());
            body.write("FileUpload/Daily_Expense_Reports".getBytes()); // ✅ folder name
            body.write(CRLF.getBytes());

            // filename
            body.write(("--" + boundary + CRLF).getBytes());
            body.write(("Content-Disposition: form-data; name=\"fileName\"" + CRLF + CRLF).getBytes());
            body.write(fileName.getBytes());
            body.write(CRLF.getBytes());

            body.write(("--" + boundary + "--" + CRLF).getBytes());

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://backendaab.in/aabuildersDash/api/files/upload"))
                    .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                    .POST(HttpRequest.BodyPublishers.ofByteArray(body.toByteArray()))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String responseBody = response.body();

                // extract URL from JSON
                String url = extractUrlFromJson(responseBody);

                res.setStatus(200);
                res.setUrl(url);
                res.setMessage("Uploaded to R2 via 8082");

            } else {
                res.setStatus(500);
                res.setMessage("Upload failed: " + response.body());
            }

        } catch (Exception e) {
            res.setStatus(500);
            res.setMessage("Exception: " + e.getMessage());
        }

        return res;
    }

    private String extractUrlFromJson(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(json);

            if (node.has("urls") && node.get("urls").isArray()) {
                return node.get("urls").get(0).asText();
            }

            return null;

        } catch (Exception e) {
            return null;
        }
    }

}
