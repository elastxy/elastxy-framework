package it.red.algen.metadata;

/**
 * Types supported for Allele type for a Gene metadata
 * @author red
 *
 */
public enum GeneMetadataType {
	BOOLEAN,	// true|false
	CHAR,		// java char type
	INTEGER,	// java long type
	DECIMAL,	// java double type
	STRING,		// java String type
	USER		// user defined type, can be anything
}
