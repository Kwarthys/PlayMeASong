package pms.generation.builders;

import pms.notes.Instrument;
import pms.notes.NoteCommand;
import pms.notes.instruments.DrumSnare;
import pms.play.Timeline;
import pms.utils.RangeSelector;

public class DrumSnareBuilder extends InstrumentLineBuilder
{
	public DrumSnareBuilder(Timeline timeline, double volume)
	{
		super(timeline, new RangeSelector(), volume);
	}	

	@Override
	public Instrument getInstrument() { return new DrumSnare(); }
	
	
									 //1    &    2    &    3  &    4    &
	private double[] drumSnareProba = {0.2, 0.2, 0.2, 0.2, 1, 0.2, 0.2, 0.2}; 
	private int snareFrequencyIndex = 10;
	
	public void buildBar(int startStep)
	{
		for(int i = 0; i < 8; ++i)
		{
			if(Math.random() < drumSnareProba[i])
			{
				track.addNote(new NoteCommand(startStep + i, 1, snareFrequencyIndex));
				track.addNote(new NoteCommand(startStep + i + 8, 1, snareFrequencyIndex));
				track.addNote(new NoteCommand(startStep + i + 2*8, 1, snareFrequencyIndex));
				track.addNote(new NoteCommand(startStep + i + 3*8, 1, snareFrequencyIndex));
				track.addNote(new NoteCommand(startStep + i + 4*8, 1, snareFrequencyIndex));
				track.addNote(new NoteCommand(startStep + i + 5*8, 1, snareFrequencyIndex));
				track.addNote(new NoteCommand(startStep + i + 6*8, 1, snareFrequencyIndex));

				System.out.print((i+1) + " ");
			}
		}
		
		System.out.print(" - ");

		for(int i = 0; i < 8; ++i)
		{
			//break
			if(Math.random() < drumSnareProba[i] + (1-drumSnareProba[i])*0.2)
			{
				
				{
					track.addNote(new NoteCommand(startStep + i + 7*8, 1, snareFrequencyIndex));
					System.out.print((i+1) + " ");
				}
			}
		}
		System.out.println();
	}	
}
