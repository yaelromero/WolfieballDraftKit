package wdk.data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import wdk.comparators.PlayerPositionComparator;

/**
 *
 * @author Yael
 */

public class Team {
    final StringProperty teamName;
    final StringProperty teamOwner;
    final IntegerProperty playersNeededForSL;
    final IntegerProperty playersNeededForTS;
    final IntegerProperty moneyLeft;
    final IntegerProperty moneyPerPlayer;
    final IntegerProperty aggRStat;
    final IntegerProperty aggHRStat;
    final IntegerProperty aggRBIStat;
    final IntegerProperty aggSBStat;
    final DoubleProperty aggBAStat;
    final IntegerProperty aggWStat;
    final IntegerProperty aggSVStat;
    final IntegerProperty aggKStat;
    final DoubleProperty aggERAStat;
    final DoubleProperty aggWHIPStat;
    final IntegerProperty WHIPPoints;
    final IntegerProperty ERAPoints;
    final IntegerProperty KPoints;
    final IntegerProperty SVPoints;
    final IntegerProperty WPoints;
    final IntegerProperty BAPoints;
    final IntegerProperty SBPoints;
    final IntegerProperty RBIPoints;
    final IntegerProperty HRPoints;
    final IntegerProperty RPoints;
    final IntegerProperty totalPoints;
    private ObservableList<Player> taxiSquad;
    private ObservableList<Player> startingLineup;
    
    public static final String DEFAULT_TEAM_NAME = "<ENTER TEAM NAME>";
    public static final String DEFAULT_TEAM_OWNER = "<ENTER TEAM_OWNER>";
    public static final int DEFAULT_PLAYERS_NEEDED = 23;
    public static final int DEFAULT_PLAYERS_TS_NEEDED = 8;
    public static final int DEFAULT_MONEY_LEFT = 260;
    public static final int DEFAULT_MON_PER_PLAYER = 260/23;
    public static final int DEFAULT_R_STAT = 0;
    public static final int DEFAULT_HR_STAT = 0;
    public static final int DEFAULT_RBI_STAT = 0;
    public static final int DEFAULT_SB_STAT = 0;     
    public static final int DEFAULT_SV_STAT = 0;
    public static final int DEFAULT_K_STAT = 0;  
    public static final int DEFAULT_WHIP_POINTS = 0;
    public static final int DEFAULT_ERA_POINTS = 0;
    public static final int DEFAULT_K_POINTS = 0;
    public static final int DEFAULT_SV_POINTS = 0;
    public static final int DEFAULT_W_POINTS = 0;
    public static final int DEFAULT_BA_POINTS = 0;
    public static final int DEFAULT_SB_POINTS = 0;
    public static final int DEFAULT_RBI_POINTS = 0;
    public static final int DEFAULT_HR_POINTS = 0;
    public static final int DEFAULT_R_POINTS = 0;
    public static final int DEFAULT_TOTAL_POINTS = 0;      
    public static final double DEFAULT_BA_STAT = 0.0;
    public static final int DEFAULT_W_STAT = 0;
    public static final double DEFAULT_ERA_STAT = 0.0;     
    public static final double DEFAULT_WHIP_STAT = 0.0;     
            
    public Team() {
        teamName = new SimpleStringProperty(DEFAULT_TEAM_NAME);
        teamOwner = new SimpleStringProperty(DEFAULT_TEAM_OWNER);
        playersNeededForSL = new SimpleIntegerProperty(DEFAULT_PLAYERS_NEEDED);
        playersNeededForTS = new SimpleIntegerProperty(DEFAULT_PLAYERS_TS_NEEDED);
        moneyLeft = new SimpleIntegerProperty(DEFAULT_MONEY_LEFT);
        moneyPerPlayer = new SimpleIntegerProperty(DEFAULT_MON_PER_PLAYER);
        aggRStat = new SimpleIntegerProperty(DEFAULT_R_STAT);
        aggHRStat = new SimpleIntegerProperty(DEFAULT_HR_STAT);
        aggRBIStat = new SimpleIntegerProperty(DEFAULT_RBI_STAT);
        aggSBStat = new SimpleIntegerProperty(DEFAULT_SB_STAT);
        aggBAStat = new SimpleDoubleProperty(DEFAULT_BA_STAT);
        aggWStat = new SimpleIntegerProperty(DEFAULT_W_STAT);
        aggSVStat = new SimpleIntegerProperty(DEFAULT_SV_STAT);
        aggKStat = new SimpleIntegerProperty(DEFAULT_K_STAT);
        aggERAStat = new SimpleDoubleProperty(DEFAULT_ERA_STAT);
        aggWHIPStat = new SimpleDoubleProperty(DEFAULT_WHIP_STAT);
        WHIPPoints = new SimpleIntegerProperty(DEFAULT_WHIP_POINTS);
        ERAPoints = new SimpleIntegerProperty(DEFAULT_ERA_POINTS);
        KPoints = new SimpleIntegerProperty(DEFAULT_K_POINTS);
        SVPoints = new SimpleIntegerProperty(DEFAULT_SV_POINTS);
        WPoints = new SimpleIntegerProperty(DEFAULT_W_POINTS);
        BAPoints = new SimpleIntegerProperty(DEFAULT_BA_POINTS);
        SBPoints = new SimpleIntegerProperty(DEFAULT_SB_POINTS);
        RBIPoints = new SimpleIntegerProperty(DEFAULT_RBI_POINTS);
        HRPoints = new SimpleIntegerProperty(DEFAULT_HR_POINTS);
        RPoints = new SimpleIntegerProperty(DEFAULT_R_POINTS);
        totalPoints = new SimpleIntegerProperty(DEFAULT_TOTAL_POINTS);
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
    
    public void setPlayersNeededForSL(int initPlayersNeededForSL) {
        playersNeededForSL.set(initPlayersNeededForSL);
    }
    
    public int getPlayersNeededForSL() {
        return playersNeededForSL.get();
    }
    
    public IntegerProperty playersNeededForSLProperty() {
        return playersNeededForSL;
    }
    
    public void setPlayersNeededForTS(int initPlayersNeededForTS) {
        playersNeededForTS.set(initPlayersNeededForTS);
    }
    
    public int getPlayersNeededForTS() {
        return playersNeededForTS.get();
    }
    
    public IntegerProperty playersNeededForTSProperty() {
        return playersNeededForTS;
    }
    
    public void setMoneyLeft(int initMoneyLeft) {
        moneyLeft.set(initMoneyLeft);
    }
    
    public int getMoneyLeft() {
        return moneyLeft.get();
    }
    
    public IntegerProperty moneyLeftProperty() {
        return moneyLeft;
    }
    
    public void setMoneyPerPlayer(int initMoneyPerPlayer) {
        moneyPerPlayer.set(initMoneyPerPlayer);
    }
    
    public int getMoneyPerPlayer() {
        return moneyPerPlayer.get();
    }
    
    public IntegerProperty moneyPerPlayerProperty() {
        return moneyPerPlayer;
    }
    
    public void setAggRStat(int initAggRStat) {
        aggRStat.set(initAggRStat);
    }
    
    public int getAggRStat() {
        return aggRStat.get();
    }
    
    public IntegerProperty aggRStatProperty() {
        return aggRStat;
    }
    
    public void setAggHRStat(int initAggHRStat) {
        aggHRStat.set(initAggHRStat);
    }
    
    public int getAggHRStat() {
        return aggHRStat.get();
    }
    
    public IntegerProperty aggHRStatProperty() {
        return aggHRStat;
    }
    
    public void setAggRBIStat(int initAggRBIStat) {
        aggRBIStat.set(initAggRBIStat);
    }
    
    public int getAggRBIStat() {
        return aggRBIStat.get();
    }
    
    public IntegerProperty aggRBIStatProperty() {
        return aggRBIStat;
    }
    
    public void setAggSBStat(int initAggSBStat) {
        aggSBStat.set(initAggSBStat);
    }
    
    public int getAggSBStat() {
        return aggSBStat.get();
    }
    
    public IntegerProperty aggSBStatProperty() {
        return aggSBStat;
    }
    
    public void setAggSVStat(int initAggSVStat) {
        aggSVStat.set(initAggSVStat);
    }
    
    public int getAggSVStat() {
        return aggSVStat.get();
    }
    
    public IntegerProperty aggSVStatProperty() {
        return aggSVStat;
    }
    
    public void setAggKStat(int initAggKStat) {
        aggKStat.set(initAggKStat);
    }
    
    public int getAggKStat() {
        return aggKStat.get();
    }
    
    public IntegerProperty aggKStatProperty() {
        return aggKStat;
    }
    
    public void setWHIPPoints(int initWHIPPoints) {
        WHIPPoints.set(initWHIPPoints);
    }
    
    public int getWHIPPoints() {
        return WHIPPoints.get();
    }
    
    public IntegerProperty WHIPPointsProperty() {
        return WHIPPoints;
    }
    
    public void setERAPoints(int initERAPoints) {
        ERAPoints.set(initERAPoints);
    }
    
    public int getERAPoints() {
        return ERAPoints.get();
    }
    
    public IntegerProperty ERAPointsProperty() {
        return ERAPoints;
    }
    
    public void setKPoints(int initKPoints) {
        KPoints.set(initKPoints);
    }
    
    public int getKPoints() {
        return KPoints.get();
    }
    
    public IntegerProperty KPointsProperty() {
        return KPoints;
    }
  
    public void setSVPoints(int initSVPoints) {
        SVPoints.set(initSVPoints);
    }
    
    public int getSVPoints() {
        return SVPoints.get();
    }
    
    public IntegerProperty SVPointsProperty() {
        return SVPoints;
    }
    
    public void setWPoints(int initWPoints) {
        WPoints.set(initWPoints);
    }
    
    public int getWPoints() {
        return WPoints.get();
    }
    
    public IntegerProperty WPointsProperty() {
        return WPoints;
    }
    
    public void setBAPoints(int initBAPoints) {
        BAPoints.set(initBAPoints);
    }
    
    public int getBAPoints() {
        return BAPoints.get();
    }
    
    public IntegerProperty BAPointsProperty() {
        return BAPoints;
    }
    
    public void setSBPoints(int initSBPoints) {
        SBPoints.set(initSBPoints);
    }
    
    public int getSBPoints() {
        return SBPoints.get();
    }
    
    public IntegerProperty SBPointsProperty() {
        return SBPoints;
    }
    
    public void setRBIPoints(int initRBIPoints) {
        RBIPoints.set(initRBIPoints);
    }
    
    public int getRBIPoints() {
        return RBIPoints.get();
    }
    
    public IntegerProperty RBIPointsProperty() {
        return RBIPoints;
    }
    
    public void setHRPoints(int initHRPoints) {
        HRPoints.set(initHRPoints);
    }
    
    public int getHRPoints() {
        return HRPoints.get();
    }
    
    public IntegerProperty HRPointsProperty() {
        return HRPoints;
    }
    
    public void setRPoints(int initRPoints) {
        RPoints.set(initRPoints);
    }
    
    public int getRPoints() {
        return RPoints.get();
    }
    
    public IntegerProperty RPointsProperty() {
        return RPoints;
    }
    
    public void setTotalPoints(int initTotalPoints) {
        totalPoints.set(initTotalPoints);
    }
    
    public int getTotalPoints() {
        return totalPoints.get();
    }
    
    public IntegerProperty totalPointsProperty() {
        return totalPoints;
    }
    
    public void setAggBAStat(double initAggBAStat) {
        aggBAStat.set(initAggBAStat);
    }
    
    public double getAggBAStat() {
        return aggBAStat.get();
    }
    
    public DoubleProperty aggBAStatProperty() {
        return aggBAStat;
    }
    
    public void setAggWStat(int initAggWStat) {
        aggWStat.set(initAggWStat);
    }
    
    public int getAggWStat() {
        return aggWStat.get();
    }
    
    public IntegerProperty aggWStatProperty() {
        return aggWStat;
    }
    
    public void setAggERAStat(double initAggERAStat) {
        aggERAStat.set(initAggERAStat);
    }
    
    public double getAggERAStat() {
        return aggERAStat.get();
    }
    
    public DoubleProperty aggERAStatProperty() {
        return aggERAStat;
    }
    
    public void setAggWHIPStat(double initAggWHIPStat) {
        aggWHIPStat.set(initAggWHIPStat);
    }
    
    public double getAggWHIPStat() {
        return aggWHIPStat.get();
    }
    
    public DoubleProperty aggWHIPStatProperty() {
        return aggWHIPStat;
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
    
    public void addPlayerToTaxiSquad(Player p) {
        getTaxiSquad().add(p);
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
    
    public int calcMoneyLeft() {
        int totalSalaries = 0;
        
        for(Player p: getStartingLineup()) {
            totalSalaries += p.getSalary();
        }
        
        return 260 - totalSalaries;
    }
    
    public int calcMoneyPerPlayer() {
        
        int monpp;
        if(getPlayersNeededForSL() > 0) {
            monpp = getMoneyLeft() / getPlayersNeededForSL();
        }
        else {
            monpp = -1;
        }
        return monpp;
    }
    
    public int calcAggRStat() {
        int aggr = 0;
        for(Player p: getStartingLineup()) {
            if(!p.getChosenPosition().equalsIgnoreCase("P")) {
                aggr+= p.getROrWStat();   
            }
        }    
        return aggr;
    }
    
    public int calcAggHRStat() {
        int agghr = 0;
        for(Player p: getStartingLineup()) {
            if(!p.getChosenPosition().equalsIgnoreCase("P")) {
                agghr+= p.getHROrSVStat();
            }
        }    
        return agghr;
    }
    
    public int calcAggRBIStat() {
        int aggrbi = 0;
        for(Player p: getStartingLineup()) {
            if(!p.getChosenPosition().equalsIgnoreCase("P")) {
                aggrbi+= p.getRBIOrKStat();
            }
        }    
        return aggrbi;
    }
    
    public int calcAggSBStat() {
        int aggsb = 0;
        for(Player p: getStartingLineup()) {
            if(!p.getChosenPosition().equalsIgnoreCase("P")) {
                aggsb+= p.getSBOrERAStat();
            }
        }    
        return aggsb;
    }
    
    public int calcAggSVStat() {
        int aggsv = 0;
        for(Player p: getStartingLineup()) {
            if(p.getChosenPosition().equalsIgnoreCase("P")) {
                aggsv+= p.getHROrSVStat();
            }
        }    
        return aggsv;
    }
    
    public int calcAggKStat() {
        int aggk = 0;
        for(Player p: getStartingLineup()) {
            if(p.getChosenPosition().equalsIgnoreCase("P")) {
                aggk += p.getRBIOrKStat();
            }
        }    
        return aggk;
    }
    
    public int calcAggWStat() {
        int aggw = 0;
        for(Player p: getStartingLineup()) {
            if(p.getChosenPosition().equalsIgnoreCase("P")) {
                aggw += p.getROrWStat();
            }
        }    
        return aggw;
    }
    
    public double calcAggBAStat() {
        int runningh = 0;
        int runningab = 0;
        double aggba;
        
        for(Player p: getStartingLineup()) {
            if(!p.getChosenPosition().equalsIgnoreCase("P")) {
                runningh += p.getHStat();
            }
        }
        
        for(Player p: getStartingLineup()) {
            if(!p.getChosenPosition().equalsIgnoreCase("P")) {
                runningab += ((Hitter)p).getABStat();
            }
        }
        
        if(runningab == 0) {
            aggba = 0;
        }
        else {
            aggba = ((double)(runningh) / runningab);
            BigDecimal db = new BigDecimal(aggba).setScale(3, RoundingMode.HALF_EVEN);
            aggba = db.doubleValue();
        }
        return aggba;
    }
    
    public double calcAggERAStat() {
        int runninger = 0;
        double runningip = 0;
        double aggera = 0;
        
        for(Player p: getStartingLineup()) {
            if(p.getChosenPosition().equalsIgnoreCase("P")) {
                runninger += ((Pitcher)p).getERStat();
            }
        }
        
        for(Player p: getStartingLineup()) {
            if(p.getChosenPosition().equalsIgnoreCase("P")) {
                runningip += ((Pitcher)p).getIPStat();
            }
        }
        
        if(runningip == 0) {
            aggera = 0;
        }
        else {
            aggera = ((double)runninger * 9)/(runningip);
            BigDecimal bd = new BigDecimal(aggera).setScale(2, RoundingMode.HALF_EVEN);
            aggera = bd.doubleValue();
        }
        return aggera;
    }
    
    public double calcAggWHIPStat() {
        int runningbb = 0;
        int runningh = 0;
        double runningip = 0;
        double aggwhip = 0;
        
        for(Player p: getStartingLineup()) {
            if(p.getChosenPosition().equalsIgnoreCase("P")) {
                runningbb += ((Pitcher)p).getBBStat();
            }
        }
        
        for(Player p: getStartingLineup()) {
            if(p.getChosenPosition().equalsIgnoreCase("P")) {
                runningip += ((Pitcher)p).getIPStat();
            }
        }
        
        for(Player p: getStartingLineup()) {
            if(p.getChosenPosition().equalsIgnoreCase("P")) {
                runningh += ((Pitcher)p).getHStat();
            }
        }
        
        if(runningip == 0) {
            aggwhip = 0;
        }
        else {
            aggwhip = ((double)(runningbb + runningh)/(runningip));
            BigDecimal db = new BigDecimal(aggwhip).setScale(2, RoundingMode.HALF_EVEN);
            aggwhip = db.doubleValue();
        }
        return aggwhip;
        
    }
    
    public ArrayList<String> getPositionsNeeded() {
        // Check which positions the team still needs
        
        ArrayList<String> positionsNeeded = new ArrayList<String>();
        
        if(this.getCCount() < 2) {
            positionsNeeded.add("C");    
        }
        if(this.get1BCount() < 1) {
            positionsNeeded.add("1B");
        }
        if(this.getCICount() < 1) {
            positionsNeeded.add("CI");
        }
        if(this.get3BCount() < 1) {
            positionsNeeded.add("3B");
        }
        if(this.get2BCount() < 1) {
            positionsNeeded.add("2B");
        }
        if(this.getMICount() < 1) {
            positionsNeeded.add("MI");
        }
        if(this.getSSCount() < 1) {
            positionsNeeded.add("SS");
        }
        if(this.getUCount() < 1) {
            positionsNeeded.add("U");
        }
        if(this.getOFCount() < 5) {
            positionsNeeded.add("OF");
        }
        if(this.getPCount() < 9) {
            positionsNeeded.add("P");
        }
        
        return positionsNeeded;
    }
}