package wdk.data;

import java.util.ArrayList;
import java.util.List;
import wdk.file.DraftFileManager;

/**
 * This class manages a Draft, which means it knows how to
 * reset one with default values.
 * 
 * @author Yael
 */
public class DraftDataManager {
    // THIS IS THE DRAFT BEING EDITED 
    Draft draft;
    
    // THIS IS THE UI, WHICH MUST BE UPDATED
    // WHENEVER OUR MODEL'S DATA CHANGES 
    DraftDataView view;
    
    // THIS HELPS US LOAD THINGS FOR OUR DRAFT
    DraftFileManager fileManager;
    
    static String          DEFAULT_TEXT = "Unknown";
    static List<Team>      DEFAULT_TEAMS = new ArrayList<>();
    static List<Player>    DEFAULT_FREE_AGENTS = new ArrayList<>();
    
    public DraftDataManager(DraftDataView initView) {
        view = initView;
    }
    
    /**
     * Accessor method for getting the draft that this class manages.
     */
    
    public Draft getDraft() {
        return draft;
    }
    
    /**
     * Accessor method for getting the file manager, which knows how
     * to read and write draft data from/to files.
     */
    public DraftFileManager getFileManager() {
        return fileManager;
    }

    /**
     * Resets the course to its default initialized settings, triggering
     * the UI to reflect these changes.
     */
    public void reset() {
        // CLEAR ALL THE COURSE VALUES
        draft.setDraftName(DEFAULT_TEXT);
        draft.setListOfTeams(DEFAULT_TEAMS);
        draft.setFreeAgents(DEFAULT_FREE_AGENTS);
        
        // AND THEN FORCE THE UI TO RELOAD THE UPDATED COURSE
        view.reloadDraft(draft);
    }
    
}
