package it.qbteam.serviceImpl;

import it.qbteam.api.PresenceApi;
import it.qbteam.model.OrganizationAccess;
import it.qbteam.model.OrganizationPresenceCounter;
import it.qbteam.model.PlaceAccess;
import it.qbteam.model.PlacePresenceCounter;
import it.qbteam.repository.sql.OrganizationAccessRepository;
import it.qbteam.repository.sql.PlaceAccessRepository;
import it.qbteam.service.PresenceService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PresenceServiceImpl implements PresenceService {

    private OrganizationAccessRepository organizationAccessRepo;
    private PlaceAccessRepository placeAccessRepo;
    private RedisTemplate<String, Integer> organizationPresenceCounter;
    private RedisTemplate<String, Integer> placePresenceCounter;


    @Override
    public Optional<OrganizationPresenceCounter> getOrganizationPresenceCounter(Long organizationId) {
        return Optional.empty();
    }

    @Override
    public Optional<List<OrganizationAccess>> getOrganizationPresenceList(Long organizationId) {
        return Optional.empty();
    }

    @Override
    public Optional<PlacePresenceCounter> getPlacePresenceCounter(Long placeId) {
        return Optional.empty();
    }

    @Override
    public Optional<List<PlaceAccess>> getPlacePresenceList(Long placeId) {
        return Optional.empty();
    }
}
