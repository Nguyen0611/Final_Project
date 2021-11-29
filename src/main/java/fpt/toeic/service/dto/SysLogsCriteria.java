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
 * Criteria class for the {@link fpt.toeic.domain.SysLogs} entity. This class is used
 * in {@link fpt.toeic.web.rest.SysLogsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sys-logs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SysLogsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter userImpact;

    private StringFilter codeAction;

    private StringFilter content;

    private ZonedDateTimeFilter impactTime;

    private ZonedDateTimeFilter endTime;

    private StringFilter ip;

    private StringFilter nameClient;

    public SysLogsCriteria() {
    }

    public SysLogsCriteria(SysLogsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.userImpact = other.userImpact == null ? null : other.userImpact.copy();
        this.codeAction = other.codeAction == null ? null : other.codeAction.copy();
        this.content = other.content == null ? null : other.content.copy();
        this.impactTime = other.impactTime == null ? null : other.impactTime.copy();
        this.endTime = other.endTime == null ? null : other.endTime.copy();
        this.ip = other.ip == null ? null : other.ip.copy();
        this.nameClient = other.nameClient == null ? null : other.nameClient.copy();
    }

    @Override
    public SysLogsCriteria copy() {
        return new SysLogsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getUserImpact() {
        return userImpact;
    }

    public void setUserImpact(StringFilter userImpact) {
        this.userImpact = userImpact;
    }

    public StringFilter getCodeAction() {
        return codeAction;
    }

    public void setCodeAction(StringFilter codeAction) {
        this.codeAction = codeAction;
    }

    public StringFilter getContent() {
        return content;
    }

    public void setContent(StringFilter content) {
        this.content = content;
    }

    public ZonedDateTimeFilter getImpactTime() {
        return impactTime;
    }

    public void setImpactTime(ZonedDateTimeFilter impactTime) {
        this.impactTime = impactTime;
    }

    public ZonedDateTimeFilter getEndTime() {
        return endTime;
    }

    public void setEndTime(ZonedDateTimeFilter endTime) {
        this.endTime = endTime;
    }

    public StringFilter getIp() {
        return ip;
    }

    public void setIp(StringFilter ip) {
        this.ip = ip;
    }

    public StringFilter getNameClient() {
        return nameClient;
    }

    public void setNameClient(StringFilter nameClient) {
        this.nameClient = nameClient;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SysLogsCriteria that = (SysLogsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(userImpact, that.userImpact) &&
            Objects.equals(codeAction, that.codeAction) &&
            Objects.equals(content, that.content) &&
            Objects.equals(impactTime, that.impactTime) &&
            Objects.equals(endTime, that.endTime) &&
            Objects.equals(ip, that.ip) &&
            Objects.equals(nameClient, that.nameClient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        userImpact,
        codeAction,
        content,
        impactTime,
        endTime,
        ip,
        nameClient
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SysLogsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (userImpact != null ? "userImpact=" + userImpact + ", " : "") +
                (codeAction != null ? "codeAction=" + codeAction + ", " : "") +
                (content != null ? "content=" + content + ", " : "") +
                (impactTime != null ? "impactTime=" + impactTime + ", " : "") +
                (endTime != null ? "endTime=" + endTime + ", " : "") +
                (ip != null ? "ip=" + ip + ", " : "") +
                (nameClient != null ? "nameClient=" + nameClient + ", " : "") +
            "}";
    }

}
