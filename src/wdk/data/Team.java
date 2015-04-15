package wdk.data;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Yael
 */

public class Team {
    final StringProperty teamName;
    final StringProperty teamOwner;
    final DoubleProperty moneyLeft;
    private List<Player> taxiSquad;
    private List<Player> startingLineup;
    
    public static final String DEFAULT_TEAM_NAME = "<ENTER TEAM NAME>";
    public static final String DEFAULT_TEAM_OWNER = "<ENTER TEAM_OWNER>";
    public static final double DEFAULT_MONEY_LEFT = 260.0;
    
    public Team() {
        teamName = new SimpleStringProperty(DEFAULT_TEAM_NAME);
        teamOwner = new SimpleStringProperty(DEFAULT_TEAM_OWNER);
        moneyLeft = new SimpleDoubleProperty(DEFAULT_MONEY_LEFT);
        taxiSquad = new ArrayList<>();
        startingLineup = new ArrayList<>();
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
    
    public void setTaxiSquad(List<Player> taxiSquad) {
        this.taxiSquad = taxiSquad;
    }
    
    public List<Player> getTaxiSquad() {
        return taxiSquad;
    }
    
    public void setStartingLineup(List<Player> startingLineup) {
        this.startingLineup = startingLineup;
    }
    
    public List<Player> getStartingLineup() {
        return startingLineup;
    }
}
