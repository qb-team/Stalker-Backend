package it.qbteam.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;

/**
 * Request object for creating a new administrator account and binding it to the organization or just binding an already existent administrator.
 */
@ApiModel(description = "Request object for creating a new administrator account and binding it to the organization or just binding an already existent administrator.")

public class AdministratorBindingRequest   {
  @JsonProperty("organizationId")
  private Long organizationId;

  @JsonProperty("orgAuthServerId")
  private String orgAuthServerId;

  @JsonProperty("mail")
  private String mail;

  @JsonProperty("password")
  private String password;

  @JsonProperty("permission")
  private Integer permission;

  public AdministratorBindingRequest organizationId(Long organizationId) {
    this.organizationId = organizationId;
    return this;
  }

  /**
   * Unique identifier of the organization the administrator is part of.
   * @return organizationId
  */
  @ApiModelProperty(required = true, value = "Unique identifier of the organization the administrator is part of.")
  @NotNull

  @Min(1L)
  public Long getOrganizationId() {
    return organizationId;
  }

  public void setOrganizationId(Long organizationId) {
    this.organizationId = organizationId;
  }

  public AdministratorBindingRequest orgAuthServerId(String orgAuthServerId) {
    this.orgAuthServerId = orgAuthServerId;
    return this;
  }

  /**
   * Administrator unique identifier from the authentication server of the organization.
   * @return orgAuthServerId
  */
  @ApiModelProperty(value = "Administrator unique identifier from the authentication server of the organization.")

  @Size(max=256) 
  public String getOrgAuthServerId() {
    return orgAuthServerId;
  }

  public void setOrgAuthServerId(String orgAuthServerId) {
    this.orgAuthServerId = orgAuthServerId;
  }

  public AdministratorBindingRequest mail(String mail) {
    this.mail = mail;
    return this;
  }

  /**
   * Administrator's e-mail address.
   * @return mail
  */
  @ApiModelProperty(required = true, value = "Administrator's e-mail address.")
  @NotNull

  @Size(max=254) @Email
  public String getMail() {
    return mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }

  public AdministratorBindingRequest password(String password) {
    this.password = password;
    return this;
  }

  /**
   * Administrator's new password.
   * @return password
  */
  @ApiModelProperty(value = "Administrator's new password.")

  @Size(min=6) 
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public AdministratorBindingRequest permission(Integer permission) {
    this.permission = permission;
    return this;
  }

  /**
   * What can or cannot do an organization's administrator. The permission levels are: - Owner: 3 (higher level) - Manager: 2 - Viewer: 1 (lowest level)
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AdministratorBindingRequest administratorBindingRequest = (AdministratorBindingRequest) o;
    return Objects.equals(this.organizationId, administratorBindingRequest.organizationId) &&
        Objects.equals(this.orgAuthServerId, administratorBindingRequest.orgAuthServerId) &&
        Objects.equals(this.mail, administratorBindingRequest.mail) &&
        Objects.equals(this.password, administratorBindingRequest.password) &&
        Objects.equals(this.permission, administratorBindingRequest.permission);
  }

  @Override
  public int hashCode() {
    return Objects.hash(organizationId, orgAuthServerId, mail, password, permission);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AdministratorBindingRequest {\n");
    
    sb.append("    organizationId: ").append(toIndentedString(organizationId)).append("\n");
    sb.append("    orgAuthServerId: ").append(toIndentedString(orgAuthServerId)).append("\n");
    sb.append("    mail: ").append(toIndentedString(mail)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
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

