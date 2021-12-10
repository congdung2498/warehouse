package com.viettel.vtnet360.vt07.vt070005.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viettel.vtnet360.vt07.vt070002.entity.FolderDetail;
import com.viettel.vtnet360.vt07.vt070005.dao.VT070005DAO;
import com.viettel.vtnet360.vt07.vt070005.entity.PaymentSummaryDocVO;
import com.viettel.vtnet360.vt07.vt070005.entity.PaymentSummaryVO;
import com.viettel.vtnet360.vt07.vt070005.entity.VoucherDocVO;
import com.viettel.vtnet360.vt07.vt070005.entity.VoucherVO;

@Service
public class VT070005ServiceImpl implements VT070005Service {

	@Autowired
	VT070005DAO vt070005dao;

	@Override
	public FolderDetail getFolderDetailById(long id, long type) {
		if (type == 1) {
		return	vt070005dao.getOfficialDispatchInFolderById(id);
		}
		if (type == 2) {
			return	vt070005dao.getVoucherInFolderById(id);
		}
		return null;
	}

	@Override
	public List<VoucherDocVO> getVoucherDocByVoucherId(long id) {
		return vt070005dao.getVoucherDocByVoucherId(id);
	}

	@Override
	public List<PaymentSummaryDocVO> getPaymentSummaryDocByPaymentSummaryId(long id) {
		return vt070005dao.getPaymentSummaryDocBySummaryId(id);
	}

	@Override
	public VoucherVO getVoucherById(long id) {
		return vt070005dao.getVoucherById(id);
	}

	@Override
	public PaymentSummaryVO getPaymentSummaryById(long id) {
		return vt070005dao.getPaymentSummaryById(id);
	}

}
