package it.qbteam.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.OffsetDateTime;
import org.openapitools.jackson.nullable.JsonNullable;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Generic access to an organization or a place of it.
 */
@ApiModel(description = "Generic access to an organization or a place of it.")

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "accessDiscriminator", visible = true)
@JsonSubTypes({
  @JsonSubTypes.Type(value = PlaceAuthenticatedAccess.class, name = "PlaceAuthenticatedAccess"),
  @JsonSubTypes.Type(value = OrganizationAuthenticatedAccess.class, name = "OrganizationAuthenticatedAccess"),
  @JsonSubTypes.Type(value = PlaceAnonymousAccess.class, name = "PlaceAnonymousAccess"),
  @JsonSubTypes.Type(value = OrganizationAnonymousAccess.class, name = "OrganizationAnonymousAccess"),
})
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Access   {
  @Id
  @JsonProperty("id")
  private Long id;

  @JsonProperty("accessDiscriminator")
  private String accessDiscriminator;

  @JsonProperty("entranceTimestamp")
  private OffsetDateTime entranceTimestamp;

  @JsonProperty("exitTimestamp")
  private OffsetDateTime exitTimestamp;

  public Access id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Access accessDiscriminator(String accessDiscriminator) {
    this.accessDiscriminator = accessDiscriminator;
    return this;
  }

  /**
   * Get accessDiscriminator
   * @return accessDiscriminator
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getAccessDiscriminator() {
    return accessDiscriminator;
  }

  public void setAccessDiscriminator(String accessDiscriminator) {
    this.accessDiscriminator = accessDiscriminator;
  }

  public Access entranceTimestamp(OffsetDateTime entranceTimestamp) {
    this.entranceTimestamp = entranceTimestamp;
    return this;
  }

  /**
   * Date and time of the moment in which the user entered the place.
   * @return entranceTimestamp
  */
  @ApiModelProperty(required = true, value = "Date and time of the moment in which the user entered the place.")
  @NotNull

  @Valid

  public OffsetDateTime getEntranceTimestamp() {
    return entranceTimestamp;
  }

  public void setEntranceTimestamp(OffsetDateTime entranceTimestamp) {
    this.entranceTimestamp = entranceTimestamp;
  }

  public Access exitTimestamp(OffsetDateTime exitTimestamp) {
    this.exitTimestamp = exitTimestamp;
    return this;
  }

  /**
   * Date and time of the moment in which the user left the place.
   * @return exitTimestamp
  */
  @ApiModelProperty(required = true, value = "Date and time of the moment in which the user left the place.")
  @NotNull

  @Valid

  public OffsetDateTime getExitTimestamp() {
    return exitTimestamp;
  }

  public void setExitTimestamp(OffsetDateTime exitTimestamp) {
    this.exitTimestamp = exitTimestamp;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Access access = (Access) o;
    return Objects.equals(this.id, access.id) &&
        Objects.equals(this.accessDiscriminator, access.accessDiscriminator) &&
        Objects.equals(this.entranceTimestamp, access.entranceTimestamp) &&
        Objects.equals(this.exitTimestamp, access.exitTimestamp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, accessDiscriminator, entranceTimestamp, exitTimestamp);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Access {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    accessDiscriminator: ").append(toIndentedString(accessDiscriminator)).append("\n");
    sb.append("    entranceTimestamp: ").append(toIndentedString(entranceTimestamp)).append("\n");
    sb.append("    exitTimestamp: ").append(toIndentedString(exitTimestamp)).append("\n");
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

