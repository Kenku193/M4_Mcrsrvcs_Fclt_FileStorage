package org.example;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import io.minio.errors.MinioException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class FileStorage {
    public static void main(String[] args)
            throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        try {
            // Create a minioClient with the MinIO server playground, its access key and secret key.
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint("http://localhost:9000")
                            .credentials("rUTEjlkJotAxDSHszEfQ", "dtnzXPB0J3ExIMnMLKIN9MbwN98dWm7IoP4uqtT1")
                            .build();

            // Make 'builds' bucket if not exist.
            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket("builds").build());
            if (!found) {
                // Make a new bucket called 'builds'.
                minioClient.makeBucket(MakeBucketArgs.builder().bucket("builds").build());
            } else {
                System.out.println("Bucket 'builds' already exists.");
            }

            // Upload 'src/main/resources/application.properties' as object name 'app.prop' to bucket
            // 'builds'.
            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket("builds")
                            .object("app.prop_1")
                            .filename("/Users/Shared/Mentor/Lections/Module_4/Faculty_Microservices_2/FileStorage/src/main/resources/application.properties")
                            .build());
            System.out.println(
                    "'/home/user/Photos/asiaphotos.zip' is successfully uploaded as "
                            + "object 'asiaphotos-2015.zip' to bucket 'asiatrip'.");
        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
            System.out.println("HTTP trace: " + e.httpTrace());
        }
    }
}