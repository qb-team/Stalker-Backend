package it.qbteam.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.qbteam.model.Access;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Access to an organization made with the authenticated trackingMode.
 */
@ApiModel(description = "Access to an organization made with the authenticated trackingMode.")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-02-29T20:29:04.115+01:00[Europe/Berlin]")

public class OrganizationAnonymousAccess   {
  @JsonProperty("access")
  private Access access;

  @JsonProperty("organizationId")
  private Long organizationId;

  public OrganizationAnonymousAccess access(Access access) {
    this.access = access;
    return this;
  }

  /**
   * Get access
   * @return access
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public Access getAccess() {
    return access;
  }

  public void setAccess(Access access) {
    this.access = access;
  }

  public OrganizationAnonymousAccess organizationId(Long organizationId) {
    this.organizationId = organizationId;
    return this;
  }

  /**
   * Unique identifier of the organization in which the user had access.
   * @return organizationId
  */
  @ApiModelProperty(required = true, value = "Unique identifier of the organization in which the user had access.")
  @NotNull


  public Long getOrganizationId() {
    return organizationId;
  }

  public void setOrganizationId(Long organizationId) {
    this.organizationId = organizationId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrganizationAnonymousAccess organizationAnonymousAccess = (OrganizationAnonymousAccess) o;
    return Objects.equals(this.access, organizationAnonymousAccess.access) &&
        Objects.equals(this.organizationId, organizationAnonymousAccess.organizationId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(access, organizationId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationAnonymousAccess {\n");
    
    sb.append("    access: ").append(toIndentedString(access)).append("\n");
    sb.append("    organizationId: ").append(toIndentedString(organizationId)).append("\n");
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

