package com.viettel.vtnet360.vt05.vt050017.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.viettel.vtnet360.vt05.vt050017.entity.VT050017DataResponse;
import com.viettel.vtnet360.vt05.vt050017.entity.VT050017RequestParam;

/**
 * @author DuyNK
 * 
 */
@Repository
public interface VT050017DAO {

	/**
	 * find list info request of hcdv
	 * 
	 * @param param
	 * @return List<vt050017DataResponse>
	 */
	public List<VT050017DataResponse> findData(VT050017RequestParam param);
}
