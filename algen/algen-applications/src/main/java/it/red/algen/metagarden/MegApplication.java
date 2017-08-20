package it.red.algen.metagarden;

/**
 * TODOM: no static attributes, but external configuration, e.g. properties or JSON file
 * TODOA: remove redundancy
 * @author red
 *
 */
public class MegApplication {
	
	// Application name
	private static final String APP_NAME = "garden";
	
	// Custom inputs
	public static final String TARGET_WELLNESS = "TARGET_WELLNESS"; // happy|unhappy
	public static final String LIMITED_TREES = "LIMITED_TREES";
	
    // Data directory
    public static String BASE_DIR = System.getProperty("datadir");
	static {
        BASE_DIR = BASE_DIR==null ? "C:\\tmp\\algendata" : BASE_DIR;
	}

    public static final String DATABASE_DIR = 		BASE_DIR + "/"+APP_NAME+"/db";
    public static final String STATS_DIR = 			BASE_DIR + "/"+APP_NAME+"/stats";
    public static final String MASSIVE_STATS_DIR = 	BASE_DIR + "/"+APP_NAME+"/massive-stats";

}
