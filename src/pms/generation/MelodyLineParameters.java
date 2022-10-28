package pms.generation;

import pms.utils.RangeSelector;

public class MelodyLineParameters
{
	public RangeSelector rangeSelector;
	
	public MelodyLineParameters(RangeSelector rangeSelector)
	{
		this.rangeSelector = rangeSelector;
	}
	
	public double[] silenceChances;
	public void registerSilenceChances(double croche, double noire, double blanche)
	{
		silenceChances = new double[3];
		silenceChances[0] = croche;
		silenceChances[1] = noire;
		silenceChances[2] = blanche;
	}
	
	public double[] pickChances;
	public void registerPickChances(double croche, double noire)
	{
		pickChances = new double[2];
		pickChances[0] = croche;
		pickChances[1] = noire;
	}
	
	public double[] spreadIndecies;
	public void registerspreadIndecies(double croche, double noire, double blanche)
	{
		spreadIndecies = new double[3];
		spreadIndecies[0] = croche;
		spreadIndecies[1] = noire;
		spreadIndecies[2] = blanche;
	}
}
