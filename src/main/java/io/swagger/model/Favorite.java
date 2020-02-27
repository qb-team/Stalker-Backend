package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.threeten.bp.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Link between the user and the organization: only the organization which users sets to be their favorite can track their movements.
 */
@ApiModel(description = "Link between the user and the organization: only the organization which users sets to be their favorite can track their movements.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-02-27T15:59:20.699Z[GMT]")
public class Favorite   {
  @JsonProperty("userId")
  private String userId = null;

  @JsonProperty("organizationId")
  private Long organizationId = null;

  @JsonProperty("creationDate")
  private OffsetDateTime creationDate = null;

  public Favorite userId(String userId) {
    this.userId = userId;
    return this;
  }

  /**
   * Authentication service's user unique identifier.
   * @return userId
  **/
  @ApiModelProperty(required = true, value = "Authentication service's user unique identifier.")
      @NotNull

    public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Favorite organizationId(Long organizationId) {
    this.organizationId = organizationId;
    return this;
  }

  /**
   * Unique identifier of the organization the user sets as favorite.
   * @return organizationId
  **/
  @ApiModelProperty(required = true, value = "Unique identifier of the organization the user sets as favorite.")
      @NotNull

    public Long getOrganizationId() {
    return organizationId;
  }

  public void setOrganizationId(Long organizationId) {
    this.organizationId = organizationId;
  }

  public Favorite creationDate(OffsetDateTime creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  /**
   * When the favorite was added by the user.
   * @return creationDate
  **/
  @ApiModelProperty(required = true, value = "When the favorite was added by the user.")
      @NotNull

    @Valid
    public OffsetDateTime getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(OffsetDateTime creationDate) {
    this.creationDate = creationDate;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Favorite favorite = (Favorite) o;
    return Objects.equals(this.userId, favorite.userId) &&
        Objects.equals(this.organizationId, favorite.organizationId) &&
        Objects.equals(this.creationDate, favorite.creationDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, organizationId, creationDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Favorite {\n");
    
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    organizationId: ").append(toIndentedString(organizationId)).append("\n");
    sb.append("    creationDate: ").append(toIndentedString(creationDate)).append("\n");
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
