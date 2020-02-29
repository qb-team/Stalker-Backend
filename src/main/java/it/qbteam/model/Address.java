package it.qbteam.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * The address of an organization.
 */
@ApiModel(description = "The address of an organization.")

public class Address   {
  @JsonProperty("street")
  private String street;

  @JsonProperty("number")
  private Integer number;

  @JsonProperty("postCode")
  private Integer postCode;

  @JsonProperty("city")
  private String city;

  @JsonProperty("state")
  private String state;

  public Address street(String street) {
    this.street = street;
    return this;
  }

  /**
   * the street where the organization is situated
   * @return street
  */
  @ApiModelProperty(required = true, value = "the street where the organization is situated")
  @NotNull


  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public Address number(Integer number) {
    this.number = number;
    return this;
  }

  /**
   * the number in the street where the organization is situated
   * @return number
  */
  @ApiModelProperty(required = true, value = "the number in the street where the organization is situated")
  @NotNull


  public Integer getNumber() {
    return number;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }

  public Address postCode(Integer postCode) {
    this.postCode = postCode;
    return this;
  }

  /**
   * the postcode of the city where the organization is situated
   * @return postCode
  */
  @ApiModelProperty(value = "the postcode of the city where the organization is situated")


  public Integer getPostCode() {
    return postCode;
  }

  public void setPostCode(Integer postCode) {
    this.postCode = postCode;
  }

  public Address city(String city) {
    this.city = city;
    return this;
  }

  /**
   * the city where the organization is situated
   * @return city
  */
  @ApiModelProperty(required = true, value = "the city where the organization is situated")
  @NotNull


  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public Address state(String state) {
    this.state = state;
    return this;
  }

  /**
   * the state where the organization is situated
   * @return state
  */
  @ApiModelProperty(value = "the state where the organization is situated")


  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Address address = (Address) o;
    return Objects.equals(this.street, address.street) &&
        Objects.equals(this.number, address.number) &&
        Objects.equals(this.postCode, address.postCode) &&
        Objects.equals(this.city, address.city) &&
        Objects.equals(this.state, address.state);
  }

  @Override
  public int hashCode() {
    return Objects.hash(street, number, postCode, city, state);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Address {\n");
    
    sb.append("    street: ").append(toIndentedString(street)).append("\n");
    sb.append("    number: ").append(toIndentedString(number)).append("\n");
    sb.append("    postCode: ").append(toIndentedString(postCode)).append("\n");
    sb.append("    city: ").append(toIndentedString(city)).append("\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
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

