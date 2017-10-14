package assignment3;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Controller class for the Character Editor.
 * @author leggy (Lachlan Healey)
 */
public class Controller implements Initializable{
	
	private Model model;
	private final FileChooser fileChooser = new FileChooser();
	private ObservableList<String> characterDisplayList;
	private Character selectedCharacter;
	private Image selectedImage;
	private String selectedImagePath;

	@FXML
	private Button loadDatabase;
	@FXML
	private TextField enterFilename;
	@FXML
	private Button saveDatabase;
	@FXML
	private Button createDatabase;
	@FXML
	private ListView characterList;
	@FXML
	private TextField characterNameCreate;
	@FXML
	private Button newCharacter;
	@FXML
	private Button newSuperCharacter;
	@FXML
	private TextField enterCharacterNameSearch;
	@FXML
	private Button search;
	@FXML
	private Button clearSearch;
	@FXML
	private Button deleteCharacter;
	@FXML
	private Button changeImage;
	@FXML
	private ImageView imageView;
	@FXML
	private TextField descriptionField;
	@FXML
	private ListView traitsList;
	@FXML
	private TextField powerLevelField;
	@FXML
	private ListView powerList;
	@FXML
	private Button saveCharacterChanges;
	@FXML
	private Label characterNameDisplay;


	/**
	 * Constructor
	 */
	public Controller() {
		this.model = new Model();
		this.characterDisplayList = FXCollections.observableArrayList();
	}

	/**
	 * Initializer
	 * @param location
	 * @param resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Handling button events
		loadDatabaseEvent();
		createDatabaseEvent();
		saveDatabaseEvent();
		createCharacterEvent();
		createSuperCharacterEvent();
		selectCharacterEvent();
		searchCharacterEvent();
		clearSearchEvent();
		deleteCharacterEvent();
		changeImageEvent();
		saveCharacterChangesEvent();

		// Setting list View Constraints
		characterList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		traitsList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		powerList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);



	}

	/**
	 * Handles load database button event
	 */
	private void loadDatabaseEvent() {
		loadDatabase.setOnAction(e -> {
			File file = fileChooser.showOpenDialog(new Stage());
			if (file != null) {
				displayDatabase(file.getPath());
			}
		});
	}

	/**
	 * Displays the intended database into the characters list view.
	 * @param filePath Path of the database file to load and display.
	 */
	private void displayDatabase(String filePath) {
		model.loadDatabase(filePath);

		characterDisplayList.addAll(model.getCharacters().trim().split(System.lineSeparator()));
		characterDisplayList.remove(0);
		characterList.setItems(characterDisplayList);

	}

	/**
	 * Handles new character creation event
	 */
	private void createCharacterEvent() {
		newCharacter.setOnAction(e -> {
			if (!characterNameCreate.getText().isEmpty()) {
				selectedCharacter = model.createCharacter(characterNameCreate.getText());
				characterDisplayList.add(characterNameCreate.getText());
				characterList.setItems(characterDisplayList);
			}
		});
	}

	/**
	 * Handles new super character creation event
	 */
	private void createSuperCharacterEvent() {
		newSuperCharacter.setOnAction(e -> {
			if (!characterNameCreate.getText().isEmpty()) {
				selectedCharacter = model.createSuperCharacter(characterNameCreate.getText());
				characterDisplayList.add(characterNameCreate.getText());
				characterList.setItems(characterDisplayList);
			}
		});
	}

	/**
	 * Database creation event
	 */
	private void createDatabaseEvent() {
		createDatabase.setOnAction(e -> {
			if (!enterFilename.getText().isEmpty()) {
				model.loadDatabase(enterFilename.getText());
			}
		});
	}

	/**
	 * Saves the database
	 */
	private void saveDatabaseEvent() {
		saveDatabase.setOnAction(e -> model.save());
	}

	/**
	 * Handles Character selection event from the list
	 */
	private void selectCharacterEvent() {
		characterList.setOnMouseClicked(e -> {
			String selectedName = characterList.getSelectionModel().getSelectedItem().toString().trim();
			Character c = model.search(selectedName);
			setCharacterFields(c);
		});
	}

	/**
	 * Handles image change event
	 */
	private void deleteCharacterEvent() {
		deleteCharacter.setOnAction(e -> {
			String name = characterList.getSelectionModel().getSelectedItem().toString();
			model.delete(name);
			characterDisplayList.remove(name);
			characterList.setItems(characterDisplayList);
		});
	}

	/**
	 * Handles image change event
	 */
	private void changeImageEvent() {
		changeImage.setOnAction(e -> {
			File file = fileChooser.showOpenDialog(new Stage());
			if (file != null) {
				selectedCharacter.setImagePath(file.getPath());
				selectedImage = new Image(file.getPath());
				imageView.setImage(selectedImage);
			}
		});
	}

	/**
	 * Handles save character changes event
	 */
	private void saveCharacterChangesEvent() {
		saveCharacterChanges.setOnAction(e -> {
			getCharacterFields(selectedCharacter);

		});
	}

	/**
	 * Populate Character object with editor fields content
	 * @param c
	 */
	private Character getCharacterFields(Character c) {

		c.setDescription(descriptionField.getText());
		ObservableList<String> traits = traitsList.getItems();
		for (String t : traits) {
			selectedCharacter.addTrait(t);
		}

		if (c instanceof SuperCharacter) {
			try {
				((SuperCharacter) selectedCharacter).setPowerRanking(Integer.parseInt(powerLevelField.getText()));
			} catch (IllegalPowerRankingException ex) {
				ex.printStackTrace();
			}

			ObservableList<String> powers = powerList.getItems();
			for (String p : powers) {
				((SuperCharacter) selectedCharacter).addPower(p);
			}
		}

		return c;
	}

	/**
	 * Initialize character editor fields with provided character object
	 * @param c
	 */
	private void setCharacterFields(Character c) {
		characterNameDisplay.setText(c.getName());
		descriptionField.setText(c.getDescription());
		selectedImage = new Image(c.getImagePath());
		imageView.setImage(selectedImage);

		ObservableList<String> traits = FXCollections.observableArrayList();
		for (String t : c.traits) {
			traits.add(t);
		}
		traitsList.setItems(traits);

		if (c instanceof SuperCharacter) {
			powerLevelField.setText(""+((SuperCharacter) c).getPowerRanking());
			ObservableList<String> powers = FXCollections.observableArrayList();
			for (String p : ((SuperCharacter) c).powers) {
				powers.add(p);
			}
			powerList.setItems(powers);
		}
	}

	/**
	 * Handles search event
	 */
	private void searchCharacterEvent() {
		search.setOnAction(e -> {
			selectedCharacter = model.search(enterCharacterNameSearch.getText());

		});
	}


	/**
	 * Handles clear search event
	 */
	private void clearSearchEvent() {
		clearSearch.setOnAction(e -> {
			characterList.setItems(characterDisplayList);
		});
	}







}
