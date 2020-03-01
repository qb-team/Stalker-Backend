package it.qbteam.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
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

  /**
   * What can or cannot do an organization's administrator. Owner is the highest level of permissions while viewer is the lowest.
   */
  public enum PermissionEnum {
    OWNER("owner"),
    
    MANAGER("manager"),
    
    VIEWER("viewer");

    private String value;

    PermissionEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static PermissionEnum fromValue(String value) {
      for (PermissionEnum b : PermissionEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("permission")
  private PermissionEnum permission;

  public Permission administratorId(String administratorId) {
    this.administratorId = administratorId;
    return this;
  }

  /**
   * Authentication service's administrator unique identifier.
   * @return administratorId
  */
  @ApiModelProperty(value = "Authentication service's administrator unique identifier.")


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

  public Permission permission(PermissionEnum permission) {
    this.permission = permission;
    return this;
  }

  /**
   * What can or cannot do an organization's administrator. Owner is the highest level of permissions while viewer is the lowest.
   * @return permission
  */
  @ApiModelProperty(required = true, value = "What can or cannot do an organization's administrator. Owner is the highest level of permissions while viewer is the lowest.")
  @NotNull


  public PermissionEnum getPermission() {
    return permission;
  }

  public void setPermission(PermissionEnum permission) {
    this.permission = permission;
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
        Objects.equals(this.permission, permission.permission);
  }

  @Override
  public int hashCode() {
    return Objects.hash(administratorId, organizationId, permission);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Permission {\n");
    
    sb.append("    administratorId: ").append(toIndentedString(administratorId)).append("\n");
    sb.append("    organizationId: ").append(toIndentedString(organizationId)).append("\n");
    sb.append("    permission: ").append(toIndentedString(permission)).append("\n");
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

