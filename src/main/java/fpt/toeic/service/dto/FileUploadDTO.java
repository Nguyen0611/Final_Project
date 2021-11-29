package fpt.toeic.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link fpt.toeic.domain.FileUpload} entity.
 */
public class FileUploadDTO implements Serializable {
    
    private Long id;

    @Size(max = 200)
    private String typeFile;

    @Size(max = 200)
    private String path;


    private Long categoryId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeFile() {
        return typeFile;
    }

    public void setTypeFile(String typeFile) {
        this.typeFile = typeFile;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FileUploadDTO)) {
            return false;
        }

        return id != null && id.equals(((FileUploadDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FileUploadDTO{" +
            "id=" + getId() +
            ", typeFile='" + getTypeFile() + "'" +
            ", path='" + getPath() + "'" +
            ", categoryId=" + getCategoryId() +
            "}";
    }
}
