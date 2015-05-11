/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wdk.gui;

import java.util.Timer;
import java.util.TimerTask;
import javafx.event.ActionEvent;
import wdk.data.Team;


/**
 *
 * @author Yael
 */

public class AutomatedDrafting {
    WDK_GUI gui;
    
    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            boolean SLDone = false;
            for(Team t: gui.dataManager.getDraft().getListOfTeams()) {
                if(t.getPlayersNeededForSL() == 0) {
                    SLDone = true;
                }
                else {
                    SLDone = false;
                    break;
                }
            }
            
            if(SLDone == false) {
                gui.handleDraftStartingLineupPlayers();            
            }
            else
                gui.handleDraftTaxiSquadPlayers();
        }
    }
    
    public AutomatedDrafting(WDK_GUI gui) {
        this.gui = gui;
        Timer timer = new Timer();
        timer.schedule(new MyTimerTask(), 0, 500);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        gui.pauseAutoDraft.setOnAction((ActionEvent e) -> {
            timer.cancel();
        });
    }
}
