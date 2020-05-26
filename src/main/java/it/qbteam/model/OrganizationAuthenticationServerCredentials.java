package it.qbteam.model;

import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Credentials for authenticating to the organizations&#39;s authentication server.
 */
@ApiModel(description = "Credentials for authenticating to the organizations's authentication server.")

public class OrganizationAuthenticationServerCredentials   {
  @JsonProperty("username")
  private String username;

  @JsonProperty("password")
  private String password;

  public OrganizationAuthenticationServerCredentials username(String username) {
    this.username = username;
    return this;
  }

  /**
   * Username of the administrator account in the organizations's authentication server.
   * @return username
  */
  @ApiModelProperty(required = true, value = "Username of the administrator account in the organizations's authentication server.")
  @NotNull

  @Size(max=256) 
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public OrganizationAuthenticationServerCredentials password(String password) {
    this.password = password;
    return this;
  }

  /**
   * Password of the administrator account in the organizations's authentication server.
   * @return password
  */
  @ApiModelProperty(required = true, value = "Password of the administrator account in the organizations's authentication server.")
  @NotNull


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrganizationAuthenticationServerCredentials organizationAuthenticationServerCredentials = (OrganizationAuthenticationServerCredentials) o;
    return Objects.equals(this.username, organizationAuthenticationServerCredentials.username) &&
        Objects.equals(this.password, organizationAuthenticationServerCredentials.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, password);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationAuthenticationServerCredentials {\n");
    
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
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

