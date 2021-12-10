package com.viettel.vtnet360.kitchen.menu.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.kitchen.dto.AbbreviationsDTO;
import com.viettel.vtnet360.kitchen.dto.AbbreviationsInsert;
import com.viettel.vtnet360.kitchen.dto.AbbreviationsPOJO;
import com.viettel.vtnet360.kitchen.dto.ChefConfig;
import com.viettel.vtnet360.kitchen.dto.KitchenDTO;
import com.viettel.vtnet360.kitchen.dto.LunchVoteMessage;
import com.viettel.vtnet360.kitchen.dto.SearchingDishByMenu;
import com.viettel.vtnet360.kitchen.dto.UnitShortNameDTO;
import com.viettel.vtnet360.kitchen.dto.UpdateMenu;
import com.viettel.vtnet360.vt02.vt020000.entity.VT020000Unit;
import com.viettel.vtnet360.vt02.vt020002.entity.VT020002Dish;
import com.viettel.vtnet360.vt02.vt020002.entity.VT020002DishOffset;
import com.viettel.vtnet360.vt02.vt020004.entity.VT020004Dish;
import com.viettel.vtnet360.vt02.vt020004.entity.VT020004InfoToFindDishInMenu;
import com.viettel.vtnet360.vt02.vt020004.entity.VT020004InfoToFindMenu;
import com.viettel.vtnet360.vt02.vt020004.entity.VT020004MenuItem;

@Transactional
public interface KitchenDAO {
  
  public int checkShortUnitName(AbbreviationsInsert unit);
  
  public int checkShortUnitNameUpdate(AbbreviationsInsert unit);
  
  public LunchVoteMessage getNotiByLunch();
  
  public int checkChefKitchenExist(ChefConfig chefCofig);
  
  public List<KitchenDTO> getKitchenByChefFull(ChefConfig chefCofig);
  
  public List<KitchenDTO> findListKitchenFull();
  
  public List<KitchenDTO> getKitchenByChef(ChefConfig chefCofig);
  
  public List<KitchenDTO> findListKitchen();
  
  public List<VT020004MenuItem> findListMenu(VT020004InfoToFindMenu info);

  public List<VT020002Dish> findListDish(VT020002DishOffset dishOffset);
  
  public List<VT020004Dish> findListDishByMenu(SearchingDishByMenu searching);

  public int totalDish(VT020002DishOffset dishOffset);

  public List<KitchenDTO> findKitchenName();

  public List<UnitShortNameDTO> findUnitShortName(String searchDTO);

  public List<String> findKitchenNameUpdate(Integer searchDTO);

  public List<UnitShortNameDTO> findUnitShortNameDropdown(Integer searchDTO);

  public List<AbbreviationsDTO> findAbbreviations(AbbreviationsPOJO abbreviationsPOJO);

  public void insertAbbreviations(AbbreviationsInsert abbreviationsInsert);

  public int countTotalMenuService(AbbreviationsPOJO abbreviationsPOJO);

  public void updateAbbreviations(AbbreviationsInsert abbreviationsInsert);

  public List<UnitShortNameDTO> checkIsExist(AbbreviationsInsert abbreviationsInsert);

  public List<VT020000Unit> getAllUnit();

  public int countTotalMenu(VT020004InfoToFindMenu info);

  public void updateMenu(UpdateMenu menu  );
  
  public List<VT020004MenuItem> checkMenuIsExist(String menuId);
  
  public List<UnitShortNameDTO> checkIsDuplicate(AbbreviationsInsert abbreviationsInsert);
  
  public List<VT020004MenuItem> findListMenuCheck(VT020004InfoToFindMenu info);
  
  public List<VT020004MenuItem> checkMenuExistResult(VT020004InfoToFindDishInMenu info);
  
  public void deleteAbbreviations(AbbreviationsInsert abbreviationsInsert );
  
  public List<UnitShortNameDTO> findUnitShortNameDropdownAll();
  
  public List<KitchenDTO> checkKitchenNotAllow(AbbreviationsInsert abbreviationsInsert);

  public VT020004Dish detailDishSetting(VT020004Dish  dish);
  
  public int checkChefExist(ChefConfig chefConfig);
  
  public List<UnitShortNameDTO> findKitchenNameUpdateCheck(Integer searchDTO);
  
  public List<KitchenDTO> findKitchenNameByUser(String searchDTO);
  
  public List<String> findDishByName(String search);
  
  public int countTotalMenuKichen(VT020004InfoToFindMenu info);
  
  public List<KitchenDTO> findKitchenNameAll();
  
  public String getRoleByUser(String searchDTO);
}
