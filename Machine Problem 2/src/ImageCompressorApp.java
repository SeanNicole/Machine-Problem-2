import javax.swing.*;
import java.awt.*;

public class ImageCompressorApp{
	
	public static void main(String[] args){
		
		ImageCompressor ic = new ImageCompressor();
		ic.setSize(480, 480);
		ic.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ic.setLocationRelativeTo(null);
		ic.setVisible(true);
	}
	
}