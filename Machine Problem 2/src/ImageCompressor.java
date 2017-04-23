import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ImageCompressor extends JFrame{
		
	private JLabel chooseButton, trainButton, compButton, saveButton;	
	
	private ImageIcon openIcon = new ImageIcon(getClass().getResource("openButton.png"));
	private ImageIcon openIcon2 = new ImageIcon(getClass().getResource("openButton1.png"));
	private ImageIcon trainIcon = new ImageIcon(getClass().getResource("train.png"));
	
	private ArrayList<Integer> tempArray = new ArrayList<Integer>();
	private ArrayList<Long> countArray = new ArrayList<Long>();		
			
	private ImagePanel imagePanel;
	private JScrollPane scroll;
	
	private Node root = null;	
	private PriorityQueue queue = new PriorityQueue();
	
	private String filename;
	private Tree tree = new Tree();
	
	public ImageCompressor(){
	
		super("Image Huffman Compressor");
		setLayout(null);		
		this.getContentPane().setBackground(Color.DARK_GRAY);		
		setSize(650, 400);
		setLocationRelativeTo(null);
					
		chooseButton = new JLabel(openIcon);
		chooseButton.setBounds(170, 150, openIcon.getIconWidth(), openIcon.getIconHeight());		
		
		trainButton = new JLabel(trainIcon);		
				
		chooseButton.addMouseListener(new Handler());				
		trainButton.addMouseListener(new Handler());
							
		add(chooseButton);
		
		
	}
	
	public class Handler extends MouseAdapter{
		public void mouseClicked(MouseEvent e){			
			Object source = e.getSource();
			
			if(source == chooseButton){
				getContentPane().removeAll();						
				
				JFileChooser fchooser = new JFileChooser();
				fchooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
					
				fchooser.showOpenDialog(null);
								
				File f = fchooser.getSelectedFile();
				String file = "" + f;						
					
				if(f != null){
					int i ;
					for(i = file.length()-1; i >= 0; i--){
						if(file.charAt(i) == '\\'){
							break;
						}
					}
						
					filename = file.substring(i+1, file.length()-3);																						
					
					try{												
						imagePanel = new ImagePanel(ImageIO.read(new File(file)));		
						imagePanel.setPreferredSize(new Dimension(imagePanel.getImage().getWidth(), imagePanel.getImage().getHeight()));
						
					}catch(IOException ee){
						ee.printStackTrace();
					}

					setBounds(150, 45, 1100, 700);
					
					chooseButton.setIcon(openIcon2);
					chooseButton.setBounds(700, 350, openIcon.getIconWidth(), openIcon.getIconHeight());
					trainButton.setBounds(745, 400, trainIcon.getIconWidth(), trainIcon.getIconHeight());				
								
					scroll = new JScrollPane(imagePanel);				
					scroll.setPreferredSize(new Dimension(imagePanel.getImage().getWidth(), imagePanel.getImage().getHeight()));
					scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
					scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
					
					
					if(imagePanel.getImage().getWidth() < 700 && imagePanel.getImage().getHeight() < 600){					
						scroll.setBounds(50, 50, imagePanel.getImage().getWidth()+10, imagePanel.getImage().getHeight()+10);
					
					}else if(imagePanel.getImage().getWidth() >= 700  && imagePanel.getImage().getHeight() < 600){					
						scroll.setBounds(50, 50, 700, imagePanel.getImage().getHeight()+10);
						
					}else if(imagePanel.getImage().getHeight() >= 600  && imagePanel.getImage().getWidth() < 700){					
						scroll.setBounds(50, 50, imagePanel.getImage().getWidth()+10, 600);
						
					}else{
						scroll.setBounds(50, 50, 700, 600);
					}				
					
					getContentPane().add(scroll);
					getContentPane().add(chooseButton);
					getContentPane().add(trainButton);
					
					revalidate();
					repaint();			

				}
			
			}
			
			if(source == trainButton){
				
				getPixelDist();
				insertToPriorityQueue();
				makeHuffmanTree();
				saveHuffmanTree();
			}
			
			if(source == compButton){
								
				
			}
			
			if(source == saveButton){
				
			}
			
		}
		
	}
	
	private void getPixelDist(){
		
		imagePanel.getPixelDist();	// gets each pixel information and stores them in a text file
		tempArray = imagePanel.getPixels();
		countArray = imagePanel.getPixelsCount();
	}
	
	private void insertToPriorityQueue(){
		
		for(int i = 0 ; i < tempArray.size(); i++){
			
			Node node = new Node(" " + tempArray.get(i) + " ", countArray.get(i));
			queue.insert(node);
			
		}
		
	}
	
	private void makeHuffmanTree(){		
		root = tree.makeTree(queue);			
	}

	private void saveHuffmanTree(){
		
		ArrayList<Node> temp = new ArrayList<Node>();
		temp.add(null);		
		
		String FILENAME = filename + ".huff";
		//String node;
		
		Node current = root;
		Node previous;
		
		boolean left = false, right = true;
		
		for(int i = 1; i < temp.size()-1; i++){				
								
			if(current != null){
			
				temp.add(current);		
				temp.add(current.left);
				temp.add(current.right);			
				
			}
			
			current = temp.get(i+1);
		}

		
		try{
			
			FileWriter fw = new FileWriter(new File(FILENAME));
			BufferedWriter bw = new BufferedWriter(fw);							
			
			bw.write();
			bw.newLine();
			bw.flush();
							
			
			
			bw.close();
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	private void getHuffmanCodes(){
		
		System.out.println("filename: " + filename);
		
		String FILENAME = filename + ".huff";
		String content;
		
		try{
			
			FileWriter fw = new FileWriter(new File(FILENAME));
			BufferedWriter bw = new BufferedWriter(fw);							
			
			for(int i = 0; i < tempArray.size(); i++){
				
				content = tree.huffmanCode(root, " " + tempArray.get(i) + " ");
				
				bw.write(tempArray.get(i) + " = " + countArray.get(i) + " = " + content);
				bw.newLine();
				bw.flush();
				
				Tree.code = "";
			}
			
			bw.close();
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
		
	}
		
	public void renderImage(String filename){	
		
		
		
	}
}