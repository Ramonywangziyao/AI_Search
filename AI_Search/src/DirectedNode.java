public class DirectedNode extends Node {
	private char direction;
	private int turns;
	DirectedNode lastNode;
	public DirectedNode(int x, int y, int c, DirectedNode parent, char dir, int ttime){
		super(x,y,c,parent);
		this.lastNode = parent;
		turns = ttime;
		direction = dir;
	}
	public DirectedNode(int x, int y){
		super(x,y,0,null);
		this.lastNode = null;
		direction = 'e';
		turns = 0;
	}
	public char getDirection() {
		return direction;
	}
	public void setDirection(char direction) {
		this.direction = direction;
	}
	public int getTurns() {
		return turns;
	}
	public void setTurns(int turns) {
		this.turns = turns;
	}
}
