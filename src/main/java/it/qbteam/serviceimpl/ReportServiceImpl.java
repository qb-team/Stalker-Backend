package it.qbteam.serviceimpl;

import it.qbteam.model.PlaceAccess;
import it.qbteam.model.TimePerUserReport;
import it.qbteam.repository.sql.PlaceAccessRepository;
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

    @Override
    public List<TimePerUserReport> getTimePerUserReport(Long organizationId) {

        Iterable<PlaceAccess> allAccessOfSingleOrganization = placeAccessRepo.findByPlaceId(organizationId);
        List<PlaceAccess> listOfAllAccessOnOrganization = new ArrayList<>();

        allAccessOfSingleOrganization.forEach(listOfAllAccessOnOrganization::add);

        Map<String, Long> mapUserIdWithTime = listOfAllAccessOnOrganization.stream().filter(access-> access.getOrgAuthServerId()!=null).collect(Collectors.groupingBy(PlaceAccess::getOrgAuthServerId, Collectors.summingLong(acc -> {
           return acc.getExitTimestamp().minusSeconds(
                    acc.getEntranceTimestamp()
                            .toInstant()
                            .toEpochMilli()*1000
            ).toInstant().toEpochMilli()*1000;
        })));
        List<TimePerUserReport> returnList = new LinkedList<>();
        mapUserIdWithTime.forEach((id, time)-> returnList.add(new TimePerUserReport()
                .totalTime(OffsetDateTime.of(LocalDateTime.of(0, 00, 00, 00, 00), ZoneOffset.ofHoursMinutes(0, 0)).plusSeconds(time))
                .orgAuthServerId(id)
                .organizationId(organizationId)));
        return returnList;
    }
}

