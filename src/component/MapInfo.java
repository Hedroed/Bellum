package component;

import java.io.*;

public class MapInfo {
	
	private boolean error;
	
	private String name;
	private int width, height;
	private int players;
	
	public MapInfo(String fileName) {
		
		BufferedReader in = null;
		
		try {
			in = new BufferedReader(new FileReader(fileName));
			name = in.readLine();
			
			//recuperation de la taille du damier
			String s = in.readLine();
			width = Integer.parseInt(s);
			s = in.readLine();
			height = Integer.parseInt(s);
			
			s = in.readLine();
			players = Integer.parseInt(s);
			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getPlayers() {
		return this.players;
	}
	
}