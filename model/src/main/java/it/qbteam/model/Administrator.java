package it.qbteam.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.*;

/**
 * Administrator
 */
@Entity
@IdClass(AdministratorId.class)
public class Administrator   {
  @Id
  @Column(length = 256)
  @JsonProperty("administratorId")
  private String administratorId;

  @Id
  @Column(length = 256)
  @JsonProperty("orgAuthServerId")
  private String orgAuthServerId;

  public Administrator administratorId(String administratorId) {
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

  public Administrator orgAuthServerId(String orgAuthServerId) {
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
    Administrator administrator = (Administrator) o;
    return Objects.equals(this.administratorId, administrator.administratorId) &&
        Objects.equals(this.orgAuthServerId, administrator.orgAuthServerId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(administratorId, orgAuthServerId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Administrator {\n");
    
    sb.append("    administratorId: ").append(toIndentedString(administratorId)).append("\n");
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

