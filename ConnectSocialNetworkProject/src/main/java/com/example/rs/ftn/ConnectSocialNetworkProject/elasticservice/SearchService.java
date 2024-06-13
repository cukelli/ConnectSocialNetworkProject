package com.example.rs.ftn.ConnectSocialNetworkProject.elasticservice;
import com.example.rs.ftn.ConnectSocialNetworkProject.indexmodel.GroupIndex;
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

    Page<GroupIndex> searchGroupsByDescription(String description, Pageable pageable);

    void save(GroupIndex groupIndex);


}