package pms.notes;

public class NoteCommand
{
	public int scaleIndex;
	public int timestep;
	public int noteDuration;
	
	public NoteCommand(int timestep, int noteDuration, int frequency)
	{
		this.scaleIndex = frequency;
		this.timestep = timestep;
		this.noteDuration = noteDuration;
	}
	
	public NoteCommand(NoteCommand n)
	{
		this.scaleIndex = n.scaleIndex;
		this.timestep = n.timestep;
		this.noteDuration = n.noteDuration;
	}
}
