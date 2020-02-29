package it.qbteam.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.qbteam.model.Movement;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Movement in a place of an organization made with the authenticated trackingMode.
 */
@ApiModel(description = "Movement in a place of an organization made with the authenticated trackingMode.")

public class PlaceAuthenticatedMovement   {
  @JsonProperty("movement")
  private Movement movement;

  @JsonProperty("placeId")
  private Long placeId;

  @JsonProperty("ldapId")
  private Long ldapId;

  public PlaceAuthenticatedMovement movement(Movement movement) {
    this.movement = movement;
    return this;
  }

  /**
   * Get movement
   * @return movement
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public Movement getMovement() {
    return movement;
  }

  public void setMovement(Movement movement) {
    this.movement = movement;
  }

  public PlaceAuthenticatedMovement placeId(Long placeId) {
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

  public PlaceAuthenticatedMovement ldapId(Long ldapId) {
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
    PlaceAuthenticatedMovement placeAuthenticatedMovement = (PlaceAuthenticatedMovement) o;
    return Objects.equals(this.movement, placeAuthenticatedMovement.movement) &&
        Objects.equals(this.placeId, placeAuthenticatedMovement.placeId) &&
        Objects.equals(this.ldapId, placeAuthenticatedMovement.ldapId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(movement, placeId, ldapId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PlaceAuthenticatedMovement {\n");
    
    sb.append("    movement: ").append(toIndentedString(movement)).append("\n");
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

