package com.viettel.vtnet360.vt03.vt030012.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt03.vt030009.dao.VT030009DAO;
import com.viettel.vtnet360.vt03.vt030009.entity.VT030009ResponseCarRoute;
import com.viettel.vtnet360.vt03.vt030009.entity.VT030009UpdateProcessCarRoute;
import com.viettel.vtnet360.vt03.vt030012.dao.VT030012DAO;
import com.viettel.vtnet360.vt03.vt030012.entity.Ratting;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT030012ServiceImpl implements VT030012Service {

	@Autowired
	private VT030012DAO vt030012dao;
	
	@Autowired
	private VT030009DAO vt030009dao;

	@Override
	public ResponseEntityBase updateRatting(Ratting rate) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);

		try {
			int i = vt030012dao.updateRatting(rate);
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

	@Override
	public ResponseEntityBase updateRattingUser(Ratting rate) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);

		try {
			int i = 0;
			List<VT030009UpdateProcessCarRoute> listUpdateProcessCarRoute = null;
			VT030009UpdateProcessCarRoute obj = new VT030009UpdateProcessCarRoute();
			obj.setBookCarId(rate.getBookCarId());
			VT030009ResponseCarRoute vt030009ResponseCarRoute = vt030009dao.findCarRoute(obj);
			Integer carTypeBooking = vt030009ResponseCarRoute.getCarBookingType();
			if(carTypeBooking != null && Constant.CAR_BOOKING_TYPE_DEFAULT == carTypeBooking) {
				listUpdateProcessCarRoute = vt030009dao.getInforUpdateProcessCarRoute(vt030009ResponseCarRoute);
				for (VT030009UpdateProcessCarRoute vt030009UpdateProcessCarRoute : listUpdateProcessCarRoute) {
					rate.setBookCarId(vt030009UpdateProcessCarRoute.getBookCarId());
					i = vt030012dao.updateRattingUser(rate);
					if (i != 1) {
						resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
						return resp;
					}
				}
			} else {
				i = vt030012dao.updateRattingUser(rate);
			}
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
