package fpt.toeic.domain;


import fpt.toeic.security.SecurityUtils;
import fpt.toeic.utils.DateUtil;
import fpt.toeic.utils.Utils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A SysLogs.
 */
@Entity
@Table(name = "sys_logs")
public class SysLogs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_impact")
    private String userImpact;

    @Column(name = "code_action")
    private String codeAction;

    @Column(name = "content")
    private String content;

    @Column(name = "impact_time")
    private ZonedDateTime impactTime;

    @Column(name = "end_time")
    private ZonedDateTime endTime;

    @Column(name = "ip")
    private String ip;

    @Column(name = "name_client")
    private String nameClient;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserImpact() {
        return userImpact;
    }

    public SysLogs userImpact(String userImpact) {
        this.userImpact = userImpact;
        return this;
    }

    public void setUserImpact(String userImpact) {
        this.userImpact = userImpact;
    }

    public String getCodeAction() {
        return codeAction;
    }

    public SysLogs codeAction(String codeAction) {
        this.codeAction = codeAction;
        return this;
    }

    public void setCodeAction(String codeAction) {
        this.codeAction = codeAction;
    }

    public String getContent() {
        return content;
    }

    public SysLogs content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ZonedDateTime getImpactTime() {
        return impactTime;
    }

    public SysLogs impactTime(ZonedDateTime impactTime) {
        this.impactTime = impactTime;
        return this;
    }

    public void setImpactTime(ZonedDateTime impactTime) {
        this.impactTime = impactTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public SysLogs endTime(ZonedDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public String getIp() {
        return ip;
    }

    public SysLogs ip(String ip) {
        this.ip = ip;
        return this;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getNameClient() {
        return nameClient;
    }

    public SysLogs nameClient(String nameClient) {
        this.nameClient = nameClient;
        return this;
    }

    public void setNameClient(String nameClient) {
        this.nameClient = nameClient;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    public SysLogs() {
    }

    public SysLogs(ZonedDateTime endTime, HttpServletRequest request, String name, HttpServletResponse response) {
        String user = SecurityUtils.getCurrentUserLogin().orElse(name);;
        this.setCodeAction( ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod());
        this.setContent(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURL().toString());
        this.setIp(Utils.getIp(request));
        this.setUserImpact(user);
        this.setNameClient(Utils.getNameClient());
        this.setImpactTime(endTime);
        this.setEndTime(DateUtil.getDateC());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SysLogs)) {
            return false;
        }
        return id != null && id.equals(((SysLogs) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SysLogs{" +
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
