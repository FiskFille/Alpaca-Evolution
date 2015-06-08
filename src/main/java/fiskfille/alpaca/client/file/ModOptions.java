package fiskfille.alpaca.client.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Arrays;

public class ModOptions
{
	public static final File file = new File("alpaca-options.txt");
	public static boolean forceRender = false;
	
	public static void save()
	{
		try
		{
			if (!file.exists())
			{
				file.createNewFile();
			}
			
			PrintWriter out = new PrintWriter(file);
			out.println("force-render:" + forceRender);
			out.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void load()
	{
		try
		{
			@SuppressWarnings("resource")
			BufferedReader input = new BufferedReader(new FileReader(file));
			String s = "";
			
			while ((s = input.readLine()) != null)
			{
                String[] astring = s.split(":");
                
                if (astring[0].equals("force-render"))
                {
                	forceRender = Boolean.valueOf(astring[1]);
                }
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}