package com.deniz.controllers;

import com.atlassian.connect.spring.AtlassianHostRestClients;
import com.atlassian.connect.spring.AtlassianHostUser;
import com.deniz.models.Expense;
import com.deniz.models.ExpenseRepository;
import com.deniz.services.JSONService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    private final JSONService jsonService;
    private final AtlassianHostRestClients atlassianHostRestClients;
    private final ExpenseRepository expenseRepository;

    public HomeController(JSONService jsonService, AtlassianHostRestClients atlassianHostRestClients, ExpenseRepository expenseRepository) {
        this.jsonService = jsonService;
        this.atlassianHostRestClients = atlassianHostRestClients;
        this.expenseRepository = expenseRepository;
    }

    @RequestMapping(value = "/helloworld", method = RequestMethod.GET)
    public ModelAndView helloWorld(@AuthenticationPrincipal AtlassianHostUser hostUser) throws UnirestException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("hello-world");
//        modelAndView.addObject("test",hostUser.getUserAccountId().get());
        return modelAndView;
    }

    @GetMapping("/search")
    public ModelAndView search(@RequestParam(value = "search") String searchText, @RequestParam(value = "jwt") String jwt, @AuthenticationPrincipal AtlassianHostUser hostUser) throws UnirestException {
        ModelAndView modelAndView = new ModelAndView();
        atlassianHostRestClients
                .authenticatedAs(hostUser)
                .getForObject("https://denizutku.atlassian.net/rest/api/2/search", Void.class);

        List<String> datas;
        HttpResponse<JsonNode> response = Unirest.get("https://denizutku.atlassian.net/rest/api/2/search")
                .basicAuth("beydogandeniz@gmail.com","6EGg3hu0WWQgjbG7MG2u7C51")
                .header("Accept", "application/json")
                .queryString("jql", "project = TEST")
//                .queryString("fields","summary")
                .queryString("expand","names")
                .asJson();
        modelAndView.addObject("body",response.getBody());
        datas = jsonService.extract(response.getBody().toString(),"summary");
        List<String> contains = new ArrayList<>();

        for(int i =0; i < datas.size();i++){
            if(datas.get(i).contains(searchText)){
            contains.add(datas.get(i));
            }
        }
        modelAndView.addObject("contains",contains);
        modelAndView.setViewName("hello-world");
        return modelAndView;
    }

    @PostMapping(value = "/save")
    public String saveProduct(@ModelAttribute("expense") Expense expense) {
        expenseRepository.save(expense);
        return "redirect:/helloworld";
    }

}
