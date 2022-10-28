package pms.generation.builders;

import pms.generation.MelodyLine;
import pms.generation.MelodyLineParameters;
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
		MelodyLine baseLine = null;
		
		for(int i = 0; i < 8; i++)
		{
			MelodyLine line;
			
			if(baseLine == null)
			{
				baseLine = MelodyLine.generateANewLine(lastNoteIndex, melodyParameters);
				line = baseLine;
			}
			else if(i < 4)
			{
				line = MelodyLine.createVariation(melodyParameters, baseLine);
			}
			else if(i < 6)
			{
				line = MelodyLine.createVariation(melodyParameters, baseLine, 2);
			}
			else
			{
				line = MelodyLine.createVariation(melodyParameters, baseLine, 4);
			}
			
			//System.out.println(line);
			
			for(NoteCommand n : line.notes)
			{
				//System.out.print(n.timestep + " - - ");
				
				NoteCommand timedNote = new NoteCommand(n);				
				timedNote.timestep += startStep + i*8;
				
				//System.out.println(n.timestep + " to " + (n.noteDuration + n.timestep));
				
				track.addNote(timedNote);				

				lastNoteIndex = n.scaleIndex;
			}	
		}
	}

}
