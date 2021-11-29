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
 * Criteria class for the {@link fpt.toeic.domain.Roles} entity. This class is used
 * in {@link fpt.toeic.web.rest.RolesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /roles?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RolesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter code;

    private StringFilter description;

    private LongFilter status;

    private StringFilter isLink;

    private ZonedDateTimeFilter updateTime;

    private LongFilter type;

    private LongFilter dsUsersId;

    private LongFilter dsRoleObjectId;

    public RolesCriteria() {
    }

    public RolesCriteria(RolesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.isLink = other.isLink == null ? null : other.isLink.copy();
        this.updateTime = other.updateTime == null ? null : other.updateTime.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.dsUsersId = other.dsUsersId == null ? null : other.dsUsersId.copy();
        this.dsRoleObjectId = other.dsRoleObjectId == null ? null : other.dsRoleObjectId.copy();
    }

    @Override
    public RolesCriteria copy() {
        return new RolesCriteria(this);
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

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public LongFilter getStatus() {
        return status;
    }

    public void setStatus(LongFilter status) {
        this.status = status;
    }

    public StringFilter getIsLink() {
        return isLink;
    }

    public void setIsLink(StringFilter isLink) {
        this.isLink = isLink;
    }

    public ZonedDateTimeFilter getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(ZonedDateTimeFilter updateTime) {
        this.updateTime = updateTime;
    }

    public LongFilter getType() {
        return type;
    }

    public void setType(LongFilter type) {
        this.type = type;
    }

    public LongFilter getDsUsersId() {
        return dsUsersId;
    }

    public void setDsUsersId(LongFilter dsUsersId) {
        this.dsUsersId = dsUsersId;
    }

    public LongFilter getDsRoleObjectId() {
        return dsRoleObjectId;
    }

    public void setDsRoleObjectId(LongFilter dsRoleObjectId) {
        this.dsRoleObjectId = dsRoleObjectId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RolesCriteria that = (RolesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(code, that.code) &&
            Objects.equals(description, that.description) &&
            Objects.equals(status, that.status) &&
            Objects.equals(isLink, that.isLink) &&
            Objects.equals(updateTime, that.updateTime) &&
            Objects.equals(type, that.type) &&
            Objects.equals(dsUsersId, that.dsUsersId) &&
            Objects.equals(dsRoleObjectId, that.dsRoleObjectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        code,
        description,
        status,
        isLink,
        updateTime,
        type,
        dsUsersId,
        dsRoleObjectId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RolesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (isLink != null ? "isLink=" + isLink + ", " : "") +
                (updateTime != null ? "updateTime=" + updateTime + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (dsUsersId != null ? "dsUsersId=" + dsUsersId + ", " : "") +
                (dsRoleObjectId != null ? "dsRoleObjectId=" + dsRoleObjectId + ", " : "") +
            "}";
    }

}
