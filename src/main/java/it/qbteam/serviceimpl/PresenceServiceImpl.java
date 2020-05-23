package it.qbteam.serviceimpl;

import it.qbteam.model.OrganizationPresenceCounter;
import it.qbteam.model.PlacePresenceCounter;
import it.qbteam.service.PresenceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PresenceServiceImpl implements PresenceService {

    private RedisTemplate<String, Integer> presenceCounterTemplate;

    @Autowired
    public PresenceServiceImpl(
        @Qualifier("presenceCounterTemplate") RedisTemplate<String, Integer> presenceCounterTemplate
    ) {
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
    public Optional<PlacePresenceCounter> getPlacePresenceCounter(Long placeId) {
        Integer currentCounter = presenceCounterTemplate.opsForValue().get("place:"+placeId);

        PlacePresenceCounter placePresenceCounter = new PlacePresenceCounter().placeId(placeId);
        placePresenceCounter = placePresenceCounter.counter(currentCounter == null ? 0 : currentCounter);

        return Optional.of(placePresenceCounter);
    }
}
