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
 * Movement to a place of an organization made with the anonymous trackingMode.
 */
@ApiModel(description = "Movement to a place of an organization made with the anonymous trackingMode.")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-02-29T20:29:04.115+01:00[Europe/Berlin]")

public class PlaceAnonymousMovement   {
  @JsonProperty("movement")
  private Movement movement;

  @JsonProperty("placeId")
  private Long placeId;

  public PlaceAnonymousMovement movement(Movement movement) {
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

  public PlaceAnonymousMovement placeId(Long placeId) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PlaceAnonymousMovement placeAnonymousMovement = (PlaceAnonymousMovement) o;
    return Objects.equals(this.movement, placeAnonymousMovement.movement) &&
        Objects.equals(this.placeId, placeAnonymousMovement.placeId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(movement, placeId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PlaceAnonymousMovement {\n");
    
    sb.append("    movement: ").append(toIndentedString(movement)).append("\n");
    sb.append("    placeId: ").append(toIndentedString(placeId)).append("\n");
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

