import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ImageCompressor extends JFrame{
		
	private BufferedImage img = null;
	private ImagePanel imagePanel;	
	
	public ImageCompressor(){
	
		try{
			
			imagePanel = new ImagePanel(ImageIO.read(new File("image.jpg")));
			//imagePanel.renderImage();
			//imagePanel.repaint();
			imagePanel.getRGB();
		
		}catch(IOException e){
			e.printStackTrace();
		}

		add(imagePanel);
	}
	
}