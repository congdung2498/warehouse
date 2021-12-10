package com.viettel.vtnet360.common.convertPdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfAnnotation;
import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfRectangle;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.TextMarginFinder;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
import com.itextpdf.text.pdf.parser.Vector;
import org.apache.log4j.Logger;

public class AddComment {
	@Autowired
	Properties linkTemplateExcel;
	private final static Logger logger = Logger.getLogger(AddComment.class);
	
	public static void addCommentSignature(String pathIn, String pathOut, List<String> patterns,String userName ,String inpath) {

		PdfReader reader = null;
		PdfReaderContentParser parser = null;
		//String inpath = "/var/templatePdf/comentExample.pdf";
		//String inpath = "D:/quang/VTNET/trunk/1.Development/QTVP/libs/documentVO/"+"comentExample.pdf";
		try {
			List<PdfDictionary> list = getAnno(inpath);
			reader = new PdfReader(pathIn);
			final PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(pathOut));
			parser = new PdfReaderContentParser(reader);
//			final StringBuilder word = new StringBuilder();
			for (final String pattern : patterns) {
				/*int orderInt = extractNumber(pattern, prefix);
				if (orderInt == -1) {
					continue;
				}*/
				for (int i = 1; i <= reader.getNumberOfPages(); i++) {
					final int pageNumber = i;
					parser.processContent(i, new TextMarginFinder() {
						@Override
						public void renderText(TextRenderInfo renderInfo) {
							//System.out.println(renderInfo.getText()+" ss");
							super.renderText(renderInfo);
						
							if (renderInfo != null && renderInfo.getText() != null && !renderInfo.getText().trim().isEmpty()) {
								
								if (renderInfo.getText().contains(pattern)) {
									
									float x = renderInfo.getAscentLine().getStartPoint().get(Vector.I1) + renderInfo.getAscentLine().getEndPoint().get(Vector.I1);
									x /= 2;
									x -= 5f;
									float y = renderInfo.getAscentLine().getStartPoint().get(Vector.I2) + renderInfo.getAscentLine().getEndPoint().get(Vector.I2);
									y /= 2;
									y += 3f;
									//Rectangle rect = new Rectangle(x, 0f, 0f, y);
									for (PdfDictionary d : list) {
										PdfAnnotation annot = PdfAnnotation.createText(stamper.getWriter(), null, null, null, true, null);
										annot.remove(PdfName.SUBTYPE);
										annot.remove(PdfName.OPEN);
										for (PdfName k : d.getKeys()) {
											if (PdfName.RECT.equals(k)) {
												PdfNumber r1 = new PdfNumber(x+5);
												PdfNumber r2 = new PdfNumber(y-50);
												PdfNumber r3 = new PdfNumber(x-10);
												PdfNumber r4 = new PdfNumber(y);
												annot.put(k, new PdfRectangle(new Rectangle(r1.floatValue(), r2.floatValue(), r3.floatValue(), r4.floatValue())));
												continue;
											}
											annot.put(k, d.get(k));
										}
										stamper.addAnnotation(annot, pageNumber);
									}
								}
							} 
						}
					});
		        }
				//addPage(reader,stamper);
			}
			stamper.close();
			reader.close();
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (DocumentException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	
	public static List<PdfDictionary> getAnno(String pathIn) throws IOException {
		List<PdfDictionary> list = new ArrayList<>();
		PdfReader reader = null;
		reader = new PdfReader(pathIn);
		PdfDictionary pageDict = reader.getPageN(1);

		PdfArray annotArray = pageDict.getAsArray(PdfName.ANNOTS);

		for (int i = 0; i < annotArray.size(); ++i) {
		  PdfDictionary curAnnot = annotArray.getAsDict(i);
		  list.add(curAnnot);
		}
		return list;
	}
	
	private static void addPage(String src,String dest){
		try {
			float factor = 2.5f;
			PdfReader reader = new PdfReader(src);
			int n = reader.getNumberOfPages();
			System.out.println(n);
			PdfDictionary page;
			for (int p = 1; p <= n; p++) {
			    page = reader.getPageN(p);
			    if (page.getAsNumber(PdfName.USERUNIT) == null)
			        page.put(PdfName.USERUNIT, new PdfNumber(factor));
			
			}
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
			stamper.close();
			reader.close();
		} catch (IOException | DocumentException e) {
			logger.error(e.getMessage(), e);
		}
	
	}
	
	private String convertUrl(){
	    int lastRegex = System.getProperty("catalina.home").lastIndexOf(File.separator);
	    String url=System.getProperty("catalina.home").substring(0, lastRegex);
	    
	    File theDir = new File(url+"/documentVO");
			 if (!theDir.exists()) {
				 theDir.getName();
			     try{
			         theDir.mkdir();
			     } 
			     catch(SecurityException se){
			     }        
	
			 }
	    return url;
	}
	
	public static void main(String[] args) {
		addPage("D:/quang/VTNET/trunk/1.Development/QTVP/libs/documentVO/216.out.pdf","D:/quang/VTNET/trunk/1.Development/QTVP/libs/documentVO/216.out.out.pdf");
	}
}
