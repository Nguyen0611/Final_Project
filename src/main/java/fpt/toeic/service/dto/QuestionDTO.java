package fpt.toeic.service.dto;

import java.io.Serializable;
import java.util.List;


public class QuestionDTO  implements Serializable {

    private Long id;

    private String name;

    private Long stt;

    private List<ListAnswersDTO> listAnswers;
    private Long categoryId;
    private String transscript;
    private Long status;

    private Long answer;

    public Long getStt() {
        return stt;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setStt(Long stt) {
        this.stt = stt;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
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

    public List<ListAnswersDTO> getListAnswers() {
        return listAnswers;
    }

    public void setListAnswers(List<ListAnswersDTO> listAnswers) {
        this.listAnswers = listAnswers;
    }

    public String getTransscript() {
        return transscript;
    }

    public void setTransscript(String transscript) {
        this.transscript = transscript;
    }

    public Long getAnswer() {
        return answer;
    }

    public void setAnswer(Long answer) {
        this.answer = answer;
    }
}
