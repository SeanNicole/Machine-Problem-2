public class PriorityQueue{
	
	public static Node first = null;	
	public static Node last = null;
	private int ctr = 0;
	
	public boolean isEmpty(){
		return (first == null);
	}
	
	public int size(){
		return ctr;
	}
	
	public void insert(Node node){								
		Node current = first;
		Node previous = null;
		
		if(isEmpty()){
			
			last = node;
			node.next = first;
			first = node;
			
		}else{			
			
			while(current != null){
				
				if(node.frequency <= current.frequency){
						break;
				}
									
				previous = current;
				current = current.next;
			}
			
			// node value is the smallest and is 
			// inserted at the [rear?]
			if(previous == null)	 
			{
				node.next = first;
				first = node;
				
			}else{
				
				previous.next = node;
				node.next = current;							
			}

		}
		
		ctr++;
	}
	
	public Node remove(){				
		
		if(!isEmpty()){
			
			Node temp = first;
			first = first.next;		
						
			return temp;
		}
		
		return null;
	}
	
}