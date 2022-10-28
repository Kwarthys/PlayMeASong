package pms.generation;

import pms.generation.builders.DrumKickBuilder;
import pms.generation.builders.DrumSnareBuilder;
import pms.generation.builders.InstrumentLineBuilder;
import pms.generation.builders.BassBuilder;
import pms.generation.builders.LeadBuilder;
import pms.notes.NoteCommand;
import pms.notes.instruments.DrumCrash;
import pms.play.Timeline;
import pms.play.Track;
import pms.utils.RangeSelector;

public class MelodyBuilder
{
	//private Timeline timeline;
	private Track crashTrack;
	
	private int counter = 10; //set to 0 for progressive buildup
	
	public MelodyBuilder(Timeline timeline)
	{
		//this.timeline = timeline;
		kickBuilder = new DrumKickBuilder(timeline, 0.4);
		snareBuilder = new DrumSnareBuilder(timeline, 1);

		bassBuilder1 = new BassBuilder(timeline, new RangeSelector(0,0.25), 0.2);
		bassBuilder2 = new BassBuilder(timeline, new RangeSelector(0.25,0.5), 0.2);

		leadBuilder1 = new LeadBuilder(timeline, new RangeSelector(0.5,0.75), 0.3);
		leadBuilder2 = new LeadBuilder(timeline, new RangeSelector(0.75,1), 0.3);
		
		crashTrack = new Track(new DrumCrash());
		crashTrack.setTrackVolume(0.8);
		timeline.tracks.add(crashTrack);
		
		System.out.println("MedodyBuilder initialized");
	}
	
	public void buildAll(int startStep)
	{
		buildDrumKickLine(startStep);
		buildDrumSnareLine(startStep);
		if(counter > 0) buildBassLine(startStep);
		if(counter > 1) buildLeadLine(startStep);
		
		/*** build crash ***/
		if(counter > 0)
		{
			crashTrack.addNote(new NoteCommand(startStep, 4, 20));
			crashTrack.addNote(new NoteCommand(startStep+8*4, 4, 20));			
		}
		
		
		counter++;
		
		//if(counter>=12)counter=0;
	}
	
	private InstrumentLineBuilder kickBuilder;

	public void buildDrumKickLine(int startStep)
	{		
		kickBuilder.buildBar(startStep);
	}	
	
	private InstrumentLineBuilder snareBuilder;
	
	public void buildDrumSnareLine(int startStep)
	{		
		snareBuilder.buildBar(startStep);
	}

	private InstrumentLineBuilder bassBuilder1;
	private InstrumentLineBuilder bassBuilder2;
	
	public void buildBassLine(int startStep)
	{
		bassBuilder1.buildBar(startStep);
		bassBuilder2.buildBar(startStep);
	}

	private InstrumentLineBuilder leadBuilder1;
	private InstrumentLineBuilder leadBuilder2;
	
	public void buildLeadLine(int startStep)
	{
		leadBuilder1.buildBar(startStep);
		leadBuilder2.buildBar(startStep);
	}
}
