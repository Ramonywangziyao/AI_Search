import java.util.ArrayList;

public class Ghost {
	private ArrayList<int[]> ghostPath;
	private final int iniPosition;
	private int position;
	public Ghost(ArrayList<int[]> gpath, int inipos){
		ghostPath = gpath;
		iniPosition = inipos;
	}

	public int[] posAtTurn(int turn){
		position = iniPosition;
		int operator = (position==0) ? 1: -1;
		for (int i=0;i<turn;i++){
			position += operator;
			if (position==0||position==ghostPath.size()-1) operator *= -1;
		}
		return ghostPath.get(position);
	}
}
