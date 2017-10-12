package assignment3;

import java.util.*;

/**
 * A place-holder exception class for illegal power rankings
 * 
 * @see SuperCharacter
 *
 * @author Dr Phil
 * @version (11/10/2017)
 */

public class IllegalPowerRankingException extends Exception {
	public IllegalPowerRankingException( ) {
		super( );
	}

	public IllegalPowerRankingException(String message) {
		super(message);
	}
}
