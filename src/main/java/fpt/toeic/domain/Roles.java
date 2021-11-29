package fpt.toeic.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Roles.
 */
@Entity
@Table(name = "roles")
public class Roles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Column(name = "is_link")
    private String isLink;

    @Column(name = "update_time")
    private ZonedDateTime updateTime;

    @Column(name = "type")
    private Long type;

    @OneToMany(mappedBy = "roles")
    private Set<Users> dsUsers = new HashSet<>();

    @OneToMany(mappedBy = "roles")
    private Set<RoleObject> dsRoleObjects = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Roles name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public Roles code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public Roles description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getStatus() {
        return status;
    }

    public Roles status(Long status) {
        this.status = status;
        return this;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getIsLink() {
        return isLink;
    }

    public Roles isLink(String isLink) {
        this.isLink = isLink;
        return this;
    }

    public void setIsLink(String isLink) {
        this.isLink = isLink;
    }

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public Roles updateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Long getType() {
        return type;
    }

    public Roles type(Long type) {
        this.type = type;
        return this;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Set<Users> getDsUsers() {
        return dsUsers;
    }

    public Roles dsUsers(Set<Users> users) {
        this.dsUsers = users;
        return this;
    }

    public Roles addDsUsers(Users users) {
        this.dsUsers.add(users);
        users.setRoles(this);
        return this;
    }

    public Roles removeDsUsers(Users users) {
        this.dsUsers.remove(users);
        users.setRoles(null);
        return this;
    }

    public void setDsUsers(Set<Users> users) {
        this.dsUsers = users;
    }

    public Set<RoleObject> getDsRoleObjects() {
        return dsRoleObjects;
    }

    public Roles dsRoleObjects(Set<RoleObject> roleObjects) {
        this.dsRoleObjects = roleObjects;
        return this;
    }

    public Roles addDsRoleObject(RoleObject roleObject) {
        this.dsRoleObjects.add(roleObject);
        roleObject.setRoles(this);
        return this;
    }

    public Roles removeDsRoleObject(RoleObject roleObject) {
        this.dsRoleObjects.remove(roleObject);
        roleObject.setRoles(null);
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
        if (!(o instanceof Roles)) {
            return false;
        }
        return id != null && id.equals(((Roles) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Roles{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", status=" + getStatus() +
            ", isLink='" + getIsLink() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", type=" + getType() +
            "}";
    }
}
