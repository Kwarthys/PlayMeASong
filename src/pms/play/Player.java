package pms.play;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import pms.generation.MelodyBuilder;
import pms.param.Parameters;

public class Player
{
	private Timeline timeline;
	private MelodyBuilder builder;
	
	public Player()
	{
		timeline = new Timeline();
		builder = new MelodyBuilder(timeline);
	}
	
	public void go()
	{
		//timeline.read
		
		final AudioFormat af = new AudioFormat(Parameters.SAMPLE_RATE, 8, 1, true, true);		

		try {
			SourceDataLine line = AudioSystem.getSourceDataLine(af);
			line.open(af, Parameters.SAMPLE_RATE);
			line.start();

			int chunkSize = 8*4*2;
			
			for(int ci = 0; ci < 16; ++ci)
			{
				System.out.println(ci*chunkSize);
				builder.buildAll(ci*chunkSize);
				for(int i = 0; i < chunkSize; ++i)
				{
					play(line, timeline.getDataAtTime(i + ci*chunkSize));
				}
			}
			
			 
			line.drain();
			line.close();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void play(SourceDataLine line, byte data[]) {
		/*int count = */line.write(data, 0, data.length);
	}
}
