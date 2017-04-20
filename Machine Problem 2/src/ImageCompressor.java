import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ImageCompressor extends JFrame{
		
	//private BufferedImage image;
	private ArrayList<Integer> tempArray = new ArrayList<Integer>();
	private ArrayList<Long> countArray = new ArrayList<Long>();		
	
	private ImagePanel imagePanel;	
	
	private Node root = null;	
	private PriorityQueue queue = new PriorityQueue();
	
	private String filename;
	private Tree tree = new Tree();
	
	public ImageCompressor(String filename){
	
		this.filename = filename;
		
		renderImage(filename);
		getPixelDist();
		insertToPriorityQueue();
		makeHuffmanTree();
		getHuffmanCodes();

		add(imagePanel);
	}
	
	private void getPixelDist(){
		
		imagePanel.getPixelDist();	// gets each pixel information and stores them in a text file
		tempArray = imagePanel.getPixels();
		countArray = imagePanel.getPixelsCount();
	}
	
	private void insertToPriorityQueue(){
		
		for(int i = 0 ; i < tempArray.size(); i++){
			
			Node node = new Node("" + tempArray.get(i), countArray.get(i));
			queue.insert(node);
			
		}
		
	}
	
	private void makeHuffmanTree(){		
		root = tree.makeTree(queue);	
		// aren't we suppose to save this tree as .huff file?
	}
	
	// saves a copy of each huffman code of the pixels as a .huff file
	private void getHuffmanCodes(){
		
		String FILENAME = "filename.huff";
		String content;
		
		try{
			
			FileWriter fw = new FileWriter(new File(FILENAME));
			BufferedWriter bw = new BufferedWriter(fw);							
			
			for(int i = 0; i < tempArray.size(); i++){
				
				content = tree.huffmanCode(root, "" + tempArray.get(i));
				
				bw.write(tempArray.get(i) + " = " + content);
				bw.newLine();
				bw.flush();
				
			}
			
			bw.close();
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
		
	}
		
	public void renderImage(String filename){	
		
		try{
			
			imagePanel = new ImagePanel(ImageIO.read(new File(filename)));			
		
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
}