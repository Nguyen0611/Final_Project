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
 * Criteria class for the {@link fpt.toeic.domain.RoleObject} entity. This class is used
 * in {@link fpt.toeic.web.rest.RoleObjectResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /role-objects?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RoleObjectCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter updateTime;

    private LongFilter rolesId;

    private LongFilter objectsId;

    public RoleObjectCriteria() {
    }

    public RoleObjectCriteria(RoleObjectCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.updateTime = other.updateTime == null ? null : other.updateTime.copy();
        this.rolesId = other.rolesId == null ? null : other.rolesId.copy();
        this.objectsId = other.objectsId == null ? null : other.objectsId.copy();
    }

    @Override
    public RoleObjectCriteria copy() {
        return new RoleObjectCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ZonedDateTimeFilter getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(ZonedDateTimeFilter updateTime) {
        this.updateTime = updateTime;
    }

    public LongFilter getRolesId() {
        return rolesId;
    }

    public void setRolesId(LongFilter rolesId) {
        this.rolesId = rolesId;
    }

    public LongFilter getObjectsId() {
        return objectsId;
    }

    public void setObjectsId(LongFilter objectsId) {
        this.objectsId = objectsId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RoleObjectCriteria that = (RoleObjectCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(updateTime, that.updateTime) &&
            Objects.equals(rolesId, that.rolesId) &&
            Objects.equals(objectsId, that.objectsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        updateTime,
        rolesId,
        objectsId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RoleObjectCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (updateTime != null ? "updateTime=" + updateTime + ", " : "") +
                (rolesId != null ? "rolesId=" + rolesId + ", " : "") +
                (objectsId != null ? "objectsId=" + objectsId + ", " : "") +
            "}";
    }

}
