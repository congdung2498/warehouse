/*
 * Copyright (C) 2010 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.vtnet360.vt00.common;

import java.util.Locale;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;
/**
 *
 * @author thangdd8@viettel.com.vn
 * @since Apr 12, 2010
 * @version 1.0
 * @tiennv41 fix bug them trycatch tim kiem key ngon ngu_ngay 05052014 o tat ca cac ham
 */
public class LanguageBundleUtils {
// * @tiennv41 fix bug them trycatch tim kiem key ngon ngu_ngay 05052014 o tat ca cac ham
    private LanguageBundleUtils() {
    }
    /**
     * RESOURCE.
     */
	private final static Logger logger = Logger.getLogger(LanguageBundleUtils.class);
    private static final String RESOURCE = "com/viettel/config/Language_vi_VN";
    private static final String RESOURCECONCAVE = "com/viettel/config/language/Concave_vi_VN";
    private static final String PLANMEASURRE = "com/viettel/config/language/drivingTest_vi_VN";
    private static final String ADDNEWBTS = "com/viettel/config/language/addNewBTS_vi_VN";
    private static final String ADDNEWBUILDING = "com/viettel/config/language/dasInbuilding_vi_VN";
    private static final String BROADCAST = "com/viettel/config/language/viewBroadcast_vi_VN";
    private static final String TARGETMEASURE = "com/viettel/config/language/targetMeasure_vi_VN";
    private static final String DASBUILDING = "com/viettel/config/language/dasInbuilding_vi_VN";
    private static final String OPTIMUMHISTORY = "com/viettel/config/language/optHistory_vi_VN";
    private static final String TROUBLE = "com/viettel/config/language/trouble_vi_VN";
    private static final String MAP = "com/viettel/config/language/optHistory_vi_VN";
    private static final String CONCAVECELL = "com/viettel/config/language/concaveCell_vi_VN";
    private static final String EVENT = "com/viettel/config/language/Event_vi_VN";
    private static final String CONFIG = "config";
    private static final String BADCELL = "com/viettel/config/language/configBadCell_vi_VN";
    private static final String VIEWBADCELL = "com/viettel/config/language/viewBadCell_vi_VN";
    private static final String OVERLAPCELL = "com/viettel/config/language/overlapCell_vi_VN";
    private static final String BADKPI = "com/viettel/config/language/BadKpi_vi_VN";
    //19092014_thaont19_start
    private static final String RESOURCEINTERFACE = "com/viettel/config/language/Interface";
    //19092014_thaont19_end
    private static final String CATREASON = "com/viettel/config/language/ctCatReason_vi_VN";
    private static final String CONFIGKPI = "com/viettel/config/language/ctConfigKpi_vi_VN";
    private static final String CTSOLUTION = "com/viettel/config/language/ctSolution_vi_VN";
    //thanhdd11_R6375_start
    private static final String DASBUILDING2014 = "com/viettel/config/language/dasInbuilding2014_vi_VN";
    //thanhdd11_R6375_end
    //R6335_tuevc_26112014_start
    private static final String SUGGEST = "com/viettel/config/language/Suggest_vi_VN";
    //R6335_tuevc_26112014_end
    //R6335_thanhdd11_02122014_start
    private static final String SUGGESTMOVE = "com/viettel/config/language/Suggest";
    //R6335_thanhdd11_02122014_end
    private static final String VIBA_VSAT = "com/viettel/config/language/VibaVisat";
    /**
     * local.
     */
    private static Locale local = null;
    /**
     * languageRb.
     */
    private static ResourceBundle languageRb = null;
    //R2906_new_tamdx_120820_start
    private static final String CONFIG_FILE = "configFile";
    //R2906_new_tamdx_120820_end
    
    //Rxxxx_HieuHT14_start
    private static final String MEASURE = "com/viettel/config/language/Measure";
    //Rxxxx_HieuHT14_end
//    R12970_p2_hantt59_start
     private static final String CFGTEMPKPI = "com/viettel/config/language/cfgTempKpi";
//     R12970_p2_hantt59_end
    /**
     * .
     */
    /**
     * getString
     *
     * @param key String
     * @return value
     */
    public static String getString(String key) {
	try {
	    if (local != null) {
		languageRb = ResourceBundle.getBundle(RESOURCE, local);
	    } else {
		languageRb = ResourceBundle.getBundle(RESOURCE);
	    }
	    return languageRb.getString(key);
	} catch (Exception ex) {
	    if (key != null) {
		return key;
	    }
	}
	return "";
    }

    public static String getString(Locale locale, String key) {
	try {
	    if (locale != local) {
		try {
		    local = locale;
		    languageRb = ResourceBundle.getBundle(RESOURCE + "_" + local.getLanguage() + "_" + local.getCountry());
		} catch (Exception ex) {
		    languageRb = ResourceBundle.getBundle(RESOURCE);
		}
	    }
	    return languageRb.getString(key);
	} catch (Exception ex) {
	    if (key != null) {
		return key;
	    }
	}
	return "";
    }

    public static String getStringConcave(String key) {
	try {
	    if (local != null) {
		languageRb = ResourceBundle.getBundle(RESOURCECONCAVE, local);
	    } else {
		languageRb = ResourceBundle.getBundle(RESOURCECONCAVE);
	    }
	    return languageRb.getString(key);
	} catch (Exception ex) {
	    if (key != null) {
		return key;
	    }
	}
	return "";
    }

    public static String getStringPlanMeasure(String key) {
	try {
	    if (local != null) {
		languageRb = ResourceBundle.getBundle(PLANMEASURRE, local);
	    } else {
		languageRb = ResourceBundle.getBundle(PLANMEASURRE);
	    }
	    return languageRb.getString(key);
	} catch (Exception ex) {
	    if (key != null) {
		return key;
	    }
	}
	return "";
    }

    public static String getStringAddNewBTS(String key) {
	try {
	    if (local != null) {
		languageRb = ResourceBundle.getBundle(ADDNEWBTS, local);
	    } else {
		languageRb = ResourceBundle.getBundle(ADDNEWBTS);
	    }
	    return languageRb.getString(key);
	} catch (Exception ex) {
	    if (key != null) {
		return key;
	    }
	}
	return "";
    }

    public static String getStringAddNewBuilding(String key) {
	try {
	    if (local != null) {
		languageRb = ResourceBundle.getBundle(ADDNEWBUILDING, local);
	    } else {
		languageRb = ResourceBundle.getBundle(ADDNEWBUILDING);
	    }
	    return languageRb.getString(key);
	} catch (Exception ex) {
	    if (key != null) {
		return key;
	    }
	}
	return "";
    }

    public static String getStringTargetMeasure(String key) {
	try {
	    if (local != null) {
		languageRb = ResourceBundle.getBundle(TARGETMEASURE, local);
	    } else {
		languageRb = ResourceBundle.getBundle(TARGETMEASURE);
	    }
	    return languageRb.getString(key);
	} catch (Exception ex) {
	    if (key != null) {
		return key;
	    }
	}
	return "";
    }

    public static String getStringBroadCast(String key) {
	try {
	    if (local != null) {
		languageRb = ResourceBundle.getBundle(BROADCAST, local);
	    } else {
		languageRb = ResourceBundle.getBundle(BROADCAST);
	    }
	    return languageRb.getString(key);
	} catch (Exception ex) {
	    if (key != null) {
		return key;
	    }
	}
	return "";
    }

    public static String getStringDasBuilding(String key) {
	try {
	    if (local != null) {
		languageRb = ResourceBundle.getBundle(DASBUILDING, local);
	    } else {
		languageRb = ResourceBundle.getBundle(DASBUILDING);
	    }
	    return languageRb.getString(key);
	} catch (Exception ex) {
	    if (key != null) {
		return key;
	    }
	}
	return "";
    }

    // thanhdd11_R6275_start
    public static String getStringDasBuilding2014(String key) {
	try {
	    if (local != null) {
		languageRb = ResourceBundle.getBundle(DASBUILDING2014, local);
	    } else {
		languageRb = ResourceBundle.getBundle(DASBUILDING2014);
	    }
	    return languageRb.getString(key);
	} catch (Exception e) {
	    System.out.println("not found key DASBUILDING2014: " + key);
	    return key;
	}

    }

    public static String getStringDasBuilding2014(String key, String defaultValue) {
	try {
	    if (local != null) {
		languageRb = ResourceBundle.getBundle(DASBUILDING2014, local);
	    } else {
		languageRb = ResourceBundle.getBundle(DASBUILDING2014);
	    }
	    return languageRb.getString(key);
	} catch (Exception e) {
	    System.out.println("not found key DASBUILDING2014: " + key + "=" + defaultValue);
	    return defaultValue;
	}

    }
// thanhdd11_R6275_end

    public static String getStringOptHistory(String key) {
	try {
	    if (local != null) {
		languageRb = ResourceBundle.getBundle(OPTIMUMHISTORY, local);
	    } else {
		languageRb = ResourceBundle.getBundle(OPTIMUMHISTORY);
	    }
	    return languageRb.getString(key);
	} catch (Exception ex) {
	    if (key != null) {
		return key;
	    }
	}
	return "";
    }

    public static String getStringMap(String key) {
	try {
	    if (local != null) {
		languageRb = ResourceBundle.getBundle(MAP, local);
	    } else {
		languageRb = ResourceBundle.getBundle(MAP);
	    }
	    return languageRb.getString(key);
	} catch (Exception ex) {
	    if (key != null) {
		return key;
	    }
	}
	return "";
    }

    public static String getStringTrouble(String key) {
	try {
	    if (local != null) {
		languageRb = ResourceBundle.getBundle(TROUBLE, local);
	    } else {
		languageRb = ResourceBundle.getBundle(TROUBLE);
	    }
	    return languageRb.getString(key);
	} catch (Exception ex) {
	    if (key != null) {
		return key;
	    }
	}
	return "";
    }

    public static String getStringConcaveCell(String key) {
	try {
	    if (local != null) {
		languageRb = ResourceBundle.getBundle(CONCAVECELL, local);
	    } else {
		languageRb = ResourceBundle.getBundle(CONCAVECELL);
	    }
	    return languageRb.getString(key);
	} catch (Exception ex) {
	    if (key != null) {
		return key;
	    }
	}
	return "";
    }

    public static String getStringEvent(String key) {
	try {
	    if (local != null) {
		languageRb = ResourceBundle.getBundle(EVENT, local);
	    } else {
		languageRb = ResourceBundle.getBundle(EVENT);
	    }
	    return languageRb.getString(key);
	} catch (Exception ex) {
	    if (key != null) {
		return key;
	    }
	}
	return "";
    }

    public static String getStringConfig(String key) {
	try {
	    if (local != null) {
		languageRb = ResourceBundle.getBundle(CONFIG, local);
	    } else {
		languageRb = ResourceBundle.getBundle(CONFIG);
	    }
	    return languageRb.getString(key);
	} catch (Exception ex) {
	    if (key != null) {
		return key;
	    }
	}
	return "";
    }

    public static String getStringBadCell(String key) {
	try {
	    if (local != null) {
		languageRb = ResourceBundle.getBundle(BADCELL, local);
	    } else {
		languageRb = ResourceBundle.getBundle(BADCELL);
	    }
	    return languageRb.getString(key);
	} catch (Exception ex) {
	    if (key != null) {
		return key;
	    }
	}
	return "";
    }

    public static String getStringViewBadCell(String key) {
	try {
	    if (local != null) {
		languageRb = ResourceBundle.getBundle(VIEWBADCELL, local);
	    } else {
		languageRb = ResourceBundle.getBundle(VIEWBADCELL);
	    }
	    return languageRb.getString(key);
	} catch (Exception ex) {
	    if (key != null) {
		return key;
	    }
	}
	return "";
    }

    public static String getStringOverlapCell(String key) {
	try {
	    if (local != null) {
		languageRb = ResourceBundle.getBundle(OVERLAPCELL, local);
	    } else {
		languageRb = ResourceBundle.getBundle(OVERLAPCELL);
	    }
	    return languageRb.getString(key);
	} catch (Exception ex) {
	    if (key != null) {
		return key;
	    }
	}
	return "";
    }

    //R2289_ADDNEW_QUYENNT11_100612
    public static String getStringBadKpi(String key) {
	try {
	    if (local != null) {
		languageRb = ResourceBundle.getBundle(BADKPI, local);
	    } else {
		languageRb = ResourceBundle.getBundle(BADKPI);
	    }
	    return languageRb.getString(key);
	} catch (Exception ex) {
	    if (key != null) {
		return key;
	    }
	}
	return "";
    }

    //R2906_new_tamdx_120820_start
    public static String getStringConfigFile(String key) {
	try {
	    if (local != null) {
		languageRb = ResourceBundle.getBundle(CONFIG_FILE, local);
	    } else {
		languageRb = ResourceBundle.getBundle(CONFIG_FILE);
	    }
	    return languageRb.getString(key);
	} catch (Exception ex) {
	    if (key != null) {
		return key;
	    }
	}
	return "";
    }
    //R2906_new_tamdx_120820_end
    //R4219_thaont19_start
    private static final String RESOURCEINFRA = "com/viettel/config/language/Infrastructure";

    public static String getStringInfra(String key) {
	try {
	    if (local != null) {
		languageRb = ResourceBundle.getBundle(RESOURCEINFRA, local);
	    } else {
		languageRb = ResourceBundle.getBundle(RESOURCEINFRA);
	    }
	    return languageRb.getString(key);
	} catch (Exception ex) {
	    if (key != null) {
		return key;
	    }
	}
	return "";
    }
    //R4219_thaont19_end
    //26122013_thaont19_baocaodong_start
    private static final String RESOURCE_ADM = "com/viettel/config/language/Adm";

    public static String getStringADM(String key) {
	try {
	    if (local != null) {
		languageRb = ResourceBundle.getBundle(RESOURCE_ADM, local);
	    } else {
		languageRb = ResourceBundle.getBundle(RESOURCE_ADM);
	    }
	    return languageRb.getString(key);
	} catch (Exception ex) {
	    if (key != null) {
		return key;
	    }
	}
	return "";
    }

    public static String getStringADM(Locale local, String key) {
	try {
	    LanguageBundleUtils.local = local;
	    if (local != null) {
		languageRb = ResourceBundle.getBundle(RESOURCE, local);
	    } else {
		languageRb = ResourceBundle.getBundle(RESOURCE);
	    }
	    return languageRb.getString(key);
	} catch (Exception ex) {
	    if (key != null) {
		return key;
	    }
	}
	return "";
    }
    //26122013_thaont19_baocaodong_end
    //19092014_thaont19_start

    public static String getStringInterface(String key) {
	try {
	    if (local != null) {
		languageRb = ResourceBundle.getBundle(RESOURCEINTERFACE, local);
	    } else {
		languageRb = ResourceBundle.getBundle(RESOURCEINTERFACE);
	    }
	    return languageRb.getString(key);
	} catch (Exception ex) {
	    if (key != null) {
		return key;
	    }
	}
	return "";
    }
    //19092014_thaont19_end
    // thanhdd11_R4962_start
    private static final String MAPPINGDEPTVSA = "com/viettel/config/language/MappingDeptVSA";

//    public static String getString(Locale locale, String key) {
//        try {
//            if (locale != local) {
//                try {
//                    local = locale;
//                    languageRb = ResourceBundle.getBundle(RESOURCE + "_" + local.getLanguage() + "_" + local.getCountry());
//                } catch (Exception ex) {
//                    languageRb = ResourceBundle.getBundle(RESOURCE);
//                }
//            }
//            return languageRb.getString(key);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return "";
//        }
//    }
    public static String getStringInterface(Locale locale, String key) {
	try {
	    ResourceBundle rc;
	    if (locale != null) {
		rc = ResourceBundle.getBundle(RESOURCEINTERFACE + "_" + locale.getLanguage() + "_" + locale.getCountry());
	    } else {
		rc = ResourceBundle.getBundle(RESOURCEINTERFACE);
	    }
	    return rc.getString(key);
	} catch (Exception ex) {
		logger.error(ex.getMessage(),ex);
	    return "";
	}
    }

    public static String getStringMappingDeptVSA(Locale locale, String key) {
	try {
	    ResourceBundle rc;
	    if (locale != null) {
		rc = ResourceBundle.getBundle(MAPPINGDEPTVSA + "_" + locale.getLanguage() + "_" + locale.getCountry());
	    } else {
		rc = ResourceBundle.getBundle(MAPPINGDEPTVSA);
	    }
	    return rc.getString(key);
	} catch (Exception ex) {
		logger.error(ex.getMessage(), ex);
	    return "";
	}
    }

    public static String getStringMappingDeptVSA(String key) {

	try {
	    if (local != null) {
		languageRb = ResourceBundle.getBundle(MAPPINGDEPTVSA, local);
	    } else {
		languageRb = ResourceBundle.getBundle(MAPPINGDEPTVSA);
	    }
	    return languageRb.getString(key);
	} catch (Exception e) {
	    return key;
	}
    }

    //thanhdd11_R4962_end
    //24092014 condt3_danhMucNguyenNhan and cau hinh chi tieu ky thuat
    public static String getStringCatReason(String key) {
	try {
	    if (local != null) {
		languageRb = ResourceBundle.getBundle(CATREASON, local);
	    } else {
		languageRb = ResourceBundle.getBundle(CATREASON);
	    }
	    return languageRb.getString(key);
	} catch (Exception ex) {
	    if (key != null) {
		return key;
	    }
	}
	return "";
    }

    public static String getStringCtConfigKpi(String key) {
	try {
	    if (local != null) {
		languageRb = ResourceBundle.getBundle(CONFIGKPI, local);
	    } else {
		languageRb = ResourceBundle.getBundle(CONFIGKPI);
	    }
	    return languageRb.getString(key);
	} catch (Exception ex) {
	    if (key != null) {
		return key;
	    }
	}
	return "";
    }

    public static String getStringCtSolution(String key) {
	try {
	    if (local != null) {
		languageRb = ResourceBundle.getBundle(CTSOLUTION, local);
	    } else {
		languageRb = ResourceBundle.getBundle(CTSOLUTION);
	    }
	    return languageRb.getString(key);
	} catch (Exception ex) {
	    if (key != null) {
		return key;
	    }
	}
	return "";
    }

    //R6335_tuevc_26112014_start
    public static String getStringSuggest(String key) {
	try {
	    if (local != null) {
		languageRb = ResourceBundle.getBundle(SUGGEST, local);
	    } else {
		languageRb = ResourceBundle.getBundle(SUGGEST);
	    }
	    return languageRb.getString(key);
	} catch (Exception ex) {
	    if (key != null) {
		return key;
	    }
	}
	return "";
    }
    //R6335_tuevc_26112014_end
    //R6335_thanhdd11_02122014_start

    public static String getStringSuggestMove(String key) {
	try {
	    if (local != null) {
		languageRb = ResourceBundle.getBundle(SUGGESTMOVE, local);
	    } else {
		languageRb = ResourceBundle.getBundle(SUGGESTMOVE);
	    }
	    return languageRb.getString(key);
	} catch (Exception ex) {
	    if (key != null) {
		return key;
	    }
	}
	return "";
    }
    //R6335_thanhdd11_02122014_end
    //Rxxxx_HieuHT14_start
    public static String getStringMeasure(String key) {
	try {
	    if (local != null) {
		languageRb = ResourceBundle.getBundle(MEASURE, local);
	    } else {
		languageRb = ResourceBundle.getBundle(MEASURE);
	    }
	    return languageRb.getString(key);
	} catch (Exception ex) {
	    if (key != null) {
		return key;
	    }
	}
	return "";
    }
    //Rxxxx_HieuHT14_end
    
    //R12690_HieuHT14_start
    public static String getStringVibaVsat(String key) {
	try {
	    if (local != null) {
		languageRb = ResourceBundle.getBundle(VIBA_VSAT, local);
	    } else {
		languageRb = ResourceBundle.getBundle(VIBA_VSAT);
	    }
	    return languageRb.getString(key);
	} catch (Exception ex) {
	    if (key != null) {
		return key;
	    }
	}
	return "";
    }
    //R12690_HieuHT14_end
    
//    R12970_p2_hantt59_Start
    public static String getStringCfgTemp(String key) {
	try {
	    if (local != null) {
		languageRb = ResourceBundle.getBundle(CFGTEMPKPI, local);
	    } else {
		languageRb = ResourceBundle.getBundle(CFGTEMPKPI);
	    }
	    return languageRb.getString(key);
	} catch (Exception ex) {
	    if (key != null) {
		return key;
	    }
	}
	return "";
    }
//    R12970_p2_hantt59_end
//    VIPC_SUGGEST_TRANSMISSION_START
//    private static final String SUGGEST_TRANSMISSION = "com/viettel/config/language/SuggestTransmission";
//    public static String getStringSuggestTransmission(String key) {
//	try {
//            local = (new ActionSupport()).getLocale();
//	    if (local != null) {
//		languageRb = ResourceBundle.getBundle(SUGGEST_TRANSMISSION, local);
//	    } else {
//		languageRb = ResourceBundle.getBundle(SUGGEST_TRANSMISSION);
//	    }
//	    return languageRb.getString(key);
//	} catch (Exception ex) {
//	    if (key != null) {
//		return key;
//	    }
//	}
//	return "";
//    }
////    VIPC_SUGGEST_TRANSMISSION_END
////   20180418_V2_SUGGESTION_START
    private static final String V2_SUGGESTION = "com/viettel/config/language/V2Suggestion";
    public static String getStringV2Suggestion(String key) {
	try {
		languageRb = ResourceBundle.getBundle(V2_SUGGESTION);
	    return languageRb.getString(key);
	} catch (Exception ex) {
	    if (key != null) {
		return key;
	    }
	}
	return "";
    }
//   20180418_V2_SUGGESTION_END
}
