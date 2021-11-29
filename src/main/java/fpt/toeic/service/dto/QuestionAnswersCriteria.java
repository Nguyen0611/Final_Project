package fpt.toeic.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link fpt.toeic.domain.QuestionAnswers} entity. This class is used
 * in {@link fpt.toeic.web.rest.QuestionAnswersResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /question-answers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class QuestionAnswersCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter answer;

    private LongFilter status;

    private LongFilter stt;

    private StringFilter answerToChoose;

    private StringFilter transscript;

    private LongFilter categoryId;

    public QuestionAnswersCriteria() {
    }

    public QuestionAnswersCriteria(QuestionAnswersCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.answer = other.answer == null ? null : other.answer.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.stt = other.stt == null ? null : other.stt.copy();
        this.answerToChoose = other.answerToChoose == null ? null : other.answerToChoose.copy();
        this.transscript = other.transscript == null ? null : other.transscript.copy();
        this.categoryId = other.categoryId == null ? null : other.categoryId.copy();
    }

    @Override
    public QuestionAnswersCriteria copy() {
        return new QuestionAnswersCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getAnswer() {
        return answer;
    }

    public void setAnswer(StringFilter answer) {
        this.answer = answer;
    }

    public LongFilter getStatus() {
        return status;
    }

    public void setStatus(LongFilter status) {
        this.status = status;
    }

    public LongFilter getStt() {
        return stt;
    }

    public void setStt(LongFilter stt) {
        this.stt = stt;
    }

    public StringFilter getAnswerToChoose() {
        return answerToChoose;
    }

    public void setAnswerToChoose(StringFilter answerToChoose) {
        this.answerToChoose = answerToChoose;
    }

    public StringFilter getTransscript() {
        return transscript;
    }

    public void setTransscript(StringFilter transscript) {
        this.transscript = transscript;
    }

    public LongFilter getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(LongFilter categoryId) {
        this.categoryId = categoryId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final QuestionAnswersCriteria that = (QuestionAnswersCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(answer, that.answer) &&
            Objects.equals(status, that.status) &&
            Objects.equals(stt, that.stt) &&
            Objects.equals(answerToChoose, that.answerToChoose) &&
            Objects.equals(transscript, that.transscript) &&
            Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        answer,
        status,
        stt,
        answerToChoose,
        transscript,
        categoryId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuestionAnswersCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (answer != null ? "answer=" + answer + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (stt != null ? "stt=" + stt + ", " : "") +
                (answerToChoose != null ? "answerToChoose=" + answerToChoose + ", " : "") +
                (transscript != null ? "transscript=" + transscript + ", " : "") +
                (categoryId != null ? "categoryId=" + categoryId + ", " : "") +
            "}";
    }

}
