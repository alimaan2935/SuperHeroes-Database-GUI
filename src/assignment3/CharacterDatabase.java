package assignment3;

import java.util.*;
import java.io.*;

/**
 * A class representing a Character 'database'. 
 * <p>
 * Here, 'database' merely refers to a searchable collection,
 * that can be stored and loaded from file.
 * 
 * @see Character
 * @see SuperCharacter
 *
 * @author Dr Phil
 * @author leggy (Lachlan Healey)
 * @version (11/10/2017)
 */

public class CharacterDatabase {
	/** The database collection is represented as a Set */
	private Set<Character> database;

	/** File path to database */
	private String dbFileName;

	/**
	 * Invariant: database != null && dbFilename != null 
	 */

    /**
     * Creates a new CharacterDatabase with the given string
	 * for the file path. 
     * 
     * @param fn File path to database store.
     */
	public CharacterDatabase(String fn) {
		database = new HashSet<Character>();
		dbFileName = fn;
	}

	/**
	 * Load a database Set object from file.
	 *
	 * @throws FileNotFoundException
	 */
	public void load() throws FileNotFoundException, Exception {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(dbFileName));

		try {
			database = (Set<Character>) in.readObject();
		}
		catch (ClassCastException cce) {
			throw new Exception("Data file corrupt.");
		}

		in.close();
	}

	/**
     * Save a database Set object to file.
     *
     * @throws IOException
     */

	public void save() throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(dbFileName));
        out.writeObject(database);
        out.close();
    }

    /**
     * Add a character to the database.
     * 
     * @param c The character to add.
     */
	public void add(Character c) { database.add(c); }

    /**
     * Remove a character to the database.
     * 
     * @param c The character to remove.
     */
	public void remove(Character c) { database.remove(c); }
	
	/**
	 * Updates a character in the database if it present, adding the character if it
	 * is not present.
	 * 
	 * @param c	The Character to update.
	 */
	public void update(Character c) {
		if (database.contains(c)) {
			database.remove(c);
		}
		database.add(c);
	}

	/**
	 * Search the character database for a character with the given name
	 * @require name != null && database != null
	 * @ensure	character with given name in DB => \result = character found &&
	 *			character with given  name not in DB => \result = null
	 * @param name the character name to search for
	 * @return character object found or null
	 */
	public Character search(String name) {
		try {
			for (Character c : database)
				if (name.equals(c.getName()))
					return (Character) c.clone();
		} catch (ClassCastException cce) { } // No need to do anything

		return null;
	}
	
	/**
	 * Returns a list of the character's names in the database. The list is not in
	 * any specific order.
	 * 
	 * @return Returns a list of the character's names in the database.
	 */
	public List<String> getCharacterNames() {
		List<String> names = new ArrayList<String>();
		for (Character character : database) {
			names.add(character.getName());
		}
		return names;
	}
	
	@Override
	public String toString() {
		String result = String.format("Character Database [%s]", dbFileName);
		if(database.isEmpty()) {
			result += "\n  Empty";
		}
		for(Character character : database) {
			result += String.format("\n  %s", character.getName() + character.getDescription());
		}
		return result;
	}
}
