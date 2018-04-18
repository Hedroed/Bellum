package com.hedroed.bellum.main;

import com.hedroed.bellum.component.*;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Font;
import java.io.*;
import java.awt.event.*;

public class MapSelecterState extends JPanel implements MouseListener{
	
	private String[] mapList;
	private File currentMap;
	private int currentInd;
	private int nbPlayers;
	
	private HashMap<String, ArrayList<TransferVec>> listVehicules;
	private int indTV;
	private String[] keysTV;
	
	private File mapDirectory;
	private ArrayList<File> mapFiles;
	private MapInfo info;
	
	private Fenetre fenetre;
	
	private Image marbre;
	private Image img;
	private Font f1;
	
	public MapSelecterState(Fenetre f) {
		this.fenetre = f;
		this.mapFiles = new ArrayList<File>();
		
		mapDirectory = new File("maps");
		this.addMouseListener(this);
		
		try {
			marbre = ImageIO.read(new File("ressources/interface.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
        try {
            f1 = Font.createFont(Font.PLAIN, new File("ressources/DALEK.ttf"));
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		refresh();
	}
	
	public void refresh() {
		File[] tab = mapDirectory.listFiles();
		this.mapFiles.clear();
		
		for(File f : tab) {
			if(isMap(f)) {
				this.mapFiles.add(f);
			}
		}
		
		selectMap(0);
	}
	
	public void selectMap(int i) {
		if(i >= 0 && i < mapFiles.size()) {
			currentMap = mapFiles.get(i);
			currentInd = i;
			img = null;
			info = new MapInfo("maps/"+currentMap.getName()+"/map.map");

			try {
				img = ImageIO.read(new File("maps/"+currentMap.getName()+"/img.png"));
			} catch (IOException ioe) {
				// ioe.printStackTrace();
				System.out.println("Le fichier image n'existe pas");
				try {
					img = ImageIO.read(new File("ressources/defaultImage.png"));
				}
				catch(IOException e) {
					e.printStackTrace();
				}
			}
			try {
				ResourcesLoader rl = new ResourcesLoader("maps/"+currentMap.getName()+"/resources");
				listVehicules = rl.getResourcesVehicules();
				
				keysTV = new String[listVehicules.keySet().size()];
				keysTV = listVehicules.keySet().toArray(keysTV);
				indTV = 0;
				
			} catch (IOException ioe) {
				// ioe.printStackTrace();
				System.out.println("Le fichier resources vehicules n'existe pas");
				listVehicules = null;
				keysTV = null;
			}
		}
	}
	
	public void paintComponent(Graphics g) {
		int w = getWidth();
		int h = getHeight();
		int center = w/2;
		
		//generate background
		g.drawImage(ImageSprite.menuBackground,0,0,w,h,null);
		g.drawImage(marbre,center-340,0,680,h,null);
		
		g.setColor(new Color(100,50,8));
		g.drawLine(center,80,center,h);
		g.drawLine(0,h-1,w-1,h-1);
		
		g.setColor(new Color(255,235,175));
		g.fillRect(center-270,90,240,h-150);
		
		
		//draw left text
		f1 = f1.deriveFont(55f);
		g.setColor(new Color(128,0,0));
		g.setFont(f1);
		g.drawString("Map :",center-60,60);
		f1 = f1.deriveFont(16f);
		g.setFont(f1);
		
		int i = 0;
		for(File f : mapFiles) {

			if(f == currentMap) {
				g.setColor(new Color(223,118,11));
				g.fillRect(center-270,98+(i*30),240,30);
			}
			g.setColor(new Color(128,0,0));
			g.drawString(f.getName(),center-(150+(f.getName().length()*6)),120+(i*30));
			i++;
		}
		
		f1 = f1.deriveFont(20f);
		g.setFont(f1);
		
		//refresh and back buttun
		g.setColor(new Color(223,118,11));
		g.fillRect(center-280,h-45,260,30);
		g.fillRect(center+20,h-45,260,30);
		g.setColor(new Color(128,0,0));
		g.drawString("Refresh",center-190,h-22);
		g.drawString("Back",center+130,h-22);

		
		//draw right text
		if(currentMap != null) {
			
			if(img != null) {
				g.drawImage(img,center+10,90,256,256,null);
			}
			
			g.drawString("Name : "+info.getName(),center+10,400);
			nbPlayers = info.getPlayers();
			g.drawString("Players : "+nbPlayers,center+10,430);
			g.drawString("Size : "+info.getWidth()+" x "+info.getHeight(),center+10,460);
			
			//time selecter
			g.setColor(new Color(223,118,11));
			g.fillRect(center+50,h-125,200,30);
			g.fillRect(center+20,h-125,20,30);
			g.fillRect(center+260,h-125,20,30);
			g.setColor(new Color(128,0,0));
			if(keysTV != null) {
				g.drawString(keysTV[indTV],center+110,h-102);
				g.drawString("<",center+27,h-102);
				g.drawString(">",center+267,h-102);
			}
			else {
				g.drawString("Normal",center+110,h-102);
			}
			
			
			//bellum buttun
			g.setColor(new Color(223,118,11));
			g.fillRect(center+20,h-85,260,30);
			g.setColor(new Color(128,0,0));
			g.drawString("Start !",center+114,h-62);
		}
		
	}
	
	private boolean isMap(File dir) {
		boolean ret = false;
		
		if(dir.isDirectory()) {
			File[] tab = dir.listFiles();
			for(File f : tab) {
				if(f.getName().equals("map.map")) {
					ret = true;
				}
			}
		}
		return ret;
	}
	
	// private boolean fileExist(String s) {
		// File[] tab = mapDirectory.listFiles();
		// boolean ret = false;
		
		// for(File f : tab) {
			// if(f.getName().equals(s)) {
				// ret = true;
				// break;
			// }
		// }
		// return ret;
	// }
	
	// private String getPartName(String s) {
		// int dot = s.lastIndexOf('.');
		// s = s.substring(0,dot);
		// return s;
	// }
	
	public String getMap() {
		return currentMap.getName()+"/map.map";
	}
	
	public ArrayList<TransferVec> getResources() {
		ArrayList<TransferVec> ret = null;
		
		if(listVehicules != null) {
			ret = listVehicules.get(keysTV[indTV]);
		}
		
		return ret;
	}
	
	public int getNbPlayers() {
		return nbPlayers;
	}
	
	//MouseListener
	public void mouseClicked(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {
		int left = (getWidth()/2)-270;
		int right = (getWidth()/2)+20;
		int h = getHeight();
		int center = getWidth()/2;
		
		if(e.getButton() == MouseEvent.BUTTON1) {

			double yMap = (double) (e.getY()-98)/30;
			// System.out.println("Click y :"+(int)yMap);
			int y = e.getY();
			int x = e.getX();
			
			if(x >= left && x < left+240) {
				if(yMap >= 0 && yMap < mapFiles.size()) {
					
					selectMap((int)yMap);
				}
				
				if(y >= h-45 && y < h-15) {
					refresh();
				}
			}
			else if(x >= right && x < right+260) {	
				if(y >= h-85 && y < h-55 && currentMap != null) {
					//click valid button
					fenetre.goPlayer();
				}
				else if(y >= h-45 && y < h-15) {
					//click back button
					fenetre.goMenu();
				}
				else if(y >= h-125 && y < h-95) {
					if(x < center+50) {
						// System.out.println("button <");
						if(indTV > 0) {
							indTV--;
						}
						else {
							indTV = keysTV.length-1;
						}
					}
					else if(x < center+280) {
						// System.out.println("button >");
						if(indTV < keysTV.length-1) {
							indTV++;
						}
						else {
							indTV = 0;
						}
					}
				}
			}
			
		}
	}
	
	public void keyPressed(int keyCode) {
		
		if(keyCode == KeyEvent.VK_DOWN) {
			
			if(currentInd < mapFiles.size()-1) {
				currentInd++;
				selectMap(currentInd);
			}
		}
		else if(keyCode == KeyEvent.VK_UP) {
			if(currentInd > 0) {
				currentInd--;
				selectMap(currentInd);
			}
		}
		else if(keyCode == KeyEvent.VK_ENTER) {
			fenetre.goPlayer();
		}
		
	}
	
}