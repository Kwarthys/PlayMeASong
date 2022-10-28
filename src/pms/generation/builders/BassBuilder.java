package pms.generation.builders;

import pms.generation.MelodyLine;
import pms.generation.MelodyLineParameters;
import pms.notes.Instrument;
import pms.notes.NoteCommand;
import pms.notes.instruments.ElectroBass;
import pms.play.Timeline;
import pms.utils.RangeSelector;

public class BassBuilder extends InstrumentLineBuilder
{	
	private MelodyLineParameters melodyParameters;	
	
	public BassBuilder(Timeline timeline)
	{
		this(timeline, new RangeSelector(0,0.25), 0.6);
	}
	
	public BassBuilder(Timeline timeline, RangeSelector rangeSelector, double trackVolume)
	{
		super(timeline, rangeSelector, trackVolume);
		
		melodyParameters = new MelodyLineParameters(rangeSelector);
		melodyParameters.registerPickChances(0.4, 0.8);
		melodyParameters.registerSilenceChances(0.2, 0.05, 0);
		melodyParameters.registerspreadIndecies(0.3, 0.1, 0);
	}

	@Override
	public void buildBar(int startStep)
	{
		MelodyLine baseLine = MelodyLine.generateANewLine(lastNoteIndex, melodyParameters);
		
		for(int i = 0; i < 8; i++)
		{
			if(i == 4)
			{
				baseLine = MelodyLine.createVariation(melodyParameters, baseLine);
			}
			
			for(NoteCommand n : baseLine.notes)
			{				
				NoteCommand timedNote = new NoteCommand(n);				
				timedNote.timestep += startStep + i*8;
				
				track.addNote(timedNote);				

				lastNoteIndex = n.scaleIndex;
			}
		}
	}

	@Override
	public Instrument getInstrument() {return new ElectroBass();}

}
