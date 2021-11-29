package fpt.toeic.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A QuestionAnswers.
 */
@Entity
@Table(name = "question_answers")
public class QuestionAnswers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 500)
    @Column(name = "name", length = 500)
    private String name;

    @Size(max = 500)
    @Column(name = "answer", length = 500)
    private String answer;

    @Column(name = "status")
    private Long status;

    @Column(name = "stt")
    private Long stt;

    @Size(max = 200)
    @Column(name = "answer_to_choose", length = 200)
    private String answerToChoose;

    @Size(max = 500)
    @Column(name = "transscript", length = 500)
    private String transscript;

    @ManyToOne
    @JsonIgnoreProperties(value = "dsQuestionAnswers", allowSetters = true)
    private Category category;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public QuestionAnswers name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnswer() {
        return answer;
    }

    public QuestionAnswers answer(String answer) {
        this.answer = answer;
        return this;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Long getStatus() {
        return status;
    }

    public QuestionAnswers status(Long status) {
        this.status = status;
        return this;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getStt() {
        return stt;
    }

    public QuestionAnswers stt(Long stt) {
        this.stt = stt;
        return this;
    }

    public void setStt(Long stt) {
        this.stt = stt;
    }

    public String getAnswerToChoose() {
        return answerToChoose;
    }

    public QuestionAnswers answerToChoose(String answerToChoose) {
        this.answerToChoose = answerToChoose;
        return this;
    }

    public void setAnswerToChoose(String answerToChoose) {
        this.answerToChoose = answerToChoose;
    }

    public String getTransscript() {
        return transscript;
    }

    public QuestionAnswers transscript(String transscript) {
        this.transscript = transscript;
        return this;
    }

    public void setTransscript(String transscript) {
        this.transscript = transscript;
    }

    public Category getCategory() {
        return category;
    }

    public QuestionAnswers category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuestionAnswers)) {
            return false;
        }
        return id != null && id.equals(((QuestionAnswers) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuestionAnswers{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", answer='" + getAnswer() + "'" +
            ", status=" + getStatus() +
            ", stt=" + getStt() +
            ", answerToChoose='" + getAnswerToChoose() + "'" +
            ", transscript='" + getTransscript() + "'" +
            "}";
    }
}
