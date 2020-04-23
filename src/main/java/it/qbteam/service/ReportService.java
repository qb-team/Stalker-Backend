package it.qbteam.service;

import it.qbteam.model.TimePerUserReport;

import java.util.List;
import java.util.Optional;

public interface ReportService {
    List<TimePerUserReport> getTimePerUserReport(Long organizationId);
}
