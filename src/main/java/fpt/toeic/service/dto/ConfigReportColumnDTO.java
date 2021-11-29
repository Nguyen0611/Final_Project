package fpt.toeic.service.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import fpt.toeic.config.Constants;
import fpt.toeic.utils.DataUtil;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConfigReportColumnDTO implements Serializable {

    private Long id;
    @NotNull
    private String columnName;
    @NotNull
    private String title;
    private Integer isRequire;
    private Integer isTimeColumn;
    private Integer isShow;
    @NotNull
    private String dataType;
    private Long reportId;
    private Integer maxLength;
    private String defaultValue;
    private String regexPattern;
    private Integer isPrimaryKey;
    private Integer pos;
    private Integer status;
    private String creator;
    private String key;
    private String extra;
    private Instant updateTime;

    private String controlType;
    private String refData;

    public ConfigReportColumnDTO() {
    }

    public ConfigReportColumnDTO(Object[] column) {
        AtomicInteger atomicInteger = new AtomicInteger();
        ConfigReportColumnDTO columnDTO = new ConfigReportColumnDTO();
        columnDTO.setColumnName(DataUtil.safeToString(column[0]).toLowerCase());
        columnDTO.setDataType(DataUtil.safeToString(column[1]));
        String nullable = DataUtil.safeToString(column[3]);
        columnDTO.setIsRequire("YES".equalsIgnoreCase(nullable) ? 1 : 0);
        columnDTO.setKey(DataUtil.safeToString(column[4]));
        columnDTO.setDefaultValue(DataUtil.safeToString(column[5]));
        columnDTO.setExtra(DataUtil.safeToString(column[6]));
        columnDTO.setTitle(DataUtil.safeToString(column[8], DataUtil.safeToString(column[0])));
        columnDTO.setIsShow(Constants.IS_SHOW);
        columnDTO.setPos(atomicInteger.getAndIncrement());
    }


    public String getControlType() {
        return controlType;
    }

    public void setControlType(String controlType) {
        this.controlType = controlType;
    }

    public String getRefData() {
        return refData;
    }

    public void setRefData(String refData) {
        this.refData = refData;
    }

    public Integer getIsPrimaryKey() {
        return isPrimaryKey;
    }

    public void setIsPrimaryKey(Integer isPrimaryKey) {
        this.isPrimaryKey = isPrimaryKey;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public Integer getIsRequire() {
        return isRequire;
    }

    public void setIsRequire(Integer isRequire) {
        this.isRequire = isRequire;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public String getRegexPattern() {
        return regexPattern;
    }

    public void setRegexPattern(String regexPattern) {
        this.regexPattern = regexPattern;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Integer getIsShow() {
        if (isShow == null) {
            isShow = 0;
        }
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public Integer getIsTimeColumn() {
        return isTimeColumn;
    }

    public void setIsTimeColumn(Integer isTimeColumn) {
        this.isTimeColumn = isTimeColumn;
    }

    public Integer getPos() {
        if (pos == null) {
            pos = 0;
        }
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConfigReportColumnDTO configReportColumnDTO = (ConfigReportColumnDTO) o;
        if (configReportColumnDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), configReportColumnDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConfigReportColumnDTO{" +
            "id=" + getId() +
            ", columnName='" + getColumnName() + "'" +
            ", title='" + getTitle() + "'" +
            ", dataType='" + getDataType() + "'" +
            ", reportId=" + getReportId() +
            ", isRequire=" + getIsRequire() +
            ", maxLength=" + getMaxLength() +
            ", regexPattern='" + getRegexPattern() + "'" +
            ", defaultValue='" + getDefaultValue() + "'" +
            ", isShow=" + getIsShow() +
            ", isTimeColumn=" + getIsTimeColumn() +
            ", pos=" + getPos() +
            ", status=" + getStatus() +
            ", creator='" + getCreator() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            "}";
    }
}
