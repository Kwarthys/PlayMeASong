package pms.utils;

public class Utils
{
	public static double lerp(double a, double b, double t)
	{	
		return a * (1 - t) + b * (t - 0);
	}
	
	
}
