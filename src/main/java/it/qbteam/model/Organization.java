package it.qbteam.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.qbteam.model.Address;
import java.time.OffsetDateTime;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Subject interested in tracking people&#39;s presence inside its own places, in either an anonymous or authenticated way.
 */
@ApiModel(description = "Subject interested in tracking people's presence inside its own places, in either an anonymous or authenticated way.")

public class Organization   {
  @JsonProperty("id")
  private Long id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("description")
  private String description;

  @JsonProperty("image")
  private String image;

  @JsonProperty("address")
  private Address address;

  @JsonProperty("serverLDAP")
  private String serverLDAP;

  @JsonProperty("creationDate")
  private String creationDate;

  @JsonProperty("modifyDate")
  private Object modifyDate = null;

  @JsonProperty("lastChangeDate")
  private OffsetDateTime lastChangeDate;

  @JsonProperty("trackingArea")
  private String trackingArea;

  /**
   * How an user who added to its favorites the organization can be tracked inside the organization's trackingArea and its places.
   */
  public enum TrackingModeEnum {
    AUTHENTICATED("authenticated"),
    
    ANONYMOUS("anonymous");

    private String value;

    TrackingModeEnum(String value) {
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
    public static TrackingModeEnum fromValue(String value) {
      for (TrackingModeEnum b : TrackingModeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("trackingMode")
  private TrackingModeEnum trackingMode;

  public Organization id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Unique identifier for an organization.
   * @return id
  */
  @ApiModelProperty(required = true, value = "Unique identifier for an organization.")
  @NotNull


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Organization name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Name of the organization.
   * @return name
  */
  @ApiModelProperty(required = true, value = "Name of the organization.")
  @NotNull


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Organization description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Small description of what the organization does.
   * @return description
  */
  @ApiModelProperty(value = "Small description of what the organization does.")


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Organization image(String image) {
    this.image = image;
    return this;
  }

  /**
   * Image/logo for the organization which gets shown on the application.
   * @return image
  */
  @ApiModelProperty(value = "Image/logo for the organization which gets shown on the application.")


  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public Organization address(Address address) {
    this.address = address;
    return this;
  }

  /**
   * Get address
   * @return address
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public Organization serverLDAP(String serverLDAP) {
    this.serverLDAP = serverLDAP;
    return this;
  }

  /**
   * URL or IP address of the LDAP server of the organization. If it's required a specific TCP port (different from LDAP's default) it must be specified. Needed only if trackingMethod is set to authenticated.
   * @return serverLDAP
  */
  @ApiModelProperty(value = "URL or IP address of the LDAP server of the organization. If it's required a specific TCP port (different from LDAP's default) it must be specified. Needed only if trackingMethod is set to authenticated.")


  public String getServerLDAP() {
    return serverLDAP;
  }

  public void setServerLDAP(String serverLDAP) {
    this.serverLDAP = serverLDAP;
  }

  public Organization creationDate(String creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  /**
   * Get creationDate
   * @return creationDate
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(String creationDate) {
    this.creationDate = creationDate;
  }

  public Organization modifyDate(Object modifyDate) {
    this.modifyDate = modifyDate;
    return this;
  }

  /**
   * When the organization was added to the system.
   * @return modifyDate
  */
  @ApiModelProperty(required = true, value = "When the organization was added to the system.")
  @NotNull


  public Object getModifyDate() {
    return modifyDate;
  }

  public void setModifyDate(Object modifyDate) {
    this.modifyDate = modifyDate;
  }

  public Organization lastChangeDate(OffsetDateTime lastChangeDate) {
    this.lastChangeDate = lastChangeDate;
    return this;
  }

  /**
   * When the organization parameters were last changed.
   * @return lastChangeDate
  */
  @ApiModelProperty(value = "When the organization parameters were last changed.")

  @Valid

  public OffsetDateTime getLastChangeDate() {
    return lastChangeDate;
  }

  public void setLastChangeDate(OffsetDateTime lastChangeDate) {
    this.lastChangeDate = lastChangeDate;
  }

  public Organization trackingArea(String trackingArea) {
    this.trackingArea = trackingArea;
    return this;
  }

  /**
   * Area subjected to movement tracking of people. It is a collection of (longitude, latitude) pairs consisting in a polygon. The string is expressed in JSON format.
   * @return trackingArea
  */
  @ApiModelProperty(required = true, value = "Area subjected to movement tracking of people. It is a collection of (longitude, latitude) pairs consisting in a polygon. The string is expressed in JSON format.")
  @NotNull


  public String getTrackingArea() {
    return trackingArea;
  }

  public void setTrackingArea(String trackingArea) {
    this.trackingArea = trackingArea;
  }

  public Organization trackingMode(TrackingModeEnum trackingMode) {
    this.trackingMode = trackingMode;
    return this;
  }

  /**
   * How an user who added to its favorites the organization can be tracked inside the organization's trackingArea and its places.
   * @return trackingMode
  */
  @ApiModelProperty(required = true, value = "How an user who added to its favorites the organization can be tracked inside the organization's trackingArea and its places.")
  @NotNull


  public TrackingModeEnum getTrackingMode() {
    return trackingMode;
  }

  public void setTrackingMode(TrackingModeEnum trackingMode) {
    this.trackingMode = trackingMode;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Organization organization = (Organization) o;
    return Objects.equals(this.id, organization.id) &&
        Objects.equals(this.name, organization.name) &&
        Objects.equals(this.description, organization.description) &&
        Objects.equals(this.image, organization.image) &&
        Objects.equals(this.address, organization.address) &&
        Objects.equals(this.serverLDAP, organization.serverLDAP) &&
        Objects.equals(this.creationDate, organization.creationDate) &&
        Objects.equals(this.modifyDate, organization.modifyDate) &&
        Objects.equals(this.lastChangeDate, organization.lastChangeDate) &&
        Objects.equals(this.trackingArea, organization.trackingArea) &&
        Objects.equals(this.trackingMode, organization.trackingMode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, image, address, serverLDAP, creationDate, modifyDate, lastChangeDate, trackingArea, trackingMode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Organization {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    image: ").append(toIndentedString(image)).append("\n");
    sb.append("    address: ").append(toIndentedString(address)).append("\n");
    sb.append("    serverLDAP: ").append(toIndentedString(serverLDAP)).append("\n");
    sb.append("    creationDate: ").append(toIndentedString(creationDate)).append("\n");
    sb.append("    modifyDate: ").append(toIndentedString(modifyDate)).append("\n");
    sb.append("    lastChangeDate: ").append(toIndentedString(lastChangeDate)).append("\n");
    sb.append("    trackingArea: ").append(toIndentedString(trackingArea)).append("\n");
    sb.append("    trackingMode: ").append(toIndentedString(trackingMode)).append("\n");
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

