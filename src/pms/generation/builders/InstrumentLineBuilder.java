package pms.generation.builders;

import pms.notes.Instrument;
import pms.notes.NoteCommand;
import pms.notes.scale.Scale;
import pms.play.Timeline;
import pms.play.Track;
import pms.utils.RangeSelector;

public abstract class InstrumentLineBuilder
{
	protected Track track;
	protected Timeline timeline;
	
	protected int lastNoteIndex = 0;
	
	protected RangeSelector rangeSelector;
	
	public InstrumentLineBuilder(Timeline timeline, RangeSelector rangeSelector, double trackVolume)
	{
		this.timeline = timeline;
		this.track = new Track(getInstrument());
		
		//System.out.println(this.timeline);
		
		this.timeline.tracks.add(this.track);
		
		this.rangeSelector = rangeSelector;
		
		this.lastNoteIndex = Scale.getRandomFrequencyIndex(rangeSelector);
		
		this.track.setTrackVolume(trackVolume);
	}
	
	public abstract Instrument getInstrument();
	public abstract void buildBar(int startStep);
	
	public void addNoteForBar(int numberOfBars, int startStep, int duration, int fIndex)
	{
		for(int i = 0; i < numberOfBars; ++i)
		{
			track.addNote(new NoteCommand(startStep+i*8, duration, fIndex));
		}
	}
}
