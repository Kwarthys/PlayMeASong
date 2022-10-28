package pms.notes.instruments;

import pms.notes.Instrument;
import pms.notes.LinearEnvelope;
import pms.utils.Keyframe;

public class SimpleKeyboard extends Instrument
{
	@Override
	protected double computeWaveform(double angle)
	{		
		return Math.sin(angle) * 100.0
				+ Math.sin(angle*2) * 50.0
				+ Math.sin(angle*3) * 4.0;
	}

	@Override
	protected void signalPostTreatment(double[] signal)
	{
		saturate(1, 30, signal);
	}

	@Override
	protected void buildEnvelope()
	{
		envelope = new LinearEnvelope();		
		envelope.addKeyframe(new Keyframe(0, 0));
		envelope.addKeyframe(new Keyframe(0.1, 1));
		envelope.addKeyframe(new Keyframe(0.2, 0.5));
		envelope.addKeyframe(new Keyframe(0.8, 0.3));
		envelope.addKeyframe(new Keyframe(1, 0));
	}
}
