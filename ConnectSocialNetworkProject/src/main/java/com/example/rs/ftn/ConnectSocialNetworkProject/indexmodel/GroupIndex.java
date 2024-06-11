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
@Setting(settingPath = "/configuration/serbian-analyzer-config.json")
public class GroupIndex {

    @Setter
    @Id
    private String id;

    @Setter
    @Field(type = FieldType.Text, store = true, name = "name")
    private String name;

    @Setter
    @Field(type = FieldType.Text, store = true, name = "description", analyzer = "serbian_simple", searchAnalyzer = "serbian_simple")
    private String description;

    @Setter
    @Field(type = FieldType.Date, store = true, name = "created_at")
    private LocalDate createdAt;

    @Setter
    @Field(type = FieldType.Boolean, store = true, name = "is_suspended")
    private boolean isSuspended;

    @Setter
    @Field(type = FieldType.Boolean, store = true, name = "is_deleted")
    private boolean isDeleted;

    @Setter
    @Field(type = FieldType.Text, store = true, name = "suspended_reason", index = false)
    private String suspendedReason;

}