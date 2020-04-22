package it.qbteam.serviceimpl;

import it.qbteam.model.TimePerUserReport;
import it.qbteam.repository.sql.OrganizationAccessRepository;
import it.qbteam.repository.sql.PlaceAccessRepository;
import it.qbteam.service.ReportService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ReportServiceImpl implements ReportService {

    private OrganizationAccessRepository organizationAccessRepo;
    private PlaceAccessRepository placeAccessRepo;

    @Override
    public Optional<List<TimePerUserReport>> getTimePerUserReport(Long organizationId) {
        return Optional.empty();
    }
}
