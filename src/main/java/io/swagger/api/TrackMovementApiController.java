package io.swagger.api;

import io.swagger.model.OrganizationAnonymousMovement;
import io.swagger.model.OrganizationAuthenticatedMovement;
import io.swagger.model.PlaceAnonymousMovement;
import io.swagger.model.PlaceAuthenticatedMovement;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-02-27T15:59:20.699Z[GMT]")
@Controller
public class TrackMovementApiController implements TrackMovementApi {

    private static final Logger log = LoggerFactory.getLogger(TrackMovementApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public TrackMovementApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> trackAnonymousMovementInOrganization(@ApiParam(value = ""  )  @Valid @RequestBody OrganizationAnonymousMovement body
) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> trackAnonymousMovementInPlace(@ApiParam(value = ""  )  @Valid @RequestBody PlaceAnonymousMovement body
) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> trackAuthenticatedMovementInOrganization(@ApiParam(value = ""  )  @Valid @RequestBody OrganizationAuthenticatedMovement body
) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> trackAuthenticatedMovementInPlace(@ApiParam(value = ""  )  @Valid @RequestBody PlaceAuthenticatedMovement body
) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

}
