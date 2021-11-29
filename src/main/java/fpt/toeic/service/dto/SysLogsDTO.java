package fpt.toeic.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;

/**
 * A DTO for the {@link fpt.toeic.domain.SysLogs} entity.
 */
public class SysLogsDTO implements Serializable {
    
    private Long id;

    private String userImpact;

    private String codeAction;

    private String content;

    private ZonedDateTime impactTime;

    private ZonedDateTime endTime;

    private String ip;

    private String nameClient;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserImpact() {
        return userImpact;
    }

    public void setUserImpact(String userImpact) {
        this.userImpact = userImpact;
    }

    public String getCodeAction() {
        return codeAction;
    }

    public void setCodeAction(String codeAction) {
        this.codeAction = codeAction;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ZonedDateTime getImpactTime() {
        return impactTime;
    }

    public void setImpactTime(ZonedDateTime impactTime) {
        this.impactTime = impactTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getNameClient() {
        return nameClient;
    }

    public void setNameClient(String nameClient) {
        this.nameClient = nameClient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SysLogsDTO)) {
            return false;
        }

        return id != null && id.equals(((SysLogsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SysLogsDTO{" +
            "id=" + getId() +
            ", userImpact='" + getUserImpact() + "'" +
            ", codeAction='" + getCodeAction() + "'" +
            ", content='" + getContent() + "'" +
            ", impactTime='" + getImpactTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", ip='" + getIp() + "'" +
            ", nameClient='" + getNameClient() + "'" +
            "}";
    }
}
