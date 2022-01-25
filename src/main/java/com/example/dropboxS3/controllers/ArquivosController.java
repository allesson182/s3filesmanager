package com.example.dropboxS3.controllers;

import com.example.dropboxS3.services.ArquivosService;
import com.example.dropboxS3.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Controller
public class ArquivosController {

    @Autowired
    ArquivosService arquivosService;

    @Autowired
    StorageService storageService;

    @PostMapping("/arquivo")
    public void uploadAquivo(@RequestParam MultipartFile arquivo){
        arquivosService.salvarArquivo(arquivo);
        storageService.uploadArquivo(arquivo);

    }

    @GetMapping("/buckets")
    public ResponseEntity listarBucket(){
        return ResponseEntity.ok().body(storageService.listarBuckets());
    }

    @GetMapping("/listar")
    public ResponseEntity listarArquivos(){
        return ResponseEntity.ok().body(storageService.listarArquivos());
    }

    @DeleteMapping("/{nome}")
    public ResponseEntity deletarArquivo(@PathVariable String nome){
        try {
            storageService.deletarArquivo(nome);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    @PutMapping("/arquivo")
    public ResponseEntity editarArquivo(@RequestHeader String nomeAntigo, @RequestHeader String nomeNovo){
        try{
            storageService.editarArquivo(nomeAntigo, nomeNovo);
        }catch (Exception e){
            ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }


}
