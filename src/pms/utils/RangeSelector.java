package pms.utils;


public class RangeSelector
{
	public double from;
	public double to;
	
	public RangeSelector(double from, double to)
	{
		this.from = from;
		this.to = to;
	}
	
	/**
	 * Default selection [0;1]
	 */
	public RangeSelector()
	{
		this.from = 0;
		this.to = 1;
	}
	
	public double width()
	{
		return to-from;
	}
}
