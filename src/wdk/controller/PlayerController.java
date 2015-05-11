package wdk.controller;

import wdk.data.Player;
import wdk.error.ErrorHandler;
import wdk.gui.WDK_GUI;
import wdk.gui.MessageDialog;
import wdk.gui.PlayerDialog;
import wdk.gui.YesNoCancelDialog;
import java.util.Collections;
import java.util.List;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import static wdk.WDK_PropertyType.REMOVE_ITEM_MESSAGE;
import wdk.data.Contract;
import wdk.data.Draft;
import wdk.data.DraftDataManager;
import static wdk.data.Player.*;

/**
 * This controller class handles the responses to all player
 * editing input, including verification of data and binding of
 * entered data to the Player object.
 * 
 * @author Yael
 */

public class PlayerController {
    PlayerDialog sid;
    MessageDialog messageDialog;
    YesNoCancelDialog yesNoCancelDialog;
    
    public PlayerController(Stage initPrimaryStage, Draft draft, MessageDialog initMessageDialog, YesNoCancelDialog initYesNoCancelDialog) {
        sid = new PlayerDialog(initPrimaryStage, draft, initMessageDialog);
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
            
            if(pl.getFirstName().equalsIgnoreCase(DEFAULT_FIRST_NAME) ||
                    pl.getLastName().equalsIgnoreCase(DEFAULT_LAST_NAME) ||
                    pl.getMLBTeam().equalsIgnoreCase(DEFAULT_MLB_TEAM) ||
                    pl.getQPOrRole().isEmpty() || pl.getFirstName().isEmpty() ||
                    pl.getLastName().isEmpty() || pl.getMLBTeam().isEmpty()) {
                messageDialog.show("Please enter all values for the player.");
            }
            else {
                if(draft.checkForSameInFreeAgents(pl) == true) {
                    messageDialog.show("A player with the same name already exists!");
                }
                else {
                    // AND ADD IT AS A ROW TO THE TABLE
                    draft.addFreeAgent(pl);
                }
            }
        }
        else {
            // THE USER MUST HAVE PRESSED CANCEL, SO
            // WE DO NOTHING
        }
    }

    public void handleEditPlayerRequest(WDK_GUI gui, Player playerToEdit) {
        DraftDataManager ddm = gui.getDataManager();
        Draft draft = ddm.getDraft();
        sid.showEditPlayerDialog(draft, playerToEdit);
        
        // DID THE USER CONFIRM?
        if(sid.wasCompleteSelected()) {
            // UPDATE THE PLAYER
            // WITH THEIR FANTASY TEAM
            // AND POSITION
            // AND CONTRACT
            // AND SALARY
            Player p = sid.getPlayer();
            
            
            // CHECK IF THE FANTASY TEAM SELECTED WAS FREE AGENT
            if(p.getFantasyTeam().equals("Free Agent")) {
                if(draft.checkForSameInFreeAgents(p) == false) {
                    for(int i = 0; i < draft.getListOfTeams().size(); i++) {
                        if(draft.getListOfTeams().get(i).checkIfPlayerInSL(p) == true) {
                            draft.getListOfTeams().get(i).removePlayerFromStartingLineup(p);
                            draft.removeDraftedPlayer(p);
                            draft.getListOfTeams().get(i).setPlayersNeededForSL(23 - draft.getListOfTeams().get(i).getStartingLineup().size());
                            draft.getListOfTeams().get(i).setMoneyLeft(draft.getListOfTeams().get(i).calcMoneyLeft());
                            draft.getListOfTeams().get(i).setMoneyPerPlayer(draft.getListOfTeams().get(i).calcMoneyPerPlayer());
                            draft.getListOfTeams().get(i).setAggRStat(draft.getListOfTeams().get(i).calcAggRStat());
                            draft.getListOfTeams().get(i).setAggHRStat(draft.getListOfTeams().get(i).calcAggHRStat());
                            draft.getListOfTeams().get(i).setAggRBIStat(draft.getListOfTeams().get(i).calcAggRBIStat());
                            draft.getListOfTeams().get(i).setAggSBStat(draft.getListOfTeams().get(i).calcAggSBStat());
                            draft.getListOfTeams().get(i).setAggBAStat(draft.getListOfTeams().get(i).calcAggBAStat());
                            draft.getListOfTeams().get(i).setAggWStat(draft.getListOfTeams().get(i).calcAggWStat());
                            draft.getListOfTeams().get(i).setAggSVStat(draft.getListOfTeams().get(i).calcAggSVStat());
                            draft.getListOfTeams().get(i).setAggKStat(draft.getListOfTeams().get(i).calcAggKStat());
                            draft.getListOfTeams().get(i).setAggERAStat(draft.getListOfTeams().get(i).calcAggERAStat());
                            draft.getListOfTeams().get(i).setAggWHIPStat(draft.getListOfTeams().get(i).calcAggWHIPStat());  
                            draft.calcPointsForWHIP();
                            draft.calcPointsForERA();
                            draft.calcPointsForK();
                            draft.calcPointsForSV();
                            draft.calcPointsForW();
                            draft.calcPointsForBA();
                            draft.calcPointsForSB();
                            draft.calcPointsForRBI();
                            draft.calcPointsForHR();
                            draft.calcPointsForR();
                            draft.calcTotalPoints();
                        }
                    }
                    draft.addFreeAgent(p);
                    p.setFantasyTeam("");
                }
                else if(draft.checkForSameInFreeAgents(p) == true) {
                    messageDialog.show("This player is currently in the Free Agent Pool!");
                }
            }
            else if(((p.getFantasyTeam().equalsIgnoreCase(DEFAULT_FANTASY_TEAM)) ||
                    (p.getFantasyTeam().isEmpty()) ||
                    (p.getChosenPosition().equalsIgnoreCase("")) ||
                    (p.getContract() == null) ||
                    (p.getSalary() == 0)) && !p.getFantasyTeam().equals("Free Agent")) {
                
                messageDialog.show("Error: invalid or incomplete values!");
            }
            else if(p.getFantasyTeam().contains("Taxi")) {
                int i = p.getFantasyTeam().indexOf('\'');
                String pla = p.getFantasyTeam().substring(0, i);
                draft.removeFreeAgent(p);
                draft.addPlayerToTS(p, draft.getTeamWithName(pla));
            }
            else {
                // NOW WE CHECK THE CASE IF USER HAS SELECTED TO PUT THE PLAYER ON A SPECIFIC TEAM  
                // REMOVE THEM FROM THE FREE AGENT LIST IF NECESSARY AND ADD THEM TO TEAM
                // OTHERWISE REMOVE THEM FROM PREVIOUS TEAM AND ADD THEM TO A NEW TEAM
                
                if(draft.checkForSameInFreeAgents(p) == false) { // DO NOT COME FROM FREE AGENT POOL
                    if(draft.checkForSameInGivenTeam(p, draft.getTeamWithName(p.getFantasyTeam())) == true) {
                        messageDialog.show("This player already exists on this team!");
                    }
                    else if(draft.checkForSameInGivenTeam(p, draft.getTeamWithName(p.getFantasyTeam())) == false) {
                        for(int i = 0; i < draft.getListOfTeams().size(); i++) {
                            if(draft.getListOfTeams().get(i).checkIfPlayerInSL(p) == true) {
                                draft.getListOfTeams().get(i).removePlayerFromStartingLineup(p);
                                draft.removeDraftedPlayer(p);
                                draft.getListOfTeams().get(i).setPlayersNeededForSL(23 - draft.getListOfTeams().get(i).getStartingLineup().size());
                                draft.getListOfTeams().get(i).setMoneyLeft(draft.getListOfTeams().get(i).calcMoneyLeft());
                                draft.getListOfTeams().get(i).setMoneyPerPlayer(draft.getListOfTeams().get(i).calcMoneyPerPlayer());
                                draft.getListOfTeams().get(i).setAggRStat(draft.getListOfTeams().get(i).calcAggRStat());
                                draft.getListOfTeams().get(i).setAggHRStat(draft.getListOfTeams().get(i).calcAggHRStat());
                                draft.getListOfTeams().get(i).setAggRBIStat(draft.getListOfTeams().get(i).calcAggRBIStat());
                                draft.getListOfTeams().get(i).setAggSBStat(draft.getListOfTeams().get(i).calcAggSBStat());
                                draft.getListOfTeams().get(i).setAggBAStat(draft.getListOfTeams().get(i).calcAggBAStat());
                                draft.getListOfTeams().get(i).setAggWStat(draft.getListOfTeams().get(i).calcAggWStat());
                                draft.getListOfTeams().get(i).setAggSVStat(draft.getListOfTeams().get(i).calcAggSVStat());
                                draft.getListOfTeams().get(i).setAggKStat(draft.getListOfTeams().get(i).calcAggKStat());
                                draft.getListOfTeams().get(i).setAggERAStat(draft.getListOfTeams().get(i).calcAggERAStat());
                                draft.getListOfTeams().get(i).setAggWHIPStat(draft.getListOfTeams().get(i).calcAggWHIPStat());
                                draft.calcPointsForWHIP();
                                draft.calcPointsForERA();
                                draft.calcPointsForK();
                                draft.calcPointsForSV();
                                draft.calcPointsForW();
                                draft.calcPointsForBA();
                                draft.calcPointsForSB();
                                draft.calcPointsForRBI();
                                draft.calcPointsForHR();
                                draft.calcPointsForR();
                                draft.calcTotalPoints();
                            }
                        }
                        draft.addPlayerToTeam(p, draft.getTeamWithName(p.getFantasyTeam()));
                        if(p.getContract().equals(Contract.S2)) {
                            draft.addDraftedPlayer(p);
                        }
                        draft.getTeamWithName(p.getFantasyTeam()).setPlayersNeededForSL(23 - draft.getTeamWithName(p.getFantasyTeam()).getStartingLineup().size());
                        draft.getTeamWithName(p.getFantasyTeam()).setMoneyLeft(draft.getTeamWithName(p.getFantasyTeam()).calcMoneyLeft());
                        draft.getTeamWithName(p.getFantasyTeam()).setMoneyPerPlayer(draft.getTeamWithName(p.getFantasyTeam()).calcMoneyPerPlayer());
                        draft.getTeamWithName(p.getFantasyTeam()).setAggRStat(draft.getTeamWithName(p.getFantasyTeam()).calcAggRStat());
                        draft.getTeamWithName(p.getFantasyTeam()).setAggHRStat(draft.getTeamWithName(p.getFantasyTeam()).calcAggHRStat());
                        draft.getTeamWithName(p.getFantasyTeam()).setAggRBIStat(draft.getTeamWithName(p.getFantasyTeam()).calcAggRBIStat());
                        draft.getTeamWithName(p.getFantasyTeam()).setAggSBStat(draft.getTeamWithName(p.getFantasyTeam()).calcAggSBStat());
                        draft.getTeamWithName(p.getFantasyTeam()).setAggBAStat(draft.getTeamWithName(p.getFantasyTeam()).calcAggBAStat());
                        draft.getTeamWithName(p.getFantasyTeam()).setAggWStat(draft.getTeamWithName(p.getFantasyTeam()).calcAggWStat());
                        draft.getTeamWithName(p.getFantasyTeam()).setAggSVStat(draft.getTeamWithName(p.getFantasyTeam()).calcAggSVStat());
                        draft.getTeamWithName(p.getFantasyTeam()).setAggKStat(draft.getTeamWithName(p.getFantasyTeam()).calcAggKStat());
                        draft.getTeamWithName(p.getFantasyTeam()).setAggERAStat(draft.getTeamWithName(p.getFantasyTeam()).calcAggERAStat());
                        draft.getTeamWithName(p.getFantasyTeam()).setAggWHIPStat(draft.getTeamWithName(p.getFantasyTeam()).calcAggWHIPStat());
                        draft.calcPointsForWHIP();
                        draft.calcPointsForERA();
                        draft.calcPointsForK();
                        draft.calcPointsForSV();
                        draft.calcPointsForW();
                        draft.calcPointsForBA();
                        draft.calcPointsForSB();
                        draft.calcPointsForRBI();
                        draft.calcPointsForHR();
                        draft.calcPointsForR();
                        draft.calcTotalPoints();
                    }
                }
                else { // COME FROM FREE AGENT POOL
                    if(draft.checkForSameInGivenTeam(p, draft.getTeamWithName(p.getFantasyTeam())) == true) {
                        messageDialog.show("This player already exists on this team!");
                    }
                    else if(draft.checkForSameInGivenTeam(p, draft.getTeamWithName(p.getFantasyTeam())) == false) {
                        draft.addPlayerToTeam(p, draft.getTeamWithName(p.getFantasyTeam()));
                        if(p.getContract().equals(Contract.S2)) {
                            draft.addDraftedPlayer(p);
                        }
                        draft.getTeamWithName(p.getFantasyTeam()).setPlayersNeededForSL(23 - draft.getTeamWithName(p.getFantasyTeam()).getStartingLineup().size());
                        draft.getTeamWithName(p.getFantasyTeam()).setMoneyLeft(draft.getTeamWithName(p.getFantasyTeam()).calcMoneyLeft());
                        draft.getTeamWithName(p.getFantasyTeam()).setMoneyPerPlayer(draft.getTeamWithName(p.getFantasyTeam()).calcMoneyPerPlayer());
                        draft.getTeamWithName(p.getFantasyTeam()).setAggRStat(draft.getTeamWithName(p.getFantasyTeam()).calcAggRStat());
                        draft.getTeamWithName(p.getFantasyTeam()).setAggHRStat(draft.getTeamWithName(p.getFantasyTeam()).calcAggHRStat());
                        draft.getTeamWithName(p.getFantasyTeam()).setAggRBIStat(draft.getTeamWithName(p.getFantasyTeam()).calcAggRBIStat());
                        draft.getTeamWithName(p.getFantasyTeam()).setAggSBStat(draft.getTeamWithName(p.getFantasyTeam()).calcAggSBStat());
                        draft.getTeamWithName(p.getFantasyTeam()).setAggBAStat(draft.getTeamWithName(p.getFantasyTeam()).calcAggBAStat());
                        draft.getTeamWithName(p.getFantasyTeam()).setAggWStat(draft.getTeamWithName(p.getFantasyTeam()).calcAggWStat());
                        draft.getTeamWithName(p.getFantasyTeam()).setAggSVStat(draft.getTeamWithName(p.getFantasyTeam()).calcAggSVStat());
                        draft.getTeamWithName(p.getFantasyTeam()).setAggKStat(draft.getTeamWithName(p.getFantasyTeam()).calcAggKStat());
                        draft.getTeamWithName(p.getFantasyTeam()).setAggERAStat(draft.getTeamWithName(p.getFantasyTeam()).calcAggERAStat());
                        draft.getTeamWithName(p.getFantasyTeam()).setAggWHIPStat(draft.getTeamWithName(p.getFantasyTeam()).calcAggWHIPStat());
                        draft.calcPointsForWHIP();
                        draft.calcPointsForERA();
                        draft.calcPointsForK();
                        draft.calcPointsForSV();
                        draft.calcPointsForW();
                        draft.calcPointsForBA();
                        draft.calcPointsForSB();
                        draft.calcPointsForRBI();
                        draft.calcPointsForHR();
                        draft.calcPointsForR();
                        draft.calcTotalPoints();
                        draft.removeFreeAgent(p);
                    }
                }           
            }
        }
        else {
            // THE USER MUST HAVE PRESSED CANCEL SO WE DO NOTHING
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
