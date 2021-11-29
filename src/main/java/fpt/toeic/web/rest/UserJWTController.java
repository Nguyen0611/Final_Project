package fpt.toeic.web.rest;

import fpt.toeic.config.Constants;
import fpt.toeic.config.CustomUserDetails;
import fpt.toeic.security.jwt.JWTFilter;
import fpt.toeic.security.jwt.TokenProvider;
import fpt.toeic.service.MailService;
import fpt.toeic.service.UserJWTService;
import fpt.toeic.service.dto.ChangePassDTO;
import fpt.toeic.service.dto.LoginDTO;
import fpt.toeic.service.dto.UsersDTO;
import fpt.toeic.utils.Translator;
import fpt.toeic.utils.Utils;
import fpt.toeic.web.rest.vm.LoginVM;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class UserJWTController {

    private static final Logger logger = LoggerFactory.getLogger( UserJWTController.class );

    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    UserJWTService userJWTService;

    @Autowired
    MailService mailService;

    public UserJWTController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> login(@RequestBody UsersDTO obj) {
        try {
            return new ResponseEntity<>( userJWTService.login( obj, true ), HttpStatus.OK );
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            if (e.getMessage().equals( Translator.toLocale( Constants.GOOGLE_RECAPTCHA_FALSE ) )) {
                return ResponseEntity.status(401).body( Utils.getStatusBadRequest(e.getMessage()));
            }
            return ResponseEntity.status(400).body( Utils.getStatusBadRequest(e.getMessage()));
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Object> authorize() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        LoginDTO login = new LoginDTO();
        Authentication authentication = securityContext.getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        login.setListObjects(customUserDetails.getList());
        login.setCustomUserDetails(customUserDetails.getUser());
        return ResponseEntity.ok().body( login  );
    }


    @PostMapping(value = "/changePass")
    public ResponseEntity<Object> changePassword(@RequestBody ChangePassDTO changePassDTO, HttpServletRequest request) {
        try {
            String updateSuccess = userJWTService.resetPassword(changePassDTO);
            return ResponseEntity.status(200).body(Collections.singletonMap(Constants.DATA, updateSuccess));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            if (e.getMessage().equals(Translator.toLocale(Constants.TOKEN_ERROR))) {
                return ResponseEntity.status(401).body( Utils.getStatusBadRequest(e.getMessage()));
            }
            return ResponseEntity.status(400).body( Utils.getStatusBadRequest(e.getMessage()));
        }
    }

    @PostMapping("/sendSimpleEmail")
    public ResponseEntity<Object> sendSimpleEmail( @RequestBody ChangePassDTO changePassDTO) {
        try {
            UsersDTO sysUserDTO = userJWTService.forgotPassword(  changePassDTO );
            String emailTo = changePassDTO.getEmail();
            String refreshToken = RandomStringUtils.randomAlphanumeric( 6 );
            sysUserDTO.setResetKey( refreshToken );
            userJWTService.updateKey( sysUserDTO );
            String htmlMsg = Translator.toLocale(Constants.EMAIL_START)  + "<b> " + sysUserDTO.getFullName() + "</b></p>" + " "
                + Translator.toLocale(Constants.EMAIL_CONTENT1)
                + Translator.toLocale( Constants.EMAIL_PATH ) + refreshToken + "  "
                + Translator.toLocale( Constants.EMAIL_CONTENT2 );
            if (!mailService.sendEmail( emailTo, Translator.toLocale( Constants.EMAIL_SUBJECT ), htmlMsg, Constants.IS_MULTI_PART, Constants.IS_HTML )) {
                throw new ServiceException( Translator.toLocale( Constants.EMAIL_FALSE ) );
            }

            return ResponseEntity.ok().body(Utils.getStatusOk(Translator.toLocale( Constants.EMAIL_TRUE ), null));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(400).body( Utils.getStatusBadRequest(e.getMessage()));
        }
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
