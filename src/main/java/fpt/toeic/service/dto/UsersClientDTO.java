package fpt.toeic.service.dto;

import fpt.toeic.domain.Users;
import fpt.toeic.utils.DateUtil;
import fpt.toeic.utils.annotations.Phone;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 */
public class UsersClientDTO implements Serializable {

    private Long id;

    @NotNull(message = "Tên tài khoản không được trống")
    @Size(max = 60)
    private String name;


    @Size(max = 100)
    private String mail;

    @NotNull(message = "Số điện thoại không được trống")
    @Size(max = 10)
    @Phone(message = "Số điện thoại không đúng định dạng")
    private String phone;

    public Users users(UsersClientDTO usersClientDTO) {
        Users users = new Users();
        users.setPass("-1");
        users.setCreationTime(DateUtil.getDateC());
        users.setCreator("Tài khoản khách hàng");
        users.setName("-1");
        users.setMail(usersClientDTO.getMail());
        users.setPhone(usersClientDTO.getPhone());
        users.setFullName(usersClientDTO.getName());
        users.setStatus(0l);
        return users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UsersClientDTO)) {
            return false;
        }

        return id != null && id.equals(((UsersClientDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UsersDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", mail='" + getMail() + "'" +
            ", phone='" + getPhone() + "'" +
            "}";
    }
}
