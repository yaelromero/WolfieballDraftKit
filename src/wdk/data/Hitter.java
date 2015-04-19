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
    final IntegerProperty ABStat;
    
    public static final String DEFAULT_POSITION = "<ENTER POSITION>";
    public static final int DEFAULT_GAMES_PLAYED = 0;
    public static final int DEFAULT_AB_STAT = 0;
    
    public Hitter() {
        position = new SimpleStringProperty(DEFAULT_POSITION);
        gamesPlayed = new SimpleIntegerProperty(DEFAULT_GAMES_PLAYED);
        ABStat = new SimpleIntegerProperty(DEFAULT_AB_STAT);
    }
    
    @Override
    public void reset() {
        setPosition(DEFAULT_POSITION);
        setGamesPlayed(DEFAULT_GAMES_PLAYED);
        setABStat(DEFAULT_AB_STAT);
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
    
    public void setABStat(int initABStat) {
        ABStat.set(initABStat);
    }
    
    public int getABStat() {
        return ABStat.get();
    }
    
    public IntegerProperty ABStatProperty() {
        return ABStat;
    }
}
