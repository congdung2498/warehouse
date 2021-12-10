package vt70000.entity;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt07.vt070000.entity.DocumentDetails;


public class DocumentDetailsTest {
	
	@Test
	public void setter_constructionName_SetProperly() throws NoSuchFieldException, IllegalAccessException{
		final DocumentDetails mockEntity = new DocumentDetails();
		mockEntity.setConstructionName("Test value");
		
		final Field field = mockEntity.getClass().getDeclaredField("constructionName");
		field.setAccessible(true);
		assertEquals("Fields didn't match", field.get(mockEntity), "Test value");
	}
	
	@Test
	public void getter_constructionName_GetProperly() throws NoSuchFieldException, IllegalAccessException{
		final DocumentDetails mockEntity = new DocumentDetails();
		final Field field = mockEntity.getClass().getDeclaredField("constructionName");
        field.setAccessible(true);
        field.set(mockEntity, "Test value");
		
        final String result = mockEntity.getConstructionName();

        //then
        assertEquals("field wasn't retrieved properly", result, "Test value");
	}
	
	@Test
	public void setter_contractName_SetProperly() throws NoSuchFieldException, IllegalAccessException{
		final DocumentDetails mockEntity = new DocumentDetails();
		mockEntity.setContractName("Test value");
		
		final Field field = mockEntity.getClass().getDeclaredField("contractName");
		field.setAccessible(true);
		assertEquals("Fields didn't match", field.get(mockEntity), "Test value");
	}
	
	@Test
	public void getter_contractName_GetProperly() throws NoSuchFieldException, IllegalAccessException{
		final DocumentDetails mockEntity = new DocumentDetails();
		final Field field = mockEntity.getClass().getDeclaredField("contractName");
        field.setAccessible(true);
        field.set(mockEntity, "Test value");
		
        final String result = mockEntity.getContractName();

        //then
        assertEquals("field wasn't retrieved properly", result, "Test value");
	}
}
