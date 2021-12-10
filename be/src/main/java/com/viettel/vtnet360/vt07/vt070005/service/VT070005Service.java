package com.viettel.vtnet360.vt07.vt070005.service;

import java.util.List;

import com.viettel.vtnet360.vt07.vt070002.entity.FolderDetail;
import com.viettel.vtnet360.vt07.vt070005.entity.PaymentSummaryDocVO;
import com.viettel.vtnet360.vt07.vt070005.entity.PaymentSummaryVO;
import com.viettel.vtnet360.vt07.vt070005.entity.VoucherDocVO;
import com.viettel.vtnet360.vt07.vt070005.entity.VoucherVO;

public interface VT070005Service {
	FolderDetail getFolderDetailById(long id, long type);

	List<VoucherDocVO> getVoucherDocByVoucherId(long id);
	
	VoucherVO getVoucherById(long id);

	List<PaymentSummaryDocVO> getPaymentSummaryDocByPaymentSummaryId(long id);

	PaymentSummaryVO getPaymentSummaryById(long id);
}
