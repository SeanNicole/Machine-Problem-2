public class Tree{
	
	private static Node node;
	public static Node newRoot;
	public static String code = "";

	public Tree(){
	}
	
	public Node makeTree(PriorityQueue queue)
	{
		
		if(!queue.isEmpty())			
		{
			Node nodeLeft = queue.remove();
			Node nodeRight = queue.remove();
			
			if(nodeRight != null){	
				
				node = new Node(nodeLeft.value + nodeRight.value, nodeLeft.frequency + nodeRight.frequency);
				node.left = nodeLeft;
				node.right = nodeRight;
				queue.insert(node);						
				
				makeTree(queue);	
				
			}else{
				newRoot = nodeLeft;		// element in queue is now a root of the tree					
			}
		}		
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
				
				if(pixel.equals("-3")){
					System.out.println("current.left.value: " + current.left.value + " = pixel: " + pixel);
				}
				
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
	
} 