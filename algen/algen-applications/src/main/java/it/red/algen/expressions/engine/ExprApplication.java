package it.red.algen.expressions.engine;

/**
 * TODOM: external configuration, e.g. properties file
 * @author red
 *
 */
public class ExprApplication {
	
	// Application name
	private static final String APP_NAME = "expressions";
	
	// Custom inputs
	public static final String TARGET_EXPRESSION_RESULT = "TARGET_EXPRESSION_RESULT";
	public static final String MAX_OPERAND_VALUE = "MAX_OPERAND_VALUE";
	
    // Data directory
    public static String BASE_DIR = System.getProperty("datadir");
	static {
        BASE_DIR = BASE_DIR==null ? "C:\\tmp\\algendata" : BASE_DIR;
	}

    public static final String DATABASE_DIR = 		BASE_DIR + "/"+APP_NAME+"/db";
    public static final String STATS_DIR = 			BASE_DIR + "/"+APP_NAME+"/stats";
    public static final String MASSIVE_STATS_DIR = 	BASE_DIR + "/"+APP_NAME+"/massive-stats";

	
//	private Map<String, Object> specifics = new HashMap<String, Object>();
//	
//	public ExprAppSpecifics(){
//		specifics.put(, )
//	}
//	
//	public String getAppName(){
//		return APP_NAME;
//	}
//
//	public Map<String, Object> getConfiguration(){
//		
//	}
//	
}
