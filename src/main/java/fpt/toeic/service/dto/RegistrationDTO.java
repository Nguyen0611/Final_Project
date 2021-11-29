package fpt.toeic.service.dto;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fpt.toeic.config.ZonedDateTimeDeserializer;
import fpt.toeic.utils.annotations.Phone;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 */
public class RegistrationDTO implements Serializable {

    private Long id;

    @Size(max = 60)
    private String pass;

    @NotNull(message = "Email không được trống")
    @Size(max = 100)
    private String mail;

    @Size(max = 60)
    private String oldPass;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RegistrationDTO)) {
            return false;
        }

        return id != null && id.equals(((RegistrationDTO) o).id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    @Override
    public int hashCode() {
        return 31;
    }


}
