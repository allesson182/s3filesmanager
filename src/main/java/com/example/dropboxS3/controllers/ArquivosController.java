package com.example.dropboxS3.controllers;

import com.example.dropboxS3.services.ArquivosService;
import com.example.dropboxS3.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/arquivo")
public class ArquivosController {

    @Autowired
    ArquivosService arquivosService;

    @Autowired
    StorageService storageService;

    @PostMapping()
    public void upload(@RequestParam MultipartFile arquivo){
        arquivosService.salvarArquivo(arquivo);
        storageService.uploadArquivo(arquivo);

    }

    @GetMapping("/s3")
    public void listarBucket(){
        storageService.listarBuckets().forEach(bucket ->{
            System.out.println(bucket.getName());
        });
    }


}
