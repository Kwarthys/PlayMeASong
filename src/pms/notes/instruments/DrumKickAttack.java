package pms.notes.instruments;

import pms.notes.Instrument;
import pms.notes.LinearEnvelope;
import pms.utils.SimpleSignalGenerators;

public class DrumKickAttack extends Instrument {

	@Override
	protected void signalPostTreatment(double[] signal) {}

	@Override
	protected double computeWaveform(double angle)
	{
		return 120 * SimpleSignalGenerators.square((relativeTimeInPeriod*2)%1.0, 0.3);
	}

	@Override
	protected void buildEnvelope()
	{
		this.envelope = new LinearEnvelope();
		this.envelope.addKeyframe(0, 1);
		this.envelope.addKeyframe(0.2,0);
		this.envelope.addKeyframe(1,0);
		
		this.noteAmpRandomRange = 0.1;

	}

}
