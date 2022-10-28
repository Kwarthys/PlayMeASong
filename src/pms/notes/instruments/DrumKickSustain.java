package pms.notes.instruments;

import pms.notes.Instrument;
import pms.notes.LinearEnvelope;
import pms.utils.Keyframe;
import pms.utils.SimpleSignalGenerators;

public class DrumKickSustain extends Instrument {

	@Override
	protected void signalPostTreatment(double[] signal) { saturate(5,100,signal); }

	@Override
	protected double computeWaveform(double angle)
	{
		/*
		double clampedAttackCoef = 1-Math.min(1, 5*this.relativeTimeInNote);
		
		return 20 * (2.0 * Math.sin(angle)
				+ 1.0 * Math.sin(angle*2)
				+ 0.2*clampedAttackCoef*Math.sin(angle*15)
				+ 0.1*clampedAttackCoef*Math.sin(angle*16));
		*/
		
		return 120 * SimpleSignalGenerators.sawtooth(relativeTimeInPeriod);
	}

	@Override
	protected void buildEnvelope()
	{
		this.envelope = new LinearEnvelope();
		this.envelope.addKeyframe(new Keyframe(0, 1));
		this.envelope.addKeyframe(new Keyframe(0.3, 0.5));
		this.envelope.addKeyframe(new Keyframe(1, 0));
	}

}
