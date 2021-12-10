package com.viettel.vtnet360.driving.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.viettel.vtnet360.driving.dto.CarbookingNoti;
import com.viettel.vtnet360.driving.dto.SearchCarBooking;
import com.viettel.vtnet360.driving.dto.SearchData;
import com.viettel.vtnet360.driving.entity.ListCarBooking;
import com.viettel.vtnet360.driving.entity.ListTrip;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt03.vt030000.entity.Employee;
import com.viettel.vtnet360.vt03.vt030014.entity.VT030014ListCondition;

public interface DrivingService {
//TODO[Thanh]: clean
  public void createNotifyForMobileTest(String username);
  
  public CarbookingNoti getCarbookingById(SearchCarBooking config);
  
	public ResponseEntityBase searchData(String searchDTO);
	public ResponseEntityBase searchManagerUnit(String searchDTO);
	public ResponseEntityBase searchManagerChief(String searchDTO);
	public ResponseEntityBase searchPlaceStart(SearchData searchData);
	public ResponseEntityBase searchUnit(int searchDTO);
	public ResponseEntityBase searchPlaceName(SearchData searchData);
	public ResponseEntityBase searchPlaceStartAll();
	
	public ResponseEntityBase searchFullName(int searchDTO);
	public ResponseEntityBase searchPhoneNumber(int searchDTO);
	public ResponseEntityBase searchEmail(int searchDTO);
	public ResponseEntityBase searchDriveName(String searchDTO);
	public ResponseEntityBase searchLicensePlate(String searchDTO);
	public ResponseEntityBase findAllListTrip(VT030014ListCondition condition, Collection<GrantedAuthority> listRole)
			throws Exception;
	
	public ResponseEntityBase updateCarDetails(ListTrip listTrip, Collection<GrantedAuthority> listRole, String username);
	public ResponseEntityBase updateRejectCarDetails(ListTrip listTrip  );
	public ResponseEntityBase insertCarDetails(ListTrip listTrip);
	public ResponseEntityBase updateNewCarDetails(ListTrip listTrip);
	public ResponseEntityBase updateCompleteCarDetails(ListTrip listTrip);
	public ResponseEntityBase updateRenewCarDetails(ListTrip listTrip);
	public ResponseEntityBase updateCarJourney(ListTrip listTrip);
	public ResponseEntityBase updateCarRoute(ListTrip listTrip);
	public ResponseEntityBase updateCarRefuse(ListTrip listTrip  );
	public ResponseEntityBase updateCarApprove(ListTrip listTrip  );
	public ResponseEntityBase updateListCarApprove(ListCarBooking listCarBooking, Collection<GrantedAuthority> roleList);
	// search approve
	public ResponseEntityBase searchApproverQltt(String searchDTO);
	public ResponseEntityBase searchApproverQldv(String searchDTO);
	public ResponseEntityBase searchApproverQlcvp	(String searchDTO);
	public ResponseEntityBase searchDriveNameIsFree(ListTrip listTrip);
	public ResponseEntityBase searchCarsIsFree(ListTrip listTrip);
	public ResponseEntityBase searchAllStatusCar();
	
	public ResponseEntityBase searchAllStatusDrive();
	public ResponseEntityBase searchAllStatusProcessDrive();
	public ResponseEntityBase getStatusCar();
	public ResponseEntityBase searchDriveSquadName(SearchData searchData);
	public ResponseEntityBase searchLicensePlate(SearchData searchData);
	public ResponseEntityBase searchSuggestionLicensePlate(SearchData searchData);
	public ResponseEntityBase getPlaceByAll();

	public String getPhoneByUserName(String userName);

	public Employee getPhoneAndUserBySquadLeader(String driverSquadId);

	public ResponseEntityBase getQlttByUserName(SearchData searchData);

	public ResponseEntityBase getQldvByUserName(SearchData searchData);

	public ResponseEntityBase getQlcvpByUserName(SearchData searchData);

	public ResponseEntityBase searchRoueType(String searchDTO);
	
	public ResponseEntityBase getRoleByUser(String searchDTO);
	
	public ResponseEntityBase findAllListTripById(VT030014ListCondition condition, Collection<GrantedAuthority> listRole)
			throws Exception;
	
	public ResponseEntityBase getQltt();
	
	public ResponseEntityBase getQldv();
	
	public ResponseEntityBase getCvp();
	
	public ResponseEntityBase updateCarRefuseOrder(ListTrip listTrip  );
	
	public ResponseEntityBase updateCarOrder(ListTrip listTrip  );
}
