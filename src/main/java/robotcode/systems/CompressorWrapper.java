package robotcode.systems;

import config.Config;
import edu.wpi.first.wpilibj.Compressor;

public class CompressorWrapper {
    public static void action(Compressor airCompressor) {
        if (Config.RunConstants.RUNNING_PNEUMATICS) {
				airCompressor.start();
			} else {
				airCompressor.stop();
		}
    }
}