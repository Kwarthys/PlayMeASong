package pms.param;

public class Parameters
{
	public static final int SAMPLE_RATE = 16 * 1024; // ~16KHz
	public static final float SECONDS_BY_TIMESTEP = 0.2f;
	public static final int SAMPLES_PER_STEP = buildStep();
	
	private static int buildStep()
	{
		return (int) (Parameters.SECONDS_BY_TIMESTEP * Parameters.SAMPLE_RATE);
	}
	
	public static final boolean MINOR_KEY = true;
}
