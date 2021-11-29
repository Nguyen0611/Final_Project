package fpt.toeic.service.dto;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * A DTO for the {@link fpt.toeic.domain.QuestionAnswers} entity.
 */
public class QuestionAnswersDTO  {

    private Long id;

    @Size(max = 60)
    private String name;

    @Size(max = 60)
    private String answer;

    private Long status;

    public Long stt;

    public Long isCheck;

    public String pathCategory;

    @Size(max = 200)
    private String answerToChoose;

    @Size(max = 500)
    private String transscript;

    private List<ListAnswersDTO> listAnswers;
    private List<IsColorDTO> listStringAnswers;
    private List<FileUploadDTO> fileUploadDTOs;

    public List<FileUploadDTO> getFileUploadDTOs() {
        return fileUploadDTOs;
    }

    public void setFileUploadDTOs(List<FileUploadDTO> fileUploadDTOs) {
        this.fileUploadDTOs = fileUploadDTOs;
    }

    public List<IsColorDTO> getListStringAnswers() {
        return listStringAnswers;
    }

    public Long getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(Long isCheck) {
        this.isCheck = isCheck;
    }

    public void setListStringAnswers(List<IsColorDTO> listStringAnswers) {
        this.listStringAnswers = listStringAnswers;
    }

    public QuestionAnswersDTO uniqueAttributes(QuestionAnswersDTO questionAnswersDTO) {
        if (stt == questionAnswersDTO.getStt()) {
            return questionAnswersDTO;
        }
        return null;
    }

    public List<ListAnswersDTO> getListAnswers() {
        return listAnswers;
    }

    public void setListAnswers(List<ListAnswersDTO> listAnswers) {
        this.listAnswers = listAnswers;
    }

    private Long categoryId;

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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getStt() {
        return stt;
    }

    public void setStt(Long stt) {
        this.stt = stt;
    }

    public String getAnswerToChoose() {
        return answerToChoose;
    }

    public void setAnswerToChoose(String answerToChoose) {
        this.answerToChoose = answerToChoose;
    }

    public String getTransscript() {
        return transscript;
    }

    public void setTransscript(String transscript) {
        this.transscript = transscript;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getPathCategory() {
        return pathCategory;
    }

    public void setPathCategory(String pathCategory) {
        this.pathCategory = pathCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuestionAnswersDTO)) {
            return false;
        }

        return id != null && id.equals(((QuestionAnswersDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuestionAnswersDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", answer='" + getAnswer() + "'" +
            ", status=" + getStatus() +
            ", stt=" + getStt() +
            ", answerToChoose='" + getAnswerToChoose() + "'" +
            ", transscript='" + getTransscript() + "'" +
            ", categoryId=" + getCategoryId() +
            ", pathCategory=" + getPathCategory() +
            "}";
    }
}
