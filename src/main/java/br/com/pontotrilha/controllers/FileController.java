package br.com.pontotrilha.controllers;

import com.google.api.services.drive.model.File;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Tag(name = "Files", description = "Endpoint to manage files")
@RestController
@RequestMapping("/api/files/v1")
public class FileController {

    @Autowired
    private GoogleDriveService googleDriveService;

  

    @CrossOrigin(origins = { "http://localhost:8080", "https://pontotrilha.com.br" })
    @Operation(summary = "Upload a file to Google Drive")
    @PostMapping
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            File uploadedFile = googleDriveService.createFile(file.getOriginalFilename(), file.getBytes());
            System.out.println(uploadedFile.getWebViewLink());
            return uploadedFile.getId();
        } catch (IOException e) {
            return "Error uploading file";
        }
    }

    @CrossOrigin(origins = { "http://localhost:8080", "https://pontotrilha.com.br" })
    @Operation(summary = "Download a file from Google Drive")
    @GetMapping("/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        try {
            byte[] fileContent = googleDriveService.downloadFile(fileId);

            if (fileContent != null) {
                ByteArrayResource resource = new ByteArrayResource(fileContent);
                System.out.println(resource.getInputStream());
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileId)
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .contentLength(fileContent.length)
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @CrossOrigin(origins = { "http://localhost:8080", "https://pontotrilha.com.br" })
    @Operation(summary = "Get file content from Google Drive")
    @GetMapping("/content/{fileId}")
    public ResponseEntity<String> getFileContent(@PathVariable String fileId) {
        try {
            byte[] fileContent = googleDriveService.downloadFile(fileId);

            if (fileContent != null) {
                String content = new String(fileContent, StandardCharsets.UTF_8);
                return ResponseEntity.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .body(content);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
            }

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    @CrossOrigin(origins = { "http://localhost:8080", "https://pontotrilha.com.br" })
    @Operation(summary = "Update a file in Google Drive")
    @PutMapping("/{fileId}")
    public ResponseEntity<String> updateFile(@PathVariable String fileId, @RequestParam("file") MultipartFile file) {
        try {
            googleDriveService.updateFile(fileId, file);
            return ResponseEntity.ok("File Updated Successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating the file");
        }
    }
}