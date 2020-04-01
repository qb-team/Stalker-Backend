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
 * AdministratorInfo
 */

public class AdministratorInfo   {
  @JsonProperty("mail")
  private String mail;

  @JsonProperty("admininistrator")
  private Administrator admininistrator;

  @JsonProperty("permission")
  private Permission permission;

  public AdministratorInfo mail(String mail) {
    this.mail = mail;
    return this;
  }

  /**
   * Mail of the administrator.
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

  public AdministratorInfo admininistrator(Administrator admininistrator) {
    this.admininistrator = admininistrator;
    return this;
  }

  /**
   * Get admininistrator
   * @return admininistrator
  */
  @ApiModelProperty(value = "")

  @Valid

  public Administrator getAdmininistrator() {
    return admininistrator;
  }

  public void setAdmininistrator(Administrator admininistrator) {
    this.admininistrator = admininistrator;
  }

  public AdministratorInfo permission(Permission permission) {
    this.permission = permission;
    return this;
  }

  /**
   * Get permission
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
    return Objects.equals(this.mail, administratorInfo.mail) &&
        Objects.equals(this.admininistrator, administratorInfo.admininistrator) &&
        Objects.equals(this.permission, administratorInfo.permission);
  }

  @Override
  public int hashCode() {
    return Objects.hash(mail, admininistrator, permission);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AdministratorInfo {\n");
    
    sb.append("    mail: ").append(toIndentedString(mail)).append("\n");
    sb.append("    admininistrator: ").append(toIndentedString(admininistrator)).append("\n");
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

