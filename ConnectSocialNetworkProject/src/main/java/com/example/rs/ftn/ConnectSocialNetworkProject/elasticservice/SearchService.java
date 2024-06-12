package com.example.rs.ftn.ConnectSocialNetworkProject.elasticservice;
import com.example.rs.ftn.ConnectSocialNetworkProject.indexmodel.GroupIndex;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Service;

@Service
public interface SearchService {

    Page<GroupIndex> searchGroupsByName(String name, Pageable pageable);

    Page<GroupIndex> searchGroupsByDescription(String description, Pageable pageable);

    void save(GroupIndex groupIndex);


}