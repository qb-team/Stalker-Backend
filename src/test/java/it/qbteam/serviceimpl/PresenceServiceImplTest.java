package it.qbteam.serviceimpl;

import it.qbteam.model.OrganizationPresenceCounter;
import it.qbteam.model.PlacePresenceCounter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
public class PresenceServiceImplTest {
    @MockBean
    private RedisTemplate<String, Integer> presenceCounterTemplate;

    @Mock
    private ValueOperations<String, Integer> redisValOps;

    @TestConfiguration
    static class PresenceServiceImplConfiguration{
        @Bean
        public PresenceServiceImpl presenceService(RedisTemplate<String, Integer> presenceCounterTemplate) {
            return new PresenceServiceImpl(presenceCounterTemplate);
        }
    }
    @Autowired
    private PresenceServiceImpl presenceService;

    private OrganizationPresenceCounter testOrgCounter = new OrganizationPresenceCounter();
    private PlacePresenceCounter testPlaceCounter= new PlacePresenceCounter();

    @Before
    public void setUp(){
        testOrgCounter.organizationId(1l);
        testPlaceCounter.placeId(1l);
        Mockito.when(presenceCounterTemplate.opsForValue()).thenReturn(redisValOps);
    }
    @Test
    public void testGetOrganizationPresenceCounterReturn0GivenInvalidOrganizationId() {
        Mockito.when(redisValOps.get("organization:1")).thenReturn(null);
        testOrgCounter.counter(0);
        Assert.assertEquals(Optional.of(testOrgCounter), presenceService.getOrganizationPresenceCounter(1l));
    }
    @Test
    public void testGetOrganizationPresenceCounterReturnValidOrganizationId(){
        Mockito.when(redisValOps.get("organization:1")).thenReturn(new Integer(12));
        testOrgCounter.counter(12);
        Assert.assertEquals(Optional.of(testOrgCounter), presenceService.getOrganizationPresenceCounter(1l));
    }
    @Test
    public void testGetPlacePresenceCounterReturn0PresenceGivenInvalidPlaceId(){
        Mockito.when(redisValOps.get("place:1")).thenReturn(null);
        testPlaceCounter.counter(0);
        Assert.assertEquals(Optional.of(testPlaceCounter), presenceService.getPlacePresenceCounter(1l));
    }
    @Test
    public void testGetPlacePresenceCounterReturnValidPresenceGivenValidPlaceId(){
        Mockito.when(redisValOps.get("place:1")).thenReturn(new Integer(12));
        testPlaceCounter.counter(12);
        Assert.assertEquals(Optional.of(testPlaceCounter), presenceService.getPlacePresenceCounter(1l));
    }
}
