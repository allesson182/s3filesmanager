package com.example.dropboxS3.services;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

@Service
public class StorageService {

    private final AmazonS3 amazonS3 = AmazonS3ClientBuilder
            .standard()
            .withRegion(Regions.US_EAST_1)
            .build();


    public List<Bucket> listarBuckets(){
       return amazonS3.listBuckets();

    }
    public void uploadArquivo(MultipartFile arquivo){
    File file = new File("C:\\\\teste2/arquivos/"+arquivo.getOriginalFilename());

        amazonS3.putObject("projetoifpe","/arquivos/"+arquivo.getOriginalFilename(),file);
    }

}