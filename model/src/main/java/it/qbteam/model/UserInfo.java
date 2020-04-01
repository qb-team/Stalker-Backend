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
 * UserInfo
 */

public class UserInfo   {
  @JsonProperty("name")
  private String name;

  @JsonProperty("surname")
  private String surname;

  @JsonProperty("mail")
  private String mail;

  public UserInfo name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Name of the user (if available in the authentication server of the organization).
   * @return name
  */
  @ApiModelProperty(required = true, value = "Name of the user (if available in the authentication server of the organization).")
  @NotNull


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public UserInfo surname(String surname) {
    this.surname = surname;
    return this;
  }

  /**
   * Surname of the user (if available in the authentication server of the organization).
   * @return surname
  */
  @ApiModelProperty(required = true, value = "Surname of the user (if available in the authentication server of the organization).")
  @NotNull


  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public UserInfo mail(String mail) {
    this.mail = mail;
    return this;
  }

  /**
   * Mail address of the user.
   * @return mail
  */
  @ApiModelProperty(required = true, value = "Mail address of the user.")
  @NotNull

@javax.validation.constraints.Email
  public String getMail() {
    return mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserInfo userInfo = (UserInfo) o;
    return Objects.equals(this.name, userInfo.name) &&
        Objects.equals(this.surname, userInfo.surname) &&
        Objects.equals(this.mail, userInfo.mail);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, surname, mail);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserInfo {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    surname: ").append(toIndentedString(surname)).append("\n");
    sb.append("    mail: ").append(toIndentedString(mail)).append("\n");
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

