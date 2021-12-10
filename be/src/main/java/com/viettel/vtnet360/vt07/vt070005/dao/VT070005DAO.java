package com.viettel.vtnet360.vt07.vt070005.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.viettel.vtnet360.vt07.vt070002.entity.FolderDetail;
import com.viettel.vtnet360.vt07.vt070005.entity.PaymentSummaryDocVO;
import com.viettel.vtnet360.vt07.vt070005.entity.PaymentSummaryVO;
import com.viettel.vtnet360.vt07.vt070005.entity.VoucherDocVO;
import com.viettel.vtnet360.vt07.vt070005.entity.VoucherVO;

@Repository
public interface VT070005DAO {
	public VoucherVO getVoucherById(@Param("voucherId") long folderId);
	
	public PaymentSummaryVO getPaymentSummaryById(@Param("paymentSummaryId") long paymentSummaryId);

	public FolderDetail getOfficialDispatchInFolderById(@Param("folderId") long folderId);
	
	public FolderDetail getVoucherInFolderById(@Param("folderId") long folderId);

	public List<VoucherDocVO> getVoucherDocByVoucherId(@Param("voucherId") long voucherId);
	
	public List<PaymentSummaryDocVO> getPaymentSummaryDocBySummaryId(@Param("paymentSummaryId") long paymentSummaryId);

	public long getVoucherIdByName(@Param("name") String name);

	public int createVoucher(VoucherVO voucher);

	public int creatVoucherDoc(VoucherDocVO voucherDoc);

	public long getVoucherDocIdByVoucherIdAndName(@Param("voucherId") long voucherId, @Param("name") String name);

	public int bindVoucherToFolder(@Param("folderId") long folderId, @Param("voucherId") long voucherId,
			@Param("createUser") String createUser);

	public long getPaymentSummaryIdByName(@Param("name") String name);

	public int createPaymentSummary(PaymentSummaryVO paymentSummary);

	public int createPaymentSummaryDoc(PaymentSummaryDocVO paymentSummary);

	public long getPaymentSummaryDocIdByPaymentSummaryIdAndName(@Param("paymentSummaryId") long paymentSummaryId,
			@Param("name") String name);

	public int bindPaymentSummaryToFolder(@Param("folderId") long folderId,
			@Param("paymentSummaryId") long paymentSummaryId, @Param("createUser") String createUser);

	public long getVoucherIdByNameGroup(@Param("name") String name);

	public int createVoucherGroup(VoucherVO voucher);

	public int creatVoucherDocGroup(VoucherDocVO voucherDoc);

	public int bindVoucherToFolderGroup(@Param("folderId") long folderId, @Param("voucherId") long voucherId,
			@Param("createUser") String createUser);

	public long getVoucherDocIdByVoucherIdAndNameGroup(@Param("voucherId") long voucherId, @Param("name") String name);

	public long getPaymentSummaryIdByNameGroup(@Param("name") String name);

	public int createPaymentSummaryGroup(PaymentSummaryVO voucher);

	public int createPaymentSummaryDocGroup(PaymentSummaryDocVO paymentSummary);

	public long getPaymentSummaryDocIdByPaymentSummaryIdAndNameGroup(@Param("paymentSummaryId") long voucherId,
			@Param("name") String name);

	public int bindPaymentSummaryToFolderGroup(@Param("folderId") long folderId,
			@Param("paymentSummaryId") long paymentSummaryId, @Param("createUser") String createUser);

}
