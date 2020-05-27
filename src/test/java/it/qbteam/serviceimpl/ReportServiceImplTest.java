package it.qbteam.serviceimpl;

import it.qbteam.model.PlaceAccess;
import it.qbteam.model.TimePerUserReport;
import it.qbteam.repository.PlaceAccessRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;

@RunWith(SpringRunner.class)
public class ReportServiceImplTest {
    @MockBean
    private PlaceAccessRepository placeAccessRepo;

    @TestConfiguration
    static class ReportServiceImplConfiguration{
        @Bean
        public ReportServiceImpl reportService( PlaceAccessRepository placeAccessRepo) {
            return new ReportServiceImpl(placeAccessRepo);
        }

    }
    @Autowired
    private ReportServiceImpl reportService;

    private Iterable<PlaceAccess> iterablePlaceAccess = new ArrayList<>();
    private List<PlaceAccess> listPlaceAccess = new ArrayList<>();
    private PlaceAccess testPlaceAccess = new PlaceAccess().placeId(1l).id(1l);
    private TimePerUserReport test1HourTestPerUserReport = new TimePerUserReport().totalTime(3600L);
    private List<TimePerUserReport> returnList = new LinkedList<>();
    private Iterable<PlaceAccess> nullIterableList = new ArrayList<>();
    @Before
    public void setUp(){
        testPlaceAccess.setEntranceTimestamp(OffsetDateTime.of(LocalDateTime.of(2020, 5, 25, 17, 23), ZoneOffset.ofHoursMinutes(0, 0)));
        testPlaceAccess.setExitTimestamp(OffsetDateTime.of(LocalDateTime.of(2020, 5, 25, 18, 23), ZoneOffset.ofHoursMinutes(0, 0)));
        testPlaceAccess.setOrgAuthServerId("prova");
        listPlaceAccess.add(testPlaceAccess);
        iterablePlaceAccess=listPlaceAccess;
        test1HourTestPerUserReport.setOrgAuthServerId("prova");
        test1HourTestPerUserReport.setPlaceId(1l);
        returnList.add(test1HourTestPerUserReport);
    }

    @Test
    public void testGetTimePerUserReportReturnListOfTimePerUserReportGivenValidPlaceId() {
        Mockito.when(placeAccessRepo.findByPlaceId(anyLong())).thenReturn(iterablePlaceAccess);
        Assert.assertEquals(returnList, reportService.getTimePerUserReport(testPlaceAccess.getPlaceId()));
    }
    @Test
    public void testGetTimePerUserReportReturnEmptyListGivenInvalidPlaceId(){
        Mockito.when(placeAccessRepo.findByPlaceId(anyLong())).thenReturn(nullIterableList);
        Assert.assertEquals(new LinkedList<PlaceAccess>(), reportService.getTimePerUserReport(3l));
    }
}
