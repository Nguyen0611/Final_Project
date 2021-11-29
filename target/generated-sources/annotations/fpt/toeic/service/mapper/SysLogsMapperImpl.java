package fpt.toeic.service.mapper;

import fpt.toeic.domain.SysLogs;
import fpt.toeic.service.dto.SysLogsDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-09-07T21:54:40+0700",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_281 (Oracle Corporation)"
)
@Component
public class SysLogsMapperImpl implements SysLogsMapper {

    @Override
    public SysLogs toEntity(SysLogsDTO dto) {
        if ( dto == null ) {
            return null;
        }

        SysLogs sysLogs = new SysLogs();

        sysLogs.setId( dto.getId() );
        sysLogs.setUserImpact( dto.getUserImpact() );
        sysLogs.setCodeAction( dto.getCodeAction() );
        sysLogs.setContent( dto.getContent() );
        sysLogs.setImpactTime( dto.getImpactTime() );
        sysLogs.setEndTime( dto.getEndTime() );
        sysLogs.setIp( dto.getIp() );
        sysLogs.setNameClient( dto.getNameClient() );

        return sysLogs;
    }

    @Override
    public SysLogsDTO toDto(SysLogs entity) {
        if ( entity == null ) {
            return null;
        }

        SysLogsDTO sysLogsDTO = new SysLogsDTO();

        sysLogsDTO.setId( entity.getId() );
        sysLogsDTO.setUserImpact( entity.getUserImpact() );
        sysLogsDTO.setCodeAction( entity.getCodeAction() );
        sysLogsDTO.setContent( entity.getContent() );
        sysLogsDTO.setImpactTime( entity.getImpactTime() );
        sysLogsDTO.setEndTime( entity.getEndTime() );
        sysLogsDTO.setIp( entity.getIp() );
        sysLogsDTO.setNameClient( entity.getNameClient() );

        return sysLogsDTO;
    }

    @Override
    public List<SysLogs> toEntity(List<SysLogsDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<SysLogs> list = new ArrayList<SysLogs>( dtoList.size() );
        for ( SysLogsDTO sysLogsDTO : dtoList ) {
            list.add( toEntity( sysLogsDTO ) );
        }

        return list;
    }

    @Override
    public List<SysLogsDTO> toDto(List<SysLogs> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<SysLogsDTO> list = new ArrayList<SysLogsDTO>( entityList.size() );
        for ( SysLogs sysLogs : entityList ) {
            list.add( toDto( sysLogs ) );
        }

        return list;
    }
}
