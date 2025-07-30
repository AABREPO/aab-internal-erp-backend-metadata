package com.aabuilders.Dashboard.Util;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.DriveScopes;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;

public class OAuth2Util {

    private static final String CLIENT_SECRET_FILE = "cred_prod.json";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "/tmp/tokens"; // directory for storing tokens

    public static Credential authorize() throws Exception {
        InputStream in = OAuth2Util.class.getClassLoader().getResourceAsStream(CLIENT_SECRET_FILE);
        if (in == null) {
            throw new IllegalStateException("Could not find '" + CLIENT_SECRET_FILE + "' in the classpath.");
        }

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                clientSecrets,
                Collections.singleton(DriveScopes.DRIVE_FILE)
        )
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .setApprovalPrompt("force")  // Ensures refresh token is always requested
                .build();

        Credential credential = flow.loadCredential("user");
        //System.out.println("Access Token: " + credential.getAccessToken());
        //System.out.println("Expires in: " + credential.getExpiresInSeconds());
        //System.out.println("Refresh Token: " + credential.getRefreshToken());
        if (credential != null && credential.getRefreshToken() != null) {
            System.out.println("✅ Reusing existing token.");
            return credential;
        }

        return flow.loadCredential("user");
    }
}
