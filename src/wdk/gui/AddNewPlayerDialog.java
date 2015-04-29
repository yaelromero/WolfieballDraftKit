package wdk.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import wdk.WDK_PropertyType;
import wdk.data.Draft;
import wdk.data.Player;
import static wdk.gui.WDK_GUI.CLASS_HEADING_LABEL;
import static wdk.gui.WDK_GUI.CLASS_PROMPT_LABEL;
import static wdk.gui.WDK_GUI.PRIMARY_STYLE_SHEET;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import wdk.data.Hitter;
import wdk.data.MLBTeam;
import wdk.data.Pitcher;

/**
 *
 * @author Yael
 */
public class AddNewPlayerDialog  extends Stage {
    // THIS IS THE OBJECT DATA BEHIND THIS UI
    Player newPlayer;
    
    // GUI CONTROLS FOR OUR DIALOG
    GridPane gridPane;
    Scene dialogScene;
    Label headingLabel;
    Label firstNameLabel;
    TextField firstNameTextField;
    Label lastNameLabel;
    TextField lastNameTextField;
    Label proTeamLabel;
    ComboBox proTeamComboBox;
    CheckBox CCheckBox;
    CheckBox oneBCheckBox;
    CheckBox threeBCheckBox;
    CheckBox twoBCheckBox;
    CheckBox SSCheckBox;
    CheckBox OFCheckBox;
    CheckBox PCheckBox;
    Button completeButton;
    Button cancelButton;
    
    // THIS IS FOR KEEPING TRACK OF WHICH BUTTON THE USER PRESSED
    String selection;
    
    // CONSTANTS FOR OUR UI
    public static final String COMPLETE = "Complete";
    public static final String CANCEL = "Cancel";
    public static final String FIRST_NAME_PROMPT = "First Name: ";
    public static final String LAST_NAME_PROMPT = "Last Name: ";
    public static final String PRO_TEAM_PROMPT = "Pro Team: ";
    public static final String PLAYER_HEADING = "Player Details";
    public static final String ADD_PLAYER_TITLE = "Add New Player";
    
    /**
     * Initializes this dialog so that it can be used for adding
     * new players.
     * 
     * @param primaryStage The owner of this modal dialog.
     */
    public AddNewPlayerDialog(Stage primaryStage, Draft draft,  MessageDialog messageDialog) {       
        // MAKE THIS DIALOG MODAL, MEANING OTHERS WILL WAIT
        // FOR IT WHEN IT IS DISPLAYED
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        
        // FIRST OUR CONTAINER
        gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 20, 20, 20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        
        // PUT THE HEADING IN THE GRID
        headingLabel = new Label(PLAYER_HEADING);
        headingLabel.getStyleClass().add(CLASS_HEADING_LABEL);
    
        // NOW THE FIRST NAME
        firstNameLabel = new Label(FIRST_NAME_PROMPT);
        firstNameLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        firstNameTextField = new TextField();
        firstNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            newPlayer.setFirstName(newValue);
        });
        
        // AND THE LAST NAME
        lastNameLabel = new Label(LAST_NAME_PROMPT);
        lastNameLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        lastNameTextField = new TextField();
        lastNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            newPlayer.setLastName(newValue);
        });
        
        // AND THE PRO TEAM
        proTeamLabel = new Label(PRO_TEAM_PROMPT);
        proTeamLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        proTeamComboBox = new ComboBox();
        ObservableList<String> proTeamChoices = FXCollections.observableArrayList();
        for (MLBTeam m: MLBTeam.values()) {
            proTeamChoices.add(m.toString());
        }
        proTeamComboBox.setItems(proTeamChoices);
        proTeamComboBox.setOnAction(e -> {
            newPlayer.setMLBTeam((String)proTeamComboBox.getValue());
        });
        
        // AND THE POSITION CHECK BOXES (BOTH HITTER AND PITCHER POSITION CANNOT BE SELECTED)
           
        CCheckBox = new CheckBox("C");
        CCheckBox.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(newValue == true) {
                newPlayer.addOnToQPOrRole("C");
            }
            else
                newPlayer.removeQPOrRole("C");
        });
        oneBCheckBox = new CheckBox("1B");
        oneBCheckBox.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(newValue == true) {
                newPlayer.addOnToQPOrRole("1B");
            }
            else
                newPlayer.removeQPOrRole("1B");
        });
        
        threeBCheckBox = new CheckBox("3B");
        threeBCheckBox.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(newValue == true) {
                newPlayer.addOnToQPOrRole("3B");
            }
            else
                newPlayer.removeQPOrRole("3B");
        });
       
        twoBCheckBox = new CheckBox("2B");
        twoBCheckBox.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(newValue == true) {
                newPlayer.addOnToQPOrRole("2B");
            }
            else
                newPlayer.removeQPOrRole("2B");
        });
        
        SSCheckBox = new CheckBox("SS");
        SSCheckBox.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(newValue == true) {
                newPlayer.addOnToQPOrRole("SS");
            }
            else
                newPlayer.removeQPOrRole("SS");
        });
        
        OFCheckBox = new CheckBox("OF");
        OFCheckBox.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(newValue == true) {
                newPlayer.addOnToQPOrRole("OF");
            }
            else
                newPlayer.removeQPOrRole("OF");
        });
       
        PCheckBox = new CheckBox("P");
        PCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue == true) {
                    newPlayer.addOnToQPOrRole("P");
                }
                else
                    newPlayer.removeQPOrRole("P");
            }
        });
      
        
        // AND FINALLY, THE BUTTONS
        completeButton = new Button(COMPLETE);
        cancelButton = new Button(CANCEL);
        
        // REGISTER EVENT HANDLERS FOR OUR BUTTONS
        EventHandler completeCancelHandler = (EventHandler<ActionEvent>) (ActionEvent ae) -> {
            Button sourceButton = (Button)ae.getSource();
            AddNewPlayerDialog.this.selection = sourceButton.getText();
            AddNewPlayerDialog.this.hide();
        };
        completeButton.setOnAction(completeCancelHandler);
        cancelButton.setOnAction(completeCancelHandler);

        // NOW LET'S ARRANGE THEM ALL AT ONCE
        gridPane.add(headingLabel, 0, 0, 2, 1);
        gridPane.add(firstNameLabel, 0, 1, 1, 1);
        gridPane.add(firstNameTextField, 1, 1, 1, 1);
        gridPane.add(lastNameLabel, 0, 2, 1, 1);
        gridPane.add(lastNameTextField, 1, 2, 1, 1);
        gridPane.add(proTeamLabel, 0, 3, 1, 1);
        gridPane.add(proTeamComboBox, 1, 3, 1, 1);
        gridPane.add(CCheckBox, 0, 4, 1, 1);
        gridPane.add(oneBCheckBox, 1, 4, 1, 1);
        gridPane.add(threeBCheckBox, 2, 4, 1, 1);
        gridPane.add(twoBCheckBox, 3, 4, 1, 1);
        gridPane.add(SSCheckBox, 4, 4, 1, 1);
        gridPane.add(OFCheckBox, 5, 4, 1, 1);
        gridPane.add(PCheckBox, 6, 4, 1, 1);
        gridPane.add(completeButton, 0, 5, 1, 1);
        gridPane.add(cancelButton, 1, 5, 1, 1);

        // AND PUT THE GRID PANE IN THE WINDOW
        dialogScene = new Scene(gridPane);
        dialogScene.getStylesheets().add(PRIMARY_STYLE_SHEET);
        this.setScene(dialogScene);
    }
    
    /**
     * Accessor method for getting the selection the user made.
     * 
     * @return Either YES, NO, or CANCEL, depending on which
     * button the user selected when this dialog was presented.
     */
    public String getSelection() {
        return selection;
    }
    
    public Player getPlayer() { 
        return newPlayer;
    }
    
    /**
     * This method loads a custom message into the label and
     * then pops open the dialog.
     * 
     * @return Player
     */
    public Player showAddPlayerDialog() {
        // SET THE DIALOG TITLE
        setTitle(ADD_PLAYER_TITLE);
        
        // RESET THE LECTURE ITEM OBJECT WITH DEFAULT VALUES
        if(PCheckBox.isSelected()) {
            newPlayer = new Pitcher();
        }
        else
            newPlayer = new Hitter();
        
        // LOAD THE UI STUFF
        firstNameTextField.setText(newPlayer.getFirstName());
        lastNameTextField.setText(newPlayer.getLastName());
        proTeamComboBox.setValue(newPlayer.getMLBTeam());
      
        
        // AND OPEN IT UP
        this.showAndWait();
        
        return newPlayer;
    }
    
    public boolean wasCompleteSelected() {
        return selection.equals(COMPLETE);
    }
}
