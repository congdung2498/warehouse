package com.viettel.vtnet360.issuesService.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

public class ServiceEntity extends BaseEntity {

  /** name of service */
  private String serviceName;

  /** id of service */
  private String serviceId;

  /** fullFillTime of service */
  private Long fullFillTime;

  /** status of service */
  private Long status;

  /** id of place */
  private String placeId;

  /** name of place */
  private String placeName;

  public ServiceEntity() {
    super();
  }

  public String getServiceName() {
    return serviceName;
  }

  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

  public String getServiceId() {
    return serviceId;
  }

  public void setServiceId(String serviceId) {
    this.serviceId = serviceId;
  }

  public Long getFullFillTime() {
    return fullFillTime;
  }

  public void setFullFillTime(Long fullFillTime) {
    this.fullFillTime = fullFillTime;
  }

  public Long getStatus() {
    return status;
  }

  public void setStatus(Long status) {
    this.status = status;
  }

  public String getPlaceId() {
    return placeId;
  }

  public void setPlaceId(String placeId) {
    this.placeId = placeId;
  }

  public String getPlaceName() {
    return placeName;
  }

  public void setPlaceName(String placeName) {
    this.placeName = placeName;
  }

}
