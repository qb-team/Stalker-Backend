package it.qbteam.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Min;

/**
 * Represents a constraint for an organization.
 */
@ApiModel(description = "Represents a constraint for an organization.")
@Entity
public class OrganizationConstraint   {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @JsonProperty("organizationId")
  private Long organizationId;

  @JsonProperty("maxArea")
  private Double maxArea;

  public OrganizationConstraint organizationId(Long organizationId) {
    this.organizationId = organizationId;
    return this;
  }

  /**
   * Unique identifier of the organization.
   * minimum: 1
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

  public OrganizationConstraint maxArea(Double maxArea) {
    this.maxArea = maxArea;
    return this;
  }

  /**
   * Maximum tracking area size, in square meters. This includes both the organization's tracking area and thus the place's area.
   * @return maxArea
  */
  @ApiModelProperty(required = true, value = "Maximum tracking area size, in square meters. This includes both the organization's tracking area and thus the place's area.")
  @NotNull

  @Valid

  public Double getMaxArea() {
    return maxArea;
  }

  public void setMaxArea(Double maxArea) {
    this.maxArea = maxArea;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrganizationConstraint organizationConstraint = (OrganizationConstraint) o;
    return Objects.equals(this.organizationId, organizationConstraint.organizationId) &&
        Objects.equals(this.maxArea, organizationConstraint.maxArea);
  }

  @Override
  public int hashCode() {
    return Objects.hash(organizationId, maxArea);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationConstraint {\n");
    
    sb.append("    organizationId: ").append(toIndentedString(organizationId)).append("\n");
    sb.append("    maxArea: ").append(toIndentedString(maxArea)).append("\n");
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

