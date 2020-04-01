package it.qbteam.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.OffsetDateTime;
import org.openapitools.jackson.nullable.JsonNullable;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Represents a row in the report generated for an user by the viewer administrator.
 */
@ApiModel(description = "Represents a row in the report generated for an user by the viewer administrator.")

public class TimePerUserReport   {
  @JsonProperty("organizationId")
  private Long organizationId;

  @JsonProperty("orgAuthServerId")
  private String orgAuthServerId;

  @JsonProperty("totalTime")
  private OffsetDateTime totalTime;

  public TimePerUserReport organizationId(Long organizationId) {
    this.organizationId = organizationId;
    return this;
  }

  /**
   * Unique identifier of the organization in which the user had access.
   * @return organizationId
  */
  @ApiModelProperty(required = true, value = "Unique identifier of the organization in which the user had access.")
  @NotNull


  public Long getOrganizationId() {
    return organizationId;
  }

  public void setOrganizationId(Long organizationId) {
    this.organizationId = organizationId;
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


  public String getOrgAuthServerId() {
    return orgAuthServerId;
  }

  public void setOrgAuthServerId(String orgAuthServerId) {
    this.orgAuthServerId = orgAuthServerId;
  }

  public TimePerUserReport totalTime(OffsetDateTime totalTime) {
    this.totalTime = totalTime;
    return this;
  }

  /**
   * Total amount of time spent inside the organization by the user.
   * @return totalTime
  */
  @ApiModelProperty(required = true, value = "Total amount of time spent inside the organization by the user.")
  @NotNull

  @Valid

  public OffsetDateTime getTotalTime() {
    return totalTime;
  }

  public void setTotalTime(OffsetDateTime totalTime) {
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
    return Objects.equals(this.organizationId, timePerUserReport.organizationId) &&
        Objects.equals(this.orgAuthServerId, timePerUserReport.orgAuthServerId) &&
        Objects.equals(this.totalTime, timePerUserReport.totalTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(organizationId, orgAuthServerId, totalTime);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TimePerUserReport {\n");
    
    sb.append("    organizationId: ").append(toIndentedString(organizationId)).append("\n");
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

