package com.viettel.vtnet360.kitchen.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viettel.vtnet360.common.security.BaseController;
import com.viettel.vtnet360.common.security.BaseEntity;
import com.viettel.vtnet360.kitchen.dto.AbbreviationsInsert;
import com.viettel.vtnet360.kitchen.dto.AbbreviationsPOJO;
import com.viettel.vtnet360.kitchen.dto.ChefConfig;
import com.viettel.vtnet360.kitchen.dto.ImageConfig;
import com.viettel.vtnet360.kitchen.dto.KitchenDTO;
import com.viettel.vtnet360.kitchen.service.KitchenService;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt02.vt020000.entity.VT020000Unit;
import com.viettel.vtnet360.vt02.vt020002.entity.VT020002Dish;
import com.viettel.vtnet360.vt02.vt020002.entity.VT020002DishOffset;
import com.viettel.vtnet360.vt02.vt020002.entity.VT020002InfoToFindKitchen;
import com.viettel.vtnet360.vt02.vt020002.service.VT020002Service;
import com.viettel.vtnet360.vt02.vt020004.entity.VT020004InfoToFindDishInMenu;
import com.viettel.vtnet360.vt02.vt020004.entity.VT020004InfoToFindMenu;
import com.viettel.vtnet360.vt02.vt020004.entity.VT020004InfoToInsertMenu;

@RestController
@RequestMapping("/com/viettel/vtnet360/kitchen")
public class KitchenController extends BaseController {
	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private KitchenService service;
	
	@Autowired
	private VT020002Service vT020002Service;
	
	@Autowired
  private Properties configProperty;
	
	
	@RequestMapping(value = "/get-all-kitchen-full", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
  public ResponseEntity<ResponseEntityBase> getAllKitchenFull(@RequestBody BaseEntity condition) {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      if(isValidated(configProperty, condition.getSecurityUsername(), condition.getSecurityPassword())) {
        List<KitchenDTO> kitchens = service.findListKitchenFull();
        resp.setData(kitchens);
        resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
  }
  
  @RequestMapping(value = "/get-kitchen-by-chef-full", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
  public ResponseEntity<ResponseEntityBase> getKitchenByChefFull(@RequestBody BaseEntity condition, Principal principal) {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      if(isValidated(configProperty, condition.getSecurityUsername(), condition.getSecurityPassword())) {
        List<KitchenDTO> kitchens = service.getKitchenByChefFull(principal);
        resp.setData(kitchens);
        resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
      }
    } catch (Exception e) {
      resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
      logger.error(e.getMessage(), e);
    }
    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
  }
	
	@RequestMapping(value = "/get-all-kitchen", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
  public ResponseEntity<ResponseEntityBase> getAllKitchen(@RequestBody BaseEntity condition) {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      if(isValidated(configProperty, condition.getSecurityUsername(), condition.getSecurityPassword())) {
        List<KitchenDTO> kitchens = service.findListKitchen();
        resp.setData(kitchens);
        resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
      }
    } catch (Exception e) {
      resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
      logger.error(e.getMessage(), e);
    }
    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
  }
	
	@RequestMapping(value = "/get-kitchen-by-chef", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
  public ResponseEntity<ResponseEntityBase> getKitchenByChef(@RequestBody BaseEntity condition, Principal principal) {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      if(isValidated(configProperty, condition.getSecurityUsername(), condition.getSecurityPassword())) {
        List<KitchenDTO> kitchens = service.getKitchenByChef(principal);
        resp.setData(kitchens);
        resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
      }
    } catch (Exception e) {
      resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
      logger.error(e.getMessage(), e);
    }
    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
  }
	
	//TODO [Thanh]: review and rewrite
	@GetMapping(value = "/dish-autocomplete/{search}", consumes = MediaType.ALL_VALUE)
  public ResponseEntity<ResponseEntityBase> dishAutocomplete(@PathVariable String search) {
    ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      response.setData(service.findDishByName(search));
      response.setStatus(Constant.SUCCESS);
    } catch (Exception e) {
      response.setStatus(Constant.RESPONSE_ERROR_DUPLICATEKEY);
    }
    return new ResponseEntity<ResponseEntityBase>(response, HttpStatus.OK);
  }
	
	//TODO [Thanh]: review and rewrite
	@RequestMapping(value = "/check-chef-exist", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
  public ResponseEntity<ResponseEntityBase> checkChefExist(@RequestBody ChefConfig chefConfig) {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      int exist = service.checkChefExist(chefConfig);
      resp.setData(exist);
      resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
    } catch (Exception e) {
      resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
      logger.error(e.getMessage(), e);
    }
    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
  }

	@RequestMapping(value = "/find-menu", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt020004FindLishMenu(@RequestBody VT020004InfoToFindMenu info, Principal principal) {
		logger.info("*********** Kitchen get list menu each day start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      Collection<GrantedAuthority> roleList = oauth.getAuthorities();
	      resp = service.findListMenu(info, userName, roleList);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** Kitchen get list get list menu each day end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/dishconfig", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt020002FindListDish(@RequestBody VT020002DishOffset dishOffset, Principal principal) {
		logger.info("*********** dishconfig get list dish start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, dishOffset.getSecurityUsername(), dishOffset.getSecurityPassword())) {
		    resp = service.getDishConfigModel(dishOffset, principal);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** dishconfig get list dish end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/find-kitchen-by-chef", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt020002FindListKitchen(@RequestBody VT020002InfoToFindKitchen info) {
		logger.info("*********** vt020002_01 get list kitchen start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
		    resp = service.findKitchenByChef(info);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt020002_01 get list kitchen end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/findKitchenName", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> findKitchenName(@RequestBody BaseEntity info) {
		logger.info("*********** findKitchenName get list kitchen start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
		    resp = service.findKitchenName();
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** findKitchenName get list kitchen end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/findKitchenNameOld", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> findKitchenNameOld(@RequestBody BaseEntity info) {
		logger.info("*********** findKitchenName get list kitchen start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
		    resp = service.findKitchenNameOld();
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** findKitchenName get list kitchen end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
	
	//TODO [Thanh]: review and rewrite
	@RequestMapping(value = "/findUnitShortName/{searchDTO}", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> findUnitShortName(@PathVariable String searchDTO) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
			resp = service.findUnitShortName(searchDTO);
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/findAbbreviations", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> findAbbreviations(@RequestBody AbbreviationsPOJO abbreviationsPOJO) {
		logger.info("*********** vt020002_01 get list kitchen start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, abbreviationsPOJO.getSecurityUsername(), abbreviationsPOJO.getSecurityPassword())) {
		    resp = service.findAbbreviations(abbreviationsPOJO);
	      resp.setStatus(Constant.SUCCESS);

		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt020002_01 get list kitchen end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/countTotalMenuService", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> countTotalMenuService(@RequestBody AbbreviationsPOJO abbreviationsPOJO) {
		logger.info("*********** vt020002_01 get list kitchen start***********");
		ResponseEntityBase resp = null;
		try {
		  if(isValidated(configProperty, abbreviationsPOJO.getSecurityUsername(), abbreviationsPOJO.getSecurityPassword())) {
		    resp = service.countTotalMenuService(abbreviationsPOJO);
	      resp.setStatus(Constant.SUCCESS);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt020002_01 get list kitchen end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/countTotalMenuKichen", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> countTotalMenuKichen(@RequestBody VT020004InfoToFindMenu info) {
		logger.info("*********** vt020002_01 get list kitchen start***********");
		ResponseEntityBase resp = null;
		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
		    resp = service.countTotalMenuKichen(info);
	      resp.setStatus(Constant.SUCCESS);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt020002_01 get list kitchen end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
	
	//TODO [Thanh]: review and rewrite
	@RequestMapping(value = "/findUnitShortNameDropdown", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> findUnitShortNameDropdown(@PathVariable Integer searchDTO) {
		logger.info("*********** vt020002_01 get list kitchen start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
			resp = service.findUnitShortNameDropdown(searchDTO);
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt020002_01 get list kitchen end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/findUnitShortNameDropdownAll", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> findUnitShortNameDropdownAll(@RequestBody BaseEntity info) {
		logger.info("*********** vt020002_01 get list kitchen start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
		    resp = service.findUnitShortNameDropdownAll();
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt020002_01 get list kitchen end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	
	@RequestMapping(value = "/abbreviationsInsert", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> insertIssuesService(@RequestBody AbbreviationsInsert abbreviationsInsert,
			Principal principal) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, abbreviationsInsert.getSecurityUsername(), abbreviationsInsert.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      abbreviationsInsert.setCreateUser((String) oauth.getPrincipal());
	      resp = service.insertAbbreviationsService(abbreviationsInsert);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** dishconfig get list dish end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);

	}

	@RequestMapping(value = "/updateAbbreviations", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> updateAbbreviations(@RequestBody AbbreviationsInsert abbreviationsInsert,
			Principal principal) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, abbreviationsInsert.getSecurityUsername(), abbreviationsInsert.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      abbreviationsInsert.setUpdateUser((String) oauth.getPrincipal());
	      resp = service.updateAbbreviations(abbreviationsInsert);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** updateAbbreviations get list updateAbbreviations end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/buildCompleteTree", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> buildCompleteTree(@RequestBody BaseEntity info) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
		    List<VT020000Unit> deptList = service.getAllUnit();
	      TreeNode root =  buildTree(deptList);
	      TreeNode[] array = new TreeNode[] {root};
	      resp.setData(array);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	//TODO [Thanh]: review and rewrite
	@RequestMapping(value = "/findKitchenNameUpdate/{searchDTO}", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> findKitchenNameUpdate(@PathVariable Integer searchDTO) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
			resp = service.findKitchenNameUpdate(searchDTO);
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
	
	//TODO [Thanh]: review and rewrite
	@RequestMapping(value = "/findKitchenNameUpdateCheck/{searchDTO}", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> findKitchenNameUpdateCheck(@PathVariable Integer searchDTO) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
			resp = service.findKitchenNameUpdateCheck(searchDTO);
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
	
	
	private TreeNode createNode(TreeNode parent, VT020000Unit vT020000Unit) {
		TreeNode treeNode = new TreeNode();
		List<TreeNode> lstChildrend = new ArrayList<>();
		treeNode.setLabel(vT020000Unit.getUnitName());
		treeNode.setData(vT020000Unit.getUnitId());
		treeNode.setChildren(lstChildrend);
		
		if (parent != null) {
			treeNode.setParent(parent.getData());
			parent.getChildren().add(treeNode);
		}
		
		return treeNode;
	}
	
	private void findChildrend(TreeNode parent, List<VT020000Unit> deptList, int i) {
		for (int j = 0; j < deptList.size(); j++) {
			if (i != j && deptList.get(i).getUnitId().longValue() == deptList.get(j).getParentId()) {
				TreeNode node = createNode(parent, deptList.get(j));
				findChildrend(node, deptList, j);
			}
		}
	}
	
	private TreeNode buildTree(List<VT020000Unit> deptList) {
		for (int i = 0; i < deptList.size(); i++) {
			if (deptList.get(i).getUnitId().longValue() == 234841l) {
				TreeNode nodeParent = createNode(null, deptList.get(i));
				findChildrend(nodeParent, deptList, i);
				return nodeParent;
			}
		}
		
		return null;
	}
	
	
	@RequestMapping(value = "/countTotalMenu", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> countTotalMenu(@RequestBody VT020004InfoToFindMenu info, Principal principal) {
		logger.info("*********** total menu start ***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      Collection<GrantedAuthority> roleList = oauth.getAuthorities();
	      resp = service.countTotalMenu(info, userName, roleList);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** total menu end***********");
		
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/updateMenu", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> insertIssuesService(@RequestBody VT020004InfoToInsertMenu info, Principal principal) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      Collection<GrantedAuthority> roleList = oauth.getAuthorities();
	      resp = service.updateMenu(info, userName, roleList);
	      resp.setStatus(Constant.SUCCESS);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** dishconfig get list dish end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
	
	//TODO [Thanh]: review and rewrite
	@RequestMapping(value = "/getDishImage", method = RequestMethod.POST, consumes = {MediaType.ALL_VALUE})
	public byte[] getDishImage(@RequestBody String dishID) {
		byte[] bytes = null;
		
		try {
			VT020002Dish vT020002Dish = vT020002Service.findDishById(dishID);
			if (vT020002Dish.getImage() != null) {
				File file  = new File(vT020002Dish.getImage());
				if (file.exists()) {
					Path path = file.toPath();
					bytes = Files.readAllBytes(path);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return bytes;
	}
	
	//For mobile
	@RequestMapping(value = "/get-dish-image", method = RequestMethod.POST, consumes = {MediaType.ALL_VALUE})
  public byte[] getDishImage(@RequestBody ImageConfig image) {
    byte[] bytes = null;
    
    try {
      if(isValidated(configProperty, image.getSecurityUsername(), image.getSecurityPassword())) {
        VT020002Dish vT020002Dish = vT020002Service.findDishById(image.getDishId());
        if (vT020002Dish.getImage() != null) {
          File file  = new File(vT020002Dish.getImage());
          if (file.exists()) {
            Path path = file.toPath();
            bytes = Files.readAllBytes(path);
          }
        }
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }

    return bytes;
  }
	
	@RequestMapping(value = "/checkMenuExistResult", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> checkMenuExistResult(@RequestBody VT020004InfoToFindDishInMenu info) {
		logger.info("*********** checkMenuExistResult get list dish start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
		    resp = service.checkMenuExistResult(info);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** checkMenuExistResult get list dish end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/deleteAbbreviations", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> deleteAbbreviations(@RequestBody AbbreviationsInsert abbreviationsInsert) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, abbreviationsInsert.getSecurityUsername(), abbreviationsInsert.getSecurityPassword())) {
		    resp = service.deleteAbbreviations(abbreviationsInsert);
	      resp.setStatus(Constant.SUCCESS);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** dishconfig get list dish end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
	
	//TODO [Thanh]: review and rewrite
	@RequestMapping(value = "/findKitchenNameByUser/{searchDTO}", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> findKitchenNameByUser(@PathVariable String searchDTO) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
			resp = service.findKitchenNameByUser(searchDTO);
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/findKitchenNameAll", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> findKitchenNameAll(@RequestBody BaseEntity info) {
		logger.info("*********** findKitchenNameAll get list kitchen start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
		    resp = service.findKitchenNameAll();
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** findKitchenNameAll get list kitchen end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
	
	
}
