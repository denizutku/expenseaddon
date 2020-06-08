package com.deniz.controllers;

import com.atlassian.connect.spring.AtlassianHostUser;
import com.deniz.models.Expense;
import com.deniz.models.ExpenseRepository;
import com.deniz.models.Response;
import com.deniz.services.FileService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    private final ExpenseRepository expenseRepository;
    private final FileService fileService;

    public RestController(ExpenseRepository expenseRepository, FileService fileService) {
        this.expenseRepository = expenseRepository;
        this.fileService = fileService;
    }

    @PostMapping(value = "/api/expense/save")
    public Response postCustomer(@RequestParam("description") String description, @RequestParam("amount") String amount,
                                 @RequestParam("file") MultipartFile multipartFile, @RequestParam("filename") String filename
            , @AuthenticationPrincipal AtlassianHostUser hostUser) {
        fileService.uploadFile(multipartFile);
        Expense expense = new Expense();
        expense.setAmount(amount);
        expense.setDescription(description);
        expense.setFilename(filename);
        expenseRepository.save(expense);
        Response response = new Response("Done", expense);
        return response;
    }

    @GetMapping(value = "/api/expense/all")
    public Response getResource(@AuthenticationPrincipal AtlassianHostUser hostUser) {
        Response response = new Response("Done", expenseRepository.findAll());
        return response;
    }

    @GetMapping(value ="/api/expense/delete/{id}")
    public Response deleteCustomer(@PathVariable(value = "id") int id) {
        expenseRepository.deleteById((long) id);
        Response response = new Response("Done",expenseRepository.findAll());
        return response;
    }

    @GetMapping(value ="/api/expense/{id}")
    public ModelAndView updateExpense(@PathVariable(value = "id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("edit");
        modelAndView.addObject("expense",expenseRepository.findById((long) id).get());
        return modelAndView;
    }
}
