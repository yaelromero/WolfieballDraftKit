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
    final StringProperty role;
    final IntegerProperty WStat;
    final IntegerProperty KStat;
    final IntegerProperty SVStat;
    final IntegerProperty ERStat;
    final IntegerProperty HStat;
    final IntegerProperty BBStat;
    final DoubleProperty ERAStat;
    final DoubleProperty WHIPStat;
    final DoubleProperty IPStat;
    
    public static final String DEFAULT_ROLE = "P";
    public static final int DEFAULT_K_STAT = 0;
    public static final int DEFAULT_SV_STAT = 0;
    public static final int DEFAULT_W_STAT = 0;
    public static final int DEFAULT_ER_STAT = 0;
    public static final int DEFAULT_H_STAT = 0;
    public static final int DEFAULT_BB_STAT = 0;
    public static final double DEFAULT_WHIP_STAT = 0.0;
    public static final double DEFAULT_ERA_STAT = 0.0;
    public static final double DEFAULT_IP_STAT = 0.0;
    
    
    public Pitcher() {
        role = new SimpleStringProperty(DEFAULT_ROLE);
        WStat = new SimpleIntegerProperty(DEFAULT_W_STAT);
        KStat = new SimpleIntegerProperty(DEFAULT_K_STAT);
        SVStat = new SimpleIntegerProperty(DEFAULT_SV_STAT);
        ERStat = new SimpleIntegerProperty(DEFAULT_ER_STAT);
        HStat = new SimpleIntegerProperty(DEFAULT_H_STAT);
        BBStat = new SimpleIntegerProperty(DEFAULT_BB_STAT);
        ERAStat = new SimpleDoubleProperty(DEFAULT_ERA_STAT);
        WHIPStat = new SimpleDoubleProperty(DEFAULT_WHIP_STAT);
        IPStat = new SimpleDoubleProperty(DEFAULT_IP_STAT);
    }
    
    @Override
    public void reset() {
        setRole(DEFAULT_ROLE);
        setWStat(DEFAULT_W_STAT);
        setKStat(DEFAULT_K_STAT);
        setSVStat(DEFAULT_SV_STAT);
        setERStat(DEFAULT_ER_STAT);
        setHStat(DEFAULT_H_STAT);
        setBBStat(DEFAULT_BB_STAT);
        setERAStat(DEFAULT_ERA_STAT);
        setWHIPStat(DEFAULT_WHIP_STAT);
        setIPStat(DEFAULT_IP_STAT);
    }
    
    public void setRole(String initRole) {
        role.set(initRole);
    }
    
    public String getRole() {
        return role.get();
    }
    
    public StringProperty roleProperty() {
        return role;
    }
    
    public void setWStat(int initWStat) {
        WStat.set(initWStat);
    }
    
    public int getWStat() {
        return WStat.get();
    }
    
    public IntegerProperty WStatProperty() {
        return WStat;
    }
    
    public void setKStat(int initKStat) {
        KStat.set(initKStat);
    }
    
    public int getKStat() {
        return KStat.get();
    }
    
    public IntegerProperty KStatProperty() {
        return KStat;
    }
    
    public void setSVStat(int initSVStat) {
        SVStat.set(initSVStat);
    }
    
    public int getSVStat() {
        return SVStat.get();
    }
    
    public IntegerProperty SVProperty() {
        return SVStat;
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
    
    public void setERAStat(double initERAStat) {
        ERAStat.set(initERAStat);
    }
    
    public double getERAStat() {
        return ERAStat.get();
    }
    
    public DoubleProperty ERAProperty() {
        return ERAStat;
    }
    
    public void setWHIPStat(double initWHIPStat) {
        WHIPStat.set(initWHIPStat);
    }
    
    public double getWHIPStat() {
        return WHIPStat.get();
    }
    
    public DoubleProperty WHIPProperty() {
        return WHIPStat;
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
