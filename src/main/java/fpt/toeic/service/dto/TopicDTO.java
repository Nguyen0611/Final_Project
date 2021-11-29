package fpt.toeic.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link fpt.toeic.domain.Topic} entity.
 */
public class TopicDTO extends BaseDTO implements Serializable {

    private Long id;

    @Size(max = 60)
    private String name;

    @Size(max = 60)
    private String code;

    private Long idType;

    private String nameType;

    private Long idPartTopic;
    private String namePartTopic;

    private ZonedDateTime updateTime;
    private String stCreationTime;
    private String stUpdateTime;
    private int isLength;

    public int getIsLength() {
        return isLength;
    }

    public void setIsLength(int isLength) {
        this.isLength = isLength;
    }

    public String getStUpdateTime() {
        return stUpdateTime;
    }

    public void setStUpdateTime(String stUpdateTime) {
        this.stUpdateTime = stUpdateTime;
    }

    private ZonedDateTime creationTime;

    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameType) {
        this.nameType = nameType;
    }

    public String getNamePartTopic() {
        return namePartTopic;
    }

    public void setNamePartTopic(String namePartTopic) {
        this.namePartTopic = namePartTopic;
    }

    public String getStCreationTime() {
        return stCreationTime;
    }

    public void setStCreationTime(String stCreationTime) {
        this.stCreationTime = stCreationTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getIdType() {
        return idType;
    }

    public void setIdType(Long idType) {
        this.idType = idType;
    }

    public Long getIdPartTopic() {
        return idPartTopic;
    }

    public void setIdPartTopic(Long idPartTopic) {
        this.idPartTopic = idPartTopic;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TopicDTO)) {
            return false;
        }

        return id != null && id.equals(((TopicDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TopicDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", idType=" + getIdType() +
            ", idPartTopic=" + getIdPartTopic() +
            ", updateTime='" + getUpdateTime() + "'" +
            ", creationTime='" + getCreationTime() + "'" +
            "}";
    }
}
