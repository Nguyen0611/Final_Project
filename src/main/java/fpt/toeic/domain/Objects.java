package fpt.toeic.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Objects.
 */
@Entity
@Table(name = "objects")
public class Objects implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "parent_id")
    private Long parentId;

    @Size(max = 60)
    @Column(name = "name", length = 60)
    private String name;

    @Size(max = 60)
    @Column(name = "code", length = 60)
    private String code;

    @Size(max = 1000)
    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "status")
    private Long status;

    @Size(max = 60)
    @Column(name = "icon", length = 60)
    private String icon;

    @Size(max = 60)
    @Column(name = "path", length = 60)
    private String path;

    @Column(name = "update_time")
    private ZonedDateTime updateTime;

    @Column(name = "type")
    private Long type;

    @OneToMany(mappedBy = "objects")
    private Set<RoleObject> dsRoleObjects = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public Objects parentId(Long parentId) {
        this.parentId = parentId;
        return this;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public Objects name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public Objects code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public Objects description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getStatus() {
        return status;
    }

    public Objects status(Long status) {
        this.status = status;
        return this;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getIcon() {
        return icon;
    }

    public Objects icon(String icon) {
        this.icon = icon;
        return this;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPath() {
        return path;
    }

    public Objects path(String path) {
        this.path = path;
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public Objects updateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Long getType() {
        return type;
    }

    public Objects type(Long type) {
        this.type = type;
        return this;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Set<RoleObject> getDsRoleObjects() {
        return dsRoleObjects;
    }

    public Objects dsRoleObjects(Set<RoleObject> roleObjects) {
        this.dsRoleObjects = roleObjects;
        return this;
    }

    public Objects addDsRoleObject(RoleObject roleObject) {
        this.dsRoleObjects.add(roleObject);
        roleObject.setObjects(this);
        return this;
    }

    public Objects removeDsRoleObject(RoleObject roleObject) {
        this.dsRoleObjects.remove(roleObject);
        roleObject.setObjects(null);
        return this;
    }

    public void setDsRoleObjects(Set<RoleObject> roleObjects) {
        this.dsRoleObjects = roleObjects;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Objects)) {
            return false;
        }
        return id != null && id.equals(((Objects) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Objects{" +
            "id=" + getId() +
            ", parentId=" + getParentId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", status=" + getStatus() +
            ", icon='" + getIcon() + "'" +
            ", path='" + getPath() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", type=" + getType() +
            "}";
    }
}
