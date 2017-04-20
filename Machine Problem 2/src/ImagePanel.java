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
	
	public void renderImage(){
		
		if(image == null)
			return;
		
		try{
			
			Graphics g = image.getGraphics();
			g.setColor(Color.DARK_GRAY);
			g.drawString("Kenasou.", 50 , 50);			
			g.drawImage(ImageIO.read(new File("image.jpg")), 100, 100, null);
			
		}catch(IOException e){		
			e.printStackTrace();
		} 
		
	}
	
	public void getRGB(){
		
		if(image == null)
			return;
		
		String FILENAME = "RGBs.txt";		
		int content;
		
		
		try{
			
			FileWriter fw = new FileWriter(FILENAME);
			BufferedWriter bw = new BufferedWriter(fw);
			
			
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
					
					bw.write(content);
					bw.newLine();
					bw.flush();					
					
				}
			}
			
			bw.close();
			
			
			String fn = "kena.txt";
			FileWriter fw1 = new FileWriter(fn);
			BufferedWriter bw1 = new BufferedWriter(fw1);
		
			
			for(int i = 0; i < tempArray.size(); i++){
				
				bw1.write(tempArray.get(i) + " - " + countArray.get(i));				
				bw1.newLine();
				bw1.flush();
			}
			
			bw1.close();
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
		
	}
}