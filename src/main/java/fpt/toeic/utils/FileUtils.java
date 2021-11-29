package fpt.toeic.utils;


import fpt.toeic.config.Constants;
import fpt.toeic.service.dto.FileDTO;
import liquibase.pro.packaged.E;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.service.spi.ServiceException;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class FileUtils {

    private Path fileStorageImageLocation;

    public String uploadFile(Long type, MultipartFile file, String code) {
        createUploadFileLocation(type, code, file);
        FileDTO fileDTO = storeFile(file);
        int index = fileDTO.getPathLocation().indexOf("assets");

        // Format path file lưu vào database
        String pathFile = "";
        try {
            pathFile = fileDTO.getPathLocation().substring(index);
            pathFile = VNCharacterUtils.removeAccent(pathFile);
        } catch (Exception ex) {
            throw new ServiceException(Translator.toLocale("error_create_location"), ex);
        }
        return pathFile;
    }

    // Tạo nơi chứa các file người dùng upload
    public void createUploadFileLocation(Long type, String code, MultipartFile file) throws ServiceException {
        if (code.equals(null)) {
            throw new ServiceException(Translator.toLocale("error_create_location"));
        }
        String fileType = StringUtils.cleanPath(file.getContentType());
        if (fileType.contains("image")) {
            // he dieu hanh win
//            if (type == 0L) {  // upload thu muc anh dai dien
//                this.fileStorageImageLocation = Paths.get(Constants.URL_FILE_UPLOAD + "\\src\\assets\\images\\avatar\\" + code).toAbsolutePath().normalize();
//            } else if (type == 1L) {
//                this.fileStorageImageLocation = Paths.get(Constants.URL_FILE_UPLOAD + "\\src\\assets\\images\\category\\" + code).toAbsolutePath().normalize();
//            }

            // he dieu hanh mac
//            toeic_web/src/assets

            if (type == 0L) {  // upload thu muc anh dai dien
                this.fileStorageImageLocation = Paths.get(Constants.URL_FILE_UPLOAD + "/src/assets/images/avatar/" + code).toAbsolutePath().normalize();
            } else if (type == 1L) {
                this.fileStorageImageLocation = Paths.get(Constants.URL_FILE_UPLOAD + "/src/assets/images/category/" + code).toAbsolutePath().normalize();
            }
        } else if (fileType.contains("audio")) {
            // he dieu hanh win
//            if (type == 1L) {
//                this.fileStorageImageLocation = Paths.get(Constants.URL_FILE_UPLOAD + "\\src\\assets\\audio\\category\\" + code).toAbsolutePath().normalize();
//            }
            if (type == 1L) {
                this.fileStorageImageLocation = Paths.get(Constants.URL_FILE_UPLOAD + "/src/assets/audio/category/" + code).toAbsolutePath().normalize();
            }

            // he dieu hanh mac
        }

        try {
            Files.createDirectories(this.fileStorageImageLocation);
        } catch (Exception ex) {
            throw new ServiceException(Translator.toLocale("error_create_location"), ex);
        }
    }

    public static String getTypeFile(String url) {
       try {
           String ext = com.google.common.io.Files.getFileExtension(url);
           return ext;
       } catch (Exception e) {
           return "";
       }
    }

    public FileDTO storeFile(MultipartFile file) throws ServiceException {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmssddMMyyyy");
        String formatDateTime = localDateTime.format(formatter);
        String fileType = StringUtils.cleanPath(file.getContentType());

        // Format tên file lưu vào folder
        String fileNameFormat = formatDateTime + RandomStringUtils.randomNumeric(4) + "_" + StringUtils.cleanPath(VNCharacterUtils.removeAccent1(file.getOriginalFilename()));
        if (fileNameFormat.contains("..")) {
            throw new ServiceException(Translator.toLocale("error_name_file") + fileNameFormat + "");
        }
        if (file.getSize() > 5000000) {
            throw new ServiceException(Translator.toLocale("error_size_file") + file.getSize() + "");
        }
        try {
            Long fileSize = file.getSize();
            Path targetLocation = Paths.get("");
            if (fileType.contains("image") || fileType.contains("audio")) {
                targetLocation = this.fileStorageImageLocation.resolve(fileNameFormat);
            }
            FileDTO fileDTO = new FileDTO(fileNameFormat , fileType , fileSize , String.valueOf(targetLocation));
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileDTO;
        } catch (IOException ex) {
            throw new ServiceException(Translator.toLocale("error.commonError") + fileNameFormat + "", ex);
        }
    }

}
