package assignment3;

import java.io.IOException;

/**
 * Model class for the Character Editor
 * @author leggy (Lachlan Healey)
 */
public class Model {

	// Character Database object
	protected CharacterDatabase database;
	
	/*
	 * TODO: INVARIANT GOES HERE
	 */
	
	public Model() {
		/*
		 * TODO: DELETE THIS LINE AND IMPLEMENT CONSTRUCTOR
		 */
	}

	/**
	 * Load database based on user selected filepath.
	 * @param path Path of database file to load
	 */
	public void loadDatabase(String path) {
		database = new CharacterDatabase(path);
		try {
			database.load();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * Create new empty database with provided filename
	 * @param filename Filename of the database to create
	 * @return true if database creation is successful and false otherwise.
	 */
	public boolean createDatabase(String filename) {
		database = new CharacterDatabase(filename);
		try {
			database.load();
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Get characters from the loaded database.
	 * @return String of characters in the loaded database
	 */
	public String getCharacters() {
		return database.toString();

	}

	/**
	 * Creates Character object with provided name and empty description
	 * Also adds it to the current database
	 * @param name Name of the new character
	 * @return Character object
	 */
	public Character createCharacter(String name) {
		Character c = new Character(name, " ");
		addCharacter(c);
		return c;
	}

	/**
	 * Creates Super Character object with provided name and empty description
	 * @param name Name of the new super character
	 * @return SuperCharacter object if successful, null otherwise.
	 */
	public SuperCharacter createSuperCharacter(String name) {
		try {
			SuperCharacter s = new SuperCharacter(name, " ", SuperCharacter.INVALID);
			addCharacter(s);
			return s;
		}catch (IllegalPowerRankingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Adds character to the database.
	 * Attempts to remove the character object from database if already
	 * exists and the add the new one.
	 * @param c Character object to add
	 */
	public void addCharacter(Character c) {
		database.remove(c);
		database.add(c);
	}

	/**
	 * Searches a character from the database
	 * @param name Name of the character to search
	 * @return Character object if found in the database, null otherwise.
	 */
	public Character search(String name) {
		return database.search(name);
	}

	/**
	 * Deletes a character from the database
	 * @param name Name of the character to remove
	 */
	public void delete(String name) {
		Character c = database.search(name);
		database.remove(c);
	}

	/**
	 * Saves current database
	 */
	public void save(){
		try {
			database.save();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}








}
