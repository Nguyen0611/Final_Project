package fpt.toeic.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fpt.toeic.domain.Category;
import fpt.toeic.domain.FileUpload;
import fpt.toeic.domain.QuestionAnswers;
import fpt.toeic.domain.Topic;
import fpt.toeic.service.*;
import fpt.toeic.service.dto.*;
import fpt.toeic.service.mapper.CategoryMapper;
import fpt.toeic.utils.*;
import fpt.toeic.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.ResponseUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing {@link fpt.toeic.domain.Category}.
 */
@RestController
@RequestMapping("/api")
public class CategoryResource {

    private final Logger log = LoggerFactory.getLogger(CategoryResource.class);

    private static final String ENTITY_NAME = "category";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoryService categoryService;
    private final TopicService topicService;
    private final ObjectsService objectsService;
    private final FileUploadService fileUploadService;
    private final QuestionAnswersService questionAnswersService;
    private final CategoryMapper categoryMapper;

    private final CategoryQueryService categoryQueryService;

    public CategoryResource(CategoryService categoryService,
                            TopicService topicService,
                            QuestionAnswersService questionAnswersService,
                            ObjectsService objectsService,
                            CategoryMapper categoryMapper,
                            FileUploadService fileUploadService,
                            CategoryQueryService categoryQueryService) {
        this.categoryService = categoryService;
        this.categoryQueryService = categoryQueryService;
        this.topicService = topicService;
        this.objectsService = objectsService;
        this.fileUploadService = fileUploadService;
        this.categoryMapper = categoryMapper;
        this.questionAnswersService = questionAnswersService;
    }

    /**
     * {@code POST  /categories} : Create a new category.
     *
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categoryDTO, or with status {@code 400 (Bad Request)} if the category has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */

    @PostMapping("/categories")
    public ResponseEntity<Object> createCategory(@Valid @ModelAttribute FormUploadDTO formUploadDTO, BindingResult result) throws URISyntaxException, JsonProcessingException {
        CategoryDTO categoryDTO = new ObjectMapper().readValue(formUploadDTO.getModel().toString(), CategoryDTO.class);
        log.debug("REST request to save Category : {}", categoryDTO);
        if (categoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new category cannot already have an ID", ENTITY_NAME, "idexists");
        }
        try {
            if (result.hasErrors() && null != formUploadDTO.getFile1()) {
                return ResponseEntity.status(400).body(result.getFieldError().getDefaultMessage());
            } else {
                String code = VNCharacterUtils.removeAccent(categoryDTO.getCategoryName());
                Optional<Topic> topic = topicService.findOne(categoryDTO.getTopicId());// Tìm topic có tồn tại hay không
                Category categoryDb = categoryService.findCode(code, topic.get());
                if (categoryDb != null) {
                    throw new IllegalArgumentException("Categories name already exits");
                }
                if (!categoryDTO.getIdPartTopic().equals(11L)) { // Nếu khác là P5 thì cho qua
                    if (null == formUploadDTO.getFile1()) {
                        throw new IllegalArgumentException("There was an error while uploading the file!");
                    }
                }
                categoryDTO.setCode(code);
                categoryDTO.setCreationTime(DateUtil.getDateC());
                Category category = categoryService.save(categoryDTO);
                if (!categoryDTO.getIdPartTopic().equals(11L)) {
                    if (null != formUploadDTO.getFile1()) {
                        FileUtils fileUtils = new FileUtils();
                        String codeFile = VNCharacterUtils.removeAccent(topic.get().getName()) + code;
                        for (MultipartFile multipartFile : formUploadDTO.getFile1()) {//
                            String path = fileUtils.uploadFile(1L, multipartFile, codeFile);// Lấy file
                            FileUploadDTO fileUpload = new FileUploadDTO();
                            fileUpload.setPath(path);
                            if (FileUtils.getTypeFile(path).equals("")) {
                                throw new IllegalArgumentException("There was an error while uploading the file!");
                            } else {
                                if (FileUtils.getTypeFile(path).equals("mp3")) {
                                    fileUpload.setTypeFile("0");
                                } else if (FileUtils.getTypeFile(path.toUpperCase()).equals("PNG")
                                    || FileUtils.getTypeFile(path.toUpperCase()).equals("JPG")
                                    || FileUtils.getTypeFile(path.toUpperCase()).equals("JPEG")) {
                                    fileUpload.setTypeFile("1");
                                } else {
                                    throw new IllegalArgumentException("There was an error while uploading the file!");
                                }
                            }
                            fileUpload.setCategoryId(category.getId());// id bảng fileupload = id bảng category
                            fileUploadService.save(fileUpload);
                        }
                    } else {
                        throw new IllegalArgumentException("There was an error while uploading the file!");
                    }
                }

                Optional<ObjectsDTO> objects = objectsService.findOne(categoryDTO.getIdType());
                if (!objects.isPresent()) {
                    throw new IllegalArgumentException("Co loi trong qua trinh xac dinh type!");
                } else {
                    List<QuestionAnswers> questionAnswersList = new ArrayList<>();

                    if (categoryDTO.getListQue().size() > 0) {
                        for (int i = 0; i < categoryDTO.getListQue().size(); i++) {
                            for (int j = 0; j < categoryDTO.getListQue().get(i).getListAnswers().size(); j++) {
                                QuestionAnswers questionAnswers = new QuestionAnswers();
                                questionAnswers.setName(categoryDTO.getListQue().get(i).getName());
                                questionAnswers.setAnswerToChoose(categoryDTO.getListQue().get(i).getListAnswers().get(j).getValue());
                                questionAnswers.setAnswer("1"); // dap an sai
                                if (categoryDTO.getListQue().get(i).getListAnswers().get(j).getStt().equals(categoryDTO.getListQue().get(i).getAnswer().toString())) {
                                    questionAnswers.setAnswer("0"); // ddap an dung
                                }
                                questionAnswers.setStt(Long.valueOf(i));
                                questionAnswers.setStatus(categoryDTO.getListQue().get(i).getStatus());
                                questionAnswers.setTransscript(categoryDTO.getListQue().get(i).getTransscript());
                                questionAnswersList.add(questionAnswers);
                                questionAnswers.setCategory(category);
                            }
                        }
                        questionAnswersService.saveAll(questionAnswersList);
                    }
                }

                return ResponseEntity.ok().body(Utils.getStatusOk("Success", null));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().body(Utils.getStatusBadRequest(e.getMessage()));
        }
    }


    @PutMapping("/categories")
    public ResponseEntity<Object> updateCategory(@Valid @ModelAttribute FormUploadDTO formUploadDTO, BindingResult result) throws URISyntaxException, JsonProcessingException {
        CategoryDTO categoryDTO = new ObjectMapper().readValue(formUploadDTO.getModel().toString(), CategoryDTO.class);
        log.debug("REST request to save Category : {}", categoryDTO);
        if (result.hasErrors() && null != formUploadDTO.getFile1()) {
            throw new BadRequestAlertException("A new category cannot already have an ID", ENTITY_NAME, "idexists");
        }
        try {
            if (result.hasErrors() && null != formUploadDTO.getFile1()) {
                return ResponseEntity.status(400).body(result.getFieldError().getDefaultMessage());
            } else {
                String code = VNCharacterUtils.removeAccent(categoryDTO.getCategoryName());
                String oldCategoryName = VNCharacterUtils.removeAccent(categoryDTO.getOldCategoryName());
                Optional<Topic> topic = topicService.findOne(categoryDTO.getTopicId());
                Category categoryDb = categoryService.findCode(oldCategoryName, topic.get());
                Optional<CategoryDTO> categoryCheck = categoryService.findOne(categoryDTO.getId());
                if (!categoryCheck.isPresent()) {
                    throw new IllegalArgumentException("Topic name doest not exist!");
                }
                if (categoryDb != null && !categoryCheck.get().getCode().equals(oldCategoryName)) {
                    throw new IllegalArgumentException("Categories name already exist!");
                }
                categoryDTO.setCreationTime(categoryCheck.get().getCreationTime());
                categoryDTO.setCode(oldCategoryName);
                categoryDTO.setUpdateTime(DateUtil.getDateC());
                Category category = categoryService.save(categoryDTO);
                if (!categoryDTO.getIdPartTopic().equals(11L)) {
                    if (null != formUploadDTO.getFile1()) {
                        FileUtils fileUtils = new FileUtils();
                        String codeFile = VNCharacterUtils.removeAccent(topic.get().getName()) + code;
                        try {

                            for (MultipartFile multipartFile : formUploadDTO.getFile1()) {
                                String path = fileUtils.uploadFile(1L, multipartFile, codeFile);
                                FileUploadDTO fileUpload = new FileUploadDTO();
                                fileUpload.setPath(path);
                                if (FileUtils.getTypeFile(path).equals("")) {
                                    throw new IllegalArgumentException("Co loi trong qua trinh upload file!");
                                } else {
                                    if (FileUtils.getTypeFile(path).equals("mp3")) {
                                        fileUpload.setTypeFile("0");
                                        fileUploadService.delete(category, "0");
                                    } else if (FileUtils.getTypeFile(path.toUpperCase()).equals("PNG")
                                        || FileUtils.getTypeFile(path.toUpperCase()).equals("JPG")
                                        || FileUtils.getTypeFile(path.toUpperCase()).equals("JPEG")) {
                                        fileUpload.setTypeFile("1");
                                        fileUploadService.delete(category, "1");
                                    } else {
                                        throw new IllegalArgumentException("Co loi trong qua trinh upload file!");
                                    }
                                }
                                fileUpload.setCategoryId(category.getId());
                                fileUploadService.save(fileUpload);
                            }
                        } catch (Exception e) {
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        }

                    }
                }

                Optional<ObjectsDTO> objects = objectsService.findOne(categoryDTO.getIdType());
                if (!objects.isPresent()) {
                    throw new IllegalArgumentException("Co loi trong qua trinh xac dinh type!");
                } else {
                    List<QuestionAnswers> questionAnswersList = new ArrayList<>();

                    if (categoryDTO.getListQue().size() > 0) {
                        for (int i = 0; i < categoryDTO.getListQue().size(); i++) {
                            for (int j = 0; j < categoryDTO.getListQue().get(i).getListAnswers().size(); j++) {
                                QuestionAnswers questionAnswers = new QuestionAnswers();
                                questionAnswers.setId(categoryDTO.getListQue().get(i).getListAnswers().get(j).getId());
                                questionAnswers.setName(categoryDTO.getListQue().get(i).getName());
                                questionAnswers.setAnswerToChoose(categoryDTO.getListQue().get(i).getListAnswers().get(j).getValue());
                                questionAnswers.setAnswer("1"); // dap an sai
                                if (categoryDTO.getListQue().get(i).getListAnswers().get(j).getStt().equals(categoryDTO.getListQue().get(i).getAnswer().toString())) {
                                    questionAnswers.setAnswer("0"); // ddap an dung
                                }
                                questionAnswers.setStt(Long.valueOf(i));
                                questionAnswers.setStatus(categoryDTO.getListQue().get(i).getStatus());
                                questionAnswers.setTransscript(categoryDTO.getListQue().get(i).getTransscript());
                                questionAnswersList.add(questionAnswers);
                                questionAnswers.setCategory(category);
                            }
                        }
                        questionAnswersService.deleteAll(categoryDTO.getId());
                        questionAnswersService.saveAll(questionAnswersList);
                    }
                }

                return ResponseEntity.ok().body(Utils.getStatusOk("Thành công", null));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().body(Utils.getStatusBadRequest(e.getMessage()));
        }
    }

    /**
     * {@code GET  /categories} : get all the categories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categories in body.
     */
    @GetMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllCategories(@RequestParam Map<String, String> paramSearch) {
        try {
            JSONObject reqParamObj = JsonUtils.transferJsonKey(new JSONObject(paramSearch), true);
            int page1 = reqParamObj.getInt("page");
            int page_size = reqParamObj.getInt("page_size");
            Long idType;
            Long idPartTopic;
            String name;
            try {
                idType = reqParamObj.getLong("idType");
            } catch (Exception e) {
                idType = null;
            }
            try {
                idPartTopic = reqParamObj.getLong("idPartTopic");
            } catch (Exception e) {
                idPartTopic = null;
            }
            try {
                name = reqParamObj.getString("name");
                if ("".equals(name.trim())) {
                    name = null;
                }
            } catch (Exception e) {
                name = null;
            }
            if (page1 != 0) {
                page1 = page1 * page_size;
            }
            List<CategoryDTO> page = categoryService.search(name, idType, idPartTopic, page1, page_size);
            long total = categoryService.total(name, idType, idPartTopic);
            JSONArray arrayResult = new JSONArray();
            for (CategoryDTO item : page) {
                JSONObject lstRoles = Utils.convertEntityToJSONObject(item);
                arrayResult.put(lstRoles);
            }
            JSONObject result = new JSONObject();
            result.put("count", total);
            result.put("list", arrayResult);
            return ResponseEntity.ok().body(Utils.getStatusOk("Thành công", result));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                Utils.getStatusBadRequest("Có lỗi xảy ra trong quá trình tìm kiếm " + e.getMessage()));
        }
    }


    // lay chi tiet 1 chu de ben admin
    @GetMapping(value = "/detailCategories", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getDetailCategories(@RequestParam Map<String, String> paramSearch) {
        try {
            JSONObject reqParamObj = JsonUtils.transferJsonKey(new JSONObject(paramSearch), true);
            Long idType;
            Long idPartTopic;
            Long id;
            try {
                idType = reqParamObj.getLong("idType");
            } catch (Exception e) {
                idType = null;
            }
            try {
                idPartTopic = reqParamObj.getLong("idPartTopic");
            } catch (Exception e) {
                idPartTopic = null;
            }
            try {
                id = reqParamObj.getLong("id");
            } catch (Exception e) {
                id = null;
            }
            List<ObjectsDTO> listTopic = objectsService.findAllByParentIdAndStatus(0L);
            List<ObjectsDTO> listPart = objectsService.findAllByParentIdAndStatus(idType);
            List<TopicDTO> lisTopic = topicService.findTopic(idType, idPartTopic);
//            Optional<CategoryDTO> category = categoryService.findOne(id);
            Optional<Category> category = categoryService.findOneEntity(id);
            Optional<CategoryDTO> categoryDTO = category.map(categoryMapper::toDto);
            categoryDTO.get().setFileUploadDTOS(category.get().getDsFileUploads());
            List<FileUploadDTO> fileUploads = new ArrayList<>();
            for (FileUpload file : category.get().getDsFileUploads()) {
                FileUploadDTO fileUploadDTO = new FileUploadDTO();
                fileUploadDTO.setPath(file.getPath());
                fileUploadDTO.setTypeFile(file.getTypeFile());
                fileUploads.add(fileUploadDTO);
            }
            List<QuestionAnswersDTO> list = questionAnswersService.findCategory(categoryDTO.get());
            List<QuestionAnswersDTO> questionAnswersDTOArrayList = new ArrayList<>();
            List<QuestionAnswersDTO> questionAnswersDTOArrayList1 = new ArrayList<>();

//            int check = 0;
            for (int i = 0; i < list.size(); i++) {
                if (list.size() > 1) {
                    int check = 0;
                    if (i == list.size() - 1) {
                        if (list.get(i).getStt() == list.get(i - 1).getStt()) {
                            questionAnswersDTOArrayList.add(list.get(i));
                            check = 1;
                        } else {
                            questionAnswersDTOArrayList.add(list.get(i));
                        }
                    } else {
                        if (list.get(i).getStt() == list.get(i + 1).getStt()) {
                            questionAnswersDTOArrayList.add(list.get(i));
                        } else {
                            questionAnswersDTOArrayList.add(list.get(i));
                            check = 1;
                        }
                    }
                    if (check == 1) {
                        QuestionAnswersDTO questionAnswersDTO = new QuestionAnswersDTO();
                        List<ListAnswersDTO> listAnswersDTOS = new ArrayList<>();
                        for (int j = 0; j < questionAnswersDTOArrayList.size(); j++) {
                            QuestionAnswersDTO questionAnswersDTO1 = questionAnswersDTOArrayList.get(j);
                            ListAnswersDTO listAnswersDTO = new ListAnswersDTO();
                            listAnswersDTO.setValue(questionAnswersDTO1.getAnswerToChoose());
                            try {
                                listAnswersDTO.setStt((String.valueOf(j + 1)));
                            } catch (Exception e) {
                                listAnswersDTO.setStt("1");
                            }
                            if (questionAnswersDTO1.getAnswer().equals("0")) {
                                questionAnswersDTO.setAnswer(String.valueOf(j + 1));
                            }
                            listAnswersDTO.setId(questionAnswersDTO1.getId());
                            listAnswersDTOS.add(listAnswersDTO);
                        }
                        questionAnswersDTO.setName(questionAnswersDTOArrayList.get(0).getName());
                        questionAnswersDTO.setCategoryId(questionAnswersDTOArrayList.get(0).getCategoryId());
                        questionAnswersDTO.setTransscript(questionAnswersDTOArrayList.get(0).getTransscript());
                        questionAnswersDTO.setStatus(questionAnswersDTOArrayList.get(0).getStatus());
                        questionAnswersDTO.setListAnswers(listAnswersDTOS);
                        questionAnswersDTO.setStt(questionAnswersDTOArrayList.get(0).getStt() + 1);
                        questionAnswersDTOArrayList1.add(questionAnswersDTO);
                        questionAnswersDTOArrayList = new ArrayList<>();
                    }
                } else if (list.size() == 1) {
                    QuestionAnswersDTO questionAnswersDTO = new QuestionAnswersDTO();
                    List<ListAnswersDTO> listAnswersDTOS = new ArrayList<>();
                    for (int j = 0; j < list.size(); j++) {
                        QuestionAnswersDTO questionAnswersDTO1 = list.get(0);
                        ListAnswersDTO listAnswersDTO = new ListAnswersDTO();
                        listAnswersDTO.setValue(questionAnswersDTO1.getAnswerToChoose());
                        try {
                            listAnswersDTO.setStt((String.valueOf(1)));
                        } catch (Exception e) {
                            listAnswersDTO.setStt("1");
                        }
                        if (questionAnswersDTO1.getAnswer().equals("0")) {
                            questionAnswersDTO.setAnswer(String.valueOf(1));
                        }
                        listAnswersDTO.setId(questionAnswersDTO1.getId());
                        listAnswersDTOS.add(listAnswersDTO);
                    }
                    questionAnswersDTO.setName(list.get(0).getName());
                    questionAnswersDTO.setCategoryId(list.get(0).getCategoryId());
                    questionAnswersDTO.setTransscript(list.get(0).getTransscript());
                    questionAnswersDTO.setStatus(list.get(0).getStatus());
                    questionAnswersDTO.setListAnswers(listAnswersDTOS);
                    questionAnswersDTO.setStt(list.get(0).getStt() + 1);
                    questionAnswersDTOArrayList1.add(questionAnswersDTO);
                }
            }
            QuestionAnswersDTO questionAnswers = new QuestionAnswersDTO();
            questionAnswers.setTransscript(list.get(0).getTransscript());
            questionAnswers.setName(list.get(0).getName());
            questionAnswers.setStatus(list.get(0).getStatus());
            JSONObject arrayResult = new JSONObject();

            arrayResult.put("listTopic", listTopic);
            arrayResult.put("listPart", listPart);
            arrayResult.put("lisTopic", lisTopic);
            arrayResult.put("lisFile", fileUploads);
            arrayResult.put("listQue", questionAnswersDTOArrayList1);

            JSONObject result = new JSONObject();
            result.put("list", arrayResult);
            return ResponseEntity.ok().body(Utils.getStatusOk("Thành công", result));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                Utils.getStatusBadRequest("Có lỗi xảy ra trong quá trình tìm kiếm " + e.getMessage()));
        }
    }


    /**
     * {@code GET  /categories/count} : count all the categories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/categories/count")
    public ResponseEntity<Long> countCategories(CategoryCriteria criteria) {
        log.debug("REST request to count Categories by criteria: {}", criteria);
        return ResponseEntity.ok().body(categoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /categories/:id} : get the "id" category.
     *
     * @param id the id of the categoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable Long id) {
        log.debug("REST request to get Category : {}", id);
        Optional<CategoryDTO> categoryDTO = categoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categoryDTO);
    }

    /**
     * {@code DELETE  /categories/:id} : delete the "id" category.
     *
     * @param id the id of the categoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping(value = "/categories/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteCategory(@PathVariable Long id) {
        try {
            log.debug("REST request to delete Category : {}", id);
            Optional<Category> categoryDTO = categoryService.findOneEntity(id);
            if (!categoryDTO.isPresent()) {
                throw new IllegalArgumentException("Category doest not exits");
            }
            fileUploadService.deleteAll(categoryDTO.get());
            // xoa ds cac cau hoi theo chu de
            questionAnswersService.deleteAll(id);

            // xoa ds file theo chu de

            // xoa chu de
            categoryService.delete(id);
            return ResponseEntity.ok().body(Utils.getStatusOk("Thành công", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                Utils.getStatusBadRequest("Có lỗi xảy ra trong xoá: " + e.getMessage()));
        }
    }
}
