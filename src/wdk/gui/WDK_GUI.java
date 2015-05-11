package wdk.gui;

import static wdk.WDK_StartupConstants.*;
import wdk.WDK_PropertyType;
import wdk.controller.FileController;
import wdk.file.DraftFileManager;
import wdk.file.DraftSiteExporter;
import wdk.data.Draft;
import wdk.data.DraftDataManager;
import wdk.data.DraftDataView;
import wdk.data.Player;
import wdk.data.Team;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;
import properties_manager.PropertiesManager;
import wdk.controller.DraftEditController;
import wdk.controller.PlayerController;
import wdk.controller.ScreenController;
import wdk.controller.TeamController;
import wdk.data.Contract;
import wdk.data.MLBTeam;

/**
 * This class provides the Graphical User Interface for this application,
 * managing all the UI components for editing a Draft and exporting it to a
 * site.
 *
 * @author Yael
 */
public class WDK_GUI implements DraftDataView {
    
    // THESE CONSTANTS ARE FOR TYING THE PRESENTATION STYLE OF
    // THIS GUI'S COMPONENTS TO A STYLE SHEET THAT IT USES

    static final String PRIMARY_STYLE_SHEET = PATH_CSS + "wdk_style.css";
    static final String CLASS_BORDERED_PANE = "bordered_pane";
    static final String CLASS_SUBJECT_PANE = "subject_pane";
    static final String CLASS_HEADING_LABEL = "heading_label";
    static final String CLASS_SUBHEADING_LABEL = "subheading_label";
    static final String CLASS_PROMPT_LABEL = "prompt_label";
    static final String EMPTY_TEXT = "";
    static final int LARGE_TEXT_FIELD_LENGTH = 100;
    static final int SMALL_TEXT_FIELD_LENGTH = 20;

    // THIS MANAGES ALL OF THE APPLICATION'S DATA
    DraftDataManager dataManager;

    // THIS MANAGES DRAFT FILE I/O
    DraftFileManager draftFileManager;

    // THIS MANAGES EXPORTING OUR SITE PAGES
    DraftSiteExporter siteExporter;

    // THIS HANDLES INTERACTIONS WITH FILE-RELATED CONTROLS
    FileController fileController;
    
    // THIS HANDLES INTERACTIONS WITH DRAFT INFO CONTROLS
    DraftEditController draftController = new DraftEditController();
    
    // THIS HANDLES INTERACTIONS WITH TEAM-RELATED CONTROLS
    TeamController teamController;
    
    // THIS HANDLES INTERACTIONS WITH SCREEN-RELATED CONTROLS
    ScreenController screenController;
    
    // THIS HANDLES INTERACTIONS WITH PLAYER-RELATED CONTROLS
    PlayerController playerController;
    
    // THIS IS THE APPLICATION WINDOW
    Stage primaryStage;

    // THIS IS THE STAGE'S SCENE GRAPH
    Scene primaryScene;
    
    // THIS PANE ORGANIZES THE BIG PICTURE CONTAINERS FOR THE
    // APPLICATION GUI
    BorderPane wdkPane;
    
    // THIS IS THE TOP TOOLBAR AND ITS CONTROLS
    FlowPane fileToolbarPane;
    Button newDraftButton;
    Button loadDraftButton;
    Button saveDraftButton;
    Button exportDraftButton;
    Button exitButton;
    
    // THIS IS THE SCREEN TOOLBAR AND ITS CONTROLS
    FlowPane screenToolbarPane;
    Button fantasyTeamsScreenButton;
    Button playersScreenButton;
    Button fantasyStandingsScreenButton;
    Button draftScreenButton;
    Button MLBTeamsScreenButton;

    // WE'LL ORGANIZE OUR WORKSPACE COMPONENTS USING A BORDER PANE
    BorderPane workspacePane;
    boolean workspaceActivated;
    
    // WE'LL PUT THE WORKSPACE INSIDE A SCROLL PANE
    ScrollPane workspaceScrollPane;
    
    // THESE ARE THE CONTROLS FOR OUR FANTASY TEAMS SCREEN
    Label fantasyTeamsHeading;
    GridPane namePlusMinusEditSel;
    VBox fantasyTeamsPane;
    VBox startingLineupBox;
    VBox taxiSquadBox;
    Label startingLineupLabel;
    Label taxiSquadLabel;
    Button addTeamButton;
    Button removeTeamButton;
    Button editTeamButton;
    Label draftNameLabel;
    TextField draftNameTextField;
    Label selectFantasyTeamLabel;
    ComboBox selectFantasyTeamComboBox;
    TableView<Player> teamTable;
    TableView<Player> taxiSquadTable;
    TableColumn pPositionColumn;
    TableColumn pFirstNameColumn;
    TableColumn pLastNameColumn;
    TableColumn pProTeamColumn;
    TableColumn pPositionsColumn;
    TableColumn pRWColumn;
    TableColumn pHRSVColumn;
    TableColumn pRBIKColumn;
    TableColumn pSBERAColumn;
    TableColumn pBAWHIPColumn;
    TableColumn pEstimatedValueColumn;
    TableColumn pContractColumn;
    TableColumn pSalaryColumn;
    TableColumn taxiPositionColumn;
    TableColumn taxiFirstNameColumn;
    TableColumn taxiLastNameColumn;
    TableColumn taxiProTeamColumn;
    TableColumn taxiPositionsColumn;
    TableColumn taxiRWColumn;
    TableColumn taxiHRSVColumn;
    TableColumn taxiRBIKColumn;
    TableColumn taxiSBERAColumn;
    TableColumn taxiBAWHIPColumn;
    TableColumn taxiEstimatedValueColumn;
    TableColumn taxiContractColumn;
    TableColumn taxiSalaryColumn;
    
    // THESE ARE THE CONTROLS FOR OUR DRAFT SCREEN
    Label draftScreenLabel;
    VBox draftScreenPane;
    GridPane bestPlayPause;
    Button draftBest;
    Button startAutoDraft;
    Button pauseAutoDraft;
    TableView<Player> draftedPlayers;
    TableColumn pickNumberColumn;
    TableColumn draftedFirstNameColumn;
    TableColumn draftedLastNameColumn;
    TableColumn draftedTeamNameColumn;
    TableColumn draftedContractColumn;
    TableColumn draftedSalaryColumn;
    
    // THESE ARE THE CONTROLS FOR OUR STANDINGS SCREEN
    Label standingsScreenLabel;
    VBox standingsScreenPane;
    TableView<Team> fantasyTeamsTable;
    TableColumn teamNameColumn;
    TableColumn playersNeededColumn;
    TableColumn moneyLeftColumn;
    TableColumn moneyPerPlayerColumn;
    TableColumn aggRColumn;
    TableColumn aggHRColumn;
    TableColumn aggRBIColumn;
    TableColumn aggSBColumn;
    TableColumn aggBAColumn;
    TableColumn aggWColumn;
    TableColumn aggSVColumn;
    TableColumn aggKColumn;
    TableColumn aggERAColumn;
    TableColumn aggWHIPColumn;
    TableColumn totalPointsColumn;
    
    // THESE ARE THE CONTROLS FOR OUR MLB TEAM SCREEN
    Label MLBTeamsScreenLabel;
    GridPane proTeamLabelAndBox;
    VBox MLBTeamsScreenPane;
    Label selectProTeamLabel;
    ComboBox selectProTeamComboBox;
    TableView<Player> playersOnProTeamTable;
    TableColumn MLBPlayerFirstNameColumn;
    TableColumn MLBPlayerLastNameColumn;
    TableColumn MLBPlayerQPColumn;

    // THESE ARE THE CONTROLS FOR OUR AVAILABLE PLAYERS SCREEN
    VBox availablePlayersPane;
    GridPane plusMinusSearch;
    Label availablePlayersLabel;
    Button addPlayerButton;
    Button removePlayerButton;
    Label searchPlayersLabel;
    TextField searchPlayersTextField;
    RadioButton allRadioButton;
    RadioButton CRadioButton;
    RadioButton oneBRadioButton;
    RadioButton CIRadioButton;
    RadioButton twoBRadioButton;
    RadioButton threeBRadioButton;
    RadioButton MIRadioButton;
    RadioButton SSRadioButton;
    RadioButton OFRadioButton;
    RadioButton URadioButton;
    RadioButton PRadioButton;
    FlowPane radioButtonPane;
    TableView<Player> availablePlayersTable;
    TableColumn playerFirstNameColumn;
    TableColumn playerLastNameColumn;
    TableColumn playerProTeamColumn;
    TableColumn playerPositionsColumn;
    TableColumn playerYearOfBirthColumn;
    TableColumn playerRWColumn;
    TableColumn playerHRSVColumn;
    TableColumn playerRBIKColumn;
    TableColumn playerSBERAColumn;
    TableColumn playerBAWHIPColumn;
    TableColumn playerEstValColumn;
    TableColumn playerNotesColumn;
    
    // AND TABLE COLUMNS
    static final String COL_FIRST_NAME = "First";
    static final String COL_LAST_NAME = "Last";
    static final String COL_PRO_TEAM = "Pro Team";
    static final String COL_TEAM_NAME = "Team Name";
    static final String COL_PLAYERS_NEEDED = "Players Needed";
    static final String COL_MONEY_LEFT = "$ Left";
    static final String COL_MON_PER_PLAYER = "$ PP";
    static final String COL_R = "R";
    static final String COL_HR = "HR";
    static final String COL_RBI = "RBI";
    static final String COL_SB = "SB";
    static final String COL_BA = "BA";
    static final String COL_W = "W";
    static final String COL_SV = "SV";
    static final String COL_K = "K";
    static final String COL_ERA = "ERA";
    static final String COL_WHIP = "WHIP";
    static final String COL_TOTAL_POINTS = "Total Points";
    static final String COL_POSITIONS = "Positions";
    static final String COL_YEAR_OF_BIRTH = "Year of Birth";
    static final String COL_RW = "R/W";
    static final String COL_HRSV = "HR/SV";
    static final String COL_RBIK = "RBI/K";
    static final String COL_SBERA = "SBI/ERA";
    static final String COL_BAWHIP = "BA/WHIP";
    static final String COL_EST_VAL = "Estimated Value";
    static final String COL_NOTES = "Notes";
    static final String COL_PICK_NUMBER = "Pick #";
    static final String COL_POSITION = "Position";
    static final String COL_CONTRACT = "Contract";
    static final String COL_SALARY = "Salary";
   
    // HERE ARE OUR DIALOGS
    MessageDialog messageDialog;
    YesNoCancelDialog yesNoCancelDialog;
    
    /**
     * Constructor for making this GUI, note that it does not initialize the UI
     * controls. To do that, call initGUI.
     *
     * @param initPrimaryStage Window inside which the GUI will be displayed.
     */
    public WDK_GUI(Stage initPrimaryStage) {
        primaryStage = initPrimaryStage;
        
    }
    
    /**
     * Accessor method for the data manager.
     *
     * @return The DraftDataManager used by this UI.
     */
    public DraftDataManager getDataManager() {
        return dataManager;
    }
    
    /**
     * Accessor method for the file controller.
     *
     * @return The FileController used by this UI.
     */
    public FileController getFileController() {
        return fileController;
    }
    
    /**
     * Accessor method for the screen controller.
     * 
     * @return the ScreenController used by this UI.
     */
    public ScreenController getScreenController() {
        return screenController;
    }

    /**
     * Accessor method for the draft file manager.
     *
     * @return The DraftFileManager used by this UI.
     */
    public DraftFileManager getDraftFileManager() {
        return draftFileManager;
    }

    /**
     * Accessor method for the site exporter.
     *
     * @return The DraftSiteExporter used by this UI.
     */
    
    public DraftSiteExporter getSiteExporter() {
        return siteExporter;
    }


    /**
     * Accessor method for the window (i.e. stage).
     *
     * @return The window (i.e. Stage) used by this UI.
     */
    
    public Stage getWindow() {
        return primaryStage;
    }
    
    /**
     * Accessor method for the message dialog.
     * 
     * @return the MessageDialog used by this UI.
     */
    
    public MessageDialog getMessageDialog() {
        return messageDialog;
    }
    
    /**
     * Accessor method for the YesNoCancelDialog.
     * 
     * @return  the YesNoCancelDialog used by this UI.
     */
    
    public YesNoCancelDialog getYesNoCancelDialog() {
        return yesNoCancelDialog;
    }

    /**
     * Mutator method for the data manager.
     *
     * @param initDataManager The DraftDataManager to be used by this UI.
     */
    public void setDataManager(DraftDataManager initDataManager) {
        dataManager = initDataManager;
    }

    /**
     * Mutator method for the draft file manager.
     *
     * @param initDraftFileManager The DraftFileManager to be used by this UI.
     */
    public void setDraftFileManager(DraftFileManager initDraftFileManager) {
        draftFileManager = initDraftFileManager;
    }

    /**
     * Mutator method for the site exporter.
     *
     * @param initSiteExporter The DraftSiteExporter to be used by this UI.
     */
    public void setSiteExporter(DraftSiteExporter initSiteExporter) {
        siteExporter = initSiteExporter;
    }
    
    /**
     * This method fully initializes the user interface for use.
     *
     * @param windowTitle The text to appear in the UI window's title bar.
     * @throws IOException Thrown if any initialization files fail to load.
     */
    public void initGUI(String windowTitle) throws IOException {
        // INIT THE DIALOGS
        initDialogs();
        
        // INIT THE TOOLBARS
        initFileToolbar();
        initScreenToolbar();

        // INIT THE CENTER WORKSPACE CONTROLS BUT DON'T ADD THEM
        // TO THE WINDOW YET
        initWorkspace();

        // NOW SETUP THE EVENT HANDLERS
        initEventHandlers();

        // AND FINALLY START UP THE WINDOW (WITHOUT THE WORKSPACE)
        initWindow(windowTitle);
    }

    /**
     * When called this function puts the workspace into the window,
     * revealing the controls for editing a Draft.
     */
    public void activateWorkspace() {
        if (!workspaceActivated) {
            // PUT THE WORKSPACE IN THE GUI
            wdkPane.setCenter(workspaceScrollPane);
            wdkPane.setBottom(screenToolbarPane);
            workspaceActivated = true;
        }
    }
    
    @Override
    public void reloadDraft(Draft draftToReload) {
       if (!workspaceActivated) {
            activateWorkspace();
        }
    }
    
    /**
     * This method is used to activate/deactivate toolbar buttons when
     * they can and cannot be used so as to provide foolproof design.
     * 
     * @param saved Describes whether the loaded Draft has been saved or not.
     */
    public void updateToolbarControls(boolean saved) {
        // THIS TOGGLES WITH WHETHER THE CURRENT DRAFT
        // HAS BEEN SAVED OR NOT
        saveDraftButton.setDisable(saved);

        // ALL THE OTHER BUTTONS ARE ALWAYS ENABLED
        // ONCE EDITING THAT FIRST DRAFT BEGINS
        loadDraftButton.setDisable(false);
        exportDraftButton.setDisable(false);

        // NOTE THAT THE NEW, LOAD, AND EXIT BUTTONS
        // ARE NEVER DISABLED SO WE NEVER HAVE TO TOUCH THEM
    }
    
    public void updateFantasyTeamToolbarControls(boolean empty) {
        // THIS TOGGLES WITH THE BUTTON CONTROLS     
        removeTeamButton.setDisable(empty);
        editTeamButton.setDisable(empty);
        
    }
    
    /**
     * This function loads all the values currently in the user interface
     * into the draft argument.
     * 
     * @param draft The draft to be updated using the data from the UI controls.
     */
    public void updateDraftInfo(Draft draft) {
        draft.setFreeAgents(dataManager.getDraft().getFreeAgents());
        draft.setDraftName(draftNameTextField.getText());
        ObservableList listOfTeams = FXCollections.observableArrayList();
        for(int i = 0; i < selectFantasyTeamComboBox.getItems().size(); i++) {
            listOfTeams.add(dataManager.getDraft().getTeamWithName((String)selectFantasyTeamComboBox.getItems().get(i)));
        }
        draft.setListOfTeams(listOfTeams);
    }
    
    private void initDialogs() {
        messageDialog = new MessageDialog(primaryStage, CLOSE_BUTTON_LABEL);
        yesNoCancelDialog = new YesNoCancelDialog(primaryStage);
    }
    
    /**
     * This function initializes all the buttons in the toolbar at the top of
     * the application window. These are related to file management.
     */
    private void initFileToolbar() {
        fileToolbarPane = new FlowPane();

        // HERE ARE OUR FILE TOOLBAR BUTTONS, NOTE THAT SOME WILL
        // START AS ENABLED (false), WHILE OTHERS DISABLED (true)
        newDraftButton = initChildButton(fileToolbarPane, WDK_PropertyType.NEW_DRAFT_ICON, WDK_PropertyType.NEW_DRAFT_TOOLTIP, false);
        loadDraftButton = initChildButton(fileToolbarPane, WDK_PropertyType.LOAD_DRAFT_ICON, WDK_PropertyType.LOAD_DRAFT_TOOLTIP, false);
        saveDraftButton = initChildButton(fileToolbarPane, WDK_PropertyType.SAVE_DRAFT_ICON, WDK_PropertyType.SAVE_DRAFT_TOOLTIP, true);
        exportDraftButton = initChildButton(fileToolbarPane, WDK_PropertyType.EXPORT_PAGE_ICON, WDK_PropertyType.EXPORT_PAGE_TOOLTIP, true);
        exitButton = initChildButton(fileToolbarPane, WDK_PropertyType.EXIT_ICON, WDK_PropertyType.EXIT_TOOLTIP, false);
    }
    
    private void initScreenToolbar() {
        screenToolbarPane = new FlowPane();
        
        fantasyTeamsScreenButton = initChildButton(screenToolbarPane, WDK_PropertyType.HOME_ICON, WDK_PropertyType.HOME_TOOLTIP, false);
        playersScreenButton = initChildButton(screenToolbarPane, WDK_PropertyType.PLAYERS_ICON, WDK_PropertyType.PLAYERS_TOOLTIP, false);
        fantasyStandingsScreenButton = initChildButton(screenToolbarPane, WDK_PropertyType.STANDINGS_ICON, WDK_PropertyType.STANDINGS_TOOLTIP, false);
        draftScreenButton = initChildButton(screenToolbarPane, WDK_PropertyType.DRAFT_SCREEN_ICON, WDK_PropertyType.DRAFT_SCREEN_TOOLTIP, false);
        MLBTeamsScreenButton = initChildButton(screenToolbarPane, WDK_PropertyType.MLB_TEAMS_ICON, WDK_PropertyType.MLB_TEAMS_TOOLTIP, false);
        
    }
    
    // CREATES AND SETS UP ALL THE CONTROLS TO GO IN THE APP WORKSPACE
    private void initWorkspace() throws IOException {
        workspacePane = new BorderPane();
        fantasyTeamsPane = new VBox();
        
        fantasyTeamsHeading = initLabel(WDK_PropertyType.FANTASY_HEADING_LABEL, CLASS_HEADING_LABEL);
        fantasyTeamsPane.getChildren().add(fantasyTeamsHeading);
 
        // THESE ARE THE CONTROLS FOR ADDING A TEAM, REMOVING A TEAM, EDITING A TEAM,
        // SELECTING A TEAM AND ADDING A DRAFT NAME
        namePlusMinusEditSel = new GridPane();
        draftNameLabel = initGridLabel(namePlusMinusEditSel, WDK_PropertyType.DRAFT_NAME_LABEL, CLASS_PROMPT_LABEL, 0, 0, 2, 1);
        draftNameTextField = initGridTextField(namePlusMinusEditSel, SMALL_TEXT_FIELD_LENGTH, EMPTY_TEXT, true, 2, 0, 4, 1);
        addTeamButton = initGridButton(namePlusMinusEditSel, WDK_PropertyType.ADD_ICON, WDK_PropertyType.ADD_TEAM_TOOLTIP, false, 0, 1, 1, 1);
        removeTeamButton = initGridButton(namePlusMinusEditSel, WDK_PropertyType.MINUS_ICON, WDK_PropertyType.REMOVE_TEAM_TOOLTIP, true, 1, 1, 1, 1);
        editTeamButton = initGridButton(namePlusMinusEditSel, WDK_PropertyType.EDIT_ICON, WDK_PropertyType.EDIT_TEAM_TOOLTIP, true, 2, 1, 1, 1);       
        selectFantasyTeamLabel = initGridLabel(namePlusMinusEditSel, WDK_PropertyType.SELECT_TEAM_LABEL, CLASS_PROMPT_LABEL, 3, 1, 2, 1);
        selectFantasyTeamComboBox = initGridComboBox(namePlusMinusEditSel, 5, 1, 1, 1);
        loadFantasyTeamsComboBox(dataManager.getDraft().getListOfTeams());
        fantasyTeamsPane.getChildren().add(namePlusMinusEditSel);
        
        
        // THESE ARE THE CONTROLS FOR THE PLAYER TABLE
        teamTable = new TableView();
        taxiSquadTable = new TableView();
        teamTable.setEditable(true);
        pPositionColumn = new TableColumn(COL_POSITION);
        taxiPositionColumn = new TableColumn(COL_POSITION);
        pPositionColumn.setSortable(false);
        pFirstNameColumn = new TableColumn(COL_FIRST_NAME);
        taxiFirstNameColumn = new TableColumn(COL_FIRST_NAME);
        pFirstNameColumn.setSortable(false);
        pFirstNameColumn.setPrefWidth(120);
        taxiFirstNameColumn.setPrefWidth(120);
        pLastNameColumn = new TableColumn(COL_LAST_NAME);
        taxiLastNameColumn = new TableColumn(COL_LAST_NAME);
        pLastNameColumn.setSortable(false);
        pLastNameColumn.setPrefWidth(120);
        taxiLastNameColumn.setPrefWidth(120);
        pProTeamColumn = new TableColumn(COL_PRO_TEAM);
        taxiProTeamColumn = new TableColumn(COL_PRO_TEAM);
        pProTeamColumn.setSortable(false);
        pPositionsColumn = new TableColumn(COL_POSITIONS);
        taxiPositionsColumn = new TableColumn(COL_POSITIONS);
        pPositionsColumn.setSortable(false);
        pPositionsColumn.setPrefWidth(120);
        taxiPositionsColumn.setPrefWidth(120);
        pRWColumn = new TableColumn(COL_RW);
        taxiRWColumn = new TableColumn(COL_RW);
        pRWColumn.setSortable(false);
        pHRSVColumn = new TableColumn(COL_HRSV);
        taxiHRSVColumn = new TableColumn(COL_HRSV);
        pHRSVColumn.setSortable(false);
        pRBIKColumn = new TableColumn(COL_RBIK);
        taxiRBIKColumn = new TableColumn(COL_RBIK);
        pRBIKColumn.setSortable(false);
        pSBERAColumn = new TableColumn(COL_SBERA);
        taxiSBERAColumn = new TableColumn(COL_SBERA);
        pSBERAColumn.setSortable(false);
        pBAWHIPColumn = new TableColumn(COL_BAWHIP);
        taxiBAWHIPColumn = new TableColumn(COL_BAWHIP);
        pBAWHIPColumn.setSortable(false);
        pEstimatedValueColumn = new TableColumn(COL_EST_VAL);
        taxiEstimatedValueColumn = new TableColumn(COL_EST_VAL);
        pEstimatedValueColumn.setSortable(false);
        pEstimatedValueColumn.setPrefWidth(120);
        taxiEstimatedValueColumn.setPrefWidth(120);
        pContractColumn = new TableColumn(COL_CONTRACT);
        taxiContractColumn = new TableColumn(COL_CONTRACT);
        pContractColumn.setSortable(false);
        pSalaryColumn = new TableColumn(COL_SALARY);
        taxiSalaryColumn = new TableColumn(COL_SALARY);
        pSalaryColumn.setSortable(false);
        
        teamTable.getColumns().add(pPositionColumn);
        teamTable.getColumns().add(pFirstNameColumn);
        teamTable.getColumns().add(pLastNameColumn);
        teamTable.getColumns().add(pProTeamColumn);
        teamTable.getColumns().add(pPositionsColumn);
        teamTable.getColumns().add(pRWColumn);
        teamTable.getColumns().add(pHRSVColumn);
        teamTable.getColumns().add(pRBIKColumn);
        teamTable.getColumns().add(pSBERAColumn);
        teamTable.getColumns().add(pBAWHIPColumn);
        teamTable.getColumns().add(pEstimatedValueColumn);
        teamTable.getColumns().add(pContractColumn);
        teamTable.getColumns().add(pSalaryColumn);      
        taxiSquadTable.getColumns().add(taxiPositionColumn);
        taxiSquadTable.getColumns().add(taxiFirstNameColumn);
        taxiSquadTable.getColumns().add(taxiLastNameColumn);
        taxiSquadTable.getColumns().add(taxiProTeamColumn);
        taxiSquadTable.getColumns().add(taxiPositionsColumn);
        taxiSquadTable.getColumns().add(taxiRWColumn);
        taxiSquadTable.getColumns().add(taxiHRSVColumn);
        taxiSquadTable.getColumns().add(taxiRBIKColumn);
        taxiSquadTable.getColumns().add(taxiSBERAColumn);
        taxiSquadTable.getColumns().add(taxiBAWHIPColumn);
        taxiSquadTable.getColumns().add(taxiEstimatedValueColumn);
        taxiSquadTable.getColumns().add(taxiContractColumn);
        taxiSquadTable.getColumns().add(taxiSalaryColumn);
              
        startingLineupBox = new VBox();
        startingLineupLabel = initLabel(WDK_PropertyType.STARTING_LINEUP_LABEL, CLASS_SUBHEADING_LABEL);
        startingLineupBox.getChildren().add(startingLineupLabel);
        startingLineupBox.getChildren().add(teamTable);
        startingLineupBox.getStyleClass().add(CLASS_BORDERED_PANE);
        
        taxiSquadBox = new VBox();
        taxiSquadLabel = initLabel(WDK_PropertyType.TAXI_SQUAD_LABEL, CLASS_SUBHEADING_LABEL);
        taxiSquadBox.getChildren().add(taxiSquadLabel);
        taxiSquadBox.getChildren().add(taxiSquadTable);
        taxiSquadBox.getStyleClass().add(CLASS_BORDERED_PANE);
        fantasyTeamsPane.getChildren().add(startingLineupBox);
        fantasyTeamsPane.getChildren().add(taxiSquadBox);
        workspacePane.setCenter(fantasyTeamsPane);

        // AND NOW PUT IT IN THE WORKSPACE
        workspaceScrollPane = new ScrollPane();
        workspaceScrollPane.setContent(workspacePane);
        workspaceScrollPane.setFitToWidth(true);
        workspaceScrollPane.getStyleClass().add(CLASS_BORDERED_PANE);
        
        registerTextFieldController(draftNameTextField);
        
        teamController = new TeamController(primaryStage, dataManager.getDraft(), messageDialog, yesNoCancelDialog);
        addTeamButton.setOnAction(e -> {
            teamController.handleAddNewTeamRequest(this);
            loadFantasyTeamsComboBox(dataManager.getDraft().getListOfTeams());
            draftController.handleDraftChangeRequest(this);
        });
        editTeamButton.setOnAction(e -> {
            if(selectFantasyTeamComboBox.getSelectionModel().getSelectedItem() != null) {
                teamController.handleEditTeamRequest(this, dataManager.getDraft().getTeamWithName((String)selectFantasyTeamComboBox.getSelectionModel().getSelectedItem()));
                loadFantasyTeamsComboBox(dataManager.getDraft().getListOfTeams());
                draftController.handleDraftChangeRequest(this);
            }
            else {
                messageDialog.show("Please select a team!");
            }
            
        });
        removeTeamButton.setOnAction(e -> {
            if(selectFantasyTeamComboBox.getSelectionModel().getSelectedItem() != null) {
                teamController.handleRemoveTeamRequest(this, dataManager.getDraft().getTeamWithName((String)selectFantasyTeamComboBox.getSelectionModel().getSelectedItem()));
                loadFantasyTeamsComboBox(dataManager.getDraft().getListOfTeams());
                draftController.handleDraftChangeRequest(this);
            }
            else if(selectFantasyTeamComboBox.getSelectionModel().getSelectedItem() == null){
                messageDialog.show("Please select a team!");
            } 
        });
        selectFantasyTeamComboBox.setOnAction(e -> {
            pPositionColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("chosenPosition"));
            pFirstNameColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("firstName"));
            pLastNameColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("lastName"));
            pProTeamColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("MLBTeam"));
            pPositionsColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("QPOrRole"));
            pRWColumn.setCellValueFactory(new PropertyValueFactory<Player, Integer>("ROrWStat"));
            pHRSVColumn.setCellValueFactory(new PropertyValueFactory<Player, Integer>("HROrSVStat"));
            pRBIKColumn.setCellValueFactory(new PropertyValueFactory<Player, Integer>("RBIOrKStat"));
            pSBERAColumn.setCellValueFactory(new PropertyValueFactory<Player, Double>("SBOrERAStat"));
            pBAWHIPColumn.setCellValueFactory(new PropertyValueFactory<Player, Double>("BAOrWHIPStat"));
            pEstimatedValueColumn.setCellValueFactory(new PropertyValueFactory<Player, Integer>("EstValStat"));
            pContractColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("contract"));
            pSalaryColumn.setCellValueFactory(new PropertyValueFactory<Player, Integer>("salary"));
            taxiPositionColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("chosenPosition"));
            taxiFirstNameColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("firstName"));
            taxiLastNameColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("lastName"));
            taxiProTeamColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("MLBTeam"));
            taxiPositionsColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("QPOrRole"));
            taxiRWColumn.setCellValueFactory(new PropertyValueFactory<Player, Integer>("ROrWStat"));
            taxiHRSVColumn.setCellValueFactory(new PropertyValueFactory<Player, Integer>("HROrSVStat"));
            taxiRBIKColumn.setCellValueFactory(new PropertyValueFactory<Player, Integer>("RBIOrKStat"));
            taxiSBERAColumn.setCellValueFactory(new PropertyValueFactory<Player, Double>("SBOrERAStat"));
            taxiBAWHIPColumn.setCellValueFactory(new PropertyValueFactory<Player, Double>("BAOrWHIPStat"));
            taxiEstimatedValueColumn.setCellValueFactory(new PropertyValueFactory<Player, Integer>("EstValStat"));
            taxiContractColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("contract"));
            taxiSalaryColumn.setCellValueFactory(new PropertyValueFactory<Player, Integer>("salary"));
            if(((String)selectFantasyTeamComboBox.getValue()) == null) {
                teamTable.setItems(null);
                taxiSquadTable.setItems(null);
            }
            else {
                showPlayersScreen();
                showFantasyTeamsScreen();
                
                teamTable.setItems(dataManager.getDraft().getTeamWithName((String)selectFantasyTeamComboBox.getValue()).getStartingLineup());
                taxiSquadTable.setItems(dataManager.getDraft().getTeamWithName((String)selectFantasyTeamComboBox.getValue()).getTaxiSquad());              
            }
        });
   
        teamTable.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                // OPEN UP THE PLAYER EDITOR
                try {
                    playerController.handleEditPlayerRequest(this, teamTable.getSelectionModel().getSelectedItem());
                    draftController.handleDraftChangeRequest(this); 
                } catch(Exception f) {
                    int selected = teamTable.getSelectionModel().getSelectedIndex();
                    teamTable.getSelectionModel().clearSelection();
                    teamTable.getSelectionModel().select(selected); 
                }
            }
        });
        
        // NOTE THAT WE HAVE NOT PUT THE WORKSPACE INTO THE WINDOW,
        // THAT WILL BE DONE WHEN THE USER EITHER CREATES A NEW
        // COURSE OR LOADS AN EXISTING ONE FOR EDITING
        workspaceActivated = false;
        
    }
    
    public void showPlayersScreen() {
        availablePlayersPane = new VBox();
        availablePlayersLabel = initLabel(WDK_PropertyType.PLAYERS_HEADING_LABEL, CLASS_HEADING_LABEL); 
        availablePlayersPane.getChildren().add(availablePlayersLabel);
               
        // THESE ARE THE CONTROLS FOR ADDING A PLAYER, REMOVING A PLAYER, AND SEARCHING
        // THE THREE CONTROLS WILL BE ADDED TO A GRIDPANE
        plusMinusSearch = new GridPane();
        addPlayerButton = initGridButton(plusMinusSearch, WDK_PropertyType.ADD_ICON, WDK_PropertyType.ADD_PLAYER_TOOLTIP, false, 0, 0, 1, 1);
        removePlayerButton = initGridButton(plusMinusSearch, WDK_PropertyType.MINUS_ICON, WDK_PropertyType.REMOVE_PLAYER_TOOLTIP, false, 1, 0, 1, 1);
        searchPlayersLabel = initGridLabel(plusMinusSearch, WDK_PropertyType.SEARCH_LABEL, CLASS_PROMPT_LABEL, 2, 0, 1, 1);
        plusMinusSearch.setMargin(searchPlayersLabel, new Insets(0, 0, 0, 45));
        availablePlayersPane.getChildren().add(plusMinusSearch);
        searchPlayersTextField = initGridTextField(plusMinusSearch, LARGE_TEXT_FIELD_LENGTH, EMPTY_TEXT, true, 3, 0, 1, 1);
        
        // THESE ARE THE CONTROLS FOR THE RADIO BUTTONS TO SELECT A PLAYER POSITION
        // THEY WILL BE ADDED TO A FLOW PANE
        
        radioButtonPane = new FlowPane();
        radioButtonPane.setPrefHeight(80);
        radioButtonPane.setAlignment(Pos.CENTER_LEFT);
        allRadioButton = new RadioButton("All");
        CRadioButton = new RadioButton("C");
        oneBRadioButton = new RadioButton("1B");
        CIRadioButton = new RadioButton("CI");
        threeBRadioButton = new RadioButton("3B");
        twoBRadioButton = new RadioButton("2B");
        MIRadioButton = new RadioButton("MI");
        SSRadioButton = new RadioButton("SS");
        OFRadioButton = new RadioButton("OF");
        URadioButton = new RadioButton("U");
        PRadioButton = new RadioButton("P");
        radioButtonPane.getChildren().addAll(allRadioButton, CRadioButton, oneBRadioButton,
                CIRadioButton, threeBRadioButton, twoBRadioButton, MIRadioButton,
                SSRadioButton, OFRadioButton, URadioButton, PRadioButton);
        ToggleGroup group = new ToggleGroup();
        allRadioButton.setToggleGroup(group);
        CRadioButton.setToggleGroup(group);
        oneBRadioButton.setToggleGroup(group);
        CIRadioButton.setToggleGroup(group);
        threeBRadioButton.setToggleGroup(group);
        twoBRadioButton.setToggleGroup(group);
        MIRadioButton.setToggleGroup(group);
        SSRadioButton.setToggleGroup(group);
        OFRadioButton.setToggleGroup(group);
        URadioButton.setToggleGroup(group);
        PRadioButton.setToggleGroup(group);
        radioButtonPane.setHgap(20);     
        radioButtonPane.getStyleClass().add(CLASS_BORDERED_PANE);
        availablePlayersPane.getChildren().add(radioButtonPane);
        
        // THESE ARE THE CONTROLS FOR THE PLAYER TABLE
        availablePlayersTable = new TableView();
        availablePlayersTable.setEditable(true);
        playerFirstNameColumn = new TableColumn(COL_FIRST_NAME);
        playerFirstNameColumn.setPrefWidth(120);
        playerLastNameColumn = new TableColumn(COL_LAST_NAME);
        playerLastNameColumn.setPrefWidth(120);
        playerProTeamColumn = new TableColumn(COL_PRO_TEAM);
        playerPositionsColumn = new TableColumn(COL_POSITIONS);
        playerPositionsColumn.setPrefWidth(120);
        playerYearOfBirthColumn = new TableColumn(COL_YEAR_OF_BIRTH);
        playerRWColumn = new TableColumn(COL_RW);
        playerHRSVColumn = new TableColumn(COL_HRSV);
        playerRBIKColumn = new TableColumn(COL_RBIK);
        playerSBERAColumn = new TableColumn(COL_SBERA);
        playerBAWHIPColumn = new TableColumn(COL_BAWHIP);
        playerEstValColumn = new TableColumn(COL_EST_VAL);
        playerEstValColumn.setPrefWidth(120);
        playerNotesColumn = new TableColumn(COL_NOTES);
        playerNotesColumn.setPrefWidth(120);
        playerNotesColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            playerNotesColumn.setOnEditCommit(
                new EventHandler<CellEditEvent<Player, String>>() {
                @Override
                    public void handle(CellEditEvent<Player, String> event) {
                        ((Player)event.getTableView().getItems().get(event.getTablePosition().getRow())
                        ).setNotes(event.getNewValue());
                    }
                }
            );
        // POPULATE THE TABLE
        
        // DEFAULT POPULATION OF TABLE
        playerFirstNameColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("firstName"));
        playerLastNameColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("lastName"));
        playerProTeamColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("MLBTeam"));
        playerPositionsColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("QPOrRole"));
        playerYearOfBirthColumn.setCellValueFactory(new PropertyValueFactory<Player, Integer>("YOB"));
        playerRWColumn.setCellValueFactory(new PropertyValueFactory<Player, Integer>("ROrWStat"));
        playerHRSVColumn.setCellValueFactory(new PropertyValueFactory<Player, Integer>("HROrSVStat"));
        playerRBIKColumn.setCellValueFactory(new PropertyValueFactory<Player, Integer>("RBIOrKStat"));
        playerSBERAColumn.setCellValueFactory(new PropertyValueFactory<Player, Double>("SBOrERAStat"));
        playerBAWHIPColumn.setCellValueFactory(new PropertyValueFactory<Player, Double>("BAOrWHIPStat"));
        playerEstValColumn.setCellValueFactory(new PropertyValueFactory<Player, Integer>("EstValStat"));
        playerNotesColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("notes"));
        availablePlayersTable.setItems(dataManager.getDraft().getFreeAgents());
        
        // DETERMINES HOW TO POPULATE THE TABLE IF THE ALL RADIO BUTTON IS SELECTED
        allRadioButton.setOnAction(e -> { 
            if(allRadioButton.isSelected()) {
                availablePlayersTable.setItems(dataManager.getDraft().getFreeAgents());
            }
        });
        
        // DETERMINES HOW TO POPULATE THE TABLE IF THE C RADIO BUTTON IS SELECTED
        ObservableList CPlayers = FXCollections.observableArrayList();
        for(int i = 0; i < dataManager.getDraft().getFreeAgents().size(); i++) {
            if((((Player)dataManager.getDraft().getFreeAgents().get(i)).getQPOrRole().contains("C"))) {
                CPlayers.add((Player)dataManager.getDraft().getFreeAgents().get(i));
            }
        }
        CRadioButton.setOnAction(e -> {
            if(CRadioButton.isSelected()) {
                availablePlayersTable.setItems(CPlayers);
            }
        });
        
        // DETERMINES HOW TO POPULATE THE TABLE IF THE 1B RADIO BUTTON IS SELECTED
        ObservableList oneBPlayers = FXCollections.observableArrayList();
        for(int i = 0; i < dataManager.getDraft().getFreeAgents().size(); i++) {
            if((((Player)dataManager.getDraft().getFreeAgents().get(i)).getQPOrRole().contains("1B"))) {
                oneBPlayers.add((Player)dataManager.getDraft().getFreeAgents().get(i));
            }
        }
        oneBRadioButton.setOnAction(e -> {
            if(oneBRadioButton.isSelected()) {
                availablePlayersTable.setItems(oneBPlayers);
            }
        });
        
        // DETERMINES HOW TO POPULATE THE TABLE IF THE CI RADIO BUTTON IS SELECTED
        ObservableList CIPlayers = FXCollections.observableArrayList();
        for(int i = 0; i < dataManager.getDraft().getFreeAgents().size(); i++) {
            if((((Player)dataManager.getDraft().getFreeAgents().get(i)).getQPOrRole().contains("1B")) ||
               (((Player)dataManager.getDraft().getFreeAgents().get(i)).getQPOrRole().contains("3B"))) {
                CIPlayers.add((Player)dataManager.getDraft().getFreeAgents().get(i));
            } 
        }
        CIRadioButton.setOnAction(e -> {
            if(CIRadioButton.isSelected()) {
                availablePlayersTable.setItems(CIPlayers);
            }
        });
        
        // DETERMINES HOW TO POPULATE THE TABLE IF THE 3B RADIO BUTTON IS SELECTED
       ObservableList threeBPlayers = FXCollections.observableArrayList();
        for(int i = 0; i < dataManager.getDraft().getFreeAgents().size(); i++) {
            if((((Player)dataManager.getDraft().getFreeAgents().get(i)).getQPOrRole().contains("3B"))) {
                threeBPlayers.add((Player)dataManager.getDraft().getFreeAgents().get(i));
            }
        }
        threeBRadioButton.setOnAction(e -> {
            if(threeBRadioButton.isSelected()) {
                availablePlayersTable.setItems(threeBPlayers);
            }
        });
        
        // DETERMINES HOW TO POPULATE THE TABLE IF THE 2B RADIO BUTTON IS SELECTED
        ObservableList twoBPlayers = FXCollections.observableArrayList();
        for(int i = 0; i < dataManager.getDraft().getFreeAgents().size(); i++) {
            if((((Player)dataManager.getDraft().getFreeAgents().get(i)).getQPOrRole().contains("2B"))) {
                twoBPlayers.add((Player)dataManager.getDraft().getFreeAgents().get(i));
            }
        }
        twoBRadioButton.setOnAction(e -> {
            if(twoBRadioButton.isSelected()) {
                availablePlayersTable.setItems(twoBPlayers);
            }
        });
        
        // DETERMINES HOW TO POPULATE THE TABLE IF THE MI RADIO BUTTON IS SELECTED
        ObservableList MIPlayers = FXCollections.observableArrayList();
        for(int i = 0; i < dataManager.getDraft().getFreeAgents().size(); i++) {
            if((((Player)dataManager.getDraft().getFreeAgents().get(i)).getQPOrRole().contains("2B")) ||
               (((Player)dataManager.getDraft().getFreeAgents().get(i)).getQPOrRole().contains("SS"))) {
                MIPlayers.add((Player)dataManager.getDraft().getFreeAgents().get(i));
            } 
        }
        MIRadioButton.setOnAction(e -> {
            if(MIRadioButton.isSelected()) {
                availablePlayersTable.setItems(MIPlayers);
            }
        });
        
        // DETERMINES HOW TO POPULATE THE TABLE IF THE SS RADIO BUTTON IS SELECTED
        ObservableList SSPlayers = FXCollections.observableArrayList();
        for(int i = 0; i < dataManager.getDraft().getFreeAgents().size(); i++) {
            if((((Player)dataManager.getDraft().getFreeAgents().get(i)).getQPOrRole().contains("SS"))) {
                SSPlayers.add((Player)dataManager.getDraft().getFreeAgents().get(i));
            }
        }
        SSRadioButton.setOnAction(e -> {
            if(SSRadioButton.isSelected()) {
                availablePlayersTable.setItems(SSPlayers);
            }
        });
        
        // DETERMINES HOW TO POPULATE THE TABLE IF THE OF RADIO BUTTON IS SELECTED
        ObservableList OFPlayers = FXCollections.observableArrayList();
        for(int i = 0; i < dataManager.getDraft().getFreeAgents().size(); i++) {
            if((((Player)dataManager.getDraft().getFreeAgents().get(i)).getQPOrRole().contains("OF"))) {
                OFPlayers.add((Player)dataManager.getDraft().getFreeAgents().get(i));
            }
        }
        OFRadioButton.setOnAction(e -> {
            if(OFRadioButton.isSelected()) {
                availablePlayersTable.setItems(OFPlayers);
            }
        });
        
        // DETERMINES HOW TO POPULATE THE TABLE IS THE U RADIO BUTTON IS SELECTED
        ObservableList UPlayers = FXCollections.observableArrayList();
        for(int i = 0; i < dataManager.getDraft().getFreeAgents().size(); i++) {
            if(!(((Player)dataManager.getDraft().getFreeAgents().get(i)).getQPOrRole().contains("P"))) {
                UPlayers.add((Player)dataManager.getDraft().getFreeAgents().get(i));
            }
        }
        URadioButton.setOnAction(e -> {
            if(URadioButton.isSelected()) {
                availablePlayersTable.setItems(UPlayers);
            }
        });
        
        // DETERMINES HOW TO POPULATE THE TABLE IS THE P RADIO BUTTON IS SELECTED
        ObservableList PPlayers = FXCollections.observableArrayList();
        for(int i = 0; i < dataManager.getDraft().getFreeAgents().size(); i++) {
            if((((Player)dataManager.getDraft().getFreeAgents().get(i)).getQPOrRole().contains("P"))) {
                PPlayers.add((Player)dataManager.getDraft().getFreeAgents().get(i));
            }
        }
        PRadioButton.setOnAction(e -> {
            if(PRadioButton.isSelected()) {
                availablePlayersTable.setItems(PPlayers);
            }
        });
  
 
        // DETERMINES HOW TO POPULATE THE TABLE WHEN USER TYPES IN THE SEARCH BAR
        searchPlayersTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchByKey((String)oldValue, (String)newValue, CPlayers, oneBPlayers,
                    CIPlayers, threeBPlayers, twoBPlayers, MIPlayers, SSPlayers, OFPlayers,
                    UPlayers, PPlayers);
        });
      
        availablePlayersTable.getColumns().add(playerFirstNameColumn);
        availablePlayersTable.getColumns().add(playerLastNameColumn);
        availablePlayersTable.getColumns().add(playerProTeamColumn);
        availablePlayersTable.getColumns().add(playerPositionsColumn);
        availablePlayersTable.getColumns().add(playerYearOfBirthColumn);
        availablePlayersTable.getColumns().add(playerRWColumn);
        availablePlayersTable.getColumns().add(playerHRSVColumn);
        availablePlayersTable.getColumns().add(playerRBIKColumn);
        availablePlayersTable.getColumns().add(playerSBERAColumn);
        availablePlayersTable.getColumns().add(playerBAWHIPColumn);
        availablePlayersTable.getColumns().add(playerEstValColumn);
        availablePlayersTable.getColumns().add(playerNotesColumn);
        
        
        availablePlayersPane.getChildren().add(availablePlayersTable);
        
        // PUT THEM INTO THE WORKSPACE PANE AND PUT THE PANE INTO THE SCROLLPANE
        workspacePane.setCenter(availablePlayersPane);
        workspaceScrollPane.setContent(workspacePane);
        
        playerController = new PlayerController(primaryStage, dataManager.getDraft(), messageDialog, yesNoCancelDialog);
        addPlayerButton.setOnAction(e -> {
            playerController.handleAddNewPlayerRequest(this);
            draftController.handleDraftChangeRequest(this);
            updateCPlayers(CPlayers);
            updateOneBPlayers(oneBPlayers);
            updateThreeBPlayers(threeBPlayers);
            updateTwoBPlayers(twoBPlayers);
            updateMIPlayers(MIPlayers);
            updateSSPlayers(SSPlayers);
            updateOFPlayers(OFPlayers);
            updateUPlayers(UPlayers);
            updatePPlayers(PPlayers);
        });
        removePlayerButton.setOnAction(e -> {
            playerController.handleRemovePlayerRequest(this, availablePlayersTable.getSelectionModel().getSelectedItem());
            draftController.handleDraftChangeRequest(this);
            updateCPlayers(CPlayers);
            updateOneBPlayers(oneBPlayers);
            updateThreeBPlayers(threeBPlayers);
            updateTwoBPlayers(twoBPlayers);
            updateMIPlayers(MIPlayers);
            updateSSPlayers(SSPlayers);
            updateOFPlayers(OFPlayers);
            updateUPlayers(UPlayers);
            updatePPlayers(PPlayers);
        });
        
        availablePlayersTable.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                // OPEN UP THE PLAYER EDITOR
                playerController.handleEditPlayerRequest(this, availablePlayersTable.getSelectionModel().getSelectedItem());
                draftController.handleDraftChangeRequest(this);
                updateCPlayers(CPlayers);
                updateOneBPlayers(oneBPlayers);
                updateThreeBPlayers(threeBPlayers);
                updateTwoBPlayers(twoBPlayers);
                updateMIPlayers(MIPlayers);
                updateSSPlayers(SSPlayers);
                updateOFPlayers(OFPlayers);
                updateUPlayers(UPlayers);
                updatePPlayers(PPlayers);                
            }
        });
    }
    
    public void updateCPlayers(ObservableList CPlayers) {
        CPlayers.clear();
        for(int i = 0; i < dataManager.getDraft().getFreeAgents().size(); i++) {
            if((((Player)dataManager.getDraft().getFreeAgents().get(i)).getQPOrRole().contains("C"))) {
                CPlayers.add((Player)dataManager.getDraft().getFreeAgents().get(i));
            }
        }
    }
    
    public void updateOneBPlayers(ObservableList oneBPlayers) {
        oneBPlayers.clear();
        for(int i = 0; i < dataManager.getDraft().getFreeAgents().size(); i++) {
            if((((Player)dataManager.getDraft().getFreeAgents().get(i)).getQPOrRole().contains("1B"))) {
                oneBPlayers.add((Player)dataManager.getDraft().getFreeAgents().get(i));
            }
        }
    }
    
    public void updateCIPlayers(ObservableList CIPlayers) {
        CIPlayers.clear();
        for(int i = 0; i < dataManager.getDraft().getFreeAgents().size(); i++) {
            if((((Player)dataManager.getDraft().getFreeAgents().get(i)).getQPOrRole().contains("1B")) ||
               (((Player)dataManager.getDraft().getFreeAgents().get(i)).getQPOrRole().contains("3B"))) {
                CIPlayers.add((Player)dataManager.getDraft().getFreeAgents().get(i));
            } 
        }
    }
    
    public void updateThreeBPlayers(ObservableList threeBPlayers) {
        threeBPlayers.clear();
        for(int i = 0; i < dataManager.getDraft().getFreeAgents().size(); i++) {
            if((((Player)dataManager.getDraft().getFreeAgents().get(i)).getQPOrRole().contains("3B"))) {
                threeBPlayers.add((Player)dataManager.getDraft().getFreeAgents().get(i));
            }
        }
    }
    
    public void updateTwoBPlayers(ObservableList twoBPlayers) {
        twoBPlayers.clear();
        for(int i = 0; i < dataManager.getDraft().getFreeAgents().size(); i++) {
            if((((Player)dataManager.getDraft().getFreeAgents().get(i)).getQPOrRole().contains("2B"))) {
                twoBPlayers.add((Player)dataManager.getDraft().getFreeAgents().get(i));
            }
        }
    }
    
    public void updateMIPlayers(ObservableList MIPlayers) {
        MIPlayers.clear();
        for(int i = 0; i < dataManager.getDraft().getFreeAgents().size(); i++) {
            if((((Player)dataManager.getDraft().getFreeAgents().get(i)).getQPOrRole().contains("2B")) ||
               (((Player)dataManager.getDraft().getFreeAgents().get(i)).getQPOrRole().contains("SS"))) {
                MIPlayers.add((Player)dataManager.getDraft().getFreeAgents().get(i));
            } 
        }
    }
    
    public void updateSSPlayers(ObservableList SSPlayers) {
        SSPlayers.clear();
        for(int i = 0; i < dataManager.getDraft().getFreeAgents().size(); i++) {
            if((((Player)dataManager.getDraft().getFreeAgents().get(i)).getQPOrRole().contains("SS"))) {
                SSPlayers.add((Player)dataManager.getDraft().getFreeAgents().get(i));
            }
        }
    }
    
    public void updateOFPlayers(ObservableList OFPlayers) {
        OFPlayers.clear();
        for(int i = 0; i < dataManager.getDraft().getFreeAgents().size(); i++) {
            if((((Player)dataManager.getDraft().getFreeAgents().get(i)).getQPOrRole().contains("OF"))) {
                OFPlayers.add((Player)dataManager.getDraft().getFreeAgents().get(i));
            }
        }
    }
    
    public void updateUPlayers(ObservableList UPlayers) {
        UPlayers.clear();
        for(int i = 0; i < dataManager.getDraft().getFreeAgents().size(); i++) {
            if(!(((Player)dataManager.getDraft().getFreeAgents().get(i)).getQPOrRole().contains("P"))) {
                UPlayers.add((Player)dataManager.getDraft().getFreeAgents().get(i));
            }
        }
    }
    
    public void updatePPlayers(ObservableList PPlayers) {
        PPlayers.clear();
        for(int i = 0; i < dataManager.getDraft().getFreeAgents().size(); i++) {
            if((((Player)dataManager.getDraft().getFreeAgents().get(i)).getQPOrRole().contains("P"))) {
                PPlayers.add((Player)dataManager.getDraft().getFreeAgents().get(i));
            }
        }
    }
 
    public void searchByKey(String oldValue, String newValue, ObservableList CPlayers, 
            ObservableList oneBPlayers, ObservableList CIPlayers, ObservableList threeBPlayers, 
            ObservableList twoBPlayers, ObservableList MIPlayers, ObservableList SSPlayers, 
            ObservableList OFPlayers, ObservableList UPlayers, ObservableList PPlayers) {
        if(oldValue != null && (newValue.length() < oldValue.length()) && allRadioButton.isSelected()) {
            availablePlayersTable.setItems(dataManager.getDraft().getFreeAgents());
        }
        else if(oldValue != null && (newValue.length() < oldValue.length()) && CRadioButton.isSelected()) {
            availablePlayersTable.setItems(CPlayers);
        }
        else if(oldValue != null && (newValue.length() < oldValue.length()) && oneBRadioButton.isSelected()) {
            availablePlayersTable.setItems(oneBPlayers);
        }
        else if(oldValue != null && (newValue.length() < oldValue.length()) && CIRadioButton.isSelected()) {
            availablePlayersTable.setItems(CIPlayers);
        }
        else if(oldValue != null && (newValue.length() < oldValue.length()) && threeBRadioButton.isSelected()) {
            availablePlayersTable.setItems(threeBPlayers);
        }
        else if(oldValue != null && (newValue.length() < oldValue.length()) && twoBRadioButton.isSelected()) {
            availablePlayersTable.setItems(twoBPlayers);
        }
        else if(oldValue != null && (newValue.length() < oldValue.length()) && MIRadioButton.isSelected()) {
            availablePlayersTable.setItems(MIPlayers);
        }
        else if(oldValue != null && (newValue.length() < oldValue.length()) && SSRadioButton.isSelected()) {
            availablePlayersTable.setItems(SSPlayers);
        }
        else if(oldValue != null && (newValue.length() < oldValue.length()) && OFRadioButton.isSelected()) {
            availablePlayersTable.setItems(OFPlayers);
        }
        else if(oldValue != null && (newValue.length() < oldValue.length()) && URadioButton.isSelected()) {
            availablePlayersTable.setItems(UPlayers);
        }
        else if(oldValue != null && (newValue.length() < oldValue.length()) && PRadioButton.isSelected()) {
            availablePlayersTable.setItems(PPlayers);
        }
        else if(oldValue != null && (newValue.length() < oldValue.length())) {
            availablePlayersTable.setItems(dataManager.getDraft().getFreeAgents());
        }
        
        newValue = (String)newValue.toUpperCase();
        ObservableList searchResults = FXCollections.observableArrayList();
        for(Object entry: availablePlayersTable.getItems()) {
            Player currPlayer = (Player)entry;
            String firstName = currPlayer.getFirstName();
            String lastName = currPlayer.getLastName();
            if(firstName.toUpperCase().contains(newValue) || lastName.toUpperCase().contains(newValue)) {
                searchResults.add(currPlayer);
            }
        }
        availablePlayersTable.setItems(searchResults);
    }
    
    public void showFantasyTeamsScreen() {   
        workspacePane.setCenter(fantasyTeamsPane);
        workspaceScrollPane.setContent(workspacePane);
    }
    
    public void showDraftScreen() {
        draftScreenPane = new VBox();
        draftScreenLabel = initLabel(WDK_PropertyType.DRAFT_HEADING_LABEL, CLASS_HEADING_LABEL);
        bestPlayPause = new GridPane();
        draftBest = initGridButton(bestPlayPause, WDK_PropertyType.BEST_ICON, WDK_PropertyType.DRAFT_PLAYER_TOOLTIP, false, 0, 0, 1, 1);
        startAutoDraft = initGridButton(bestPlayPause, WDK_PropertyType.PLAY_ICON, WDK_PropertyType.START_AUTO_TOOLTIP, false, 1, 0, 1, 1);
        pauseAutoDraft = initGridButton(bestPlayPause, WDK_PropertyType.PAUSE_ICON, WDK_PropertyType.PAUSE_AUTO_TOOLTIP, false, 2, 0, 1, 1);
        draftedPlayers = new TableView();
        pickNumberColumn = new TableColumn(COL_PICK_NUMBER);
        draftedFirstNameColumn = new TableColumn(COL_FIRST_NAME);
        draftedLastNameColumn = new TableColumn(COL_LAST_NAME);
        draftedTeamNameColumn = new TableColumn(COL_TEAM_NAME);
        draftedTeamNameColumn.setPrefWidth(150);
        draftedContractColumn = new TableColumn(COL_CONTRACT);
        draftedSalaryColumn = new TableColumn(COL_SALARY);
        draftedPlayers.getColumns().add(pickNumberColumn);
        draftedPlayers.getColumns().add(draftedFirstNameColumn);
        draftedPlayers.getColumns().add(draftedLastNameColumn);
        draftedPlayers.getColumns().add(draftedTeamNameColumn);
        draftedPlayers.getColumns().add(draftedContractColumn);
        draftedPlayers.getColumns().add(draftedSalaryColumn);
        
        pickNumberColumn.setCellValueFactory(new Callback<CellDataFeatures<Player, String>, ObservableValue<String>>() {
            @Override 
            public ObservableValue<String> call(CellDataFeatures<Player, String> p) {
                return new ReadOnlyObjectWrapper(draftedPlayers.getItems().indexOf(p.getValue()) + 1 + "");
            }   
        });  
        draftedFirstNameColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("firstName"));
        draftedLastNameColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("lastName"));
        draftedTeamNameColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("fantasyTeam"));
        draftedContractColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("contract"));
        draftedSalaryColumn.setCellValueFactory(new PropertyValueFactory<Player, Integer>("salary")); 
        draftBest.setOnAction((ActionEvent e) -> {  
            
            boolean SLDone = false;
            for(Team t: dataManager.getDraft().getListOfTeams()) {
                if(t.getPlayersNeededForSL() == 0) {
                    SLDone = true;
                }
                else {
                    SLDone = false;
                    break;
                }
            }
            
            if(SLDone == false) {
                handleDraftStartingLineupPlayers();            
            }
            else
                handleDraftTaxiSquadPlayers();
        });
        
        startAutoDraft.setOnAction((ActionEvent e) -> {
            AutomatedDrafting ad = new AutomatedDrafting(this);
        });
        
        draftedPlayers.setItems(dataManager.getDraft().getDraftedPlayers());
        draftScreenPane.getChildren().add(draftScreenLabel);
        draftScreenPane.getChildren().add(bestPlayPause);
        draftScreenPane.getChildren().add(draftedPlayers);
        workspacePane.setCenter(draftScreenPane);
        workspaceScrollPane.setContent(workspacePane);     
    }
    
    public void handleDraftStartingLineupPlayers() {
        for(Team t: dataManager.getDraft().getListOfTeams()) {
            boolean skip = false;
            if(t.getPlayersNeededForSL() > 0) {
                for(String p: t.getPositionsNeeded()) {
                    // FIND THE SELECTED PLAYERS ELIGIBLE FOR THAT POSITION
                    // AND SELECT THE FIRST PLAYER
                    Player m = dataManager.getDraft().playersEligibleForPosition(p).get(0);
                    m.setFantasyTeam(t.getTeamName());
                    m.setContract(Contract.S2);
                    m.setSalary(1);
                    m.setChosenPosition(p);
                    dataManager.getDraft().addPlayerToTeam(m, t);
                    dataManager.getDraft().addDraftedPlayer(m); 
                    dataManager.getDraft().removeFreeAgent(m);
                    skip = true;
                    break;
                }
                t.setPlayersNeededForSL(23 - (t.getStartingLineup().size()));
                    t.setMoneyLeft(t.calcMoneyLeft());
                    t.setMoneyPerPlayer(t.calcMoneyPerPlayer());
                    t.setAggRStat(t.calcAggRStat());
                    t.setAggHRStat(t.calcAggHRStat());
                    t.setAggRBIStat(t.calcAggRBIStat());
                    t.setAggSBStat(t.calcAggSBStat());
                    t.setAggBAStat(t.calcAggBAStat());
                    t.setAggWStat(t.calcAggWStat());
                    t.setAggSVStat(t.calcAggSVStat());
                    t.setAggKStat(t.calcAggKStat());
                    t.setAggERAStat(t.calcAggERAStat());
                    t.setAggWHIPStat(t.calcAggWHIPStat());
                    dataManager.getDraft().calcPointsForWHIP();
                    dataManager.getDraft().calcPointsForERA();
                    dataManager.getDraft().calcPointsForK();
                    dataManager.getDraft().calcPointsForSV();
                    dataManager.getDraft().calcPointsForW();
                    dataManager.getDraft().calcPointsForBA();
                    dataManager.getDraft().calcPointsForSB();
                    dataManager.getDraft().calcPointsForRBI();
                    dataManager.getDraft().calcPointsForHR();
                    dataManager.getDraft().calcPointsForR();
                    dataManager.getDraft().calcTotalPoints();
                    
            }
            if(skip) {
                break;
            }
        }
    }
    
    public void handleDraftTaxiSquadPlayers() {
        for(Team t: dataManager.getDraft().getListOfTeams()) {
            boolean skip = false;
            if(t.getPlayersNeededForTS() > 0) {
                // FIND THE SELECTED PLAYERS ELIGIBLE FOR THAT POSITION
                // AND SELECT THE FIRST PLAYER
                Player m = dataManager.getDraft().playersForTaxiSquad().get(0);
                m.setFantasyTeam(t.getTeamName());
                m.setContract(Contract.X);
                m.setSalary(1);
                m.setChosenPosition(dataManager.getDraft().playersForTaxiSquad().get(0).getQPOrRole().split("_")[0]);
                t.addPlayerToTaxiSquad(m);
                dataManager.getDraft().addDraftedPlayer(m);
                dataManager.getDraft().removeFreeAgent(m);
                t.setPlayersNeededForTS(8 - (t.getTaxiSquad().size()));
                skip = true;
                break;
            }
            if(skip) {
                break;
            }
        }
    }
    
    public void showStandingsScreen() {
        standingsScreenPane = new VBox();
        standingsScreenLabel = initLabel(WDK_PropertyType.STANDINGS_HEADING_LABEL, CLASS_HEADING_LABEL);
        standingsScreenPane.getChildren().add(standingsScreenLabel);
        
        fantasyTeamsTable = new TableView();
        teamNameColumn = new TableColumn(COL_TEAM_NAME);
        teamNameColumn.setPrefWidth(125);
        playersNeededColumn = new TableColumn(COL_PLAYERS_NEEDED);
        playersNeededColumn.setPrefWidth(100);
        moneyLeftColumn = new TableColumn(COL_MONEY_LEFT);
        moneyPerPlayerColumn = new TableColumn(COL_MON_PER_PLAYER);
        aggRColumn = new TableColumn(COL_R);
        aggHRColumn = new TableColumn(COL_HR);
        aggRBIColumn = new TableColumn(COL_RBI);
        aggSBColumn = new TableColumn(COL_SB);
        aggBAColumn = new TableColumn(COL_BA);
        aggWColumn = new TableColumn(COL_W);
        aggSVColumn = new TableColumn(COL_SV);
        aggKColumn = new TableColumn(COL_K);
        aggERAColumn = new TableColumn(COL_ERA);
        aggWHIPColumn = new TableColumn(COL_WHIP);
        totalPointsColumn = new TableColumn(COL_TOTAL_POINTS);
        
        teamNameColumn.setCellValueFactory(new PropertyValueFactory<Team, String>("teamName"));
        playersNeededColumn.setCellValueFactory(new PropertyValueFactory<Team, Integer>("playersNeededForSL"));
        moneyLeftColumn.setCellValueFactory(new PropertyValueFactory<Team, Integer>("moneyLeft"));
        moneyPerPlayerColumn.setCellValueFactory(new PropertyValueFactory<Team, Integer>("moneyPerPlayer"));
        aggRColumn.setCellValueFactory(new PropertyValueFactory<Team, Integer>("aggRStat"));
        aggHRColumn.setCellValueFactory(new PropertyValueFactory<Team, Integer>("aggHRStat"));
        aggRBIColumn.setCellValueFactory(new PropertyValueFactory<Team, Integer>("aggRBIStat"));
        aggSBColumn.setCellValueFactory(new PropertyValueFactory<Team, Integer>("aggSBStat"));
        aggBAColumn.setCellValueFactory(new PropertyValueFactory<Team, Double>("aggBAStat"));
        aggWColumn.setCellValueFactory(new PropertyValueFactory<Team, Double>("aggWStat"));
        aggSVColumn.setCellValueFactory(new PropertyValueFactory<Team, Integer>("aggSVStat"));
        aggKColumn.setCellValueFactory(new PropertyValueFactory<Team, Integer>("aggKStat"));
        aggERAColumn.setCellValueFactory(new PropertyValueFactory<Team, Double>("aggERAStat"));
        aggWHIPColumn.setCellValueFactory(new PropertyValueFactory<Team, Double>("aggWHIPStat"));
        totalPointsColumn.setCellValueFactory(new PropertyValueFactory<Team, Integer>("totalPoints"));
        
        fantasyTeamsTable.getColumns().add(teamNameColumn);
        fantasyTeamsTable.getColumns().add(playersNeededColumn);
        fantasyTeamsTable.getColumns().add(moneyLeftColumn);
        fantasyTeamsTable.getColumns().add(moneyPerPlayerColumn);
        fantasyTeamsTable.getColumns().add(aggRColumn);
        fantasyTeamsTable.getColumns().add(aggHRColumn);
        fantasyTeamsTable.getColumns().add(aggRBIColumn);
        fantasyTeamsTable.getColumns().add(aggSBColumn);
        fantasyTeamsTable.getColumns().add(aggBAColumn);
        fantasyTeamsTable.getColumns().add(aggWColumn);
        fantasyTeamsTable.getColumns().add(aggSVColumn);
        fantasyTeamsTable.getColumns().add(aggKColumn);
        fantasyTeamsTable.getColumns().add(aggERAColumn);
        fantasyTeamsTable.getColumns().add(aggWHIPColumn);
        fantasyTeamsTable.getColumns().add(totalPointsColumn);
        fantasyTeamsTable.setItems(dataManager.getDraft().getListOfTeams());
        
        standingsScreenPane.getChildren().add(fantasyTeamsTable);
        workspacePane.setCenter(standingsScreenPane);
        workspaceScrollPane.setContent(workspacePane);
    }
    
    public void showMLBScreen() throws IOException {
        MLBTeamsScreenPane = new VBox();
        MLBTeamsScreenLabel = initLabel(WDK_PropertyType.MLB_TEAMS_HEADING_LABEL, CLASS_HEADING_LABEL);
        MLBTeamsScreenPane.getChildren().add(MLBTeamsScreenLabel);
        
        proTeamLabelAndBox = new GridPane();
        selectProTeamLabel = initGridLabel(proTeamLabelAndBox, WDK_PropertyType.SELECT_PRO_TEAM_LABEL, CLASS_PROMPT_LABEL, 0, 0, 1, 1);
        selectProTeamComboBox = initGridComboBox(proTeamLabelAndBox, 1, 0, 1, 1);
        
        ObservableList<String> proTeamChoices = FXCollections.observableArrayList();
        for(MLBTeam m: MLBTeam.values()) {
            proTeamChoices.add(m.toString());
        }
        selectProTeamComboBox.setItems(proTeamChoices);
        
        playersOnProTeamTable = new TableView();
        MLBPlayerFirstNameColumn = new TableColumn(COL_FIRST_NAME);
        MLBPlayerLastNameColumn = new TableColumn(COL_LAST_NAME);
        MLBPlayerQPColumn = new TableColumn(COL_POSITIONS);
        
        playersOnProTeamTable.getColumns().add(MLBPlayerFirstNameColumn);
        playersOnProTeamTable.getColumns().add(MLBPlayerLastNameColumn);
        playersOnProTeamTable.getColumns().add(MLBPlayerQPColumn);
        
        selectProTeamComboBox.setOnAction(e -> {
            ObservableList<Player> playersWithTeam = FXCollections.observableArrayList();
            for(Player p: dataManager.getDraft().getFreeAgents()) {
                if(p.getMLBTeam().equalsIgnoreCase((String)selectProTeamComboBox.getSelectionModel().getSelectedItem()))
                    playersWithTeam.add(p);
            }
            for(Team t: dataManager.getDraft().getListOfTeams()) {
                for(int i = 0; i < dataManager.getDraft().getTeamWithName(t.getTeamName()).getStartingLineup().size(); i++) {
                    if(dataManager.getDraft().getTeamWithName(t.getTeamName()).getStartingLineup().get(i).getMLBTeam().equalsIgnoreCase(((String)selectProTeamComboBox.getSelectionModel().getSelectedItem())))
                        playersWithTeam.add(dataManager.getDraft().getTeamWithName(t.getTeamName()).getStartingLineup().get(i));
                }       
            }
            for(Team t: dataManager.getDraft().getListOfTeams()) {
                for(int i = 0; i < dataManager.getDraft().getTeamWithName(t.getTeamName()).getTaxiSquad().size(); i++) {
                    if(dataManager.getDraft().getTeamWithName(t.getTeamName()).getTaxiSquad().get(i).getMLBTeam().equalsIgnoreCase(((String)selectProTeamComboBox.getSelectionModel().getSelectedItem())))
                        playersWithTeam.add(dataManager.getDraft().getTeamWithName(t.getTeamName()).getTaxiSquad().get(i));
                }    
            } 
            MLBPlayerFirstNameColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("firstName"));
            MLBPlayerLastNameColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("lastName"));
            MLBPlayerQPColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("QPOrRole"));
            
            if(((String)selectProTeamComboBox.getValue()) == null)
                playersOnProTeamTable.setItems(null);
            else
                playersOnProTeamTable.setItems(playersWithTeam);
        
        });
        
        MLBTeamsScreenPane.getChildren().add(proTeamLabelAndBox);
        MLBTeamsScreenPane.getChildren().add(playersOnProTeamTable);
        workspacePane.setCenter(MLBTeamsScreenPane);
        workspaceScrollPane.setContent(workspacePane);

    }
    
    // INITIALIZE THE WINDOW (i.e. STAGE) PUTTING ALL THE CONTROLS
    // THERE EXCEPT THE WORKSPACE, WHICH WILL BE ADDED THE FIRST
    // TIME A NEW Draft IS CREATED OR LOADED
    private void initWindow(String windowTitle) {
        // SET THE WINDOW TITLE
        primaryStage.setTitle(windowTitle);

        // GET THE SIZE OF THE SCREEN
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        // AND USE IT TO SIZE THE WINDOW
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());

        // ADD THE TOOLBAR ONLY, NOTE THAT THE WORKSPACE
        // HAS BEEN CONSTRUCTED, BUT WON'T BE ADDED UNTIL
        // THE USER STARTS EDITING A Draft
        wdkPane = new BorderPane();
        wdkPane.setTop(fileToolbarPane);
        primaryScene = new Scene(wdkPane);

        // NOW TIE THE SCENE TO THE WINDOW, SELECT THE STYLESHEET
        // WE'LL USE TO STYLIZE OUR GUI CONTROLS, AND OPEN THE WINDOW
        primaryScene.getStylesheets().add(PRIMARY_STYLE_SHEET);
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }
    
    // INIT ALL THE EVENT HANDLERS
    private void initEventHandlers() throws IOException {
        // FIRST THE FILE CONTROLS
        fileController = new FileController(messageDialog, yesNoCancelDialog, draftFileManager, siteExporter);
        newDraftButton.setOnAction(e -> {
            fileController.handleNewDraftRequest(this);
        });
        loadDraftButton.setOnAction(e -> {
            fileController.handleLoadDraftRequest(this);
        });
        saveDraftButton.setOnAction(e -> {
            fileController.handleSaveDraftRequest(this, dataManager.getDraft());
        });

        exitButton.setOnAction(e -> {
            fileController.handleExitRequest(this);
        });
        
        screenController = new ScreenController();
        fantasyTeamsScreenButton.setOnAction(e -> {
            screenController.handleFantasyTeamsScreenRequest(this);
        });
        
        playersScreenButton.setOnAction(e -> {
            screenController.handlePlayersScreenRequest(this);
        });
        
        draftScreenButton.setOnAction(e -> {
            screenController.handleDraftScreenRequest(this);
        });
        
        fantasyStandingsScreenButton.setOnAction(e -> {
            screenController.handleStandingsScreenRequest(this);
        });
        
        MLBTeamsScreenButton.setOnAction(e -> {
            try {
                screenController.handleMLBScreenRequest(this);
            } catch (IOException ex) {
                Logger.getLogger(WDK_GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });    
    }

    // INIT A BUTTON AND ADD IT TO A CONTAINER IN A TOOLBAR
    private Button initChildButton(Pane toolbar, WDK_PropertyType icon, WDK_PropertyType tooltip, boolean disabled) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String imagePath = "file:" + PATH_IMAGES + props.getProperty(icon.toString());
        Image buttonImage = new Image(imagePath);
        Button button = new Button();
        button.setDisable(disabled);
        button.setGraphic(new ImageView(buttonImage));
        Tooltip buttonTooltip = new Tooltip(props.getProperty(tooltip.toString()));
        button.setTooltip(buttonTooltip);
        toolbar.getChildren().add(button);
        return button;
    }
    
    // INIT A LABEL AND SET IT'S STYLESHEET CLASS
    private Label initLabel(WDK_PropertyType labelProperty, String styleClass) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String labelText = props.getProperty(labelProperty);
        Label label = new Label(labelText);
        label.getStyleClass().add(styleClass);
        return label;
    }
    
    // INIT A LABEL AND PUT IT IN A TOOLBAR
    private Label initChildLabel(Pane container, WDK_PropertyType labelProperty, String styleClass) {
        Label label = initLabel(labelProperty, styleClass);
        container.getChildren().add(label);
        return label;
    }
    
    // INIT A LABEL AND PLACE IT IN A GridPane IN ITS PROPER PLACE
    private Label initGridLabel(GridPane container, WDK_PropertyType labelProperty, String styleClass, int col, int row, int colSpan, int rowSpan) {
        Label label = initLabel(labelProperty, styleClass);
        container.add(label, col, row, colSpan, rowSpan);
        return label;
    }
    
    // INIT A COMBO BOX AND PUT IT IN A GridPane
    private ComboBox initGridComboBox(GridPane container, int col, int row, int colSpan, int rowSpan) throws IOException {
        ComboBox comboBox = new ComboBox();
        container.add(comboBox, col, row, colSpan, rowSpan);
        return comboBox;
    }
    
    // INIT A TEXT FIELD AND PUT IT IN A GridPane
    private TextField initGridTextField(GridPane container, int size, String initText, boolean editable, int col, int row, int colSpan, int rowSpan) {
        TextField tf = new TextField();
        tf.setPrefColumnCount(size);
        tf.setText(initText);
        tf.setEditable(editable);
        container.add(tf, col, row, colSpan, rowSpan);
        return tf;
    }
    
    // INIT A BUTTON AND ADD IT TO A GRIDPANE
    private Button initGridButton(GridPane container, WDK_PropertyType icon, WDK_PropertyType tooltip, 
            boolean disabled, int col, int row, int colSpan, int rowSpan) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String imagePath = "file:" + PATH_IMAGES + props.getProperty(icon.toString());
        Image buttonImage = new Image(imagePath);
        Button button = new Button();
        button.setDisable(disabled);
        button.setGraphic(new ImageView(buttonImage));
        Tooltip buttonTooltip = new Tooltip(props.getProperty(tooltip.toString()));
        button.setTooltip(buttonTooltip);
        container.add(button, col, row, colSpan, rowSpan);
        return button;
    }
    
    // REGISTER THE EVENT LISTENER FOR A TEXT FIELD
    private void registerTextFieldController(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            draftController.handleDraftChangeRequest(this);
        });
    }
    
    // LOAD THE COMBO BOX TO HOLD DRAFT TEAMS
    private void loadFantasyTeamsComboBox(ObservableList<Team> teams) {
        selectFantasyTeamComboBox.getItems().clear();
        selectFantasyTeamComboBox.getSelectionModel().clearSelection();
        selectFantasyTeamComboBox.setValue(null);
        for(int i = 0; i < teams.size(); i++) {
            selectFantasyTeamComboBox.getItems().add(teams.get(i).getTeamName());
        }
    }
}
