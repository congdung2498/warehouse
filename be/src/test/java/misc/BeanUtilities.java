package misc;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.SecureRandom;

public class BeanUtilities {   
	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static SecureRandom rnd = new SecureRandom();
	
	public static String randomString( int len ){
	   StringBuilder sb = new StringBuilder( len );
	   for( int i = 0; i < len; i++ ) 
	      sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
	   return sb.toString();
	}
	
    public static Object getObjectFieldValue(Object object, String fieldName) {
		Object result = null;
		try {
			Class<?> clazz = object.getClass();
			Field field = clazz.getField(fieldName);
			field.setAccessible(true);
			result = field.get(object);
		}
		catch(Exception e) {}
		return result;
	}
    
}
