package com.example.dropboxS3.controllers;

import com.example.dropboxS3.services.ArquivosService;
import com.example.dropboxS3.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;


@Controller
public class ArquivosController {

    @Autowired
    ArquivosService arquivosService;

    @Autowired
    StorageService storageService;


    @GetMapping("home")
    public String home(Model model) {
        model.addAttribute("listaArquivos",storageService.listarArquivos());
        return "index";
    }


    @PostMapping("/arquivo")
    public String uploadAquivo(@RequestParam MultipartFile file, Model model){
        try {
            arquivosService.salvarArquivo(file);
            storageService.uploadArquivo(file);
        }catch (Exception e){
            model.addAttribute("message", e.getMessage());
            return "index";
        }
        return "redirect:/home";
    }

    @GetMapping("/buckets")
    public ResponseEntity listarBucket(){
        return ResponseEntity.ok().body(storageService.listarBuckets());
    }


    @GetMapping("/deletararquivo")
    public String deletarArquivo(@RequestParam String nome, Model model){
        try {
            storageService.deletarArquivo(nome);
        }catch (Exception e){
            model.addAttribute("message", e.getMessage());
            model.addAttribute("listaArquivos",storageService.listarArquivos());
            return "home";
        }
        return "redirect:/home";
    }

    @GetMapping("/editararquivo")
    public String editarArquivo(@RequestParam String nomeAntigo, @RequestParam String nomeNovo, Model model){
        try{
            storageService.editarArquivo(nomeAntigo, nomeNovo);

        }catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            model.addAttribute("listaArquivos",storageService.listarArquivos());
            return "home";
        }
        return "redirect:/home";
    }

    @GetMapping("/baixararquivo")
    public ResponseEntity baixar(@RequestParam String nome) throws IOException {
        storageService.baixarArquivo(nome);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(arquivosService.baixarArquivo(nome));
    }


}
