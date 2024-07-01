package com.example.rs.ftn.ConnectSocialNetworkProject.elasticservice.impl;
import java.util.ArrayList;
import java.util.List;
import com.example.rs.ftn.ConnectSocialNetworkProject.elasticservice.SearchService;
import com.example.rs.ftn.ConnectSocialNetworkProject.indexmodel.GroupIndex;
import com.example.rs.ftn.ConnectSocialNetworkProject.indexmodel.PostIndex;
import com.example.rs.ftn.ConnectSocialNetworkProject.indexrepository.GroupIndexRepository;
import com.example.rs.ftn.ConnectSocialNetworkProject.indexrepository.PostIndexRepository;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
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
    public List<PostIndex> findAllByUserInAndTitle(ArrayList<String> usernameList, String title) {
        return postIndexRepository.findAllByUserInAndTitle(usernameList, title);
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
    public GroupIndex findGroupByDatabaseId(Long databaseId) {
       return groupIndexRepository.getByDatabaseId(databaseId);
    }

    @Override
    public PostIndex findPostByDatabaseId(Long databaseId) {
        return postIndexRepository.getByDatabaseId(databaseId);
    }

    @Override
    public List<PostIndex> findByPostLikesBetween(ArrayList<String> usernameList, Long minLikes, Long maxLikes) {
        return postIndexRepository.findAllByUserInAndPostLikesBetween(usernameList, minLikes,maxLikes);
    }

    @Override
    public List<PostIndex> findByGroupPostedAndPostLikesBetween(Long groupId, Long minLikes, Long maxLikes) {
        return postIndexRepository.findByGroupPostedAndPostLikesBetween(groupId, minLikes,maxLikes);
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
    public List<PostIndex> findByContentAndGroupPosted(String content, Long groupId) {
        return postIndexRepository.findByContentAndGroupPosted(content, groupId);
    }

    @Override
    public List<PostIndex> findByPdfContentAndGroupPosted(String pdfContent, Long groupId) {
        return postIndexRepository.findByPdfContentAndGroupPosted(pdfContent, groupId);
    }

    @Override
    public List<PostIndex> findByTitleAndGroupPosted(String title, Long groupId) {
        return postIndexRepository.findByTitleAndGroupPosted(title, groupId);
    }


    @Override
    public List<PostIndex> findByUserAndIsDeletedIsFalse(String user) {
        return postIndexRepository.findByUserAndIsDeletedIsFalse(user);
    }

    @Override
    public List<GroupIndex> findByPostCountBetween(Long minPosts, Long maxPosts) {
        return groupIndexRepository.findByPostCountBetween(minPosts,maxPosts);
    }

    @Override
    public List<PostIndex> findAllByUserInAndContent(ArrayList<String> usernameList, String content) {
        return postIndexRepository.findAllByUserInAndContent(usernameList,content);
    }

    @Override
    public List<PostIndex> findAllByUserInAndPdfContent(ArrayList<String> usernameList, String pdfContent) {
        return postIndexRepository.findAllByUserInAndPdfContent(usernameList,pdfContent);
    }

    @Override
    public List<PostIndex> findAllByUserIn(ArrayList<String> usernameList) {
        return  postIndexRepository.findAllByUserIn(usernameList);
    }


    public void save(GroupIndex groupIndex) {
        elasticsearchTemplate.save(groupIndex, IndexCoordinates.of("group_index"));
    }

    public void save(PostIndex postIndex) {
        elasticsearchTemplate.save(postIndex, IndexCoordinates.of("post_index"));
    }
}
