package pms.generation.builders;

import pms.notes.Instrument;
import pms.notes.NoteCommand;
import pms.notes.instruments.DrumKickSustain;
import pms.notes.instruments.DrumKickAttack;
import pms.play.Timeline;
import pms.play.Track;
import pms.utils.RangeSelector;

public class DrumKickBuilder extends InstrumentLineBuilder
{
	private Track attackTrack;
	
	public DrumKickBuilder(Timeline timeline, double volume)
	{
		super(timeline, new RangeSelector(), volume);
		
		attackTrack = new Track(new DrumKickAttack());
		timeline.tracks.add(attackTrack);
		
		attackTrack.setTrackVolume(1);
	}	

	@Override
	public Instrument getInstrument() { return new DrumKickSustain(); }

	private double[] drumkickProba = {1, 0.2, 0.5, 0.2, 0.5, 0.2, 0.5, 0.3}; 
	private int kickFrequencyIndex = 0;

	public void buildBar(int startStep)
	{		
		for(int i = 0; i < 8; ++i)
		{
			if(Math.random() < drumkickProba[i])
			{
				for(int ni = 0; ni < 7; ni++)
				{
					NoteCommand n = new NoteCommand(startStep + i + ni*8, 1, kickFrequencyIndex);
					track.addNote(n);
					attackTrack.addNote(n);
				}
				/*
				track.addNote(new NoteCommand(startStep + i + 8, 1, kickFrequency));
				track.addNote(new NoteCommand(startStep + i + 2*8, 1, kickFrequency));
				track.addNote(new NoteCommand(startStep + i + 3*8, 1, kickFrequency));
				track.addNote(new NoteCommand(startStep + i + 4*8, 1, kickFrequency));
				track.addNote(new NoteCommand(startStep + i + 5*8, 1, kickFrequency));
				track.addNote(new NoteCommand(startStep + i + 6*8, 1, kickFrequency));
				*/

				System.out.print((i+1) + " ");
			}
		}
		
		System.out.print(" - ");

		for(int i = 0; i < 8; ++i)
		{
			//break
			if(Math.random() < drumkickProba[i] + (1-drumkickProba[i])*0.2)
			{
				{
					track.addNote(new NoteCommand(startStep + i + 7*8, 1, kickFrequencyIndex));
					System.out.print((i+1) + " ");
				}
			}
		}
		
		System.out.println();
	}
}
