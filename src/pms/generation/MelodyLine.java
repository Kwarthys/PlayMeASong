package pms.generation;

import java.util.ArrayList;

import pms.notes.NoteCommand;
import pms.notes.scale.Scale;
import pms.utils.SilencePrint;

public class MelodyLine
{
	public ArrayList<NoteCommand> notes = new ArrayList<>();
	
	public MelodyLine(MelodyLine toCopy)
	{
		for(NoteCommand n : toCopy.notes)
		{
			//this.notes.add(new NoteCommand(n));
			
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
	}
	
	public MelodyLine()
	{
		
	}

	public static MelodyLine generateANewLine(int lastNoteIndex, MelodyLineParameters params)
	{
		MelodyLine line = new MelodyLine();

		int remainingSpace = 8;

		while(remainingSpace > 0)
		{
			int i = 8 - remainingSpace;

			double rand = Math.random();

			if(rand < params.pickChances[0])
			{
				//croche
				if(Math.random() > params.silenceChances[0])
				{
					double spreadIndex = params.spreadIndecies[0];
					lastNoteIndex = Scale.getRandomFrequencyIndexNear(lastNoteIndex, spreadIndex, params.rangeSelector);
					line.notes.add(new NoteCommand(i, 1, lastNoteIndex));
				}

				remainingSpace -= 1;
			}
			else
			{				
				if(rand < params.pickChances[1] && remainingSpace >= 2)
				{
					//noire
					if(Math.random() > params.silenceChances[1])
					{
						double spreadIndex = params.spreadIndecies[1];
						lastNoteIndex = Scale.getRandomFrequencyIndexNear(lastNoteIndex, spreadIndex, params.rangeSelector);
						line.notes.add(new NoteCommand(i, 2, lastNoteIndex));

					}

					remainingSpace -= 2;
				}
				else if(remainingSpace >= 4)
				{
					//blanche
					if(Math.random() > params.silenceChances[2])
					{
						double spreadIndex = params.spreadIndecies[2];
						lastNoteIndex = Scale.getRandomFrequencyIndexNear(lastNoteIndex, spreadIndex, params.rangeSelector);
						line.notes.add(new NoteCommand(i, 4, lastNoteIndex));

					}

					remainingSpace -= 4;
				}
			}
		}

		return line;
	}
	
	public static MelodyLine createVariation(MelodyLineParameters params, MelodyLine base, int numberOfAlterations)
	{
		MelodyLine line = new MelodyLine(base);
				
		for(int i = 0; i < numberOfAlterations; ++i)
		{
			//System.out.print(line);
			
			ArrayList<SilencePrint> silences;
			boolean foundSilences = false;
			if(Math.random() > 0.5)
			{
				silences = line.findSilences();
				foundSilences = silences.size() != 0;
				if(foundSilences)
				{
					//System.out.println(" : SilenceRemoval");
					silenceRemover(params, line, silences);
				}
			}
			
			if(!foundSilences)
			{				
				//System.out.println(" : Pitch");
				oneNotePitchVariation(params, line);
			}
			
			//System.out.println(line + "\n");			
		}
		
		return line;
	}
	
	public static MelodyLine createVariation(MelodyLineParameters params, MelodyLine base)
	{
		return createVariation(params, base, 1);
	}

	
	private static void oneNotePitchVariation(MelodyLineParameters params, MelodyLine toAlter)
	{		
		int indexToAlter = (int)(Math.random() * toAlter.notes.size());
		
		toAlter.notes.get(indexToAlter).scaleIndex = Scale.getRandomFrequencyIndex(params.rangeSelector);
	}
	
	private static void silenceRemover(MelodyLineParameters params, MelodyLine toAlter, ArrayList<SilencePrint> silences)
	{		
		if(silences.size() == 0) return;
		
		/*
		for(SilencePrint sp : silences)
		{
			System.out.println("Silence at " + sp.startIndex + " " + sp.length + " after " + sp.lastNoteIndexInList);
		}
		*/
		
		SilencePrint silence = silences.get((int)(Math.random() * silences.size()));
		
		int extent = (int)(Math.random() * silence.length);
		silence.startIndex += extent;
		silence.length -= extent;
		
		double rand = Math.random();
		int newNoteDuration = rand < params.pickChances[0] ? 1 : rand < params.pickChances[1] ? 2 : 4;
		
		if(silence.length < newNoteDuration)
		{
			if(silence.length== 1) newNoteDuration = 1;
			else newNoteDuration = 2;
		}


		int noteIndex;
		
		if(silence.lastNoteIndexInList != -1)
		{
			int lastNoteIndex = toAlter.notes.get(silence.lastNoteIndexInList).scaleIndex;			
			noteIndex = Scale.getRandomFrequencyIndexNear(lastNoteIndex, params.spreadIndecies[newNoteDuration == 1 ? 0 : newNoteDuration == 2 ? 1 : 2], params.rangeSelector);
			NoteCommand note = new NoteCommand(silence.startIndex, newNoteDuration, noteIndex);			
			toAlter.notes.add(silence.lastNoteIndexInList+1, note);
			
			//System.out.println("adding " + note.scaleIndex + " after " + toAlter.notes.get(silence.lastNoteIndexInList).timestep);
		}
		else
		{
			noteIndex = Scale.getRandomFrequencyIndex(params.rangeSelector);
			NoteCommand note = new NoteCommand(silence.startIndex, newNoteDuration, noteIndex);
			toAlter.notes.add(0, note);
			
			//System.out.println("adding " + note.scaleIndex + " to the end");
		}
		
	}
	
	private ArrayList<SilencePrint> findSilences()
	{
		ArrayList<SilencePrint> silences = new ArrayList<>();
		
		int step = 0;
		
		for(int i = 0; i < this.notes.size(); ++i)
		{			
			NoteCommand n = this.notes.get(i);
			
			if(step != n.timestep)
			{
				//System.out.println("step " + step + " != " + n.timestep + " n.timstep");
				silences.add(new SilencePrint(step, n.timestep - step, i == 0 ? -1 : i-1));
				
				step = n.timestep;
			}

			step += n.noteDuration;				
			
		}
		
		if(step < 8)
		{
			silences.add(new SilencePrint(step, 8 - step, this.notes.size()-1));
		}
		
		return silences;
	}
	
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("Line :");
		
		int step = 0;
		
		for(int i = 0; i < this.notes.size(); ++i)
		{
			NoteCommand n = this.notes.get(i);
			
			/**** Silence ? ****/
			for(int sil = step; sil < n.timestep; ++sil)
			{
				sb.append(" x");
				step++;
			}
			/*******************/			
			
			sb.append(" ");
			sb.append(n.scaleIndex);
			
			step++;
			
			for(int si = 1; si < n.noteDuration; ++si)
			{
				sb.append(" -");
				step++;
			}
		}
		
		/**** Silence ? ****/
		for(int sil = step; sil < 8; ++sil)
		{
			sb.append(" x");
		}
		/*******************/
		
		return sb.toString();
	}

}
