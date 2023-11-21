package br.com.erudio.controllers;

import com.google.api.services.drive.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/files/v1")
public class FileController {

    @Autowired
    private GoogleDriveService googleDriveService; // Classe de acesso à API do Google Drive

    //@CrossOrigin(origins = {"http://localhost:8080", "https://erudio.com.br", "http://localhost:3000"})
    @PostMapping
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            File uploadedFile = googleDriveService.createFile(file.getOriginalFilename(), file.getBytes());
            return uploadedFile.getId();
        } catch (IOException e) {
            return "Erro ao fazer o upload do arquivo";
        }
    }

    /*@GetMapping("/{fileId}")
    public void downloadFile(@PathVariable String fileId, HttpServletResponse response) throws IOException {
        byte[] fileContent = googleDriveService.getFileContent(fileId);
        String filename = fileId;
        String disposition = "attachment; filename=\"" + filename + "\"";
        response.setHeader("Content-Disposition", disposition);
        response.setContentType("application/octet-stream");
        response.getOutputStream().write(fileContent);
        response.getOutputStream().flush();
    }*/

    //@CrossOrigin(origins = {"http://localhost:8080", "https://erudio.com.br", "http://localhost:3000"})
    @GetMapping("/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        try {
            byte[] fileContent = googleDriveService.downloadFile(fileId);

            if (fileContent != null) {
                ByteArrayResource resource = new ByteArrayResource(fileContent);

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
            // Trate exceções conforme necessário
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileId) {
        try {
            // Exclui o arquivo do Google Drive
            googleDriveService.deleteFile(fileId);

            return ResponseEntity.ok("Arquivo excluído com sucesso");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir o arquivo");
        }
    }
}