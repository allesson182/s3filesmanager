package com.example.dropboxS3.controllers;

import com.example.dropboxS3.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/")
public class GeralController {

    @Autowired
    StorageService storageService;


    @GetMapping("home")
    public String home(Model model) {
        model.addAttribute("listaArquivos",storageService.listarArquivos());
        return "index";
    }
}
