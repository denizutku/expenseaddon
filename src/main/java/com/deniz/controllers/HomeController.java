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



    @PostMapping(value = "/save")
    public String saveProduct(@ModelAttribute("expense") Expense expense) {
        expenseRepository.save(expense);
        return "redirect:/helloworld";
    }

}
