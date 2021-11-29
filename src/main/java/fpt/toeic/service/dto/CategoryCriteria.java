package fpt.toeic.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link fpt.toeic.domain.Category} entity. This class is used
 * in {@link fpt.toeic.web.rest.CategoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /categories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CategoryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private StringFilter categoryName;

    private LongFilter status;

    private ZonedDateTimeFilter updateTime;

    private ZonedDateTimeFilter creationTime;

    private LongFilter dsQuestionAnswersId;

    private LongFilter dsFileUploadId;

    private LongFilter topicId;

    public CategoryCriteria() {
    }

    public CategoryCriteria(CategoryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.categoryName = other.categoryName == null ? null : other.categoryName.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.updateTime = other.updateTime == null ? null : other.updateTime.copy();
        this.creationTime = other.creationTime == null ? null : other.creationTime.copy();
        this.dsQuestionAnswersId = other.dsQuestionAnswersId == null ? null : other.dsQuestionAnswersId.copy();
        this.dsFileUploadId = other.dsFileUploadId == null ? null : other.dsFileUploadId.copy();
        this.topicId = other.topicId == null ? null : other.topicId.copy();
    }

    @Override
    public CategoryCriteria copy() {
        return new CategoryCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public StringFilter getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(StringFilter categoryName) {
        this.categoryName = categoryName;
    }

    public LongFilter getStatus() {
        return status;
    }

    public void setStatus(LongFilter status) {
        this.status = status;
    }

    public ZonedDateTimeFilter getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(ZonedDateTimeFilter updateTime) {
        this.updateTime = updateTime;
    }

    public ZonedDateTimeFilter getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(ZonedDateTimeFilter creationTime) {
        this.creationTime = creationTime;
    }

    public LongFilter getDsQuestionAnswersId() {
        return dsQuestionAnswersId;
    }

    public void setDsQuestionAnswersId(LongFilter dsQuestionAnswersId) {
        this.dsQuestionAnswersId = dsQuestionAnswersId;
    }

    public LongFilter getDsFileUploadId() {
        return dsFileUploadId;
    }

    public void setDsFileUploadId(LongFilter dsFileUploadId) {
        this.dsFileUploadId = dsFileUploadId;
    }

    public LongFilter getTopicId() {
        return topicId;
    }

    public void setTopicId(LongFilter topicId) {
        this.topicId = topicId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CategoryCriteria that = (CategoryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(categoryName, that.categoryName) &&
            Objects.equals(status, that.status) &&
            Objects.equals(updateTime, that.updateTime) &&
            Objects.equals(creationTime, that.creationTime) &&
            Objects.equals(dsQuestionAnswersId, that.dsQuestionAnswersId) &&
            Objects.equals(dsFileUploadId, that.dsFileUploadId) &&
            Objects.equals(topicId, that.topicId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        code,
        categoryName,
        status,
        updateTime,
        creationTime,
        dsQuestionAnswersId,
        dsFileUploadId,
        topicId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (categoryName != null ? "categoryName=" + categoryName + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (updateTime != null ? "updateTime=" + updateTime + ", " : "") +
                (creationTime != null ? "creationTime=" + creationTime + ", " : "") +
                (dsQuestionAnswersId != null ? "dsQuestionAnswersId=" + dsQuestionAnswersId + ", " : "") +
                (dsFileUploadId != null ? "dsFileUploadId=" + dsFileUploadId + ", " : "") +
                (topicId != null ? "topicId=" + topicId + ", " : "") +
            "}";
    }

}
