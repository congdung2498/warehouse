package com.viettel.vtnet360.issuesService.entity;

public class StationeryEntity {
  private String loginUserName;

  /** role of login user */
  private String loginRole;

  /** id of stationery */
  private String stationeryId;

  /** name of stationery */
  private String stationeryName;

  /** type of stationery */
  private String stationeryType;

  /** price of stationery */
  private int stationeryPrice;

  /** unit caculation of stationery */
  private String stationeryUnitCal;

  /** status of stationer */
  private String status;

  /** master class of stationery type */
  private String masterClassType;

  /** master class of stationery unit calculation */
  private String masterClassUnit;

  /** code value of stationery type */
  private String codeTypeValue;

  /** code value of stationery unit calculation */
  private String codeUnitValue;

  /** select from which row */
  private int startRow;

  /** number of row to select */
  private int rowSize;
}
