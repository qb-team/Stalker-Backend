package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.model.Access;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Access to an organization made with the authenticated trackingMode.
 */
@ApiModel(description = "Access to an organization made with the authenticated trackingMode.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-02-27T15:59:20.699Z[GMT]")
public class OrganizationAuthenticatedAccess   {
  @JsonProperty("access")
  private Access access = null;

  @JsonProperty("organizationId")
  private Long organizationId = null;

  @JsonProperty("ldapId")
  private Long ldapId = null;

  public OrganizationAuthenticatedAccess access(Access access) {
    this.access = access;
    return this;
  }

  /**
   * Get access
   * @return access
  **/
  @ApiModelProperty(required = true, value = "")
      @NotNull

    @Valid
    public Access getAccess() {
    return access;
  }

  public void setAccess(Access access) {
    this.access = access;
  }

  public OrganizationAuthenticatedAccess organizationId(Long organizationId) {
    this.organizationId = organizationId;
    return this;
  }

  /**
   * Unique identifier of the organization in which the user had access.
   * @return organizationId
  **/
  @ApiModelProperty(required = true, value = "Unique identifier of the organization in which the user had access.")
      @NotNull

    public Long getOrganizationId() {
    return organizationId;
  }

  public void setOrganizationId(Long organizationId) {
    this.organizationId = organizationId;
  }

  public OrganizationAuthenticatedAccess ldapId(Long ldapId) {
    this.ldapId = ldapId;
    return this;
  }

  /**
   * Organization LDAP server's user unique identifier.
   * @return ldapId
  **/
  @ApiModelProperty(required = true, value = "Organization LDAP server's user unique identifier.")
      @NotNull

    public Long getLdapId() {
    return ldapId;
  }

  public void setLdapId(Long ldapId) {
    this.ldapId = ldapId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrganizationAuthenticatedAccess organizationAuthenticatedAccess = (OrganizationAuthenticatedAccess) o;
    return Objects.equals(this.access, organizationAuthenticatedAccess.access) &&
        Objects.equals(this.organizationId, organizationAuthenticatedAccess.organizationId) &&
        Objects.equals(this.ldapId, organizationAuthenticatedAccess.ldapId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(access, organizationId, ldapId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationAuthenticatedAccess {\n");
    
    sb.append("    access: ").append(toIndentedString(access)).append("\n");
    sb.append("    organizationId: ").append(toIndentedString(organizationId)).append("\n");
    sb.append("    ldapId: ").append(toIndentedString(ldapId)).append("\n");
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
