package assignment3;

import java.util.*;

/**
 * DO NOT MODIFY THIS CLASS
 */

/**
 * A class representing a Super-Character. 
 * <p>
 * A Super-Character is a Character with a set of super-powers,
 * and a given power ranking in the range [1..10]. 
 * <p>
 * A Super-character with a power ranking out of the range [1..10] 
 * is considered either as UNRANKABLE or INVALID.
 * 
 * @see Character
 *
 * @author Dr Phil
 * @author Dr-to-be Doug
 * @author leggy (Lachlan Healey)
 * @version (11/10/17)
 */
public class SuperCharacter extends Character {
	/** A special value to represent unrankable */
	public static final int UNRANKABLE = Integer.MAX_VALUE;
	/** A special value to represent an invalid power ranking  */
	public static final int INVALID = Integer.MIN_VALUE;
	/** The minimum possible value for power ranking */
	public static final int MIN_POWER = 1;
	/** The maximum possible value for power ranking */
	public static final int MAX_POWER = 10;
	
	/** The super-character's power ranking */
	protected int powerRanking;
	/** The set of powers of this super-character */
	protected Set<String> powers;

	/*
	 * Invariants:
	 * 
	 * powers != null && !powers.contains(null) && !powers.get(i).isEmpty()
	 */
	
	/**
	 * Creates a new Super-Character with the given name,
	 * description, and power ranking.
	 * 
	 * @param n The super-character's name.
	 * @param d The super-character's description.
	 * @param p The super-character's power ranking.
	 * @throws IllegalPowerRankingException
	 */
	public SuperCharacter(String n, String d, int p) throws IllegalPowerRankingException {
		super(n, d);
		setPowerRanking(p);
		powers = new HashSet<String>();
	}
	
	/**
	 * Creates a new Super-Character with the given name,
	 * description, image path, and power ranking.
	 * 
	 * @param n The super-character's name.
	 * @param d The super-character's description.
	 * @param i The super-character's image path.
	 * @param p The super-character's power ranking.
	 * @throws IllegalPowerRankingException
	 */
	public SuperCharacter(String n, String d, String i, int p) throws IllegalPowerRankingException {
		super(n, d, i);
		setPowerRanking(p);
		powers = new HashSet<String>();
	}

	/**
	 * Add a power to the Super-Character's set of powers.
	 * 

	 * @param p The super-character's power to add.
	 */
	public void addPower(String p) { 
		powers.add(p); 
	}
	
	/**
	 * Remove a power from the Super-Character's set of powers.
	 * 
	 * @param p The super-character's power to remove.
	 */
	public void removePower(String p) { 
		powers.remove(p); 
	}
		
	/**
	 * @return The super-character's power ranking. 
	 */
	public int getPowerRanking() { 
		return powerRanking; 
	}
	
	/**
	 * Set the Super-Character's power ranking.
	 * 
	 * @param p A numeric value representing the Super-Character's 
	 * power ranking, in the range [1..10]. If the value set is out
	 * of the range [1..10] then  the super-character is considered 
	 * either as UNRANKABLE or INVALID.
	 * @throws IllegalPowerRankingException
	 */
	public void setPowerRanking(int p) throws IllegalPowerRankingException {
		if (validPowerRanking(p))
			powerRanking = p;
		else 
			throw new IllegalPowerRankingException ("Warning.  Invalid value for power ranking.");
	}

	@Override
	public String toString() {
		String s = super.toString() +"\n\n" + "Powers:\n" + powers;
		return s;
	}
	
	/**
	 * Check whether the given number is a valid power ranking.
	 * 
	 * @param p The value of power ranking to check.
	 * @return True if the value is a valid power ranking, false
	 * otherwise.
	 */
	protected boolean validPowerRanking(int p) {
		return MIN_POWER <= p && p <= MAX_POWER || p == UNRANKABLE;
	}
	
	/**
	 * Check the correctness of this Super-Character object.
	 * Validate the Super-Character's invariants.
	 * 
	 * @return True if this object is a valid Super-Character, that is,
	 * if none of the Super-Character invariants are broken. Return false
	 * otherwise.
	 */
	protected boolean validateSuperCharacter() {
		if (!super.validateCharacter()) {
			return false;
		}
		// check each power
		for (String power : powers) {
			if (power == null) {
				System.out.println("Super-Character's power must not be null.");
				return false;
			}
			if (power.isEmpty()) {
				System.out.println("Super-Character's power must not be an empty.");
				return false;
			}
		}
		// check power ranking
		if (!validPowerRanking(powerRanking)) {
			System.out.println("Invalid power ranking.");
			return false;
		}
		
		return true;
	}

	/**
	 * Check whether this super-character contains the 
	 * given set of powers, that is, check if the
	 * set of powers of this character and the given set 
	 * of powers have the same elements. 
	 * 
	 * @param expectedPowers The set of powers to check.
	 * @return True if validation passed, false otherwise.
	 */
	protected boolean comparePowers(Set<String> expectedPowers)  {
		if (powers.size() != expectedPowers.size())
			return false;
		for (String power : expectedPowers) {
			if (!powers.contains(power))
				return false;
		}
		return true;
	}

	@Override
    public Object clone() {
        SuperCharacter clone = null;

        try {
            clone  = new SuperCharacter(name, description, imagePath, powerRanking);

            for (String t : traits)
                clone.traits.add(t);

            for (String p : powers)
                clone.powers.add(p);

        } catch (IllegalPowerRankingException ipre) { ipre.printStackTrace(); } // this can never happen

        return clone;
    }

}
		
