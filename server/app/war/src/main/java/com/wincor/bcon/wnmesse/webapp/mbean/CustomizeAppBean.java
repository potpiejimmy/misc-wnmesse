package com.wincor.bcon.wnmesse.webapp.mbean;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.imageio.ImageIO;
import javax.inject.Named;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;
import com.wincor.bcon.wnmesse.server.ejb.AppCreatorEJBLocal;

/**
 * This is a sample JSF managed bean.
 * 
 * Pleas note that no configuration in faces-config.xml is required, the
 * bean can be referenced in a JSF faces file (XHTML) using the name
 * specified in the Named annotation. Also note the scope annotation
 * that specified the managed bean's scope.    
 */
@Named
@SessionScoped
public class CustomizeAppBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EJB
	private AppCreatorEJBLocal creatorEJB;
	
	private String outputFile = null;

	/**
	 * Called from JSF if save button is pressed.
	 */
	public void createApp() {
		this.outputFile = creatorEJB.createApplication();
	}

	public String getOutputFile() {
		return this.outputFile;
	}
	
	public StreamedContent getQrcode() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		generateQRCodeImage(baos, this.outputFile, 240, 240);
        return new DefaultStreamedContent(new ByteArrayInputStream(baos.toByteArray()), "image/png");
    }  
	
    private static void generateQRCodeImage(OutputStream outputStream, String code, int width, int height) throws Exception {

              if (code == null || code.length() == 0)
                      throw new Exception("Code unspecified");

              if (width <= 0 || height <= 0)
                      throw new Exception("Invalid width: " + width + " or height " + height);

              if (width != height)
                      throw new Exception("width " + width + " and height " + height + " are not the same");

              QRCode qrcode = Encoder.encode(code, ErrorCorrectionLevel.L);
              int qrSize = qrcode.getMatrix().getWidth();

              int margin = 4;
              int imageSize = qrSize + 2 * margin; // includes quiet zone
              if (width < imageSize) width = imageSize;
              int magnify = width / imageSize;
              int remaining = width % imageSize;
              int topLeftPosition =  ((remaining > 0) ? remaining / 2 : magnify) +
margin * magnify;
              int size = width;

              // Make the BufferedImage that are to hold the QRCode
              BufferedImage image = new BufferedImage(size, size,
BufferedImage.TYPE_INT_RGB);
              image.createGraphics();
              Graphics2D graphics = (Graphics2D) image.getGraphics();
              graphics.setColor(Color.WHITE);
              graphics.fillRect(0, 0, size, size);

              // paint the image using the ByteMatrix
              graphics.setColor(Color.BLACK);
              for (int i = 0; i < qrSize; i ++) {
                      for (int j = 0; j < qrSize; j ++) {
                              if (qrcode.getMatrix().get(i, j) == 1)
                                      graphics.fillRect(topLeftPosition + i * magnify, topLeftPosition + j * magnify, magnify, magnify);
                      }
              }

              ImageIO.write(image, "png", outputStream);
      }

}
