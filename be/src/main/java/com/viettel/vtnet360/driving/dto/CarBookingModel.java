package com.viettel.vtnet360.driving.dto;

import java.util.List;

import com.viettel.vtnet360.vt03.vt030002.entity.Seat;
import com.viettel.vtnet360.vt03.vt030014.entity.VT030014ListCondition;

public class CarBookingModel {
  
  private List<VT030014ListCondition> squads;
  private List<VT030014ListCondition> carTypes;
  private List<VT030014ListCondition> journeyTypes;
  private List<VT030014ListCondition> places;
  private List<Seat>                  seats;
  
  
  public List<VT030014ListCondition> getSquads() { return squads; }
  public void setSquads(List<VT030014ListCondition> squads) { this.squads = squads; }
  
  public List<VT030014ListCondition> getCarTypes() { return carTypes; }
  public void setCarTypes(List<VT030014ListCondition> carTypes) { this.carTypes = carTypes; }
  
  public List<Seat> getSeats() { return seats; }
  public void setSeats(List<Seat> seats) { this.seats = seats; }
  
  public List<VT030014ListCondition> getJourneyTypes() { return journeyTypes; }
  public void setJourneyTypes(List<VT030014ListCondition> journeyTypes) { this.journeyTypes = journeyTypes; }
  
  public List<VT030014ListCondition> getPlaces() { return places; }
  public void setPlaces(List<VT030014ListCondition> places) { this.places = places; }
}
