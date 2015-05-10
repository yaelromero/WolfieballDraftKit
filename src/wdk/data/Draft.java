package wdk.data;

import java.util.ArrayList;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import wdk.comparators.TeamBAStatComparator;
import wdk.comparators.TeamERAStatComparator;
import wdk.comparators.TeamHRStatComparator;
import wdk.comparators.TeamKStatComparator;
import wdk.comparators.TeamRBIStatComparator;
import wdk.comparators.TeamRStatComparator;
import wdk.comparators.TeamSBStatComparator;
import wdk.comparators.TeamSVStatComparator;
import wdk.comparators.TeamWHIPStatComparator;
import wdk.comparators.TeamWStatComparator;

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
    
    public Team getTeamWithName(String name) {
        Team result = null;
        for(Team t: listOfTeams) {
            if(t.getTeamName().equalsIgnoreCase(name)) {
                result = t;
            }
        }
        return result;
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
        for(int i = 0; i < freeAgents.size(); i++) {
            if(freeAgents.get(i).getFirstName().equalsIgnoreCase(p.getFirstName()) &&
                    freeAgents.get(i).getLastName().equalsIgnoreCase(p.getLastName())) {
                freeAgents.remove(i);
            }
        }
    }
    
    public boolean checkForSameInFreeAgents(Player pl) {
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
    
    public boolean checkForSameInGivenTeam(Player p, Team t) {
        boolean exists = false;
        for(int i = 0; i < t.getStartingLineup().size(); i++) {
            if(t.getStartingLineup().get(i).getFirstName().equalsIgnoreCase(p.getFirstName()) &&
                t.getStartingLineup().get(i).getLastName().equalsIgnoreCase(p.getLastName())) {
                exists = true;
                break;
            }
            else {
                exists = false;
            }
        }
        return exists;
    }
    
    public void addPlayerToTeam(Player p, Team t) {
        for(int i = 0; i < getListOfTeams().size(); i++) {
            if(t.equals(getListOfTeams().get(i))){
                getListOfTeams().get(i).addPlayerToStartingLineup(p);
            }
        }
    }
    
    public void removePlayerFromTeam(Player p, Team t) {
        for(int i = 0; i < getListOfTeams().size(); i++) {
            if(t.equals(getListOfTeams().get(i))) {
                getListOfTeams().get(i).removePlayerFromStartingLineup(p);
            }
        }
    }
  
    public void calcPointsForWHIP() {
        int maxPointsForWHIP = listOfTeams.size();
        
        ArrayList<Team> teams = new ArrayList<Team>();
        for(Team t: listOfTeams) {
            teams.add(t);
        }
        Collections.sort(teams, new TeamWHIPStatComparator());
        
        for(int i = 0; i < teams.size(); i++) {
            teams.get(i).setWHIPPoints(maxPointsForWHIP);
            maxPointsForWHIP--;
        }
        
        for(int i = 0; i < teams.size() - 1; i++) {
            if(teams.get(i).getAggWHIPStat() == teams.get(i+1).getAggWHIPStat()) {
                teams.get(i+1).setWHIPPoints(teams.get(i).getWHIPPoints());
            }
        }
    }
    
    public void calcPointsForERA() {
        int maxPointsForERA = listOfTeams.size();
        
        ArrayList<Team> teams = new ArrayList<Team>();
        for(Team t: listOfTeams) {
            teams.add(t);
        }
        Collections.sort(teams, new TeamERAStatComparator());
        
        for(int i = 0; i < teams.size(); i++) {
            teams.get(i).setERAPoints(maxPointsForERA);
            maxPointsForERA--;
        }
        
        for(int i = 0; i < teams.size() - 1; i++) {
            if(teams.get(i).getAggERAStat() == teams.get(i+1).getAggERAStat()) {
                teams.get(i+1).setERAPoints(teams.get(i).getERAPoints());
            }
        }
    }
    
    public void calcPointsForK() {
        int maxPointsForK = listOfTeams.size();
        
        ArrayList<Team> teams = new ArrayList<Team>();
        for(Team t: listOfTeams) {
            teams.add(t);
        }
        Collections.sort(teams, new TeamKStatComparator());
        
        for(int i = 0; i < teams.size(); i++) {
            teams.get(i).setKPoints(maxPointsForK);
            maxPointsForK--;
        }
        
        for(int i = 0; i < teams.size() - 1; i++) {
            if(teams.get(i).getAggKStat() == teams.get(i+1).getAggKStat()) {
                teams.get(i+1).setKPoints(teams.get(i).getKPoints());
            }
        }
    }
    
    public void calcPointsForSV() {
        int maxPointsForSV = listOfTeams.size();
        
        ArrayList<Team> teams = new ArrayList<Team>();
        for(Team t: listOfTeams) {
            teams.add(t);
        }
        Collections.sort(teams, new TeamSVStatComparator());
        
        for(int i = 0; i < teams.size(); i++) {
            teams.get(i).setSVPoints(maxPointsForSV);
            maxPointsForSV--;
        }
        
        for(int i = 0; i < teams.size() - 1; i++) {
            if(teams.get(i).getAggSVStat() == teams.get(i+1).getAggSVStat()) {
                teams.get(i+1).setSVPoints(teams.get(i).getSVPoints());
            }
        }
    }
    
    public void calcPointsForW() {
        int maxPointsForW = listOfTeams.size();
        
        ArrayList<Team> teams = new ArrayList<Team>();
        for(Team t: listOfTeams) {
            teams.add(t);
        }
        Collections.sort(teams, new TeamWStatComparator());
        
        for(int i = 0; i < teams.size(); i++) {
            teams.get(i).setWPoints(maxPointsForW);
            maxPointsForW--;
        }
        
        for(int i = 0; i < teams.size() - 1; i++) {
            if(teams.get(i).getAggWStat() == teams.get(i+1).getAggWStat()) {
                teams.get(i+1).setWPoints(teams.get(i).getWPoints());
            }
        }
    }
    
    public void calcPointsForBA() {
        int maxPointsForBA = listOfTeams.size();
        
        ArrayList<Team> teams = new ArrayList<Team>();
        for(Team t: listOfTeams) {
            teams.add(t);
        }
        Collections.sort(teams, new TeamBAStatComparator());
        
        for(int i = 0; i < teams.size(); i++) {
            teams.get(i).setBAPoints(maxPointsForBA);
            maxPointsForBA--;
        }
        
        for(int i = 0; i < teams.size() - 1; i++) {
            if(teams.get(i).getAggBAStat() == teams.get(i+1).getAggBAStat()) {
                teams.get(i+1).setBAPoints(teams.get(i).getBAPoints());
            }
        }
    }
    
    public void calcPointsForSB() {
        int maxPointsForSB = listOfTeams.size();
        
        ArrayList<Team> teams = new ArrayList<Team>();
        for(Team t: listOfTeams) {
            teams.add(t);
        }
        Collections.sort(teams, new TeamSBStatComparator());
        
        for(int i = 0; i < teams.size(); i++) {
            teams.get(i).setSBPoints(maxPointsForSB);
            maxPointsForSB--;
        }
        
        for(int i = 0; i < teams.size() - 1; i++) {
            if(teams.get(i).getAggSBStat() == teams.get(i+1).getAggSBStat()) {
                teams.get(i+1).setSBPoints(teams.get(i).getSBPoints());
            }
        }
    }
    
    public void calcPointsForRBI() {
        int maxPointsForRBI = listOfTeams.size();
        
        ArrayList<Team> teams = new ArrayList<Team>();
        for(Team t: listOfTeams) {
            teams.add(t);
        }
        Collections.sort(teams, new TeamRBIStatComparator());
        
        for(int i = 0; i < teams.size(); i++) {
            teams.get(i).setRBIPoints(maxPointsForRBI);
            maxPointsForRBI--;
        }
        
        for(int i = 0; i < teams.size() - 1; i++) {
            if(teams.get(i).getAggRBIStat() == teams.get(i+1).getAggRBIStat()) {
                teams.get(i+1).setRBIPoints(teams.get(i).getRBIPoints());
            }
        }
    }
    
    public void calcPointsForHR() {
        int maxPointsForHR = listOfTeams.size();
        
        ArrayList<Team> teams = new ArrayList<Team>();
        for(Team t: listOfTeams) {
            teams.add(t);
        }
        Collections.sort(teams, new TeamHRStatComparator());
        
        for(int i = 0; i < teams.size(); i++) {
            teams.get(i).setHRPoints(maxPointsForHR);
            maxPointsForHR--;
        }
        
        for(int i = 0; i < teams.size() - 1; i++) {
            if(teams.get(i).getAggHRStat() == teams.get(i+1).getAggHRStat()) {
                teams.get(i+1).setHRPoints(teams.get(i).getHRPoints());
            }
        }
    }
    
    public void calcPointsForR() {
        int maxPointsForR = listOfTeams.size();
        
        ArrayList<Team> teams = new ArrayList<Team>();
        for(Team t: listOfTeams) {
            teams.add(t);
        }
        Collections.sort(teams, new TeamRStatComparator());
        
        for(int i = 0; i < teams.size(); i++) {
            teams.get(i).setRPoints(maxPointsForR);
            maxPointsForR--;
        }
        
        for(int i = 0; i < teams.size() - 1; i++) {
            if(teams.get(i).getAggRStat() == teams.get(i+1).getAggRStat()) {
                teams.get(i+1).setRPoints(teams.get(i).getRPoints());
            }
        }
    }
    
    public void calcTotalPoints() {
        
        for(int i = 0; i < listOfTeams.size(); i++) {
            listOfTeams.get(i).setTotalPoints(listOfTeams.get(i).getWHIPPoints() +
                    listOfTeams.get(i).getERAPoints() + listOfTeams.get(i).getKPoints() +
                    listOfTeams.get(i).getSVPoints() + listOfTeams.get(i).getWPoints() +
                    listOfTeams.get(i).getBAPoints() + listOfTeams.get(i).getSBPoints() +
                    listOfTeams.get(i).getRBIPoints() + listOfTeams.get(i).getHRPoints() + 
                    listOfTeams.get(i).getRPoints());
        }
    }
    
}
