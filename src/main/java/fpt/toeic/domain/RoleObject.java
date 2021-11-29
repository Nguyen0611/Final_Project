package fpt.toeic.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A RoleObject.
 */
@Entity
@Table(name = "role_object")
public class RoleObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "update_time")
    private ZonedDateTime updateTime;

    @ManyToOne
    @JsonIgnoreProperties(value = "dsRoleObjects", allowSetters = true)
    private Roles roles;

    @ManyToOne
    @JsonIgnoreProperties(value = "dsRoleObjects", allowSetters = true)
    private Objects objects;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public RoleObject updateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Roles getRoles() {
        return roles;
    }

    public RoleObject roles(Roles roles) {
        this.roles = roles;
        return this;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public Objects getObjects() {
        return objects;
    }

    public RoleObject objects(Objects objects) {
        this.objects = objects;
        return this;
    }

    public void setObjects(Objects objects) {
        this.objects = objects;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoleObject)) {
            return false;
        }
        return id != null && id.equals(((RoleObject) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RoleObject{" +
            "id=" + getId() +
            ", updateTime='" + getUpdateTime() + "'" +
            "}";
    }
}
