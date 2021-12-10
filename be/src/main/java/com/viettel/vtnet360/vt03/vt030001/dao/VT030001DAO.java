package com.viettel.vtnet360.vt03.vt030001.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.vt03.vt030000.entity.VT030000EntityDriveSquad;
import com.viettel.vtnet360.vt03.vt030000.entity.VT030000EntityEmployee;

/**
 * 
 * @author CuongHD
 *
 */
@Transactional
public interface VT030001DAO {
		/**
		 * tim kiem tat ca truong ban xe theo vi tri
		 * 
		 * @param int placeId
		 * @return List<VT030000EntityEmployee>
		 */
		public List<VT030000EntityEmployee> findDriver(VT030000EntityEmployee obj);
		
		/**
		 * Tim kiem doi xe theo dieu kien
		 * 
		 * @param VT030000EntityDriveSquad object
		 * @return List<VT030000EntityDriveSquad>
		 */
		public List<VT030000EntityDriveSquad> findSquad(VT030000EntityDriveSquad object);
		
		/**
		 * Them moi doi xe
		 * 
		 * @param VT030000EntityDriveSquad object
		 * @return int
		 */
		public int insertDriveSquad(VT030000EntityDriveSquad object);
		
		/**
		 * 
		 * 
		 * @param VT030000EntityDriveSquad object
		 * @return int 
		 */
		public int checkDuplicate(VT030000EntityDriveSquad object);
		
		/**
		 * 
		 * @param VT030000EntityDriveSquad object
		 * @return int 
		 */
		public int checkDuplicate1(VT030000EntityDriveSquad object);
		
		/**
		 * 
		 * @param VT030000EntityDriveSquad object
		 * @return int 
		 */
		public int update(VT030000EntityDriveSquad object);
		
		/**
		 * Xoa tat ca xe trrong doi xe
		 * 
		 * @param String squadId
		 * @return int 
		 */
		public int deleteCars(String squadId);
		
		/**
		 * Xoa tat ca lai xe trong doi xe
		 * 
		 * @param String squadId
		 * @return int 
		 */
		public int deleteDriver(String squadId);
		
		/**
		 * xoa tat ca xe duoc gan cho lai xe thuoc doi xe
		 * 
		 * @param String squadId
		 * @return int 
		 */
		public int deleteCarDriver(String squadId);
		
		/**
		 * Xoa doi xe
		 * 
		 * @param VT030000EntityDriveSquad object
		 * @return int 
		 */
		public int deleteSquad(VT030000EntityDriveSquad object);
		
		/**
		 * Check all Car belongs DriveSquadId has Free?
		 * 
		 * @param id
		 * @return int 
		 */
		public int isExistActiveCar(String id);
		
		/**
		 * find placeId 
		 * 
		 * @param String username
		 * @return int
		 */
		public int selectPlaceId(String username);
		
		/**
		 * Update phone number 
		 * 
		 * @param String username
		 * @return int
		 */
		public int updatePhone(VT030000EntityDriveSquad obj);
		
		/**
		 * get status
		 * 
		 * @param String squadId
		 * @return int
		 */
		public int getStatus(String squadId);
		
		/**
		 * get total record 
		 * 
		 * @return int
		 */
		public int getTotalRecord();	
		
		/**
		 * 
		 * @param squadId
		 * @return int
		 */
		public int isDelete(String squadId);
		
		/**
		 * 
		 * @param username
		 * @return int 
		 */
		public int checkExistedManagerSquad(String username);
		
		/**
		 * 
		 * @param driveId
		 * @return String 
		 */
		public String selectUserManagerCarSquad(String driveId);
			
		

}
