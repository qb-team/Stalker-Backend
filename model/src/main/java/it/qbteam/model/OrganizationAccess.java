package it.qbteam.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.OffsetDateTime;
import org.openapitools.jackson.nullable.JsonNullable;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Access to an organization.
 */
@ApiModel(description = "Access to an organization.")
@Entity
public class OrganizationAccess   {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonProperty("id")
  private Long id;

  @JsonProperty("entranceTimestamp")
  private OffsetDateTime entranceTimestamp;

  @JsonProperty("exitTimestamp")
  private OffsetDateTime exitTimestamp;

  @JsonProperty("organizationId")
  private Long organizationId;

  @JsonProperty("orgAuthServerId")
  private String orgAuthServerId;

  public OrganizationAccess id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public OrganizationAccess entranceTimestamp(OffsetDateTime entranceTimestamp) {
    this.entranceTimestamp = entranceTimestamp;
    return this;
  }

  /**
   * Date and time of the moment in which the user entered the place.
   * @return entranceTimestamp
  */
  @ApiModelProperty(required = true, value = "Date and time of the moment in which the user entered the place.")
  @NotNull

  @Valid

  public OffsetDateTime getEntranceTimestamp() {
    return entranceTimestamp;
  }

  public void setEntranceTimestamp(OffsetDateTime entranceTimestamp) {
    this.entranceTimestamp = entranceTimestamp;
  }

  public OrganizationAccess exitTimestamp(OffsetDateTime exitTimestamp) {
    this.exitTimestamp = exitTimestamp;
    return this;
  }

  /**
   * Date and time of the moment in which the user left the place.
   * @return exitTimestamp
  */
  @ApiModelProperty(required = true, value = "Date and time of the moment in which the user left the place.")

  @Valid

  public OffsetDateTime getExitTimestamp() {
    return exitTimestamp;
  }

  public void setExitTimestamp(OffsetDateTime exitTimestamp) {
    this.exitTimestamp = exitTimestamp;
  }

  public OrganizationAccess organizationId(Long organizationId) {
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

  public OrganizationAccess orgAuthServerId(String orgAuthServerId) {
    this.orgAuthServerId = orgAuthServerId;
    return this;
  }

  /**
   * User unique identifier from the authentication server of the organization.
   * @return orgAuthServerId
  */
  @ApiModelProperty(value = "User unique identifier from the authentication server of the organization.")


  public String getOrgAuthServerId() {
    return orgAuthServerId;
  }

  public void setOrgAuthServerId(String orgAuthServerId) {
    this.orgAuthServerId = orgAuthServerId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrganizationAccess organizationAccess = (OrganizationAccess) o;
    return Objects.equals(this.id, organizationAccess.id) &&
        Objects.equals(this.entranceTimestamp, organizationAccess.entranceTimestamp) &&
        Objects.equals(this.exitTimestamp, organizationAccess.exitTimestamp) &&
        Objects.equals(this.organizationId, organizationAccess.organizationId) &&
        Objects.equals(this.orgAuthServerId, organizationAccess.orgAuthServerId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, entranceTimestamp, exitTimestamp, organizationId, orgAuthServerId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationAccess {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    entranceTimestamp: ").append(toIndentedString(entranceTimestamp)).append("\n");
    sb.append("    exitTimestamp: ").append(toIndentedString(exitTimestamp)).append("\n");
    sb.append("    organizationId: ").append(toIndentedString(organizationId)).append("\n");
    sb.append("    orgAuthServerId: ").append(toIndentedString(orgAuthServerId)).append("\n");
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

