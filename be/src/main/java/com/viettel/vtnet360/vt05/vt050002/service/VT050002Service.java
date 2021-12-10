package com.viettel.vtnet360.vt05.vt050002.service;

import java.util.List;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt05.vt050002.entity.Employee;
import com.viettel.vtnet360.vt05.vt050002.entity.Receiver;
import com.viettel.vtnet360.vt05.vt050002.entity.Unit;
import com.viettel.vtnet360.vt05.vt050002.entity.Place;


public interface VT050002Service {
  public List<Place> findPlaceByHCDV(Place place);
  
	public ResponseEntityBase getReceiver();

	public ResponseEntityBase searchReceiver(Receiver receiver);

	public ResponseEntityBase getRole();

	public ResponseEntityBase findEmployee(Employee employee);

	public ResponseEntityBase findUnit(Unit unit);

	public ResponseEntityBase findPlace(Place place);

	public ResponseEntityBase insertReceiver(Receiver receiver);

	public ResponseEntityBase updateReceiver(Receiver receiver);

	public ResponseEntityBase deleteReceiver(Receiver receiver);
	
	public List<Place> findPlaceByVPTCT(Place place);
}
