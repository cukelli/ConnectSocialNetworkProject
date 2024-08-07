package com.example.rs.ftn.ConnectSocialNetworkProject.elasticservice.impl;
import com.example.rs.ftn.ConnectSocialNetworkProject.elasticservice.FileService;
import com.example.rs.ftn.ConnectSocialNetworkProject.exception.NotFoundException;
import com.example.rs.ftn.ConnectSocialNetworkProject.exception.StorageException;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final MinioClient minioClient;

    @Value("${spring.minio.bucket}")
    private String bucketName;

    @Override
    public String store(MultipartFile file, String serverFilename) {
        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file.");
        }
        var originalFilenameTokens =
                Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
        var extension = originalFilenameTokens[originalFilenameTokens.length - 1];

        try {
            var args = PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(serverFilename + "." + extension)
                    .headers(Collections.singletonMap("Content-Disposition",
                            "attachment; filename=\"" + file.getOriginalFilename() + "\""))
                    .stream(file.getInputStream(), file.getInputStream().available(), -1)
                    .build();
//            file.getInputStream().close();
            minioClient.putObject(args);
        } catch (IOException | ErrorResponseException | InsufficientDataException | InternalException |
                 InvalidKeyException | InvalidResponseException | NoSuchAlgorithmException | ServerException |
                 XmlParserException e) {
            e.getMessage();
            throw new StorageException("Error while storing file in Minio.");
        }

        return serverFilename + "." + extension;
    }

    @Override
    public void delete(String serverFilename) {
        try {
            RemoveObjectArgs args = RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(serverFilename)
                    .build();
            minioClient.removeObject(args);
        } catch (Exception e) {
            throw new StorageException("Error while deleting " + serverFilename + " from Minio.");
        }
    }

    @Override
    public GetObjectResponse loadAsResource(String serverFilename) {
        try {
            // Get signed URL
            var argsDownload = GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(bucketName)
                    .object(serverFilename)
                    .expiry(60 * 5) // in seconds
                    .build();
            var downloadUrl = minioClient.getPresignedObjectUrl(argsDownload);
            System.out.println(downloadUrl);

            // Get object response
            var args = GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(serverFilename)
                    .build();
            return minioClient.getObject(args);
        } catch (Exception e) {
            throw new NotFoundException("Document " + serverFilename + " does not exist.");
        }
    }
}