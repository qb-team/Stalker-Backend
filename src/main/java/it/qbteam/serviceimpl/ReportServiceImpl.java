package it.qbteam.serviceimpl;

import it.qbteam.model.PlaceAccess;
import it.qbteam.model.TimePerUserReport;
import it.qbteam.repository.PlaceAccessRepository;
import it.qbteam.service.ReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
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

    private Long getDuration(OffsetDateTime firstDate, OffsetDateTime lastDate) {
        return lastDate.minusSeconds(firstDate.toInstant().toEpochMilli() * 1000).toInstant().toEpochMilli() * 1000;
    }

    @Override
    public List<TimePerUserReport> getTimePerUserReport(Long placeId) {
        final OffsetDateTime timeIdentity = OffsetDateTime.of(LocalDateTime.of(0, 00, 00, 00, 00), ZoneOffset.ofHoursMinutes(0, 0));
        Iterable<PlaceAccess> allAccessOfSinglePlace = placeAccessRepo.findByPlaceId(placeId);
        List<PlaceAccess> listOfAllAccessOnPlace = new ArrayList<>();

        allAccessOfSinglePlace.forEach(listOfAllAccessOnPlace::add);

        Map<String, Long> mapUserIdWithTime = listOfAllAccessOnPlace.stream()
                .filter(access -> access.getOrgAuthServerId() != null)
                .collect(Collectors.groupingBy(PlaceAccess::getOrgAuthServerId, Collectors
                        .summingLong(acc -> getDuration(acc.getEntranceTimestamp(), acc.getExitTimestamp()))));

        List<TimePerUserReport> returnList = new LinkedList<>();
        mapUserIdWithTime.forEach((id, time) -> {
            TimePerUserReport report = new TimePerUserReport();

            report.setTotalTime(timeIdentity.plusSeconds(time));
            report.setOrgAuthServerId(id);
            report.setPlaceId(placeId);

            returnList.add(report);
        });

        return returnList;
    }
}
