package it.qbteam.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.NativeWebRequest;

import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import it.qbteam.api.OrganizationApi;
import it.qbteam.model.Organization;
import it.qbteam.repository.OrganizationRepository;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Controller
public class OrganizationApiController implements OrganizationApi {
    @Autowired
    private OrganizationRepository orgRepo;

    private boolean checkCompatibility(NativeWebRequest nwRequest, String mediaType) {
        boolean compatible = false;
        for (MediaType m : MediaType.parseMediaTypes(nwRequest.getHeader("Accept"))) {
            System.out.println("Checking compatibility...");
            compatible = m.isCompatibleWith(MediaType.valueOf(mediaType));
        }
        System.out.println("Compatible: " + compatible);
        return compatible;
    }
    /**
     * GET /organization/{organizationId} : Gets the data of a single organization.
     * Gets the data of a single organization.
     *
     * @param organizationId ID of an organization. (required)
     * @return Organization returned successfully. (status code 200)
     * or Organization not found. (status code 400)
     */
    @Override
    public ResponseEntity<Organization> getOrganizationById(Long organizationId) {
        Optional<NativeWebRequest> nwr = getRequest();
        Optional<Organization> org;
        System.out.println("Get ID before if");
        if(nwr.isPresent() && checkCompatibility(getRequest().get(), "application/json"))
        {
            System.out.println("Get ID inside if");
            org = orgRepo.findById(organizationId);
            if(org.isPresent())
                return new ResponseEntity<Organization>(org.get(), HttpStatus.OK);
        }
        System.out.println("Get ID after if");
        return new ResponseEntity<Organization>(HttpStatus.BAD_REQUEST);
    }

    /**
     * GET /organization : Returns the list of all organizations.
     * Returns the list of all organizations.
     *
     * @return Organizations returned successfully. (status code 200)
     */
    @Override
    public ResponseEntity<List<Organization>> getOrganizationList() {
        Optional<NativeWebRequest> nwr = getRequest();
        List<Organization> orgList = new ArrayList<Organization>();
        System.out.println("Get List before if");
        if(nwr.isPresent() && checkCompatibility(nwr.get(), "application/json"))
        {
            System.out.println("Get List inside if");
            orgRepo.findAll().forEach(orgList::add);
        }
        System.out.println("Get List after if" + nwr.isPresent());
        return new ResponseEntity<List<Organization>>(orgList, HttpStatus.OK);
    }

    /**
     * PUT /organization/{organizationId} : Updates one or more properties of a single organization.
     * Updates one or more properties of a single organization.
     *
     * @param organizationId ID of an organization. (required)
     * @param organization   (required)
     * @return Organization updated successfully. (status code 200)
     * or Invalid organizationId supplied. (status code 400)
     * or Organization not found. (status code 405)
     */
    @Override
    public ResponseEntity<Organization> updateOrganization(Long organizationId, @Valid Organization organization) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}