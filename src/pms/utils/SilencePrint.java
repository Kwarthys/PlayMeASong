package pms.utils;

public class SilencePrint
{
	public int startIndex = 0;
	public int length = 0;
	public int lastNoteIndexInList = 0;
	
	public SilencePrint(int startIndex, int length, int lastNoteIndexInList)
	{
		this.startIndex = startIndex;
		this.length = length;
		this.lastNoteIndexInList = lastNoteIndexInList;
	}
}
