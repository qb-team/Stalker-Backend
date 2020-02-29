package it.qbteam.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.qbteam.model.Access;
import it.qbteam.model.PlaceAuthenticatedAccessAllOf;
import java.time.OffsetDateTime;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Access to a place of an organization made with the authenticated trackingMode.
 */
@ApiModel(description = "Access to a place of an organization made with the authenticated trackingMode.")

public class PlaceAuthenticatedAccess extends Access  {
  @JsonProperty("placeId")
  private Long placeId;

  @JsonProperty("ldapId")
  private Long ldapId;

  public PlaceAuthenticatedAccess placeId(Long placeId) {
    this.placeId = placeId;
    return this;
  }

  /**
   * Unique identifier of the place in which the user had access.
   * @return placeId
  */
  @ApiModelProperty(required = true, value = "Unique identifier of the place in which the user had access.")
  @NotNull


  public Long getPlaceId() {
    return placeId;
  }

  public void setPlaceId(Long placeId) {
    this.placeId = placeId;
  }

  public PlaceAuthenticatedAccess ldapId(Long ldapId) {
    this.ldapId = ldapId;
    return this;
  }

  /**
   * Organization LDAP server's user unique identifier.
   * @return ldapId
  */
  @ApiModelProperty(required = true, value = "Organization LDAP server's user unique identifier.")
  @NotNull


  public Long getLdapId() {
    return ldapId;
  }

  public void setLdapId(Long ldapId) {
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
    PlaceAuthenticatedAccess placeAuthenticatedAccess = (PlaceAuthenticatedAccess) o;
    return Objects.equals(this.placeId, placeAuthenticatedAccess.placeId) &&
        Objects.equals(this.ldapId, placeAuthenticatedAccess.ldapId) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(placeId, ldapId, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PlaceAuthenticatedAccess {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    placeId: ").append(toIndentedString(placeId)).append("\n");
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

