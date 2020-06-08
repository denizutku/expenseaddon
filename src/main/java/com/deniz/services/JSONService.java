package com.deniz.services;

import com.atlassian.connect.spring.AtlassianHostRestClients;
import com.atlassian.connect.spring.AtlassianHostUser;
import com.github.wnameless.json.flattener.JsonFlattener;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class JSONService {

    private final AtlassianHostRestClients atlassianHostRestClients;

    public JSONService(AtlassianHostRestClients atlassianHostRestClients) {
        this.atlassianHostRestClients = atlassianHostRestClients;
    }


    public List<String> extract(String json, String key) {

        List<String> urls = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            Map<String, Object> flattenedJsonMap =
                    JsonFlattener.flattenAsMap(jsonObject.toString());
            flattenedJsonMap.forEach((k, v) -> {
                        if (k.contains(key)) {
                            urls.add(v.toString());
                        }
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return urls;

    }

    public void doSth(AtlassianHostUser hostUser) {
        atlassianHostRestClients
                .authenticatedAs(hostUser)
                .getForObject("https://denizutku.atlassian.net/rest/api/2/search",Void.class);
    }
}
