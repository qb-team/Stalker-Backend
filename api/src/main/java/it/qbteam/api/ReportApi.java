/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (4.2.3).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package it.qbteam.api;

import it.qbteam.model.TimePerUserReport;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Validated
@Api(value = "report", description = "the report API")
public interface ReportApi {

    /**
     * GET /report/organization/{organizationId} : Gets the report of total time spent per user inside the organization.
     * Gets the report of total time spent by each user inside the organization. Only web-app admininistrators can access this end-point.
     *
     * @param organizationId ID of the organization. The viewer admininistrator must have permissions for this organization. (required)
     * @return Report of time spent in the organization per user returned successfully. (status code 200)
     *         or Report is empty. Nothing gets returned. (status code 204)
     *         or The administrator is not authenticated. Nothing gets returned. (status code 401)
     *         or Users cannot have access. Nothing gets returned. (status code 403)
     *         or The organization could not be found. Nothing gets returned. (status code 404)
     */
    @ApiOperation(value = "Gets the report of total time spent per user inside the organization.", nickname = "getTimePerUserReport", notes = "Gets the report of total time spent by each user inside the organization. Only web-app admininistrators can access this end-point.", response = TimePerUserReport.class, responseContainer = "List", authorizations = {
        @Authorization(value = "bearerAuth")
    }, tags={ "organization","report", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Report of time spent in the organization per user returned successfully.", response = TimePerUserReport.class, responseContainer = "List"),
        @ApiResponse(code = 204, message = "Report is empty. Nothing gets returned."),
        @ApiResponse(code = 401, message = "The administrator is not authenticated. Nothing gets returned."),
        @ApiResponse(code = 403, message = "Users cannot have access. Nothing gets returned."),
        @ApiResponse(code = 404, message = "The organization could not be found. Nothing gets returned.") })
    @RequestMapping(value = "/report/organization/{organizationId}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<TimePerUserReport>> getTimePerUserReport(@Min(1L)@ApiParam(value = "ID of the organization. The viewer admininistrator must have permissions for this organization.",required=true) @PathVariable("organizationId") Long organizationId);

}
