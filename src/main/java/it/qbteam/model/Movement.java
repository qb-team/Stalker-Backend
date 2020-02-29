package it.qbteam.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.OffsetDateTime;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Generic movement in an organization or in a place of it.
 */
@ApiModel(description = "Generic movement in an organization or in a place of it.")

public class Movement   {
  @JsonProperty("timestamp")
  private OffsetDateTime timestamp;

  /**
   * Type of movement.
   */
  public enum MovementTypeEnum {
    ENTRANCE("entrance"),
    
    EXIT("exit");

    private String value;

    MovementTypeEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static MovementTypeEnum fromValue(String value) {
      for (MovementTypeEnum b : MovementTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("movementType")
  private MovementTypeEnum movementType;

  public Movement timestamp(OffsetDateTime timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  /**
   * Date and time of the moment in which the user entered the place.
   * @return timestamp
  */
  @ApiModelProperty(required = true, value = "Date and time of the moment in which the user entered the place.")
  @NotNull

  @Valid

  public OffsetDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(OffsetDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public Movement movementType(MovementTypeEnum movementType) {
    this.movementType = movementType;
    return this;
  }

  /**
   * Type of movement.
   * @return movementType
  */
  @ApiModelProperty(required = true, value = "Type of movement.")
  @NotNull


  public MovementTypeEnum getMovementType() {
    return movementType;
  }

  public void setMovementType(MovementTypeEnum movementType) {
    this.movementType = movementType;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Movement movement = (Movement) o;
    return Objects.equals(this.timestamp, movement.timestamp) &&
        Objects.equals(this.movementType, movement.movementType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(timestamp, movementType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Movement {\n");
    
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("    movementType: ").append(toIndentedString(movementType)).append("\n");
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

