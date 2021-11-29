package fpt.toeic.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;

/**
 * A DTO for the {@link fpt.toeic.domain.RoleObject} entity.
 */
public class RoleObjectDTO implements Serializable {
    
    private Long id;

    private ZonedDateTime updateTime;


    private Long rolesId;

    private Long objectsId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Long getRolesId() {
        return rolesId;
    }

    public void setRolesId(Long rolesId) {
        this.rolesId = rolesId;
    }

    public Long getObjectsId() {
        return objectsId;
    }

    public void setObjectsId(Long objectsId) {
        this.objectsId = objectsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoleObjectDTO)) {
            return false;
        }

        return id != null && id.equals(((RoleObjectDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RoleObjectDTO{" +
            "id=" + getId() +
            ", updateTime='" + getUpdateTime() + "'" +
            ", rolesId=" + getRolesId() +
            ", objectsId=" + getObjectsId() +
            "}";
    }
}
