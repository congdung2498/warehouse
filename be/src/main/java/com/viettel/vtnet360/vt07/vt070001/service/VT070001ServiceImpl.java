package com.viettel.vtnet360.vt07.vt070001.service;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt07.vt070001.dao.VT070001DAO;
import com.viettel.vtnet360.vt07.vt070001.entity.VT070001EntityBarcodeDetail;
import com.viettel.vtnet360.vt07.vt070001.entity.VT070001EntityBarcodePrefix;
import com.viettel.vtnet360.vt07.vt070001.entity.VT070001EntityBarcodeRange;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT070001ServiceImpl implements VT070001Service {

	@Autowired
	VT070001DAO vt070001dao;
	
	@Autowired
	Properties warehouseMessage;

	private final Logger logger = Logger.getLogger(this.getClass());

	
	private int getUnit(Collection<GrantedAuthority> userRoles) {
		for (GrantedAuthority temp : userRoles) {
	        if (Constant.PMQT_ROLE_WAREHOUSE_GROUP.equalsIgnoreCase(temp.getAuthority())) {
	        	return 1;
	        }
		}
		return 0;
	}
	
	@Override
	public ResponseEntityBase findTypeBarcode(VT070001EntityBarcodePrefix obj, Collection<GrantedAuthority> userRoles) {
		int unit = getUnit(userRoles);
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		List<VT070001EntityBarcodePrefix> data = new ArrayList<>();
		try {
			data = unit == 1? vt070001dao.findTypeBarcodeGroup(obj)
					: vt070001dao.findTypeBarcode(obj);
			response = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, data);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return response;
	}



	@Override
	public ResponseEntityBase insertBarcodeRange(VT070001EntityBarcodeRange object, Collection<GrantedAuthority> userRoles) {
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		int unit = getUnit(userRoles);
		try {

			String checkXss =  object.getDescription();
			if(checkXss!=null) {
				checkXss =  validateXss(checkXss);
				object.setDescription(checkXss);
			}
			int rowEffect = unit == 1? vt070001dao.insertBarcodeRangeGroup(object)
					: vt070001dao.insertBarcodeRange(object);
			VT070001EntityBarcodeRange data = unit == 1? vt070001dao.getBarcodeRangeLastItemGroup(object.getBarCodeRangeId())
					: vt070001dao.getBarcodeRangeLastItem(object.getBarCodeRangeId());
			int rowEffectDetail = 0;
			String code = "";
			Timestamp ts = new Timestamp(data.getCreateDate().getTime());
			String a= ts.toString().replace("\\s+","");
			a=a.replace("[-+.^:,]", "");
			a=a.substring(2,4);
			int sequence = 0;
			String stt="";
			for(int i=0;i<object.getQuantity();i++) {
				sequence = unit == 1? vt070001dao.getBarcodeDetailSequenceGroup()
						: vt070001dao.getBarcodeDetailSequence();
				int se = 1000000+sequence;
				stt = String.valueOf(se).substring(1);
				code =data.getPrefix() + a +stt;
				VT070001EntityBarcodeDetail detail = new VT070001EntityBarcodeDetail(data.getBarCodeRangeId(),code,0,false,data.getCreateUser());
				rowEffectDetail = unit == 1? vt070001dao.insertBarcodeDetailGroup(detail)
						: vt070001dao.insertBarcodeDetail(detail);
			}
			if (rowEffect > 0 && rowEffectDetail > 0) {
				return new ResponseEntityBase(Constant.REQUEST_ACTION_INSERT, data);
			} else {
				return new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, data);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return response;
	}
	public String validateXss(String checkXss) {
		/*checkXss = checkXss.replace("&","&amp;");
		checkXss = checkXss.replace("\"","&quot;");
		checkXss = checkXss.replace("'","&#x27;");
		checkXss = checkXss.replace("/","&#x2F;");
		checkXss = checkXss.replace(">","&gt;");
		checkXss = checkXss.replace("<","&lt;");*/
		return checkXss;
	}


	@Override
	public ResponseEntityBase findBarcodeRange(VT070001EntityBarcodeRange object, Collection<GrantedAuthority> userRoles) {
		int unit = getUnit(userRoles);
		ResponseEntityBase response = null;
		List<VT070001EntityBarcodeRange> data = new ArrayList<>();
		try {

			String checkXss =  object.getDescription();
			if(checkXss!=null) {
				checkXss = validateXss(checkXss);
				if(checkXss.length()>255) {
					checkXss = checkXss.substring(0, 255);
				}
				object.setDescription(checkXss);
			}

			int totalRecords = unit ==1 ? vt070001dao.getTotalRecordGroup(object)
					: vt070001dao.getTotalRecord(object);
			data = unit ==1 ? vt070001dao.findBarcodeRangeGroup(object)
					: vt070001dao.findBarcodeRange(object);
			if (!data.isEmpty()) {
				if (object.getPageNumber() == 0 && data.size() < object.getPageSize()) {
					totalRecords = data.size();
				}
				data.get(0).setTotalRecords(totalRecords);
			}
			response = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, data);
		} catch (Exception e) {
			response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, data);
			logger.error(e.getMessage(), e);
		}
		return response;
	}



	@Override
	public ResponseEntityBase findBarcodeDetail(VT070001EntityBarcodeRange object, Collection<GrantedAuthority> userRoles) {
		int unit = getUnit(userRoles);
		ResponseEntityBase response = null;
		List<VT070001EntityBarcodeDetail> data = new ArrayList<>();
		try {

			data = unit == 1? vt070001dao.findBarcodeDetailGroup(object.getBarCodeRangeId())
					: vt070001dao.findBarcodeDetail(object.getBarCodeRangeId());

			response = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, data);
		} catch (Exception e) {
			response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, data);
			logger.error(e.getMessage(), e);
		}
		return response;
	}



	@Override
	public ResponseEntityBase changeBarcodeRangeDetail(VT070001EntityBarcodeRange object, Collection<GrantedAuthority> userRoles) {
		int unit = getUnit(userRoles);
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		try {
			int rowEffect = unit == 1? vt070001dao.updateBarcodeRangeGroup(object.getBarCodeRangeId())
					: vt070001dao.updateBarcodeRange(object.getBarCodeRangeId());
			if (rowEffect  > 0) {
				return new ResponseEntityBase(Constant.REQUEST_ACTION_INSERT, null);
			} else {
				return new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return response;
	}



	@Override
	public ResponseEntityBase checkChangeBarcodeRange(VT070001EntityBarcodeRange object, Collection<GrantedAuthority> userRoles) {
		int unit = getUnit(userRoles);
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		try {

			VT070001EntityBarcodeRange rowEffect = unit == 1? vt070001dao.checkChangeBarcodeRangeGroup(object.getBarCodeRangeId())
					: vt070001dao.checkChangeBarcodeRange(object.getBarCodeRangeId());

			if (rowEffect.isPrinted()) {
				return new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
			} else {
				return new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return response;
	}

	@Override
	public ResponseEntityBase checkBarcodeAvailable(VT070001EntityBarcodeDetail object, Collection<GrantedAuthority> userRoles) {
		int unit = getUnit(userRoles);
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		try {
			List<VT070001EntityBarcodeDetail> barCodes = unit == 1? vt070001dao.checkBarcodeIsAvailGroup(object)
					: vt070001dao.checkBarcodeIsAvail(object);
			if(barCodes == null || barCodes.size() != 1)
			{
				object.setError(MessageFormat.format(warehouseMessage.getProperty("0700001"), object.getCode()));
				//object.setError("0700001#QR Code " + object.getCode() + " was used or not exist");
			}
			return new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, object);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return response;
	}

}
