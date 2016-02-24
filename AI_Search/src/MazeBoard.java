import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Timer;

import javax.swing.JComponent;
import javax.swing.JPanel;


public class MazeBoard extends JComponent implements ActionListener{
	private Timer timer;
	private MazeMap m;
	
	int rowCount;
	int colCount;
	int goalX;
	int goalY;
	int xG,yG;
	int xS,yS;
	int [] sL;
	int [] gL;
	char [][] theMaze;
	Node sN;
	Node fN;
	public MazeBoard() throws IOException, InterruptedException
	{
	
		m = new MazeMap();
		theMaze = m.getMap();
		rowCount = m.cols;
		colCount = m.row;
		
		for(int k =0;k<rowCount;k++)
		{
			for(int j = 0;j<colCount;j++)
			{
				System.out.print(theMaze[k][j]);
			}
			System.out.println();
		}
		//System.out.println("here "+colCount+"   "+rowCount);
		gL = searchForGoal(theMaze,colCount,rowCount);
		sL = searchForPacman(theMaze,colCount,rowCount);
		//System.out.println(sL[0]+"  "+sL[1]);
		xS = sL[0];
		yS = sL[1];
		xG = gL[0];
		yG = gL[1];
		rowCount = m.cols;
		colCount = m.row;
		sN = new Node(xS,yS);
		fN = new Node(xG,yG);
		//int result = aStarwGhost(theMaze,sN,fN);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		repaint();
	}
	//search start node
	public static int[] searchForPacman(char[][]mazeContext, int width, int height)
	{
	//	System.out.println(width+"     "+height);
		for(int i = 0; i<height;i++)
		{
			for(int j = 0; j<width;j++)
			{
				if(mazeContext[i][j] == 'P')
				{
					int[] pacmanPosition = new int[2];
					pacmanPosition[0] = i;
					pacmanPosition[1] = j;
			//	System.out.println("!!!");
					return pacmanPosition;
				}
			}
		}
	//	System.out.println("???");
		return null;
	}
	
	
	//*******DFS
	public int depthFirst(ArrayList<Node> solution, char[][] mazeContext,int x,int y,boolean [][] walkedTrace,int width, int height,int steps,int nodesE,Node endNode) throws InterruptedException
	{
		repaint();
		Thread.sleep(30);
		//base case
		//goal found
	if(mazeContext[x][y] == '.'&&x==endNode.getX()&&y==endNode.getY())
	{
		int leastNodeExpanded= backTrace(solution.get(solution.size()-1),mazeContext);
		System.out.println("Total steps: "+leastNodeExpanded+"    Total Node Expanded: "+nodesE);
		repaint();
		return 1;
	}
	else
	{
		//west wall check
		if(walkedTrace[x][y-1]==false&&(mazeContext[x][y-1]=='.'||mazeContext[x][y-1]==' ')&&x>0&&y-1>0&&x<height-1&&y-1<width-1) //east
		{
				nodesE++;
				Node newNode = new Node(x,y);
				if(!solution.isEmpty())
				{
				newNode.lastNode = solution.get(solution.size()-1);
				}
				solution.add(newNode);
				walkedTrace[x][y] = true;
				mazeContext[x][y] = '.';
				if(depthFirst(solution,mazeContext,x,y-1,walkedTrace,width,height,steps,nodesE,endNode)==1)
				{
					repaint();
					return 1;	
				}
				solution.remove(solution.size()-1); //unpaint
				mazeContext[x][y] = ' ';
		}
		//north wall check
			if(walkedTrace[x-1][y]==false&&(mazeContext[x-1][y]=='.'||mazeContext[x-1][y]==' ')&&x-1>0&&y>0&&x-1<height-1&&y<width-1) //east
			{
					nodesE++;
					Node newNode = new Node(x,y);
					if(!solution.isEmpty())
					{
					newNode.lastNode = solution.get(solution.size()-1);
					}
					solution.add(newNode);
					walkedTrace[x][y] = true;
					mazeContext[x][y] = '.';
					if(depthFirst(solution,mazeContext,x-1,y,walkedTrace,width,height,steps,nodesE,endNode)==1)
					{
						repaint();
						return 1;	
					}
					solution.remove(solution.size()-1); //unpaint
					mazeContext[x][y] = ' ';
					
			}
			//east wall check
			if(walkedTrace[x][y+1]==false&&(mazeContext[x][y+1]=='.'||mazeContext[x][y+1]==' ')&&x>0&&y+1>0&&x<height-1&&y+1<width-1) //east
			{
					nodesE++;
					Node newNode = new Node(x,y);
					if(!solution.isEmpty())
					{
					newNode.lastNode = solution.get(solution.size()-1);
					}
					solution.add(newNode);
					walkedTrace[x][y] = true;
					mazeContext[x][y] = '.';
					if(depthFirst(solution,mazeContext,x,y+1,walkedTrace,width,height,steps,nodesE,endNode)==1)
					{
						repaint();
						return 1;	
					}else
						
					solution.remove(solution.size()-1); //unpaint
					mazeContext[x][y] = ' ';
					
			}
			//south wall check
			if(walkedTrace[x+1][y]==false&&(mazeContext[x+1][y]=='.'||mazeContext[x+1][y]==' ')&&x+1>0&&y>0&&x+1<height-1&&y<width-1) //east
			{
					Node newNode = new Node(x,y);
					nodesE++;
					if(!solution.isEmpty())
					{
					newNode.lastNode = solution.get(solution.size()-1);
					}
					solution.add(newNode);
					walkedTrace[x][y] = true;
					mazeContext[x][y] = '.';
					if(depthFirst(solution,mazeContext,x+1,y,walkedTrace,width,height,steps,nodesE,endNode)==1)
					{
						repaint();
						return 1;	
					}
					solution.remove(solution.size()-1); //unpaint
					mazeContext[x][y] = ' ';
					
			}

	}

	return 0;
}
	
	
	
	
	
	//******BFS
	public int breadth(char[][]mazeContext, Node newNode, int Height, int Width) throws InterruptedException
	{
		//baseCase
		int numberOfSteps = 0;
		int nodeExpanded = 0;
		Queue<Node> mazeLocaQue = new LinkedList<Node>();
		List<String> isChecked = new ArrayList<String>();
		mazeLocaQue.add(newNode);
		isChecked.add(newNode.getX()+"/"+newNode.getY());
		
		while(!mazeLocaQue.isEmpty())
		{
			repaint();
			
			Node currentNode = mazeLocaQue.poll();
			mazeContext[currentNode.getX()][currentNode.getY()] = '.';
			nodeExpanded++;
			int currentX = currentNode.getX();
			int currentY = currentNode.getY();	
			int newY = currentY+1;
			//check the boundary and whether the node has been walked through
			//there are four directions: north, south, east and west. four if statement to check four direction for every node. if not walked before
			//expande it.
			if(isChecked.indexOf(currentX+"/"+newY)<0&&currentX>0 && currentY+1>0 && currentX<Width-1 && currentY+1<Height-1)
			{
				Node theNewNode = new Node(currentX,currentY+1);			
				numberOfSteps++;
				if(mazeContext[currentX][currentY+1] == ' '||mazeContext[currentX][currentY+1] == '.')
				{
					nodeExpanded++;
					theNewNode.lastNode = currentNode;
					//goal found
					if(mazeContext[currentX][currentY+1] == '.')
					{
						int leastNodeExpanded= backTrace(theNewNode,mazeContext);
						clearScreen();
						System.out.println("Total steps: "+leastNodeExpanded+"    Total Node Expanded: "+nodeExpanded);
						repaint();
						return 1;
					}
					mazeLocaQue.add(theNewNode);
					isChecked.add(theNewNode.getX()+"/"+theNewNode.getY());
					mazeContext[theNewNode.getX()][theNewNode.getY()] = 'F';	
				}
				
			}
			int newX = currentX-1;
			if(isChecked.indexOf(newX+"/"+currentY)<0&&currentX-1>0 && currentY>0 && currentX-1<Width-1 && currentY<Height-1)
			{
				Node theNewNode = new Node(currentX-1,currentY);
				theNewNode.lastNode = currentNode;
				if( mazeContext[currentX-1][currentY] == ' ' || mazeContext[currentX-1][currentY]== '.')
				{
					nodeExpanded++;
					if(mazeContext[currentX-1][currentY] == '.')
					{
						numberOfSteps++;
						int leastNodeExpanded= backTrace(theNewNode,mazeContext);
						System.out.println("Total steps: "+leastNodeExpanded+"    Total Node Expanded: "+nodeExpanded);
						repaint();
						return 1;
					}
					mazeLocaQue.add(theNewNode);
					isChecked.add(theNewNode.getX()+"/"+theNewNode.getY());
					mazeContext[theNewNode.getX()][theNewNode.getY()] = 'F';
				}
			}
			newY = currentY-1;
			if(isChecked.indexOf(currentX+"/"+newY)<0&&currentX>0 && currentY-1>0 && currentX<Width-1 && currentY-1<Height-1)
			{
				Node theNewNode = new Node(currentX,currentY-1);
				if(isChecked.indexOf(theNewNode.getX()+"/"+theNewNode.getY())<0 && mazeContext[currentX][currentY-1] == ' '||mazeContext[currentX][currentY-1]== '.')
				{
					nodeExpanded++;
					theNewNode.lastNode = currentNode;
					if(mazeContext[currentX][currentY-1] == '.')
					{
						numberOfSteps++;
						int leastNodeExpanded= backTrace(theNewNode,mazeContext);
						System.out.println("Total steps: "+leastNodeExpanded+"    Total Node Expanded: "+nodeExpanded);
						repaint();
						return 1;
					}
					mazeLocaQue.add(theNewNode);
					isChecked.add(theNewNode.getX()+"/"+theNewNode.getY());
					mazeContext[theNewNode.getX()][theNewNode.getY()] = 'F';
				}
			}
			newX = currentX+1;
			if(isChecked.indexOf(newX+"/"+currentY)<0&&currentX+1>0 && currentY>0 && currentX+1<Width-1 && currentY<Height-1)
			{
				Node theNewNode = new Node(currentX+1,currentY);
				if(mazeContext[currentX+1][currentY] == ' '||mazeContext[currentX+1][currentY]== '.')
				{
					nodeExpanded++;
					theNewNode.lastNode = currentNode;
					if(mazeContext[currentX+1][currentY] == '.')
					{
						numberOfSteps++;
						int leastNodeExpanded= backTrace(theNewNode,mazeContext);
						System.out.println("Total steps: "+leastNodeExpanded+"    Total Node Expanded: "+nodeExpanded);
						repaint();
						return 1;
					}
					mazeLocaQue.add(theNewNode);
					isChecked.add(theNewNode.getX()+"/"+theNewNode.getY());
					mazeContext[theNewNode.getX()][theNewNode.getY()] = 'F';
				}
			}
			//printing the map
			Thread.sleep(30);
		}
		return 0;
	}
	
	
	
	
	//*******Greedy Algorithm
	public int greedyBest(char[][]mazeContext, Node newNode,Node endNode, int Width, int Height) throws InterruptedException
	{
		int numberOfSteps = 0;
		int nodeExpanded = 0;
		PriorityQueue<Node> mazeLocaQue = new PriorityQueue<Node>();
		List<String> isChecked = new ArrayList<String>();
		newNode.setH(calcuManhattonDistance(endNode.getX(),newNode.getX(),endNode.getY(),newNode.getY()));
		mazeLocaQue.add(newNode);
		int [] xM = {0,0,-1,1};
		int [] yM = {-1,1,0,0};
		numberOfSteps++;
		while(!mazeLocaQue.isEmpty())
		{	
			repaint();
			clearScreen();
			Node currentNode = mazeLocaQue.poll();
			mazeContext[currentNode.getX()][currentNode.getY()] = '.';
			repaint();
			isChecked.add(currentNode.getX()+"/"+currentNode.getY());
			numberOfSteps++;
			//found goal
			if(mazeContext[currentNode.getX()][currentNode.getY()] == '.'&&isChecked.indexOf(currentNode.getX()+"/"+currentNode.getY())<0)
			{
				numberOfSteps++;
				int leastNodeExpanded = backTrace(currentNode,mazeContext);
				System.out.println("Total steps: "+leastNodeExpanded+"    Total Node Expanded: "+nodeExpanded);
				repaint();
				return 1;
			}else
			{
				numberOfSteps++;
			//starting counter
			//applicable actions
				clearScreen();
				//four directions
				for(int i = 0;i<4;i++ )//not finished.  > starting counter
				{
					numberOfSteps++;
					int newX = currentNode.getX()+xM[i];
					int newY = currentNode.getY()+yM[i];
					//goal found
					if(mazeContext[newX][newY] == '.'&&isChecked.indexOf(currentNode.getX()+"/"+currentNode.getY())<0)
					{
						numberOfSteps++;
						int leastNodeExpanded = backTrace(currentNode,mazeContext);
						Thread.sleep(80);
						System.out.println("Total steps: "+leastNodeExpanded+"    Total Node Expanded: "+nodeExpanded);
						repaint();
						return 1;
					}
					if(mazeContext[newX][newY] == ' ' ||mazeContext[newX][newY] == '.' && newX>0 && newY>0 && newX<Width-1 && newY<Height-1)
					{				
						Node expandedNode = new Node(newX,newY);
						nodeExpanded++;
						//goal found
						if(mazeContext[expandedNode.getX()][expandedNode.getY()] == '.'&&isChecked.indexOf(expandedNode.getX()+"/"+expandedNode.getY())<0)
						{
							expandedNode.lastNode = currentNode;
							int leastNodeExpanded = backTrace(expandedNode,mazeContext);
							Thread.sleep(80);
							System.out.println("Total steps: "+leastNodeExpanded+"    Total Node Expanded: "+nodeExpanded);
							repaint();
							return 1;
						}
				//end if
						//calculating heuristic. set it to the heuristic for the current expanded node
						expandedNode.setH(calcuManhattonDistance(endNode.getX(),expandedNode.getX(),endNode.getY(),expandedNode.getY()));

						numberOfSteps++;
						if(isChecked.indexOf(expandedNode.getX()+"/"+expandedNode.getY())<0)
						{
							expandedNode.lastNode = currentNode;
							mazeLocaQue.add(expandedNode);//insert current state
							isChecked.add(expandedNode.getX()+"/"+expandedNode.getY());//insert successor state
							mazeContext[expandedNode.getX()][expandedNode.getY()] = 'F';
						}

					Thread.sleep(30);
					}
				}
			}
		}
		return 0;
	}
	//back trace function
	public static int backTrace(Node walkedNode,char [][] mazeContext)
	{
		int leastSteps = 1;
		while(walkedNode.lastNode!=null)
		{
			leastSteps++;
			mazeContext[walkedNode.getX()][walkedNode.getY()] = 'O';
			walkedNode = walkedNode.lastNode;
		}
		
		return leastSteps;
	}
	//**********A*
	public int aStar(char[][]mazeContext, Node startNode, final Node endNode) throws InterruptedException
	{
		Comparator<Node> nodeCom = new Comparator<Node>() {
		public int compare(Node node1, Node node2) {
		if(node1.getCost()+calcuManhattonDistance(node1.getX(),endNode.getX(),node1.getY(),endNode.getY())<node2.getCost()+calcuManhattonDistance(node2.getX(),endNode.getX(),node2.getY(),endNode.getY())) return -1;
		else if(node1.getCost()+calcuManhattonDistance(node1.getX(),endNode.getX(),node1.getY(),endNode.getY())>node2.getCost()+calcuManhattonDistance(node2.getX(),endNode.getX(),node2.getY(),endNode.getY())) return 1;
		return 0;
		}
	};
	int countN = 0;
	PriorityQueue<Node> mazeLocaQue = new PriorityQueue<Node>(1,nodeCom);
	mazeLocaQue.offer(startNode);
	Node result = mazeLocaQue.poll();
	while(calcuManhattonDistance(result.getX(),endNode.getX(),result.getY(),endNode.getY())>1){
		
		mazeContext[result.getX()][result.getY()] = 'F';
		Thread.sleep(30);
	
		repaint();
		
		ArrayList<Node> cnodes = extractNode(mazeContext,result);
		for(Node node:cnodes)
		{
			countN++;
			mazeLocaQue.offer(node);
		}
		
		mazeContext[result.getX()][result.getY()] = '.';
		Thread.sleep(30);
		repaint();
		
		result = mazeLocaQue.poll();
		
	}
	countN += extractNode(mazeContext,result).size();
	mazeContext[result.getX()][result.getY()] = '.';
	//Thread.sleep(80);
	countN++;
	

	int counter  = 1;
	while(result.lastNode != null){
		counter++;
		mazeContext[result.getX()][result.getY()] = 'O';
		result = result.lastNode;
	}
	if(result!=null)
	{
		System.out.println("Total steps: "+counter+"    Total Node Expanded: "+countN);
		repaint();
		return 1;
	}
		return 0;
	}

	public static ArrayList<Node> extractNode(char[][]mazeContext, Node node){
		ArrayList<Node> resultList = new ArrayList<>();
		if (mazeContext[node.getX()+1][node.getY()]==' ') resultList.add(new Node(node.getX()+1,node.getY(),node.getCost()+1,node));
		if (mazeContext[node.getX()-1][node.getY()]==' ') resultList.add(new Node(node.getX()-1,node.getY(),node.getCost()+1,node));
		if (mazeContext[node.getX()][node.getY()+1]==' ') resultList.add(new Node(node.getX(),node.getY()+1,node.getCost()+1,node));
		if (mazeContext[node.getX()][node.getY()-1]==' ') resultList.add(new Node(node.getX(),node.getY()-1,node.getCost()+1,node));
		return resultList;
	}
	
	
	
	
	
	//Calculating heuristic
	
	//*******the algorithm Ghost
	public int aStarwGhost(char[][] mazeContext,Node startNode,final Node endNode) throws InterruptedException{
		char[][] mazecopy = new char[mazeContext.length][];
		List<String> isChecked = new ArrayList<String>();
		for (int i=0;i<mazeContext.length;i++)
			mazecopy[i] = Arrays.copyOf(mazeContext[i], mazeContext[i].length);
//		ArrayList<int[]> ghostPath = new ArrayList<>();
		ArrayList<ArrayList<int[]>> ghostsPath = new ArrayList<ArrayList<int[]>>(26);		//maximum is 25 ghosts, since P is already taken for player
		for (int i=0;i<26;i++)
			ghostsPath.add(new ArrayList<int[]>());
		int countN = 0;
		int[] currentGPos = new int[26];
		for (int i=0;i<currentGPos.length;i++)
			currentGPos[i] = -1;
		for(int i=0;i<mazeContext.length;i++)
			for(int j=0;j<mazeContext[i].length;j++){
/*				if (mazeContext[i][j]=='g'){
					int[] path = {i,j};
					ghostPath.add(path);
				} else if (mazeContext[i][j]=='G'){
					int[] path = {i,j};
					ghostPath.add(path);
					currentGPos = ghostPath.indexOf(path);
				} */
				if (Character.isLetter(mazeContext[i][j])&&mazeContext[i][j]!='P'){
					if (Character.isUpperCase(mazeContext[i][j])){
						int number = (int)mazeContext[i][j]-(int)'A';
						int[] path = {i,j};
						ghostsPath.get(number).add(path);
						currentGPos[number] = ghostsPath.get(number).indexOf(path);
						mazeContext[i][j] = ' ';
					} else {
						int number = (int)mazeContext[i][j]-(int)'a';
						int[] path = {i,j};
						ghostsPath.get(number).add(path);
						mazeContext[i][j] = ' ';
					}
				}

			}
		ArrayList<Ghost> ghosts = new ArrayList<Ghost>();
		for(int i=0;i<currentGPos.length;i++)
			if (currentGPos[i]!=-1){
				ghosts.add(new Ghost(ghostsPath.get(i),currentGPos[i]));
			}
//		Ghost ghost = new Ghost(ghostPath,currentGPos);
//		ArrayList<Node> iniList = extractNodewGhost(mazeContext,startNode,ghosts);
		countN++;
		Comparator<Node> nodeCom = new Comparator<Node>() {
			public int compare(Node node1, Node node2) {
				if(node1.getCost()+calcuManhattonDistance(node1.getX(),endNode.getX(),node1.getY(),endNode.getY())<node2.getCost()+calcuManhattonDistance(node2.getX(),endNode.getX(),node2.getY(),endNode.getY())) return -1;
				else if(node1.getCost()+calcuManhattonDistance(node1.getX(),endNode.getX(),node1.getY(),endNode.getY())>node2.getCost()+calcuManhattonDistance(node2.getX(),endNode.getX(),node2.getY(),endNode.getY())) return 1;
				return 0;
			}
		};
		PriorityQueue<Node> mazeLocaQue = new PriorityQueue<Node>(1,nodeCom);
		mazeLocaQue.offer(startNode);
//		for(Node node:iniList)
//		{
//			mazeLocaQue.offer(node);
//		}
		Node resultN = mazeLocaQue.poll();
		while(calcuManhattonDistance(resultN.getX(),endNode.getX(),resultN.getY(),endNode.getY())>1){
			//mazeContext[resultN.getX()][resultN.getY()]='.';
			System.out.println("ccccc  "+resultN.getX()+ "    bvvvbb : "+resultN.getY()+"   ENDNODE: "+endNode.getX()+"   "+endNode.getY());
			ArrayList<Node> cnodes = extractNodewGhost(mazeContext,resultN,ghosts);
			countN++;
			for(Node node:cnodes)
			{
				mazeLocaQue.offer(node);
			}
			resultN = mazeLocaQue.poll();
		}
		clearScreen();
		ArrayList<Node> path = new ArrayList<>();
		
		path.add(resultN);
		int counter  = 1;
	while(resultN.lastNode != null){
		//System.out.println("asdasdsa");
		counter++;
		path.add(resultN.lastNode);
		resultN = resultN.lastNode;
		
	}
	counter += extractNodewGhost(mazeContext,resultN,ghosts).size();
	for(int i=0;i<mazecopy.length;i++)
	{
		for(int j=0;j<mazecopy[i].length;j++)
		{
			System.out.println(mazecopy[i][j]);
//			if (mazecopy[i][j]=='g'||mazecopy[i][j]=='G') mazecopy[i][j] = ' ';
		}
	}
	/*
	for(Ghost ghost:ghosts)
		for(int[] gpos:ghost.getGhostPath())
			mazecopy[gpos[0]][gpos[1]] = ' ';
	*/
	Collections.reverse(path);
	
	for(Node player: path){
		int [] gposCopy;
		List<Integer> tempGx = new ArrayList<Integer>();
		List<Integer> tempGy = new ArrayList<Integer>();
		for(Ghost ghost: ghosts){
			int[] gpos = ghost.posAtTurn(player.getCost());
			mazeContext[gpos[0]][gpos[1]] = 'G';
			repaint();
			tempGx.add(gpos[0]); 
			tempGy.add(gpos[1]);
			
		}
		mazeContext[player.getX()][player.getY()] = 'P';
		//*******
		repaint();
		Thread.sleep(30);
		isChecked.add(player.getX()+"/"+player.getY());
		for(int i=0;i<tempGx.size();i++)
		{
		if(isChecked.indexOf(tempGx.get(i)+"/"+tempGy.get(i))<1)
		{
		//	System.out.println("nodot");
			mazeContext[tempGx.get(i)][tempGy.get(i)] = ' ';
		}
		else
		{
			mazeContext[tempGx.get(i)][tempGy.get(i)] = '.';;
		}
		}
		//mazeContext[tempGx][tempGy] = ' ';
		
		
		Thread.sleep(30);
		
		
		/*
		
		*/
		mazeContext[player.getX()][player.getY()] = '.';
		//*******
		for(Ghost ghost: ghosts){
			int[] gpos = ghost.posAtTurn(player.getCost());
			mazecopy[gpos[0]][gpos[1]] = ' ';
		}
		mazecopy[player.getX()][player.getY()] = ' ';
		Thread.sleep(30);
		clearScreen();
	}
	
	if(resultN!=null) {
		for(int k =0;k<mazeContext.length;k++) {
			for(int j = 0;j<mazeContext[k].length;j++){
				System.out.print(mazeContext[k][j]);
			}
			System.out.println();
		}
		System.out.println("Total steps: "+counter+"    Total Node Expanded: "+countN);
		return 1;}
	
	return 0;
}

	public static void clearScreen(){
		for(int i=0;i<100;i++) System.out.println();
	}
	public static int calcuManhattonDistance(int x0,int x1, int y0, int y1)
	{
		int distance = Math.abs(x1-x0) + Math.abs(y1-y0);
		return distance;
	}
	public static int[] searchForGoal(char[][]mazeContext, int width, int height)
	{
		for(int i = 0; i<height;i++)
		{
			for(int j = 0; j<width;j++)
			{
				if(mazeContext[i][j] == '.')
				{
					int[] goalPosition = new int[2];
					goalPosition[0] = i;
					goalPosition[1] = j;
					return goalPosition;
				}
			}
		}
		return null;
	}
//extract node node walls .find path
	public static ArrayList<Node> extractNodewGhost(char[][]mazeContext, Node node, ArrayList<Ghost> ghosts){
		ArrayList<Node> resultList = new ArrayList<>();
//		int[] ghostPosition = ghost.posAtTurn(node.getCost());
		if(touchGhost(node.getX(),node.getY(),node.getCost(),ghosts)) return resultList;
//		if(node.getX()==ghostPosition[0]&&node.getY()==ghostPosition[1]) return resultList;
	/*	
		if (mazeContext[node.getX()+1][node.getY()]==' '&&!(touchGhost(node.getX()+1,node.getY(),node.getCost(),ghosts))){
			Node nextNode = new Node(node.getX()+1,node.getY(),node.getCost()+1,node);
			resultList.add(nextNode);
			resultList.add(new Node(node.getX(),node.getY(),node.getCost()+2,nextNode));
		}
		if (mazeContext[node.getX()-1][node.getY()]==' '&&!(touchGhost(node.getX()-1,node.getY(),node.getCost(),ghosts))){
			Node nextNode = new Node(node.getX()-1,node.getY(),node.getCost()+1,node);
			resultList.add(nextNode);
			resultList.add(new Node(node.getX(),node.getY(),node.getCost()+2,nextNode));
		}
		if (mazeContext[node.getX()][node.getY()+1]==' '&&!(touchGhost(node.getX(),node.getY()+1,node.getCost(),ghosts))){
			Node nextNode = new Node(node.getX(),node.getY()+1,node.getCost()+1,node);
			resultList.add(nextNode);
			resultList.add(new Node(node.getX(),node.getY(),node.getCost()+2,nextNode));
		}
		if (mazeContext[node.getX()][node.getY()-1]==' '&&!(touchGhost(node.getX(),node.getY()-1,node.getCost(),ghosts))){
			Node nextNode = new Node(node.getX(),node.getY()-1,node.getCost()+1,node);
			resultList.add(nextNode);
			resultList.add(new Node(node.getX(),node.getY(),node.getCost()+2,nextNode));
		}
	*/
		if (mazeContext[node.getX()+1][node.getY()]!='%'&&!(touchGhost(node.getX()+1,node.getY(),node.getCost(),ghosts))) resultList.add(new Node(node.getX()+1,node.getY(),node.getCost()+1,node));
		if (mazeContext[node.getX()-1][node.getY()]!='%'&&!(touchGhost(node.getX()-1,node.getY(),node.getCost(),ghosts))) resultList.add(new Node(node.getX()-1,node.getY(),node.getCost()+1,node));
		if (mazeContext[node.getX()][node.getY()+1]!='%'&&!(touchGhost(node.getX(),node.getY()+1,node.getCost(),ghosts))) resultList.add(new Node(node.getX(),node.getY()+1,node.getCost()+1,node));
		if (mazeContext[node.getX()][node.getY()-1]!='%'&&!(touchGhost(node.getX(),node.getY()-1,node.getCost(),ghosts))) resultList.add(new Node(node.getX(),node.getY()-1,node.getCost()+1,node));

//		if (mazeContext[node.getX()+1][node.getY()]!='%'&&!(node.getX()+1==ghostPosition[0]&&node.getY()==ghostPosition[1])) resultList.add(new Node(node.getX()+1,node.getY(),node.getCost()+1,node));
//		if (mazeContext[node.getX()-1][node.getY()]!='%'&&!(node.getX()-1==ghostPosition[0]&&node.getY()==ghostPosition[1])) resultList.add(new Node(node.getX()-1,node.getY(),node.getCost()+1,node));
//		if (mazeContext[node.getX()][node.getY()+1]!='%'&&!(node.getX()==ghostPosition[0]&&node.getY()+1==ghostPosition[1])) resultList.add(new Node(node.getX(),node.getY()+1,node.getCost()+1,node));
//		if (mazeContext[node.getX()][node.getY()-1]!='%'&&!(node.getX()==ghostPosition[0]&&node.getY()-1==ghostPosition[1])) resultList.add(new Node(node.getX(),node.getY()-1,node.getCost()+1,node));
		return resultList;
	}
	public static boolean touchGhost(int nodeX,int nodeY,int cost, ArrayList<Ghost> ghosts){
		for(Ghost ghost:ghosts){
			int[] ghostPosition = ghost.posAtTurn(cost);
			if (nodeX==ghostPosition[0]&&nodeY==ghostPosition[1]) return true;
		}
		return false;
	}

	 //1.2  turnCost
public static int directionDiff(char d1,char d2){
	final char[] direction = {'n','e','s','w'};
	int d1pos = 0;
	int d2pos = 0;
	for (int i=0;i<direction.length;i++){
		if (d1==direction[i]) d1pos = i;
		if (d2==direction[i]) d2pos = i;
	}
	int difference = Math.abs(d1pos-d2pos);
	if (difference==2) return 2;
	else if (difference==0) return 0;
	return 1;
}
public static int turnHeuristic(DirectedNode node, int gx, int gy,int forwardcost,int turncost){

	int turnsChange = node.getTurns()-node.lastNode.getTurns();
	return calcuManhattonDistance(node.getX(),gx,node.getY(),gy)*forwardcost+turnsChange*turncost;

}	
public static ArrayList<DirectedNode> extractNodeTurnCost(char[][]mazeContext, DirectedNode node, int forwardCost, int turnCost){
	ArrayList<DirectedNode> resultList = new ArrayList<>();
	if (mazeContext[node.getX()+1][node.getY()]==' ') resultList.add(new DirectedNode(node.getX()+1,node.getY(),node.getCost()+directionDiff('s',node.getDirection())*turnCost+forwardCost,node,'s',node.getTurns()+directionDiff('s',node.getDirection())));
	if (mazeContext[node.getX()-1][node.getY()]==' ') resultList.add(new DirectedNode(node.getX()-1,node.getY(),node.getCost()+directionDiff('n',node.getDirection())*turnCost+forwardCost,node,'n',node.getTurns()+directionDiff('n',node.getDirection())));
	if (mazeContext[node.getX()][node.getY()+1]==' ') resultList.add(new DirectedNode(node.getX(),node.getY()+1,node.getCost()+directionDiff('e',node.getDirection())*turnCost+forwardCost,node,'e',node.getTurns()+directionDiff('e',node.getDirection())));
	if (mazeContext[node.getX()][node.getY()-1]==' ') resultList.add(new DirectedNode(node.getX(),node.getY()-1,node.getCost()+directionDiff('w',node.getDirection())*turnCost+forwardCost,node,'w',node.getTurns()+directionDiff('w',node.getDirection())));
	return resultList;
}

//our H
public int aStarTurnCostOurHeuristic(char[][] mazeContext,Node startNode,final Node endNode,int caseChoosing) throws InterruptedException{
	DirectedNode startNodedir = new DirectedNode(startNode.getX(),startNode.getY());
	int counter = 0;
	int forwardCost = caseChoosing == 1 ? 1 : 2;
	int turnCost = caseChoosing == 1 ? 2 : 1;		
	
	
	Comparator<DirectedNode> nodeCom = new Comparator<DirectedNode>() {
		public int compare(DirectedNode node1, DirectedNode node2) {
			if(node1.getCost()+turnHeuristic(node1,endNode.getX(),endNode.getY(),forwardCost,turnCost)<node2.getCost()+turnHeuristic(node2,endNode.getX(),endNode.getY(),forwardCost,turnCost)) return -1;
			else if(node1.getCost()+turnHeuristic(node1,endNode.getX(),endNode.getY(),forwardCost,turnCost)>node2.getCost()+turnHeuristic(node2,endNode.getX(),endNode.getY(),forwardCost,turnCost)) return 1;
			
			return 0;
		}
	};
	PriorityQueue<DirectedNode> mazeLocaQue = new PriorityQueue<DirectedNode>(1,nodeCom);
	mazeLocaQue.offer(startNodedir);
	DirectedNode result = mazeLocaQue.poll();
	while(calcuManhattonDistance(result.getX(),endNode.getX(),result.getY(),endNode.getY())>1){
		mazeContext[result.getX()][result.getY()] = '.';
		repaint();
		Thread.sleep(30);
		/*
		for(int k =0;k<mazeContext.length;k++) {
			for(int j = 0;j<mazeContext[k].length;j++){
				System.out.print(mazeContext[k][j]);
			}
			System.out.println();
		}
		*/
		//Thread.sleep(200);
		ArrayList<DirectedNode> cnodes = extractNodeTurnCost(mazeContext,result,forwardCost,turnCost);
		for(DirectedNode node:cnodes){
			counter++;
			mazeLocaQue.offer(node);
		}
		result = mazeLocaQue.poll();
		
		mazeContext[result.getX()][result.getY()] = 'F';
		repaint();
		Thread.sleep(30);
	}
	counter += extractNodeTurnCost(mazeContext, result, forwardCost, turnCost).size();
	mazeContext[result.getX()][result.getY()] = '.';

	System.out.println((result.getX()+1)+" "+(result.getY()+1));
	
	for(int k =0;k<mazeContext.length;k++) {
		for(int j = 0;j<mazeContext[k].length;j++){
			System.out.print(mazeContext[k][j]);
		}
		System.out.println();
	}
	
	System.out.println(result.getX()+","+(result.lastNode.getY()+1)+","+result.lastNode.getDirection()+","+result.getCost());
	if (result==null) return 0;
	int steps = 1;
	int cost = result.getCost()+forwardCost;
	while(result.lastNode != null){
		mazeContext[result.getX()][result.getY()] = 'O';
		repaint();
		System.out.println((result.lastNode.getX()+1)+","+(result.lastNode.getY()+1)+","+result.lastNode.getDirection());
		result = result.lastNode;
		steps ++;
	}
	System.out.println("Total steps: "+steps+"    Total Node Expanded: "+counter+"    Solution Cost: "+cost);
	return 1;
}



public int aStarTurnCostManhatton(char[][] mazeContext,Node startNode,final Node endNode,int caseChoosing) throws InterruptedException{
	DirectedNode startNodedir = new DirectedNode(startNode.getX(),startNode.getY());
	
	int counter = 0;
	int forwardCost = caseChoosing == 1 ? 1 : 2;
	int turnCost = caseChoosing == 1 ? 2 : 1;
	
	
	Comparator<DirectedNode> nodeCom = new Comparator<DirectedNode>() {
		public int compare(DirectedNode node1, DirectedNode node2) {
//			
			if(node1.getCost()+calcuManhattonDistance(node1.getX(),endNode.getX(),node1.getY(),endNode.getY())<node2.getCost()+calcuManhattonDistance(node2.getX(),endNode.getX(),node2.getY(),endNode.getY())) return -1;
			else if(node1.getCost()+calcuManhattonDistance(node1.getX(),endNode.getX(),node1.getY(),endNode.getY())>node2.getCost()+calcuManhattonDistance(node2.getX(),endNode.getX(),node2.getY(),endNode.getY())) return 1;

			return 0;
		}
	};
	PriorityQueue<DirectedNode> mazeLocaQue = new PriorityQueue<DirectedNode>(1,nodeCom);
	mazeLocaQue.offer(startNodedir);
	DirectedNode result = mazeLocaQue.poll();
	while(calcuManhattonDistance(result.getX(),endNode.getX(),result.getY(),endNode.getY())>1){
		mazeContext[result.getX()][result.getY()] = '.';
		repaint();
		Thread.sleep(30);
		ArrayList<DirectedNode> cnodes = extractNodeTurnCost(mazeContext,result,forwardCost, turnCost);
		for(DirectedNode node:cnodes){
			counter++;
			mazeLocaQue.offer(node);
		}
		result = mazeLocaQue.poll();
		mazeContext[result.getX()][result.getY()] = 'F';
		repaint();
		Thread.sleep(30);
	}
	counter += extractNodeTurnCost(mazeContext, result, forwardCost, turnCost).size();
	mazeContext[result.getX()][result.getY()] = '.';
	repaint();
	//System.out.println((result.getX()+1)+" "+(result.getY()+1));
	
	if (result==null) return 0;
	int steps = 1;
	int cost = result.getCost()+forwardCost;
	while(result.lastNode != null){
		mazeContext[result.getX()][result.getY()] = 'O';
		repaint();
		
		result = result.lastNode;
		steps ++;
		

	}
	System.out.println("Total steps: "+steps+"    Total Node Expanded: "+counter+"    Solution Cost: "+cost);
	return 1;
}




//override paint method
	public void paintComponent(Graphics g)
	{
		//super.paint(g);
		
		//g.drawImage(m.getWall(), 50, 50, null);
	
		
		//char [][] currentMaze =theMaze;
		
		goalX = xG;
		goalY = yG;
		/*
		for(int k =0;k<rowCount;k++)
		{
			for(int j = 0;j<colCount;j++)
			{
				System.out.print(theMaze[k][j]);
			}
			System.out.println();
		}
		*/
		//System.out.println("painting");
		for(int x = 0;x<colCount;x++)
		{
			for(int y = 0; y<rowCount;y++)
			{
				if(theMaze[y][x]=='%')
				{
					//g.setColor(Color.black);
					//g.fillRect(x*25, y*25, 25,25);
					g.drawImage(m.getWall(), x*10, y*10, this);
				}else
				if(theMaze[y][x]==' ')
				{
					//g.drawImage(m.getPath(), x*25, y*25, null);
					g.setColor(Color.white);
					g.fillRect(x*10, y*10, 10, 10);
				}else
				if(theMaze[y][x]=='P')
				{
					g.drawImage(m.getPac(), x*10, y*10, this);
					//g.setColor(Color.orange);
					//g.fillRect(x*25, y*25, 25, 25);
				}else
				if(theMaze[y][x]=='.')//and x y is not the end goal position
				{
					if(y == goalX && x == goalY)
					{
						g.drawImage(m.getGoal(), x*10, y*10, this);
					}else
					{
					g.drawImage(m.getNode(), x*10, y*10, this);
					}
					//g.setColor(Color.red);
					//g.fillRect(x*25, y*25, 25,25);
				}else
				/*if(currentMaze[x][y]=='O')
				{
					//g.drawImage(m.getPath(), x*25, y*25, null);
					g.setColor(Color.gray);
					g.fillRect(x*10, y*10, 10, 10);
				}*/
				if(theMaze[y][x]=='G')
				{
					g.drawImage(m.getGhost(), x*10, y*10, null);
					//g.setColor(Color.gray);
					//g.fillRect(x*10, y*10, 10, 10);
				}else
				if(theMaze[y][x]=='g')
				{
					//g.drawImage(m.getGhost(), x*10, y*10, null);
					g.setColor(Color.white);
					g.fillRect(x*10, y*10, 10, 10);
				}else
				if(theMaze[y][x]=='O')
				{
					g.setColor(Color.cyan);
					g.fillRect(x*10, y*10, 10, 10);
				}else
					if(theMaze[y][x]=='F')
					{
						g.drawImage(m.getFront(), x*10, y*10, null);
					}
				
			}
		}
		
	}
}
