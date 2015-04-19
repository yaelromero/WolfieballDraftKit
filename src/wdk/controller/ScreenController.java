package wdk.controller;

import java.io.IOException;
import java.util.ArrayList;
import wdk.data.Hitter;
import wdk.data.Pitcher;
import wdk.gui.WDK_GUI;

/**
 * This controller class provides responses to interactions with the buttons in
 * the screen toolbar.
 *
 * @author Yael
 */

public class ScreenController {
    
    public ScreenController() {}
    
    public void handlePlayersScreenRequest(WDK_GUI gui) {
        gui.showPlayersScreen();
    }
    
    public void handleFantasyTeamsScreenRequest(WDK_GUI gui) {
        gui.showFantasyTeamsScreen();
    }
    
    public void handleDraftScreenRequest(WDK_GUI gui) {
        gui.showDraftScreen();
    }
    
    public void handleStandingsScreenRequest(WDK_GUI gui) {
        gui.showStandingsScreen();
    }
    
    public void handleMLBScreenRequest(WDK_GUI gui) {
        gui.showMLBScreen();
    }
}