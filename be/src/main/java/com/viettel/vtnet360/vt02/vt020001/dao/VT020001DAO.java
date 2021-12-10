package com.viettel.vtnet360.vt02.vt020001.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.viettel.vtnet360.vt02.vt020001.entity.VT020001Place;

/**
 * Class DAO for screen VT020001 : setting place
 * 
 * @author DuyNK 20/9/2018
 */
@Repository
public interface VT020001DAO {

	/**
	 * get list place
	 * 
	 * @return List<VT020001Place>
	 */
	public List<VT020001Place> findPlace(VT020001Place placeInfo);

	/**
	 * count total record of places
	 * 
	 * @param placeInfo
	 * @return number of record
	 */
	public int countTotalRecord(VT020001Place placeInfo);

	/**
	 * check place code and place name are existed or not
	 * 
	 * @param place
	 * @return Integer
	 */
	public int checkPlaceExist(VT020001Place place);

	/**
	 * check place name was existed or not
	 * 
	 * @param place
	 * @return Integer
	 */
	public int checkPlaceUpdate(VT020001Place place);

	/**
	 * insert place
	 * 
	 * @param place
	 * @return Integer
	 */
	public int insertPlace(VT020001Place place);

	/**
	 * update place
	 * 
	 * @param place
	 * @return Integer
	 */
	public int updatePlace(VT020001Place place);

	/**
	 * delete place
	 * 
	 * @param listPlaceCode
	 * @return
	 */
	public void deletePlace(VT020001Place placeInfo);
	
	/**
	 * check condition before update place ( this place is deleted before or not)
	 * 
	 * @param placeCode
	 * @return Integer
	 */
	public int checkConditionBeforeUpdate(String placeCode);
}
