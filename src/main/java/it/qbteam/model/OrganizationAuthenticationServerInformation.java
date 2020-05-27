package it.qbteam.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Information of a user of an organizations.
 */
@ApiModel(description = "Information of a user of an organizations.")

public class OrganizationAuthenticationServerInformation   {
  @JsonProperty("orgAuthServerId")
  private String orgAuthServerId;

  @JsonProperty("name")
  private String name;

  @JsonProperty("surname")
  private String surname;

  public OrganizationAuthenticationServerInformation orgAuthServerId(String orgAuthServerId) {
    this.orgAuthServerId = orgAuthServerId;
    return this;
  }

  /**
   * User unique identifier from the authentication server of the organization.
   * @return orgAuthServerId
  */
  @ApiModelProperty(required = true, value = "User unique identifier from the authentication server of the organization.")
  @NotNull

  @Size(max=256) 
  public String getOrgAuthServerId() {
    return orgAuthServerId;
  }

  public void setOrgAuthServerId(String orgAuthServerId) {
    this.orgAuthServerId = orgAuthServerId;
  }

  public OrganizationAuthenticationServerInformation name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Name of the user.
   * @return name
  */
  @ApiModelProperty(required = true, value = "Name of the user.")
  @NotNull


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public OrganizationAuthenticationServerInformation surname(String surname) {
    this.surname = surname;
    return this;
  }

  /**
   * Surname of the user.
   * @return surname
  */
  @ApiModelProperty(required = true, value = "Surname of the user.")
  @NotNull


  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrganizationAuthenticationServerInformation organizationAuthenticationServerInformation = (OrganizationAuthenticationServerInformation) o;
    return Objects.equals(this.orgAuthServerId, organizationAuthenticationServerInformation.orgAuthServerId) &&
        Objects.equals(this.name, organizationAuthenticationServerInformation.name) &&
        Objects.equals(this.surname, organizationAuthenticationServerInformation.surname);
  }

  @Override
  public int hashCode() {
    return Objects.hash(orgAuthServerId, name, surname);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationAuthenticationServerInformation {\n");
    
    sb.append("    orgAuthServerId: ").append(toIndentedString(orgAuthServerId)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    surname: ").append(toIndentedString(surname)).append("\n");
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

