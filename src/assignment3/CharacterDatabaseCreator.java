package assignment3;

import java.io.IOException;

/**
 * Example class for using the CharacterDatabase.
 * @author leggy
 *
 */
public class CharacterDatabaseCreator {

	public static void main(String[] args) {
		try {
			createDemoDatabase();
		} catch (IllegalPowerRankingException | IOException e) {
			System.out.println("AN ERROR OCCURRED:");
			System.out.println("    " + e.getMessage());
			// e.printStackTrace();
		}
		
		
		exampleDatabaseUpdate();
	}

	private static void createDemoDatabase() throws IllegalPowerRankingException, IOException {
		SuperCharacter tim = new SuperCharacter("Timmy", "Has nice eyes", "images/goodboy.png", 9);
		tim.addTrait("Is a good doggo");
		tim.addTrait("Digs good holes");
		tim.addPower("Really good at getting away with being naughty");

		SuperCharacter baa = new SuperCharacter("Baaa baaa", "Is undercover as a doggo", "images/woofer.png", 3);
		baa.addTrait("Is actually a sheep.");
		baa.addPower("Doesn't get cold in winter.");

		Character borks = new Character("Borker", "Looks good in black and white", "images/borker.png");
		borks.addTrait("Will bork when you least suspect it.");

		CharacterDatabase database = new CharacterDatabase("database1.dat");
		database.add(tim);
		database.add(baa);
		database.add(borks);
		
		database.save();
	}
	
	public static void exampleDatabaseUpdate() {
		
		System.out.println("Example of database updating:");
		System.out.println();
		
		CharacterDatabase database = new CharacterDatabase("example.dat");
		
		System.out.println("Adding Barry to database...");
		database.add(new Character("Barry", "Example character"));
		System.out.println("Barry's imagePath: " + database.search("Barry").getImagePath());
		
		
		System.out.println("Updating Barry, adding an imagePath...");
		database.update(new Character("Barry", "An updated character with an image!", "images/woofer.png"));
		System.out.println("Barry's new imagePath: " + database.search("Barry").getImagePath());
	}

}
