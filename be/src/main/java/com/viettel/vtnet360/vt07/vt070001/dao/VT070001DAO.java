package com.viettel.vtnet360.vt07.vt070001.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.viettel.vtnet360.vt07.vt070001.entity.VT070001EntityBarcodeDetail;
import com.viettel.vtnet360.vt07.vt070001.entity.VT070001EntityBarcodePrefix;
import com.viettel.vtnet360.vt07.vt070001.entity.VT070001EntityBarcodeRange;

@Repository
public interface VT070001DAO {


	
	public List<VT070001EntityBarcodePrefix> findTypeBarcode(VT070001EntityBarcodePrefix obj);
	
	public int insertBarcodeRange(VT070001EntityBarcodeRange object);
	
	public int insertBarcodeDetail(VT070001EntityBarcodeDetail object);
	
	public VT070001EntityBarcodeRange getBarcodeRangeLastItem(int barCodeRangeId);
	
	public int getTotalRecord(VT070001EntityBarcodeRange object);	
	
	public List<VT070001EntityBarcodeRange> findBarcodeRange(VT070001EntityBarcodeRange object);
	
	public List<VT070001EntityBarcodeDetail> findBarcodeDetail(int barCodeRangeId);
	
	public int updateBarcodeRange(int barCodeRangeId);
	
	public int updateBarcodeDetail(int barCodeRangeId);
	
	public List<VT070001EntityBarcodeDetail> checkBarcodeIsAvail(VT070001EntityBarcodeDetail barCodeDetailRequest);
	
	public int updateStatusBarcodeDetail(VT070001EntityBarcodeDetail barCodeDetailRequest);
	 
	public VT070001EntityBarcodeRange checkChangeBarcodeRange(int barCodeRangeId);
	
	public int getBarcodeDetailSequence();
	
	// Group region
	
	public List<VT070001EntityBarcodePrefix> findTypeBarcodeGroup(VT070001EntityBarcodePrefix obj);
	public int insertBarcodeRangeGroup(VT070001EntityBarcodeRange object);	
	public int insertBarcodeDetailGroup(VT070001EntityBarcodeDetail object);	
	public VT070001EntityBarcodeRange getBarcodeRangeLastItemGroup(int barCodeRangeId);	
	public int getTotalRecordGroup(VT070001EntityBarcodeRange object);	
	public List<VT070001EntityBarcodeRange> findBarcodeRangeGroup(VT070001EntityBarcodeRange object);
	public List<VT070001EntityBarcodeDetail> findBarcodeDetailGroup(int barCodeRangeId);	
	public int updateBarcodeRangeGroup(int barCodeRangeId);	
	public int updateBarcodeDetailGroup(int barCodeRangeId);	
	public List<VT070001EntityBarcodeDetail> checkBarcodeIsAvailGroup(VT070001EntityBarcodeDetail barCodeDetailRequest);
	public int updateStatusBarcodeDetailGroup(VT070001EntityBarcodeDetail barCodeDetailRequest);	 
	public VT070001EntityBarcodeRange checkChangeBarcodeRangeGroup(int barCodeRangeId);	
	public int getBarcodeDetailSequenceGroup();
}
