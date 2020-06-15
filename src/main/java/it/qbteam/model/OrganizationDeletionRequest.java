package it.qbteam.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Request object for creating a new deletion request for the organization. The request will be analyzed by a Stalker administrator.
 */
@ApiModel(description = "Request object for creating a new deletion request for the organization. The request will be analyzed by a Stalker administrator.")
@Entity
public class OrganizationDeletionRequest   {
  @Id
  @JsonProperty("organizationId")
  private Long organizationId;

  @JsonProperty("requestReason")
  @Column(length = 512)
  private String requestReason;

  @JsonProperty("administratorId")
  @Column(length = 256)
  private String administratorId;

  public OrganizationDeletionRequest organizationId(Long organizationId) {
    this.organizationId = organizationId;
    return this;
  }

  /**
   * Unique identifier of the organization.
   * minimum: 1
   * @return organizationId
  */
  @ApiModelProperty(required = true, value = "Unique identifier of the organization.")
  @NotNull

  @Min(1L)
  public Long getOrganizationId() {
    return organizationId;
  }

  public void setOrganizationId(Long organizationId) {
    this.organizationId = organizationId;
  }

  public OrganizationDeletionRequest requestReason(String requestReason) {
    this.requestReason = requestReason;
    return this;
  }

  /**
   * Request reason for the deletion request.
   * @return requestReason
  */
  @ApiModelProperty(required = true, value = "Request reason for the deletion request.")
  @NotNull

  @Size(min=100, max=512)
  public String getRequestReason() {
    return requestReason;
  }

  public void setRequestReason(String requestReason) {
    this.requestReason = requestReason;
  }

  public OrganizationDeletionRequest administratorId(String administratorId) {
    this.administratorId = administratorId;
    return this;
  }

  /**
   * Authentication service's administrator unique identifier.
   * @return administratorId
  */
  @ApiModelProperty(value = "Authentication service's administrator unique identifier.")

  @Size(max=256)
  public String getAdministratorId() {
    return administratorId;
  }

  public void setAdministratorId(String administratorId) {
    this.administratorId = administratorId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrganizationDeletionRequest organizationDeletionRequest = (OrganizationDeletionRequest) o;
    return Objects.equals(this.organizationId, organizationDeletionRequest.organizationId) &&
        Objects.equals(this.requestReason, organizationDeletionRequest.requestReason) &&
        Objects.equals(this.administratorId, organizationDeletionRequest.administratorId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(organizationId, requestReason, administratorId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationDeletionRequest {\n");
    
    sb.append("    organizationId: ").append(toIndentedString(organizationId)).append("\n");
    sb.append("    requestReason: ").append(toIndentedString(requestReason)).append("\n");
    sb.append("    administratorId: ").append(toIndentedString(administratorId)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

