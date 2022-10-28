package pms.notes.instruments;

import pms.notes.Instrument;
import pms.notes.LinearEnvelope;
import pms.utils.SimpleSignalGenerators;

public class ElectroHigh extends Instrument {

	@Override
	protected void signalPostTreatment(double[] signal) { saturate(10, 100, signal); }

	@Override
	protected double computeWaveform(double angle)
	{		
		return 120 * SimpleSignalGenerators.sawtooth(relativeTimeInPeriod);
	}

	@Override
	protected void buildEnvelope()
	{
		this.envelope = new LinearEnvelope();
		this.envelope.addKeyframe(0, 0);
		this.envelope.addKeyframe(0.1, 1);
		this.envelope.addKeyframe(0.8, 0.5);
		this.envelope.addKeyframe(1, 0);
		
		this.noteAmpRandomRange = 0.5;
		
		this.glideRelativeTime = 0.2;
	}

}
