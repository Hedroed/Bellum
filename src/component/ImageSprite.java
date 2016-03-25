package component;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageSprite {
	
	public static int tileSize = 46; 
	public static Image[] explosion = createSprite(45,45,9,9,"ressources/explosion.png");
	public static Image[] mapSprite = createSprite(65,65,2,6,"ressources/spriteMap.png");
	
	public static Image[] createSprite(int w, int h,int r, int c, String filePath) {
		
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
}