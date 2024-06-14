package com.example.rs.ftn.ConnectSocialNetworkProject.elasticservice.impl;
import java.util.List;
import com.example.rs.ftn.ConnectSocialNetworkProject.elasticservice.SearchService;
import com.example.rs.ftn.ConnectSocialNetworkProject.indexmodel.GroupIndex;
import com.example.rs.ftn.ConnectSocialNetworkProject.indexmodel.PostIndex;
import com.example.rs.ftn.ConnectSocialNetworkProject.indexrepository.GroupIndexRepository;
import com.example.rs.ftn.ConnectSocialNetworkProject.indexrepository.PostIndexRepository;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Service;

@Service
public class SearchServiceImpl implements SearchService {

    private final GroupIndexRepository groupIndexRepository;

    private final ElasticsearchTemplate elasticsearchTemplate;
    private final PostIndexRepository postIndexRepository;


    public  SearchServiceImpl(GroupIndexRepository groupIndexRepository, ElasticsearchTemplate elasticsearchTemplate, PostIndexRepository postIndexRepository){
        this.groupIndexRepository = groupIndexRepository;
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.postIndexRepository = postIndexRepository;
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
    public List<PostIndex> searchPostByContent(String content) {
        List<PostIndex> postIndexes = postIndexRepository.findByContent(content);
        return postIndexes;
    }

    @Override
    public List<PostIndex> searchPostByTitle(String title) {
        List<PostIndex> postIndexes = postIndexRepository.findByTitle(title);
        return postIndexes;
    }

    @Override
    public List<GroupIndex> getAllGroupIndexes() {
        return groupIndexRepository.findAll();
    }

    @Override
    public List<PostIndex> getAllPostIndexes() {
        return postIndexRepository.findAll();
    }

    public void save(GroupIndex groupIndex) {
        elasticsearchTemplate.save(groupIndex, IndexCoordinates.of("group_index"));
    }

    public void save(PostIndex postIndex) {
        elasticsearchTemplate.save(postIndex, IndexCoordinates.of("group_index"));
    }

}
