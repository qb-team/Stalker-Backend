package it.qbteam.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.springframework.data.redis.core.RedisHash;

/**
 * Number of people currently inside the organization&#39;s trackingArea.
 */
@ApiModel(description = "Number of people currently inside the organization's trackingArea.")
@RedisHash("OrganizationPresenceCounter")
public class OrganizationPresenceCounter   {
  @JsonProperty("organizationId")
  private Long organizationId;

  @JsonProperty("counter")
  private Integer counter;

  public OrganizationPresenceCounter organizationId(Long organizationId) {
    this.organizationId = organizationId;
    return this;
  }

  /**
   * Unique identifier of the organization.
   * @return organizationId
  */
  @ApiModelProperty(required = true, value = "Unique identifier of the organization.")
  @NotNull

  @Min(1L)
  public Long getOrganizationId() {
    return organizationId;
  }

  public void setOrganizationId(Long organizationId) {
    this.organizationId = organizationId;
  }

  public OrganizationPresenceCounter counter(Integer counter) {
    this.counter = counter;
    return this;
  }

  /**
   * Number of people currently inside the organization's trackingArea.
   * @return counter
  */
  @ApiModelProperty(required = true, value = "Number of people currently inside the organization's trackingArea.")
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
    OrganizationPresenceCounter organizationPresenceCounter = (OrganizationPresenceCounter) o;
    return Objects.equals(this.organizationId, organizationPresenceCounter.organizationId) &&
        Objects.equals(this.counter, organizationPresenceCounter.counter);
  }

  @Override
  public int hashCode() {
    return Objects.hash(organizationId, counter);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationPresenceCounter {\n");
    
    sb.append("    organizationId: ").append(toIndentedString(organizationId)).append("\n");
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

