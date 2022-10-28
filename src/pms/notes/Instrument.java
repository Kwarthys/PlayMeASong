package pms.notes;

import pms.notes.scale.Scale;
import pms.param.Parameters;
import pms.utils.Utils;

public abstract class Instrument
{
	public Instrument()
	{
		buildEnvelope();
	}
	
	protected LinearEnvelope envelope;
	protected double phaseOffset = 0;

	protected double relativeTimeInNote = 0;
	protected double relativeTimeInPeriod= 0;
	
	protected double noteAmpRandomRange = 0.5;
	protected double noteAmplitude = 1.0;
	
	private double lastPeriod = -1;
	protected double glideRelativeTime = 0;
	
	protected double maxGap = -1;
	
	protected double[] getNoteData(NoteCommand n, int timestep)
	{	
		//System.out.println("Generating " + n.frequency + ", " + n.timestep + " at " + timestep);
		
		double[] data = new double[Parameters.SAMPLES_PER_STEP];
		double angle = 0;
		double period = (double)Parameters.SAMPLE_RATE / Scale.frequencies.get(n.scaleIndex);
		
		if(lastPeriod == -1) lastPeriod = period;
		
		for(int i = 0; i < data.length; ++i)
		{
			this.relativeTimeInNote = (double)(timestep - n.timestep + (i / (double)Parameters.SAMPLES_PER_STEP)) / (double)n.noteDuration;
			
			if(glideRelativeTime != 0)
			{
				double glideTime = - relativeTimeInNote / (glideRelativeTime/(1.0*n.noteDuration)) + 1.0;
				glideTime = glideTime > 0 ? glideTime : 0;
				
				angle = 2.0 * Math.PI * i / (Utils.lerp(period, lastPeriod, glideTime));
			}
			else
			{
				angle = 2.0 * Math.PI * i / period;
			}
			
			angle += phaseOffset;
			
			this.relativeTimeInPeriod = (angle / Math.PI / 2.0)%1.0;
			
			double value = computeWaveform(angle) * envelope.evaluate(relativeTimeInNote) * noteAmplitude;
			
			data[i] = value;
		}
		
		signalPostTreatment(data);
		
		lastPeriod = period;
		registerPhaseOffset(angle);		
		return data;
	}

	public void notifySilence()
	{
		lastPeriod = -1;
	}
	
	protected void signalPostTreatment(double[] signal) {};
	protected abstract double computeWaveform(double angle);
	protected abstract void buildEnvelope();
	
	public double[] playNote(NoteCommand n, int timestep)
	{
		computeNewNoteAmp();
		return getNoteData(n, timestep);
	}
	
	private void computeNewNoteAmp()
	{
		noteAmplitude = 1.0 - (Math.random() * noteAmpRandomRange);
	}
	
	protected void registerPhaseOffset(double phaseOffset)
	{
		this.phaseOffset = phaseOffset%(2*Math.PI);
	}
	
	protected void saturate(double gain, double cut, double[] data)
	{
		for(int i = 0; i < data.length; ++i)
		{
			//boolean didcut = false;
			//StringBuffer sb = new StringBuffer();
			//sb.append(data[i] + " * " + gain + " -> ");
			
			double value = data[i];
			
			value *= gain;
			
			//sb.append(value + " cut / " + cut);
			
			if(value > cut)
			{
				value = cut;
				//didcut = true;
			}
			else if(value < -cut)
			{
				value = -cut;
				//didcut = true;
			}
			
			data[i] = value;
			
			/*
			sb.append(" : " + data[i]);
			
			if(didcut)
			{
				System.out.println(sb.toString());
			}*/
		}
	}
}
