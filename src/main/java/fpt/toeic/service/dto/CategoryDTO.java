package fpt.toeic.service.dto;

import fpt.toeic.domain.FileUpload;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

/**
 * A DTO for the {@link fpt.toeic.domain.Category} entity.
 */
public class CategoryDTO extends BaseDTO implements Serializable {

    private Long id;

    private String code;

    @Size(max = 60)
    private String categoryName;
    private String oldCategoryName;

    private Long status;
    private String name;

    @NotNull
    private Long topicId;

    @NotNull
    private Long idType;

    private String nameType;

    private List<QuestionDTO> listQue;
    private Set<FileUpload> fileUploadDTOS;

    @NotNull
    private Long idPartTopic;
    private String namePartTopic;

    private String stCreationTime;
    private String stUpdateTime;
    private ZonedDateTime updateTime;

    private ZonedDateTime creationTime;

    public String getName() {
        return name;
    }

    public String getOldCategoryName() {
        return oldCategoryName;
    }

    public void setOldCategoryName(String oldCategoryName) {
        this.oldCategoryName = oldCategoryName;
    }

    public Set<FileUpload> getFileUploadDTOS() {
        return fileUploadDTOS;
    }

    public void setFileUploadDTOS(Set<FileUpload> fileUploadDTOS) {
        this.fileUploadDTOS = fileUploadDTOS;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getIdType() {
        return idType;
    }

    public String getStCreationTime() {
        return stCreationTime;
    }

    public void setStCreationTime(String stCreationTime) {
        this.stCreationTime = stCreationTime;
    }

    public String getStUpdateTime() {
        return stUpdateTime;
    }

    public void setStUpdateTime(String stUpdateTime) {
        this.stUpdateTime = stUpdateTime;
    }

    public void setIdType(Long idType) {
        this.idType = idType;
    }

    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameType) {
        this.nameType = nameType;
    }


    public List<QuestionDTO> getListQue() {
        return listQue;
    }

    public void setListQue(List<QuestionDTO> listQue) {
        this.listQue = listQue;
    }

    public Long getIdPartTopic() {
        return idPartTopic;
    }

    public void setIdPartTopic(Long idPartTopic) {
        this.idPartTopic = idPartTopic;
    }

    public String getNamePartTopic() {
        return namePartTopic;
    }

    public void setNamePartTopic(String namePartTopic) {
        this.namePartTopic = namePartTopic;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public ZonedDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(ZonedDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoryDTO)) {
            return false;
        }

        return id != null && id.equals(((CategoryDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoryDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", categoryName='" + getCategoryName() + "'" +
            ", status=" + getStatus() +
            ", updateTime='" + getUpdateTime() + "'" +
            ", creationTime='" + getCreationTime() + "'" +
            ", topicId=" + getTopicId() +
            "}";
    }
}
