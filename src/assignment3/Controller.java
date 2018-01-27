package assignment3;

import java.io.File;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Controller class for the Character Editor.
 * @author Ali Nawaz Maan
 */
public class Controller implements Initializable{
	
	private Model model;
	private final FileChooser fileChooser = new FileChooser();
	private ObservableList<String> characterDisplayList;
	private Character selectedCharacter;
	private Image selectedImage;
	private boolean databaseLoaded = false;

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
	@FXML
	private Button addTraitBtn;
	@FXML
	private Button removeTraitBtn;
	@FXML
	private Button addPowerBtn;
	@FXML
	private Button removePowerBtn;


	/**
	 * Constructor
	 */
	public Controller() {
		this.model = new Model();
		this.characterDisplayList = FXCollections.observableArrayList();
	}

	/**
	 * Initializer
	 * @param location location
	 * @param resources resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Handling click events
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
		editTraitsEvent();
		editPowersEvent();
		saveCharacterChangesEvent();
		addTraitEvent();
		removeTraitEvent();
		addPowerEvent();
		removePowerEvent();
	}

	/**
	 * Handles load database button event
	 */
	private void loadDatabaseEvent() {
		loadDatabase.setOnAction(e -> {
			File file = fileChooser.showOpenDialog(new Stage());
			if (file != null) {
				displayDatabase(file.getPath());
				databaseLoaded = true;
			}
		});
	}

	/**
	 * Displays the intended database into the characters list view.
	 * @param filePath Path of the database file to load and display.
	 */
	private void displayDatabase(String filePath) {
		try {
			model.loadDatabase(filePath);
		}catch (Exception e) {
			showAlert(1, "Error loading database", "Error loading database", e.getMessage());
			return;
		}
		characterDisplayList = FXCollections.observableArrayList();
		characterDisplayList.addAll(model.getCharacters());

		Comparator<String> comparator = new StringComparator();
		characterDisplayList.sort(comparator);

		characterList.setItems(characterDisplayList);

		showAlert(2, "Database loaded", "Database Loaded", "Database has been loaded. " +
				"Please save after making changes in characters.");
	}

	/**
	 * Handles new character creation event
	 */
	private void createCharacterEvent() {
		newCharacter.setOnAction(e -> {
			if (databaseLoadCheck()) {
				if (!characterNameCreate.getText().isEmpty()) {
					if (characterDisplayList.contains(characterNameCreate.getText())) {
						showAlert(1, "Character already created", "Character already created", "Please " +
								"try to provide some other name for your character.");
					}else {
						selectedCharacter = model.createCharacter(characterNameCreate.getText());
						characterDisplayList.add(selectedCharacter.getName());
						characterList.setItems(characterDisplayList);
						selectItem(selectedCharacter.getName());
						characterNameCreate.setText("");
					}
				}
			}
		});
	}

	/**
	 * Handles new super character creation event
	 */
	private void createSuperCharacterEvent() {
		newSuperCharacter.setOnAction(e -> {
			if (databaseLoadCheck()) {
				if (!characterNameCreate.getText().isEmpty()) {
					if (characterDisplayList.contains(characterNameCreate.getText())) {
						showAlert(1, "Character already created", "Character already created", "Please " +
								"try to provide some other name for your character.");
					}else {
						selectedCharacter = model.createSuperCharacter(characterNameCreate.getText());
						characterDisplayList.add(selectedCharacter.getName());
						characterList.setItems(characterDisplayList);
						selectItem(selectedCharacter.getName());
						characterNameCreate.setText("");
					}
				}
			}
		});
	}

	/**
	 * Database creation event
	 */
	private void createDatabaseEvent() {
		createDatabase.setOnAction(e -> {
			if (!enterFilename.getText().isEmpty()) {
				try {
					model.createDatabase(enterFilename.getText());
				}catch (Exception exc) {
					showAlert(1, "Error creating database", "Error creating database", exc.getMessage());
				}

				databaseLoaded = true;
				characterDisplayList = FXCollections.observableArrayList();
				characterList.setItems(characterDisplayList);
				showAlert(2, "Database created", "Database created", "Your database has been created. " +
						"Please add characters and super characters to edit their characteristics and save the database.");

			}else {
				showAlert(1,"Empty filename", "Empty filename", "Please enter filename of the database to be created");
			}
		});
	}

	/**
	 * Saves the database
	 */
	private void saveDatabaseEvent() {
		saveDatabase.setOnAction(e -> {
			if (databaseLoadCheck() && !characterDisplayList.isEmpty()) {
				model.save();
				showAlert(2, "Database saved", "Database saved", "Your changes to the database are saved.");
			}else {
				showAlert(1, "No characters to save", "No characters to save", "Please add characters to save.");
			}
		});
	}

	/**
	 * Handles Character selection event from the list
	 */
	private void selectCharacterEvent() {
		characterList.setOnMouseClicked(e -> {
			if (databaseLoadCheck()) {
				if (!characterDisplayList.isEmpty()) {
					String selectedName = characterList.getSelectionModel().getSelectedItem().toString();
					Character c = model.search(selectedName);
					selectedCharacter = c;
					setCharacterFields(selectedCharacter);
				}
			}
		});
	}

	/**
	 * Handles delete character event
	 */
	private void deleteCharacterEvent() {
		deleteCharacter.setOnAction(e -> {
			if (databaseLoadCheck()) {
				String name = characterList.getSelectionModel().getSelectedItem().toString();
				model.delete(name);
				characterDisplayList.remove(name);
				characterList.setItems(characterDisplayList);
			}
		});
	}

	/**
	 * Handles image change exvent
	 */
	private void changeImageEvent() {
		changeImage.setOnAction(e -> {
			if (databaseLoadCheck()) {
				File file = fileChooser.showOpenDialog(new Stage());
				if (file != null) {
					String filePath = getImagePath(file);
					selectedCharacter.setImagePath(filePath);
					selectedImage = new Image(selectedCharacter.getImagePath());
					imageView.setImage(selectedImage);
				}
			}
		});
	}

	/**
	 * Handles traits editing event
	 */
	private void editTraitsEvent() {
		traitsList.setCellFactory(TextFieldListCell.forListView());
		traitsList.setOnEditCommit(new EventHandler<ListView.EditEvent>() {
			@Override
			public void handle(ListView.EditEvent event) {
				String editingTrait = traitsList.getItems().get(traitsList.getEditingIndex()).toString();
				selectedCharacter.removeTrait(editingTrait);

				int index = event.getIndex();
				String newTrait = event.getNewValue().toString();
				traitsList.getItems().set(index, newTrait);
				traitsList.setItems(traitsList.getItems());
				selectedCharacter.addTrait(newTrait);
			}
		});
	}

	/**
	 * Handles adding a new trait event
	 */
	private void addTraitEvent() {
		addTraitBtn.setOnAction(e -> {
			traitsList.getItems().add("New Trait");
			traitsList.setItems(traitsList.getItems());
		});
	}

	/**
	 * Handles removing a trait event
	 */
	private void removeTraitEvent() {
		removeTraitBtn.setOnAction(e -> {
			String removal = traitsList.getSelectionModel().getSelectedItem().toString();
			traitsList.getItems().remove(removal);
			traitsList.setItems(traitsList.getItems());
			selectedCharacter.removeTrait(removal);

		});
	}

	/**
	 * Handles powers editing event
	 */
	private void editPowersEvent() {
		powerList.setCellFactory(TextFieldListCell.forListView());
		powerList.setOnEditCommit(new EventHandler<ListView.EditEvent>() {
			@Override
			public void handle(ListView.EditEvent event) {
				String editingPower = powerList.getItems().get(powerList.getEditingIndex()).toString();
				SuperCharacter s = (SuperCharacter) selectedCharacter;
				s.removePower(editingPower);

				int index = event.getIndex();
				String newPower = event.getNewValue().toString();
				powerList.getItems().set(index, newPower);
				powerList.setItems(powerList.getItems());
				s.addPower(newPower);

				selectedCharacter = s;
			}
		});
	}

	/**
	 * Handles adding a new power event
	 */
	private void addPowerEvent() {
		addPowerBtn.setOnAction(e -> {
			powerList.getItems().add("New Power");
			powerList.setItems(powerList.getItems());
		});
	}

	/**
	 * Handles removing a power event
	 */
	private void removePowerEvent() {
		removePowerBtn.setOnAction(e -> {
			String removal = powerList.getSelectionModel().getSelectedItem().toString();
			powerList.getItems().remove(removal);
			powerList.setItems(powerList.getItems());

			SuperCharacter s = (SuperCharacter) selectedCharacter;
			s.removePower(removal);
			selectedCharacter = s;

		});
	}

	/**
	 * Handles save character changes event
	 */
	private void saveCharacterChangesEvent() {
		saveCharacterChanges.setOnAction(e -> {
			if (databaseLoadCheck()) {
				Character c = getCharacterFields(selectedCharacter);
				model.addCharacter(c);

				showAlert(2, "Changes saved", "Changes saved", "Character changes has been saved." +
						" Please save the database for changes to take effect on the database file.");
			}

		});
	}

	/**
	 * Handles search event
	 */
	private void searchCharacterEvent() {
		search.setOnAction(e -> {
			if (databaseLoadCheck()) {
				String selectedName = enterCharacterNameSearch.getText();
				selectedCharacter = model.search(selectedName);

				if (selectedCharacter == null) {
					showAlert(1, "Character not found", "Character not found", selectedName + " is not in the database");
				} else {
					ObservableList<String> searchResults = FXCollections.observableArrayList();
					searchResults.add(selectedCharacter.getName());
					characterList.setItems(searchResults);
				}
			}
		});
	}


	/**
	 * Handles clear search event
	 */
	private void clearSearchEvent() {
		clearSearch.setOnAction(e -> {
			if (databaseLoadCheck()) {
				characterList.setItems(characterDisplayList);
				enterCharacterNameSearch.setText("");
			}
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
			c.addTrait(t);
		}

		if (c instanceof SuperCharacter) {
			try {
				((SuperCharacter) c).setPowerRanking(Integer.parseInt(powerLevelField.getText()));
			} catch (IllegalPowerRankingException ex) {
				ex.printStackTrace();
			}

			ObservableList<String> powers = powerList.getItems();
			for (String p : powers) {
				((SuperCharacter) c).addPower(p);
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
			powerLevelField.setDisable(false);
			powerList.setDisable(false);

			powerLevelField.setText(""+((SuperCharacter) c).getPowerRanking());
			ObservableList<String> powers = FXCollections.observableArrayList();
			for (String p : ((SuperCharacter) c).powers) {
				powers.add(p);
			}
			powerList.setItems(powers);
		}else {
			powerLevelField.setText("");
			powerLevelField.setDisable(true);

			powerList.setItems(FXCollections.observableArrayList());
			powerList.setDisable(true);

		}
	}


	/**
	 * Selects, focusses and scrolls to a selected item from the characterlist listview.
	 * @param item Item to be selected from the list view
	 */
	private void selectItem(String item) {
		int i = characterDisplayList.indexOf(item);
		characterList.scrollTo(i);
		characterList.getFocusModel().focus(i);
		characterList.getSelectionModel().select(i);

		Character c = model.search(characterList.getSelectionModel().getSelectedItem().toString());
		selectedCharacter = c;
		setCharacterFields(selectedCharacter);
	}

	/**
	 * Shows alert message
	 * @param title title of the alert window
	 * @param description header of the alert box
	 * @param content content description of alert box
	 */
	private void showAlert(int type ,String title, String description, String content) {

		Alert alert;

		if (type == 1) {
			alert = new Alert(Alert.AlertType.ERROR);
		}else {
			alert = new Alert(Alert.AlertType.INFORMATION);
		}
		alert.setTitle(title);
		alert.setHeaderText(description);
		alert.setContentText(content);
		alert.show();
	}


	/**
	 * Checks if database is loaded or not.
	 * Shows alert to prompt user to load the database first.
	 * @return true if database is loaded, false otherwise.
	 */
	private boolean databaseLoadCheck() {
		if (!databaseLoaded) {
			showAlert(1, "Database not loaded", "Database not loaded", "Please load or create a database" +
					" first to perform this action");
			return false;
		}
		return true;
	}

	private String getImagePath(File file) {
		String[] filePath = file.getPath().split("/");
		return "images/" + filePath[filePath.length-1];
	}

	/**
	 * String comparator class
	 */
	private class StringComparator implements Comparator<String> {

		@Override
		public int compare(String o1, String o2) {

			return o1.compareTo(o2);
		}
	}
}
