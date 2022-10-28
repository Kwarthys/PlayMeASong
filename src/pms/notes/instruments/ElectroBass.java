package pms.notes.instruments;

import pms.notes.Instrument;
import pms.notes.LinearEnvelope;
import pms.utils.SimpleSignalGenerators;

public class ElectroBass extends Instrument
{
	@Override
	protected void signalPostTreatment(double[] signal) { saturate(50, 120, signal); }

	@Override
	protected double computeWaveform(double angle)
	{
		return 120 * SimpleSignalGenerators.sawtooth(relativeTimeInPeriod);
	}

	@Override
	protected void buildEnvelope()
	{
		envelope = new LinearEnvelope();
		envelope.addKeyframe(0,1);
		envelope.addKeyframe(1,0);
		
		this.noteAmpRandomRange = 0.5;
	}

}
