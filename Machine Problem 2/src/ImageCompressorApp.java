import javax.swing.*;
import java.awt.*;

public class ImageCompressorApp{
	
	private static String filename = "image";
	
	public static void main(String[] args){
		
		ImageCompressor frame = new ImageCompressor();		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		//frame.setSize(300, 250);
		//frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
}