package com.example.rs.ftn.ConnectSocialNetworkProject.indexmodel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "dummy_index")
@Setting(settingPath = "/configuration/serbian-analyzer-config.json")
public class PostIndex {

@Id
@Getter
@Setter
private String id;

@Getter
@Setter
@Field(type = FieldType.Text, store = true, name = "content",
        analyzer = "serbian_simple", searchAnalyzer = "serbian_simple")
private String content;

@Getter
@Setter
@Field(type = FieldType.Text, store = true, name = "title",
            analyzer = "serbian_simple", searchAnalyzer = "serbian_simple")
    private String title;

@Getter
@Setter
@Field(type = FieldType.Date, store = true, name = "creation_date")
private LocalDateTime creationDate;

@Getter
@Setter
@Field(type = FieldType.Boolean, store = true, name = "is_deleted")
private boolean isDeleted;

@Getter
@Setter
@Field(type = FieldType.Text, store = true, name = "image_paths")
private List<String> imagePaths;

@Getter
@Setter
@Field(type = FieldType.Text, store = true, name = "user")
private String user;

@Getter
@Setter
@Field(type = FieldType.Long, store = true, name = "group_posted")
private Long groupPosted;


}