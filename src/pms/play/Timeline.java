package pms.play;

import java.util.ArrayList;

import pms.param.Parameters;

public class Timeline
{
	public double mainVolume = 0.2;
	
	public ArrayList<Track> tracks;
	
	public Timeline()
	{
		 tracks = new ArrayList<>();
	}
		
	public byte[] getDataAtTime(int time)
	{
		int length = (int)(Parameters.SAMPLE_RATE * Parameters.SECONDS_BY_TIMESTEP);

		double tempData[] = new double[length];
		byte data[] = new byte[length];
		
		for(int ti = 0; ti < tracks.size(); ++ti)
		{
			double[] trackData = tracks.get(ti).getDataAt(time);
			
			if(trackData != null)
			{
				for(int i = 0; i < length; ++i)
				{
					tempData[i] += trackData[i] * tracks.get(ti).getTrackVolume();
				}				
			}			
		}
		
		for(int i = 0; i < data.length; ++i)
		{
			double value = tempData[i] * mainVolume;
			if(value > 126) data[i] = 126;
			else if(value < -126) data[i] = -126;
			else data[i] = (byte)value;
		}
		
		return data;
	}
}
