package wdk.gui;

import java.io.File;
import java.util.ArrayList;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import static wdk.WDK_StartupConstants.PATH_FLAGS;
import static wdk.WDK_StartupConstants.PATH_PLAYERS;
import wdk.data.Contract;
import wdk.data.Hitter;
import wdk.data.MLBTeam;
import wdk.data.Pitcher;
import static wdk.gui.WDK_GUI.CLASS_SUBHEADING_LABEL;

/**
 *
 * @author Yael
 */
public class PlayerDialog  extends Stage {
    // THIS IS THE OBJECT DATA BEHIND THIS UI
    Player newPlayer;
    
    // GUI CONTROLS FOR OUR DIALOG
    GridPane gridPane;
    FlowPane flowPane;
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
    
    
    GridPane editGridPane;
    Scene editDialogScene;
    Label editHeadingLabel;
    Image playerPicture;
    Image playerFlag;
    Label playerName;
    Label playerQP;
    Label fantasyTeamLabel;
    Label positionLabel;
    Label contractLabel;
    Label salaryLabel;
    ComboBox fantasyTeamComboBox;
    ComboBox positionComboBox;
    ComboBox contractComboBox;
    TextField salaryTextField;
    
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
    public static final String FANTASY_TEAM_PROMPT = "Fantasy Team: ";
    public static final String POSITION_PROMPT = "Position: ";
    public static final String CONTRACT_PROMPT = "Contract: ";
    public static final String SALARY_PROMPT = "Salary ($):";
    public static final String EDIT_PLAYER_TITLE = "Edit Player";
    public static final String PLAYER_DETAILS_HEADING = "Player Details";
    public static final String BLANK_PIC = "AAA_PhotoMissing";
    
    /**
     * Initializes this dialog so that it can be used for adding
     * new players.
     * 
     * @param primaryStage The owner of this modal dialog.
     */
    public PlayerDialog(Stage primaryStage, Draft draft,  MessageDialog messageDialog) {       
        // MAKE THIS DIALOG MODAL, MEANING OTHERS WILL WAIT
        // FOR IT WHEN IT IS DISPLAYED
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);

        // FIRST OUR CONTAINER
        flowPane = new FlowPane();
        flowPane.setHgap(10);
        gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 20, 20, 20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPrefSize(350, 300);
        
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
        oneBCheckBox = new CheckBox("1B");
        threeBCheckBox = new CheckBox("3B");
        twoBCheckBox = new CheckBox("2B"); 
        SSCheckBox = new CheckBox("SS");
        OFCheckBox = new CheckBox("OF");      
        PCheckBox = new CheckBox("P");
        
        CCheckBox.setOnAction(e -> handleCheckBoxAction(e));
        oneBCheckBox.setOnAction(e -> handleCheckBoxAction(e));
        threeBCheckBox.setOnAction(e -> handleCheckBoxAction(e));
        twoBCheckBox.setOnAction(e -> handleCheckBoxAction(e));
        SSCheckBox.setOnAction(e -> handleCheckBoxAction(e));
        OFCheckBox.setOnAction(e -> handleCheckBoxAction(e)); 
        PCheckBox.setOnAction(e -> handleCheckBoxAction(e));
      
        
        // AND FINALLY, THE BUTTONS
        completeButton = new Button(COMPLETE);
        cancelButton = new Button(CANCEL);
        
        // REGISTER EVENT HANDLERS FOR OUR BUTTONS
        EventHandler completeCancelHandler = (EventHandler<ActionEvent>) (ActionEvent ae) -> {
            Button sourceButton = (Button)ae.getSource();
            PlayerDialog.this.selection = sourceButton.getText();
            PlayerDialog.this.hide();
        };
        completeButton.setOnAction(completeCancelHandler);
        cancelButton.setOnAction(completeCancelHandler);

        // NOW LET'S ARRANGE THEM ALL AT ONCE
        flowPane.getChildren().add(CCheckBox);
        flowPane.getChildren().add(oneBCheckBox);
        flowPane.getChildren().add(threeBCheckBox);
        flowPane.getChildren().add(twoBCheckBox);
        flowPane.getChildren().add(SSCheckBox);
        flowPane.getChildren().add(OFCheckBox);
        flowPane.getChildren().add(PCheckBox);
        
        gridPane.add(headingLabel, 0, 0, 2, 1);
        gridPane.add(firstNameLabel, 0, 1, 1, 1);
        gridPane.add(firstNameTextField, 1, 1, 1, 1);
        gridPane.add(lastNameLabel, 0, 2, 1, 1);
        gridPane.add(lastNameTextField, 1, 2, 1, 1);
        gridPane.add(proTeamLabel, 0, 3, 1, 1);
        gridPane.add(proTeamComboBox, 1, 3, 1, 1);
        gridPane.add(flowPane, 0, 6, 3, 1);
        gridPane.add(completeButton, 0, 8, 1, 1);
        gridPane.add(cancelButton, 1, 8, 1, 1);

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
        CCheckBox.setSelected(false);
        oneBCheckBox.setSelected(false);
        threeBCheckBox.setSelected(false);
        twoBCheckBox.setSelected(false);
        SSCheckBox.setSelected(false);
        OFCheckBox.setSelected(false);
        PCheckBox.setSelected(false);

        this.setScene(dialogScene);
        
        // AND OPEN IT UP
        this.showAndWait();
        
        return newPlayer;
    }    
    
    public boolean wasCompleteSelected() {
        return selection.equals(COMPLETE);
    }
    
    private void handleCheckBoxAction(ActionEvent e) {
        String result = "";                
        if(CCheckBox.isSelected()) {
            if(result.equals("")) {
                result = "C";
            }
            else
                result += "_" + "C";
        }
        if(oneBCheckBox.isSelected()) {
            if(result.equals("")) {
                result = "1B";
            }
            else
                result += "_" + "1B";
        }
        if(threeBCheckBox.isSelected()) {
            if(result.equals("")) {
                result = "3B";
            }
            else
                result += "_" + "3B";
        }
        if(twoBCheckBox.isSelected()) {
            if(result.equals("")) {
                result = "2B";
            }
            else
                result += "_" + "2B";
        }
        if(SSCheckBox.isSelected()) {
            if(result.equals("")) {
                result = "SS";
            }
            else
                result += "_" + "SS";
        }
        if(OFCheckBox.isSelected()) {
            if(result.equals("")) {
                result = "OF";
            }
            else
                result += "_" + "OF";
        }
        if(PCheckBox.isSelected()) {
            CCheckBox.setSelected(false);
            oneBCheckBox.setSelected(false);
            threeBCheckBox.setSelected(false);
            twoBCheckBox.setSelected(false);
            SSCheckBox.setSelected(false);
            OFCheckBox.setSelected(false);
            
            result = "P";
        }
        newPlayer.setQPOrRole(result);
    }
    
    public void showEditPlayerDialog(Draft draft, Player playerToEdit) {
        if(playerToEdit instanceof Hitter) {
            newPlayer = new Hitter();
            ((Hitter)newPlayer).setGamesPlayed(((Hitter)playerToEdit).getGamesPlayed());
            ((Hitter)newPlayer).setABStat(((Hitter)playerToEdit).getABStat());
        }
        else if(playerToEdit instanceof Pitcher) {
            newPlayer = new Pitcher();
            ((Pitcher)newPlayer).setERStat(((Pitcher)playerToEdit).getERStat());
            ((Pitcher)newPlayer).setBBStat(((Pitcher)playerToEdit).getBBStat());
            ((Pitcher)newPlayer).setIPStat(((Pitcher)playerToEdit).getIPStat());
        }

        newPlayer.setFirstName(playerToEdit.getFirstName());
        newPlayer.setLastName(playerToEdit.getLastName());
        newPlayer.setNOB(playerToEdit.getNOB());
        newPlayer.setContract(playerToEdit.getContract());
        newPlayer.setMLBTeam(playerToEdit.getMLBTeam());
        newPlayer.setFantasyTeam(playerToEdit.getFantasyTeam());
        newPlayer.setSalary(playerToEdit.getSalary());
        newPlayer.setAge(playerToEdit.getAge());
        newPlayer.setYOB(playerToEdit.getYOB());
        newPlayer.setEstVal(playerToEdit.getEstVal());
        newPlayer.setHStat(playerToEdit.getHStat());
        newPlayer.setNotes(playerToEdit.getNotes());
        newPlayer.setROrWStat(playerToEdit.getROrWStat());
        newPlayer.setHROrSVStat(playerToEdit.getHROrSVStat());
        newPlayer.setRBIOrKStat(playerToEdit.getRBIOrKStat());
        newPlayer.setSBOrERAStat(playerToEdit.getSBOrERAStat());
        newPlayer.setBAOrWHIPStat(playerToEdit.getBAOrWHIPStat());
        newPlayer.setQPOrRole(playerToEdit.getQPOrRole());
        newPlayer.setChosenPosition(playerToEdit.getChosenPosition());
        
        
        // FIRST OUR CONTAINER
        editGridPane = new GridPane();
        editGridPane.setPadding(new Insets(10, 20, 20, 20));
        editGridPane.setHgap(10);
        editGridPane.setVgap(10);
        editGridPane.setPrefSize(300, 400);
        
        // PUT THE HEADING IN THE GRID
        editHeadingLabel = new Label(PLAYER_DETAILS_HEADING);
        editHeadingLabel.getStyleClass().add(CLASS_HEADING_LABEL);
    
        // NOW THE PLAYER IMAGE
        String name = newPlayer.getLastName() + newPlayer.getFirstName();
        File file = new File(PATH_PLAYERS + name + ".jpg");
        if(file.exists()) {
            playerPicture = new Image("file:" + PATH_PLAYERS + name + ".jpg");
        }
        else {
            playerPicture = new Image("file:" + PATH_PLAYERS + BLANK_PIC + ".jpg");
        }
        ImageView faceView = new ImageView();
        faceView.setImage(playerPicture);
        
        // NOW THE PLAYER FLAG
        playerFlag = new Image("file:" + PATH_FLAGS + newPlayer.getNOB() + ".png");
        ImageView flagView = new ImageView();
        flagView.setImage(playerFlag);

        // NOW THE PLAYER NAME
        playerName = new Label(newPlayer.getFirstName() + " " + newPlayer.getLastName());
        playerName.getStyleClass().add(CLASS_SUBHEADING_LABEL);
        
        // NOW THE PLAYER QP
        playerQP = new Label(newPlayer.getQPOrRole());
        playerQP.getStyleClass().add(CLASS_PROMPT_LABEL);
        
        // NOW THE PLAYER FANTASY TEAM & POSITION
        fantasyTeamLabel = new Label(FANTASY_TEAM_PROMPT);
        fantasyTeamComboBox = new ComboBox();
        ObservableList<String> fantasyTeamChoices = FXCollections.observableArrayList();
        fantasyTeamChoices.add("Free Agent");
        for(int i = 0; i < draft.getListOfTeams().size(); i++) {
            fantasyTeamChoices.add(draft.getListOfTeams().get(i).getTeamName());
        }
        fantasyTeamComboBox.setItems(fantasyTeamChoices);
        fantasyTeamComboBox.setOnAction(e -> {
            newPlayer.setFantasyTeam((String)fantasyTeamComboBox.getValue());
        });
        
        positionComboBox = new ComboBox();
        fantasyTeamComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldTeam, Object newTeam) {
                String oldTeamName = (String)oldTeam;
                String newTeamName = (String)newTeam;
                
                if(newTeamName.equals("Free Agent")) {
                    positionComboBox.setDisable(true);
                    contractComboBox.setDisable(true);
                    salaryTextField.setDisable(true);
                }
                else {
                    ObservableList positionChoices = FXCollections.observableArrayList();
                    String arrayQPString = newPlayer.getQPOrRole();
 
                    if(arrayQPString.contains("1B") || arrayQPString.contains("3B")) {
                        if(draft.getTeamWithName(newTeamName).getCICount() < 1) {
                            positionChoices.add("CI");
                        }
                    }
                    if(arrayQPString.contains("1B")) {
                        if(draft.getTeamWithName(newTeamName).get1BCount() < 1) {
                            positionChoices.add("1B");
                        }
                    }
                    if(arrayQPString.contains("3B")) {
                        if(draft.getTeamWithName(newTeamName).get3BCount() < 1) {
                            positionChoices.add("3B");
                        }
                    }
                    if(arrayQPString.contains("C")) {
                        if(draft.getTeamWithName(newTeamName).getCCount() < 2) {
                            positionChoices.add("C");
                        }
                    }
                    if(arrayQPString.contains("2B") || arrayQPString.contains("SS")) {
                        if(draft.getTeamWithName(newTeamName).getSSCount() < 1) {
                            positionChoices.add("MI");
                        }
                    }
                    if(arrayQPString.contains("2B")) {
                        if(draft.getTeamWithName(newTeamName).get2BCount() < 1) {
                            positionChoices.add("2B");
                        }
                    }
                    if(arrayQPString.contains("SS")) {
                        if(draft.getTeamWithName(newTeamName).getSSCount() < 1) {
                            positionChoices.add("SS");
                        }
                    }
                    if(!arrayQPString.contains("P")) {
                        if(draft.getTeamWithName(newTeamName).getUCount() < 1) {
                            positionChoices.add("U");
                        }
                    }
                    if(arrayQPString.contains("P")) {
                        if(draft.getTeamWithName(newTeamName).getPCount() < 9) {
                            positionChoices.add("P");
                        }
                    }
                    if(arrayQPString.contains("OF")) {
                        if(draft.getTeamWithName(newTeamName).getOFCount() < 5) {
                            positionChoices.add("OF");
                        }
                    }
                    positionComboBox.setItems(positionChoices);
                }
                positionComboBox.setOnAction(e -> {
                    newPlayer.setChosenPosition((String)positionComboBox.getValue());
                });
            }
        });
        
        // NOW THE PLAYER POSITION
        positionLabel = new Label(POSITION_PROMPT);
        
        // NOW THE PLAYER CONTRACT
        contractLabel = new Label(CONTRACT_PROMPT);
        contractComboBox = new ComboBox();
        ObservableList<String> contractChoices = FXCollections.observableArrayList();
        for(Contract c: Contract.values()) {
            contractChoices.add(c.toString());
        }
        contractComboBox.setItems(contractChoices);
        contractComboBox.setOnAction(e -> {
            newPlayer.setContract(Contract.valueOf(contractComboBox.getValue().toString()));
        });
        
        // NOW THE PLAYER SALARY
        salaryLabel = new Label(SALARY_PROMPT);
        salaryTextField = new TextField();
        salaryTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                newPlayer.setSalary(Integer.parseInt(newValue));
            } catch(NumberFormatException n) {
            }
        });

        // AND FINALLY, THE BUTTONS
        completeButton = new Button(COMPLETE);
        cancelButton = new Button(CANCEL);
        
        // REGISTER EVENT HANDLERS FOR OUR BUTTONS
        EventHandler completeCancelHandler = (EventHandler<ActionEvent>) (ActionEvent ae) -> {
            Button sourceButton = (Button)ae.getSource();
            PlayerDialog.this.selection = sourceButton.getText();
            PlayerDialog.this.hide();
        };
        completeButton.setOnAction(completeCancelHandler);
        cancelButton.setOnAction(completeCancelHandler);

        // NOW LET'S ARRANGE THEM ALL AT ONCE
        //col, row, colspan, rowspan
        editGridPane.add(editHeadingLabel, 0, 0, 2, 1);
        editGridPane.add(faceView, 0, 1, 1, 4);
        editGridPane.add(flagView, 1, 1, 1, 1);
        editGridPane.add(playerName, 1, 2, 3, 1);
        editGridPane.add(playerQP, 1, 4, 1, 1);
        editGridPane.add(fantasyTeamLabel, 0, 5, 1, 1);
        editGridPane.add(fantasyTeamComboBox, 1, 5, 3, 1);
        editGridPane.add(positionLabel, 0, 6, 1, 1);
        editGridPane.add(positionComboBox, 1, 6, 3, 1);
        editGridPane.add(contractLabel, 0, 7, 1, 1);
        editGridPane.add(contractComboBox, 1, 7, 3, 1);
        editGridPane.add(salaryLabel, 0, 8, 1, 1);
        editGridPane.add(salaryTextField, 1, 8, 3, 1);
        editGridPane.add(completeButton, 1, 9, 1, 1);
        editGridPane.add(cancelButton, 2, 9, 1, 1);

        // AND PUT THE GRID PANE IN THE WINDOW
        editDialogScene = new Scene(editGridPane);
        editDialogScene.getStylesheets().add(PRIMARY_STYLE_SHEET);
        this.setScene(editDialogScene);
        
        setTitle(EDIT_PLAYER_TITLE);
               
        // AND OPEN IT UP
        this.showAndWait();
    }
}
