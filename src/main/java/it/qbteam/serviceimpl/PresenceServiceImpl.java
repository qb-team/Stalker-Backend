package it.qbteam.serviceimpl;

import it.qbteam.model.OrganizationAccess;
import it.qbteam.model.OrganizationPresenceCounter;
import it.qbteam.model.PlaceAccess;
import it.qbteam.model.PlacePresenceCounter;
import it.qbteam.repository.sql.OrganizationAccessRepository;
import it.qbteam.repository.sql.PlaceAccessRepository;
import it.qbteam.service.PresenceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PresenceServiceImpl implements PresenceService {

    private OrganizationAccessRepository organizationAccessRepo;
    private PlaceAccessRepository placeAccessRepo;
    private RedisTemplate<String, Integer> organizationPresenceCounter;
    private RedisTemplate<String, Integer> placePresenceCounter;

    @Autowired
    public PresenceServiceImpl(
        OrganizationAccessRepository organizationAccessRepository,
        PlaceAccessRepository placeAccessRepository,
        RedisTemplate<String, Integer> organizationPresenceCounter,
        RedisTemplate<String, Integer> placePresenceCounter
    ) {
        this.organizationAccessRepo = organizationAccessRepository;
        this.placeAccessRepo = placeAccessRepository;
        this.organizationPresenceCounter = organizationPresenceCounter;
        this.placePresenceCounter = placePresenceCounter;
    }


    @Override
    public Optional<OrganizationPresenceCounter> getOrganizationPresenceCounter(Long organizationId) {
        return Optional.empty();
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
        return Optional.empty();
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
