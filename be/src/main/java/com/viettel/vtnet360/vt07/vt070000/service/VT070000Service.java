package com.viettel.vtnet360.vt07.vt070000.service;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt07.vt070000.entity.RackDetail;
import com.viettel.vtnet360.vt07.vt070000.entity.RequestParam;
import com.viettel.vtnet360.vt07.vt070000.entity.UpdateStatusRequest;
import com.viettel.vtnet360.vt07.vt070000.entity.WarehouseDetail;
import com.viettel.vtnet360.vt07.vt070000.entity.WarehouseRequestParam;
import com.viettel.vtnet360.vt07.vt070000.entity.search.ConstructionDetailSearch;
import com.viettel.vtnet360.vt07.vt070000.entity.search.ContractDetailSearch;
import com.viettel.vtnet360.vt07.vt070000.entity.search.DocumentDetailSearch;
import com.viettel.vtnet360.vt07.vt070000.entity.search.DocumentSearchRequest;
import com.viettel.vtnet360.vt07.vt070000.entity.search.FolderDetailSearch;
import com.viettel.vtnet360.vt07.vt070000.entity.search.OfficialDispatchDetailSearch;
import com.viettel.vtnet360.vt07.vt070000.entity.search.PackageDetailSearch;
import com.viettel.vtnet360.vt07.vt070000.entity.search.PaymentSummaryDetailSearch;
import com.viettel.vtnet360.vt07.vt070000.entity.search.ProjectDetailSearch;
import com.viettel.vtnet360.vt07.vt070000.entity.search.VoucherDetailSearch;
import com.viettel.vtnet360.vt07.vt070002.entity.TinBoxDetail;

public interface VT070000Service {
	public Object getListWarehouseDetail(RequestParam rackRequestParam, Collection<GrantedAuthority> roleList);
	public Object updateWarehouseDetail(WarehouseDetail warehouseDetail, String updateUser, Collection<GrantedAuthority> roleList);
	public Object getLocationByQrCode(RequestParam rackRequestParam, Collection<GrantedAuthority> roleList);
	
	public ResponseEntityBase getWarehouseDetail(WarehouseRequestParam warehouseDetail, Collection<GrantedAuthority> roleList);
	
	public ResponseEntityBase insertUpdateWarehouse(WarehouseRequestParam warehouseDetail, Collection<GrantedAuthority> roleList);
	
	public ResponseEntityBase deleteWarehouse(WarehouseRequestParam warehouseDetail, Collection<GrantedAuthority> roleList);
	public ResponseEntityBase getDiagram(String warehouseId, Collection<GrantedAuthority> roleList);
	
	public ResponseEntityBase getListSlotOfWarehouse(WarehouseRequestParam warehouseRequestParam, Collection<GrantedAuthority> roleList);
	
	public ResponseEntityBase getAllListWarehouseDetail(WarehouseRequestParam warehouseRequestParam, Collection<GrantedAuthority> roleList);
	
	public File createExcelOutputExcel(WarehouseRequestParam warehouseRequestParam, Collection<GrantedAuthority> listRole);
	public ResponseEntityBase findRack(RackDetail object, Collection<GrantedAuthority> roleList);
	
	public ResponseEntityBase updatePrintTimeRack(RackDetail listRack, Collection<GrantedAuthority> roleList);
	
	public Object getListMainWarehouseDetail(RequestParam rackRequestParam, Collection<GrantedAuthority> roleList);

	public ResponseEntityBase updateStatusBySearchType(UpdateStatusRequest updateSttRequest);

	public List<PaymentSummaryDetailSearch> getPaymentSummaryByDocName(DocumentSearchRequest documentSearchRequest, Collection<GrantedAuthority> roleList);
	public List<VoucherDetailSearch> getVoucherByDocNameAndType(DocumentSearchRequest documentSearchRequest, Collection<GrantedAuthority> roleList);
	public List<VoucherDetailSearch> getVoucherDocByDocNameAndType(DocumentSearchRequest documentSearchRequest, Collection<GrantedAuthority> roleList);
	public List<OfficialDispatchDetailSearch> getOfficialDispatchByIncomingDocName(DocumentSearchRequest documentSearchRequest, Collection<GrantedAuthority> roleList);
	public List<OfficialDispatchDetailSearch> getOfficialDispatchBytravelsDocName(DocumentSearchRequest documentSearchRequest, Collection<GrantedAuthority> roleList);
	public List<ContractDetailSearch> getContractByDocName(DocumentSearchRequest documentSearchRequest, Collection<GrantedAuthority> roleList);
	public List<PackageDetailSearch> getPackageByDocName(DocumentSearchRequest documentSearchRequest, Collection<GrantedAuthority> roleList);
	public List<ProjectDetailSearch> getProjectByDocName(DocumentSearchRequest documentSearchRequest, Collection<GrantedAuthority> roleList);
	public List<FolderDetailSearch> getFolderByName(DocumentSearchRequest documentSearchRequest, Collection<GrantedAuthority> roleList);
	public List<TinBoxDetail> getTinBoxByName(DocumentSearchRequest documentSearchRequest, Collection<GrantedAuthority> roleList);
	public List<ConstructionDetailSearch> getConstructionByDocName(DocumentSearchRequest documentSearchRequest, Collection<GrantedAuthority> roleList);

	public List<DocumentDetailSearch> getDocDetailByIdAndType(long projectId, long type, long folderId) throws Exception ;
}
