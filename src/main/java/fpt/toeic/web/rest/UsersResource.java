package fpt.toeic.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fpt.toeic.config.Constants;
import fpt.toeic.config.CustomUserDetails;
import fpt.toeic.domain.Users;
import fpt.toeic.domain.Users_;
import fpt.toeic.service.MailService;
import fpt.toeic.service.UsersQueryService;
import fpt.toeic.service.UsersService;
import fpt.toeic.service.dto.*;
import fpt.toeic.service.mapper.UsersMapper;
import fpt.toeic.utils.FileUtils;
import fpt.toeic.utils.JsonUtils;
import fpt.toeic.utils.Translator;
import fpt.toeic.utils.Utils;
import fpt.toeic.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.web.util.PaginationUtil;
import org.apache.commons.lang.RandomStringUtils;
import org.hibernate.service.spi.ServiceException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing {@link fpt.toeic.domain.Users}.
 */
@RestController
@RequestMapping("/api")
public class UsersResource {

    private final Logger log = LoggerFactory.getLogger(UsersResource.class);

    private static final String ENTITY_NAME = "users";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    MailService mailService;

    private final UsersService usersService;

    private final UsersQueryService usersQueryService;

    private final UsersMapper usersMapper;


    public UsersResource(UsersService usersService, UsersQueryService usersQueryService,
                         UsersMapper usersMapper) {
        this.usersService = usersService;
        this.usersMapper = usersMapper;
        this.usersQueryService = usersQueryService;
    }

    /**
     * {@code POST  /users} : Create a new users.
     *
     * @param formUploadDTO the usersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new usersDTO, or with status {@code 400 (Bad Request)} if the users has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     *                            add users Admin
     */
    @PostMapping("/users")
    public ResponseEntity<Object> createUsers(@Valid @ModelAttribute FormUploadDTO formUploadDTO, BindingResult result) throws JsonProcessingException { // Tr??? ra nh??m ?????i t?????ng
        UsersDTO usersDTO = new ObjectMapper().readValue(formUploadDTO.getModel().toString(), UsersDTO.class);// Nh???ng th??ng tin user nh???p vao
        log.debug("REST request to save Users : {}", formUploadDTO);
        if (usersDTO.getId() != null) {
            throw new BadRequestAlertException("A new users cannot already have an ID", ENTITY_NAME, "idexists");
        }
        try {
            if (result.hasErrors() && null != formUploadDTO.getFile()) {
                return ResponseEntity.status(400).body(Utils.getStatusBadRequest(result.getFieldError().getDefaultMessage()));
            } else {

                    CustomUserDetails principal = null; // T???o user = null
                    try {
                        principal = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();// L???y user login ????ng nh???p ????? t???o m???i
                    } catch (Exception e) {
                        log.debug(e.toString());
                    }
                // n???u m?? gi???i m?? token c?? v???n ????? kh??ng l???y ra ???????c t??i kho???n t???o token th?? b???t bu???c quay v??? trang login
                if (principal == null) { // check quy???n c???a user ????ng nh???p
                    return ResponseEntity.status(401).body(Utils.getStatusBadRequest("Vui l??ng ????ng nh???p l???i"));
                }
                if (null != formUploadDTO.getFile()) {// Kh??c null set file v??o path
                    FileUtils fileUtils = new FileUtils();
                    String path = fileUtils.uploadFile(0L, formUploadDTO.getFile(), usersDTO.getMail());
                    usersDTO.setPathUrl(path);
                }
                String pass = RandomStringUtils.randomAlphanumeric(6);
                usersDTO.setPass(new BCryptPasswordEncoder().encode(pass)); // M?? ho?? m???t kh???u
                usersService.save(usersDTO, principal); // L??u v??o DB
                try {
                    // khi t???o user th??nh c??ng th?? g???i b???t ?????u g???i mail
                    String conten = "If you agree to participate, please click on this link  http://localhost:4201/toeic-web/auths/login to add account information. Account " + usersDTO.getMail() + " <br> Password " + pass;
                    if (!mailService.sendEmail(usersDTO.getMail(), Translator.toLocale(Constants.EMAIL_SUBJECT_LOGIN), conten, Constants.IS_MULTI_PART, Constants.IS_HTML)) {
                        throw new ServiceException(Translator.toLocale(Constants.EMAIL_FALSE));
                    }
                } catch (Exception e) {
                    throw new ServiceException(Translator.toLocale(Constants.EMAIL_FALSE));
                }
                return ResponseEntity.ok().body(Utils.getStatusOk("Th??nh c??ng", null));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().body(Utils.getStatusBadRequest(e.getMessage()));
        }
    }


    @PostMapping(value = "/registration", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> registration(@Valid @RequestBody RegistrationDTO registrationDTO, BindingResult result) throws JsonProcessingException {
        log.debug("REST request to save Users : {}", registrationDTO);
        try {
            if (result.hasErrors()) {
                return ResponseEntity.status(400).body(Utils.getStatusBadRequest(result.getFieldError().getDefaultMessage()));
            } else {
                UsersDTO usersDTO = new UsersDTO();
                usersDTO.setName(registrationDTO.getMail());
                usersDTO.setMail(registrationDTO.getMail());
                String pass = RandomStringUtils.randomAlphanumeric(6);
                usersDTO.setPass(new BCryptPasswordEncoder().encode(pass));
                usersDTO.setStatus(1L);
                usersService.saveClient(usersDTO);
                try {
                    // khi t???o user th??nh c??ng th?? g???i b???t ?????u g???i mail
                    String conten = "If you agree to participate, please click this link http://localhost:4201/toeic-web/auths/login to add account information. AccountAcc  " + usersDTO.getMail() + " <br> Password  " + pass;
                    if (!mailService.sendEmail(usersDTO.getMail(), Translator.toLocale(Constants.EMAIL_SUBJECT_LOGIN), conten, Constants.IS_MULTI_PART, Constants.IS_HTML)) {
                        throw new ServiceException(Translator.toLocale(Constants.EMAIL_FALSE));
                    }
                } catch (Exception e) {
                    throw new ServiceException(Translator.toLocale(Constants.EMAIL_FALSE));
                }
                return ResponseEntity.ok().body(Utils.getStatusOk("Success", null));
            }


        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().body(Utils.getStatusBadRequest(e.getMessage()));
        }
    }

    /**
     * {@code PUT  /users} : Updates an existing users.
     *
     * @param formUploadDTO the usersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated usersDTO,
     * or with status {@code 400 (Bad Request)} if the usersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the usersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     * cap nhat users
     */
    @PutMapping(value = "/users", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> updateUsersImg(@Valid @ModelAttribute FormUploadDTO formUploadDTO, BindingResult result) throws URISyntaxException, JsonProcessingException {
        UsersDTO usersDTO = new ObjectMapper().readValue(formUploadDTO.getModel().toString(), UsersDTO.class);
        log.debug("REST request to update Users : {}", usersDTO);
        if (usersDTO.getId() == null ) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        try {
            if (result.hasErrors() && null != formUploadDTO.getFile()) {
                return ResponseEntity.status(400).body(result.getFieldError());
            } else {
                String path = null;
                if (null != formUploadDTO.getFile()) {
                    FileUtils fileUtils = new FileUtils();
                    path = fileUtils.uploadFile(0L, formUploadDTO.getFile(), usersDTO.getMail());
                }
                UsersDTO usersDTO1 = usersService.update(usersDTO, path);
                usersDTO1.setPass(null);
                JSONObject usersJson = new JSONObject();
                usersJson.put("list", Utils.convertEntityToJSONObject(usersDTO1));
                return ResponseEntity.ok().body(Utils.getStatusOk("Success", usersJson));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().body(Utils.getStatusBadRequest(e.getMessage()));
        }
    }


    /**
     * {@code POST  /users} : Create a new users.
     *
     * @param usersDTO the usersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new usersDTO, or with status {@code 400 (Bad Request)} if the users has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     *                            add users Client - Students
     */
    @PostMapping(value = "/client/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createClientUsers(@Valid @RequestBody UsersClientDTO usersDTO, BindingResult result) {
        log.debug("REST request to save Users : {}", usersDTO);
        if (usersDTO.getId() != null) {
            throw new BadRequestAlertException("A new users cannot already have an ID", ENTITY_NAME, "idexists");
        }
        try {
            if (result.hasErrors()) {
                return ResponseEntity.status(400).body(result.getFieldError());
            } else {
                // th1:Tai khoan chua ton tai dua vao so dt - tao tk moi
                if (usersService.findByPhone(usersDTO.getPhone()).size() == 0) {
                    usersService.saveClient(usersDTO);
                } else {
                    // th2: tai khoang da ton tai - bo qua
                }
                return ResponseEntity.ok().body(Utils.getStatusOk("", null));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().body(Utils.getStatusBadRequest(e.getMessage()));
        }
    }


    /**
     * {@code GET  /users} : get all the users.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of users in body.
     */

    // tim hieu them cho lay danh sach user
    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllUsers(@RequestParam Map<String, String> paramSearch) throws InstantiationException, IllegalAccessException {
        try {
            JSONObject reqParamObj = JsonUtils.transferJsonKey(new JSONObject(paramSearch), true);
            UsersCriteria criteria = Utils.mappingCriteria(UsersCriteria.class, Utils.updateJsontoCamelCase(reqParamObj, true)); // Truy???n d??? li???u ng?????i d??ng nh???p v??o
            log.debug("REST request to get Users by criteria: {}", criteria);
            Pageable pageable = Utils.getPage(reqParamObj, Users_.ID);// Thuc hi???n ph??n trang
            Page<Users> page = usersQueryService.findByCriteria(criteria, pageable);// Th???c hi???n key t??m ki???m
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);// Set v??o header
            JSONArray arrayResult = new JSONArray(); // T???o 1 ?????i t?????ng
            for (Users item : page) {
                item.setPass(null);
                UsersDTO usersDTO = usersMapper.toDto(item); // map v??o
                usersDTO.setRolesName(item.getRoles().getName());
                JSONObject usersJson = Utils.convertEntityToJSONObject(usersDTO); // Tr??? v??? ki???u d??? li???u Json
                arrayResult.put(usersJson); // Ch??n user jsson v??o m???ng khai b??o b??n tr??n
            }
            JSONObject result = new JSONObject();
            result.put("count", usersQueryService.countByCriteria(criteria)); // ?????m s??? l????hg b???n ghi
            result.put("list", arrayResult); // Map ds v??o t??n list
            return ResponseEntity.ok().headers(headers).body(Utils.getStatusOk("Success", result)); // Tr??? v??? th??ng b??o
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                Utils.getStatusBadRequest("C?? l???i x???y ra trong qu?? tr??nh t??m ki???m " + e.getMessage()));
        }

    }

    /**
     * {@code GET  /users/count} : count all the users.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/users/count")
    public ResponseEntity<Long> countUsers(UsersCriteria criteria) {
        log.debug("REST request to count Users by criteria: {}", criteria);
        return ResponseEntity.ok().body(usersQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /users/:id} : get the "id" users.
     *
     * @param id the id of the usersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the usersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getUsers(@PathVariable Long id) {
        log.debug("REST request to get Users : {}", id);
        UsersCriteria criteria = new UsersCriteria();

        LongFilter maUser = new LongFilter();
        maUser.setEquals(id);
        criteria.setId(maUser);

        Optional<Users> users = usersQueryService.findOneByCriteria(criteria);
        if (users.orElse(null) == null) {
            return ResponseEntity.badRequest().body(
                Utils.getStatusBadRequest("Not found"));
        }
        JSONObject result = new JSONObject();
        Users users1 = users.get();
        UsersDTO usersDTO1 = usersMapper.toDto(users1);
        usersDTO1.setPass(null);
        JSONObject usersJson = new JSONObject();
        usersJson.put("list", Utils.convertEntityToJSONObject(usersDTO1));
        return ResponseEntity.ok().body(Utils.getStatusOk("", usersJson));
    }

    /**
     * {@code DELETE  /users/:id} : delete the "id" users.
     *
     * @param id the id of the usersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteUsers(@PathVariable Long id) {
        try {
            log.debug("REST request to delete Users : {}", id);
            Optional<UsersDTO> usersDTO = usersService.findOne(id);
            if (!usersDTO.isPresent()) {
                throw new IllegalArgumentException("User does not exist");
            }
//            userRoleService.deleteAllByUsersId(id);
            usersService.delete(id);
            return ResponseEntity.ok().body(Utils.getStatusOk("Success", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                Utils.getStatusBadRequest("C?? l???i x???y ra trong xo??: " + e.getMessage()));
        }
    }
}
