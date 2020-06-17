package it.qbteam.serviceimpl;

import it.qbteam.model.OrganizationMovement;
import it.qbteam.model.PlaceMovement;
import it.qbteam.persistence.movementtracker.publisher.OrganizationMovementPublisher;
import it.qbteam.persistence.movementtracker.publisher.PlaceMovementPublisher;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
public class MovementServiceImplTest {
    @MockBean
    private OrganizationMovementPublisher organizationMovementPublisher;
    @MockBean
    private PlaceMovementPublisher placeMovementPublisher;

    @TestConfiguration
    static class MovementServiceImplConfiguration{
        @Bean
        public MovementServiceImpl movementService(OrganizationMovementPublisher organizationMovementPublisher, PlaceMovementPublisher placeMovementPublisher) {
            return new MovementServiceImpl(organizationMovementPublisher, placeMovementPublisher);
        }

    }
    @Autowired
    private MovementServiceImpl movementService;

    @Test
    public void testTrackMovementInOrganizationReturnOptionalEmptyGivenInvalidOrganizationMovement() {
        Assert.assertEquals(Optional.empty(), movementService.trackMovementInOrganization(null));
        Assert.assertEquals(Optional.empty(), movementService.trackMovementInOrganization(new OrganizationMovement().movementType(0)));
        Assert.assertEquals(Optional.empty(), movementService.trackMovementInOrganization(new OrganizationMovement().movementType(-1).exitToken(null)));
        Assert.assertEquals(Optional.empty(), movementService.trackMovementInOrganization(new OrganizationMovement().movementType(-1)));
    }
    @Test
    public void testTrackMovementInOrganizationReturnValidOptionalOfOrganizationMovement(){
        Assert.assertNotNull(movementService.trackMovementInOrganization(new OrganizationMovement().movementType(1)).get().getExitToken());
        Mockito.doNothing().when(organizationMovementPublisher).publish(any(OrganizationMovement.class));
        Assert.assertNotNull(movementService.trackMovementInOrganization(new OrganizationMovement().movementType(-1).exitToken("provaexittoken")).get());
    }
    @Test
    public void testTrackMovementInPlaceReturnOptionalEmptyGivenInvalidPlaceMovement(){
        Assert.assertEquals(Optional.empty(), movementService.trackMovementInPlace(null));
        Assert.assertEquals(Optional.empty(), movementService.trackMovementInPlace(new PlaceMovement().movementType(0)));
        Assert.assertEquals(Optional.empty(), movementService.trackMovementInPlace(new PlaceMovement().movementType(-1).exitToken(null)));
        Assert.assertEquals(Optional.empty(), movementService.trackMovementInPlace(new PlaceMovement().movementType(-1)));
    }
    @Test
    public void testTrackMovementInPlaceReturnValidOptionalOfPlaceMovement(){
        Assert.assertNotNull(movementService.trackMovementInPlace(new PlaceMovement().movementType(1)).get().getExitToken());
        Assert.assertNotNull(movementService.trackMovementInPlace(new PlaceMovement().movementType(-1).exitToken("provaexittoken")).get());
    }
}
