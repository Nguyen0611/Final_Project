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

/**
 * Criteria class for the {@link fpt.toeic.domain.FileUpload} entity. This class is used
 * in {@link fpt.toeic.web.rest.FileUploadResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /file-uploads?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FileUploadCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter typeFile;

    private StringFilter path;

    private LongFilter categoryId;

    public FileUploadCriteria() {
    }

    public FileUploadCriteria(FileUploadCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.typeFile = other.typeFile == null ? null : other.typeFile.copy();
        this.path = other.path == null ? null : other.path.copy();
        this.categoryId = other.categoryId == null ? null : other.categoryId.copy();
    }

    @Override
    public FileUploadCriteria copy() {
        return new FileUploadCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTypeFile() {
        return typeFile;
    }

    public void setTypeFile(StringFilter typeFile) {
        this.typeFile = typeFile;
    }

    public StringFilter getPath() {
        return path;
    }

    public void setPath(StringFilter path) {
        this.path = path;
    }

    public LongFilter getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(LongFilter categoryId) {
        this.categoryId = categoryId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FileUploadCriteria that = (FileUploadCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(typeFile, that.typeFile) &&
            Objects.equals(path, that.path) &&
            Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        typeFile,
        path,
        categoryId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FileUploadCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (typeFile != null ? "typeFile=" + typeFile + ", " : "") +
                (path != null ? "path=" + path + ", " : "") +
                (categoryId != null ? "categoryId=" + categoryId + ", " : "") +
            "}";
    }

}
