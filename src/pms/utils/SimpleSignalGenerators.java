package pms.utils;

import pms.notes.LinearEnvelope;

public class SimpleSignalGenerators
{
	public static final LinearEnvelope sawtoothEnvelope = buildSawtoothEnvelope();
	
	public static LinearEnvelope buildSawtoothEnvelope()
	{
		LinearEnvelope e = new LinearEnvelope();
		e.addKeyframe(0,-1);
		e.addKeyframe(1,1);
		
		return e;
	}
	
	public static double sawtooth(double timeInPeriod)
	{
		return sawtoothEnvelope.evaluate(timeInPeriod);
	}
	
	
	
	public static double square(double timeInPeriod, double highRatio)
	{		
		if(timeInPeriod > highRatio)
		{
			return -1;
		}
		else
		{
			return 1;
		}
	}
	
	
	public static final LinearEnvelope triangleEnvelope = buildTriangleEnvelope();
	
	public static LinearEnvelope buildTriangleEnvelope()
	{
		LinearEnvelope e = new LinearEnvelope();
		e.addKeyframe(0,-1);
		e.addKeyframe(0.5,1);
		e.addKeyframe(1,-1);
		
		return e;
	}
	
	public static double triangle(double timeInPeriod)
	{
		return triangleEnvelope.evaluate(timeInPeriod);
	}

}
