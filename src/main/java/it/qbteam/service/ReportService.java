package it.qbteam.service;

import it.qbteam.model.TimePerUserReport;

import java.util.List;

/**
 * Report Service
 * 
 * This service performs the actual operations for retrieving reports of organizations and places.
 * 
 * @author Davide Lazzaro
 * @author Tommaso Azzalin
 */
public interface ReportService {
    /**
     * Description
     * 
     * @param placeId
     * @return
     */
    List<TimePerUserReport> getTimePerUserReport(Long placeId);
}
