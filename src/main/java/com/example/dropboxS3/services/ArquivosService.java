package com.example.dropboxS3.services;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ArquivosService {


    @Value("${dropbox.arquivos.raiz}")
    private String raiz;


    public void salvarArquivo(MultipartFile arquivo){

        Path diretorioPath = Paths.get(this.raiz, "arquivos");
        Path arquivoPath = diretorioPath.resolve(arquivo.getOriginalFilename());
        try {
            Files.createDirectories(diretorioPath);
            arquivo.transferTo(arquivoPath);

        } catch (IOException e) {
            throw new RuntimeException("erro ao salvar arquivo", e);
        }



    }
}
