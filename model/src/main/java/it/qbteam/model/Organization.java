package it.qbteam.model;

import java.time.OffsetDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Subject interested in tracking people&#39;s presence inside its own places, in either an anonymous or authenticated way.
 */
@ApiModel(description = "Subject interested in tracking people's presence inside its own places, in either an anonymous or authenticated way.")
@Entity
public class Organization   {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @JsonProperty("id")
  private Long id;

  @Column(length = 128)
  @JsonProperty("name")
  private String name;

  @Column(length = 512)
  @JsonProperty("description")
  private String description;

  @Column(length = 300)
  @JsonProperty("image")
  private String image;

  @Column(length = 256)
  @JsonProperty("street")
  private String street;

  @Column(length = 10)
  @JsonProperty("number")
  private String number;

  @JsonProperty("postCode")
  private Integer postCode;

  @Column(length = 100)
  @JsonProperty("city")
  private String city;

  @Column(length = 100)
  @JsonProperty("country")
  private String country;

  @Column(length = 2048)
  @JsonProperty("authenticationServerURL")
  private String authenticationServerURL;

  @JsonProperty("creationDate")
  private OffsetDateTime creationDate;

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

  @Enumerated(EnumType.STRING)
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

  public Organization street(String street) {
    this.street = street;
    return this;
  }

  /**
   * The street where the organization is located.
   * @return street
  */
  @ApiModelProperty(required = true, value = "The street where the organization is located.")
  @NotNull


  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public Organization number(String number) {
    this.number = number;
    return this;
  }

  /**
   * The number in the street where the organization is located.
   * @return number
  */
  @ApiModelProperty(required = true, value = "The number in the street where the organization is located.")
  @NotNull


  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public Organization postCode(Integer postCode) {
    this.postCode = postCode;
    return this;
  }

  /**
   * The postcode where the organization is located.
   * @return postCode
  */
  @ApiModelProperty(value = "The postcode where the organization is located.")
  @NotNull

  public Integer getPostCode() {
    return postCode;
  }

  public void setPostCode(Integer postCode) {
    this.postCode = postCode;
  }

  public Organization city(String city) {
    this.city = city;
    return this;
  }

  /**
   * The city where the organization is located.
   * @return city
  */
  @ApiModelProperty(required = true, value = "The city where the organization is located.")
  @NotNull


  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public Organization country(String country) {
    this.country = country;
    return this;
  }

  /**
   * The country where the organization is located.
   * @return country
  */
  @ApiModelProperty(required = true, value = "The country where the organization is located.")
  @NotNull


  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public Organization authenticationServerURL(String authenticationServerURL) {
    this.authenticationServerURL = authenticationServerURL;
    return this;
  }

  /**
   * URL or IP address of the authentication server of the organization. If it's required a specific port or protocol it must be specified. Needed only if trackingMethod is set to authenticated.
   * @return authenticationServerURL
  */
  @ApiModelProperty(value = "URL or IP address of the authentication server of the organization. If it's required a specific port or protocol it must be specified. Needed only if trackingMethod is set to authenticated.")


  public String getAuthenticationServerURL() {
    return authenticationServerURL;
  }

  public void setAuthenticationServerURL(String authenticationServerURL) {
    this.authenticationServerURL = authenticationServerURL;
  }

  public Organization creationDate(OffsetDateTime creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  /**
   * When the organization was added to the system.
   * @return creationDate
  */
  @ApiModelProperty(required = true, value = "When the organization was added to the system.")
  @NotNull

  @Valid

  public OffsetDateTime getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(OffsetDateTime creationDate) {
    this.creationDate = creationDate;
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
  @NotNull
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
        Objects.equals(this.street, organization.street) &&
        Objects.equals(this.number, organization.number) &&
        Objects.equals(this.postCode, organization.postCode) &&
        Objects.equals(this.city, organization.city) &&
        Objects.equals(this.country, organization.country) &&
        Objects.equals(this.authenticationServerURL, organization.authenticationServerURL) &&
        Objects.equals(this.creationDate, organization.creationDate) &&
        Objects.equals(this.lastChangeDate, organization.lastChangeDate) &&
        Objects.equals(this.trackingArea, organization.trackingArea) &&
        Objects.equals(this.trackingMode, organization.trackingMode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, image, street, number, postCode, city, country, authenticationServerURL, creationDate, lastChangeDate, trackingArea, trackingMode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Organization {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    image: ").append(toIndentedString(image)).append("\n");
    sb.append("    street: ").append(toIndentedString(street)).append("\n");
    sb.append("    number: ").append(toIndentedString(number)).append("\n");
    sb.append("    postCode: ").append(toIndentedString(postCode)).append("\n");
    sb.append("    city: ").append(toIndentedString(city)).append("\n");
    sb.append("    country: ").append(toIndentedString(country)).append("\n");
    sb.append("    authenticationServerURL: ").append(toIndentedString(authenticationServerURL)).append("\n");
    sb.append("    creationDate: ").append(toIndentedString(creationDate)).append("\n");
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

