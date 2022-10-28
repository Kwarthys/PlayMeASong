package pms.notes;

import java.util.ArrayList;

import pms.utils.Keyframe;

public class LinearEnvelope
{	
	private ArrayList<Keyframe> keyframes = new ArrayList<>();

	public void addKeyframe(double time, double value)
	{
		addKeyframe(new Keyframe(time, value));
	}
	
	public void addKeyframe(Keyframe k)
	{
		for(int i = 0; i < keyframes.size(); ++i)
		{
			if(k.time == keyframes.get(i).time)
			{
				keyframes.set(i, k);
				return;
			}

			if(keyframes.get(i).time > k.time)
			{
				keyframes.add(i, k);
				return;
			}
		}

		keyframes.add(k);
	}

	public double evaluate(double time)
	{
		if(keyframes.size() < 2)
		{
			System.out.println("Not enough point(s) to interpolate. " + keyframes.size() + "points.");
			return 0;
		}

		if(time < keyframes.get(0).time)
		{
			System.out.println("Evaluate (" + time + ") not between keyframes (before first one)");
			return 0;
		}

		for(int ki = 1; ki < keyframes.size(); ++ki)
		{
			if(time <= keyframes.get(ki).time)
			{
				return interpolateBetween(time, keyframes.get(ki-1), keyframes.get(ki));
			}
		}

		System.out.println("Evaluate (" + time + ") not between keyframes (after last one)");
		return 0;		
	}


	private double interpolateBetween(double time, Keyframe a, Keyframe b)
	{		
		return (a.value * (b.time - time) + b.value * (time - a.time)) / (b.time - a.time);
	}


}


