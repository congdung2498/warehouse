package com.viettel.vtnet360.common.convertPdf;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import com.viettel.vtnet360.vt00.common.utils.ResourceBundleUtils;
import org.apache.log4j.Logger;
public class ConvertExcelToPdf {
	private final static Logger logger = Logger.getLogger(ConvertExcelToPdf.class);
	public static final String SOFFICE_PATH;
	
	private static final int OS_TYPE;// 0: window; 1:linux; -1: other
	static {
		String osName = System.getProperty("os.name");
		if (osName != null && osName.toLowerCase().contains("window")) {
			OS_TYPE = 0;
			SOFFICE_PATH = ResourceBundleUtils.getValueByKey("SOFFICE_PATH");
		} else if (osName != null && osName.toLowerCase().contains("linux")) {
			OS_TYPE = 1;
			SOFFICE_PATH = ResourceBundleUtils.getValueByKey("SOFFICE_PATH");
		} else {
			OS_TYPE = -1;
			SOFFICE_PATH = null;
		}
	}
	
	public static String convertToPDF( String outdirPath, String documentPath) throws IOException {
		boolean invalid = false;
		String msg = "";
		if (SOFFICE_PATH == null || SOFFICE_PATH.isEmpty() || !new File(SOFFICE_PATH).exists()) {
			invalid = true;
			msg += "sofficePath is not exist \r\n";
		}
		if (outdirPath == null || outdirPath.isEmpty() || !new File(outdirPath).exists()) {
			invalid = true;
			msg += "outdirPath is not exist \r\n";
		}
		if (documentPath == null || documentPath.isEmpty() || !new File(documentPath).exists()) {
			invalid = true;
			msg += "documentPath is not exist \r\n";
		}
		outdirPath = "\"" + new File(outdirPath).getAbsolutePath() + "\"";
		documentPath = "\"" + new File(documentPath).getAbsolutePath() + "\"";
		if (invalid) {
			try {
				throw new Exception(msg);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		String cmd = "cd \"" + new File(SOFFICE_PATH).getParentFile().getAbsolutePath() + "\" && "; 
		cmd += "." + File.separator + new File(SOFFICE_PATH).getName() + " --headless --convert-to pdf:writer_pdf_Export --outdir " + outdirPath + " " + documentPath;
		process(cmd, new File(SOFFICE_PATH).getParentFile());
		String pdfPath = documentPath.replaceAll("\"", "").replace(".docx", ".pdf");
		if (pdfPath == null || pdfPath.isEmpty() || !new File(pdfPath).exists()) {
			System.out.println("Convert failure.");
			return null;
		}
		return pdfPath;
    }
	
	private static String process(String cmd, File dir) throws IOException {
		System.out.println(cmd);
		ProcessBuilder builder = null;
		if (OS_TYPE == 1) {
			builder = new ProcessBuilder("bash", "-c", cmd);
		} else if (OS_TYPE == 0) {
			builder = new ProcessBuilder("cmd.exe", "/c", cmd);
		}
		if(builder != null){
		builder.redirectErrorStream(true);
		builder.directory(dir);
		Process p = builder.start();
		BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line = null;
		StringBuilder content = new StringBuilder();
		while (true) {
			try {
				line = r.readLine();
				content.append(line).append("\n");
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
			if (line == null) {
				break;
			}
		
		}
		return content.toString();
	}
	 return null;
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(convertToPDF( new File("C:/Users/vtn-ptpm-nv76/Documents/Downloads/voffice/docx2html/gen_word/ninhnv1rk_test_2019_7_8_16h9m24s755ms.xlsx").getParent(), "C:/Users/vtn-ptpm-nv76/Documents/Downloads/voffice/docx2html/gen_word/ninhnv1rk_test_2019_7_8_16h9m24s755ms.xlsx"));
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			// TODO Auto-generated catch block
		}
	}
}
