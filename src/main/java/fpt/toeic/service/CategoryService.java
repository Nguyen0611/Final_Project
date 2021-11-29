package fpt.toeic.service;

import fpt.toeic.domain.Category;
import fpt.toeic.domain.Topic;
import fpt.toeic.repository.CategoryRepository;
import fpt.toeic.service.dto.CategoryDTO;
import fpt.toeic.service.dto.QuestionAnswersDTO;
import fpt.toeic.service.mapper.CategoryMapper;
import fpt.toeic.utils.DataUtil;
import liquibase.pro.packaged.L;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * Service Implementation for managing {@link Category}.
 */
@Service
@Transactional
public class CategoryService {

    private final Logger log = LoggerFactory.getLogger(CategoryService.class);

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    /**
     * Save a category.
     *
     * @param categoryDTO the entity to save.
     * @return the persisted entity.
     */
    public Category save(CategoryDTO categoryDTO) {
        log.debug("Request to save Category : {}", categoryDTO);
        Category category = categoryMapper.toEntity(categoryDTO);
        return categoryRepository.save(category);
    }

    /**
     * Get all the categories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Categories");
        return categoryRepository.findAll(pageable)
            .map(categoryMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Category findCode(String code, Topic topic) {
        log.debug("Request to get all Categories");
        return categoryRepository.findByCodeAndTopic(code,topic);
    }

    @Transactional(readOnly = true)
    public List<CategoryDTO> search(String name, Long id_type, Long id_part_topic, int page, int page_size) {
        log.debug("Request to get all categories");
        List<CategoryDTO> lstObject = categoryRepository.searchTopic(DataUtil.makeLikeParam(name), id_type, id_part_topic, page, page_size)
            .stream().map(e -> {
                CategoryDTO dto = new CategoryDTO();
                dto.setId(DataUtil.safeToLong(e[0]));
                dto.setCategoryName(DataUtil.safeToString(e[1]));
                dto.setStatus(DataUtil.safeToLong(e[2]));
                dto.setTopicId(DataUtil.safeToLong(e[3]));
                dto.setName(DataUtil.safeToString(e[4]));
                dto.setIdType(DataUtil.safeToLong(e[5]));
                dto.setNameType(DataUtil.safeToString(e[6]));
                dto.setIdPartTopic(DataUtil.safeToLong(e[7]));
                dto.setNamePartTopic(DataUtil.safeToString(e[8]));
                dto.setStCreationTime(DataUtil.safeToString(e[9]));
                dto.setStUpdateTime(DataUtil.safeToString(e[10]));
                return dto;
            }).collect(toList());
        return lstObject;
    }

    @Transactional(readOnly = true)
    public Long total(String name, Long id_type, Long id_part_topic) {
        log.debug("Request to get all categories");
        return categoryRepository.countAllByNameAndIdTypeAndIdPartTopic(DataUtil.makeLikeParam(name), id_type, id_part_topic);
    }


    /**
     * Get one category by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CategoryDTO> findOne(Long id) {
        log.debug("Request to get Category : {}", id);
        Optional<Category> category = categoryRepository.findById(id);
        return category
            .map(categoryMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<Category> findOneEntity(Long id) {
        log.debug("Request to get Category : {}", id);
        Optional<Category> category = categoryRepository.findById(id);
        return category;
    }


//    public List<QuestionAnswersDTO> getDuplicates(final List<QuestionAnswersDTO> personList) {
//        return getDuplicatesMap(personList).values().stream()
//            .filter(duplicates -> duplicates.size() > 1)
//            .flatMap(Collection::stream)
//            .collect(toList());
//    }
//
//    private Map<QuestionAnswersDTO, List<QuestionAnswersDTO>> getDuplicatesMap(List<QuestionAnswersDTO> personList) {
//        return personList.stream().collect(groupingBy((d1, d2) -> {d1.uniqueAttributes(d1)}));
//    }

    public static List<QuestionAnswersDTO> extractDuplicatesWithIdentityCountingV2(final List<QuestionAnswersDTO> personList){
        System.out.println(Function.identity());
        List<QuestionAnswersDTO> duplicates = personList.stream()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
            .entrySet().stream()
            .flatMap(n -> Collections.nCopies(n.getValue().intValue(), n.getKey()).stream())
            .collect(toList());

        return null;

    }

    /**
     * Delete the category by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Category : {}", id);
        categoryRepository.deleteById(id);
    }
}
