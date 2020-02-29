package it.qbteam.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.qbteam.model.Access;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Access to a place of an organization made with the anonymous trackingMode.
 */
@ApiModel(description = "Access to a place of an organization made with the anonymous trackingMode.")

public class PlaceAnonymousAccess   {
  @JsonProperty("access")
  private Access access;

  @JsonProperty("placeId")
  private Long placeId;

  public PlaceAnonymousAccess access(Access access) {
    this.access = access;
    return this;
  }

  /**
   * Get access
   * @return access
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public Access getAccess() {
    return access;
  }

  public void setAccess(Access access) {
    this.access = access;
  }

  public PlaceAnonymousAccess placeId(Long placeId) {
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
    PlaceAnonymousAccess placeAnonymousAccess = (PlaceAnonymousAccess) o;
    return Objects.equals(this.access, placeAnonymousAccess.access) &&
        Objects.equals(this.placeId, placeAnonymousAccess.placeId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(access, placeId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PlaceAnonymousAccess {\n");
    
    sb.append("    access: ").append(toIndentedString(access)).append("\n");
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

