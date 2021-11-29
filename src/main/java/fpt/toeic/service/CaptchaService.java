package fpt.toeic.service;

import fpt.toeic.config.Constants;
import fpt.toeic.service.dto.RecaptchaDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class CaptchaService {

    private final Logger log = LoggerFactory.getLogger( CaptchaService.class );


    private final RestTemplate restTemplate;

    public CaptchaService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    public boolean verify(String response) {
        MultiValueMap param = new LinkedMultiValueMap<>();
        param.add( "secret", Constants.RECAPTCHA_SECRET_KEY );
        param.add( "response", response );

        RecaptchaDTO recaptchaResponse;
        try {
            recaptchaResponse = this.restTemplate.postForObject( Constants.RECAPTCHA_VERIFY_URL, param, RecaptchaDTO.class );
            if(recaptchaResponse != null) {
                return recaptchaResponse.isSuccess();
            }
        } catch (RestClientException e) {
            log.error( e.getMessage(), e );
        }
        return false;
    }

}
