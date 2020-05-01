package it.qbteam.serviceimpl;

import it.qbteam.model.OrganizationAccess;
import it.qbteam.model.OrganizationPresenceCounter;
import it.qbteam.model.PlaceAccess;
import it.qbteam.model.PlacePresenceCounter;
import it.qbteam.repository.sql.OrganizationAccessRepository;
import it.qbteam.repository.sql.PlaceAccessRepository;
import it.qbteam.service.PresenceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class PresenceServiceImpl implements PresenceService {

    private OrganizationAccessRepository organizationAccessRepo;
    private PlaceAccessRepository placeAccessRepo;
    private RedisTemplate<String, Integer> presenceCounterTemplate;

    @Autowired
    public PresenceServiceImpl(
        OrganizationAccessRepository organizationAccessRepository,
        PlaceAccessRepository placeAccessRepository,
        @Qualifier("presenceCounterTemplate") RedisTemplate<String, Integer> presenceCounterTemplate
    ) {
        this.organizationAccessRepo = organizationAccessRepository;
        this.placeAccessRepo = placeAccessRepository;
        this.presenceCounterTemplate = presenceCounterTemplate;
    }


    @Override
    public Optional<OrganizationPresenceCounter> getOrganizationPresenceCounter(Long organizationId) {
        Integer currentCounter = presenceCounterTemplate.opsForValue().get("organization:"+organizationId);
        
        OrganizationPresenceCounter orgPresenceCounter = new OrganizationPresenceCounter().organizationId(organizationId);
        orgPresenceCounter = orgPresenceCounter.counter(currentCounter == null ? 0 : currentCounter);

        return Optional.of(orgPresenceCounter);
    }

    @Override
    public List<OrganizationAccess> getOrganizationPresenceList(Long organizationId) {
        List<OrganizationAccess> accessList = new LinkedList<>();
        List<OrganizationAccess> presenceList = new LinkedList<>();

        organizationAccessRepo.findByOrganizationId(organizationId).forEach(accessList::add);

        accessList.parallelStream().filter((orgAccess) -> orgAccess.getExitTimestamp() == null).forEach(presenceList::add);

        return presenceList;
    }

    @Override
    public Optional<PlacePresenceCounter> getPlacePresenceCounter(Long placeId) {
        Integer currentCounter = presenceCounterTemplate.opsForValue().get("place:"+placeId);
        
        PlacePresenceCounter placePresenceCounter = new PlacePresenceCounter().placeId(placeId);
        placePresenceCounter = placePresenceCounter.counter(currentCounter == null ? 0 : currentCounter);
        
        return Optional.of(placePresenceCounter);
    }

    @Override
    public List<PlaceAccess> getPlacePresenceList(Long placeId) {
        List<PlaceAccess> accessList = new LinkedList<>();
        List<PlaceAccess> presenceList = new LinkedList<>();

        placeAccessRepo.findByPlaceId(placeId).forEach(accessList::add);

        accessList.parallelStream().filter((orgAccess) -> orgAccess.getExitTimestamp() == null).forEach(presenceList::add);

        return presenceList;
    }
}
