import javax.swing.*;
import java.awt.*;

public class ImageCompressorApp{
	
	private static String filename = "image.jpg";
	
	public static void main(String[] args){
		
		ImageCompressor ic = new ImageCompressor(filename);
		ic.setSize(480, 480);
		ic.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ic.setLocationRelativeTo(null);
		ic.setVisible(true);
	}
	
}