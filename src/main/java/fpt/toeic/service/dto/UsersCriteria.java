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
 * Criteria class for the {@link fpt.toeic.domain.Users} entity. This class is used
 * in {@link fpt.toeic.web.rest.UsersResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /users?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UsersCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter fullName;

    private StringFilter pass;

    private StringFilter pathUrl;

    private ZonedDateTimeFilter dateOfBirth;

    private StringFilter mail;

    private StringFilter phone;

    private LongFilter status;

    private StringFilter resetKey;

    private ZonedDateTimeFilter resetDate;

    private StringFilter creator;

    private ZonedDateTimeFilter creationTime;

    private LongFilter rolesId;

    public UsersCriteria() {
    }

    public UsersCriteria(UsersCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.fullName = other.fullName == null ? null : other.fullName.copy();
        this.pass = other.pass == null ? null : other.pass.copy();
        this.pathUrl = other.pathUrl == null ? null : other.pathUrl.copy();
        this.dateOfBirth = other.dateOfBirth == null ? null : other.dateOfBirth.copy();
        this.mail = other.mail == null ? null : other.mail.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.resetKey = other.resetKey == null ? null : other.resetKey.copy();
        this.resetDate = other.resetDate == null ? null : other.resetDate.copy();
        this.creator = other.creator == null ? null : other.creator.copy();
        this.creationTime = other.creationTime == null ? null : other.creationTime.copy();
        this.rolesId = other.rolesId == null ? null : other.rolesId.copy();
    }

    @Override
    public UsersCriteria copy() {
        return new UsersCriteria(this);
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

    public StringFilter getFullName() {
        return fullName;
    }

    public void setFullName(StringFilter fullName) {
        this.fullName = fullName;
    }

    public StringFilter getPass() {
        return pass;
    }

    public void setPass(StringFilter pass) {
        this.pass = pass;
    }

    public StringFilter getPathUrl() {
        return pathUrl;
    }

    public void setPathUrl(StringFilter pathUrl) {
        this.pathUrl = pathUrl;
    }

    public ZonedDateTimeFilter getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(ZonedDateTimeFilter dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public StringFilter getMail() {
        return mail;
    }

    public void setMail(StringFilter mail) {
        this.mail = mail;
    }

    public StringFilter getPhone() {
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public LongFilter getStatus() {
        return status;
    }

    public void setStatus(LongFilter status) {
        this.status = status;
    }

    public StringFilter getResetKey() {
        return resetKey;
    }

    public void setResetKey(StringFilter resetKey) {
        this.resetKey = resetKey;
    }

    public ZonedDateTimeFilter getResetDate() {
        return resetDate;
    }

    public void setResetDate(ZonedDateTimeFilter resetDate) {
        this.resetDate = resetDate;
    }

    public StringFilter getCreator() {
        return creator;
    }

    public void setCreator(StringFilter creator) {
        this.creator = creator;
    }

    public ZonedDateTimeFilter getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(ZonedDateTimeFilter creationTime) {
        this.creationTime = creationTime;
    }

    public LongFilter getRolesId() {
        return rolesId;
    }

    public void setRolesId(LongFilter rolesId) {
        this.rolesId = rolesId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UsersCriteria that = (UsersCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(fullName, that.fullName) &&
            Objects.equals(pass, that.pass) &&
            Objects.equals(pathUrl, that.pathUrl) &&
            Objects.equals(dateOfBirth, that.dateOfBirth) &&
            Objects.equals(mail, that.mail) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(status, that.status) &&
            Objects.equals(resetKey, that.resetKey) &&
            Objects.equals(resetDate, that.resetDate) &&
            Objects.equals(creator, that.creator) &&
            Objects.equals(creationTime, that.creationTime) &&
            Objects.equals(rolesId, that.rolesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        fullName,
        pass,
        pathUrl,
        dateOfBirth,
        mail,
        phone,
        status,
        resetKey,
        resetDate,
        creator,
        creationTime,
        rolesId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UsersCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (fullName != null ? "fullName=" + fullName + ", " : "") +
                (pass != null ? "pass=" + pass + ", " : "") +
                (pathUrl != null ? "pathUrl=" + pathUrl + ", " : "") +
                (dateOfBirth != null ? "dateOfBirth=" + dateOfBirth + ", " : "") +
                (mail != null ? "mail=" + mail + ", " : "") +
                (phone != null ? "phone=" + phone + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (resetKey != null ? "resetKey=" + resetKey + ", " : "") +
                (resetDate != null ? "resetDate=" + resetDate + ", " : "") +
                (creator != null ? "creator=" + creator + ", " : "") +
                (creationTime != null ? "creationTime=" + creationTime + ", " : "") +
                (rolesId != null ? "rolesId=" + rolesId + ", " : "") +
            "}";
    }

}
