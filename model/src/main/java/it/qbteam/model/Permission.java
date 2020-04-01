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
import javax.persistence.IdClass;
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
 * What can or cannot do an organization&#39;s administrator.
 */
@ApiModel(description = "What can or cannot do an organization's administrator.")
@Entity
@IdClass(PermissionId.class)
public class Permission   {
  @Id
  @JsonProperty("administratorId")
  private String administratorId;

  @Id
  @JsonProperty("organizationId")
  private Long organizationId;

  @JsonProperty("permission")
  private Integer permission;

  @JsonProperty("nominatedBy")
  private String nominatedBy;

  public Permission administratorId(String administratorId) {
    this.administratorId = administratorId;
    return this;
  }

  /**
   * Authentication service's administrator unique identifier.
   * @return administratorId
  */
  @ApiModelProperty(required = true, value = "Authentication service's administrator unique identifier.")
  @NotNull


  public String getAdministratorId() {
    return administratorId;
  }

  public void setAdministratorId(String administratorId) {
    this.administratorId = administratorId;
  }

  public Permission organizationId(Long organizationId) {
    this.organizationId = organizationId;
    return this;
  }

  /**
   * Unique identifier of the organization the administrator is part of.
   * @return organizationId
  */
  @ApiModelProperty(required = true, value = "Unique identifier of the organization the administrator is part of.")
  @NotNull


  public Long getOrganizationId() {
    return organizationId;
  }

  public void setOrganizationId(Long organizationId) {
    this.organizationId = organizationId;
  }

  public Permission permission(Integer permission) {
    this.permission = permission;
    return this;
  }

  /**
   * What can or cannot do an organization's administrator. The permission levels are: - Owner: 3 (higher level) - Manager: 2 - Viewer: 1 (lowest level)
   * minimum: 1
   * maximum: 3
   * @return permission
  */
  @ApiModelProperty(required = true, value = "What can or cannot do an organization's administrator. The permission levels are: - Owner: 3 (higher level) - Manager: 2 - Viewer: 1 (lowest level)")
  @NotNull

@Min(1) @Max(3) 
  public Integer getPermission() {
    return permission;
  }

  public void setPermission(Integer permission) {
    this.permission = permission;
  }

  public Permission nominatedBy(String nominatedBy) {
    this.nominatedBy = nominatedBy;
    return this;
  }

  /**
   * admininistratorId of the owner admininistrator who nominated the current admininistrator.
   * @return nominatedBy
  */
  @ApiModelProperty(required = true, value = "admininistratorId of the owner admininistrator who nominated the current admininistrator.")
  @NotNull


  public String getNominatedBy() {
    return nominatedBy;
  }

  public void setNominatedBy(String nominatedBy) {
    this.nominatedBy = nominatedBy;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Permission permission = (Permission) o;
    return Objects.equals(this.administratorId, permission.administratorId) &&
        Objects.equals(this.organizationId, permission.organizationId) &&
        Objects.equals(this.permission, permission.permission) &&
        Objects.equals(this.nominatedBy, permission.nominatedBy);
  }

  @Override
  public int hashCode() {
    return Objects.hash(administratorId, organizationId, permission, nominatedBy);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Permission {\n");
    
    sb.append("    administratorId: ").append(toIndentedString(administratorId)).append("\n");
    sb.append("    organizationId: ").append(toIndentedString(organizationId)).append("\n");
    sb.append("    permission: ").append(toIndentedString(permission)).append("\n");
    sb.append("    nominatedBy: ").append(toIndentedString(nominatedBy)).append("\n");
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

