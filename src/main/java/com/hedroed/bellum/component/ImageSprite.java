package com.hedroed.bellum.component;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageSprite {
	
	public static Image[] explosion = createSprite(45,45,9,9,"ressources/explosion.png");
	public static Image[] mapSprite ;//= createSprite(65,65,2,6,"ressources/spriteMap.png");
	public static Image[] baseExplosion = createSprite(256,256,1,48,"ressources/baseExplosion.png");
	public static Image menuBackground = createImage("ressources/bg_menu.jpg");
	
	public static Image[] createSprite(int w, int h,int r, int c, String filePath) {
		System.out.println("load :"+filePath);
		
		BufferedImage image = null;
		Image[] ret = new Image[r * c];
		
		try {
			image = ImageIO.read(new File(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < r; i++)
		{
			for (int j = 0; j < c; j++)
			{
				ret[(i * c) + j] = image.getSubimage(j * w,i * h,w,h);
			}
		}
		
		return ret;
	}
	
	public static Image createImage(String filePath) {
		System.out.println("load :"+filePath);
		Image ret = null;
		try {
			ret = ImageIO.read(new File(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
}