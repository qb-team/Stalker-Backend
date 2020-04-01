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
 * User
 */
@Entity
@IdClass(UserId.class)
public class User   {
  @Id
  @JsonProperty("userId")
  private String userId;

  @Id
  @JsonProperty("orgAuthServerId")
  private String orgAuthServerId;

  public User userId(String userId) {
    this.userId = userId;
    return this;
  }

  /**
   * Authentication service's user unique identifier.
   * @return userId
  */
  @ApiModelProperty(required = true, value = "Authentication service's user unique identifier.")
  @NotNull


  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public User orgAuthServerId(String orgAuthServerId) {
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
    User user = (User) o;
    return Objects.equals(this.userId, user.userId) &&
        Objects.equals(this.orgAuthServerId, user.orgAuthServerId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, orgAuthServerId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class User {\n");
    
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
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

