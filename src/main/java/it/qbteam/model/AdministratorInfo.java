package it.qbteam.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * AdministratorInfo
 */

public class AdministratorInfo {
  @JsonProperty("mail")
  private String mail;

  @JsonProperty("permission")
  private Permission permission;

  public AdministratorInfo mail(String mail) {
    this.mail = mail;
    return this;
  }

  /**
   * Mail of the administrator.
   * 
   * @return mail
   */
  @ApiModelProperty(required = true, value = "Mail of the administrator.")
  @NotNull

  @javax.validation.constraints.Email
  public String getMail() {
    return mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }

  public AdministratorInfo permission(Permission permission) {
    this.permission = permission;
    return this;
  }

  /**
   * Get permission
   * 
   * @return permission
   */
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public Permission getPermission() {
    return permission;
  }

  public void setPermission(Permission permission) {
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
    AdministratorInfo administratorInfo = (AdministratorInfo) o;
    return Objects.equals(this.mail, administratorInfo.mail)
        && Objects.equals(this.permission, administratorInfo.permission);
  }

  @Override
  public int hashCode() {
    return Objects.hash(mail, permission);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AdministratorInfo {\n");

    sb.append("    mail: ").append(toIndentedString(mail)).append("\n");
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
