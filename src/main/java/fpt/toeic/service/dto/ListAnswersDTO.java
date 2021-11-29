package fpt.toeic.service.dto;

public class ListAnswersDTO {
    private Long id;
    private String stt;
    private String value;

    public String getStt() {
        return stt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStt(String stt) {
        this.stt = stt;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
