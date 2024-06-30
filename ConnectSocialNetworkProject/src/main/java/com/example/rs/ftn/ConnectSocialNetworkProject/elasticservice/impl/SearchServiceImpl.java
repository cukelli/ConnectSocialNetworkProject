package com.example.rs.ftn.ConnectSocialNetworkProject.elasticservice.impl;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.rs.ftn.ConnectSocialNetworkProject.elasticservice.SearchService;
import com.example.rs.ftn.ConnectSocialNetworkProject.indexmodel.GroupIndex;
import com.example.rs.ftn.ConnectSocialNetworkProject.indexmodel.PostIndex;
import com.example.rs.ftn.ConnectSocialNetworkProject.indexrepository.GroupIndexRepository;
import com.example.rs.ftn.ConnectSocialNetworkProject.indexrepository.PostIndexRepository;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregations;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;


@Service
public class SearchServiceImpl implements SearchService {

    private final GroupIndexRepository groupIndexRepository;

    private final ElasticsearchTemplate elasticsearchTemplate;
    private final PostIndexRepository postIndexRepository;
    private final ElasticsearchOperations operations;


    public  SearchServiceImpl(GroupIndexRepository groupIndexRepository, ElasticsearchTemplate elasticsearchTemplate, PostIndexRepository postIndexRepository, ElasticsearchOperations operations){
        this.groupIndexRepository = groupIndexRepository;
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.postIndexRepository = postIndexRepository;
        this.operations = operations;
    }

    @Override
    public List<GroupIndex> searchGroupsByName(String name) {
        List<GroupIndex> groupIndexes = groupIndexRepository.findByName(name);
        return groupIndexes;
    }

    @Override
    public List<GroupIndex> searchGroupsByPostRange(Integer minPosts, Integer maxPosts) {
        Query query = new NativeSearchQueryBuilder()                       // we build a Elasticsearch native query
                .addAggregation(terms("groupPosted").field("groupPosted").size(10000)) // add the aggregation
                .build();

        SearchHits<PostIndex> searchHits = operations.search(query, PostIndex.class);
        System.out.println(searchHits.getTotalHits());
        return null;
    }

    @Override
    public List<PostIndex> findByContentAndGroupPostedOrPdfContentAndGroupPosted(String content, String pdfContent,Long groupId) {
        return postIndexRepository.findByContentAndGroupPostedOrPdfContentAndGroupPosted(content,pdfContent,groupId);

    }



    @Override
    public List<GroupIndex> searchGroupsByDescriptionOrPdfDescription(String description, String pdfDescription) {
        List<GroupIndex> groupIndexes = groupIndexRepository.findByDescriptionOrPdfDescription(description,pdfDescription);
        return groupIndexes;
    }

    @Override
    public List<PostIndex> searchPostsByContentOrPdfContent(String content, String pdfContent) {
        List<PostIndex> postIndexes = postIndexRepository.findByContentOrPdfContent(content,pdfContent);
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

//    @Override
//    public List<PostIndex> getAllGroupIndexes() {
//        return groupIndexRepository.findAll();
//    }


    @Override
    public List<PostIndex> getAllPostIndexes() {
        return postIndexRepository.findAll();
    }

    @Override
    public List<PostIndex> getAllPostIndexesinGroup(Long groupId) {
        return postIndexRepository.findByGroupPosted(groupId);
    }

    @Override
    public List<PostIndex> findByUserAndIsDeletedIsFalse(String user) {
        return postIndexRepository.findByUserAndIsDeletedIsFalse(user);
    }

    public void save(GroupIndex groupIndex) {
        elasticsearchTemplate.save(groupIndex, IndexCoordinates.of("group_index"));
    }

    public void save(PostIndex postIndex) {
        elasticsearchTemplate.save(postIndex, IndexCoordinates.of("post_index"));
    }
}
