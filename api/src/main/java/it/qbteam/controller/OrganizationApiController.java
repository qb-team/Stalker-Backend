package it.qbteam.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Min;

// import com.google.firebase.FirebaseApp;

import it.qbteam.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
// import org.springframework.web.context.request.NativeWebRequest;

import it.qbteam.api.OrganizationApi;
import it.qbteam.repository.sql.OrganizationRepository;

@Controller
public class OrganizationApiController implements OrganizationApi {
    @Autowired
    private OrganizationRepository orgRepo;

    // @Autowired
    // private FirebaseApp firebaseApp;

    // private boolean checkCompatibility(NativeWebRequest nwRequest, String mediaType) {
    //     boolean compatible = false;
    //     for (MediaType m : MediaType.parseMediaTypes(nwRequest.getHeader("Accept"))) {
    //         System.out.println("Checking compatibility...");
    //         compatible = m.isCompatibleWith(MediaType.valueOf(mediaType));
    //     }
    //     System.out.println("Compatible: " + compatible);
    //     return compatible;
    // }

    /**
     * GET /organization/{organizationId} : Gets the data of a single organization.
     * Gets the data of a single organization.
     *
     * @param organizationId ID of an organization. (required)
     * @return Organization returned successfully. (status code 200) or Organization
     *         not found. (status code 400)
     */
    @Override
    public ResponseEntity<Organization> getOrganization(Long organizationId) {
        Optional<Organization> org = orgRepo.findById(organizationId);

        if (org.isPresent()) {
            return new ResponseEntity<Organization>(org.get(), HttpStatus.OK);
        }

        return new ResponseEntity<Organization>(HttpStatus.BAD_REQUEST);
    }

    /**
     * GET /organization : Returns the list of all organizations. Returns the list
     * of all organizations.
     *
     * @return Organizations returned successfully. (status code 200)
     */
    @Override
    public ResponseEntity<List<Organization>> getOrganizationList() {
        // try {
        //     FirebaseToken token = FirebaseAuth.getInstance(firebaseApp).verifyIdToken(
        //             "eyJhbGciOiJSUzI1NiIsITZiMWM5MjFmYTg2NzcwZjNkNTBjMTJjMTVkNmVhY2E4ZjBkMzUiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vc3RhbGtlci1pbW9sYS1pbmZvcm1hdGljYSIsImF1ZCI6InN0YWxrZXItaW1vbGEtaW5mb3JtYXRpY2EiLCJhdXRoX3RpbWUiOjE1ODQ3MTUxNzgsInVzZXJfaWQiOiJ4QkE1RjB0ajlKaENsSU4ydlZEdThyMElSQXYxIiwic3ViIjoieEJBNUYwdGo5SmhDbElOMnZWRHU4cjBJUkF2MSIsImlhdCI6MTU4NDcxNTE3OCwiZXhwIjoxNTg0NzE4Nzc4LCJlbWFpbCI6ImF6emFsaW50b21tYXNvQGdtYWlsLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJmaXJlYmFzZSI6eyJpZGVudGl0aWVzIjp7ImVtYWlsIjpbImF6emFsaW50b21tYXNvQGdtYWlsLmNvbSJdfSwic2lnbl9pbl9wcm92aWRlciI6InBhc3N3b3JkIn19.Ax99ZaE374dT6D_UKC_TpvwbhfOHuPuRoTdz3v6o5pYHe9dSNn1po4-Up3t9XQSgYsm1OLvTCY_MdQEZlID5v3EY5MEZ_5bURXFzLhsp8sk_HReMbMWaUBCSEfHu_zcvVoJI5ZWlKEMEu4JpaghVmpQ9IUG_ArWx6LRu349ePh-g9OEiMLS6xufdpw0jy6TEh2AT7EWYuwyDLrlC5BTVhQTFSNDUHZEKxJe3AXjiQU4iL_9AykGktGWqylApft5EK1QRmlGZHQCxsNlEZdtAq6K4A57xObk6gWcclivBaT87ulAbcZUg5KbNMgAQ9y0PKeS81q8B5z7n-opaLx6b3w");

        //     System.out.println("EMAIL: " + token.getEmail());

        // } catch (FirebaseAuthException e) {
        //     e.printStackTrace();
        // }
        List<Organization> orgList = new ArrayList<Organization>();
        orgRepo.findAll().forEach(orgList::add);
        
        return new ResponseEntity<List<Organization>>(orgList, HttpStatus.OK);
    }

    /**
     * POST /organization/{organizationId}/requestdeletion : Sends a deletion request to the system. The request will be examined by Stalker administrators.
     * Sends a deletion request to the system.  The request will be examined by Stalker administrators. Only web-app administrators can access this end-point.
     *
     * @param organizationId ID of an organization. The administrator must have at least owner permission to the organization. (required)
     * @param requestReason  Request reason for the deletion request. (required)
     * @return Request sent successfully. Nothing gets returned. (status code 204)
     * or The administrator is not authenticated. Nothing gets returned. (status code 401)
     * or Users or administrator with viewer or manager permission cannot have access. Nothing gets returned. (status code 403)
     * or The organization could not be found. Nothing gets returned. (status code 404)
     */
    @Override
    public ResponseEntity<Void> requestDeletionOfOrganization(@Min(1L) Long organizationId, String requestReason) {
        return null;
    }

    /**
     * PATCH /organization/{organizationId}/trackingArea : Updates the coordinates of the tracking area of an organization.
     * Updates the coordinates of the tracking area of an organization. Only web-app administrators can access this end-point.
     *
     * @param organizationId ID of an organization. The administrator must have at least manager permission to the organization. (required)
     * @param trackingArea   JSON representation of a tracking trackingArea. (required)
     * @return The tracking area of the organization was updated successfully. The organization gets returned. (status code 200)
     * or The new tracking area does not respect the area constraints for the organization. Nothing gets returned. (status code 400)
     * or The administrator is not authenticated. Nothing gets returned. (status code 401)
     * or Users or administrator with viewer permission cannot have access. Nothing gets returned. (status code 403)
     * or The organization could not be found. Nothing gets returned. (status code 404)
     */
    @Override
    public ResponseEntity<Organization> updateOrganizationTrackingArea(@Min(1L) Long organizationId, String trackingArea) {
        return null;
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
