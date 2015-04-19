package wdk.gui;

import static wdk.WDK_StartupConstants.*;
import wdk.WDK_PropertyType;
import wdk.controller.FileController;
import wdk.file.DraftFileManager;
import wdk.file.DraftSiteExporter;
import wdk.data.Contract;
import wdk.data.Draft;
import wdk.data.DraftDataManager;
import wdk.data.DraftDataView;
import wdk.data.Hitter;
import wdk.data.Pitcher;
import wdk.data.Player;
import wdk.data.Team;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import wdk.controller.ScreenController;

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
    static final int SMALL_TEXT_FIELD_LENGTH = 5;

    // THIS MANAGES ALL OF THE APPLICATION'S DATA
    DraftDataManager dataManager;

    // THIS MANAGES DRAFT FILE I/O
    DraftFileManager draftFileManager;

    // THIS MANAGES EXPORTING OUR SITE PAGES
    DraftSiteExporter siteExporter;

    // THIS HANDLES INTERACTIONS WITH FILE-RELATED CONTROLS
    FileController fileController;
    
    // THIS HANDLES INTERACTIONS WITH SCREEN-RELATED CONTROLS
    ScreenController screenController;
    
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
    VBox fantasyTeamsPane;
    
    // THESE ARE THE CONTROLS FOR OUR DRAFT SCREEN
    Label draftScreenLabel;
    VBox draftScreenPane;
    
    // THESE ARE THE CONTROLS FOR OUR STANDINGS SCREEN
    Label standingsScreenLabel;
    VBox standingsScreenPane;
    
    // THESE ARE THE CONTROLS FOR OUR MLB TEAM SCREEN
    Label MLBTeamsScreenLabel;
    VBox MLBTeamsScreenPane;

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
    static final String COL_POSITIONS = "Positions";
    static final String COL_YEAR_OF_BIRTH = "Year of Birth";
    static final String COL_RW = "R/W";
    static final String COL_HRSV = "HR/SV";
    static final String COL_RBIK = "RBI/K";
    static final String COL_SBERA = "SBI/ERA";
    static final String COL_BAWHIP = "BA/WHIP";
    static final String COL_EST_VAL = "Estimated Value";
    static final String COL_NOTES = "Notes";
   
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
    public void initGUI(String windowTitle, ArrayList<Pitcher> pitchers, ArrayList<Hitter> hitters) throws IOException {
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
 
        workspacePane.setCenter(fantasyTeamsPane);

        // AND NOW PUT IT IN THE WORKSPACE
        workspaceScrollPane = new ScrollPane();
        workspaceScrollPane.setContent(workspacePane);
        workspaceScrollPane.setFitToWidth(true);
        workspaceScrollPane.getStyleClass().add(CLASS_BORDERED_PANE);
     
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
        playerFirstNameColumn = new TableColumn(COL_FIRST_NAME);
        playerLastNameColumn = new TableColumn(COL_LAST_NAME);
        playerProTeamColumn = new TableColumn(COL_PRO_TEAM);
        playerPositionsColumn = new TableColumn(COL_POSITIONS);
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
    }
    
    public void showFantasyTeamsScreen() {   
        workspacePane.setCenter(fantasyTeamsPane);
        workspaceScrollPane.setContent(workspacePane);
    }
    
    public void showDraftScreen() {
        draftScreenPane = new VBox();
        draftScreenLabel = initLabel(WDK_PropertyType.DRAFT_HEADING_LABEL, CLASS_HEADING_LABEL);
        draftScreenPane.getChildren().add(draftScreenLabel);
        workspacePane.setCenter(draftScreenPane);
        workspaceScrollPane.setContent(workspacePane);     
    }
    
    public void showStandingsScreen() {
        standingsScreenPane = new VBox();
        standingsScreenLabel = initLabel(WDK_PropertyType.STANDINGS_HEADING_LABEL, CLASS_HEADING_LABEL);
        standingsScreenPane.getChildren().add(standingsScreenLabel);
        workspacePane.setCenter(standingsScreenPane);
        workspaceScrollPane.setContent(workspacePane);
    }
    
    public void showMLBScreen() {
        MLBTeamsScreenPane = new VBox();
        MLBTeamsScreenLabel = initLabel(WDK_PropertyType.MLB_TEAMS_HEADING_LABEL, CLASS_HEADING_LABEL);
        MLBTeamsScreenPane.getChildren().add(MLBTeamsScreenLabel);
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
            screenController.handleMLBScreenRequest(this);
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
}