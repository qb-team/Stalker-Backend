package it.qbteam.service;

import it.qbteam.model.TimePerUserReport;

import java.util.List;

/**
 * Report Service
 * 
 * This service performs the actual operations for retrieving reports of places.
 * 
 * @author Davide Lazzaro
 * @author Tommaso Azzalin
 */
public interface ReportService {
    /**
     * Returns a set of aggregated data in which each record contains how much time (in seconds) has been a certain user in a place of an organization.
     * The data is collected from the access data, which means that only authenticated accesses make sense and will be returned.
     * 
     * @param placeId id of the place of which data is requested
     * @return list in which each record contains how much time (in seconds) has been a certain user in a place of an organization.
     */
    List<TimePerUserReport> getTimePerUserReport(Long placeId);
}
