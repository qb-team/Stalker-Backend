package it.qbteam.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.IdClass;
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
  @JsonProperty("ldapId")
  private Integer ldapId;

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

  public User ldapId(Integer ldapId) {
    this.ldapId = ldapId;
    return this;
  }

  /**
   * Organization LDAP server's user unique identifier.
   * @return ldapId
  */
  @ApiModelProperty(value = "Organization LDAP server's user unique identifier.")


  public Integer getLdapId() {
    return ldapId;
  }

  public void setLdapId(Integer ldapId) {
    this.ldapId = ldapId;
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
        Objects.equals(this.ldapId, user.ldapId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, ldapId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class User {\n");
    
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    ldapId: ").append(toIndentedString(ldapId)).append("\n");
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

