package com.springboot.restblog.utils;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;

public class FileUploadUtils {
    public static void saveFile(String uploadDir,
                         MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        //create if file path is not exists
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        //check image file
//        if (!isImageFile(multipartFile)) {
//            throw new RuntimeException("You can only upload image file");
//        }
        //check file size
        float fileSizeInMegabytes = multipartFile.getSize() / 1000000.0f;
        if (fileSizeInMegabytes > 5.0f) {
            throw new RuntimeException("File must be maximum 5 megabytes");
        }
        //rename file
        String fileExtention = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        String generatedFileName = UUID.randomUUID().toString().replace("-", "");
        generatedFileName = generatedFileName + "." + fileExtention;

        //copy if file is exists
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(generatedFileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException exception) {
            throw new IOException("Could not save file: " + generatedFileName, exception);
        }
    }

    private static boolean isImageFile(MultipartFile multipartFile) {
        String fileExtention = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        return Arrays.asList(new String[] {"png", "jpg", "jpeg", "bmp", "gif", "pdf"})
                .contains(fileExtention.trim().toLowerCase());
    }

    public static void cleanDir(String dir) {
        Path dirPath = Paths.get(dir);
        try {
            Files.list(dirPath).forEach(file -> {
                try {
                    Files.delete(file);
                } catch (IOException exception) {
                    System.out.println("Could not delete file: " + file);
                }
            });
        } catch (IOException exception) {
            System.out.println("Could not list directory: " + dirPath);
        }
    }
}
