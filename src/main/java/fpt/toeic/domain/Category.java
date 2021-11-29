package fpt.toeic.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Category.
 */
@Entity
@Table(name = "category")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Size(max = 60)
    @Column(name = "category_name", length = 60)
    private String categoryName;

    @Column(name = "status")
    private Long status;

    @Column(name = "update_time")
    private ZonedDateTime updateTime;

    @Column(name = "creation_time")
    private ZonedDateTime creationTime;

    @OneToMany(mappedBy = "category")
    private Set<QuestionAnswers> dsQuestionAnswers = new HashSet<>();

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private Set<FileUpload> dsFileUploads = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "dsCategories", allowSetters = true)
    private Topic topic;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Category code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Category categoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getStatus() {
        return status;
    }

    public Category status(Long status) {
        this.status = status;
        return this;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public Category updateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public ZonedDateTime getCreationTime() {
        return creationTime;
    }

    public Category creationTime(ZonedDateTime creationTime) {
        this.creationTime = creationTime;
        return this;
    }

    public void setCreationTime(ZonedDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public Set<QuestionAnswers> getDsQuestionAnswers() {
        return dsQuestionAnswers;
    }

    public Category dsQuestionAnswers(Set<QuestionAnswers> questionAnswers) {
        this.dsQuestionAnswers = questionAnswers;
        return this;
    }

    public Category addDsQuestionAnswers(QuestionAnswers questionAnswers) {
        this.dsQuestionAnswers.add(questionAnswers);
        questionAnswers.setCategory(this);
        return this;
    }

    public Category removeDsQuestionAnswers(QuestionAnswers questionAnswers) {
        this.dsQuestionAnswers.remove(questionAnswers);
        questionAnswers.setCategory(null);
        return this;
    }

    public void setDsQuestionAnswers(Set<QuestionAnswers> questionAnswers) {
        this.dsQuestionAnswers = questionAnswers;
    }

    public Set<FileUpload> getDsFileUploads() {
        return dsFileUploads;
    }

    public Category dsFileUploads(Set<FileUpload> fileUploads) {
        this.dsFileUploads = fileUploads;
        return this;
    }

    public Category addDsFileUpload(FileUpload fileUpload) {
        this.dsFileUploads.add(fileUpload);
        fileUpload.setCategory(this);
        return this;
    }

    public Category removeDsFileUpload(FileUpload fileUpload) {
        this.dsFileUploads.remove(fileUpload);
        fileUpload.setCategory(null);
        return this;
    }

    public void setDsFileUploads(Set<FileUpload> fileUploads) {
        this.dsFileUploads = fileUploads;
    }

    public Topic getTopic() {
        return topic;
    }

    public Category topic(Topic topic) {
        this.topic = topic;
        return this;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category)) {
            return false;
        }
        return id != null && id.equals(((Category) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Category{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", categoryName='" + getCategoryName() + "'" +
            ", status=" + getStatus() +
            ", updateTime='" + getUpdateTime() + "'" +
            ", creationTime='" + getCreationTime() + "'" +
            "}";
    }
}
