package fpt.toeic.service.dto;

import org.springframework.http.HttpHeaders;

import java.util.List;

public class LoginDTO {
    private HttpHeaders httpHeaders;
    private UsersDTO customUserDetails;
    private List<DsRolesDTO> listObjects;
    private String path;

    public String getPath() {
        return path;
    }


    public void setPath(String path) {
        this.path = path;
    }

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }

    public void setHttpHeaders(HttpHeaders httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    public UsersDTO getCustomUserDetails() {
        return customUserDetails;
    }

    public void setCustomUserDetails(UsersDTO customUserDetails) {
        this.customUserDetails = customUserDetails;
    }

    public List<DsRolesDTO> getListObjects() {
        return listObjects;
    }

    public void setListObjects(List<DsRolesDTO> listObjects) {
        this.listObjects = listObjects;
    }
}
