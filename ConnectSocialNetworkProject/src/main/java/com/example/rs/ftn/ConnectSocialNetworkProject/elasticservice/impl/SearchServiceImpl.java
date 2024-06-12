package com.example.rs.ftn.ConnectSocialNetworkProject.elasticservice.impl;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import java.util.List;
import com.example.rs.ftn.ConnectSocialNetworkProject.elasticservice.SearchService;
import com.example.rs.ftn.ConnectSocialNetworkProject.exception.MalformedQueryException;
import com.example.rs.ftn.ConnectSocialNetworkProject.indexmodel.GroupIndex;
import com.example.rs.ftn.ConnectSocialNetworkProject.indexrepository.GroupIndexRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final GroupIndexRepository groupIndexRepository;

    private final ElasticsearchOperations elasticsearchOperations;



    @Override
    public Page<GroupIndex> searchGroupsByName(String name, Pageable pageable) {
        List<GroupIndex> groupIndexes = groupIndexRepository.findByName(name);
        return getPageableResults(groupIndexes, pageable);
    }

    @Override
    public Page<GroupIndex> searchGroupsByDescription(String description, Pageable pageable) {
        List<GroupIndex> groupIndexes = groupIndexRepository.findByDescription(description);
        return getPageableResults(groupIndexes, pageable);
    }

    private Page<GroupIndex> getPageableResults(List<GroupIndex> groupIndexes, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), groupIndexes.size());

        List<GroupIndex> sublist = groupIndexes.subList(start, end);
        return new PageImpl<>(sublist, pageable, groupIndexes.size());
    }

    public void save(GroupIndex groupIndex) {
        elasticsearchOperations.save(groupIndex, IndexCoordinates.of("group_index"));
    }

}
