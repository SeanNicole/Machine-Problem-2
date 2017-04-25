import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
		String RGBcontent = "";
		String hCode = "";
		
		try{
			
			FileWriter fw = new FileWriter(new File(FILENAME));
			BufferedWriter bw = new BufferedWriter(fw);
			
			for(int i = 0; i < tempArray.size(); i++){
				
				RGBcontent = "" + tempArray.get(i);
				hCode = tree.huffmanCode(root, "" + tempArray.get(i));
				
				bw.write(RGBcontent + "|" + hCode);				
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
		String content;
		
		try{
			
			FileWriter fw = new FileWriter(new File(FILENAME));
			BufferedWriter bw = new BufferedWriter(fw);							
			
			for(int i = 0; i < tempArray.size(); i++){
				
				content = tree.huffmanCode(root, " " + tempArray.get(i) + " ");
				
				bw.write(content);				
				bw.flush();
				
				Tree.code = "";
			}
			
			bw.close();
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
		
	}		
	
	private void readHuffFile(String FILENAME){
		
		BufferedReader br = null;
		FileReader fr = null;
		
		
		try{
			
			fr = new FileReader(FILENAME);
			br = new BufferedReader(fr);									
			
			String sCurrentLine = br.readLine();
			System.out.println(sCurrentLine);			
		
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	public void actionPerformed(ActionEvent e){		
		Object source = e.getSource();
		
		if(source == open){
					
			FileNameExtensionFilter filter = new FileNameExtensionFilter("jpg", "png", "szl");
			JFileChooser fchooser = new JFileChooser();
			fchooser.setFileFilter(filter);
			
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
					

				filename = file.substring(i+1, file.length()-4);																						
				
				try{												
					imagePanel = new ImagePanel(ImageIO.read(new File(file)));		
					imagePanel.setPreferredSize(new Dimension(imagePanel.getImage().getWidth(), imagePanel.getImage().getHeight()));
					
				}catch(IOException ee){
					ee.printStackTrace();
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
		}
		
		if(source == updateEH){
						
			JFileChooser fchooser = new JFileChooser();
			fchooser.setAcceptAllFileFilterUsed(false);			
			
			int returnVal = fchooser.showOpenDialog(null);
			
			if(returnVal == JFileChooser.APPROVE_OPTION){
				File file = fchooser.getSelectedFile();
				
				if(file.getName().endsWith(".huff")){
					
					String FILENAME = "" + file;															
					readHuffFile(FILENAME);
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
		}
		
		
	}

}