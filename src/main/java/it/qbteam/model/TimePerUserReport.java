package it.qbteam.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Represents a row in the report generated for an user by the viewer administrator.
 */
@ApiModel(description = "Represents a row in the report generated for an user by the viewer administrator.")

public class TimePerUserReport   {
  @JsonProperty("placeId")
  private Long placeId;

  @JsonProperty("orgAuthServerId")
  private String orgAuthServerId;

  @JsonProperty("totalTime")
  private Long totalTime;

  public TimePerUserReport placeId(Long placeId) {
    this.placeId = placeId;
    return this;
  }

  /**
   * Unique identifier of the place in which the user had access.
   * minimum: 1
   * @return placeId
  */
  @ApiModelProperty(required = true, value = "Unique identifier of the place in which the user had access.")
  @NotNull

  @Min(1L)
  public Long getPlaceId() {
    return placeId;
  }

  public void setPlaceId(Long placeId) {
    this.placeId = placeId;
  }

  public TimePerUserReport orgAuthServerId(String orgAuthServerId) {
    this.orgAuthServerId = orgAuthServerId;
    return this;
  }

  /**
   * User unique identifier from the authentication server of the organization.
   * @return orgAuthServerId
  */
  @ApiModelProperty(required = true, value = "User unique identifier from the authentication server of the organization.")
  @NotNull

  @Size(max=256) 
  public String getOrgAuthServerId() {
    return orgAuthServerId;
  }

  public void setOrgAuthServerId(String orgAuthServerId) {
    this.orgAuthServerId = orgAuthServerId;
  }

  public TimePerUserReport totalTime(Long totalTime) {
    this.totalTime = totalTime;
    return this;
  }

  /**
   * Total amount of time (in seconds) spent inside the organization by the user.
   * @return totalTime
  */
  @ApiModelProperty(required = true, value = "Total amount of time (in seconds) spent inside the organization by the user.")
  @NotNull


  public Long getTotalTime() {
    return totalTime;
  }

  public void setTotalTime(Long totalTime) {
    this.totalTime = totalTime;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TimePerUserReport timePerUserReport = (TimePerUserReport) o;
    return Objects.equals(this.placeId, timePerUserReport.placeId) &&
        Objects.equals(this.orgAuthServerId, timePerUserReport.orgAuthServerId) &&
        Objects.equals(this.totalTime, timePerUserReport.totalTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(placeId, orgAuthServerId, totalTime);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TimePerUserReport {\n");
    
    sb.append("    placeId: ").append(toIndentedString(placeId)).append("\n");
    sb.append("    orgAuthServerId: ").append(toIndentedString(orgAuthServerId)).append("\n");
    sb.append("    totalTime: ").append(toIndentedString(totalTime)).append("\n");
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

