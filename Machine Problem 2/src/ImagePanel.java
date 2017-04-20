import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{
	
	private BufferedImage image;
	private ArrayList<Integer> tempArray = new ArrayList<Integer>();
	private ArrayList<Long> countArray = new ArrayList<Long>();	
	
	public ImagePanel(BufferedImage image){
		this.image = image;		
	}
	
	public BufferedImage getImage(){
		return image;
	}
	
	public void paint(Graphics g){
		g.drawImage(image, 100, 100, this);
	}
	
	// obtains image's pixels information, stores them without repetition 
	// and writes their distribution in a .txt file
	public void getPixelDist(){
		
		if(image == null)
			return;
				
		int content;
			
		for(int i = 0; i < image.getHeight(); i++){
			for(int j = 0; j < image.getWidth(); j++){
					
				content = image.getRGB(j, i);										
					
				if(!tempArray.contains(content)){
						
					tempArray.add(content);
					countArray.add((long) 1);
						
				}else{
						
					int index = tempArray.indexOf(content);
					countArray.set(index, countArray.get(index)+1 );
						
				}						
			}
		}
		
	}
	
	public ArrayList<Integer> getPixels(){
		return tempArray;		
	}
	
	public ArrayList<Long> getPixelsCount(){
		return countArray;
	}
}