package fpt.toeic.repository;

import fpt.toeic.domain.Category;
import fpt.toeic.domain.FileUpload;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the FileUpload entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FileUploadRepository extends JpaRepository<FileUpload, Long>, JpaSpecificationExecutor<FileUpload> {
    List<FileUpload> findAllByCategory(Category category);

    void deleteAllByCategoryAndAndTypeFile(Category category, String type);

    void deleteAllByCategory(Category category);
}
