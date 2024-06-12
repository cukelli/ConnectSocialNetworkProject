package com.example.rs.ftn.ConnectSocialNetworkProject.indexrepository;
import com.example.rs.ftn.ConnectSocialNetworkProject.indexmodel.GroupIndex;
import com.example.rs.ftn.ConnectSocialNetworkProject.indexmodel.PostIndex;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Lazy
@Repository
public interface PostIndexRepository extends ElasticsearchRepository<PostIndex,String> {

    List<PostIndex> findByTitle(String title);

    List<PostIndex> findByContent(String content);

}
