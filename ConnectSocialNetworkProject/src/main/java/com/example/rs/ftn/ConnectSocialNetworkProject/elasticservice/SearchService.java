package com.example.rs.ftn.ConnectSocialNetworkProject.elasticservice;
import com.example.rs.ftn.ConnectSocialNetworkProject.indexmodel.GroupIndex;
import com.example.rs.ftn.ConnectSocialNetworkProject.indexmodel.PostIndex;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public interface SearchService {

    List<GroupIndex> searchGroupsByName(String name);

    List<GroupIndex> searchGroupsByDescriptionOrPdfDescription(String description, String pdfDescription);

    List<GroupIndex> getAllGroupIndexes();

    GroupIndex findGroupByDatabaseId(Long databaseId);

    PostIndex findPostByDatabaseId(Long databaseId);

    List<PostIndex> findByPostLikesBetween(ArrayList<String> usernameList, Long minLikes, Long maxLikes);
    List<PostIndex> findByGroupPostedAndPostLikesBetween(Long groupId,  Long minLikes, Long maxLikes);

    void save(GroupIndex groupIndex);

    List<PostIndex> searchPostByTitle(String title);
    List<PostIndex> searchPostsByContentOrPdfContent(String content, String pdfContent);
    List<PostIndex> findAllByUserInAndTitle(ArrayList<String> usernameList, String title);

    List<PostIndex> getAllPostIndexes();

    List<PostIndex>  findAllByUserIn(ArrayList<String> usernameList);
    List<PostIndex> getAllPostIndexesinGroup(Long groupId);

    List<PostIndex> findByContentAndGroupPosted(String content, Long groupId);
    List<PostIndex> findByPdfContentAndGroupPosted(String pdfContent, Long groupId);
    List<PostIndex> findByTitleAndGroupPosted(String title, Long groupId);

    List<PostIndex> findByUserAndIsDeletedIsFalse(String user);

    List<GroupIndex> findByPostCountBetween(Long minPosts, Long maxPosts);
    List<PostIndex> findAllByUserInAndContent(ArrayList<String> usernameList, String content);
    List<PostIndex> findAllByUserInAndPdfContent(ArrayList<String> usernameList, String pdfContent);


    void save(PostIndex postIndex);





}