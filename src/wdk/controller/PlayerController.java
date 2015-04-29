package wdk.controller;

import wdk.data.Player;
import wdk.error.ErrorHandler;
import wdk.gui.WDK_GUI;
import wdk.gui.MessageDialog;
import wdk.gui.AddNewPlayerDialog;
import wdk.gui.YesNoCancelDialog;
import java.util.Collections;
import java.util.List;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import static wdk.WDK_PropertyType.REMOVE_ITEM_MESSAGE;
import wdk.data.Draft;
import wdk.data.DraftDataManager;

/**
 * This controller class handles the responses to all player
 * editing input, including verification of data and binding of
 * entered data to the Player object.
 * 
 * @author Richard McKenna
 */

public class PlayerController {
    AddNewPlayerDialog sid;
    MessageDialog messageDialog;
    YesNoCancelDialog yesNoCancelDialog;
    
    public PlayerController(Stage initPrimaryStage, Draft draft, MessageDialog initMessageDialog, YesNoCancelDialog initYesNoCancelDialog) {
        sid = new AddNewPlayerDialog(initPrimaryStage, draft, initMessageDialog);
        messageDialog = initMessageDialog;
        yesNoCancelDialog = initYesNoCancelDialog;
    }
    
    // THESE ARE FOR PLAYERS
    
    public void handleAddNewPlayerRequest(WDK_GUI gui) {
        DraftDataManager ddm = gui.getDataManager();
        Draft draft = ddm.getDraft();
        sid.showAddPlayerDialog();
        
        // DID THE USER CONFIRM?
        if (sid.wasCompleteSelected()) {
            // GET THE LECTURE ITEM
            Player pl = sid.getPlayer();
            
            // AND ADD IT AS A ROW TO THE TABLE
            draft.addFreeAgent(pl);
        }
        else {
            // THE USER MUST HAVE PRESSED CANCEL, SO
            // WE DO NOTHING
        }
    }
    
    public void handleRemovePlayerRequest(WDK_GUI gui, Player playerToRemove) {
        // PROMPT THE USER TO SAVE UNSAVED WORK
        yesNoCancelDialog.show(PropertiesManager.getPropertiesManager().getProperty(REMOVE_ITEM_MESSAGE));
        
        // AND NOW GET THE USER'S SELECTION
        String selection = yesNoCancelDialog.getSelection();

        // IF THE USER SAID YES, THEN SAVE BEFORE MOVING ON
        if (selection.equals(YesNoCancelDialog.YES)) { 
            gui.getDataManager().getDraft().removeFreeAgent(playerToRemove);
        }
    }
}