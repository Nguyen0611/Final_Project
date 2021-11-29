package fpt.toeic.service;

import fpt.toeic.domain.Category;
import fpt.toeic.domain.FileUpload;
import fpt.toeic.domain.QuestionAnswers;
import fpt.toeic.repository.CategoryRepository;
import fpt.toeic.repository.FileUploadRepository;
import fpt.toeic.repository.QuestionAnswersRepository;
import fpt.toeic.service.dto.CategoryDTO;
import fpt.toeic.service.dto.IsColorDTO;
import fpt.toeic.service.dto.QuestionAnswersDTO;
import fpt.toeic.service.mapper.CategoryMapper;
import fpt.toeic.service.mapper.QuestionAnswersMapper;
import liquibase.pro.packaged.F;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Service Implementation for managing {@link QuestionAnswers}.
 */
@Service
@Transactional
public class QuestionAnswersService {

    private final Logger log = LoggerFactory.getLogger(QuestionAnswersService.class);

    private final QuestionAnswersRepository questionAnswersRepository;

    private final QuestionAnswersMapper questionAnswersMapper;

    private final CategoryMapper categoryMapper;

    private final CategoryService categoryService;
    private final FileUploadService fileUploadService;

    private final CategoryRepository categoryRepository;

    private final FileUploadRepository fileUploadRepository;

    private static final String PATH_IMG = "https://via.placeholder.com/400";

    public QuestionAnswersService(QuestionAnswersRepository questionAnswersRepository,
                                  CategoryMapper categoryMapper,
                                  FileUploadService fileUploadService,
                                  QuestionAnswersMapper questionAnswersMapper,
                                  CategoryService categoryService,
                                  CategoryRepository categoryRepository,
                                  FileUploadRepository fileUploadRepository) {
        this.questionAnswersRepository = questionAnswersRepository;
        this.questionAnswersMapper = questionAnswersMapper;
        this.categoryMapper = categoryMapper;
        this.categoryService = categoryService;
        this.fileUploadService = fileUploadService;
        this.categoryRepository = categoryRepository;
        this.fileUploadRepository = fileUploadRepository;
    }

    /**
     * Save a questionAnswers.
     *
     * @param questionAnswersDTO the entity to save.
     * @return the persisted entity.
     */
    public QuestionAnswersDTO save(QuestionAnswersDTO questionAnswersDTO) {
        log.debug("Request to save QuestionAnswers : {}", questionAnswersDTO);
        QuestionAnswers questionAnswers = questionAnswersMapper.toEntity(questionAnswersDTO);
        questionAnswers = questionAnswersRepository.save(questionAnswers);
        return questionAnswersMapper.toDto(questionAnswers);
    }


    public List<QuestionAnswers> saveAll(List<QuestionAnswers> products) {
        return questionAnswersRepository.saveAll(products);
    }

    @Transactional(readOnly = true)
    public List<QuestionAnswersDTO> findCategory(CategoryDTO category) {
        List<QuestionAnswers> list = questionAnswersRepository.findAllByCategory(categoryMapper.toEntity(category));
        List<QuestionAnswersDTO> topicDTOS = new ArrayList<>();
        for (QuestionAnswers objects : list) {
            topicDTOS.add(questionAnswersMapper.toDto(objects));
        }
        return topicDTOS;
    }

    /**
     * Get all the questionAnswers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<QuestionAnswersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all QuestionAnswers");
        return questionAnswersRepository.findAll(pageable)
            .map(questionAnswersMapper::toDto);
    }


    /**
     * Get one questionAnswers by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<QuestionAnswersDTO> findOne(Long id) {
        log.debug("Request to get QuestionAnswers : {}", id);
        return questionAnswersRepository.findById(id)
            .map(questionAnswersMapper::toDto);
    }

    /**
     * Delete the questionAnswers by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete QuestionAnswers : {}", id);
        questionAnswersRepository.deleteById(id);
    }

    public void deleteAll(Long id) {
        log.debug("Request to delete QuestionAnswers : {}", id);
        questionAnswersRepository.deleteByCategory(id);
    }

    @Transactional(readOnly = true)
    public List<List<QuestionAnswersDTO>> findAllQuestionsByTopic(Long topicId) {
//        Category category = categoryService.findByCategoryId(id);
        List<Category> categories = categoryRepository.findCategoryByTopicId(topicId);
        List<List<QuestionAnswersDTO>> questionAnswersResult = new ArrayList<>();
        for (int i= 0; i < categories.size(); i++){
            List<QuestionAnswers> questionAnswers = questionAnswersRepository.findAllByCategory(categories.get(i));
            for (int j =0; j<(questionAnswers.size()) ; j=j+4){
                List<QuestionAnswersDTO> groupQuestionDTO = new ArrayList<>();
                groupQuestionDTO.add(questionAnswersMapper.toDto(questionAnswers.get(j)));
                groupQuestionDTO.add(questionAnswersMapper.toDto(questionAnswers.get(j+1)));
                groupQuestionDTO.add(questionAnswersMapper.toDto(questionAnswers.get(j+2)));
                groupQuestionDTO.add(questionAnswersMapper.toDto(questionAnswers.get(j+3)));
                questionAnswersResult.add(groupQuestionDTO);
            }
        }
        log.debug("Request categoryRepository : {}", categories);
        return questionAnswersResult;
    }

    @Transactional(readOnly = true)
    public List<QuestionAnswersDTO> findAllQuestionsClient(Long categoryId) {
        List<QuestionAnswersDTO> questionAnswersDTOArrayList = new ArrayList<>();
        List<QuestionAnswers> questionAnswers = questionAnswersRepository.findAllByCategoryClient(categoryId);
        for (QuestionAnswers objects : questionAnswers) {
            objects.setTransscript(null);
            objects.setAnswer(null);
            QuestionAnswersDTO questionAnswersDTO = new QuestionAnswersDTO();
            questionAnswersDTO = questionAnswersMapper.toDto(objects);
            List<String> stringList = Arrays.asList(questionAnswersDTO.getAnswerToChoose().split("#--#"));
            log.info(String.valueOf(stringList));
            stringList.sort(Comparator.naturalOrder());
            List<IsColorDTO> isColorDTOS = new ArrayList<>();
            for (String strings : stringList) {
                IsColorDTO isColorDTOs = new IsColorDTO();
                isColorDTOs.setColor("primary");
                isColorDTOs.setValue(strings);
                isColorDTOs.setCategoryId(objects.getCategory().getId());
                isColorDTOS.add(isColorDTOs);
            }
            questionAnswersDTO.setFileUploadDTOs(fileUploadService.findAll(objects.getCategory()));
            questionAnswersDTO.setListStringAnswers(isColorDTOS);
            questionAnswersDTOArrayList.add(questionAnswersDTO);
        }
        return questionAnswersDTOArrayList;
    }

    @Transactional(readOnly = true)
    public List<IsColorDTO> findByStatusAndCategory(Long categoryId, Category category, String value) {
        List<QuestionAnswers> questionAnswers = questionAnswersRepository.findByStatusAndCategoryOrderByAnswerToChoose(categoryId, category);
        List<IsColorDTO> isColorDTOS = new ArrayList<>();
        for (QuestionAnswers questionAnswers1 : questionAnswers) {
            IsColorDTO isColorDTOs = new IsColorDTO();
            if (questionAnswers1.getAnswerToChoose().equals(value)) {
                if (questionAnswers1.getAnswer().equals("1")) {
                    // DAP ANS SAI
                    isColorDTOs.setColor("danger");
                } else if (questionAnswers1.getAnswer().equals("0")) {
                    isColorDTOs.setColor("success");
                }
            } else if (questionAnswers1.getAnswer().equals("0")) {
                isColorDTOs.setColor("success");

            } else {
                isColorDTOs.setColor("primary");
            }
            isColorDTOs.setTransscript(questionAnswers1.getTransscript());
            isColorDTOs.setCategoryName(category.getCategoryName());
            isColorDTOs.setValue(questionAnswers1.getAnswerToChoose());
            isColorDTOS.add(isColorDTOs);
        }
        return isColorDTOS;
    }

    @Transactional(readOnly = true)
    public Map<String, Map<String, List<QuestionAnswersDTO>>> findAllQuestionsByTopicPart6(Long topicId) {

        List<Category> categoriesCopy = new ArrayList<>(categoryRepository.findCategoryByTopicId(topicId));
        List<Category> randomCategories = new ArrayList<>();
        if(categoriesCopy.size() >= 2){
            Collections.shuffle(categoriesCopy);
            randomCategories = categoriesCopy.subList(0, 2);
        } else {
            randomCategories = categoriesCopy;
        }

        Map<String, Map<String, List<QuestionAnswersDTO>>> questionGroupsLV0 = new HashMap<>();
        for (int i= 0; i < randomCategories.size(); i++){

            Map<String, List<QuestionAnswersDTO>> questionsGroupLV1 = new HashMap<>();

            Category categoryRandom = randomCategories.get(i);
            List<QuestionAnswers> questionAnswers = questionAnswersRepository.findAllByCategory(categoryRandom);
            List<FileUpload> fileUpload = fileUploadRepository.findAllByCategory(categoryRandom);

            for (int j =0; j<questionAnswers.size() ; j=j+4){
                List<QuestionAnswersDTO> groupQuestionDTO = new ArrayList<>();
                groupQuestionDTO.add(questionAnswersMapper.toDto(questionAnswers.get(j)));
                groupQuestionDTO.add(questionAnswersMapper.toDto(questionAnswers.get(j+1)));
                groupQuestionDTO.add(questionAnswersMapper.toDto(questionAnswers.get(j+2)));
                groupQuestionDTO.add(questionAnswersMapper.toDto(questionAnswers.get(j+3)));

                if(fileUpload.size()>=1) {
                    groupQuestionDTO.get(0).setPathCategory(fileUpload.get(0).getPath().replace("\\", "/"));
                    groupQuestionDTO.get(1).setPathCategory(fileUpload.get(0).getPath().replace("\\", "/"));
                    groupQuestionDTO.get(2).setPathCategory(fileUpload.get(0).getPath().replace("\\", "/"));
                    groupQuestionDTO.get(3).setPathCategory(fileUpload.get(0).getPath().replace("\\", "/"));
                } else {
                    groupQuestionDTO.get(0).setPathCategory(PATH_IMG);
                    groupQuestionDTO.get(1).setPathCategory(PATH_IMG);
                    groupQuestionDTO.get(2).setPathCategory(PATH_IMG);
                    groupQuestionDTO.get(3).setPathCategory(PATH_IMG);
                }

                questionsGroupLV1.put( "question" + String.valueOf(j/4+1) , groupQuestionDTO);
            }
            questionGroupsLV0.put("listQuestion" + String.valueOf(i+1), questionsGroupLV1);
        }
        log.debug("Request categoryRepository : {}", questionGroupsLV0);
        return questionGroupsLV0;
    }
}
