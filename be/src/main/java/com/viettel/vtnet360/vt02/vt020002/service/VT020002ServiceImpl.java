package com.viettel.vtnet360.vt02.vt020002.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.viettel.vtnet360.kitchen.dto.ChefConfig;
import com.viettel.vtnet360.kitchen.menu.dao.KitchenDAO;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.InputValidate;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt02.vt020002.dao.VT020002DAO;
import com.viettel.vtnet360.vt02.vt020002.entity.VT020002Dish;
import com.viettel.vtnet360.vt02.vt020002.entity.VT020002DishOffset;
import com.viettel.vtnet360.vt02.vt020002.entity.VT020002InfoToCheckKitchenExist;
import com.viettel.vtnet360.vt02.vt020002.entity.VT020002InfoToDeleteDishInMenu;
import com.viettel.vtnet360.vt02.vt020002.entity.VT020002InfoToFindKitchen;
import com.viettel.vtnet360.vt02.vt020002.entity.VT020002Kitchen;

/**
 * Class serviceImpl for screen VT020002 : setting dish
 * 
 * @author DuyNK 09/08/2018
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT020002ServiceImpl implements VT020002Service {

	private final Logger logger = Logger.getLogger(this.getClass());
	private InputValidate validate = new InputValidate();

	@Autowired
	private VT020002DAO vt020002DAO;
	
	@Autowired
	private KitchenDAO kitchenDAO;

	/**
	 * Select all kitchen from KITCHEN_SETTING
	 * 
	 * @param info
	 * @return ResponseEntity
	 */
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findListKitchen(VT020002InfoToFindKitchen info) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<VT020002Kitchen> listKitchen = null;
		try {
			info.setStatus(Constant.STATUS_ACTIVE);
			info.setDeleteFG(Constant.DELETE_FG_ACTIVE);
			listKitchen = vt020002DAO.findListKitchen(info);
			resp.setData(listKitchen);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	/**
	 * Select list dish from DISH_SETTING by KITCHEN_ID, DISH_NAME, limit record by
	 * pageNumber and page size
	 * 
	 * @param dishOffset
	 * @return ResponseEntity
	 */
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findListDish(VT020002DishOffset dishOffset) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<VT020002Dish> listDish = null;

		// validate kitchenID (string)
		if (dishOffset.getKitchenID() != null) {
			dishOffset.setKitchenID(dishOffset.getKitchenID().trim());
			if (dishOffset.getKitchenID().trim().equals("")) {
				dishOffset.setKitchenID(null);
			}
		}

		// validate dishName (string)
		if (StringUtils.isBlank(dishOffset.getDishName())) {
			dishOffset.setDishName(null);
		}
		try {
			dishOffset.setDeleteFG(Constant.DELETE_FG_ACTIVE);
			dishOffset.setPageNumber((dishOffset.getPageNumber()-1) * dishOffset.getPageSize());
			listDish = vt020002DAO.findListDish(dishOffset);
			resp.setData(listDish);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	/**
	 * Select kitchenInfoOfThisChef
	 * 
	 * @param VT020002DishOffset
	 * @return ResponseEntity
	 */
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findKitchenInfoOfThisChef(String userName) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		VT020002Kitchen kitchen = null;
		try {
			VT020002InfoToFindKitchen info = new VT020002InfoToFindKitchen();
			info.setChefUserName(userName);
			info.setDeleteFG(Constant.DELETE_FG_ACTIVE);
			kitchen = vt020002DAO.findKitchenByUserName(info);
			if (kitchen == null) {
				resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
				return resp;
			}
			resp.setData(kitchen);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	/**
	 * Insert new Dish to DISH_SETTING
	 * 
	 * @param dish
	 * @param userName
	 * @return ResponseEntityBase
	 * @throws Exception 
	 */
	@Override
	@Transactional
	public ResponseEntityBase insertDish(VT020002Dish dish, String userName, Collection<GrantedAuthority> roleList, MultipartFile image) throws Exception {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
		  // check validate input
      if (dish == null) {
        resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
        return resp;
      }
			//if user is chef => get kitchenID of chef
			if (roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_CHEF)) && !roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))) {
				// get kitchen info of this user
				VT020002InfoToFindKitchen info = new VT020002InfoToFindKitchen();
				info.setChefUserName(userName);
				info.setDeleteFG(Constant.DELETE_FG_ACTIVE);
				
				int isChef = kitchenDAO.checkChefKitchenExist(new ChefConfig(userName, dish.getKitchenID()));
        if (isChef == 0) {
          resp.setStatus(Constant.RESPONSE_STATUS_NO_ROLE);
          return resp;
        }
			}

			//if role admin check kitchen active or not
			VT020002InfoToCheckKitchenExist info = new VT020002InfoToCheckKitchenExist();
			info.setKitchenID(dish.getKitchenID());
			info.setDeleteFG(Constant.DELETE_FG_ACTIVE);
			info.setStatus(Constant.STATUS_ACTIVE);
			int checkKitchenActive = vt020002DAO.checkKitchenActive(info);
			if(checkKitchenActive == Constant.NONE_EXIST_RECORD) {
			  resp.setStatus(Constant.RESPONSE_STATUS_RECORD_NOT_EXISTED);
			  return resp;
			}
			
			if (!validate.validateWordForDish(dish.getDishName(), 50)) {
				resp.setStatus(Constant.ERROR_VALID_SPECIAL_CHAR);
				return resp;
			}
			if (dish.getStatus() != Constant.STATUS_ACTIVE) {
				dish.setStatus(Constant.STATUS_INACTIVE);
			}

			// get data neccessary
			dish = new VT020002Dish(null, dish.getDishName().trim(), dish.getKitchenID(), null, dish.getStatus(),
					Constant.DELETE_FG_ACTIVE, null);

			// check dish name existed in database
			int dishExsit = vt020002DAO.findDish(new VT020002Dish(null, dish.getDishName(), dish.getKitchenID(), "", 0,
					Constant.DELETE_FG_ACTIVE, null));
			if (dishExsit != Constant.NONE_EXIST_RECORD) {
				resp.setStatus(Constant.RESPONSE_STATUS_RECORD_EXISTED);
				return resp;
			}

			// set createUser by tokken
			dish.setCreateUser(userName);

			// insert to database
			int i = vt020002DAO.insertDish(dish);
			
			String path = saveImage(image, dish);
			dish.setImage(path);
			
			if (i == Constant.SUCCESS) {
				if (path != null) {
					vt020002DAO.updateDish(dish);
				}
				resp.setData(dish);
				resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
			} else {
				resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			}
			
		} catch (Exception e) {
		  resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}
	
	private String saveImage(MultipartFile image, VT020002Dish dish) {
	  if(image == null) return null;
	  
		String OrigifileName = image.getOriginalFilename();
		String extension = FilenameUtils.getExtension(OrigifileName);
		String newName = dish.getDishID() + "." + extension;
		FileOutputStream out = null;
		String directory = new File("").getAbsoluteFile().getParent() + File.separator + Constant.DISH_IMAGE_PATH;
		File directoryFile = new File(directory);
		if(!directoryFile.exists()) {
		  directoryFile.mkdirs();
		}
		
		String path = directory + File.separator + newName;
		
		File imageToSave = new File(path);
		if (imageToSave.exists()) {
		  imageToSave.delete();
		}
		try {
			imageToSave.createNewFile();
			out = new FileOutputStream(imageToSave);
			out.write(image.getBytes());
			out.flush();
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		} finally {
			if(out != null){
			try {
				out.close();
			} catch (IOException e) {
				logger.error(e.getMessage(),e);
				// TODO Auto-generated catch block
				}
			}
		}
		
		return path;
	}

	/**
	 * update Dish to DISH_SETTING
	 * 
	 * @param dish
	 * @param userName
	 * @return ResponseEntityBase
	 */
	@Override
	@Transactional
	public ResponseEntityBase updateDish(VT020002Dish dish, String userName, Collection<GrantedAuthority> roleList, MultipartFile image) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			// check validate input
			if (dish == null) {
				resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
				resp.setData(null);
				return resp;
			}
			if (StringUtils.isBlank(dish.getDishID())) {
				resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
				resp.setData(null);
				return resp;
			}
			if (!validate.validateWordForDish(dish.getDishName(), 50)) {
				resp.setStatus(Constant.ERROR_VALID_SPECIAL_CHAR);
				resp.setData(null);
				return resp;
			}
			if (dish.getStatus() != Constant.STATUS_ACTIVE) {
				dish.setStatus(Constant.STATUS_INACTIVE);
			}
			// get data neccessary
			List<String> listDishID = new ArrayList<>();
			listDishID.add(dish.getDishID());
			dish.setListDishID(listDishID);
			dish.setDeleteFG(Constant.DELETE_FG_ACTIVE);
			
			//check condition before update & delete dish (check dish is deleted or not)
			int checkCondition = vt020002DAO.checkConditionBeforeUpdate(dish);
			if(checkCondition != listDishID.size()) {
				resp.setStatus(Constant.ERROR_DISH_REQUEST_INVALID);
				return resp;
			}
			
			//if user is chef => get kitchenID of chef
			if (roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_CHEF)) && 
			                                      !roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))) {
				// get kitchen info of this user
				VT020002InfoToFindKitchen infoToFindKitchen = new VT020002InfoToFindKitchen();
				infoToFindKitchen.setChefUserName(userName);
				infoToFindKitchen.setDeleteFG(Constant.DELETE_FG_ACTIVE);
				int isChef = kitchenDAO.checkChefKitchenExist(new ChefConfig(userName, dish.getKitchenID()));
				if (isChef == 0) {
					resp.setStatus(Constant.RESPONSE_STATUS_NO_ROLE);
					return resp;
				}
			}
			//if role admin check kitchen active or not
			if (roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))) {
				VT020002InfoToCheckKitchenExist info = new VT020002InfoToCheckKitchenExist();
				info.setKitchenID(dish.getKitchenID());
				info.setDeleteFG(Constant.DELETE_FG_ACTIVE);
				info.setStatus(Constant.STATUS_ACTIVE);
				int checkKitchenActive = vt020002DAO.checkKitchenActive(info);
				if(checkKitchenActive == Constant.NONE_EXIST_RECORD) {
					resp.setStatus(Constant.RESPONSE_STATUS_RECORD_NOT_EXISTED);
					return resp;
				}
			}
			// check dish name existed in database
			int dishExsit = vt020002DAO.findDish(dish);
			if (dishExsit != Constant.NONE_EXIST_RECORD) {
				resp.setStatus(Constant.RESPONSE_STATUS_RECORD_EXISTED);
				return resp;
			}

			// set createUser by tokken
			dish.setUpdateUser(userName);
			
			if (image != null) {
				String path = saveImage(image, dish);
				dish.setImage(path);
			}
		// update to database
      int i = vt020002DAO.updateDish(dish);
      if (i == Constant.SUCCESS) {
        resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
      } else {
        resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
      }
			
			// delete dish in menu if status = 0 for today + 2
			if (dish.getStatus() == Constant.STATUS_INACTIVE) {
				Calendar today = Calendar.getInstance();
				today.add(Calendar.DAY_OF_MONTH, 1);
				VT020002InfoToDeleteDishInMenu info = new VT020002InfoToDeleteDishInMenu(listDishID, today.getTime());
				vt020002DAO.deleteDishInMenu(info);
			}
		} catch (Exception e) {
		  resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
		}
		return resp;
	}

	/**
	 * change dish delete_flag to inactive(0) in DISH_SETTING
	 * 
	 * @param dish
	 * @param userName
	 * @return ResponseEntityBase
	 */
	@Override
	@Transactional
	public ResponseEntityBase deleteDish(VT020002Dish dish, String userName, Collection<GrantedAuthority> roleList) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			// check validate input
			if (dish == null) {
				resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
				resp.setData(null);
				return resp;
			} else if (dish.getListDishID() == null || dish.getListDishID().isEmpty()) {
				resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
				resp.setData(null);
				return resp;
			}
			
			//if user is chef => get kitchenID of chef
			if (roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_CHEF)) && !roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))) {
				// get kitchen info of this user
				VT020002InfoToFindKitchen infoToFindKitchen = new VT020002InfoToFindKitchen();
				infoToFindKitchen.setChefUserName(userName);
				infoToFindKitchen.setDeleteFG(Constant.DELETE_FG_ACTIVE);
				
				for(String dishID : dish.getListDishID()) {
				  VT020002Dish existDish = vt020002DAO.findDishById(dishID);
				  int isChef = kitchenDAO.checkChefKitchenExist(new ChefConfig(userName, existDish.getKitchenID()));
	        if (isChef == 0) {
	          resp.setStatus(Constant.RESPONSE_STATUS_NO_ROLE);
	          return resp;
	        }
				}
			}
			
			//check condition before update & delete dish (check dish is deleted or not)
			dish.setDeleteFG(Constant.DELETE_FG_ACTIVE);
			int checkCondition = vt020002DAO.checkConditionBeforeUpdate(dish);
			if(checkCondition != dish.getListDishID().size()) {
				resp.setStatus(Constant.ERROR_DISH_REQUEST_INVALID);
				return resp;
			}
			
			//if role admin => no check kitchen active or not
			dish.setDeleteFG(Constant.DELETE_FG_INACTIVE);
			dish.setUpdateUser(userName);
			// delete dish
			vt020002DAO.deleteDish(dish);

			// delete dish in menu in future
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DAY_OF_MONTH, 1);
			c.set(Calendar.MILLISECOND, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.HOUR_OF_DAY, 0);
			VT020002InfoToDeleteDishInMenu info = new VT020002InfoToDeleteDishInMenu(dish.getListDishID(), c.getTime());
			vt020002DAO.deleteDishInMenu(info);
			resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findAllKitchen(VT020002InfoToFindKitchen info) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<VT020002Kitchen> listKitchen = null;
		try {
			info.setDeleteFG(Constant.DELETE_FG_ACTIVE);
			listKitchen = vt020002DAO.findAllKitchen(info);
			resp.setData(listKitchen);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	@Transactional(readOnly = true)
	public VT020002Dish findDishById(String dishID) {
		VT020002Dish vt020002Dish = null;
		try {
			vt020002Dish = vt020002DAO.findDishById(dishID);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return vt020002Dish;
	}
	
	public void saveImage(byte[] data, VT020002Dish dish) throws IOException {
    if (data != null) {
      String newName = dish.getDishID() + "." + "png";
      
      String directory = new File("").getAbsoluteFile().getParent() + File.separator + Constant.DISH_IMAGE_PATH;
      File directoryFile = new File(directory);
      if(!directoryFile.exists()) {
        directoryFile.mkdirs();
      }
      
      String path = directory + File.separator + newName;
      BufferedImage image = ImageIO.read(new ByteArrayInputStream(data));
      File imageToSave = new File(path);
      
      if (imageToSave.exists()) {
        imageToSave.delete();
      }
      ImageIO.write(image, "png", imageToSave);
      
      dish.setImage(path);
      vt020002DAO.updateDish(dish);
    }
	}
}
