package it.qbteam.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Number of people currently inside a place of an organization.
 */
@ApiModel(description = "Number of people currently inside a place of an organization.")
@RedisHash("PlacePresenceCounter")
public class PlacePresenceCounter   {
  @Id
  @JsonProperty("placeId")
  private Long id;

  @JsonProperty("counter")
  private Integer counter;

  public PlacePresenceCounter placeId(Long placeId) {
    this.id = placeId;
    return this;
  }

  /**
   * Unique identifier of the place.
   * @return placeId
  */
  @ApiModelProperty(required = true, value = "Unique identifier of the place.")
  @NotNull


  public Long getPlaceId() {
    return id;
  }

  public void setPlaceId(Long placeId) {
    this.id = placeId;
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
    return Objects.equals(this.id, placePresenceCounter.id) &&
        Objects.equals(this.counter, placePresenceCounter.counter);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, counter);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PlacePresenceCounter {\n");
    
    sb.append("    placeId: ").append(toIndentedString(id)).append("\n");
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

