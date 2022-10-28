package pms.notes.instruments;

import pms.notes.Instrument;
import pms.notes.LinearEnvelope;
import pms.utils.SimpleSignalGenerators;

public class DrumCrash extends Instrument {	
	
	@Override
	protected void signalPostTreatment(double[] signal) { /*saturate(3,10,signal);*/ }

	@Override
	protected double computeWaveform(double angle)
	{
		double signal = 0;
		
		/*
		double fadeout = 0.5;
		double speedUp = 1;
		
		double amp = 1;
		double speed = 1;
		
		double randomPart = 0.2;
		
		int harmonics = 5;
		
		for(int i = 0; i < harmonics; ++i)
		{
			//signal += 20 * amp * Math.sin(angle * speed * (1-randomPart/2+Math.random()*randomPart));
			
		
			amp = amp*fadeout;
			speed = speed + speedUp;
		}
		
		*/

		signal += 70 * (Math.random()-0.5)*2 * noiseEnvelope.evaluate(relativeTimeInNote);
		signal += 50 * SimpleSignalGenerators.square(relativeTimeInPeriod, 0.3);
		
		return signal;
	}
	
	private LinearEnvelope noiseEnvelope;

	@Override
	protected void buildEnvelope()
	{
		envelope = new LinearEnvelope();
		envelope.addKeyframe(0, 1);
		envelope.addKeyframe(0.3, 0.3);
		envelope.addKeyframe(1, 0);
		
		noiseEnvelope = new LinearEnvelope();
		noiseEnvelope.addKeyframe(0, 0.5);
		noiseEnvelope.addKeyframe(0.3, 1);
		noiseEnvelope.addKeyframe(1, 0);
	}

}
