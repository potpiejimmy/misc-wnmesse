package com.wincor.bcon.wnmesse.webapp.mbean;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;
import com.wincor.bcon.wnmesse.server.ejb.AppCreatorEJBLocal;
import com.wincor.bcon.wnmesse.server.util.Utils;
import com.wincor.bcon.wnmesse.server.vo.AppCreationResult;
import com.wincor.bcon.wnmesse.server.vo.AppCustomization;

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
	
	private static final int MAX_IMAGE_SIZE = 320;

	@EJB
	private AppCreatorEJBLocal creatorEJB;
	
	private AppCreationResult creationResult = null;
	
	private AppCustomization customization = new AppCustomization();

	/**
	 * Called from JSF if save button is pressed.
	 */
	public void createApp() {
		this.creationResult = creatorEJB.createApplication(this.customization);
	}

	public AppCreationResult getCreationResult() {
		return this.creationResult;
	}
	
	public StreamedContent getQrcode() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		generateQRCodeImage(baos, this.creationResult.getLink(), 240, 240);
        return new DefaultStreamedContent(new ByteArrayInputStream(baos.toByteArray()), "image/png");
    }  
	
	public String reset() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index.xhtml?faces-redirect=true";
    }
	
    public void handleFileUpload(FileUploadEvent event) {  
        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
        
        try {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream in = event.getFile().getInputstream();
        Utils.writeThrough(in, baos);
        in.close();
        baos.close();
        this.customization.setImg(Utils.getJPEGDownSizedImage(baos.toByteArray(), MAX_IMAGE_SIZE, MAX_IMAGE_SIZE, 1.0f));
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
    }  
    
	public boolean isImgAvailable() {
		return this.customization.getImg() != null;
	}
	
	public StreamedContent getImg() throws Exception {
        return new DefaultStreamedContent(new ByteArrayInputStream(this.customization.getImg()), "image/jpeg");
    }  
	
    public AppCustomization getCustomization() {
		return this.customization;
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
