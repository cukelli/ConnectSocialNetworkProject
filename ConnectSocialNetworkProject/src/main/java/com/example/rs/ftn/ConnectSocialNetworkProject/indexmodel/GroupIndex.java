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
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "group_index")
public class GroupIndex {

    @Id
    private String id;

    @Field(type = FieldType.Text, store = true, name = "name",analyzer = "serbian", searchAnalyzer = "serbian")
    private String name;

    @Field(type = FieldType.Text, store = true, name = "groupDescriptionPdf",analyzer = "serbian", searchAnalyzer = "serbian")
    private String groupDescriptionPdf;

    @Field(type = FieldType.Text, store = true, name = "description", analyzer = "serbian", searchAnalyzer = "serbian")
    private String description;

    @Field(type = FieldType.Date, store = true, name = "created_at")
    private LocalDate createdAt;

    @Field(type = FieldType.Boolean, store = true, name = "is_suspended")
    private boolean isSuspended;

    @Field(type = FieldType.Boolean, store = true, name = "is_deleted")
    private boolean isDeleted;

    @Field(type = FieldType.Text, store = true, name = "suspended_reason", index = false)
    private String suspendedReason;

    @Field(type = FieldType.Long, store = true, name = "database_id")
    private Long databaseId;

}