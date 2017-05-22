package Utilities;

import java.io.File;
import java.util.Random;
import java.io.IOException;
import java.awt.image.BufferedImage;


public class RandomUtilities 
{
	public static String GenerateString()
	{
		Random rng = new Random();
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		int length = new Random().nextInt(15);
		
	    char[] text = new char[length];
	    for (int i = 0; i < length; i++)
	    {
	        text[i] = characters.charAt(rng.nextInt(characters.length()));
	    }
	    return new String(text);
	}
	
	public static String GenerateString(int max)
	{
		Random rng = new Random();
		String characters = " ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		int length = new Random().nextInt(max);
		
	    char[] text = new char[length];
	    for (int i = 0; i < length; i++)
	    {
	        text[i] = characters.charAt(rng.nextInt(characters.length()));
	    }
	    return new String(text);
	}
	

	public static String GenerateString(String characters, int min, int max)
	{
		Random rng = new Random();
		int length = new Random().nextInt(max) + min;
		
		
	    char[] text = new char[length];
	    for (int i = 0; i < length; i++)
	    {
	        text[i] = characters.charAt(rng.nextInt(characters.length()));
	    }
	    return new String(text);
	}
	

	public static String GenerateStringExt(int max)
	{
		return GenerateString(" \n-.,ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz", 1, max);
	}
	
	public static String GenerateRndImage(int w, int h)
	{
		BufferedImage bf = new BufferedImage(w, h , BufferedImage.TYPE_INT_RGB);
		for (int y = 0; y < h; y++) 
		{
			for (int x = 0; x < w; x++) 
			{
				int rgb = new Random().nextInt(256);
				bf.setRGB(x, y, rgb);
			}
		}
		
		File tmpImg = null;
		try {
			tmpImg = File.createTempFile("tmp-img", ".png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tmpImg.getAbsolutePath();
		
	}
}
