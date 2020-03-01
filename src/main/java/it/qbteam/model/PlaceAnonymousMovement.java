package it.qbteam.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.qbteam.model.Movement;
import java.time.OffsetDateTime;
import org.openapitools.jackson.nullable.JsonNullable;

import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Movement to a place of an organization made with the anonymous trackingMode.
 */
@ApiModel(description = "Movement to a place of an organization made with the anonymous trackingMode.")
@Entity
public class PlaceAnonymousMovement extends Movement  {
  @JsonProperty("placeId")
  private Long placeId;

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
    return Objects.equals(this.placeId, placeAnonymousMovement.placeId) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(placeId, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PlaceAnonymousMovement {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
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

