package fpt.toeic.service;

import fpt.toeic.config.Constants;
import fpt.toeic.config.CustomUserDetails;
import fpt.toeic.domain.Users;
import fpt.toeic.service.dto.ChangePassDTO;
import fpt.toeic.service.dto.LoginDTO;
import fpt.toeic.service.dto.UsersDTO;
import fpt.toeic.service.mapper.UsersMapper;
import fpt.toeic.utils.DateUtil;
import fpt.toeic.utils.Translator;
import fpt.toeic.utils.Utils;
import fpt.toeic.utils.ValidateUtils;
import io.jsonwebtoken.lang.Assert;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class UserJWTService {

    private static final Logger logger = LoggerFactory.getLogger(UserJWTService.class);

    @Autowired
    fpt.toeic.repository.UsersRepository usersRepository;

    @Autowired
    AuthenticationProvider authenticationProvider;

    @Autowired
    CaptchaService captchaService;

    private final UsersMapper usersMapper;

    public UserJWTService(UsersMapper usersMapper) {
        this.usersMapper = usersMapper;
    }

    public LoginDTO login(UsersDTO obj, boolean checkCaptcha) {

        // lay ra thong user do dung k
        Users rs = usersRepository.searchUsersId(obj.getName(), null, null);
        UsersDTO usersDTO = usersMapper.toDto(rs);//map user sang DTO để xử lý ,
        // == null
        if (usersDTO != null) {
            // neu k tim duoc user voi username la obj.getNAme() thi k vao day
            // so sanh ng dung gui vao vs pass trong he thong
            if (ValidateUtils.changePassword(usersDTO.getPass(), obj.getPass())) {
                // khi user name va pass trung he thong
                // tu tim hieu
                Authentication authentication = authenticationProvider
                    .authenticate(new UsernamePasswordAuthenticationToken(obj, obj.getPass()));
                Assert.notNull(authentication, Constants.LOGIN_FALSE); // lưu tất cả những gì mà user có như quyền , tên , pass .....

                LoginDTO login = new LoginDTO();
                CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();// lấy thông tin trong
                String jwt = customUserDetails.getJwt();// lấy ra token từ usernamepassword
                HttpHeaders httpHeaders = new HttpHeaders();//tạo header
                httpHeaders.add(Constants.AUTHORIZATION, Constants.BEARER + jwt);// set token
                login.setHttpHeaders(httpHeaders);// Khi nó trả về response API gọi >> LoginDTO
                login.setListObjects(customUserDetails.getList()); //  user đang đăng nhập như tên, quyền
                login.setCustomUserDetails(customUserDetails.getUser()); //trả về thông tin user đăng nhập
                return login;
            }
            logger.error(Constants.LOGIN_FALSE);
            throw new ServiceException(Translator.toLocale(Constants.LOGIN_FALSE));

        } else {
            logger.error(Constants.LOGIN_FALSE);
            throw new ServiceException(Translator.toLocale(Constants.LOGIN_FALSE));

        }

    }

    public String resetPassword(ChangePassDTO changePassDTO) {
        UsersDTO userDTO = null;
        if (StringUtils.isNotEmpty(changePassDTO.getKey())) {
            try {
                Users rs = usersRepository.searchUsersId(null, null, changePassDTO.getKey());
                userDTO = usersMapper.toDto(rs);
                Date date = Date.from(userDTO.getResetDate().toInstant());
                System.out.println(date);
                if (DateUtil.compareDate(DateUtil.addMinutesToDate(Date.from(userDTO.getResetDate().toInstant()), 15), new Date()) == 1) {
                    logger.error(Constants.TOKEN_ERROR);
                    throw new ServiceException(Translator.toLocale(Constants.TOKEN_ERROR));
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new ServiceException(Translator.toLocale(Constants.TOKEN_ERROR));
            }
        }
        if (StringUtils.isNotEmpty(changePassDTO.getUserName())) {
            CustomUserDetails principal = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            System.out.println(principal);
            userDTO = principal.getUser();
        }
        if (userDTO != null) {
            Utils.checkRestPass(changePassDTO);
            if (StringUtils.isNotEmpty(changePassDTO.getUserName())) {
                if (!ValidateUtils.changePassword(userDTO.getPass(), changePassDTO.getOldPass())) {
                    logger.error(Constants.LOGIN_PASS_CHANGE_FALSE);
                    throw new ServiceException(Translator.toLocale(Constants.LOGIN_PASS_CHANGE_FALSE));
                }
                if (ValidateUtils.checkResetPass(changePassDTO.getOldPass(), changePassDTO.getNewPass())) {
                    logger.error(Constants.LOGIN_PASS_OLD_SS_FALSE);
                    throw new ServiceException(Translator.toLocale(Constants.LOGIN_PASS_OLD_SS_FALSE));
                }
            }
            if (!ValidateUtils.checkResetPass(changePassDTO.getComPass(), changePassDTO.getNewPass())) {
                logger.error(Constants.LOGIN_PASS_NEW_SS_FALSE);
                throw new ServiceException(Translator.toLocale(Constants.LOGIN_PASS_NEW_SS_FALSE));
            }
            userDTO.setPass(new BCryptPasswordEncoder().encode(changePassDTO.getNewPass()));
            try {
                Users users = usersMapper.toEntity(userDTO);
                usersRepository.save(users);
                if (StringUtils.isNotEmpty(changePassDTO.getKey())) {
                    userDTO.setResetKey(null);
                    userDTO.setResetDate(null);
                    Users users1 = usersMapper.toEntity(userDTO);
                    usersRepository.save(users1);
                }
                logger.info(Constants.CHANGE_PASS_SUS);
                return Translator.toLocale(Constants.CHANGE_PASS_SUS);
            } catch (ServiceException e) {
                logger.error(Constants.CHANGE_PASS_FALSE, e);
                throw new ServiceException(Translator.toLocale(Constants.CHANGE_PASS_FALSE));
            }
        } else {
            logger.error(Constants.CHANGE_NAME_FALSE);
            throw new ServiceException(Translator.toLocale(Constants.CHANGE_NAME_FALSE));
        }
    }

    public UsersDTO forgotPassword(ChangePassDTO obj) {
        if (null != obj.getEmail()) {
            try {
                Users rs = usersRepository.searchUsersId(null, obj.getEmail(), null);
                UsersDTO userDTO = usersMapper.toDto(rs);
                if (userDTO != null) {
                    return userDTO;
                }
            } catch (Exception e) {
                logger.error(Translator.toLocale(Constants.LOGIN_EMAIL_FALSE), e);
                throw new ServiceException(Translator.toLocale(Constants.LOGIN_EMAIL_FALSE));
            }
        }
        logger.error(Constants.LOGIN_EMAIL_FALSE);
        throw new ServiceException(Translator.toLocale(Constants.LOGIN_EMAIL_FALSE));
    }

    public void updateKey(UsersDTO sysUserDTO) {
        sysUserDTO.setResetDate(DateUtil.getDateC());
        Users sysUser = usersMapper.toEntity(sysUserDTO);
        usersRepository.save(sysUser);
    }
}
