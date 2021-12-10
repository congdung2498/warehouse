package com.viettel.vtnet360.common.security;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceUtils;

@Configuration
public class CustomJDBCAppender extends org.apache.log4j.jdbc.JDBCAppender {
	private final Logger logger = Logger.getLogger(this.getClass());
	 private String driverClassName = null;
	 private String url = null;
	 private String dbUsername = null;
	 private String dbPassword = null;

	
	public void getDataProperties(){
		
		// if setting already set then we won't set it again
		// if (driverClassName != null) return;
		
		InputStream inputStream = null;
		try {
			// init to read from properties
			Properties prop = new Properties();
			String propFileName = "environment.properties";
 
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
			// set setting
//			driverClassName = EnDecryptUtils.decrypt(prop.getProperty("jdbc.driverClassName"));
//			url = EnDecryptUtils.decrypt(prop.getProperty("jdbc.url"));
//			dbUsername = EnDecryptUtils.decrypt(prop.getProperty("jdbc.username"));
//			dbPassword = EnDecryptUtils.decrypt(prop.getProperty("jdbc.password"));
			driverClassName = prop.getProperty("jdbc.driverClassName");
			url = prop.getProperty("jdbc.url");
			dbUsername = prop.getProperty("jdbc.username");
			dbPassword = prop.getProperty("jdbc.password");
			
		} catch (Exception e) {
			System.err.println("Exception: " + e);
		} finally {
			if(inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

	protected BasicDataSource getDataSource() {
		getDataProperties();
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(this.driverClassName);
		dataSource.setUrl(this.url);
		dataSource.setUsername(this.dbUsername);
		dataSource.setPassword(this.dbPassword);
		return dataSource;
	}

	
	protected java.sql.Connection getConnection() throws java.sql.SQLException {
         if(connection == null || connection.isClosed()) {
             connection = DataSourceUtils.getConnection(getDataSource());
             return connection;
         } else{
             return connection;
         }
     }

    protected void execute(String message) throws java.sql.SQLException {

	     Connection con = null;
	     PreparedStatement stmt = null;
	     String sql = "INSERT INTO `LOGGER` (`DATETIME`, `METHOD_NAME`, `LEVEL`, `MESSAGE`) VALUES (?,?,?,?);";
	     String[] parts = message.split("',split'", -1);
	     try {
	         con = getConnection();
	         stmt = con.prepareStatement(sql);
	         stmt.setString(1, parts[0].trim());
	         stmt.setString(2, parts[1].trim().substring(0, Math.min(255, parts[1].trim().length())));
	         stmt.setString(3, parts[2].trim().substring(0, Math.min(10, parts[2].trim().length())));
	         stmt.setString(4, parts[3].trim().substring(0, Math.min(1000, parts[3].trim().length())));
	         stmt.executeUpdate();
	     } finally {
	         if(stmt != null) {
	             stmt.close();
	         }
	         closeConnection(con);
	     }
     }
    
     protected void closeConnection() throws java.sql.SQLException {
             if (connection != null && !connection.isClosed())
                   connection.close();
     }
}