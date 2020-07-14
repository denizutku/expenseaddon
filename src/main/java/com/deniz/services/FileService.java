//package com.deniz.services;
//
//import com.mashape.unirest.http.HttpResponse;
//import com.mashape.unirest.http.JsonNode;
//import com.mashape.unirest.http.Unirest;
//import org.apache.commons.codec.binary.Base64;
//import org.apache.commons.codec.binary.StringUtils;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.file.Files;
//import java.nio.file.StandardCopyOption;
//import java.util.ArrayList;
//import java.util.List;
//
//
//@Service
//public class FileService {
//
//    private final JSONService jsonService;
//
//    public String uploadDir = "app/tmp";
//
//    public FileService(JSONService jsonService) {
//        this.jsonService = jsonService;
//    }
//
//
//    public File convert(MultipartFile file) throws IOException {
//        File convFile = new File(file.getOriginalFilename());
//        convFile.createNewFile();
//        try(InputStream is = file.getInputStream()) {
//            Files.copy(is, convFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
//        }
//        return convFile;
//    }
//
//    public void uploadFile(MultipartFile file) throws Exception {
//
//
//        HttpResponse<JsonNode> response = Unirest.post("https://api.imgur.com/3/image")
//                .header("Authorization", "Client-ID " + "43b4dfd081c3806")
//                .header("Accept", "application/json")
//                .queryString("image", convert(file))
//                .asJson();
//        List<String> imgLink = jsonService.extract(response.getBody().toString(),"data");
//        System.out.println(response.getBody().toString());
//
//
//        //        try {
////            Path copyLocation = Paths
////                    .get(uploadDir + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
////            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
////        } catch (Exception e) {
////            e.printStackTrace();
////            throw new FileStorageException("Could not store file " + file.getOriginalFilename()
////                    + ". Please try again!");
////        }
//    }
//
//}
