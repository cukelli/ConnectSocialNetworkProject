package com.example.rs.ftn.ConnectSocialNetworkProject.elasticservice;
import com.example.rs.ftn.ConnectSocialNetworkProject.indexmodel.GroupIndex;
import com.example.rs.ftn.ConnectSocialNetworkProject.indexmodel.PostIndex;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SearchService {

    List<GroupIndex> searchGroupsByName(String name);

    List<GroupIndex> searchGroupsByDescriptionOrPdfDescription(String description, String pdfDescription);

    List<GroupIndex> getAllGroupIndexes();

    List<GroupIndex> searchGroupsByPostRange(Integer minPosts, Integer maxPosts);

    void save(GroupIndex groupIndex);

    List<PostIndex> searchPostByTitle(String title);
    List<PostIndex> searchPostsByContentOrPdfContent(String content, String pdfContent);

    List<PostIndex> getAllPostIndexes();
    List<PostIndex> getAllPostIndexesinGroup(Long groupId);

    List<PostIndex> findByContentAndGroupPostedOrPdfContentAndGroupPosted(String content, String pdfContent,Long groupId);

    List<PostIndex> findByUserAndIsDeletedIsFalse(String user);


    void save(PostIndex postIndex);



}