import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.ImageIcon;


public class MazeMap {
	private Scanner m;
	//private String Map[] = new String[24];
	private char [][] mazeMap;;
	private Image pacman,wall,walkedNode,paceNode,goal,ghost,front;
	char [][] maze = null;
	int row;
	int cols;
	BufferedReader bufferNew;
	
	
	
	//********important **************
	//PATH EDITING FOR FILE SCAN
	//modify the file here to test three maps for ghost search
	String fileName = "src/bigMaze.txt";
	
	
	
	
	public MazeMap() throws IOException
	{
		ImageIcon img = new ImageIcon("src/pacman.png");
		pacman = img.getImage();
		img = new ImageIcon("src/wall.png");
		wall = img.getImage();
		img = new ImageIcon("src/walkedNode.png");
		walkedNode = img.getImage();
		img = new ImageIcon("src/walkedPath.png");
		paceNode = img.getImage();
		img = new ImageIcon("src/goal.png");
		goal = img.getImage();
		img = new ImageIcon("src/ghost.png");
		ghost = img.getImage();
		img = new ImageIcon("src/front.png");
		front = img.getImage();
		int totalCount = openFileCount();
		closeFile();
		openFile();
		readFileS(totalCount);
		closeFile();
		
	}
	public Image getPac()
	{
		return pacman;
	}
	public Image getFront()
	{
		return front;
	}
	public Image getGhost()
	{
		return ghost;
	}
	public Image getGoal()
	{
		return goal;
	}
	public Image getWall()
	{
		return wall;
	}
	public Image getNode()
	{
		return walkedNode;
	}
	public Image getPath()
	{
		return paceNode;
	}
	public char[][] getMap()
	{
		
		return maze;
	}
	public int openFileCount() throws IOException
	{
		FileReader fr = new FileReader(fileName);
		BufferedReader buffer = new BufferedReader (fr);

		char [][] maze = null;
		String line;
		int x = 0;
		int count = 0;

		//total rows
		while((line = buffer.readLine())!=null)
		{
	
			count++;
		}
		fr.close();
		return count;
	}
	public void openFile() throws IOException
	{
		
		FileReader fr2 = new FileReader(fileName);
		bufferNew = new BufferedReader (fr2);
		
	}
	public void readFileS(int count) throws IOException
	{
		int size = 0;
		int x = 0;
		String line;
		int theCount = count;
		
		while((line = bufferNew.readLine())!=null)
		{
			char [] vals = line.toCharArray();
            if (maze == null) {
                size = vals.length;
                maze = new char[theCount][size];
            }
            row = size;
            cols = count;
            for (int col = 0; col < size; col++) {
                maze[x][col] = vals[col];
                System.out.print(maze[x][col]);
            }
            x++;
            System.out.println();
		}	
	
	}
	
	public void closeFile()
	{
		
	}
}
