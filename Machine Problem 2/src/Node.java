public class Node{
	
	public String value;
	public long frequency;
	public Node left, right, next;
	
	public Node(String value, long frequency){
		
		this.value = value;
		this.frequency = frequency;
		left = null; right = null; next = null;
	}
	
	
}