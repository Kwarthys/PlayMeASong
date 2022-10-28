package pms.notes.instruments;

import pms.notes.Instrument;
import pms.notes.LinearEnvelope;
import pms.utils.Keyframe;

public class DrumSnare extends Instrument {

	@Override
	protected void signalPostTreatment(double[] signal) {}

	@Override
	protected double computeWaveform(double angle)
	{
		return 100 * (Math.sin(angle) + 0.5*Math.sin(angle*2) + 0.3*Math.sin(angle*3) +  0.2*Math.sin(angle*4))
				+ (Math.random()-0.5)*2 * 40;
	}

	@Override
	protected void buildEnvelope()
	{
		this.envelope = new LinearEnvelope();
		this.envelope.addKeyframe(new Keyframe(0, 1));
		this.envelope.addKeyframe(new Keyframe(0.3, 0.2));
		this.envelope.addKeyframe(new Keyframe(0.7, 0));
		this.envelope.addKeyframe(new Keyframe(1, 0));
	}

}
