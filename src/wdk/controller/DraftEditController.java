package wdk.controller;

import wdk.data.Draft;
import wdk.error.ErrorHandler;
import wdk.gui.WDK_GUI;

/**
 * This controller class handles the responses to all draft
 * editing input, including verification of data and binding of
 * entered data to the Draft object.
 * 
 * @author Yael
 */

public class DraftEditController {

// WE USE THIS TO MAKE SURE OUR PROGRAMMED UPDATES OF UI
    // VALUES DON'T THEMSELVES TRIGGER EVENTS
    private boolean enabled;

    /**
     * Constructor that gets this controller ready, not much to
     * initialize as the methods for this function are sent all
     * the objects they need as arguments.
     */
    public DraftEditController() {
        enabled = true;
    }

    /**
     * This mutator method lets us enable or disable this controller.
     * 
     * @param enableSetting If false, this controller will not respond to
     * Draft editing. If true, it will.
     */
    public void enable(boolean enableSetting) {
        enabled = enableSetting;
    }

    /**
     * This controller function is called in response to the user changing
     * course details in the UI. It responds by updating the bound Draft
     * object using all the UI values, including the verification of that
     * data.
     * 
     * @param gui The user interface that requested the change.
     */
    public void handleDraftChangeRequest(WDK_GUI gui) {
        if (enabled) {
            try {
                // UPDATE THE DRAFT, VERIFYING INPUT VALUES
                gui.updateDraftInfo(gui.getDataManager().getDraft());
                
                // THE DRAFT IS NOW DIRTY, MEANING IT'S BEEN 
                // CHANGED SINCE IT WAS LAST SAVED, SO MAKE SURE
                // THE SAVE BUTTON IS ENABLED
                gui.getFileController().markAsEdited(gui);
            } catch (Exception e) {
                // SOMETHING WENT WRONG
                ErrorHandler eH = ErrorHandler.getErrorHandler();
                eH.handleUpdateDraftError();
            }
        }
    }
}
