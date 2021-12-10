package com.viettel.vtnet360.driving.request.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.driving.dto.SearchData;
import com.viettel.vtnet360.driving.entity.Cars;
import com.viettel.vtnet360.driving.entity.DrivesSquad;
import com.viettel.vtnet360.driving.entity.ListCarBooking;
import com.viettel.vtnet360.driving.entity.ListTrip;
import com.viettel.vtnet360.driving.entity.Manager;
import com.viettel.vtnet360.driving.entity.Place;
import com.viettel.vtnet360.vt03.vt030000.entity.Employee;
import com.viettel.vtnet360.vt03.vt030012.entity.Ratting;
import com.viettel.vtnet360.vt03.vt030014.entity.VT030014ListCondition;

@Repository
@Transactional
public interface DrivingDAO {

	public List<VT030014ListCondition> searchData(String searchDTO);

	public List<VT030014ListCondition> searchManagerUnit(String searchDTO);

	public List<VT030014ListCondition> searchManagerChief(String searchDTO);

	public List<Place> searchPlaceStart(SearchData searchData);

	public List<VT030014ListCondition> searchPlaceName(SearchData searchData);
	
	public List<VT030014ListCondition> searchPlaceStartAll();

	public String searchUnit(int searchDTO);

	public String searchFullName(int searchDTO);

	public String searchPhoneNumber(int searchDTO);

	public String searchEmail(int searchDTO);

	public List<Manager> searchDriveName(String searchDTO);

	public List<String> searchLicensePlate(String searchDTO);

	/**
	 * get list car booking
	 * 
	 * @param condition
	 * @return List<VT030014ListTrip>
	 */
	public List<VT030014ListCondition> findAllListTrip(VT030014ListCondition condition);

	/**
	 * update car booking
	 * 
	 * @param
	 * @return
	 */
	
	public void updateBooking(ListTrip listTrip);
	
	public void updateCarDetails(ListTrip listTrip);

	public void updateRejectCarDetails(ListTrip listTrip);

	public int updateNewCarDetails(ListTrip listTrip);

	public int insertCarDetails(ListTrip listTrip);

	public void updateCompleteCarDetails(ListTrip listTrip);

	public void updateRenewCarDetails(ListTrip listTrip);

	public List<String> searchApproverQldv(String searchDTO);

	public List<String> searchApproverQltt(String searchDTO);

	public List<String> searchApproverQlcvp(String searchDTO);

	public List<ListTrip> searchDriveNameIsFree(ListTrip listTrip);

	public List<ListTrip> searchCarsIsFree(ListTrip listTrip);

	public List<Integer> searchAllStatusCar();

	public void updateCarJourney(ListTrip listTrip);

	public void updateCarRoute(ListTrip listTrip);

	public List<DrivesSquad> searchDriveSquadName(SearchData searchData);

	public List<Cars> searchLicensePlate(SearchData searchData);

	public List<Integer> searchAllStatusDrive();

	public List<Integer> searchAllStatusProcessDrive();

	public List<Integer> getStatusCar();

	public List<Cars> searchSuggestionLicensePlate(SearchData searchData);

	public List<VT030014ListCondition> getPlaceByAll();

	public String getPhoneByUserName(String userName);

	public Employee getPhoneBySquadLeader(String driverSquadId);
	
	public List<VT030014ListCondition> getQlttByUserName(SearchData searchData);
	
	public List<VT030014ListCondition> getQldvByUserName(SearchData searchData);
	
	public List<VT030014ListCondition> getQlcvpByUserName(SearchData searchData);
	
	public String searchRoueType(String searchDTO);
	
	public String getRoleByUser(String searchDTO);
	
	public void updateCarRefuse(ListTrip listTrip);
	
	public void updateCarApprove(ListTrip listTrip);
	
	public void updateListCarApprove(ListCarBooking listCarBooking );
	
	public List<VT030014ListCondition> findAllListTripById(VT030014ListCondition condition);
	
	public List<VT030014ListCondition> getQltt();
	
	public List<VT030014ListCondition> getQldv();
	
	public List<VT030014ListCondition> getCvp();
	
	public void updateCarRefuseOrder(ListTrip listTrip);
	
	public int updateRatting(Ratting rate);
	
	public void updateDriveCarApprove(ListTrip listTrip);
	
	public void updateCarOrderApprove(ListTrip listTrip);
	
	public void updateBookingCarApprove(ListTrip listTrip);
	
	public Long getDateStartOfReqest(String bookCarId);
	
	public Long getDateEndOfReqest(String bookCarId);
	
}
