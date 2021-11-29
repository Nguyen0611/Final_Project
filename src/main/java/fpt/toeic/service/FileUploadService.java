package fpt.toeic.service;

import fpt.toeic.domain.Category;
import fpt.toeic.domain.FileUpload;
import fpt.toeic.repository.FileUploadRepository;
import fpt.toeic.service.dto.FileUploadDTO;
import fpt.toeic.service.mapper.FileUploadMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link FileUpload}.
 */
@Service
@Transactional
public class FileUploadService {

    private final Logger log = LoggerFactory.getLogger(FileUploadService.class);

    private final FileUploadRepository fileUploadRepository;

    private final FileUploadMapper fileUploadMapper;

    public FileUploadService(FileUploadRepository fileUploadRepository, FileUploadMapper fileUploadMapper) {
        this.fileUploadRepository = fileUploadRepository;
        this.fileUploadMapper = fileUploadMapper;
    }

    /**
     * Save a fileUpload.
     *
     * @param fileUploadDTO the entity to save.
     * @return the persisted entity.
     */
    public FileUploadDTO save(FileUploadDTO fileUploadDTO) {
        log.debug("Request to save FileUpload : {}", fileUploadDTO);
        FileUpload fileUpload = fileUploadMapper.toEntity(fileUploadDTO);
        fileUpload = fileUploadRepository.save(fileUpload);
        return fileUploadMapper.toDto(fileUpload);
    }

    /**
     * Get all the fileUploads.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FileUploadDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FileUploads");
        return fileUploadRepository.findAll(pageable)
            .map(fileUploadMapper::toDto);
    }


    /**
     * Get one fileUpload by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FileUploadDTO> findOne(Long id) {
        log.debug("Request to get FileUpload : {}", id);
        return fileUploadRepository.findById(id)
            .map(fileUploadMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<FileUploadDTO> findAll(Category id) {
        log.debug("Request to get FileUpload : {}", id);
        List<FileUploadDTO> fileUploadDTOS = new ArrayList<>();
        List<FileUpload> list = fileUploadRepository.findAllByCategory(id);
        for(FileUpload fileUpload : list) {
            FileUploadDTO fileUploadDTO = new FileUploadDTO();
            fileUploadDTO.setTypeFile(fileUpload.getTypeFile());
            fileUploadDTO.setPath(fileUpload.getPath());
            fileUploadDTOS.add(fileUploadDTO);
        }
        return fileUploadDTOS;
    }

    /**
     * Delete the fileUpload by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FileUpload : {}", id);
        fileUploadRepository.deleteById(id);
    }

    public void delete(Category category, String type) {
        log.debug("Request to delete FileUpload : {}", category);
        fileUploadRepository.deleteAllByCategoryAndAndTypeFile(category,type);
    }

    public void deleteAll(Category category) {
        log.debug("Request to delete FileUpload : {}", category);
        fileUploadRepository.deleteAllByCategory(category);
    }
}
