package wdk.data;

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
public class Pitcher extends Player {
    final IntegerProperty ERStat;
    final IntegerProperty HStat;
    final IntegerProperty BBStat;
    final DoubleProperty IPStat;
    
    public static final int DEFAULT_ER_STAT = 0;
    public static final int DEFAULT_H_STAT = 0;
    public static final int DEFAULT_BB_STAT = 0;
    public static final double DEFAULT_IP_STAT = 0.0;
    
    
    public Pitcher() {
        ERStat = new SimpleIntegerProperty(DEFAULT_ER_STAT);
        HStat = new SimpleIntegerProperty(DEFAULT_H_STAT);
        BBStat = new SimpleIntegerProperty(DEFAULT_BB_STAT);
        IPStat = new SimpleDoubleProperty(DEFAULT_IP_STAT);
    }
    
    @Override
    public void reset() {
        setERStat(DEFAULT_ER_STAT);
        setHStat(DEFAULT_H_STAT);
        setBBStat(DEFAULT_BB_STAT);
        setIPStat(DEFAULT_IP_STAT);
    }
    
    public void setERStat(int initERStat) {
        ERStat.set(initERStat);
    }
    
    public int getERStat() {
        return ERStat.get();
    }
    
    public IntegerProperty ERStatProperty() {
        return ERStat;
    }
    
    public void setHStat(int initHStat) {
        HStat.set(initHStat);
    }
    
    public int getHStat() {
        return HStat.get();
    }
    
    public IntegerProperty HStatProperty() {
        return HStat;
    }
    
    public void setBBStat(int initBBStat) {
        BBStat.set(initBBStat);
    }
    
    public int getBBStat() {
        return BBStat.get();
    }
    
    public IntegerProperty BBStatProperty() {
        return BBStat;
    }
    
    public void setIPStat(double initIPStat) {
        IPStat.set(initIPStat);
    }
    
    public double getIPStat() {
        return IPStat.get();
    }
    
    public DoubleProperty IPProperty() {
        return IPStat;
    }
}
