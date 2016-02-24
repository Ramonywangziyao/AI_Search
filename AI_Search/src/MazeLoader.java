import java.util.*;
import java.awt.Color;
import java.awt.Image;
import java.io.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class MazeLoader {
	//initialize variables here
	
	
		boolean [][] trace;
	/*
		public static void main(String[]args) throws IOException, InterruptedException
		{
			FileChooseingMethodChoosing();
		}
	*/	
		public static char[][] FileChooseingMethodChoosing() throws IOException, InterruptedException
		{
			String fileName;
			Scanner scanner = new Scanner(System.in);
			System.out.println("enter the file name");
			fileName = scanner.next();
			FileReader fr = new FileReader(fileName);
			BufferedReader buffer = new BufferedReader (fr);

			char [][] maze = null;
			String line;
			int x = 0;
			int count = 0;
			int size = 0;
			//total rows
			while((line = buffer.readLine())!=null)
			{
		
				count++;
			}
			fr.close();
			
			while(true)
			{
			x=0;
			maze=null;
			System.out.println();
			System.out.println("What method do you want to use? 1.Depth   2.Breath   3.Greedy   4.A*   5.Ghost  6.WithTurnManhatton  7.WithTurnOurHeuristic   0.Exit   9.New File Path");
			int methodChose = scanner.nextInt();
			FileReader fr2 = new FileReader(fileName);
			BufferedReader bufferNew = new BufferedReader (fr2);
			while((line = bufferNew.readLine())!=null)
			{
				char [] vals = line.toCharArray();
	            if (maze == null) {
	                size = vals.length;
	                maze = new char[count][size];
	            }
	            for (int col = 0; col < size; col++) {
	                maze[x][col] = vals[col];
	                System.out.print(maze[x][col]);
	            }
	            x++;
	            System.out.println();
			}
			int [] startPacman = searchForPacman(maze,size,count);
			int [] goalofPacman = searchForGoal(maze,size,count);
			Node startNode = new Node(startPacman[0],startPacman[1]);
			Node endNode = new Node(goalofPacman[0],goalofPacman[1]);
			ArrayList<Node> solution = new ArrayList<Node>();
			boolean [][] trace = new boolean[count][size];

			if(methodChose == 0)
			{
				System.exit(0);
			}
			if(methodChose == 1)
			{
			int stepsTotal = 0;
			int totalNode = 0;
			int result = depthFirst(solution,maze,startNode.getX(),startNode.getY(),trace,size,count,stepsTotal,totalNode,endNode);
			if(result == 1)
			{
				System.out.println("Goal Found!");
				fr2.close();
			}
			else if(result == 0)
			{
				System.out.println("Goal Not Found!");
				fr2.close();
			}
			}else if(methodChose == 2)
			{
			int result = breadth(maze,startNode,size,count);
			if(result == 1)
			{
				System.out.println("Goal Found!");
				fr2.close();
			}
			else if(result == 0)
			{
				System.out.println("Goal Not Found!");
				fr2.close();
			}
			}else if(methodChose == 3)
			{
			int result = greedyBest(maze,startNode,endNode,size,count);
			if(result == 1)
			{
				System.out.println("Goal Found!");
				fr2.close();
			}
			else if(result == 0)
			{
				System.out.println("Goal Not Found!");
				fr2.close();
			}
			}
			else if(methodChose == 4)
			{
			int result = aStar(maze,startNode,endNode);
			if(result == 1)
			{
				System.out.println("Goal Found!");
				fr2.close();
			}
			else if(result == 0)
			{
				System.out.println("Goal Not Found!");
				fr2.close();
			}
			}
			else if(methodChose == 5)
			{
			int result = aStarwGhost(maze,startNode,endNode);
			if(result == 1)
			{
				System.out.println("Goal Found!");
				fr2.close();
			}
			else if(result == 0)
			{
				System.out.println("Goal Not Found!");
				fr2.close();
			}
			}
			else if(methodChose == 6)
			{
			int caseOne = 1;
			int caseTwo = 2;
			System.out.println("Type 1 for case 1: forward 1 turn 2    Type 2 for case 2: forward 2 turn 1 ");
			int caseChose = scanner.nextInt();
			int result = -1;
			if(caseChose == 1)
			{
				result = aStarTurnCostManhatton(maze,startNode,endNode,caseOne);
			}else if(caseChose == 2)
			{
			    result = aStarTurnCostManhatton(maze,startNode,endNode,caseTwo);
			}
			if(result == 1)
			{
				System.out.println("Goal Found!");
				fr2.close();
			}
			else if(result == 0)
			{
				System.out.println("Goal Not Found!");
				fr2.close();
			}
			}
			else if(methodChose == 7)
			{
				int caseOne = 1;
				int caseTwo = 2;
				System.out.println("Type 1 for case 1: forward 1 turn 2    Type 2 for case 2: forward 2 turn 1 ");
				int caseChose = scanner.nextInt();
				int result = -1;
				if(caseChose == 1)
				{
					result = aStarTurnCostOurHeuristic(maze,startNode,endNode,caseOne);
				}else if(caseChose == 2)
				{
				    result = aStarTurnCostOurHeuristic(maze,startNode,endNode,caseTwo);
				}
				if(result == 1)
				{
					System.out.println("Goal Found!");
					fr2.close();
				}
				else if(result == 0)
				{
					System.out.println("Goal Not Found!");
					fr2.close();
				}
			}
			else if(methodChose == 9)
			{
				fr2.close();
				//recursive to keep runing
				FileChooseingMethodChoosing();
			}
		
			}
		
	}
	public static int[] searchForPacman(char[][]mazeContext, int width, int height)
	{
		for(int i = 0; i<height;i++)
		{
			for(int j = 0; j<width;j++)
			{
				if(mazeContext[i][j] == 'P')
				{
					int[] pacmanPosition = new int[2];
					pacmanPosition[0] = i;
					pacmanPosition[1] = j;
				
					return pacmanPosition;
				}
			}
		}
		return null;
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
	// DFS
	public static int depthFirst(ArrayList<Node> solution, char[][] mazeContext,int x,int y,boolean [][] walkedTrace,int width, int height,int steps,int nodesE,Node endNode) throws InterruptedException
	{
		clearScreen();
		//base case
		for(int k =0;k<height;k++)
		{
			for(int j = 0;j<width;j++)
			{
				System.out.print(mazeContext[k][j]);
			}
			System.out.println();
		}
		Thread.sleep(80);
		//goal found
	if(mazeContext[x][y] == '.'&&x==endNode.getX()&&y==endNode.getY())
	{
		int leastNodeExpanded= backTrace(solution.get(solution.size()-1),mazeContext);
		for(int k =0;k<height;k++)
		{
			for(int j = 0;j<width;j++)
			{
				System.out.print(mazeContext[k][j]);
			}
			System.out.println();
		}
		System.out.println("Total steps: "+leastNodeExpanded+"    Total Node Expanded: "+nodesE);
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
						return 1;	
					}
					solution.remove(solution.size()-1); //unpaint
					mazeContext[x][y] = ' ';
					
			}

	}

	return 0;
}
	
	//BFS
	public static int breadth(char[][]mazeContext, Node newNode, int Height, int Width) throws InterruptedException
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
			Node currentNode = mazeLocaQue.poll();
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
						for(int k =0;k<Width;k++)
						{
							for(int j = 0;j<Height;j++)
							{
								System.out.print(mazeContext[k][j]);
							}
							System.out.println();
						}
						System.out.println("Total steps: "+leastNodeExpanded+"    Total Node Expanded: "+nodeExpanded);
						return 1;
					}
					mazeLocaQue.add(theNewNode);
					isChecked.add(theNewNode.getX()+"/"+theNewNode.getY());
					mazeContext[theNewNode.getX()][theNewNode.getY()] = '.';	
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
						clearScreen();
						for(int k =0;k<Width;k++)
						{
							for(int j = 0;j<Height;j++)
							{
								System.out.print(mazeContext[k][j]);
							}
							System.out.println();
						}
						System.out.println("Total steps: "+leastNodeExpanded+"    Total Node Expanded: "+nodeExpanded);
						return 1;
					}
					mazeLocaQue.add(theNewNode);
					isChecked.add(theNewNode.getX()+"/"+theNewNode.getY());
					mazeContext[theNewNode.getX()][theNewNode.getY()] = '.';
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
						clearScreen();
						for(int k =0;k<Width;k++)
						{
							for(int j = 0;j<Height;j++)
							{
								System.out.print(mazeContext[k][j]);
							}
							System.out.println();
						}
						System.out.println("Total steps: "+leastNodeExpanded+"    Total Node Expanded: "+nodeExpanded);
						return 1;
					}
					mazeLocaQue.add(theNewNode);
					isChecked.add(theNewNode.getX()+"/"+theNewNode.getY());
					mazeContext[theNewNode.getX()][theNewNode.getY()] = '.';
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
						clearScreen();
						for(int k =0;k<Width;k++)
						{
							for(int j = 0;j<Height;j++)
							{
								System.out.print(mazeContext[k][j]);
							}
							System.out.println();
						}
						System.out.println("Total steps: "+leastNodeExpanded+"    Total Node Expanded: "+nodeExpanded);
						return 1;
					}
					mazeLocaQue.add(theNewNode);
					isChecked.add(theNewNode.getX()+"/"+theNewNode.getY());
					mazeContext[theNewNode.getX()][theNewNode.getY()] = '.';
				}
			}
			clearScreen();
			//printing the map
			for(int k =0;k<Width;k++)
			{
				for(int j = 0;j<Height;j++)
				{
					System.out.print(mazeContext[k][j]);
				}
				System.out.println();
			}
			Thread.sleep(80);
		}
		return 0;
	}
	//greedy best first search
	public static int greedyBest(char[][]mazeContext, Node newNode,Node endNode, int Width, int Height) throws InterruptedException
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
			clearScreen();
			Node currentNode = mazeLocaQue.poll();
			isChecked.add(currentNode.getX()+"/"+currentNode.getY());
			numberOfSteps++;
			//found goal
			if(mazeContext[currentNode.getX()][currentNode.getY()] == '.'&&isChecked.indexOf(currentNode.getX()+"/"+currentNode.getY())<0)
			{
				numberOfSteps++;
				int leastNodeExpanded = backTrace(currentNode,mazeContext);
				clearScreen();
				for(int k =0;k<Width;k++)
				{
					for(int j = 0;j<Height;j++)
					{
						System.out.print(mazeContext[k][j]);
					}
					System.out.println();
				}
				System.out.println("Total steps: "+leastNodeExpanded+"    Total Node Expanded: "+nodeExpanded);
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
						clearScreen();
						for(int k =0;k<Height;k++)
						{
							for(int j = 0;j<Width;j++)
							{
								System.out.print(mazeContext[k][j]);
							}
							System.out.println();
						}
						Thread.sleep(80);
						System.out.println("Total steps: "+leastNodeExpanded+"    Total Node Expanded: "+nodeExpanded);
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
							clearScreen();
							for(int k =0;k<Height;k++)
							{
								for(int j = 0;j<Width;j++)
								{
									System.out.print(mazeContext[k][j]);
								}
								System.out.println();
							}
							Thread.sleep(80);
							System.out.println("Total steps: "+leastNodeExpanded+"    Total Node Expanded: "+nodeExpanded);
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
							mazeContext[expandedNode.getX()][expandedNode.getY()] = '.';
						}
						clearScreen();
						for(int k =0;k<Height;k++)
						{	
							for(int j = 0;j<Width;j++)
							{
								System.out.print(mazeContext[k][j]);
							}
							System.out.println();
						}

					Thread.sleep(80);
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
	
	//Calculating heuristic
	public static int calcuManhattonDistance(int x0,int x1, int y0, int y1)
	{
		int distance = Math.abs(x1-x0) + Math.abs(y1-y0);
		return distance;
	}
	//a star search
	public static int aStar(char[][]mazeContext, Node startNode, final Node endNode)
	{
		ArrayList<Node> iniList = extractNode(mazeContext,startNode);
		Comparator<Node> nodeCom = new Comparator<Node>() {
		public int compare(Node node1, Node node2) {
		if(node1.getCost()+calcuManhattonDistance(node1.getX(),endNode.getX(),node1.getY(),endNode.getY())<node2.getCost()+calcuManhattonDistance(node2.getX(),endNode.getX(),node2.getY(),endNode.getY())) return -1;
		else if(node1.getCost()+calcuManhattonDistance(node1.getX(),endNode.getX(),node1.getY(),endNode.getY())>node2.getCost()+calcuManhattonDistance(node2.getX(),endNode.getX(),node2.getY(),endNode.getY())) return 1;
		return 0;
		}
	};
	int countN = 0;
	PriorityQueue<Node> mazeLocaQue = new PriorityQueue<Node>(iniList.size(),nodeCom);
	for(Node node:iniList)
		mazeLocaQue.offer(node);
	Node result = mazeLocaQue.poll();
	while(calcuManhattonDistance(result.getX(),endNode.getX(),result.getY(),endNode.getY())>1){
		mazeContext[result.getX()][result.getY()] = '.';
		countN++;
		ArrayList<Node> cnodes = extractNode(mazeContext,result);
		for(Node node:cnodes)
			mazeLocaQue.offer(node);
		result = mazeLocaQue.poll();
	}
	mazeContext[result.getX()][result.getY()] = '.';
	countN++;
	

	int counter  = 1;
	while(result.lastNode != null){
		counter++;
		mazeContext[result.getX()][result.getY()] = 'O';
		result = result.lastNode;
	}
	if(result!=null)
	{
		for(int k =0;k<mazeContext.length;k++) {
			for(int j = 0;j<mazeContext[k].length;j++){
				System.out.print(mazeContext[k][j]);
			}
			System.out.println();
		}
		System.out.println("Total steps: "+counter+"    Total Node Expanded: "+countN);
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


	
	//1.2 in manhatton, there are 2 cases. 2 different cost. in our heuristic, it is only one
	public static int aStarTurnCostManhatton(char[][] mazeContext,Node startNode,final Node endNode,int caseChoosing){
		DirectedNode startNodedir = new DirectedNode(startNode.getX(),startNode.getY());
		
		int forwardCost = caseChoosing == 1 ? 1 : 2;
		int turnCost = caseChoosing == 2 ? 2 : 1;
		
		ArrayList<DirectedNode> iniList = extractNodeTurnCost(mazeContext,startNodedir,forwardCost,turnCost);
		Comparator<DirectedNode> nodeCom = new Comparator<DirectedNode>() {
			public int compare(DirectedNode node1, DirectedNode node2) {
//				
				if(node1.getCost()+calcuManhattonDistance(node1.getX(),endNode.getX(),node1.getY(),endNode.getY())<node2.getCost()+calcuManhattonDistance(node2.getX(),endNode.getX(),node2.getY(),endNode.getY())) return -1;
				else if(node1.getCost()+calcuManhattonDistance(node1.getX(),endNode.getX(),node1.getY(),endNode.getY())>node2.getCost()+calcuManhattonDistance(node2.getX(),endNode.getX(),node2.getY(),endNode.getY())) return 1;

				return 0;
			}
		};
		PriorityQueue<DirectedNode> mazeLocaQue = new PriorityQueue<DirectedNode>(iniList.size(),nodeCom);
		for(DirectedNode node:iniList)
			mazeLocaQue.offer(node);
		DirectedNode result = mazeLocaQue.poll();
		while(calcuManhattonDistance(result.getX(),endNode.getX(),result.getY(),endNode.getY())>1){
			mazeContext[result.getX()][result.getY()] = '.';
			System.out.println((result.getX()+1)+" "+(result.getY()+1));
			/*
			for(int k =0;k<mazeContext.length;k++) {
				for(int j = 0;j<mazeContext[k].length;j++){
					System.out.print(mazeContext[k][j]);
				}
				System.out.println();
			}
			*/
			//Thread.sleep(200);
			ArrayList<DirectedNode> cnodes = extractNodeTurnCost(mazeContext,result,forwardCost, turnCost);
			for(DirectedNode node:cnodes)
				mazeLocaQue.offer(node);
			result = mazeLocaQue.poll();
		}
		mazeContext[result.getX()][result.getY()] = '.';
		System.out.println((result.getX()+1)+" "+(result.getY()+1));
		
		for(int k =0;k<mazeContext.length;k++) {
			for(int j = 0;j<mazeContext[k].length;j++){
				System.out.print(mazeContext[k][j]);
			}
			System.out.println();
		}
		
		System.out.println(result.getX()+","+(result.lastNode.getY()+1)+","+result.lastNode.getDirection()+","+result.getCost());
		while(result.lastNode != null){
			System.out.println((result.lastNode.getX()+1)+","+(result.lastNode.getY()+1)+","+result.lastNode.getDirection());
			result = result.lastNode;
			return 1;
		}
		return 0;
	}
	//our heuristic
	public static int aStarTurnCostOurHeuristic(char[][] mazeContext,Node startNode,final Node endNode,int caseChoosing){
		DirectedNode startNodedir = new DirectedNode(startNode.getX(),startNode.getY());
		
		int forwardCost = caseChoosing == 1 ? 1 : 2;
		int turnCost = caseChoosing == 2 ? 2 : 1;		
		
		ArrayList<DirectedNode> iniList = extractNodeTurnCost(mazeContext,startNodedir,forwardCost,turnCost);
		Comparator<DirectedNode> nodeCom = new Comparator<DirectedNode>() {
			public int compare(DirectedNode node1, DirectedNode node2) {
				if(node1.getCost()+turnHeuristic(node1,endNode.getX(),endNode.getY(),forwardCost,turnCost)<node2.getCost()+turnHeuristic(node2,endNode.getX(),endNode.getY(),forwardCost,turnCost)) return -1;
				else if(node1.getCost()+turnHeuristic(node1,endNode.getX(),endNode.getY(),forwardCost,turnCost)>node2.getCost()+turnHeuristic(node2,endNode.getX(),endNode.getY(),forwardCost,turnCost)) return 1;
				
				return 0;
			}
		};
		PriorityQueue<DirectedNode> mazeLocaQue = new PriorityQueue<DirectedNode>(iniList.size(),nodeCom);
		for(DirectedNode node:iniList)
			mazeLocaQue.offer(node);
		DirectedNode result = mazeLocaQue.poll();
		while(calcuManhattonDistance(result.getX(),endNode.getX(),result.getY(),endNode.getY())>1){
			mazeContext[result.getX()][result.getY()] = '.';
			System.out.println((result.getX()+1)+" "+(result.getY()+1));
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
			for(DirectedNode node:cnodes)
				mazeLocaQue.offer(node);
			result = mazeLocaQue.poll();
		}
		mazeContext[result.getX()][result.getY()] = '.';
		System.out.println((result.getX()+1)+" "+(result.getY()+1));
		
		for(int k =0;k<mazeContext.length;k++) {
			for(int j = 0;j<mazeContext[k].length;j++){
				System.out.print(mazeContext[k][j]);
			}
			System.out.println();
		}
		
		System.out.println(result.getX()+","+(result.lastNode.getY()+1)+","+result.lastNode.getDirection()+","+result.getCost());
		while(result.lastNode != null){
			System.out.println((result.lastNode.getX()+1)+","+(result.lastNode.getY()+1)+","+result.lastNode.getDirection());
			result = result.lastNode;
			return 1;
		}
		return 0;
	}
	public static ArrayList<DirectedNode> extractNodeTurnCost(char[][]mazeContext, DirectedNode node, int forwardCost, int turnCost){
		ArrayList<DirectedNode> resultList = new ArrayList<>();
		if (mazeContext[node.getX()+1][node.getY()]==' ') resultList.add(new DirectedNode(node.getX()+1,node.getY(),node.getCost()+directionDiff('s',node.getDirection())*turnCost+forwardCost,node,'s',node.getTurns()+directionDiff('s',node.getDirection())));
		if (mazeContext[node.getX()-1][node.getY()]==' ') resultList.add(new DirectedNode(node.getX()-1,node.getY(),node.getCost()+directionDiff('n',node.getDirection())*turnCost+forwardCost,node,'n',node.getTurns()+directionDiff('n',node.getDirection())));
		if (mazeContext[node.getX()][node.getY()+1]==' ') resultList.add(new DirectedNode(node.getX(),node.getY()+1,node.getCost()+directionDiff('e',node.getDirection())*turnCost+forwardCost,node,'e',node.getTurns()+directionDiff('e',node.getDirection())));
		if (mazeContext[node.getX()][node.getY()-1]==' ') resultList.add(new DirectedNode(node.getX(),node.getY()-1,node.getCost()+directionDiff('w',node.getDirection())*turnCost+forwardCost,node,'w',node.getTurns()+directionDiff('w',node.getDirection())));
		return resultList;
	}
	
	//1.3   search the map with ghost
	public static int aStarwGhost(char[][] mazeContext,Node startNode,final Node endNode) throws InterruptedException{
		char[][] mazecopy = mazeContext.clone();
		ArrayList<int[]> ghostPath = new ArrayList<>();
		int countN = 0;
		int currentGPos = 0;
		for(int i=0;i<mazeContext.length;i++)
			for(int j=0;j<mazeContext[i].length;j++){
				if (mazeContext[i][j]=='g'){
					int[] path = {i,j};
					ghostPath.add(path);
				} else if (mazeContext[i][j]=='G'){
					int[] path = {i,j};
					ghostPath.add(path);
					currentGPos = ghostPath.indexOf(path);
				}
			}
		Ghost ghost = new Ghost(ghostPath,currentGPos);
		ArrayList<Node> iniList = extractNodewGhost(mazeContext,startNode,ghost);
		countN++;
		Comparator<Node> nodeCom = new Comparator<Node>() {
			public int compare(Node node1, Node node2) {
				if(node1.getCost()+calcuManhattonDistance(node1.getX(),endNode.getX(),node1.getY(),endNode.getY())<node2.getCost()+calcuManhattonDistance(node2.getX(),endNode.getX(),node2.getY(),endNode.getY())) return -1;
				else if(node1.getCost()+calcuManhattonDistance(node1.getX(),endNode.getX(),node1.getY(),endNode.getY())>node2.getCost()+calcuManhattonDistance(node2.getX(),endNode.getX(),node2.getY(),endNode.getY())) return 1;
				return 0;
			}
		};
		PriorityQueue<Node> mazeLocaQue = new PriorityQueue<Node>(iniList.size(),nodeCom);
		for(Node node:iniList)
		{
			mazeLocaQue.offer(node);
		}
		Node resultN = mazeLocaQue.poll();
		while(calcuManhattonDistance(resultN.getX(),endNode.getX(),resultN.getY(),endNode.getY())>1){
			System.out.println("ccccc  "+resultN.getX()+ "    bvvvbb : "+resultN.getY()+"   ENDNODE: "+endNode.getX()+"   "+endNode.getY());
			ArrayList<Node> cnodes = extractNodewGhost(mazeContext,resultN,ghost);
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
		System.out.println("asdasdsa");
		counter++;
		path.add(resultN.lastNode);
		resultN = resultN.lastNode;
	}
	for(int i=0;i<mazecopy.length;i++)
	{
		for(int j=0;j<mazecopy[i].length;j++)
		{
			System.out.println(mazecopy[i][j]);
			if (mazecopy[i][j]=='g'||mazecopy[i][j]=='G') mazecopy[i][j] = ' ';
		}
	}
	Collections.reverse(path);
	for(Node player: path){
		int[] gpos = ghost.posAtTurn(player.getCost());
		mazecopy[gpos[0]][gpos[1]] = 'G';
		mazecopy[player.getX()][player.getY()] = 'P';
		for(int i=0;i<mazecopy.length;i++){
			for(int j=0;j<mazecopy[i].length;j++)
				System.out.print(mazecopy[i][j]);
			System.out.println();
		}
		mazecopy[gpos[0]][gpos[1]] = ' ';
		mazecopy[player.getX()][player.getY()] = ' ';
		Thread.sleep(1000);
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
//extract node node walls .find path
public static ArrayList<Node> extractNodewGhost(char[][]mazeContext, Node node, Ghost ghost){
	ArrayList<Node> resultList = new ArrayList<>();
	int[] ghostPosition = ghost.posAtTurn(node.getCost());
	if(node.getX()==ghostPosition[0]&&node.getY()==ghostPosition[1]) return resultList;
	if (mazeContext[node.getX()+1][node.getY()]!='%'&&!(node.getX()+1==ghostPosition[0]&&node.getY()==ghostPosition[1])) resultList.add(new Node(node.getX()+1,node.getY(),node.getCost()+1,node));
	if (mazeContext[node.getX()-1][node.getY()]!='%'&&!(node.getX()-1==ghostPosition[0]&&node.getY()==ghostPosition[1])) resultList.add(new Node(node.getX()-1,node.getY(),node.getCost()+1,node));
	if (mazeContext[node.getX()][node.getY()+1]!='%'&&!(node.getX()==ghostPosition[0]&&node.getY()+1==ghostPosition[1])) resultList.add(new Node(node.getX(),node.getY()+1,node.getCost()+1,node));
	if (mazeContext[node.getX()][node.getY()-1]!='%'&&!(node.getX()==ghostPosition[0]&&node.getY()-1==ghostPosition[1])) resultList.add(new Node(node.getX(),node.getY()-1,node.getCost()+1,node));
	return resultList;
}
public static int turnHeuristic(DirectedNode node, int gx, int gy,int forwardcost,int turncost){

	int turnsChange = node.getTurns()-node.lastNode.getTurns();
	return calcuManhattonDistance(node.getX(),gx,node.getY(),gy)*forwardcost+turnsChange*turncost;

}
public static void clearScreen(){
	for(int i=0;i<100;i++) System.out.println();
}
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

	
	
}
