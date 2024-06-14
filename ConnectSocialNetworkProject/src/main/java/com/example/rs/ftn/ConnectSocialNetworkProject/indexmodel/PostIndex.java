package com.example.rs.ftn.ConnectSocialNetworkProject.indexmodel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "dummy_index")
public class PostIndex {

@Id
private String id;

@Field(type = FieldType.Text, store = true, name = "content",analyzer = "serbian", searchAnalyzer = "serbian")
private String content;

@Field(type = FieldType.Text, store = true, name = "title",analyzer = "serbian", searchAnalyzer = "serbian")
private String title;

@Field(type = FieldType.Date, store = true, name = "creation_date")
private LocalDateTime creationDate;

@Field(type = FieldType.Boolean, store = true, name = "is_deleted")
private boolean isDeleted;

@Field(type = FieldType.Text, store = true, name = "image_paths")
private List<String> imagePaths;

@Field(type = FieldType.Text, store = true, name = "user")
private String user;

@Field(type = FieldType.Long, store = true, name = "group_posted")
private Long groupPosted;

@Field(type = FieldType.Long, store = true, name = "database_id")
private Long databaseId;

}