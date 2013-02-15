package com.wincor.bcon.wnmesse.server.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.swing.ImageIcon;

/**
 * Contains useful static utility methods used throughout the MMD server application
 */
public class Utils
{
    /**
     * Reads all bytes available on the input stream and writes them
     * to the output stream
     *
     * @param	is input stream
     * @param	os output stream
     */
    public static void writeThrough(InputStream is, OutputStream os) throws IOException
    {
        byte[] buf = new byte[1024];
        int read = is.read(buf);
        while (read!=-1)
        {
            os.write(buf, 0, read);
            read = is.read(buf);
        }
    }
    
    /**
     * Read a given resource to memory and return it in a byte array
     */
    public static byte[] readResource(String resource) throws IOException
    {
        return readResource(Thread.currentThread().getContextClassLoader(), resource);
    }
    
    /**
     * Read a given resource to memory and return it in a byte array
     */
    public static byte[] readResource(ClassLoader classLoader, String resource) throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        writeThrough(classLoader.getResourceAsStream(resource), baos);
        baos.close();
        return baos.toByteArray();
    }
    
    /**
     * Returns a scaled buffered image of the given image.
     * @param image an image
     * @param width new width
     * @param height new height
     * @return scaled image instance as a buffered image
     */
    public static BufferedImage getScaledBufferedImage(Image image, int width, int height)
    {
        image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        img.getGraphics().drawImage(new ImageIcon(image).getImage(), 0, 0, null);
        return img;
    }
    
    /**
     * Returns the JPEG encoding of the given buffered image
     * @param bufferedImage an image
     * @return byte array (JPEG encoded image)
     */
    public static byte[] getJPEGEncoding(BufferedImage bufferedImage, float quality) throws IOException
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        
        Iterator<ImageWriter> writers = ImageIO.getImageWritersBySuffix("jpeg");
        ImageWriter writer = writers.next();
        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(quality);
        writer.setOutput(ImageIO.createImageOutputStream(bos));
        writer.write(null, new IIOImage(bufferedImage, null, null), param);
        
        bos.close();
        return bos.toByteArray();
    }
    
    /**
     * Checks the size of the given image data, resizes it to comply with maxWidth
     * and maxHeight if necessary, and returns the jpeg encoding of the resulting
     * image
     * @param imageData input image data, may be png, jpg, gif
     * @param maxWidth maximum width, downsize if necessary (keeps aspect ratio)
     * @param maxHeight maximum height, downsize if necessary (keeps aspect ratio)
     * @return jpeg encoding
     */
    public static byte[] getJPEGDownSizedImage(byte[] imageData, int maxWidth, int maxHeight, float quality) throws IOException
    {
        ImageIcon img = new ImageIcon(imageData);
        float downFacX = ((float)img.getIconWidth())/maxWidth;
        float downFacY = ((float)img.getIconHeight())/maxHeight;
        float downFac = Math.max(1.0f, Math.max(downFacX, downFacY));
        BufferedImage bufImg = getScaledBufferedImage(img.getImage(), (int)(img.getIconWidth()/downFac), (int)(img.getIconHeight()/downFac));
        return getJPEGEncoding(bufImg, quality);
    }
}
