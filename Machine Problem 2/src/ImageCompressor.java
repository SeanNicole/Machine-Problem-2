import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImageCompressor extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;

	private JLabel chooseButton, trainButton, compButton, saveButton;	
	
	private ImageIcon openIcon = new ImageIcon(getClass().getResource("openButton.png"));
	private ImageIcon openIcon2 = new ImageIcon(getClass().getResource("openButton1.png"));
	private ImageIcon trainIcon = new ImageIcon(getClass().getResource("train.png"));
	private ImageIcon compIcon = new ImageIcon(getClass().getResource("compIcon.png"));
	
	private JMenuBar menuBar;
	private JMenu fileMenu, compressMenu;
	private JMenuItem open, save, train, updateEH, createNH;	
	
	private ArrayList<Integer> tempArray = new ArrayList<Integer>();
	private ArrayList<Long> countArray = new ArrayList<Long>();		
			
	private static ImagePanel imagePanel;
	private static JScrollPane scroll;
	
	private Node root = null;	
	private PriorityQueue queue = new PriorityQueue();
	
	private String filename;
	private Tree tree = new Tree();
	
	public ImageCompressor(){
	
		super("Image Huffman Compressor");
		setLayout(null);		
		
		this.getContentPane().setBackground(Color.DARK_GRAY);		
		setSize(1100, 700);
		setLocationRelativeTo(null);		
		
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");						
		compressMenu = new JMenu("Compress image");
		
		menuBar.add(fileMenu);
		menuBar.add(compressMenu);
		
		open = new JMenuItem("Open image file", KeyEvent.VK_T);
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));		
		
		save = new JMenuItem("Save compressed file");
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
								
		train = new JMenu("Train Huffman Tree");		
		createNH = new JMenuItem("New Huffman tree");
		updateEH = new JMenuItem("Existing Huffman tree");
		
		compressMenu.add(train);
		train.add(createNH);
		train.add(updateEH);
		
		fileMenu.add(open);		
		fileMenu.add(save);			
		
		open.addActionListener(this);
		save.addActionListener(this);
		createNH.addActionListener(this);
		updateEH.addActionListener(this);
		
		setJMenuBar(menuBar);
		
	}	
	
	private void getPixelDist(){
		
		imagePanel.getPixelDist();	
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
		
		System.out.println("Inside saveHuffmanTree\nfilename: " + filename);
		
		ArrayList<Node> temp = new ArrayList<Node>();
		temp.add(null);		
		
		String FILENAME = filename + ".huff";
		String RGBcontent = "", RGBfreq = "";
		
		try{
			
			FileWriter fw = new FileWriter(new File(FILENAME));
			BufferedWriter bw = new BufferedWriter(fw);
			
			for(int i = 0; i < tempArray.size(); i++){
				
				RGBcontent = "" + tempArray.get(i);
				RGBfreq = "" + countArray.get(i);
				//hCode = tree.huffmanCode(root, "" + tempArray.get(i));
				
				bw.write(RGBfreq + RGBcontent + "|");				
				bw.flush();								
				
				Tree.code = "";				
			}
			bw.close();
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
		System.out.println("End save...");		
	}
	
	private void getHuffmanCodes(){
				
		
		String FILENAME = filename + ".szl";
		String content = "";
		
		try{
			
			FileWriter fw = new FileWriter(new File(FILENAME));
			BufferedWriter bw = new BufferedWriter(fw);							
			
			System.out.println("-----||width: " + imagePanel.getImage().getWidth());
			System.out.println("-----||height: " + imagePanel.getImage().getHeight());
			
			
			for(int x = 0; x < imagePanel.getImage().getWidth(); x++){				
				for(int y = 0; y < imagePanel.getImage().getHeight(); y++){
					
					//get huffman code string
					content += tree.huffmanCode(root, " " + imagePanel.getImage().getRGB(x, y) + " ");
					Tree.code = "";
					
				}
			}
			/*
			int mod = content.length()%7;
			if(mod != 0){
				String x = content.substring(content.length()-mod, content.length());

				for(int i = 0; i < 7-mod; i++){
					x = "0" + x;
				}							
								
				content = content.substring(0, content.length()-mod) + x;
			}			
			
			int cLength = content.length()/7;
			char[] charArray = new char[cLength];
			
			int beginIndex = 0;
			int endIndex = 7;

			//chunk by 7
			for(int i = 0; i < cLength; i++){
				
				charArray[i] = (char) Integer.parseInt(content.substring(beginIndex, endIndex), 2);
				
				bw.write(charArray[i]);				
				bw.flush();
				
				beginIndex = endIndex;
				endIndex += 7; 				
			}*/						

			bw.write(content);				
			bw.flush();
			bw.close();
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
		
	}		
	
	private String[] readHuffFile(String FILENAME){
		
		System.out.println("Reading HUFF file...");
		
		BufferedReader br = null;
		FileReader fr = null;
		String[] token = null;
		
		try{
			
			fr = new FileReader(FILENAME);
			br = new BufferedReader(fr);									
			
			String huffContent = br.readLine();
			System.out.println(huffContent);		
			
			token = huffContent.split("[|]"); 														
			
		}catch(IOException e){
			e.printStackTrace();
		}
				
		return token;
	}
	
	private void updateHuffmanTree(String[] token){
		
		System.out.println("Updating Huffman Tree");
		
		ArrayList<Integer> tempArray_Huff = new ArrayList<Integer>();
		ArrayList<Long> countArray_Huff = new ArrayList<Long>();
		
		for(int i = 0; i < token.length; i++){
			
			for(int j = 0; j < token[i].length(); j++){
								
				if(token[i].charAt(j) == '-'){										
					
					tempArray_Huff.add(Integer.parseInt(token[i].substring(j, token[i].length())));
					countArray_Huff.add(Long.parseLong(token[i].substring(0, j)));
					
				}				
			}
			
		}
		
		System.out.println("Getting pixel distribution...");
		getPixelDist();		
		
		
		System.out.println("Updating ArrayList...");
		for(int i = 0; i < tempArray_Huff.size(); i++){
						
			if(tempArray.contains(tempArray_Huff.get(i))){

				int index = tempArray.indexOf(tempArray_Huff.get(i));
				
				tempArray.set(index, tempArray_Huff.get(i));
				countArray.set(index, countArray_Huff.get(i));
				
			}else{			
				
				tempArray.add(tempArray_Huff.get(i));
				countArray.add(countArray_Huff.get(i));
				
			}
			
		}
		
		System.out.println("Inserting elements to PriorityQueue...");
		insertToPriorityQueue();		
		System.out.println("Creating Huffman tree...");
		makeHuffmanTree();
		System.out.println("Saving Huffman tree...");
		saveHuffmanTree();
		System.out.println("Huffman tree saved!");
		
	}
	
	private void renderImage(String FILENAME){
								
		
		int i ;
		for(i = FILENAME.length()-1; i >= 0; i--){
			if(FILENAME.charAt(i) == '\\'){
				break;
			}
		}
			
		filename = FILENAME.substring(i+1, FILENAME.length()-4);																						
		
		if(FILENAME.endsWith(".szl")){
			
			String content = "";
			String temp = "";
			int ascii;
			
			try{
				//get codes			
				FileReader fr = new FileReader(new File(FILENAME));
				BufferedReader br = new BufferedReader(fr);
					
				temp = br.readLine();
				//char[] array = br.readLine().toCharArray();								
				br.close();
				/*
				for(int j = 0; j < array.length; j++){
					
					ascii = (int) array[j];
					content += Integer.toBinaryString(ascii);					
				}*/								
				
			}catch(IOException e){
				e.printStackTrace();
			}
			
			System.out.println("temp: " + temp);
			
			//read huffman file
			String[] token = readHuffFile(FILENAME.substring(0, FILENAME.length()-4) + ".huff");					
			
			tempArray.removeAll(tempArray);
			countArray.removeAll(countArray);
			
			for(int i1 = 0; i1 < token.length; i1++){
				
				for(int j = 0; j < token[i1].length(); j++){
									
					if(token[i1].charAt(j) == '-'){										
						
						tempArray.add(Integer.parseInt(token[i1].substring(j, token[i1].length())));
						countArray.add(Long.parseLong(token[i1].substring(0, j)));
						
					}				
				}
				
			}

			//create huffman tree
			System.out.println("Inserting elements to queue...");
			insertToPriorityQueue();		
			System.out.println("Creating Huffman tree...");
			makeHuffmanTree();									

			ArrayList<Integer> compressedPixels = new ArrayList<Integer>();
			
			//traverse through tree until pixel found
			int index = 0;
			int ctr = 1;
			String bits;
			System.out.println("temp.length: " + temp.length());
			while(index < temp.length()){
				
				bits = temp.substring(index, index + ctr);
				String returnVal = tree.traverse(bits);
				
				if(returnVal != null){
					
					System.out.println("returnVal: " + returnVal);
					returnVal = returnVal.trim();
					compressedPixels.add(Integer.parseInt(returnVal));
					
					index = index + ctr;
					ctr = 0;
				}
				
				ctr++;								
			}
			
			//draw the image
			int w = 217, h = 35;			
			int type = BufferedImage.TYPE_INT_RGB;
			
			BufferedImage image = new BufferedImage(w, h, type);
			System.out.println("Arraylist size: " + compressedPixels.size());
			
			int ind = 0;
			for(int x = 0; x < w; x++){
				for(int y = 0; y < h; y++){
										
					image.setRGB(x, y, compressedPixels.get(ind));
					ind++;
				}
			}
			
			imagePanel = new ImagePanel(image);		
			imagePanel.setPreferredSize(new Dimension(imagePanel.getImage().getWidth(), imagePanel.getImage().getHeight()));
			
		}else{
			
			try{												
				imagePanel = new ImagePanel(ImageIO.read(new File(FILENAME)));		
				imagePanel.setPreferredSize(new Dimension(imagePanel.getImage().getWidth(), imagePanel.getImage().getHeight()));
				
			}catch(IOException ee){
				ee.printStackTrace();
			}
			
		}		
									
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
		revalidate();
		repaint();
		
	}
	
	public void actionPerformed(ActionEvent e){		
		Object source = e.getSource();
		
		if(source == open){
											
			JFileChooser fchooser = new JFileChooser();
			
			fchooser.setAcceptAllFileFilterUsed(false);			
			
			int returnVal = fchooser.showOpenDialog(null);
			
			if(returnVal == JFileChooser.APPROVE_OPTION){
				File file = fchooser.getSelectedFile();
				
				if(file.getName().endsWith(".png") || file.getName().endsWith(".szl") ){
					
					String FILENAME = "" + file;
					getContentPane().removeAll();
					renderImage(FILENAME);
					//getContentPane().add(comp)
					
				}else{
					JOptionPane.showMessageDialog(null, "Invalid file");
				}
				
			}			
		}
		
		if(source == updateEH){
						
			JFileChooser fchooser = new JFileChooser();
			fchooser.setAcceptAllFileFilterUsed(false);			
			
			int returnVal = fchooser.showOpenDialog(null);
			
			if(returnVal == JFileChooser.APPROVE_OPTION){
				File file = fchooser.getSelectedFile();
				
				if(file.getName().endsWith(".huff")){
					
					String FILENAME = "" + file;															
					String[] token = readHuffFile(FILENAME);
					updateHuffmanTree(token);
					
				}else{
					JOptionPane.showMessageDialog(null, "Invalid file");
				}
				
			}
										
			
		}
		
		if(source == createNH){
			
			getPixelDist();
			insertToPriorityQueue();
			makeHuffmanTree();
			saveHuffmanTree();			
			
		}
		
		if(source == save){
			getHuffmanCodes();
			System.out.println(".SZL created!");
		}
		
		
	}

}