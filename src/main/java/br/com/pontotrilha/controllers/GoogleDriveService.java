package br.com.pontotrilha.controllers;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class GoogleDriveService {

    private Drive drive;

    public GoogleDriveService() throws IOException, GeneralSecurityException {

        InputStream credentialsStream = getClass().getResourceAsStream("/credenciais.json");
        GoogleCredentials credentials = ServiceAccountCredentials.fromStream(credentialsStream)
                .createScoped(Collections.singleton(DriveScopes.DRIVE));

        this.drive = new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                new HttpCredentialsAdapter(credentials))
                .setApplicationName("Nome do Aplicativo")
                .build();
    }

    public File createFile(String filename, byte[] content) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setName(filename);

        java.io.File tempFile = java.io.File.createTempFile("temp", null);
        FileUtils.writeByteArrayToFile(tempFile, content);

        FileContent mediaContent = new FileContent("application/octet-stream", tempFile);

        File createdFile = drive.files().create(fileMetadata, mediaContent).execute();
        tempFile.delete();

        return createdFile;
    }

    public byte[] downloadFile(String fileId) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        drive.files().get(fileId).executeMediaAndDownloadTo(outputStream);

        System.err.println(drive.files().get(fileId).getAlt());

        return outputStream.toByteArray();
    }

    public void updateFile(String fileId, MultipartFile newFile) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setName(newFile.getOriginalFilename());

        java.io.File tempFile = java.io.File.createTempFile("temp", null);
        newFile.transferTo(tempFile);

        FileContent mediaContent = new FileContent("application/octet-stream", tempFile);

        drive.files().update(fileId, fileMetadata, mediaContent).execute();
        tempFile.delete();
    }
}