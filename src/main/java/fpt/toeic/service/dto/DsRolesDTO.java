package fpt.toeic.service.dto;

import javax.validation.constraints.Size;
import java.io.Serializable;

public class DsRolesDTO implements Serializable {


    private Long id;

    private Long parenId;

    @Size(max = 60)
    private String title;

    @Size(max = 60)
    private String code;

    private Long status;

    @Size(max = 60)
    private String icon;

    @Size(max = 60)
    private String link;

    private Long type;

    public DsRolesDTO() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParenId() {
        return parenId;
    }

    public void setParenId(Long parenId) {
        this.parenId = parenId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }
}
