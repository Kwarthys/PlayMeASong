package pms.notes.scale;

import java.util.ArrayList;

import pms.param.Parameters;
import pms.utils.RangeSelector;

public class Scale
{
	//MINOR
	// F     H  F     F     H  F    F
	// 1  2  3  4  5  6  7  8  9 10 11 12
	// 1  0  1  1  0  1  0  1  1 0  1  0
	private static final int minorKey[] = {1,0,1,1,0,1,0,1,1,0,1,0}; // F H F F H F F

	//MAJOR
	// F     F     H  F     F    F     H
	// 1  2  3  4  5  6  7  8  9 10 11 12
	// 1  0  1  0  1  1  0  1  0 1  0  1
	private static final int majorKey[] = {1,0,1,0,1,1,0,1,0,1,0,1}; // F H F F H F F

	public static ArrayList<Double> frequencies = build(55,4.5);

	public static ArrayList<Double> build(double startFrequency, double numberOfOctaves)
	{
		ArrayList<Double> lesNotes = new ArrayList<>();

		int size = (int)(numberOfOctaves * 12);
		double lastFrequency = startFrequency;
		for(int i = 0; i < size; ++i)
		{
			double f = lastFrequency * Math.pow(2.0, 1.0/12.0);
			if(i==0)
			{
				f = lastFrequency;
			}

			if(Parameters.MINOR_KEY)
			{
				if(minorKey[i%12] == 1)
				{
					lesNotes.add(f);				
				}
			}
			else
			{
				if(majorKey[i%12] == 1)
				{
					lesNotes.add(f);				
				}
			}			

			lastFrequency = f;
		}		

		return lesNotes;				
	}


	public static double getRandomFrequency()
	{
		return getRandomFrequency(new RangeSelector());
	}

	public static double getRandomFrequency(RangeSelector rangeSelector)
	{
		return frequencies.get(getRandomFrequencyIndex(rangeSelector));
	}


	public static int getRandomFrequencyIndex()
	{
		return getRandomFrequencyIndex(new RangeSelector());
	}

	public static int getRandomFrequencyIndex(RangeSelector rangeSelector)
	{
		return (int)((Math.random()*rangeSelector.width() + rangeSelector.from)*frequencies.size());
	}


	public static int getRandomFrequencyIndexNear(int baseIndex, double nearCoef)
	{		
		return getRandomFrequencyIndexNear(baseIndex, nearCoef, new RangeSelector());
	}

	public static int getRandomFrequencyIndexNear(int baseIndex, double nearCoef, RangeSelector rangeSelector)
	{
		//System.out.println("RandomIndexNear - baseIndex:" + baseIndex + ", nearCoef:" + nearCoef + ", range(" + rangeSelector.from + "," + rangeSelector.to+")");
		double range = frequencies.size() * rangeSelector.width() * (1-nearCoef*nearCoef);
		//System.out.println("range = " + range);
		range = range - range/2.0;

		int minIndex = (int) (baseIndex - range);
		int maxIndex = (int) (baseIndex + range);

		//System.out.println("Min : " + minIndex + " - Max: " + maxIndex);

		if(minIndex < frequencies.size() * rangeSelector.from)
		{
			int offset = (int) (frequencies.size() * rangeSelector.from - minIndex);

			minIndex += offset;
			maxIndex += offset;
			
			//System.out.println("MinModif + " + offset);
			
			if(maxIndex > frequencies.size() * rangeSelector.to)
			{
				offset = (int) (maxIndex - frequencies.size() * rangeSelector.to);
				
				maxIndex -= offset;
				//System.out.println("MinMaxModif - " + offset);
			}

			//System.out.println("MinCorrected Min : " + minIndex + " - Max: " + maxIndex);
		}
		else if(maxIndex > frequencies.size() * rangeSelector.to)
		{
			int offset = (int) (maxIndex - frequencies.size() * rangeSelector.to);

			minIndex -= offset;
			maxIndex -= offset;
			
			//System.out.println("MaxModif - " + offset);
			
			if(minIndex < frequencies.size() * rangeSelector.from)
			{
				offset = (int) (frequencies.size() * rangeSelector.from - minIndex);
				minIndex += offset;
				
				//System.out.println("MaxMinModif + " + offset);
			}
			
			//System.out.println("MaxCorrected Min : " + minIndex + " - Max: " + maxIndex);
		}


		int index = (int) (Math.random() * (maxIndex-minIndex) + minIndex);

		//System.out.println("returning " + index);

		return index;
	}
}
