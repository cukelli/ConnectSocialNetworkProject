package com.example.rs.ftn.ConnectSocialNetworkProject.elasticservice;
import com.example.rs.ftn.ConnectSocialNetworkProject.indexmodel.GroupIndex;
import com.example.rs.ftn.ConnectSocialNetworkProject.indexmodel.PostIndex;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SearchService {

    List<GroupIndex> searchGroupsByName(String name);

    List<GroupIndex> searchGroupsByDescription(String description);

    List<GroupIndex> getAllGroupIndexes();

    void save(GroupIndex groupIndex);

    List<PostIndex> searchPostByTitle(String title);

    List<PostIndex> searchPostByContent(String content);

    List<PostIndex> getAllPostIndexes();

    void save(PostIndex postIndex);


}