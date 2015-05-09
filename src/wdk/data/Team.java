package wdk.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import wdk.gui.PlayerPositionComparator;

/**
 *
 * @author Yael
 */

public class Team {
    final StringProperty teamName;
    final StringProperty teamOwner;
    final DoubleProperty moneyLeft;
    private ObservableList<Player> taxiSquad;
    private ObservableList<Player> startingLineup;
    
    public static final String DEFAULT_TEAM_NAME = "<ENTER TEAM NAME>";
    public static final String DEFAULT_TEAM_OWNER = "<ENTER TEAM_OWNER>";
    public static final double DEFAULT_MONEY_LEFT = 260.0;
    
    public Team() {
        teamName = new SimpleStringProperty(DEFAULT_TEAM_NAME);
        teamOwner = new SimpleStringProperty(DEFAULT_TEAM_OWNER);
        moneyLeft = new SimpleDoubleProperty(DEFAULT_MONEY_LEFT);
        taxiSquad = FXCollections.observableArrayList();
        startingLineup = FXCollections.observableArrayList();
    }
    
    public void reset() {
        ////////
    }
    
    public void setTeamName(String initTeamName) {
        teamName.set(initTeamName);
    }
    
    public String getTeamName() {
        return teamName.get();
    }
    
    public StringProperty teamNameProperty() {
        return teamName;
    }
    
    public void setTeamOwner(String initTeamOwner) {
        teamOwner.set(initTeamOwner);
    }
    
    public String getTeamOwner() {
        return teamOwner.get();
    }
    
    public StringProperty teamOwnerProperty() {
        return teamOwner;
    }
    
    public void setMoneyLeft(double initMoneyLeft) {
        moneyLeft.set(initMoneyLeft);
    }
    
    public double getMoneyLeft() {
        return moneyLeft.get();
    }
    
    public DoubleProperty moneyLeftProperty() {
        return moneyLeft;
    }
    
    public void setTaxiSquad(ObservableList<Player> taxiSquad) {
        this.taxiSquad = taxiSquad;
    }
    
    public ObservableList<Player> getTaxiSquad() {
        return taxiSquad;
    }
    
    public void setStartingLineup(ObservableList<Player> startingLineup) {
        this.startingLineup = startingLineup;
    }
    
    public ObservableList<Player> getStartingLineup() {
        return startingLineup;
    }
    
    public void addPlayerToStartingLineup(Player p) {
        getStartingLineup().add(p);
        Collections.sort(getStartingLineup(), new PlayerPositionComparator());
    }
    
    public void removePlayerFromStartingLineup(Player p) {
        for(int i = 0; i < getStartingLineup().size(); i++) {
            if(getStartingLineup().get(i).getFirstName().equalsIgnoreCase(p.getFirstName()) &&
                    getStartingLineup().get(i).getLastName().equalsIgnoreCase(p.getLastName())) {
                getStartingLineup().remove(i);
            }
        }
        getStartingLineup().remove(p);
    }
    
    public boolean checkIfPlayerInSL(Player p) {
        boolean exists = false;
        for(int i = 0; i < getStartingLineup().size(); i++) {
            if(getStartingLineup().get(i).getFirstName().equalsIgnoreCase(p.getFirstName()) &&
                    getStartingLineup().get(i).getLastName().equalsIgnoreCase(p.getLastName())) {
                exists = true;
                break;
            }
            else
                exists = false;
        }
        return exists;
    }
    
    public int getCCount() {
        int count = 0;
        for(int i = 0; i < getStartingLineup().size(); i++) {
            if(getStartingLineup().get(i).getChosenPosition().contains("C")) {
                count++;
            }      
        }
        return count;
    }
    
    public int get1BCount() {
        int count = 0;
        for(int i = 0; i < getStartingLineup().size(); i++) {
            if(getStartingLineup().get(i).getChosenPosition().contains("1B")) {
                count++;
            }      
        }
        return count;
    }
    
    public int getCICount() {
        int count = 0;
        for(int i = 0; i < getStartingLineup().size(); i++) {
            if(getStartingLineup().get(i).getChosenPosition().contains("CI")) {
                count++;
            }      
        }
        return count;
    }
    
    public int get3BCount() {
        int count = 0;
        for(int i = 0; i < getStartingLineup().size(); i++) {
            if(getStartingLineup().get(i).getChosenPosition().contains("3B")) {
                count++;
            }      
        }
        return count;
    }
    
    public int get2BCount() {
        int count = 0;
        for(int i = 0; i < getStartingLineup().size(); i++) {
            if(getStartingLineup().get(i).getChosenPosition().contains("2B")) {
                count++;
            }      
        }
        return count;
    }
    
    public int getMICount() {
        int count = 0;
        for(int i = 0; i < getStartingLineup().size(); i++) {
            if(getStartingLineup().get(i).getChosenPosition().contains("MI")) {
                count++;
            }      
        }
        return count;
    }
    
    public int getSSCount() {
        int count = 0;
        for(int i = 0; i < getStartingLineup().size(); i++) {
            if(getStartingLineup().get(i).getChosenPosition().contains("SS")) {
                count++;
            }      
        }
        return count;
    }
    
    public int getUCount() {
        int count = 0;
        for(int i = 0; i < getStartingLineup().size(); i++) {
            if(getStartingLineup().get(i).getChosenPosition().contains("U")) {
                count++;
            }      
        }
        return count;
    }
    
    public int getOFCount() {
        int count = 0;
        for(int i = 0; i < getStartingLineup().size(); i++) {
            if(getStartingLineup().get(i).getChosenPosition().contains("OF")) {
                count++;
            }      
        }
        return count;
    }
    
    public int getPCount() {
        int count = 0;
        for(int i = 0; i < getStartingLineup().size(); i++) {
            if(getStartingLineup().get(i).getChosenPosition().contains("P")) {
                count++;
            }      
        }
        return count;
    }
    
}
