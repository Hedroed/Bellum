package main;

import component.MapInfo;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.io.*;
import java.awt.event.*;

public class MapSelecterState extends JPanel implements MouseListener{
	
	private String[] mapList;
	private File currentMap;
	private int nbPlayers;
	
	private File mapDirectory;
	private File[] mapFiles;
	
	private Fenetre fenetre;
	
	
	public MapSelecterState(Fenetre f) {
		this.fenetre = f;
		
		mapDirectory = new File("maps");
		this.addMouseListener(this);
		
		refresh();
	}
	
	public void refresh() {
		mapFiles = mapDirectory.listFiles();
		currentMap = mapFiles[0];
	}
	
	public void paintComponent(Graphics g) {
		int w = getWidth();
		int h = getHeight();
		int center = w/2;
		
		//generate background
		g.setColor(new Color(255,235,175));
		g.fillRect(0,0,w,h);
		
		g.setColor(new Color(116,60,8));
		g.fillRect(0,0,w,50);
		
		g.setColor(new Color(246,182,30));
		g.fillRect(center-300,50,600,h-50);
		g.setColor(new Color(100,50,8));
		g.drawLine(center,50,center,h);
		g.drawLine(0,49,w,49);
		g.drawLine(0,h-1,w-1,h-1);
		
		g.setColor(new Color(255,235,175));
		g.fillRect(center-270,90,240,h-150);
		
		
		//draw left text
		Font f1 = null;
        try {
            f1 = Font.createFont(Font.TRUETYPE_FONT, new File("ressources/DALEK.ttf"));
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		f1 = f1.deriveFont(20f);
		g.setColor(new Color(128,0,0));
		g.setFont(f1);
		g.drawString("Map :",center-180,80);
		f1 = f1.deriveFont(16f);
		g.setFont(f1);
		
		int i = 0;
		for(File f : mapFiles) {
			if(isMap(f.getName())) {
				if(f == currentMap) {
					g.setColor(new Color(223,118,11));
					g.fillRect(center-270,98+(i*30),240,30);
				}
				g.setColor(new Color(128,0,0));
				g.drawString(f.getName(),center-(150+(f.getName().length()*6)),120+(i*30));
				i++;
			}
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
			
			
			MapInfo info = new MapInfo("maps/"+currentMap.getName());
			g.drawString("Name : "+info.getName(),center+10,400);
			nbPlayers = info.getPlayers();
			g.drawString("Players : "+nbPlayers,center+10,430);
			g.drawString("Size : "+info.getWidth()+" x "+info.getHeight(),center+10,460);
		
			//refresh and back buttun
			g.setColor(new Color(223,118,11));
			g.fillRect(center+20,h-85,260,30);
			g.setColor(new Color(128,0,0));
			g.drawString("Bellum !",center+114,h-62);
		}
		
	}
	
	private boolean isMap(String s) {
		int dot = s.lastIndexOf('.');
		s = s.substring(dot+1);
		return s.equals("map");
	}
	
	public String getMap() {
		return currentMap.getName();
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
		int right = (getWidth()/2)+30;
		int h = getHeight();
		
		if(e.getButton() == MouseEvent.BUTTON1) {

			double yMap = (double) (e.getY()-98)/30;
			// System.out.println("Click y :"+(int)yMap);
			int y = e.getY();
			int x = e.getX();
			
			if(x >= left && x < left+240) {
				if(yMap >= 0 && yMap < mapFiles.length) {
					currentMap = mapFiles[(int)yMap];
				}
				
				if(y >= h-45 && y < h-15) {
					refresh();
				}
				repaint();
			}
			else if(x >= right && x < right+240) {	
				if(y >= h-85 && y < h-55 && currentMap != null) {
					//click valid button
					System.out.println("OK !!");
					fenetre.mapNext();
				}
				else if(y >= h-45 && y < h-15) {
					//click back button
					System.out.println("Back !!");
					fenetre.mapBack();
				}
				repaint();
			}
		}
	}
	
}