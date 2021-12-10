package com.viettel.vtnet360.vt05.vt050002.dao;

import java.util.List;

import com.viettel.vtnet360.vt05.vt050002.entity.Employee;
import com.viettel.vtnet360.vt05.vt050002.entity.Receiver;
import com.viettel.vtnet360.vt05.vt050002.entity.Role;
import com.viettel.vtnet360.vt05.vt050002.entity.Place;
import com.viettel.vtnet360.vt05.vt050002.entity.Unit;

public interface VT050002DAO {
	public List<Receiver> getReceiver(String code);

	public List<Receiver> searchReceiver(Receiver receiver);

	public List<Role> getRole(String role);

	public List<Employee> findEmployee(Employee employee);

	public List<Unit> findUnit(Unit unit);

	public List<Place> findPlace(Place place);
	
	public List<Place> findPlaceByHCDV(Place place);

	public int insertReceiver(Receiver receiver);

	public int updateReceiver(Receiver receiver);

	public int deleteReceiver(Receiver receiver);

	public int checkDeleted(Receiver receiver);

	public void deletePlace(Receiver receiver);

	public void insertPlace(Receiver receiver);
	
	public List<Place> findPlaceByVPTCT(Place place);

}
