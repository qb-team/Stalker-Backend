package it.qbteam.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.springframework.data.redis.core.RedisHash;

/**
 * Number of people currently inside a place of an organization.
 */
@ApiModel(description = "Number of people currently inside a place of an organization.")
@RedisHash("PlacePresenceCounter")
public class PlacePresenceCounter   {
  @JsonProperty("placeId")
  private Long placeId;

  @JsonProperty("counter")
  private Integer counter;

  public PlacePresenceCounter placeId(Long placeId) {
    this.placeId = placeId;
    return this;
  }

  /**
   * Unique identifier of the place.
   * @return placeId
  */
  @ApiModelProperty(required = true, value = "Unique identifier of the place.")
  @NotNull

  @Min(1L)
  public Long getPlaceId() {
    return placeId;
  }

  public void setPlaceId(Long placeId) {
    this.placeId = placeId;
  }

  public PlacePresenceCounter counter(Integer counter) {
    this.counter = counter;
    return this;
  }

  /**
   * Number of people currently inside a place of an organization.
   * @return counter
  */
  @ApiModelProperty(required = true, value = "Number of people currently inside a place of an organization.")
  @NotNull

  @Min(0)
  public Integer getCounter() {
    return counter;
  }

  public void setCounter(Integer counter) {
    this.counter = counter;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PlacePresenceCounter placePresenceCounter = (PlacePresenceCounter) o;
    return Objects.equals(this.placeId, placePresenceCounter.placeId) &&
        Objects.equals(this.counter, placePresenceCounter.counter);
  }

  @Override
  public int hashCode() {
    return Objects.hash(placeId, counter);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PlacePresenceCounter {\n");
    
    sb.append("    placeId: ").append(toIndentedString(placeId)).append("\n");
    sb.append("    counter: ").append(toIndentedString(counter)).append("\n");
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

