package com.example.rs.ftn.ConnectSocialNetworkProject.indexrepository;
import com.example.rs.ftn.ConnectSocialNetworkProject.indexmodel.GroupIndex;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Lazy
@Repository
public interface GroupIndexRepository extends ElasticsearchRepository<GroupIndex, String> {
}