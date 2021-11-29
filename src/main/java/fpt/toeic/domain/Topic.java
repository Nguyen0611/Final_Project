package fpt.toeic.domain;


import fpt.toeic.service.dto.TopicDTO;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Topic.
 */
@Entity
@Table(name = "topic")
public class Topic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 60)
    @Column(name = "name", length = 60)
    private String name;

    @Size(max = 60)
    @Column(name = "code", length = 60)
    private String code;

    @Column(name = "id_type")
    private Long idType;

    @Column(name = "id_part_topic")
    private Long idPartTopic;

    @Column(name = "update_time")
    private ZonedDateTime updateTime;

    @Column(name = "creation_time")
    private ZonedDateTime creationTime;

    @OneToMany(mappedBy = "topic", fetch = FetchType.EAGER)
    private Set<Category> dsCategories = new HashSet<>();

    public TopicDTO toDto(Topic entity) {
        if ( entity == null ) {
            return null;
        }

        TopicDTO topicDTO = new TopicDTO();

        topicDTO.setId( entity.getId() );
        topicDTO.setName( entity.getName() );
        topicDTO.setCode( entity.getCode() );
        topicDTO.setIdType( entity.getIdType() );
        topicDTO.setIdPartTopic( entity.getIdPartTopic() );
        topicDTO.setUpdateTime( entity.getUpdateTime() );
        topicDTO.setCreationTime( entity.getCreationTime() );

        return topicDTO;
    }
    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Topic name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public Topic code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getIdType() {
        return idType;
    }

    public Topic idType(Long idType) {
        this.idType = idType;
        return this;
    }

    public void setIdType(Long idType) {
        this.idType = idType;
    }

    public Long getIdPartTopic() {
        return idPartTopic;
    }

    public Topic idPartTopic(Long idPartTopic) {
        this.idPartTopic = idPartTopic;
        return this;
    }

    public void setIdPartTopic(Long idPartTopic) {
        this.idPartTopic = idPartTopic;
    }

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public Topic updateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public ZonedDateTime getCreationTime() {
        return creationTime;
    }

    public Topic creationTime(ZonedDateTime creationTime) {
        this.creationTime = creationTime;
        return this;
    }

    public void setCreationTime(ZonedDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public Set<Category> getDsCategories() {
        return dsCategories;
    }

    public Topic dsCategories(Set<Category> categories) {
        this.dsCategories = categories;
        return this;
    }

    public Topic addDsCategory(Category category) {
        this.dsCategories.add(category);
        category.setTopic(this);
        return this;
    }

    public Topic removeDsCategory(Category category) {
        this.dsCategories.remove(category);
        category.setTopic(null);
        return this;
    }

    public void setDsCategories(Set<Category> categories) {
        this.dsCategories = categories;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Topic)) {
            return false;
        }
        return id != null && id.equals(((Topic) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Topic{" +
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
