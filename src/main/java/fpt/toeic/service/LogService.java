package fpt.toeic.service;

import fpt.toeic.config.ApplicationContextHolder;
import fpt.toeic.domain.SysLogs;
import fpt.toeic.repository.SysLogsRepository;
import fpt.toeic.utils.base.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LogService extends Task {

    @Autowired
    SysLogsRepository logsDAO = ApplicationContextHolder.getContext().getBean(SysLogsRepository.class);

    private final Logger log = LoggerFactory.getLogger(LogService.class);

    @Override
    public Integer call() throws Exception {
        List lstLog = getItems();
        try {
            if (lstLog != null && !lstLog.isEmpty()) {
                save(lstLog);
            }
        } catch (Exception e) {
            log.error(e.toString(), e);
            return 0;
        }
        return 1;
    }

    public void save(List<SysLogs> lst) {
        try {
            for (SysLogs logs : lst) {
                logsDAO.save(logs);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        } finally {
//           // dong ket noi
        }
    }
}
