package com.example.rs.ftn.ConnectSocialNetworkProject.indexrepository;
import com.example.rs.ftn.ConnectSocialNetworkProject.indexmodel.GroupIndex;
import com.example.rs.ftn.ConnectSocialNetworkProject.indexmodel.PostIndex;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//@Lazy
@Repository
public interface PostIndexRepository extends ElasticsearchRepository<PostIndex,String> {

    List<PostIndex> findByGroupPosted(Long groupId);
    List<PostIndex> findByTitle(String title);
    List<PostIndex> findByTitleAndGroupPosted(String title, Long groupId);

    List<PostIndex> findByUserAndIsDeletedIsFalse(String user);
    List<PostIndex> findByContentOrPdfContent(String content, String pdfContent);
    List<PostIndex> findByContentAndGroupPosted(String content,Long groupPosted);
    List<PostIndex> findByPdfContentAndGroupPosted(String pdfContent,Long groupPosted);

    List<PostIndex> findAllByUserInAndContent(ArrayList<String> usernameList, String content);
    List<PostIndex> findAllByUserInAndPdfContent(ArrayList<String> usernameList, String pdfContent);
    List<PostIndex> findAllByUserInAndTitle(ArrayList<String> usernameList, String title);

    List<PostIndex> findAll();
    List<PostIndex> findAllByUserIn(ArrayList<String> usernameList);
    List<PostIndex> findAllByUserInAndPostLikesBetween(ArrayList<String> usernameList, Long minLikes, Long maxLikes);
    List<PostIndex> findByGroupPostedAndPostLikesBetween(Long groupId,  Long minLikes, Long maxLikes);
    PostIndex getByDatabaseId(Long databaseId);
    PostIndex save(PostIndex postIndex);

}
