public class Tree{
	
	private static Node node;
	public static Node newRoot;
	public static String code = "";
	
	public Node makeTree(PriorityQueue queue)
	{
		
		while(queue.size() > 1)			
		{
			Node nodeLeft = queue.remove();
			Node nodeRight = queue.remove();
			
				
			node = new Node(nodeLeft.value + nodeRight.value, nodeLeft.frequency + nodeRight.frequency);
			node.left = nodeLeft;
			node.right = nodeRight;
			queue.insert(node);						
						
		}		
		
		newRoot = queue.remove();		
		return newRoot;		
	}
	
	public void printBinaryTree(Node root, int level){
				
	    if(root == null) return;
	    	    
	    printBinaryTree(root.right, level+1);
	    
	    if(level!=0){
	        for(int i = 0; i < level-1; i++)
	            System.out.print("|\t");
	        	
	        	if(root.left == null && root.right == null)
	        		System.out.println("|------[" + root.value + ":" + root.frequency + "]");
	        	else
	        		System.out.println("|------[" + root.frequency + "]");
	    }
	    else
	        System.out.println("["+root.frequency + "]");
	    
	    printBinaryTree(root.left, level+1);
	}
	
	
	//recursive method
	public String huffmanCode(Node currentNode, String pixel)
	{		
		Node current = currentNode;				

		if(current.left != null || current.right != null){
						
			
			if(current.left.value.equals(pixel)){
				
				code += "0";				
				return code;
			}
			else if(nodeValContains(current.left.value, pixel)){
				code += "0";
				current = current.left;				
			}
			
			else if(current.right.value.equals(pixel)){
				code += "1";
				return code;
			}
			else if(nodeValContains(current.right.value, pixel)){
				code += "1";
				current = current.right;
			}else{				
				return null;
			}
						
			huffmanCode(current, pixel);
		}
		
		return code;
	}
	
	private boolean nodeValContains(String string1, String string2){
	
		if(string1.contains(string2))
			return true;
		
		return false;		 
	}
	
	public String traverse(String bits){
		
		Node current = newRoot;						
		char[] character = bits.toCharArray();
		
		System.out.println("bits.length: " + bits.length());
		for(int i = 0; i < bits.length(); i++){
			
			System.out.println("i: " + i);
			
			if(character[i] == '0'){				
				//traverse left
				current = current.left;
				
			}
			else if(character[i] == '1'){				
				//traverse right
				current = current.right;				
			}		
			
		}	
		
		if(current.right == null || current.left == null)		
			return current.value;				
		
		return null;
	}
	
} 