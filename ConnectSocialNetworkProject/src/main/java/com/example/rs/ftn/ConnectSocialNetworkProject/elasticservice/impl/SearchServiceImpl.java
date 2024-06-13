package com.example.rs.ftn.ConnectSocialNetworkProject.elasticservice.impl;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import java.util.List;
import com.example.rs.ftn.ConnectSocialNetworkProject.elasticservice.SearchService;
import com.example.rs.ftn.ConnectSocialNetworkProject.exception.MalformedQueryException;
import com.example.rs.ftn.ConnectSocialNetworkProject.indexmodel.GroupIndex;
import com.example.rs.ftn.ConnectSocialNetworkProject.indexrepository.GroupIndexRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Service;

@Service
public class SearchServiceImpl implements SearchService {

    private final GroupIndexRepository groupIndexRepository;

    private final ElasticsearchTemplate elasticsearchTemplate;


    public  SearchServiceImpl(GroupIndexRepository groupIndexRepository, ElasticsearchTemplate elasticsearchTemplate){
        this.groupIndexRepository = groupIndexRepository;
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public List<GroupIndex> searchGroupsByName(String name) {
        List<GroupIndex> groupIndexes = groupIndexRepository.findByName(name);
        return groupIndexes;
    }

    @Override
    public List<GroupIndex> searchGroupsByDescription(String description) {
        List<GroupIndex> groupIndexes = groupIndexRepository.findByDescription(description);
        return groupIndexes;
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

    @Override
    public List<GroupIndex> getAllGroupIndexes() {
        return groupIndexRepository.findAll();
    }

    public void save(GroupIndex groupIndex) {
        elasticsearchTemplate.save(groupIndex, IndexCoordinates.of("group_index"));
    }

    private String preprocessQuery(String query) {
        return query.toLowerCase().chars()
                .mapToObj(c -> (char) c)
                .map(c -> Character.isLetter(c) ? c : ' ')
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

}
