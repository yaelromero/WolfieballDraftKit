package wdk.controller;

import static wdk.WDK_PropertyType.DRAFT_SAVED_MESSAGE;
import static wdk.WDK_PropertyType.NEW_DRAFT_CREATED_MESSAGE;
import static wdk.WDK_PropertyType.SAVE_UNSAVED_WORK_MESSAGE;
import static wdk.WDK_StartupConstants.PATH_DRAFTS;
import wdk.data.Draft;
import wdk.data.DraftDataManager;
import wdk.error.ErrorHandler;
import wdk.file.DraftFileManager;
import wdk.file.DraftSiteExporter;
import wdk.gui.WDK_GUI;
import wdk.gui.MessageDialog;
import wdk.gui.YesNoCancelDialog;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import properties_manager.PropertiesManager;
import static wdk.WDK_PropertyType.PROP_APP_TITLE;

/**
 * This controller class provides responses to interactions with the buttons in
 * the file toolbar.
 *
 * @author Yael
 */
public class FileController {

    // WE WANT TO KEEP TRACK OF WHEN SOMETHING HAS NOT BEEN SAVED
    private boolean saved;

    // THIS GUY KNOWS HOW TO READ AND WRITE DRAFT DATA
    private DraftFileManager draftIO;

    // THIS GUY KNOWS HOW TO EXPORT THE DRAFT PAGE
    private DraftSiteExporter exporter;

    // THIS WILL PROVIDE FEEDBACK TO THE USER WHEN SOMETHING GOES WRONG
    ErrorHandler errorHandler;
    
    // THIS WILL PROVIDE FEEDBACK TO THE USER AFTER
    // WORK BY THIS CLASS HAS COMPLETED
    MessageDialog messageDialog;
    
    // AND WE'LL USE THIS TO ASK YES/NO/CANCEL QUESTIONS
    YesNoCancelDialog yesNoCancelDialog;
    
    // WE'LL USE THIS TO GET OUR VERIFICATION FEEDBACK
    PropertiesManager properties;

    /**
     * This default constructor starts the program without a draft file being
     * edited.
     *
     * @param initMessageDialog Dialog that will provide feedback to the user.
     * @param initYesNoCancelDialog Dialog that will ask yes/no/cancel questions
     * @param initDraftIO The object that will be reading and writing draft
     * data.
     * @param initExporter The object that will be exporting drafts to Web
     * sites.
     */
    public FileController(
            MessageDialog initMessageDialog,
            YesNoCancelDialog initYesNoCancelDialog,
            DraftFileManager initDraftIO,
            DraftSiteExporter initExporter) {
        // NOTHING YET
        saved = true;
        
        // KEEP THESE GUYS FOR LATER
        draftIO = initDraftIO;
        exporter = initExporter;
        
        // BE READY FOR ERRORS
        errorHandler = ErrorHandler.getErrorHandler();
        
        // AND GET READY TO PROVIDE FEEDBACK
        messageDialog = initMessageDialog;
        yesNoCancelDialog = initYesNoCancelDialog;
        properties = PropertiesManager.getPropertiesManager();
    }
    
    /**
     * This method marks the appropriate variable such that we know
     * that the current Draft has been edited since it's been saved.
     * The UI is then updated to reflect this.
     * 
     * @param gui The user interface editing the Draft.
     */
    public void markAsEdited(WDK_GUI gui) {
        // THE Draft OBJECT IS NOW DIRTY
        saved = false;
        
        // LET THE UI KNOW
        gui.updateToolbarControls(saved);
    }

    /**
     * This method starts the process of editing a new Draft. If a draft is
     * already being edited, it will prompt the user to save it first.
     * 
     * @param gui The user interface editing the Draft.
     */
    public void handleNewDraftRequest(WDK_GUI gui) {
        try {
            // WE MAY HAVE TO SAVE CURRENT WORK
            boolean continueToMakeNew = true;
 
            if (!saved) {
                // THE USER CAN OPT OUT HERE WITH A CANCEL
                continueToMakeNew = promptToSave(gui);
            }
            
            // IF THE USER REALLY WANTS TO MAKE A NEW DRAFT
            if (continueToMakeNew) {
                // RESET THE DATA, WHICH SHOULD TRIGGER A RESET OF THE UI
                DraftDataManager dataManager = gui.getDataManager();
                dataManager.reset();
                gui.showFantasyTeamsScreen();
                saved = false;

                // REFRESH THE GUI, WHICH WILL ENABLE AND DISABLE
                // THE APPROPRIATE CONTROLS
                gui.updateToolbarControls(saved);

                // TELL THE USER THE DRAFT HAS BEEN CREATED
                messageDialog.show(properties.getProperty(NEW_DRAFT_CREATED_MESSAGE));
            }
        } catch (IOException ioe) {
            // SOMETHING WENT WRONG, PROVIDE FEEDBACK
            errorHandler.handleNewDraftError();
        }
    }

    /**
     * This method lets the user open a Draft saved to a file. It will also
     * make sure data for the current Draft is not lost.
     * 
     * @param gui The user interface editing the draft.
     */
    
    public void handleLoadDraftRequest(WDK_GUI gui) {
        try {
            // WE MAY HAVE TO SAVE CURRENT WORK
            boolean continueToOpen = true;
            if (!saved) {
                // THE USER CAN OPT OUT HERE WITH A CANCEL
                continueToOpen = promptToSave(gui);
            }

            // IF THE USER REALLY WANTS TO OPEN A DRAFT
            if (continueToOpen) {
                // GO AHEAD AND PROCEED LOADING A DRAFT
                promptToOpen(gui);
            }
        } catch (IOException ioe) {
            // SOMETHING WENT WRONG
            errorHandler.handleLoadDraftError();
        }
    }

    /**
     * This method will save the current draft to a file. Note that we already
     * know the name of the file, so we won't need to prompt the user.
     * 
     * @param gui The user interface editing the Draft.
     * 
     * @param draftToSave The draft being edited that is to be saved to a file.
     */
    public void handleSaveDraftRequest(WDK_GUI gui, Draft draftToSave) {
        try {
            // SAVE IT TO A FILE
            draftIO.saveDraft(draftToSave);

            // MARK IT AS SAVED
            saved = true;

            // TELL THE USER THE FILE HAS BEEN SAVED
            messageDialog.show(properties.getProperty(DRAFT_SAVED_MESSAGE));

            // AND REFRESH THE GUI, WHICH WILL ENABLE AND DISABLE
            // THE APPROPRIATE CONTROLS
            gui.updateToolbarControls(saved);
        } catch (IOException ioe) {
            errorHandler.handleSaveDraftError();
        }
    }

    /**
     * This method will exit the application, making sure the user doesn't lose
     * any data first.
     * 
     * @param gui
     */
    public void handleExitRequest(WDK_GUI gui) {
        try {
            // WE MAY HAVE TO SAVE CURRENT WORK
            boolean continueToExit = true;
            if (!saved) {
                // THE USER CAN OPT OUT HERE
                continueToExit = promptToSave(gui);
            }

            // IF THE USER REALLY WANTS TO EXIT THE APP
            if (continueToExit) {
                // EXIT THE APPLICATION
                System.exit(0);
            }
        } catch (IOException ioe) {
            ErrorHandler eH = ErrorHandler.getErrorHandler();
            eH.handleExitError();
        }
    }

    /**
     * This helper method verifies that the user really wants to save their
     * unsaved work, which they might not want to do. Note that it could be used
     * in multiple contexts before doing other actions, like creating a new
     * Draft, or opening another Draft. Note that the user will be
     * presented with 3 options: YES, NO, and CANCEL. YES means the user wants
     * to save their work and continue the other action (we return true to
     * denote this), NO means don't save the work but continue with the other
     * action (true is returned), CANCEL means don't save the work and don't
     * continue with the other action (false is returned).
     *
     * @return true if the user presses the YES option to save, true if the user
     * presses the NO option to not save, false if the user presses the CANCEL
     * option to not continue.
     */  
    private boolean promptToSave(WDK_GUI gui) throws IOException {
        // PROMPT THE USER TO SAVE UNSAVED WORK
        yesNoCancelDialog.show(properties.getProperty(SAVE_UNSAVED_WORK_MESSAGE));
        
        // AND NOW GET THE USER'S SELECTION
        String selection = yesNoCancelDialog.getSelection();

        // IF THE USER SAID YES, THEN SAVE BEFORE MOVING ON
        if (selection.equals(YesNoCancelDialog.YES)) {
            // SAVE THE DRAFT
            DraftDataManager dataManager = gui.getDataManager();
            draftIO.saveDraft(dataManager.getDraft());
            saved = true;
            
            // AND THE INSTRUCTOR INFO
            //Instructor lastInstructor = dataManager.getCourse().getInstructor();
            //courseIO.saveLastInstructor(lastInstructor, JSON_FILE_PATH_LAST_INSTRUCTOR);
        } // IF THE USER SAID CANCEL, THEN WE'LL TELL WHOEVER
        // CALLED THIS THAT THE USER IS NOT INTERESTED ANYMORE
        else if (selection.equals(YesNoCancelDialog.CANCEL)) {
            return false;
        }

        // IF THE USER SAID NO, WE JUST GO ON WITHOUT SAVING
        // BUT FOR BOTH YES AND NO WE DO WHATEVER THE USER
        // HAD IN MIND IN THE FIRST PLACE
        return true;      
    }


    /**
     * This helper method asks the user for a file to open. The user-selected
     * file is then loaded and the GUI updated. Note that if the user cancels
     * the open process, nothing is done. If an error occurs loading the file, a
     * message is displayed, but nothing changes.
     */
    private void promptToOpen(WDK_GUI gui) {
        // AND NOW ASK THE USER FOR THE DRAFT TO OPEN
        FileChooser draftFileChooser = new FileChooser();
        draftFileChooser.setInitialDirectory(new File(PATH_DRAFTS));
        File selectedFile = draftFileChooser.showOpenDialog(gui.getWindow());

        // ONLY OPEN A NEW FILE IF THE USER SAYS OK
        if (selectedFile != null) {
            try {
                Draft draftToLoad = gui.getDataManager().getDraft();
                draftIO.loadDraft(draftToLoad, selectedFile.getAbsolutePath());
                gui.reloadDraft(draftToLoad);
                saved = true;
                gui.updateToolbarControls(saved);
                //Instructor lastInstructor = courseToLoad.getInstructor();
                //courseIO.saveLastInstructor(lastInstructor, JSON_FILE_PATH_LAST_INSTRUCTOR);
            } catch (Exception e) {
                ErrorHandler eH = ErrorHandler.getErrorHandler();
                eH.handleLoadDraftError();
            }
        }
    }

    /**
     * This mutator method marks the file as not saved, which means that when
     * the user wants to do a file-type operation, we should prompt the user to
     * save current work first. Note that this method should be called any time
     * the draft is changed in some way.
     */
    
    public void markFileAsNotSaved() {
        saved = false;
    }

    /**
     * Accessor method for checking to see if the current draft has been saved
     * since it was last edited.
     *
     * @return true if the current draft is saved to the file, false otherwise.
     */
    public boolean isSaved() {
        return saved;
    }
}
