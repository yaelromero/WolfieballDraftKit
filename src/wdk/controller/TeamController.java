package wdk.controller;

import wdk.data.Player;
import wdk.error.ErrorHandler;
import wdk.gui.WDK_GUI;
import wdk.gui.MessageDialog;
import wdk.gui.TeamDialog;
import wdk.gui.YesNoCancelDialog;
import java.util.Collections;
import java.util.List;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import static wdk.WDK_PropertyType.REMOVE_ITEM_MESSAGE;
import wdk.data.Draft;
import wdk.data.DraftDataManager;
import wdk.data.Team;
import static wdk.data.Team.DEFAULT_TEAM_NAME;
import static wdk.data.Team.DEFAULT_TEAM_OWNER;

/**
 * This controller class handles the responses to all team
 * editing input, including verification of data and binding of
 * entered data to the Team object.
 * 
 * @author Yael
 */

public class TeamController {
    TeamDialog sid;
    MessageDialog messageDialog;
    YesNoCancelDialog yesNoCancelDialog;
    
    public TeamController(Stage initPrimaryStage, Draft draft, MessageDialog initMessageDialog, YesNoCancelDialog initYesNoCancelDialog) {
        sid = new TeamDialog(initPrimaryStage, draft, initMessageDialog);
        messageDialog = initMessageDialog;
        yesNoCancelDialog = initYesNoCancelDialog;
    }
    
    // THESE ARE FOR TEAMS
    
    public void handleAddNewTeamRequest(WDK_GUI gui) {
        DraftDataManager ddm = gui.getDataManager();
        Draft draft = ddm.getDraft();
        sid.showAddTeamDialog();
        
        // DID THE USER CONFIRM?
        if (sid.wasCompleteSelected()) {
            // GET THE LECTURE ITEM
            Team tm = sid.getTeam();
            
            if(tm.getTeamName().equalsIgnoreCase(DEFAULT_TEAM_NAME) ||
                    tm.getTeamName().isEmpty() || tm.getTeamOwner().equalsIgnoreCase(DEFAULT_TEAM_OWNER) ||
                    tm.getTeamOwner().isEmpty()) {
                messageDialog.show("Please enter valid values for each field.");
            }
            else {
                // ADD THE TEAM TO THE DRAFT
                draft.addTeam(tm);
            }
        }
        else {
            // THE USER MUST HAVE PRESSED CANCEL, SO
            // WE DO NOTHING
        }    
        if(gui.getDataManager().getDraft().getListOfTeams().size() == 0) {
            gui.updateFantasyTeamToolbarControls(true);
        }
        else {
            gui.updateFantasyTeamToolbarControls(false);
        }
    }
    
    public void handleEditTeamRequest(WDK_GUI gui, Team teamToEdit) {
        DraftDataManager ddm = gui.getDataManager();
        Draft draft = ddm.getDraft();
        sid.showEditTeamDialog(teamToEdit);
        
        // DID THE USER CONFIRM?
        if (sid.wasCompleteSelected()) {
            // UPDATE THE TEAM
            Team tm = sid.getTeam();
            
            if(tm.getTeamName().equalsIgnoreCase(DEFAULT_TEAM_NAME) ||
                    tm.getTeamName().isEmpty() || tm.getTeamOwner().equalsIgnoreCase(DEFAULT_TEAM_OWNER) ||
                    tm.getTeamOwner().isEmpty()) {
                messageDialog.show("Please enter valid values for each field.");
            }
            else {
                teamToEdit.setTeamName(tm.getTeamName());
                teamToEdit.setTeamOwner(tm.getTeamOwner());
            }
        }
        else {
            // THE USER MUST HAVE PRESSED CANCEL, SO
            // WE DO NOTHING
        }        
        if(gui.getDataManager().getDraft().getListOfTeams().size() == 0) {
            gui.updateFantasyTeamToolbarControls(true);
        }
        else {
            gui.updateFantasyTeamToolbarControls(false);
        }
    }
    
    public void handleRemoveTeamRequest(WDK_GUI gui, Team teamToRemove) {
        // PROMPT THE USER TO SAVE UNSAVED WORK
        yesNoCancelDialog.show(PropertiesManager.getPropertiesManager().getProperty(REMOVE_ITEM_MESSAGE));
        
        // AND NOW GET THE USER'S SELECTION
        String selection = yesNoCancelDialog.getSelection();

        // IF THE USER SAID YES, THEN SAVE BEFORE MOVING ON
        if (selection.equals(YesNoCancelDialog.YES)) { 
            for(Player p: teamToRemove.getStartingLineup()) {
                // remove player from team
                // and then add to free agent
                gui.getDataManager().getDraft().addFreeAgent(p);
                gui.getDataManager().getDraft().removePlayerFromTeam(p, teamToRemove);             
            }
            teamToRemove.getStartingLineup().clear();
            gui.getDataManager().getDraft().removeTeam(teamToRemove);       
        }
        if(gui.getDataManager().getDraft().getListOfTeams().size() == 0) {
            gui.updateFantasyTeamToolbarControls(true);
        }
        else {
            gui.updateFantasyTeamToolbarControls(false);
        }
    }
}