package it.qbteam.serviceimpl;

import it.qbteam.model.PlaceAccess;
import it.qbteam.model.TimePerUserReport;
import it.qbteam.persistence.repository.PlaceAccessRepository;
import it.qbteam.service.ReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    private PlaceAccessRepository placeAccessRepo;

    @Autowired
    public ReportServiceImpl(PlaceAccessRepository placeAccessRepository) {
        this.placeAccessRepo = placeAccessRepository;
    }

    /**
     * Calculates the duration in seconds (rounded to lowest integer) between firstDate and lastDate.
     *
     * @param firstDate
     * @param lastDate
     * @return duration between firstDate and lastDate
     */
    private Long getDuration(OffsetDateTime firstDate, OffsetDateTime lastDate) {
        return lastDate.minusSeconds(firstDate.toInstant().toEpochMilli()/1000).toInstant().toEpochMilli()/1000;
    }

    /**
     * Returns a set of aggregated data in which each record contains how much time (in seconds) has been a certain user in a place of an organization.
     * The data is collected from the access data, which means that only authenticated accesses make sense and will be returned.
     *
     * @param placeId id of the place of which data is requested
     * @return list in which each record contains how much time (in seconds) has been a certain user in a place of an organization.
     */
    @Override
    public List<TimePerUserReport> getTimePerUserReport(Long placeId) {
        Iterable<PlaceAccess> allAccessOfSinglePlace = placeAccessRepo.findByPlaceId(placeId);
        List<PlaceAccess> listOfAllAccessOnPlace = new ArrayList<>();

        allAccessOfSinglePlace.forEach(listOfAllAccessOnPlace::add);

        Map<String, Long> mapUserIdWithTime = listOfAllAccessOnPlace.stream()
                .filter(access -> access.getOrgAuthServerId() != null && access.getExitTimestamp() != null)
                .collect(Collectors.groupingBy(PlaceAccess::getOrgAuthServerId, Collectors
                        .summingLong(acc -> getDuration(acc.getEntranceTimestamp(), acc.getExitTimestamp()))));

        List<TimePerUserReport> returnList = new LinkedList<>();
        mapUserIdWithTime.forEach((id, time) -> {
            TimePerUserReport report = new TimePerUserReport();

            report.setTotalTime(time);
            report.setOrgAuthServerId(id);
            report.setPlaceId(placeId);

            returnList.add(report);
        });

        return returnList;
    }
}
