
package imagesteganography;

/**
 *
 * @author Simit
 */
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;

public class ImageSteganography {

    
    public static void main(String[] args) {
        
        String frontfile = "image1.rgb";
        String hiddenfile = "image2.rgb";
   	
        int width = 352;
	int height = 288;
	
        byte frontImage[][][] = new byte [3][352][288];
        byte hiddenImage[][][] = new byte [3][352][288];
        byte stegedImage[][][] = new byte [3][352][288];
        byte DecfrontImage[][][] = new byte [3][352][288];
        byte DechiddenImage[][][] = new byte [3][352][288];
        
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        BufferedImage img1 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        BufferedImage img2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        try {
	    //--------------Read Front Image---------------------//
            File file = new File(frontfile);
	    InputStream is = new FileInputStream(file);

	    long len = file.length();
	    byte[] bytes = new byte[(int)len];
	    
	    int offset = 0;
            int numRead = 0;
            while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
                offset += numRead;
            }
    
    		
            int ind = 0;
		for(int y = 0; y < height; y++){
	
			for(int x = 0; x < width; x++){
                            
				byte r = bytes[ind];
                                frontImage[0][x][y] = r;
				byte g = bytes[ind+height*width];
                                frontImage[1][x][y] = g;
				byte b = bytes[ind+height*width*2];
                                frontImage[2][x][y] = b;
				
				ind++;
			}
		}
                
            //-----------------------Read Hidden Image------------------------//    
            file = new File(hiddenfile);
	    is = new FileInputStream(file);

	    len = file.length();
	    bytes = new byte[(int)len];
	    
	    offset = 0;
            numRead = 0;
            while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
                offset += numRead;
            }
    
    		
            ind = 0;
		for(int y = 0; y < height; y++){
	
			for(int x = 0; x < width; x++){
                            
				byte r = bytes[ind];
                                hiddenImage[0][x][y] = r;
				byte g = bytes[ind+height*width];
                                hiddenImage[1][x][y] = g;
				byte b = bytes[ind+height*width*2];
                                hiddenImage[2][x][y] = b;
				
				ind++;
			}
		}    
		
        } catch (FileNotFoundException e) {
        e.printStackTrace();
        } catch (IOException e) {
        e.printStackTrace();
        }
    
        steganographMyImage(frontImage, hiddenImage, stegedImage);
        
        getoriginalImage(stegedImage,DecfrontImage,DechiddenImage);
        
        for(int y=0; y<288;y++)
        {
            for(int x=0; x<352; x++)
            {
        
                    byte R1 = (byte)(stegedImage[0][x][y]);
                    byte G1 = (byte)(stegedImage[1][x][y]);
                    byte B1 = (byte)(stegedImage[2][x][y]);
           
                    int pixel = 0xff000000 | ((R1 & 0xff) << 16) | ((G1 & 0xff) << 8) | (B1 & 0xff);
                    
                    img.setRGB(x,y,pixel);
            }
        }
        for(int y=0; y<288;y++)
        {
            for(int x=0; x<352; x++)
            {
        
                    byte R1 = (byte)(DecfrontImage[0][x][y]);
                    byte G1 = (byte)(DecfrontImage[1][x][y]);
                    byte B1 = (byte)(DecfrontImage[2][x][y]);
           
                    int pixel = 0xff000000 | ((R1 & 0xff) << 16) | ((G1 & 0xff) << 8) | (B1 & 0xff);
                    
                    img1.setRGB(x,y,pixel);
            }
        }
        for(int y=0; y<288;y++)
        {
            for(int x=0; x<352; x++)
            {
        
                    byte R1 = (byte)(DechiddenImage[0][x][y]);
                    byte G1 = (byte)(DechiddenImage[1][x][y]);
                    byte B1 = (byte)(DechiddenImage[2][x][y]);
           
                    int pixel = 0xff000000 | ((R1 & 0xff) << 16) | ((G1 & 0xff) << 8) | (B1 & 0xff);
                    
                    img2.setRGB(x,y,pixel);
            }
        }
        
        // Use a label to display the image
        JFrame frame = new JFrame("Encoded Image");
        JLabel label = new JLabel(new ImageIcon(img));
        frame.getContentPane().add(label, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JFrame frame1 = new JFrame("Decoded Image--Front");
        JLabel label1 = new JLabel(new ImageIcon(img1));
        frame1.getContentPane().add(label1, BorderLayout.CENTER);
        frame1.pack();
        frame1.setVisible(true);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JFrame frame2 = new JFrame("Decoded Image--Hidden");
        JLabel label2 = new JLabel(new ImageIcon(img2));
        frame2.getContentPane().add(label2, BorderLayout.CENTER);
        frame2.pack();
        frame2.setVisible(true);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static void steganographMyImage(byte[][][] afrontImage, byte[][][] ahiddenImage, byte[][][] astegedImage)
    {
        for(int y=0; y<288; y++)
        {
            for(int x=0; x<352; x++)
            {
                afrontImage[0][x][y] =  (byte) (afrontImage[0][x][y] & 0xF0);
                afrontImage[1][x][y] =  (byte) (afrontImage[1][x][y] & 0xF0);
                afrontImage[2][x][y] =  (byte) (afrontImage[2][x][y] & 0xF0);
                
                ahiddenImage[0][x][y] =  (byte) ((ahiddenImage[0][x][y] & 0xF0)>>4);
                ahiddenImage[1][x][y] =  (byte) ((ahiddenImage[1][x][y] & 0xF0)>>4);
                ahiddenImage[2][x][y] =  (byte) ((ahiddenImage[2][x][y] & 0xF0)>>4);
                
                astegedImage[0][x][y] = (byte) (afrontImage[0][x][y]|ahiddenImage[0][x][y]);
                astegedImage[1][x][y] = (byte) (afrontImage[1][x][y]|ahiddenImage[1][x][y]);
                astegedImage[2][x][y] = (byte) (afrontImage[2][x][y]|ahiddenImage[2][x][y]);
            }
        }
    }
    
    public static void getoriginalImage(byte[][][] astegedImage, byte[][][] afrontImage, byte[][][] ahiddenImage)
    {
        for(int y=0; y<288; y++)
        {
            for(int x=0; x<352; x++)
            {
                afrontImage[0][x][y] =  (byte) (astegedImage[0][x][y] & 0xF0);
                afrontImage[1][x][y] =  (byte) (astegedImage[1][x][y] & 0xF0);
                afrontImage[2][x][y] =  (byte) (astegedImage[2][x][y] & 0xF0);
                
                ahiddenImage[0][x][y] =  (byte) ((astegedImage[0][x][y] & 0x0F)<<4);
                ahiddenImage[1][x][y] =  (byte) ((astegedImage[1][x][y] & 0x0F)<<4);
                ahiddenImage[2][x][y] =  (byte) ((astegedImage[2][x][y] & 0x0F)<<4);
            }
        }
    }
    
}
