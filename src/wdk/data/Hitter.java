package wdk.data;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Yael
 */

public class Hitter extends Player {
    final StringProperty position;
    final IntegerProperty gamesPlayed;
    final IntegerProperty RStat;
    final IntegerProperty HRStat;
    final IntegerProperty RBIStat;
    final IntegerProperty ABStat;
    final IntegerProperty SBStat;
    final DoubleProperty BAStat;
    final StringProperty QP;
    
    public static final String DEFAULT_POSITION = "<ENTER POSITION>";
    public static final String DEFAULT_QP = "<ENTER QP>";
    public static final int DEFAULT_GAMES_PLAYED = 0;
    public static final int DEFAULT_R_STAT = 0;
    public static final int DEFAULT_HR_STAT = 0;
    public static final int DEFAULT_RBI_STAT = 0;
    public static final int DEFAULT_AB_STAT = 0;
    public static final int DEFAULT_SB_STAT = 0;
    public static final double DEFAULT_BA_STAT = 0.0;
    
    public Hitter() {
        position = new SimpleStringProperty(DEFAULT_POSITION);
        QP = new SimpleStringProperty(DEFAULT_QP);
        gamesPlayed = new SimpleIntegerProperty(DEFAULT_GAMES_PLAYED);
        RStat = new SimpleIntegerProperty(DEFAULT_R_STAT);
        HRStat = new SimpleIntegerProperty(DEFAULT_HR_STAT);
        RBIStat = new SimpleIntegerProperty(DEFAULT_RBI_STAT);
        ABStat = new SimpleIntegerProperty(DEFAULT_AB_STAT);
        SBStat = new SimpleIntegerProperty(DEFAULT_SB_STAT);
        BAStat = new SimpleDoubleProperty(DEFAULT_BA_STAT);
    }
    
    @Override
    public void reset() {
        setPosition(DEFAULT_POSITION);
        setGamesPlayed(DEFAULT_GAMES_PLAYED);
        setRStat(DEFAULT_R_STAT);
        setHRStat(DEFAULT_HR_STAT);
        setRBIStat(DEFAULT_HR_STAT);
        setABStat(DEFAULT_AB_STAT);
        setSBStat(DEFAULT_SB_STAT);
        setBAStat(DEFAULT_BA_STAT);
        setQP(DEFAULT_QP);
    }
    
    public void setPosition(String initPosition) {
        position.set(initPosition);
    }
    
    public String getPosition() {
        return position.get();
    }
    
    public StringProperty positionProperty() {
        return position;
    }
    
    public void setGamesPlayed(int initGamesPlayed) {
        gamesPlayed.set(initGamesPlayed);
    }
    
    public int getGamesPlayed() {
        return gamesPlayed.get();
    }
    
    public IntegerProperty gamesPlayedProperty() {
        return gamesPlayed;
    }
    
    public void setRStat(int initRStat) {
        RStat.set(initRStat);
    }
    
    public int getRStat() {
        return RStat.get();
    }
    
    public IntegerProperty RStatProperty() {
        return RStat;
    }
    
    public void setHRStat(int initHRStat) {
        HRStat.set(initHRStat);
    }
    
    public int getHRStat() {
        return HRStat.get();
    }
    
    public IntegerProperty HRStatProperty() {
        return HRStat;
    }
    
    public void setRBIStat(int initRBIStat) {
        RBIStat.set(initRBIStat);
    }
    
    public int getRBIStat() {
        return RBIStat.get();
    }
    
    public IntegerProperty RBIStatProperty() {
        return RBIStat;
    }
    
    public void setABStat(int initABStat) {
        ABStat.set(initABStat);
    }
    
    public int getABStat() {
        return ABStat.get();
    }
    
    public IntegerProperty ABStatProperty() {
        return ABStat;
    }
    
    public void setSBStat(int initSBStat) {
        SBStat.set(initSBStat);
    }
    
    public int getSBStat() {
        return SBStat.get();
    }
    
    public IntegerProperty SBStatProperty() {
        return SBStat;
    }
    
    public void setBAStat(double initBAStat) {
        BAStat.set(initBAStat);
    }
    
    public double getBAStat() {
        return BAStat.get();
    }
    
    public DoubleProperty BAStatProperty() {
        return BAStat;
    }
    
    public void setQP(String initQP) {
        QP.set(initQP);
    }
    
    public String getQP() {
        return QP.get();
    }
    
    public StringProperty QPProperty() {
        return QP;
    }
}
