package it.red.algen.garden.engine;

public class GardenApplication {
    
    public static String BASE_DIR = System.getProperty("datadir");
	static {
        BASE_DIR = BASE_DIR==null ? "C:\\tmp\\algendata" : BASE_DIR;
        System.out.println("Set BASEDIR "+BASE_DIR);
	}
    
    public static final String DATABASE_DIR = 		BASE_DIR + "/garden/db";
    public static final String STATS_DIR = 			BASE_DIR + "/garden/stats";
    public static final String MASSIVE_STATS_DIR = 	BASE_DIR + "/garden/massive-stats";

}
