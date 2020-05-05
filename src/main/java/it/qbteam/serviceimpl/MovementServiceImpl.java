package it.qbteam.serviceimpl;

import it.qbteam.movementtracker.publisher.PlaceMovementPublisher;
import it.qbteam.movementtracker.publisher.OrganizationMovementPublisher;
import it.qbteam.model.OrganizationMovement;
import it.qbteam.model.PlaceMovement;
import it.qbteam.service.MovementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;
import java.util.Random;

@Service
public class MovementServiceImpl implements MovementService {

    private OrganizationMovementPublisher organizationMovementPublisher;
    private PlaceMovementPublisher placeMovementPublisher;

    @Autowired
    public MovementServiceImpl(OrganizationMovementPublisher organizationMovementPublisher, PlaceMovementPublisher placeMovementPublisher) {
        this.organizationMovementPublisher = organizationMovementPublisher;
        this.placeMovementPublisher = placeMovementPublisher;
    }

    @Override
    public Optional<OrganizationMovement> trackMovementInOrganization(OrganizationMovement organizationMovement) {
        if(organizationMovement == null) {
            return Optional.empty();
        }

        if(organizationMovement.getMovementType() == 1) {
            organizationMovement.setExitToken(generateExitToken());
        } else if(organizationMovement.getMovementType() != -1 || organizationMovement.getExitToken() == null) {
            return Optional.empty();
        }

        organizationMovementPublisher.publish(organizationMovement);

        return Optional.of(organizationMovement);
    }

    @Override
    public Optional<PlaceMovement> trackMovementInPlace(PlaceMovement placeMovement) {
        if(placeMovement == null) {
            return Optional.empty();
        }

        if(placeMovement.getMovementType() == 1) {
            placeMovement.setExitToken(generateExitToken());
        } else if(placeMovement.getMovementType() != -1 || placeMovement.getExitToken() == null) {
            return Optional.empty();
        }
        
        placeMovementPublisher.publish(placeMovement);

        return Optional.of(placeMovement);
    }

    /**
     * Alphanumerical string token generator.
     * From https://www.baeldung.com/java-random-string
     * and https://www.baeldung.com/java-secure-random.
     * 
     * @return randomly generated alphanumerical string
     */
    private String generateExitToken() {
        int leftLimit = 48; // ASCII index for character '0'
        int rightLimit = 122; // ASCII index for character 'z'
        int targetStringLength = 16;
        Random random = new SecureRandom();
     
        String generatedString = random.ints(leftLimit, rightLimit + 1)
          .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)) // only characters in [0-9a-z]
          .limit(targetStringLength)
          .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
          .toString();
     
        System.out.println("exitToken: " + generatedString);

        return generatedString;
    }
}
