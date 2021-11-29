package fpt.toeic.service.dto;

/**
 * A DTO for the {@link fpt.toeic.domain.QuestionAnswers} entity.
 */
public class IsColorDTO {

    private String value;
    private String color;
    private Long categoryId;
    private String categoryName;
    private String transscript;

    public String getTransscript() {
        return transscript;
    }

    public void setTransscript(String transscript) {
        this.transscript = transscript;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
