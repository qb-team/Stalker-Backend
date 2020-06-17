package it.qbteam.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Data for requesting information about users of an organization.
 */
@ApiModel(description = "Data for requesting information about users of an organization.")

public class OrganizationAuthenticationServerRequest   {
  @JsonProperty("organizationCredentials")
  private OrganizationAuthenticationServerCredentials organizationCredentials;

  @JsonProperty("organizationId")
  private Long organizationId;

  @JsonProperty("orgAuthServerIds")
  @Valid
  private List<String> orgAuthServerIds = new ArrayList<>();

  public OrganizationAuthenticationServerRequest organizationCredentials(OrganizationAuthenticationServerCredentials organizationCredentials) {
    this.organizationCredentials = organizationCredentials;
    return this;
  }

  /**
   * Get organizationCredentials
   * @return organizationCredentials
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public OrganizationAuthenticationServerCredentials getOrganizationCredentials() {
    return organizationCredentials;
  }

  public void setOrganizationCredentials(OrganizationAuthenticationServerCredentials organizationCredentials) {
    this.organizationCredentials = organizationCredentials;
  }

  public OrganizationAuthenticationServerRequest organizationId(Long organizationId) {
    this.organizationId = organizationId;
    return this;
  }

  /**
   * Unique identifier of an organization.
   * minimum: 1
   * @return organizationId
  */
  @ApiModelProperty(required = true, value = "Unique identifier of an organization.")
  @NotNull

  @Min(1L)
  public Long getOrganizationId() {
    return organizationId;
  }

  public void setOrganizationId(Long organizationId) {
    this.organizationId = organizationId;
  }

  public OrganizationAuthenticationServerRequest orgAuthServerIds(List<String> orgAuthServerIds) {
    this.orgAuthServerIds = orgAuthServerIds;
    return this;
  }

  public OrganizationAuthenticationServerRequest addOrgAuthServerIdsItem(String orgAuthServerIdsItem) {
    this.orgAuthServerIds.add(orgAuthServerIdsItem);
    return this;
  }

  /**
   * List of ids of users to get their information.
   * @return orgAuthServerIds
  */
  @ApiModelProperty(required = true, value = "List of ids of users to get their information.")
  @NotNull

  @Size(min=1) 
  public List<String> getOrgAuthServerIds() {
    return orgAuthServerIds;
  }

  public void setOrgAuthServerIds(List<String> orgAuthServerIds) {
    this.orgAuthServerIds = orgAuthServerIds;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrganizationAuthenticationServerRequest organizationAuthenticationServerRequest = (OrganizationAuthenticationServerRequest) o;
    return Objects.equals(this.organizationCredentials, organizationAuthenticationServerRequest.organizationCredentials) &&
        Objects.equals(this.organizationId, organizationAuthenticationServerRequest.organizationId) &&
        Objects.equals(this.orgAuthServerIds, organizationAuthenticationServerRequest.orgAuthServerIds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(organizationCredentials, organizationId, orgAuthServerIds);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationAuthenticationServerRequest {\n");
    
    sb.append("    organizationCredentials: ").append(toIndentedString(organizationCredentials)).append("\n");
    sb.append("    organizationId: ").append(toIndentedString(organizationId)).append("\n");
    sb.append("    orgAuthServerIds: ").append(toIndentedString(orgAuthServerIds)).append("\n");
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

