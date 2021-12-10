package com.viettel.vtnet360.vt05.vt050002.service;

import java.util.ArrayList;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt05.vt050002.dao.VT050002DAO;
import com.viettel.vtnet360.vt05.vt050002.entity.Employee;
import com.viettel.vtnet360.vt05.vt050002.entity.Receiver;
import com.viettel.vtnet360.vt05.vt050002.entity.Role;
import com.viettel.vtnet360.vt05.vt050002.entity.Unit;
import com.viettel.vtnet360.vt05.vt050002.entity.Place;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT050002ServiceImpl implements VT050002Service {

	private final Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private VT050002DAO vt050002DAO;

	public List<Integer> listChosenUnit = new ArrayList<>();

	
	@Override
	public List<Place> findPlaceByHCDV(Place place) {
	  return vt050002DAO.findPlaceByHCDV(place);
	}
	@Override
	public List<Place> findPlaceByVPTCT(Place place) {
	  return vt050002DAO.findPlaceByVPTCT(place);
	}
	@Override
	public ResponseEntityBase getReceiver() {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		String role = "";
		role = Constant.STATIONERY_HCDV_CLASS;

		List<Receiver> listReceiver = null;
		try {
			listReceiver = vt050002DAO.getReceiver(role);
			resp.setData(listReceiver);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase getRole() {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<Role> listRole = null;
		String role = "";
		try {
			role = Constant.STATIONERY_HCDV_CLASS;
			listRole = vt050002DAO.getRole(role);
			resp.setData(listRole);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase findEmployee(Employee employee) {

		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<Employee> listEmployee = null;
		if (employee.getRole().equals("01")) {
			if (employee.getListUnit() == null || employee.getListUnit().size()==0) {
				resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
				return resp;
			}
			employee.setUnit(employee.getUnit());
		} else if (employee.getRole().equals("02")) {
			List<Integer>listUnit= new ArrayList<>();
			listUnit.add(240541);
			employee.setListUnit(listUnit);
			//employee.setUnit("240541");
		} 
		try {
			listEmployee = vt050002DAO.findEmployee(employee);
			resp.setData(listEmployee);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase findUnit(Unit unit) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<Unit> listUnit = null;
		try {
			listUnit = vt050002DAO.findUnit(unit);
			resp.setData(listUnit);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase findPlace(Place place) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<Place> listPlace = null;

		try {
			listPlace = vt050002DAO.findPlace(place);
			resp.setData(listPlace);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase searchReceiver(Receiver receiver) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<Receiver> listReceiver = new ArrayList<>();

		receiver.setRoleId(Constant.STATIONERY_HCDV_CLASS);
		try {
			listReceiver = vt050002DAO.searchReceiver(receiver);
			resp.setData(listReceiver);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase insertReceiver(Receiver receiver) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		int duplicate = 0;
		String role = Constant.STATIONERY_HCDV_CLASS;
		List<Receiver> listReceiver = vt050002DAO.getReceiver(role);
		for (Receiver item : listReceiver) {
			if (item.getUserName() != null && item.getUserName().equals(receiver.getUserName())) {
				duplicate = 1;
				break;
			}
		}

		if (duplicate == 1) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			resp.setData(null);
		} else {
			try {
				int i = vt050002DAO.insertReceiver(receiver);
				if (i == 1) {
					vt050002DAO.insertPlace(receiver);
					resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
					return resp;
				} else {
					resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
					return resp;
				}
			} catch (Exception e) {
				resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
				resp.setData(null);
			}
		}
		return resp;
	}

	@Override
	public ResponseEntityBase updateReceiver(Receiver receiver) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		String role = Constant.STATIONERY_HCDV_CLASS;
		List<Receiver> listReceiver = vt050002DAO.getReceiver(role);
		int checked = 0;
		int status = vt050002DAO.checkDeleted(receiver);
		if (status == 1) {
			resp.setStatus(Constant.ERROR_UPDATE);
			return resp;
		}
		for (Receiver item : listReceiver) {
			if (item.getUserName() != null && item.getUserName().equals(receiver.getUserName())
					&& !item.getReceiverId().equals(receiver.getReceiverId())) {
				checked++;
			}
		}

		if (checked == 0) {
			try {
				int i = vt050002DAO.updateReceiver(receiver);
				if (i == 1) {
					resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
					vt050002DAO.deletePlace(receiver);
					vt050002DAO.insertPlace(receiver);
					return resp;
				} else {
					resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
					return resp;
				}
			} catch (Exception e) {
				resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
				resp.setData(null);
			}
		} else {
			resp.setStatus(Constant.RESPONSE_ERROR_DUPLICATEKEY);
			resp.setData(null);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase deleteReceiver(Receiver receiver) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		int status = vt050002DAO.checkDeleted(receiver);
		if (status == 1) {
			resp.setStatus(Constant.ERROR_UPDATE);
			return resp;
		}
		try {
			int i = vt050002DAO.deleteReceiver(receiver);
			if (i == 1) {
				resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
				return resp;
			} else {
				resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
				return resp;
			}
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			resp.setData(null);
		}
		return resp;
	}

}
