package wdk.data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    
    public DraftDataManager(DraftDataView initView, ArrayList<Pitcher> pitchers, ArrayList<Hitter> hitters) {
        ObservableList completePlayers = FXCollections.observableArrayList();
        
        for(Pitcher p: pitchers) {
            if(p.getIPStat() == 0.0) {
                p.setSBOrERAStat(0.0);
                p.setBAOrWHIPStat(0.0);
            }
            else {
                double tempSBOrERA = ((double)p.getERStat() * 9)/(p.getIPStat());
                BigDecimal bd = new BigDecimal(tempSBOrERA).setScale(2, RoundingMode.HALF_EVEN);
                tempSBOrERA = bd.doubleValue();
                p.setSBOrERAStat(tempSBOrERA);
                
                double tempBAOrWHIP = ((double)(p.getBBStat() + p.getHStat())/(p.getIPStat()));
                BigDecimal db = new BigDecimal(tempBAOrWHIP).setScale(2, RoundingMode.HALF_EVEN);
                tempBAOrWHIP = db.doubleValue();
                p.setBAOrWHIPStat(tempBAOrWHIP);
            }
        }
        for(Pitcher p: pitchers) {
            completePlayers.add(p);
        }
        for(Hitter h: hitters) {
            if(h.getABStat() == 0.0) {
                h.setBAOrWHIPStat(0.0);
            }
            else {
                double tempBAOrWHIP = ((double)(h.getHStat()) / h.getABStat());
                BigDecimal db = new BigDecimal(tempBAOrWHIP).setScale(3, RoundingMode.HALF_EVEN);
                tempBAOrWHIP = db.doubleValue();
                h.setBAOrWHIPStat(tempBAOrWHIP);
            }
        }
        for(Hitter h: hitters) {
            completePlayers.add(h);
        }
        view = initView;
        
        draft = new Draft(completePlayers);
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
        // CLEAR ALL THE DRAFT VALUES
        /*
        draft.setDraftName(DEFAULT_TEXT);
        draft.clearListOfTeams();
        */
        //draft.clearFreeAgents();
        // AND THEN FORCE THE UI TO RELOAD THE UPDATED COURSE
        view.reloadDraft(draft);
        
    }
    
}
