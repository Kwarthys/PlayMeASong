package pms.generation.builders;

import pms.generation.MelodyLine;
import pms.generation.MelodyLineParameters;
import pms.generation.MelodyLine.PatternFormat;
import pms.notes.Instrument;
import pms.notes.NoteCommand;
import pms.notes.instruments.ElectroHigh;
import pms.play.Timeline;
import pms.utils.RangeSelector;

public class LeadBuilder extends InstrumentLineBuilder
{	
	private MelodyLineParameters melodyParameters;	
	
	public LeadBuilder(Timeline timeline)
	{
		this(timeline, new RangeSelector(0.75,1), 0.8);
	}
	
	public LeadBuilder(Timeline timeline, RangeSelector rangeSelector, double trackVolume)
	{
		super(timeline, rangeSelector, trackVolume);
		
		melodyParameters = new MelodyLineParameters(rangeSelector);
		melodyParameters.registerPickChances(0.5, 0.8);
		melodyParameters.registerSilenceChances(0.3, 0.1, 0.05);
		melodyParameters.registerspreadIndecies(0.7, 0.3, 0.2);
	}

	@Override
	public Instrument getInstrument() {return new ElectroHigh();}

	@Override
	public void buildBar(int startStep)
	{
		MelodyLine[] lines = MelodyLine.generatePattern(PatternFormat.ABACABAD, lastNoteIndex, melodyParameters);

		int i = 0;
		for(MelodyLine line : lines)
		{			
			for(NoteCommand n : line.notes)
			{
				
				NoteCommand timedNote = new NoteCommand(n);				
				timedNote.timestep += startStep + i*8;
				
				track.addNote(timedNote);				

				lastNoteIndex = n.scaleIndex;
				
			}	
			++i;
		}
	}

}
