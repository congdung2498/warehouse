package com.viettel.vtnet360.vt02.vt020001.service;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt02.vt020001.entity.VT020001Place;

/**
 * Class service for screen VT020001 : setting placeInfo
 * 
 * @author DuyNK 08/09/2018
 */
public interface VT020001Service {

	/**
	 * Select all placeInfo from QLDV_PLACE
	 * 
	 * @param placeInfoInfo
	 * @return ResponseEntityBase(status,List<VT020001Place>)
	 */
	public ResponseEntityBase findListPlace(VT020001Place placeInfoInfo);

	/**
	 * count total record of places
	 * 
	 * @param placeInfo
	 * @return number of record
	 */
	public ResponseEntityBase counTotalRecord(VT020001Place placeInfo);

	/**
	 * Insert new placeInfo to QLDV_PLACE
	 * 
	 * @param VT020001Place
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase insertPlace(VT020001Place placeInfo);

	/**
	 * update placeInfo to QLDV_PLACE
	 * 
	 * @param VT020001Place
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase updatePlace(VT020001Place placeInfo);

	/**
	 * Delete placeInfo in QLDV_PLACE
	 * 
	 * @param VT020001Place
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase deletePlace(VT020001Place placeInfo);
}
