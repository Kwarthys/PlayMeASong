package pms.notes.instruments;

import pms.notes.Instrument;
import pms.notes.LinearEnvelope;
import pms.utils.Keyframe;

public class Piano extends Instrument
{	
	private LinearEnvelope waveformEnvelope;
	
	@Override
	protected void buildEnvelope()
	{
		envelope = new LinearEnvelope();		
		envelope.addKeyframe(new Keyframe(0, 0.1));
		envelope.addKeyframe(new Keyframe(0.1, 1));
		envelope.addKeyframe(new Keyframe(0.9, 0.8));
		envelope.addKeyframe(new Keyframe(1, 0.1));
		
		waveformEnvelope = new LinearEnvelope();
		waveformEnvelope.addKeyframe(new Keyframe(0, 0));
		waveformEnvelope.addKeyframe(new Keyframe(0.1, 1));
		waveformEnvelope.addKeyframe(new Keyframe(0.2, 0.8));
		waveformEnvelope.addKeyframe(new Keyframe(0.4, 0.8));
		waveformEnvelope.addKeyframe(new Keyframe(0.6, -0.8));
		waveformEnvelope.addKeyframe(new Keyframe(0.8, -0.7));
		waveformEnvelope.addKeyframe(new Keyframe(0.9, -1));
		waveformEnvelope.addKeyframe(new Keyframe(1, 0));
	}
	
	
	@Override
	protected double computeWaveform(double angle)
	{		
		return 100 * (waveformEnvelope.evaluate(relativeTimeInPeriod)
				+ 30 * waveformEnvelope.evaluate((relativeTimeInPeriod*2.0)%1.0)
				+ 20 * waveformEnvelope.evaluate((relativeTimeInPeriod*3.0)%1.0));
	}


	@Override
	protected void signalPostTreatment(double[] signal) {}

}
