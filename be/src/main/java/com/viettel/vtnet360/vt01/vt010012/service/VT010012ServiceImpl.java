package com.viettel.vtnet360.vt01.vt010012.service;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt01.vt010012.dao.VT010012DAO;
import com.viettel.vtnet360.vt01.vt010012.entity.VT010012ListAddCard;
import com.viettel.vtnet360.vt01.vt010012.entity.VT010012ListCondition;
import com.viettel.vtnet360.vt01.vt010012.entity.VT010012ListEmployee;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT010012ServiceImpl implements VT010012Service {

	private final Logger logger = Logger.getLogger(this.getClass());

	private static final String END_UPDATE_CARD_FAIL = "**************** End processing update card employee - Fail ****************";

	@Autowired
	private VT010012DAO vt010012dao;

	/**
	 * find List Employee
	 * 
	 * @param empInfo VT010012ListEmployee
	 * @return ResponseEntityBase
	 */
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findListEmployee(VT010012ListEmployee empInfo) throws Exception {

		logger.info("**************** Start get list employees ****************");

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		try {
			if (empInfo.getResult() != null) {
				String patern = empInfo.getResult();
				empInfo.setResult(patern.trim());
			}

			List<VT010012ListEmployee> employees = vt010012dao.findListEmployee(empInfo);
			reb.setData(employees);
			reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

			logger.info("**************** End get list employees - OK ****************");
		} catch (Exception e) {
			logger.info("**************** End get list employees - Fail ****************");
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	/**
	 * find List Card
	 * 
	 * @param cardCondition VT010012ListCondition
	 * @return ResponseEntityBase
	 */
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findListCard(VT010012ListCondition cardCondition) throws Exception {

		logger.info("**************** Start get list card id ****************");

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		try {
			List<VT010012ListCondition> listCard = vt010012dao.findListCard(cardCondition);
			reb.setData(listCard);
			reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

			logger.info("**************** End get list card id - OK ****************");
		} catch (Exception e) {
			logger.info("**************** End get list card id - Fail ****************");
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	/**
	 * find List Add Card
	 * 
	 * @param condition VT010012ListCondition
	 * @param listRole  Collection<GrantedAuthority>
	 * @return ResponseEntityBase
	 */
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findListAddCard(VT010012ListCondition condition, Collection<GrantedAuthority> listRole)
			throws Exception {

		logger.info("**************** Start get list add card ****************");

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		try {
			if (listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))
					|| listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF))) {

				if (condition.getPersonInfo() != null) {
					String pattern = condition.getPersonInfo();
					condition.setPersonInfo(pattern.trim());
				}

				List<VT010012ListAddCard> listAddCards = vt010012dao.findListAddCard(condition);
				reb.setData(listAddCards);
				reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

				logger.info("**************** End get list add card - OK ****************");
			} else {
				logger.info("**************** End get list add card - Fail - insufficient power ****************");

				reb.setStatus(Constant.RESPONSE_INSUFFICIENT_POWERS);
			}
		} catch (Exception e) {
			logger.info("**************** End get list add card - Fail ****************");
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	/**
	 * count Total Record
	 * 
	 * @param condition VT010012ListCondition
	 * @param listRole  Collection<GrantedAuthority>
	 * @return ResponseEntityBase
	 */
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase countTotalRecord(VT010012ListCondition condition, Collection<GrantedAuthority> listRole)
			throws Exception {

		logger.info("**************** Start count record add card ****************");

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		try {
			if (listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))
					|| listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF))) {
				
				if(condition.getPersonInfo()!=null) {
				String pattern = condition.getPersonInfo();
				condition.setPersonInfo(pattern.trim());
				}
				int totalRecord = vt010012dao.countTotalRecord(condition);
				reb.setData(totalRecord);
				reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

				logger.info("**************** End count record add card - OK ****************");
			} else {
				logger.info("************* End count record add card - Fail - insufficient power *************");

				reb.setStatus(Constant.RESPONSE_INSUFFICIENT_POWERS);
			}
		} catch (Exception e) {
			logger.info("**************** End count record add card - Fail ****************");
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	public ResponseEntityBase handleUpdateCard(ResponseEntityBase reb, VT010012ListAddCard condition) throws Exception {
		logger.info("**************** Start update card and employee ****************");

		try {
			int statusUpdate = vt010012dao.updateCardEmp(condition);

			if (statusUpdate > 0) {
				logger.info("**************** End update card and employee - OK ****************");
				logger.info("**************** End processing update card employee - OK ****************");

				reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
			} else {
				logger.info("**************** End update card and employee - Fail ****************");
				logger.info(VT010012ServiceImpl.END_UPDATE_CARD_FAIL);
			}
		} catch (DataIntegrityViolationException dve) {
			logger.info("**************** Data too long ****************");
			logger.info("**************** End update card and employee - Fail ****************");

			reb.setStatus(Constant.RESPONSE_DATA_TOO_LONG);
		}

		return reb;
	}

	/**
	 * update Card Emp
	 * 
	 * @param condition VT010012ListCondition
	 * @param listRole  Collection<GrantedAuthority>
	 * @return ResponseEntityBase
	 */
	@Override
	@Transactional
	public ResponseEntityBase updateCardEmp(VT010012ListAddCard condition, Collection<GrantedAuthority> listRole)
			throws Exception {

		logger.info("**************** Start processing update card employee ****************");

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		logger.info("**************** Start check role ****************");

		try {
			if (listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))
					|| listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF))) {
				logger.info("**************** End check role - OK ****************");

				if (condition.getCardId() == null || condition.getCardId().length() == 0) {
					reb = handleUpdateCard(reb, condition);
				} else {
					logger.info("**************** Start check existing couple card and employee ****************");

					int checkCardEmp = vt010012dao.checkExistCardAndEmp(condition);

					logger.info("**************** End check existing couple card and employee - OK ****************");

					if (checkCardEmp <= 0) {
						reb = handleUpdateCard(reb, condition);
					} else {
						logger.info("**************** Couple card and employee was existed ****************");
						logger.info(VT010012ServiceImpl.END_UPDATE_CARD_FAIL);

						reb.setStatus(Constant.RESPONSE_EXISTED_CARD_EMPLOYEE);
					}
				}
			} else {
				logger.info("************* End check role - Fail - insufficient power *************");
				logger.info(VT010012ServiceImpl.END_UPDATE_CARD_FAIL);

				reb.setStatus(Constant.RESPONSE_INSUFFICIENT_POWERS);
			}
		} catch (Exception e) {
			logger.info(VT010012ServiceImpl.END_UPDATE_CARD_FAIL);
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

}
