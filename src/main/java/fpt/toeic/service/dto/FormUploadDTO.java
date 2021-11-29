package fpt.toeic.service.dto;

import fpt.toeic.utils.annotations.ValidFile;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Transient;
import javax.validation.constraints.Null;

import java.util.List;

import static fpt.toeic.config.Constants.CONTENT_TYPE.*;


public class FormUploadDTO {
    private JSONObject model; // Tạo model
//    @ValidFile(filesAllow = {JPG, JPEG, PNG}, message = "{attachFile.onlySupportMicrosoftAndPdfFile}")
    private MultipartFile file;
    private List<MultipartFile> file1;

    public List<MultipartFile> getFile1() {
        return file1;
    } // upload 2 file liên tục

    public void setFile1(List<MultipartFile> file1) {
        this.file1 = file1;
    }

    public MultipartFile getFile() {
        return file;
    } //Tab Input data

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public JSONObject getModel() {
        return model;
    } // Đối tượng truyền sang như Tên chủ đề, đề bài - keyvalue

    public void setModel(JSONObject model) {
        this.model = model;
    }//
}
