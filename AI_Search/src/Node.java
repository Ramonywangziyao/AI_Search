
public class Node implements Comparable<Node> {
	private int nodeX;
	private int nodeY;
	Node lastNode;
	private int heu;
	private int cost;
	public Node(int xX,int yY)
	{
		nodeX = xX;
		nodeY = yY;
		
	}
	public Node(int x, int y, int c, Node parent){
		nodeX = x;
		nodeY = y;
		cost = c;
		lastNode = parent;
	}
	
	public int getCost(){
		return cost;
	}
	
	public int getX()
	{
		return nodeX;
	}
	
	public int getY()
	{
		return nodeY;
	}
	public int getH()
	{
		return this.heu;
	}
	public void setH(int nH)
	{
		this.heu = nH;
	}
	public void setX(int x)
	{
		this.nodeX = x;
	}
	
	public void setY(int y)
	{
		this.nodeY = y;
	}
	
	 public int compareTo(Node arg0) 
     {
         if(this.heu < arg0.heu)
         {
             return -1;
         }
         else if(this.heu> arg0.heu)
         {
             return 1;
         }

         return 0;
     }

}
