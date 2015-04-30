package wdk.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Yael
 */

public class Draft {
    String draftName;
    ObservableList<Team> listOfTeams;
    ObservableList<Player> freeAgents;
    
    public Draft(ObservableList initFreeAgents) {
        listOfTeams = FXCollections.observableArrayList();
        freeAgents = initFreeAgents;
    }
    
    public String getDraftName() {
        return draftName;
    }
    
    public void setDraftName(String draftName) {
        this.draftName = draftName;
    }
    
    public void setListOfTeams(ObservableList<Team> listOfTeams) {
        this.listOfTeams = listOfTeams;
    }
    
    public ObservableList<Team> getListOfTeams() {
        return listOfTeams;
    }
    
    public void clearListOfTeams() {
        listOfTeams.clear();
    }
    
    public void addTeam(Team a) {
        listOfTeams.add(a);
    }
    
    public void removeTeam(Team a) {
        listOfTeams.remove(a);
    }
    
    public void setFreeAgents(ObservableList<Player> freeAgents) {
        this.freeAgents = freeAgents;
    }
    
    public ObservableList<Player> getFreeAgents() {
        return freeAgents;
    }
    
    public void clearFreeAgents() {
        freeAgents.clear();
    }
    
    public void addFreeAgent(Player p) {
        freeAgents.add(p);
    }
    
    public void removeFreeAgent(Player p) {
        freeAgents.remove(p);
    }
    
    public boolean checkForSame(Player pl) {
        boolean exists = false;
        for(int i = 0; i < freeAgents.size(); i++) {
            if(freeAgents.get(i).getFirstName().equalsIgnoreCase(pl.getFirstName()) &&
                freeAgents.get(i).getLastName().equalsIgnoreCase(pl.getLastName())) {
                exists = true;
                break;
            }
            else {
                exists = false;
            }
        }
        return exists;
    }
}
