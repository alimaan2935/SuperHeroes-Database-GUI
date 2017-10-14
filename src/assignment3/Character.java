package assignment3;

import java.util.*;
import java.io.*;

/**
 * DO NOT MODIFY THIS CLASS
 */

/**
 * A class representing a Character with name, 
 * description, and a set of traits.
 *
 * @author Dr Phil
 * @author Dr-to-be Doug
 * @author leggy (Lachlan Healey)
 * @version (10/10/2017)
 */

public class Character implements Serializable {
	/** Character's name */
    protected String name;
    /** Character's description */
    protected String description;
    /** A set of character's traits */
    protected Set<String> traits;
    /** The path to the character's image */
    protected String imagePath = DEFAULT_IMAGE_LOCATION;
    
    /** Default image location */
    private static String DEFAULT_IMAGE_LOCATION = "images/default.png";

    /*
     * Invariants:
     * 
     * name != null && !name.isEmpty()
     * description != null
     * traits != null && !traits.contains(null) && !traits.get(i).isEmpty()
     */

	/** Creates a character instance
	 *
     * @param n The character's name.
     * @param d The character's description.
     */
	public Character(String n, String d) {
		name = n;
		description = d;
		traits = new HashSet<String>();
	}
	
	/** As for Character(n, d)
	 *
    * @param n The character's name.
    * @param d The character's description.
    * @param i The character's image path.
    */
	public Character(String n, String d, String i) {
		this(n, d);
		imagePath = i;
	}

    /**
     * @return The Character's name.
     */
    public String getName() { return name; }

    /**
     * @return The character's description.
     */
    public String getDescription() { return description; }
    
    /**
     * @return The character's image path.
     */
    public String getImagePath() {
    		return imagePath;
    }
    
    /**
     * Sets the Character's image path.
     * @param path	The Character's image path as a String.
     */
    public void setImagePath(String path) {
    		imagePath = path;
    }

    /**
     * Set the Character's name.
     * 
     * @param n Character's name as a String.
     */
    public void setName(String n) { name = n; }

    /**
     * Set the Character's description.
     * 
     * @param d Character's description as a String.
     */
    public void setDescription(String d) { description = d; }

	/** Add a trait to the Character's set of traits.
     * 
     * @param t The character's trait to add.
     */
    public void addTrait(String t) {
        traits.add(t);
    }

    /**
     * Remove a trait from the Character's set of traits.
     */
    public void removeTrait(String t) {
        traits.remove(t);
    }

    @Override
    public String toString() {
        String s = name + "\n" + description + "\n\n" + "Traits:\n" + traits;
        return s;
    }

    /**
     * Check the correctness of this Character object.
     * Validate the Character's invariants.
     * 
     * @return True if this object is a valid Character, that is,
     * if none of the Character invariants are broken. Return false
     * otherwise.
     */
    protected boolean validateCharacter(){
        if (name == null) {
            System.out.println("Character's name must not be null.");
            return false;
        }
        if (name.isEmpty()) {
            System.out.println("Character's name must not be an empty string.");
            return false;
        }
        if (description == null) {
            System.out.println("Character's description must not be null.");
            return false;
        }
        if (traits == null) {
            System.out.println("Character's trait set must not be null.");
            return false;
        }
        // check each trait
        for (String trait : traits) {
            if (trait == null) {
                System.out.println("Character's trait must not be null.");
                return false;
            }
            if (trait.isEmpty()) {
                System.out.println("Character's trait must not be an empty.");
                return false;
            }
          }

        return true;
    }

    /**
     * Check whether this character contains the given set 
     * of traits, that is, check if the set of traits
     * of this character and the given set of traits have 
     * the same elements. 
     * 
     * @param expectedTraits The set of traits to check.
     * @return True if validation passed, false otherwise.
     */
    protected boolean compareTraits(Set<String> expectedTraits) {
        if (traits.size() != expectedTraits.size())
            return false;
        for (String trait : expectedTraits) {
            if (!traits.contains(trait))
                return false;
        }
        return true;
    }

	@Override
	public boolean equals(Object other) {
		try {
			Character c = (Character) other;
			return name.equals(c.getName());
		}
		catch (ClassCastException cce) {
			return false;
		}
	}

	@Override
	public Object clone() {
		Character clone = new Character(name, description, imagePath);
		for (String t : traits)
			clone.traits.add(t);

		return clone;
	}
}
		
