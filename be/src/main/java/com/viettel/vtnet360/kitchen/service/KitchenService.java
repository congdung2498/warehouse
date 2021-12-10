package com.viettel.vtnet360.kitchen.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.kitchen.dto.AbbreviationsDTO;
import com.viettel.vtnet360.kitchen.dto.AbbreviationsInsert;
import com.viettel.vtnet360.kitchen.dto.AbbreviationsPOJO;
import com.viettel.vtnet360.kitchen.dto.ChefConfig;
import com.viettel.vtnet360.kitchen.dto.KitchenDTO;
import com.viettel.vtnet360.kitchen.dto.LunchVoteMessage;
import com.viettel.vtnet360.kitchen.dto.SearchingDishByMenu;
import com.viettel.vtnet360.kitchen.dto.UnitShortNameDTO;
import com.viettel.vtnet360.kitchen.menu.dao.KitchenDAO;
import com.viettel.vtnet360.vt00.common.AdditionalInfoBase;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.InputValidate;
import com.viettel.vtnet360.vt00.common.Notification;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt02.vt020000.entity.VT020000Unit;
import com.viettel.vtnet360.vt02.vt020002.dao.VT020002DAO;
import com.viettel.vtnet360.vt02.vt020002.entity.VT020002Dish;
import com.viettel.vtnet360.vt02.vt020002.entity.VT020002DishOffset;
import com.viettel.vtnet360.vt02.vt020002.entity.VT020002InfoToFindKitchen;
import com.viettel.vtnet360.vt02.vt020002.entity.VT020002Kitchen;
import com.viettel.vtnet360.vt02.vt020004.dao.VT020004DAO;
import com.viettel.vtnet360.vt02.vt020004.entity.VT020004Dish;
import com.viettel.vtnet360.vt02.vt020004.entity.VT020004InfoToFindDishInMenu;
import com.viettel.vtnet360.vt02.vt020004.entity.VT020004InfoToFindKitchen;
import com.viettel.vtnet360.vt02.vt020004.entity.VT020004InfoToFindMenu;
import com.viettel.vtnet360.vt02.vt020004.entity.VT020004InfoToInsertMenu;
import com.viettel.vtnet360.vt02.vt020004.entity.VT020004Menu;
import com.viettel.vtnet360.vt02.vt020004.entity.VT020004MenuItem;

@Service
public class KitchenService {

	private final Logger logger = Logger.getLogger(this.getClass());
	private InputValidate validate = new InputValidate();

	@Autowired
	private VT020002DAO vt020002DAO;

	@Autowired
	private VT020004DAO vt020004DAO;

	@Autowired
	private KitchenDAO kitchenDAO;
	
	//TODO[Thanh]: clean
	@Autowired
  private Notification notification;

	public void createNotifyForMobileTest(String username) {
	  LunchVoteMessage noti = kitchenDAO.getNotiByLunch();
	  AdditionalInfoBase addInfo = new AdditionalInfoBase();
	  addInfo.setId(noti.getLunchId());
    addInfo.setRoleReceiver(Constant.PMQT_ROLE_EMPLOYYEE);
	  notification.sendNotification(
	      username,
	      addInfo.toString(), 
	      "Mời Đ/c đánh giá và cho ý kiến bữa trưa hôm nay trên VTNet360 trước 15h", 
	      "ĐÁNH GIÁ BỮA ĂN TRƯA",
	      Constant.TYPE_NOTIFY_MODUL02, username, 1);
	}
	
	@Transactional(readOnly = true)
	public List<KitchenDTO> findListKitchen() {
	  List<KitchenDTO> kitchens = new ArrayList<>();
    kitchens.add(new KitchenDTO(null, "Chọn tất cả"));
    kitchens.addAll(kitchenDAO.findListKitchen());
	  return kitchens;
	}
	
	@Transactional(readOnly = true)
	public List<KitchenDTO> getKitchenByChef(Principal principal) {
	  OAuth2Authentication oauth = (OAuth2Authentication) principal;
    String userName = (String) oauth.getPrincipal();
    List<KitchenDTO> kitchens = new ArrayList<>();
    kitchens.add(new KitchenDTO(null, "Chọn tất cả"));
    kitchens.addAll(kitchenDAO.getKitchenByChef(new ChefConfig(userName)));
    return kitchens;
  }
	
	@Transactional(readOnly = true)
  public List<KitchenDTO> findListKitchenFull() {
    List<KitchenDTO> kitchens = new ArrayList<>();
    kitchens.add(new KitchenDTO(null, "Chọn tất cả"));
    kitchens.addAll(kitchenDAO.findListKitchenFull());
    return kitchens;
  }
	
	@Transactional(readOnly = true)
  public List<KitchenDTO> getKitchenByChefFull(Principal principal) {
    OAuth2Authentication oauth = (OAuth2Authentication) principal;
    String userName = (String) oauth.getPrincipal();
    List<KitchenDTO> kitchens = new ArrayList<>();
    kitchens.add(new KitchenDTO(null, "Chọn tất cả"));
    kitchens.addAll(kitchenDAO.getKitchenByChefFull(new ChefConfig(userName)));
    return kitchens;
  }
	
	@Transactional(readOnly = true)
	public List<String> findDishByName(String search) {
	  return kitchenDAO.findDishByName(search);
	}
	
	@Transactional(readOnly = true)
	public int checkChefExist(ChefConfig chefConfig) {
	  return kitchenDAO.checkChefExist(chefConfig);
	}
	
	@Transactional(readOnly = true)
	public ResponseEntityBase findListMenu(VT020004InfoToFindMenu info, String useName,
			Collection<GrantedAuthority> roleList) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		if (info.getDateFrom() != null) {
			info.setDateFrom(validate.validateDate(info.getDateFrom()));
		}
		if (info.getDateTo() != null) {
			info.setDateTo(validate.validateDate(info.getDateTo()));
		}
		List<VT020004Menu> listMenu = new ArrayList<>();
		List<VT020004MenuItem> listMenuItem = null;
		try {
			listMenuItem = kitchenDAO.findListMenu(info);
			if (listMenuItem.isEmpty()) {
				resp.setData(null);
				return resp;
			}
			
			for (VT020004MenuItem menu : listMenuItem) {
				List<VT020004Dish> dishs = kitchenDAO.findListDishByMenu(new SearchingDishByMenu(menu.getDateOfMenu(), menu.getKitchenID()));
				listMenu.add(new VT020004Menu(menu.getMenuId(), menu.getDateOfMenu(), dishs, null, menu.getKitchenID()));
			}
			
			resp.setData(listMenu);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}
	
	
	
	@Transactional(readOnly = true)
	public ResponseEntityBase findKitchenByChef(VT020002InfoToFindKitchen info) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<VT020002Kitchen> listKitchen = null;
		try {
			info.setDeleteFG(Constant.DELETE_FG_ACTIVE);
			listKitchen = vt020002DAO.findKitchenByChef(info);
			resp.setData(listKitchen);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Transactional(readOnly = true)
  public ResponseEntityBase getDishConfigModel(VT020002DishOffset dishOffset, Principal principal) {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);

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
      int totalDish = kitchenDAO.totalDish(dishOffset);
      List<VT020002Dish> dish = kitchenDAO.findListDish(dishOffset);
      if(dish != null && dish.size() > 0) {
        for(VT020002Dish dishConfig : dish) {
          if(isCheckImage(dishConfig)) dishConfig.setIsImage(1);
        }
      }

      List<KitchenDTO> kitchens = null;
      if (dishOffset.isLoadKitchen()) {
        kitchens = new ArrayList<>();
        kitchens.add(new KitchenDTO(null, "All"));

        OAuth2Authentication oauth = (OAuth2Authentication) principal;
        String userName = (String) oauth.getPrincipal();
        Collection<GrantedAuthority> roleList = oauth.getAuthorities();

        List<KitchenDTO> allKitchens = kitchenDAO.findListKitchen();
        for (GrantedAuthority temp : roleList) {
          if (Constant.PMQT_ROLE_CHEF.equalsIgnoreCase(temp.getAuthority())) {
            List<KitchenDTO> kitchensByChef = kitchenDAO.getKitchenByChef(new ChefConfig(userName));
            if(kitchensByChef.size() > 0) {
              for(KitchenDTO kitchen : allKitchens) {
                for(KitchenDTO kitchenByChef : kitchensByChef) {
                  if(kitchen.getKitchenID().equals(kitchenByChef.getKitchenID())) {
                    String newName = kitchen.getKitchenName() + " (Bếp đã đăng ký)";
                    kitchen.setKitchenName(newName);
                  }
                }
              }
            }
            break;
          }
        }
        kitchens.addAll(allKitchens);
      }
      DishConfigModel dishConfigModel = new DishConfigModel(dish, kitchens, totalDish);
      resp.setData(dishConfigModel);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return resp;
  }

	@Transactional(readOnly = true)
	public ResponseEntityBase findKitchenName() {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			List<KitchenDTO> listKitchen = null;
			listKitchen = new ArrayList<>();
			listKitchen.add(new KitchenDTO(null, "--------- Chọn bếp----------"));
			listKitchen.addAll(kitchenDAO.findKitchenName());
			resp.setData(listKitchen);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}
	@Transactional(readOnly = true)
	public ResponseEntityBase findKitchenNameAll() {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			List<KitchenDTO> listKitchen = new ArrayList<>();
			listKitchen.add(new KitchenDTO(null, "--------- Chọn bếp----------"));
			listKitchen.addAll(kitchenDAO.findKitchenNameAll());
			resp.setData(listKitchen);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}
	@Transactional(readOnly = true)
	public ResponseEntityBase findKitchenNameOld() {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			List<KitchenDTO> listKitchen = kitchenDAO.findKitchenName();
			resp.setData(listKitchen);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}
	
	@Transactional(readOnly = true)
	public ResponseEntityBase findUnitShortName(String searchDTO) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<UnitShortNameDTO> unitShortNameDTOs = null;
		try {
			unitShortNameDTOs = kitchenDAO.findUnitShortName(searchDTO);
			resp.setData(unitShortNameDTOs);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Transactional(readOnly = true)
	public ResponseEntityBase findAbbreviations(AbbreviationsPOJO abbreviationsPOJO) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			
			List<AbbreviationsDTO> listAbbreviations = kitchenDAO.findAbbreviations(abbreviationsPOJO);
			resp.setData(listAbbreviations);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}
	
	@Transactional(readOnly = true)
	public ResponseEntityBase countTotalMenuService(AbbreviationsPOJO abbreviationsPOJO) throws Exception {

		int countRecords = 0;
		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, countRecords);

		try {
			logger.info("**************** Start count records list menu services ****************");

				
				countRecords = kitchenDAO.countTotalMenuService(abbreviationsPOJO);
				reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
				reb.setData(countRecords);

				logger.info("**************** End count records list menu services - OK ****************");
			
		} catch (Exception e) {
			logger.info("**************** End count records list menu services - Fail ****************");
			logger.error(e.getMessage(), e);
		}

		return reb;
	}
	
	@Transactional(readOnly = true)
	public ResponseEntityBase countTotalMenuKichen(VT020004InfoToFindMenu info) throws Exception {

		int countRecords = 0;
		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, countRecords);

		try {
			logger.info("**************** Start count records list menu services ****************");

				
				countRecords = kitchenDAO.countTotalMenuKichen(info);
				reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
				reb.setData(countRecords);

				logger.info("**************** End count records list menu services - OK ****************");
			
		} catch (Exception e) {
			logger.info("**************** End count records list menu services - Fail ****************");
			logger.error(e.getMessage(), e);
		}

		return reb;
	}
	
	@Transactional(readOnly = true)
	public ResponseEntityBase findUnitShortNameDropdown(Integer  searchDTO) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			List<UnitShortNameDTO> shortNameDTOs = kitchenDAO.findUnitShortNameDropdown(searchDTO);
			resp.setData(shortNameDTOs);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}
	@Transactional(readOnly = true)
	public ResponseEntityBase findUnitShortNameDropdownAll() {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			List<UnitShortNameDTO> shortNameDTOs = kitchenDAO.findUnitShortNameDropdownAll();
			resp.setData(shortNameDTOs);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}
	
	@Transactional(readOnly = true)
	public ResponseEntityBase findKitchenNameUpdate(Integer  searchDTO) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			List<String> list  = kitchenDAO.findKitchenNameUpdate(searchDTO);
			resp.setData(list);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}
	
	@Transactional(readOnly = true)
	public ResponseEntityBase findKitchenNameUpdateCheck(Integer  searchDTO) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			List<UnitShortNameDTO> list  = kitchenDAO.findKitchenNameUpdateCheck(searchDTO);
			resp.setData(list);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}
	
	@Transactional()
	public ResponseEntityBase insertAbbreviationsService(AbbreviationsInsert abbreviationsInsert) {
	  ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
	  try {	
	    int isExistShortName = kitchenDAO.checkShortUnitName(abbreviationsInsert);
	    if(isExistShortName > 0) {
	      reb.setStatus(Constant.RESPONSE_STATUS_RECORD_EXISTED);
	      return reb;
	    }
	    
	    List<KitchenDTO> checkKitchenNotAllow = kitchenDAO.checkKitchenNotAllow(abbreviationsInsert);
	    List<UnitShortNameDTO> checkIssuesService = kitchenDAO.checkIsExist(abbreviationsInsert);
	    if(checkKitchenNotAllow.isEmpty()){
	      if (checkIssuesService.isEmpty()) {
	        kitchenDAO.insertAbbreviations(abbreviationsInsert);
	        reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
	      } else {
	        reb.setStatus(Constant.RESPONSE_ERROR_DUPLICATEKEY);
	      }
	    } else {
	      reb.setStatus(Constant.RESPONSE_EXIST_SERVICE_NOT_VALID);;
	    }
	  }catch (Exception e) {
	    logger.error(e.getMessage(), e);
	  }
	  return reb;
	}

	@Transactional
	public ResponseEntityBase updateAbbreviations(AbbreviationsInsert abbreviationsInsert) {
	  ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
	  try {
	    int isExistShortName = kitchenDAO.checkShortUnitNameUpdate(abbreviationsInsert);
      if(isExistShortName > 0) {
        resp.setStatus(Constant.RESPONSE_STATUS_RECORD_EXISTED);
        return resp;
      }
      
	    List<UnitShortNameDTO> checkIssuesService = kitchenDAO.checkIsDuplicate(abbreviationsInsert);
	    if (checkIssuesService.isEmpty()) {
	      kitchenDAO.updateAbbreviations(abbreviationsInsert);
	      resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
	    } else {
	      resp.setStatus(Constant.RESPONSE_ERROR_DUPLICATEKEY);
	    }
	  } catch (Exception e) {
	    logger.error(e.getMessage(), e);
	  }
	  return resp;
	}
	
	
	@Transactional
	public List<VT020000Unit> getAllUnit() {
		List<VT020000Unit> lstUnit = new ArrayList<>();
		try {
			lstUnit = kitchenDAO.getAllUnit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return lstUnit;
	}
	
	@Transactional(readOnly = true)
	public ResponseEntityBase countTotalMenu(VT020004InfoToFindMenu info, String useName,
			Collection<GrantedAuthority> roleList) {
		
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		if (info.getDateFrom() != null) {
			info.setDateFrom(validate.validateDate(info.getDateFrom()));
		}
		
		if (info.getDateTo() != null) {
			info.setDateTo(validate.validateDate(info.getDateTo()));
		}
		
		try {
			info.setPageNumber((info.getPageNumber() - 1) * info.getPageSize());
			Integer total = kitchenDAO.countTotalMenu(info);
			resp.setData(total);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		
		return resp;
	}
	
	@Transactional()
	  public ResponseEntityBase updateMenu(VT020004InfoToInsertMenu param, String userName, Collection<GrantedAuthority> roleList) {
	    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
	    try {	
	      VT020004Menu info = new VT020004Menu();
	      info.setListDish(param.getListDish());
	      info.setKitchenID(param.getKitchenID());
	      info.setChefID(userName);
	      // convert dayOfMenu to Date
	      info.setDateOfMenu(validate.validateDate(param.getDateOfMenu()));
	   // validate input
	      if (param.getDateOfMenu() == null || param.getListDish().isEmpty()) {
	        resp.setStatus(0);
	        return resp;
	      }
	      // check kitchen exist
	      VT020004InfoToFindKitchen infoToFindKitchen = new VT020004InfoToFindKitchen();
	      infoToFindKitchen.setUserName(userName);
	      infoToFindKitchen.setKitchenID(info.getKitchenID());
	      infoToFindKitchen.setDeleteFG(Constant.DELETE_FG_ACTIVE);
	      infoToFindKitchen.setStatus(Constant.STATUS_ACTIVE);
	      int checkKitchen = vt020004DAO.checkKitchenExisted(infoToFindKitchen);
	      if (checkKitchen == Constant.NONE_EXIST_RECORD) {
	        resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
	        return resp;
	      }
	      VT020004InfoToFindDishInMenu infoToDeleteMenuOld = new VT020004InfoToFindDishInMenu(validate.validateDate(param.getDateOfMenuOld()),
            null, info.getKitchenID());
	      
	      if(validate.validateDate(param.getDateOfMenuOld()).compareTo(validate.validateDate(param.getDateOfMenu())) != 0) {
          // delete menu old
          vt020004DAO.deleteMenuByday(infoToDeleteMenuOld);
          VT020004InfoToFindDishInMenu infoFindDish = new VT020004InfoToFindDishInMenu(info.getDateOfMenu(),
              null, info.getKitchenID());
          vt020004DAO.deleteMenuByday(infoFindDish);
          vt020004DAO.inserMenu(info);
          resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
        } else {
          VT020004InfoToFindDishInMenu infoFindDish = new VT020004InfoToFindDishInMenu(info.getDateOfMenu(),
              null, info.getKitchenID());
          vt020004DAO.deleteMenuByday(infoFindDish);
          // insert data
          vt020004DAO.inserMenu(info);
        }
	    } catch (Exception e) {
	      logger.error(e.getMessage(), e);
	    }
	    return resp;
	  }
	
	@Transactional(readOnly = true)
	public ResponseEntityBase checkMenuExistResult(VT020004InfoToFindDishInMenu info) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<VT020004MenuItem> listKitchen = null;
		try {
			listKitchen = kitchenDAO.checkMenuExistResult(info);
			resp.setData(listKitchen);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}
	
	@Transactional(readOnly = true)
	public ResponseEntityBase deleteAbbreviations(AbbreviationsInsert abbreviationsInsert) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		
		try {
			 kitchenDAO.deleteAbbreviations(abbreviationsInsert);
			 resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}
	
	@Transactional(readOnly = true)
	public ResponseEntityBase findKitchenNameByUser(String searchDTO) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<KitchenDTO> unitShortNameDTOs = null;
		try {
			unitShortNameDTOs = kitchenDAO.findKitchenNameByUser(searchDTO);
			resp.setData(unitShortNameDTOs);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}
	
	
	private boolean isCheckImage(VT020002Dish vT020002Dish) {
    try {
      if (vT020002Dish.getImage() != null) {
        File file  = new File(vT020002Dish.getImage());
        if (file.exists()) {
          Path path = file.toPath();
          byte[] bytes = Files.readAllBytes(path);
          if(bytes != null) return true;
        }
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }

    return false;
  }
}
