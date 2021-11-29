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
 * Criteria class for the {@link fpt.toeic.domain.Topic} entity. This class is used
 * in {@link fpt.toeic.web.rest.TopicResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /topics?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TopicCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter code;

    private LongFilter idType;

    private LongFilter idPartTopic;

    private ZonedDateTimeFilter updateTime;

    private ZonedDateTimeFilter creationTime;

    private LongFilter dsCategoryId;

    public TopicCriteria() {
    }

    public TopicCriteria(TopicCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.idType = other.idType == null ? null : other.idType.copy();
        this.idPartTopic = other.idPartTopic == null ? null : other.idPartTopic.copy();
        this.updateTime = other.updateTime == null ? null : other.updateTime.copy();
        this.creationTime = other.creationTime == null ? null : other.creationTime.copy();
        this.dsCategoryId = other.dsCategoryId == null ? null : other.dsCategoryId.copy();
    }

    @Override
    public TopicCriteria copy() {
        return new TopicCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public LongFilter getIdType() {
        return idType;
    }

    public void setIdType(LongFilter idType) {
        this.idType = idType;
    }

    public LongFilter getIdPartTopic() {
        return idPartTopic;
    }

    public void setIdPartTopic(LongFilter idPartTopic) {
        this.idPartTopic = idPartTopic;
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

    public LongFilter getDsCategoryId() {
        return dsCategoryId;
    }

    public void setDsCategoryId(LongFilter dsCategoryId) {
        this.dsCategoryId = dsCategoryId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TopicCriteria that = (TopicCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(code, that.code) &&
            Objects.equals(idType, that.idType) &&
            Objects.equals(idPartTopic, that.idPartTopic) &&
            Objects.equals(updateTime, that.updateTime) &&
            Objects.equals(creationTime, that.creationTime) &&
            Objects.equals(dsCategoryId, that.dsCategoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        code,
        idType,
        idPartTopic,
        updateTime,
        creationTime,
        dsCategoryId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TopicCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (idType != null ? "idType=" + idType + ", " : "") +
                (idPartTopic != null ? "idPartTopic=" + idPartTopic + ", " : "") +
                (updateTime != null ? "updateTime=" + updateTime + ", " : "") +
                (creationTime != null ? "creationTime=" + creationTime + ", " : "") +
                (dsCategoryId != null ? "dsCategoryId=" + dsCategoryId + ", " : "") +
            "}";
    }

}
