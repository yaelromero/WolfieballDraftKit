package wdk.data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yael
 */

public class Draft {
    String draftName;
    List<Team> listOfTeams;
    List<Player> freeAgents;
    
    public Draft() {
        listOfTeams = new ArrayList<>();
        freeAgents = new ArrayList<>();
    }
    
    public String getDraftName() {
        return draftName;
    }
    
    public void setDraftName(String draftName) {
        this.draftName = draftName;
    }
    
    public void setListOfTeams(List<Team> listOfTeams) {
        this.listOfTeams = listOfTeams;
    }
    
    public List<Team> getListOfTeams() {
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
    
    public void setFreeAgents(List<Player> freeAgents) {
        this.freeAgents = freeAgents;
    }
    
    public List<Player> getFreeAgents() {
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
}
