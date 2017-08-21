package it.red.algen.components;


/**
 * Metadata for a Component
 * @author red
 *
 */
public class ComponentMetadata {
	/**
	 * A Java class provided in the classpath (default).
	 */
	public static final String TYPE_JAVA = 		"java";
	
//	/**
//	 * A Scala script. TODOM: to be added
//	 */
//	public static final String TYPE_SCALA = 	"scala";
//	
//	/**
//	 * A Groovy script. TODOM: to be evaluated
//	 */
//	public static final String TYPE_SCRIPT =	"groovy";
	
//	/**
//	 * Name of the component, to be chosen amongst AppComponents constants.
//	 */
//	public String name;

	/**
	 * Type of the component:
	 * "java" for a fully qualified Java class provided in the classpath
	 */
	public String type = TYPE_JAVA;
	
	/**
	 * Content of the metadata: e.g. Java fully qualifier name for TYPE_JAVA
	 */
	public String content;
	
	
	
	public String toString(){
		return String.format("ComponentMetadata[type=%s; content=%.50s]", type, content);
	}
}
