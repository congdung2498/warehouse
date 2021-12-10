package com.viettel.vtnet360.otp;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OTPService {
  private final Logger logger = Logger.getLogger(this.getClass());

  @Autowired
  private DataSource dataSource;
  

  public String sendOTP(String username) {
    String otp = createOTP(6);
    delete(username);
    insert(username, otp);
    insertMessage(username, otp);
    return otp;
  }

  public CapchaImage getCaptchaImage(String username) {
    CapchaImage capcha = getCaptchaImage();
    update(username, 0, capcha.getCapcha());
    return capcha;
  }

  public int isValidOTP(OTP otp) {
    OTP createdOtp = getOTPInfo(otp.getUsername());

    Calendar current = Calendar.getInstance();
    long range = current.getTimeInMillis() - createdOtp.getCreateDate().getTime();
    if(range > (5 * 60 * 1000)) return 2;

    if(createdOtp.getOtp().equals(otp.getOtp())) {
      delete(otp.getUsername());
      return 1;
    }

    return 3;
  }

  public int isValidCapcha(OTP otp) {
    OTP createdOtp = getOTPInfo(otp.getUsername());
    if(createdOtp.getCapcha().equals(otp.getCapcha())) return 1;
    return 3;
  }

  private String createOTP(int size) {
    StringBuilder generatedToken = new StringBuilder();
    try {
      SecureRandom number = SecureRandom.getInstance("SHA1PRNG");
      for (int i = 0; i < size; i++) {
        generatedToken.append(number.nextInt(9));
      }
    } catch (NoSuchAlgorithmException e) {
      logger.error(e.getMessage(), e);
    }

    return generatedToken.toString();
  }

  private CapchaImage getCaptchaImage() {
    try {
      Color backgroundColor = Color.white;
      Color borderColor = Color.black;
      Color textColor = Color.black;
      Color circleColor = new Color(190, 160, 150);
      Font textFont = new Font("Verdana", Font.BOLD, 20);
      int charsToPrint = 6;
      int width = 160;
      int height = 50;
      int circlesToDraw = 25;
      float horizMargin = 10.0f;
      double rotationRange = 0.7; 
      BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
      Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
      g.setColor(backgroundColor);
      g.fillRect(0, 0, width, height);

      g.setColor(circleColor);
      for (int i = 0; i < circlesToDraw; i++) {
        int L = (int) (Math.random() * height / 2.0);
        int X = (int) (Math.random() * width - L);
        int Y = (int) (Math.random() * height - L);
        g.draw3DRect(X, Y, L * 2, L * 2, true);
      }
      g.setColor(textColor);
      g.setFont(textFont);
      FontMetrics fontMetrics = g.getFontMetrics();
      int maxAdvance = fontMetrics.getMaxAdvance();
      int fontHeight = fontMetrics.getHeight();

      String elegibleChars = "ABCDEFGHJKLMNPQRSTUVWXYabcdefghjkmnpqrstuvwxy0123456789";
      char[] chars = elegibleChars.toCharArray();
      float spaceForLetters = -horizMargin * 2 + width;
      float spacePerChar = spaceForLetters / (charsToPrint - 1.0f);
      StringBuffer finalString = new StringBuffer();
      for (int i = 0; i < charsToPrint; i++) {
        double randomValue = Math.random();
        int randomIndex = (int) Math.round(randomValue * (chars.length - 1));
        char characterToShow = chars[randomIndex];
        finalString.append(characterToShow);

        // we can rotate it independently
        int charWidth = fontMetrics.charWidth(characterToShow);
        int charDim = Math.max(maxAdvance, fontHeight);
        int halfCharDim = (int) (charDim / 2);
        BufferedImage charImage = new BufferedImage(charDim, charDim, BufferedImage.TYPE_INT_ARGB);
        Graphics2D charGraphics = charImage.createGraphics();
        charGraphics.translate(halfCharDim, halfCharDim);
        double angle = (Math.random() - 0.5) * rotationRange;
        charGraphics.transform(AffineTransform.getRotateInstance(angle));
        charGraphics.translate(-halfCharDim, -halfCharDim);
        charGraphics.setColor(textColor);
        charGraphics.setFont(textFont);
        int charX = (int) (0.5 * charDim - 0.5 * charWidth);
        charGraphics.drawString("" + characterToShow, charX, (int) ((charDim - fontMetrics.getAscent()) / 2 + fontMetrics.getAscent()));
        float x = horizMargin + spacePerChar * (i) - charDim / 2.0f;
        int y = (int) ((height - charDim) / 2);
        g.drawImage(charImage, (int) x, y, charDim, charDim, null, null);
        charGraphics.dispose();
      }
      g.setColor(borderColor);
      g.drawRect(0, 0, width - 1, height - 1);
      g.dispose();

      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ImageIO.write(bufferedImage, "png", baos );
      baos.flush();
      byte[] imageInByte = baos.toByteArray();
      baos.close();

      return new CapchaImage(imageInByte, finalString.toString());
    } catch (Exception ioe) {
      throw new RuntimeException("Unable to build image", ioe);
    }
  }

  public OTP getOTPInfo(String username) {
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet rs = null;
    try {
      con = dataSource.getConnection();
      preparedStatement = con.prepareStatement("SELECT * FROM OTP WHERE USERNAME = ?");
      preparedStatement.setString(1, username);
      rs = preparedStatement.executeQuery();
      OTP otp = null;
      while(rs.next()) {
        otp = new OTP(rs.getString("USERNAME"), rs.getString("OTP"), rs.getInt("COUNT"), rs.getString("CAPCHA"), rs.getDate("CREATE_DATE"));
      }
      return otp;
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    } finally {
      if (con != null) {
        try {
          con.close();
        } catch (SQLException e) {
          logger.error(e);
       }
      }
      if (preparedStatement != null) {
        try {
          preparedStatement.close();
        } catch (SQLException e) {
          logger.error(e);
       }
      }
      if (rs != null) {
        try {
          rs.close();
        } catch (SQLException e) {
          logger.error(e);
        }
      }
//      close(rs);
//      close(con);
//      close(preparedStatement);
    }
    return null;
  }

  public void delete(String username) {
    Connection con = null;
    PreparedStatement preparedStatement = null;

    try {
      con = dataSource.getConnection();
      preparedStatement = con.prepareStatement("DELETE FROM OTP WHERE USERNAME = ?");
      preparedStatement.setString(1, username);
      preparedStatement.executeUpdate();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    } finally {
      if (con != null) {
        try {
          con.close();
        } catch (SQLException e) {
          logger.error(e);
        }
      }
      if (preparedStatement != null) {
        try {
          preparedStatement.close();
        } catch (SQLException e) {
          logger.error(e);
        }
      }
    }
  }

  public void update(String username, int count, String capcha) {
    Connection con = null;
    PreparedStatement preparedStatement = null;

    try {
      con = dataSource.getConnection();
      preparedStatement = con.prepareStatement("UPDATE OTP SET COUNT = ?, CAPCHA = ? WHERE USERNAME = ?");
      preparedStatement.setInt(1, count);
      preparedStatement.setString(2, capcha);
      preparedStatement.setString(3, username);
      preparedStatement.executeUpdate();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    } finally {
      if (con != null) {
        try {
          con.close();
        } catch (SQLException e) {
          logger.error(e);
        }
      }
      if (preparedStatement != null) {
        try {
          preparedStatement.close();
        } catch (SQLException e) {
          logger.error(e);
        }
      }
    }
  }

  public void insert(String username, String otp) {
    Connection con = null;
    PreparedStatement preparedStatement = null;

    try {
      con = dataSource.getConnection();
      preparedStatement = con.prepareStatement("INSERT INTO OTP (USERNAME, OTP, COUNT, CREATE_DATE) VALUES(?, ?, 0, NOW())");
      preparedStatement.setString(1, username);
      preparedStatement.setString(2, otp);
      preparedStatement.executeUpdate();
    } catch (Exception e) {
      logger.error(e);
    } finally {
      if (con != null) {
        try {
          con.close();
        } catch (SQLException e) {
          logger.error(e);
        }
      }
      if (preparedStatement != null) {
        try {
          preparedStatement.close();
        } catch (SQLException e) {
          logger.error(e);
        }
      }
    }
  }
  
  public void insertMessage(String username, String otp)  {
    Connection con = null;
    PreparedStatement preparedStatement = null;
    PreparedStatement empPreparedStatement = null;
    ResultSet rs = null;
    try {
      con = dataSource.getConnection();
      String getEmployeeSql = "SELECT EMPLOYEE_ID, PHONE_NUMBER FROM QLDV_EMPLOYEE WHERE USER_NAME = ?";
      empPreparedStatement = con.prepareStatement(getEmployeeSql);
      empPreparedStatement.setString(1, username);
      rs = empPreparedStatement.executeQuery();
      //if(rs == null) return; 
      String userId = null;
      String phone = null;
      if (rs != null) {
          while(rs.next()) {
            userId = rs.getString("EMPLOYEE_ID");
            phone = rs.getString("PHONE_NUMBER");
          }
          rs.close();
      }
      String content = "[VTNet360] Ma bao mat " + otp + " co hieu luc trong 5 phut";
      String sql = "INSERT INTO CAT_MESSAGE " + 
            "(USER_ID, CONTENT, STATUS, INSERT_TIME, PHONE, SMS_TYPE) "  +
            "VALUES (?, ?, ?, NOW(), ?, ?) ";
      
      preparedStatement = con.prepareStatement(sql);
      preparedStatement.setString(1, userId);
      preparedStatement.setString(2, content);
      preparedStatement.setInt(3, 0);
      preparedStatement.setString(4, phone);
      preparedStatement.setString(5, "OTP");
      preparedStatement.executeUpdate();
    } catch (Exception e) {
      logger.error(e);
    } finally {
      if (rs != null) {
        try {
          rs.close();
        } catch (SQLException e) {
          logger.error(e);
        }
      }
      if (con != null) {
        try {
          con.close();
        } catch (SQLException e) {
          logger.error(e);
        }
      }
      if (preparedStatement != null) {
        try {
          preparedStatement.close();
        } catch (SQLException e) {
          logger.error(e);
        }
      }
      if (empPreparedStatement != null) {
        try {
          empPreparedStatement.close();
        } catch (SQLException e) {
          logger.error(e);
        }
      }
    	}
  }

  private void close(Connection con) {
    if (con != null) {
      try {
        con.close();
      } catch (SQLException e) {
        logger.error(e);
      }
    }
  }

  private void close(Statement statement) {
    if (statement != null) {
      try {
        statement.close();
      } catch (SQLException e) {
        logger.error(e);
      }
    }
  }
  
  private void close(ResultSet rs) {
	    if (rs != null) {
	      try {
	        rs.close();
	      } catch (SQLException e) {
	        logger.error(e);
	      }
	    }
	  }
}
