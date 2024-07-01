package com.example.rs.ftn.ConnectSocialNetworkProject.indexrepository;
import com.example.rs.ftn.ConnectSocialNetworkProject.indexmodel.GroupIndex;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Lazy
@Repository
public interface GroupIndexRepository extends ElasticsearchRepository<GroupIndex, String> {
    List<GroupIndex> findByName(String name);
    GroupIndex getByDatabaseId(Long databaseId);
    List<GroupIndex> findByDescriptionOrPdfDescription(String description, String pdfDescription);
    List<GroupIndex> findAll();
    List<GroupIndex> findByPostCountBetween(Long minPosts, Long maxPosts);
    GroupIndex save(GroupIndex groupIndex);

}