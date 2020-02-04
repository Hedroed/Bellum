package com.hedroed.bellum.component;

import com.hedroed.bellum.main.*;

import java.awt.Graphics;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Color;
import java.awt.Image;
import java.io.InputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GameMessage {
	
	private Font f1;
	private Color c1;
	private Image gameInterface;
	
	public GameMessage(String fontName, String interfaceName, Color mainColor) {
		this.c1 = mainColor;
		try {
            InputStream in = getClass().getResourceAsStream(fontName);
            f1 = Font.createFont(Font.PLAIN, in);
        } catch (Exception e) {
            e.printStackTrace();
        }
		try {
            InputStream in = getClass().getResourceAsStream(interfaceName);
			gameInterface = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void drawMessage(Graphics g, String message, String subMessage) {
		int centerX = FDamier.width/2;
		int centerY = FDamier.height/2;
		
		g.setColor(c1);
		g.drawImage(gameInterface,centerX-200,centerY-100,400,200,null);
		
		f1 = f1.deriveFont(65f);
		FontMetrics metrics = g.getFontMetrics(f1);
		int length = metrics.stringWidth(message);
		g.setFont(f1);
		g.drawString(message,centerX-(length/2),centerY);
		
		f1 = f1.deriveFont(35f);
		metrics = g.getFontMetrics(f1);
		length = metrics.stringWidth(subMessage);
		g.setFont(f1);
		g.drawString(subMessage,centerX-(length/2),centerY+60);
	}
}