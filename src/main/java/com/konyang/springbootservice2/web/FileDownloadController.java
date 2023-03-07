package com.konyang.springbootservice2.web;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
public class FileDownloadController {
    // This method handles GET requests to /download/{folderName} endpoint and returns the compressed folder as a byte array.
    @GetMapping("/download/{folderName}")
    public ResponseEntity<ByteArrayResource> downloadFolder(@PathVariable String folderName) throws IOException {

        // The path of the source folder is specified.
        String sourceFolderPath = "C:/Users/Jack Ro/Desktop/freelec-springboot2-webservice-master/testdb/" + folderName;

        // A File object is created based on the sourceFolderPath.
        File sourceFolder = new File(sourceFolderPath);

        // If the source folder does not exist or it is not a directory, a 404 error is returned.
        if (!sourceFolder.exists() || !sourceFolder.isDirectory()) {
            return ResponseEntity.notFound().build();
        }

        // A zip file is created based on the source folder.
        String zipFileName = folderName + ".zip";
        File zipFile = new File(sourceFolder.getParentFile(), zipFileName);

        // A FileOutputStream and a ZipOutputStream are created to write the contents of the source folder to the zip file.
        FileOutputStream fos = new FileOutputStream(zipFile);
        ZipOutputStream zos = new ZipOutputStream(fos);

        // The zipDirectory method is called to recursively zip the source folder and its contents.
        zipDirectory(sourceFolder, sourceFolder.getName(), zos);

        // The ZipOutputStream, FileOutputStream and zipFile objects are closed.
        zos.closeEntry();
        zos.close();
        fos.close();

        // The contents of the zip file are read into a byte array.
        byte[] bytes = new byte[(int) zipFile.length()];
        FileInputStream fis = new FileInputStream(zipFile);
        fis.read(bytes);
        fis.close();

        // HttpHeaders are created to specify the content disposition of the response.
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + zipFileName + "\"");

        // A ResponseEntity is returned with the contents of the zip file as a ByteArrayResource.
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(zipFile.length())
                .body(new ByteArrayResource(bytes));
    }

    // This is a private helper method that takes a File object representing a directory, a String
    // representing the parent folder name, and a ZipOutputStream object.

    // It is called recursively by the downloadFolder method to zip the contents of a folder and its subfolders.
    private void zipDirectory(File sourceFolder, String parentFolder, ZipOutputStream zos) throws IOException {

        // Iterate over the files in the source folder.
        for (File file : sourceFolder.listFiles()) {

            // If the file is a directory, recursively call the zipDirectory method to zip its contents.
            if (file.isDirectory()) {
                zipDirectory(file, parentFolder + "/" + file.getName(), zos);
                continue;
            }

            // If the file is not a directory, a FileInputStream is created to read its contents.
            FileInputStream fis = new FileInputStream(file);

            // A new ZipEntry is created with the parent folder name and the file name.
            ZipEntry zipEntry = new ZipEntry(parentFolder + "/" + file.getName());

            // The zipEntry is added to the ZipOutputStream.
            zos.putNextEntry(zipEntry);

            // A byte array is created to hold the contents of the file.
            byte[] bytes = new byte[1024];

            // The contents of the file are read into the byte array in 1024-byte chunks, and written to the ZipOutputStream.
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zos.write(bytes, 0, length);
            }

            // The FileInputStream and ZipEntry objects are closed.
            fis.close();
        }
    }
}