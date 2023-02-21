import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/download/{folderName}")
    public ResponseEntity<ByteArrayResource> downloadFolder(@PathVariable String folderName) throws IOException {
        String sourceFolderPath = "C:/Users/Jack Ro/Desktop/freelec-springboot2-webservice-master/testdb/" + folderName;
        File sourceFolder = new File(sourceFolderPath);

        if (!sourceFolder.exists() || !sourceFolder.isDirectory()) {
            return ResponseEntity.notFound().build();
        }

        String zipFileName = folderName + ".zip";
        File zipFile = new File(sourceFolder.getParentFile(), zipFileName);

        FileOutputStream fos = new FileOutputStream(zipFile);
        ZipOutputStream zos = new ZipOutputStream(fos);

        zipDirectory(sourceFolder, sourceFolder.getName(), zos);

        zos.closeEntry();
        zos.close();
        fos.close();

        byte[] bytes = new byte[(int) zipFile.length()];
        FileInputStream fis = new FileInputStream(zipFile);
        fis.read(bytes);
        fis.close();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + zipFileName + "\"");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(zipFile.length())
                .body(new ByteArrayResource(bytes));
    }

    private void zipDirectory(File sourceFolder, String parentFolder, ZipOutputStream zos) throws IOException {
        for (File file : sourceFolder.listFiles()) {
            if (file.isDirectory()) {
                zipDirectory(file, parentFolder + "/" + file.getName(), zos);
                continue;
            }

            FileInputStream fis = new FileInputStream(file);
            ZipEntry zipEntry = new ZipEntry(parentFolder + "/" + file.getName());
            zos.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zos.write(bytes, 0, length);
            }

            fis.close();
        }
    }
}