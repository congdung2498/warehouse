package com.viettel.vtnet360.vt06.vt060001.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.vt06.vt060001.dao.VT060001DAO;
import com.viettel.vtnet360.vt06.vt060001.entity.SearchCondition;
import com.viettel.vtnet360.vt06.vt060001.entity.VersionDetail;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT060001ServiceImpl implements VT060001Service {

	@Autowired
	VT060001DAO vt060001dao;
	
	@Override
	public List<VersionDetail> getListVersionDetail(SearchCondition searchCondition) throws Exception {
		List<VersionDetail> result = vt060001dao.getListVersionDetail(searchCondition);
		return result != null ? result : new ArrayList<>();
	}

	@Override
	public boolean insertVersionDetail(VersionDetail insertCondition) throws Exception {
		int result;
		result = vt060001dao.insertVersionDetail(insertCondition);
		return result != 0;
	}
	
	
	@Override
	public boolean updateVersionDetail(VersionDetail insertCondition) throws Exception {
		int result;
		SearchCondition searchCondition = new SearchCondition();
		searchCondition.setVersionType(insertCondition.getVersionType());
		if (vt060001dao.getListVersionDetail(searchCondition) != null) {
			vt060001dao.updateStatusOldVersion(searchCondition);
			result = vt060001dao.insertVersionDetail(insertCondition);
		} else {
			vt060001dao.updateStatusOldVersion(searchCondition);
			result = vt060001dao.insertVersionDetail(insertCondition);
		}
		
		return result != 0;
	}
	
	@Override
	public boolean deleteVersionDetail(SearchCondition updateCondition) throws Exception {
		int result;
		result = vt060001dao.updateStatusOldVersion(updateCondition);
		return result != 0;
	}
	
	@Override
	public VersionDetail getVersionDetail(String deviceType) throws Exception {
		return vt060001dao.getVersionDetail(deviceType);
	}

	@Override
	public void setUserVersionDetail(String deviceType, String version, String userName) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("deviceType", deviceType);
		map.put("version", version);
		map.put("userName", userName);
		vt060001dao.setUserVersionDetail(map);
	}
}
