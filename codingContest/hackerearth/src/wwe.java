import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Node
{
    int data;
    int heightFromParent=0;
    boolean isLeaf= false;
    String type ;
    Node left, right;
    Node parent;
    public Node(int item, String type)
    {
        data = item;
        left = right =parent= null;
        this.type= type;
    }
    
    // leaf could be junction or last person in queue at junction
    public boolean isNodeLeaf(){
    	if (left == null && right == null && !type.equalsIgnoreCase("marker")) return true;
    	else return false;
    }
    
    public void addChild(Node n){
    	if (this.left == null ){
    		this.left= n;
    	}
    	else if (this.right == null){
    		this.right = n;
    	}
    	else{
    		System.out.println("Not a binary tree");
    	}
    		
    }
    // type person, junction, sentinal
    public void setType(String type){
    	this.type= type;
    }
    public String getType(){
    	return this.type;
    }
}

class BinaryTree
{
    //Root of the Binary Tree
    Node root;
 
    public List<List<Node>> findAlloptimalPaths(Node root){
    	List<List<Node>>  result = new ArrayList<List<Node>>();
    	
    	Queue<Node> q= new LinkedList<Node>();
    	q.add(root);
    	int globalLevel = Integer.MAX_VALUE ; // maximum number of nodes in tree, set when found fist leaf node
    	int currentLevel=0;
    	Node marker = new Node(0,"marker");
    	q.add(marker);
    	while(! q.isEmpty() && currentLevel <= globalLevel){
    		Node tmp = q.poll();
    		if(tmp != null && tmp.type.equalsIgnoreCase("marker") ){
    			currentLevel= currentLevel +1;
    			// if queue has more element then addd  marker for next stage of dfs other wise end loop
    			if (q.peek() != null){
    				q.add(marker);
    			}
    			
    		}
    		else {
    			Node leftchld= tmp.left;
    			if (leftchld != null){
    				leftchld.parent = tmp;
    				leftchld.heightFromParent=currentLevel;
    				q.add(leftchld);
    				
    			}
    			
    			Node rightchld= tmp.right;
    			if (rightchld != null){
    				rightchld.parent = tmp;
    				rightchld.heightFromParent=currentLevel;
    				q.add(rightchld);
    				
    			}
    			
    			if ( tmp.isNodeLeaf()){
    				//find the leaf set the global level to current current level anmd
    				/// find other optiml path till same height
    				if (globalLevel>= currentLevel){
    					globalLevel= currentLevel;
    					List<Node> optimalPath= addPath(tmp);
    					result.add(optimalPath);
    				}
    				
    				
    			}
    			
    			
    		}
    		
    	}
    	return result;
    	
    }
    
    public void BFS(Node root){
    	Queue<Node> q= new LinkedList<Node>();
    	q.add(root);
    	Node marker = new Node(0,"marker");
    	q.add(marker);
    	while(! q.isEmpty() ){
    		Node tmp = q.poll();
    		if(tmp != null && tmp.type.equalsIgnoreCase("marker") ){
    			System.out.println("");
    			// if queue has more element then addd  marker for next stage of dfs other wise end loop
    			if (q.peek() != null){
    				q.add(marker);
    			}
    			
    		}
    		else {
    			System.out.print(tmp.type+":"+tmp.data+" ,");
    			Node leftchld= tmp.left;
    			if (leftchld != null){
    				
    				q.add(leftchld);
    				
    			}
    			
    			Node rightchld= tmp.right;
    			if (rightchld != null){
    				q.add(rightchld);
    				
    			}
    		}
    	}
    }
    
    public List<Node> addPath(Node n){
    	Node tmp = n;
    	List<Node> lst = new ArrayList<Node>();
    	while(tmp.parent != null){
    		//if (tmp.type.equalsIgnoreCase("junction")){
    			lst.add(tmp);
    		//}
    		tmp = tmp.parent;
    	}
    	lst.add(tmp);
    	return lst;
    }
    
    public void printPath(List<List<Node>> path){
    	for (List<Node> i : path){
    		boolean first= true;
    		for (Node j : i ){
    			if (j.type.equalsIgnoreCase("junction")){
    				if (first){
        				first= false;
        				System.out.print(j.data );
        				continue;
        			}
        			System.out.print(" -> " );
        			System.out.print(j.data );
    			}
    			
    		}
    		System.out.println();
    	}
    }
    
    public int sizeSubtree(Node n){
    	if (n== null ){
    		return 0;
    	}
    	else{
    		return sizeSubtree(n.left)+1+sizeSubtree(n.right);
    	}
    }
    
    public int countTime(List<Node> path){
    	boolean start= false;
    	int currentCount =0, currentSum=0;
    	Node prev=null, current=null;
    	for (Node j : path ){
    		current=j;
    		if (j.type.equalsIgnoreCase("person") && start){
    			currentCount++; 
    		}
    		if (j.type.equalsIgnoreCase("junction") && start ){
    			currentSum=currentSum +currentCount;
    			
    			Node otherChld=null;
    			if(current.left == prev){
    				//search in right subtree
    				otherChld= current.right;
    			}
    			
    			if(current.right == prev){
    				//search in right subtree
    				otherChld= current.left;
    			}
    			int tmpSum = sizeSubtree(otherChld);
    			if(tmpSum >= currentSum +1){
    				currentSum= currentSum +currentSum+1;
    			}
    			else{
    				currentSum= tmpSum +currentSum;
    			}
    			currentCount=0;
				
			}
			if (j.type.equalsIgnoreCase("junction") && !start ){
				start = true;
				
			}
			prev= current;
		}
    	return currentSum;
    }
    
}


public class wwe {
	/* Driver program to test above functions */
	
	
	public static HashMap <Integer,Node > hm = new HashMap<Integer, Node>(); 
	public static Node root;
	
    public static void main(String args[]) throws IOException
    {	 BinaryTree bt = new BinaryTree();
    	 bt.root = root;
    	 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
         String line = br.readLine();
         int junctions = Integer.parseInt(line);
         line = br.readLine();
         int queues = Integer.parseInt(line);
         int globalPersonCount= 100; // person numbering start from 100
         for (int j = 0; j < queues; j++) {
        	 String numbers = br.readLine();
        	 String[] tokens = numbers.split(" ");
      		int[] edge = new int[tokens.length];
      		for (int k =0 ; k< edge.length ; k++){
      			edge[k]= Integer.parseInt(tokens[k]);
      		}
      		if (wwe.hm.get(edge[0])== null){
      			Node tmp = new Node (edge[0],"junction");
      			hm.put(edge[0], tmp);
      		}
      		
      		if (wwe.hm.get(edge[1])== null){
      			Node tmp = new Node (edge[1],"junction");
      			hm.put(edge[1], tmp);
      		}
      		
      		Node parent = hm.get(edge[1]), current=parent;
      		for (int i = 0; i<edge[2]; i++){
      			globalPersonCount++;
      			current= new Node(globalPersonCount, "person");
      			parent.addChild(current);
      			current.parent=parent;
      			parent= current;
      		}
      		
      		current.addChild(wwe.hm.get(edge[0]));
      		wwe.hm.get(edge[0]).parent=current;
      		root = hm.get(edge[1]);
      		//bt.BFS(root);
         }
         
         while(root.parent != null){
        	 root = root.parent;
         }
             //reading first test case condition
         
         
         List <List<Node>> paths= bt.findAlloptimalPaths(root);
         System.out.println(bt.countTime(paths.get(0)));
         bt.printPath(paths);
         
 		
    }
	
}