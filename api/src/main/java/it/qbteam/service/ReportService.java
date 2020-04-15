package it.qbteam.service;

import it.qbteam.model.TimePerUserReport;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ReportService {
    Optional<List<TimePerUserReport>> getTimePerUserReport(Long organizationId);
}
