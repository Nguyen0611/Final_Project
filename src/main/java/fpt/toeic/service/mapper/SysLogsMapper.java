package fpt.toeic.service.mapper;


import fpt.toeic.domain.*;
import fpt.toeic.service.dto.SysLogsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SysLogs} and its DTO {@link SysLogsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SysLogsMapper extends EntityMapper<SysLogsDTO, SysLogs> {



    default SysLogs fromId(Long id) {
        if (id == null) {
            return null;
        }
        SysLogs sysLogs = new SysLogs();
        sysLogs.setId(id);
        return sysLogs;
    }
}
