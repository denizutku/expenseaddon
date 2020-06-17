package com.deniz.services;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


@Service
public class FileService {

    private final JSONService jsonService;

    public String uploadDir = "app/tmp";

    public FileService(JSONService jsonService) {
        this.jsonService = jsonService;
    }

    public void uploadFile(MultipartFile file) throws Exception {


        HttpResponse<JsonNode> response = Unirest.get("https://api.imgbb.com/1/upload")
                .header("Accept", "application/json")
                .queryString("key", "08b71016f4131d1bb0566cc9dd4f73ed")
                .queryString("image",file)
                .asJson();
        List<String> imgLink = jsonService.extract(response.getBody().toString(),"data");
        System.out.println(imgLink.get(0));


        //        try {
//            Path copyLocation = Paths
//                    .get(uploadDir + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
//            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new FileStorageException("Could not store file " + file.getOriginalFilename()
//                    + ". Please try again!");
//        }
    }

}
