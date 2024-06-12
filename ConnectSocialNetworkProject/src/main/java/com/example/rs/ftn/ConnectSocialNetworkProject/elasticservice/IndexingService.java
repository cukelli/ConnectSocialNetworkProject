package com.example.rs.ftn.ConnectSocialNetworkProject.elasticservice;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface IndexingService {

    String indexGroupDocument(MultipartFile documentFile);

//    String indexPostDocument(MultipartFile documentFile);

}