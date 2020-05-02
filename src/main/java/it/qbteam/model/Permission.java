package it.qbteam.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Transient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import javax.validation.constraints.Email;

/**
 * What can or cannot do an organization&#39;s administrator.
 */
@ApiModel(description = "What can or cannot do an organization's administrator.")
@Entity
@IdClass(PermissionId.class)
public class Permission   {
  @Id
  @Column(length = 256)
  @JsonProperty("administratorId")
  private String administratorId;

  @Column(length = 256)
  @JsonProperty("orgAuthServerId")
  private String orgAuthServerId;

  @JsonProperty("mail")
  @Transient
  private String mail;

  @Id
  @JsonProperty("organizationId")
  private Long organizationId;

  @JsonProperty("permission")
  private Integer permission;

  @Column(length = 256)
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

  public Permission orgAuthServerId(String orgAuthServerId) {
    this.orgAuthServerId = orgAuthServerId;
    return this;
  }

  /**
   * Administrator unique identifier from the authentication server of the organization.
   * @return orgAuthServerId
  */
  @ApiModelProperty(value = "Administrator unique identifier from the authentication server of the organization.")


  public String getOrgAuthServerId() {
    return orgAuthServerId;
  }

  public void setOrgAuthServerId(String orgAuthServerId) {
    this.orgAuthServerId = orgAuthServerId;
  }

  public Permission mail(String mail) {
    this.mail = mail;
    return this;
  }

  /**
   * Administrator's e-mail address.
   * @return mail
  */
  @ApiModelProperty(value = "Administrator's e-mail address.")

  @Email
  public String getMail() {
    return mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
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
   * administratorId of the owner administrator who nominated the current administrator.
   * @return nominatedBy
  */
  @ApiModelProperty(value = "administratorId of the owner administrator who nominated the current administrator.")


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
        Objects.equals(this.orgAuthServerId, permission.orgAuthServerId) &&
        Objects.equals(this.mail, permission.mail) &&
        Objects.equals(this.permission, permission.permission) &&
        Objects.equals(this.nominatedBy, permission.nominatedBy);
  }

  @Override
  public int hashCode() {
    return Objects.hash(administratorId, organizationId, orgAuthServerId, mail, permission, nominatedBy);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Permission {\n");
    
    sb.append("    administratorId: ").append(toIndentedString(administratorId)).append("\n");
    sb.append("    organizationId: ").append(toIndentedString(organizationId)).append("\n");
    sb.append("    orgAuthServerId: ").append(toIndentedString(orgAuthServerId)).append("\n");
    sb.append("    mail: ").append(toIndentedString(mail)).append("\n");
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