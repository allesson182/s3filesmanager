package com.example.dropboxS3.services;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.List;

@Service
public class StorageService {

    @Value("${dropbox.arquivos.raiz}")
    private String raiz;
    private final AmazonS3 amazonS3 = AmazonS3ClientBuilder
            .standard()
            .withRegion(Regions.US_EAST_1)
            .build();


    public List<Bucket> listarBuckets() {
        return amazonS3.listBuckets();

    }

    public void uploadArquivo(MultipartFile arquivo) {
        File file = new File(this.raiz+"\\arquivos\\" + arquivo.getOriginalFilename());
        amazonS3.putObject("projetoifpe", arquivo.getOriginalFilename(), file);
    }

    public List<S3ObjectSummary> listarArquivos() {
        ListObjectsV2Result result = amazonS3.listObjectsV2("projetoifpe");
        return result.getObjectSummaries();
//        for (S3ObjectSummary os : objects) {
//            System.out.println("* " + os.);
    }


    public void deletarArquivo(String nome) {
        amazonS3.deleteObject("projetoifpe", nome);
    }

    public String editarArquivo(String nomeAntigo, String nomeNovo) {
        amazonS3.copyObject("projetoifpe", "/" + nomeAntigo, "projetoifpe", "/" + nomeNovo);
        return "redirect:/";
    }

    public void baixarArquivo(String nome)  {
        File localFile = new File(this.raiz+"/download/"+nome);
        ObjectMetadata object = amazonS3.getObject(new GetObjectRequest("projetoifpe", nome), localFile);
    }

}
