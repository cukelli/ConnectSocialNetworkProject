package com.example.rs.ftn.ConnectSocialNetworkProject.indexrepository;
import com.example.rs.ftn.ConnectSocialNetworkProject.indexmodel.PostIndex;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Lazy
@Repository
public interface PostIndexRepository extends ElasticsearchRepository<PostIndex,String> {

    List<PostIndex> findByGroupPosted(Long groupId);
    List<PostIndex> findByTitle(String title);
    List<PostIndex> findByUserAndIsDeletedIsFalse(String user);
    List<PostIndex> findByContentOrPdfContent(String content, String pdfContent);
    List<PostIndex> findByContentAndGroupPostedOrPdfContentAndGroupPosted(String content, String pdfContent,Long groupId);
    List<PostIndex> findAll();
    PostIndex save(PostIndex postIndex);

}
