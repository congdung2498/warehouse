package com.viettel.vtnet360.vt07.vt070003.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.viettel.vtnet360.vt07.vt070003.entity.FileUploadInfo;
import com.viettel.vtnet360.vt07.vt070002.entity.FolderDetail;
import com.viettel.vtnet360.vt07.vt070003.entity.OfficialDispatch;
import com.viettel.vtnet360.vt07.vt070003.entity.OfficialDispatchDoc;

@Repository
public interface VT070003DAO {
	OfficialDispatch getOfficialDispatchById(@Param("officialDispatchId") long officialDispatchId);

	List<OfficialDispatchDoc> getOfficialDispatchDocById(@Param("officialDispatchId") long officialDispatchId);
	
	public FolderDetail getOfficialInFolderById(@Param("folderId") long folderId);

	public long getOfficialDispatchIdByName(@Param("name") String name);
	
	public int createOfficialDispatch(@Param("name") String name, @Param("type") int type, @Param("folderId") long folderId);

	public int createOfficialDispatchTravelsDoc(@Param("officialDispatchId") long officialDispatchId,@Param("name") String name, @Param("type") int type,@Param("levelBaoMat") String levelBaoMat,  @Param("folderId") long folderId);

	public int createIncomingOfficialDispatchDoc(@Param("officialDispatchId") long officialDispatchId,@Param("name") String name,  @Param("type") int type,@Param("levelBaoMat") String levelBaoMat,  @Param("folderId") long folderId);

	public long getOfficialDispatchTravelsDocIdByDispatchIdAndName(@Param("officialDispatchId") long officialDispatchId,@Param("name") String name);
	
	public long getIncomingOfficialDispatchDocIdByDispatchIdAndName(@Param("officialDispatchId") long officialDispatchId,@Param("name") String name);
	
	public void updateFileUpload(FileUploadInfo fileUpload);
	
	public String getFileUploadByIdAndType(FileUploadInfo fileUpload);


	
	public long getOfficialDispatchIdByNameGroup(@Param("name") String name);
	
	public int createOfficialDispatchGroup(@Param("name") String name, @Param("type") int type,  @Param("folderId") long folderId);

	public int createOfficialDispatchTravelsDocGroup(@Param("officialDispatchId") long officialDispatchId,@Param("name") String name, @Param("type") int type,@Param("levelBaoMat") String levelBaoMat,  @Param("folderId") long folderId);

	public int createIncomingOfficialDispatchDocGroup(@Param("officialDispatchId") long officialDispatchId,@Param("name") String name,  @Param("type") int type,@Param("levelBaoMat") String levelBaoMat,  @Param("folderId") long folderId);

	public long getOfficialDispatchTravelsDocIdByDispatchIdAndNameGroup(@Param("officialDispatchId") long officialDispatchId,@Param("name") String name);
	
	public long getIncomingOfficialDispatchDocIdByDispatchIdAndNameGroup(@Param("officialDispatchId") long officialDispatchId,@Param("name") String name);

}
