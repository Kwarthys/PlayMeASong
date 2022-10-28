package pms.play;

import java.util.ArrayList;

import pms.notes.Instrument;
import pms.notes.NoteCommand;

public class Track
{	
	private Instrument instrument;
	
	private ArrayList<NoteCommand> notes = new ArrayList<>();
	
	private double trackVolume = 1;
	
	public void setTrackVolume(double v) {trackVolume = v;}
	public double getTrackVolume() {return trackVolume;}
	
	public Track(Instrument instrument)
	{
		this.instrument = instrument;
	}
	
	public void addNote(NoteCommand n)
	{
		if(notes.size() > 0)
		{
			for(int ni = 0; ni < notes.size(); ++ni)
			{
				if(n.timestep < notes.get(ni).timestep)
				{
					notes.add(ni, n);
					return;
				}
			}
			
			notes.add(new NoteCommand(n));
		}
		else
		{
			notes.add(new NoteCommand(n));
		}
		
		//System.out.println("Track has " + notes.size() + "notes");
	}
	
	
	public boolean hasNoteAt(int time)
	{
		return getNoteAt(time) != null;
	}
	
	private NoteCommand getNoteAt(int time)
	{
		if(notes.size() > 0)
		{
			NoteCommand n = notes.get(0);
			
			if(time >= n.timestep && time < n.timestep + n.noteDuration)
			{
				return n;
			}
			else if(time > n.timestep + n.noteDuration)
			{
				notes.remove(0);
			}
		}
		
		return null;
	}

	public double[] getDataAt(int time)
	{
		NoteCommand n = getNoteAt(time);
		
		if(n == null)
		{
			//System.out.println("no notes at " + time);
			instrument.notifySilence();
			return null;
		}
		
		if(time == n.timestep + n.noteDuration - 1)
		{
			//System.out.println("removed f" + n.frequency + " at " + time);
			notes.remove(0);
		}
		
		return instrument.playNote(n, time);
	}
}
