package com.viettel.vtnet360.vt00.common;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class AvatarSync {
	
	private final static Logger logger = Logger.getLogger(AvatarSync.class);
	
	public static String GetAvataByEmployeeID(String employeeCode) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			ResourceBundle resource = ResourceBundle.getBundle("config");
			String urlToken = resource.getString("TOKEN_URL");
			String grantType = resource.getString("GRANT_TYPE");
			String clientId = resource.getString("CLIENT_ID");
			String clientSecret = resource.getString("CLIENT_SECRET");
			String username = resource.getString("USERNAME_TOKEN");
			String password = resource.getString("PASS_WORD");
			String urlAvata = resource.getString("AVATA_URL");
			MultiValueMap<String, Object> paramsToken = new LinkedMultiValueMap<String, Object>();
			paramsToken.add("grant_type", grantType);
			paramsToken.add("client_id", clientId);
			paramsToken.add("client_secret", clientSecret);
			paramsToken.add("username", username);
			paramsToken.add("password", password);
			HttpEntity entityToken = new HttpEntity(paramsToken, headers);
			TTNSToken bodyToken = restTemplate.postForEntity(urlToken, entityToken, TTNSToken.class).getBody();
			if (StringUtils.isNotEmpty(bodyToken.getToken_type()) && StringUtils.isNotEmpty(bodyToken.getAccess_token())) {
				headers.set("Authorization", bodyToken.getToken_type() + " " + bodyToken.getAccess_token());
				HttpEntity entityEmp = new HttpEntity(headers);
				UriComponentsBuilder builderAvata = UriComponentsBuilder.fromUriString(urlAvata);
				builderAvata.queryParam("employee_code", employeeCode);
				LinkedHashMap bodyAvata = (LinkedHashMap) restTemplate.exchange(builderAvata.buildAndExpand().toUri(), HttpMethod.GET, entityEmp, Object.class).getBody();
				if ("200".equals(String.valueOf(bodyAvata.get("status")))) {

					String base64Image = String.valueOf(bodyAvata.get("entity"));
					
					try {
						// create a buffered image
						BufferedImage image = null;
						BufferedImage imageOuput = null;
						byte[] imageByte;
						
						Decoder decoder = Base64.getMimeDecoder();
						Encoder encoder = Base64.getMimeEncoder();
						imageByte = decoder.decode(base64Image.split(",")[1]);
						
						ByteArrayOutputStream os = new ByteArrayOutputStream();
						
						ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
						image = ImageIO.read(bis);
						bis.close();
						
						double aspectRatio = (double) image.getWidth()/(double) image.getHeight();
						imageOuput = resizeImage(image, 480, (int) (480/aspectRatio));
						
						ImageIO.write(imageOuput, "jpg", os);
						base64Image = encoder.encodeToString(os.toByteArray());
					} catch (Exception ex) {
						logger.error(ex.getMessage(), ex);
					}
					
					return "data:image/jpeg;base64," + base64Image.replaceAll("\\n", "").replaceAll("\\r", "");
				}
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		 String base64Image = "data:image/jpeg;base64,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";
		try {
			// create a buffered image
			BufferedImage image = null;
			BufferedImage imageOuput = null;
			byte[] imageByte;
			
			Decoder decoder = Base64.getMimeDecoder();
			Encoder encoder = Base64.getMimeEncoder();
			imageByte = decoder.decode(base64Image.split(",")[1]);
			
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			
			ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
			image = ImageIO.read(bis);
			bis.close();
			
			double aspectRatio = (double) image.getWidth()/(double) image.getHeight();
			imageOuput = resizeImage(image, 480, (int) (480/aspectRatio));
			
			ImageIO.write(imageOuput, "jpg", os);
			base64Image = encoder.encodeToString(os.toByteArray());
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		return "data:image/jpeg;base64," + base64Image.replaceAll("\\n", "").replaceAll("\\r", "");
	}
	
	public static BufferedImage resizeImage(final Image image, int width, int height) {
        final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        final Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setComposite(AlphaComposite.Src);
        //below three lines are for RenderingHints for better image quality at cost of higher processing time
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.drawImage(image, 0, 0, width, height, null);
        graphics2D.dispose();
        return bufferedImage;
    }
}
