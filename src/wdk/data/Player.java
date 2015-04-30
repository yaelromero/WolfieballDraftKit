package wdk.data;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import static wdk.data.Contract.S1;

/**
 * A simple data class for storing information about a player.
 *
 * @author Yael
 */

public class Player {
    final StringProperty firstName;
    final StringProperty lastName;
    final ObjectProperty contract;
    final StringProperty MLBTeam;
    final IntegerProperty salary;
    final IntegerProperty age;
    final StringProperty NOB;
    final IntegerProperty YOB;
    final IntegerProperty estVal;
    final IntegerProperty HStat;
    final StringProperty notes;
    final IntegerProperty ROrWStat;
    final IntegerProperty HROrSVStat;
    final IntegerProperty RBIOrKStat;
    final DoubleProperty SBOrERAStat;
    final DoubleProperty BAOrWHIPStat;
    final StringProperty QPOrRole;
    
    public static final String DEFAULT_FIRST_NAME = "<ENTER FIRST NAME>";
    public static final String DEFAULT_LAST_NAME = "<ENTER LAST NAME>";
    public static final Contract DEFAULT_CONTRACT = S1;
    public static final String DEFAULT_MLB_TEAM = "<ENTER MLB TEAM>";
    public static final String DEFAULT_NOB = "<ENTER NOB>";
    public static final int DEFAULT_YOB = 0;
    public static final String DEFAULT_NOTES = "<ENTER NOTES>";
    public static final int DEFAULT_SALARY = 0;
    public static final int DEFAULT_H_STAT = 0;
    public static final int DEFAULT_AGE = 0;
    public static final int DEFAULT_EST_VAL = 0;
    public static final int DEFAULT_RORW_STAT = 0;
    public static final int DEFAULT_HRORSV_STAT = 0;
    public static final int DEFAULT_RBIORK_STAT = 0;
    public static final Double DEFAULT_SBORERA_STAT = 0.0;
    public static final Double DEFAULT_BAORWHIP_STAT = 0.0;
    public static final String DEFAULT_QPORROLE = "";
    
    public Player() {
        firstName = new SimpleStringProperty(DEFAULT_FIRST_NAME);
        lastName = new SimpleStringProperty(DEFAULT_LAST_NAME);
        contract = new SimpleObjectProperty(DEFAULT_CONTRACT);
        MLBTeam = new SimpleStringProperty(DEFAULT_MLB_TEAM);
        NOB = new SimpleStringProperty(DEFAULT_NOB);
        YOB = new SimpleIntegerProperty(DEFAULT_YOB);
        notes = new SimpleStringProperty(DEFAULT_NOTES);
        salary = new SimpleIntegerProperty(DEFAULT_SALARY);
        age = new SimpleIntegerProperty(DEFAULT_AGE);
        estVal = new SimpleIntegerProperty(DEFAULT_EST_VAL);
        HStat = new SimpleIntegerProperty(DEFAULT_H_STAT);
        ROrWStat = new SimpleIntegerProperty(DEFAULT_RORW_STAT);
        HROrSVStat = new SimpleIntegerProperty(DEFAULT_HRORSV_STAT);
        RBIOrKStat = new SimpleIntegerProperty(DEFAULT_RBIORK_STAT);
        SBOrERAStat = new SimpleDoubleProperty(DEFAULT_SBORERA_STAT);
        BAOrWHIPStat = new SimpleDoubleProperty(DEFAULT_BAORWHIP_STAT);
        QPOrRole = new SimpleStringProperty(DEFAULT_QPORROLE);
    }
    
    public void reset() {
        setFirstName(DEFAULT_FIRST_NAME);
        setLastName(DEFAULT_LAST_NAME);
        setContract(DEFAULT_CONTRACT);
        setMLBTeam(DEFAULT_MLB_TEAM);
        setNOB(DEFAULT_NOB);
        setNotes(DEFAULT_NOTES);
        setSalary(DEFAULT_SALARY);
        setAge(DEFAULT_AGE);
        setHStat(DEFAULT_H_STAT);
        setEstVal(DEFAULT_EST_VAL);
        setROrWStat(DEFAULT_RORW_STAT);
        setHROrSVStat(DEFAULT_HRORSV_STAT);
        setRBIOrKStat(DEFAULT_RBIORK_STAT);
        setSBOrERAStat(DEFAULT_SBORERA_STAT);
        setBAOrWHIPStat(DEFAULT_BAORWHIP_STAT);
        setQPOrRole(DEFAULT_QPORROLE);
        
    }
    
    public void setQPOrRole(String initQPOrRole) {
        QPOrRole.set(initQPOrRole);
    }
    
    public String getQPOrRole() {
        return QPOrRole.get();
    }
    
    public StringProperty QPOrRoleProperty() {
        return QPOrRole;
    }
    
    public void setFirstName(String initFirstName) {
        firstName.set(initFirstName);
    }
    
    public String getFirstName() {
        return firstName.get();
    }
    
    public StringProperty firstNameProperty() {
        return firstName;
    }
    
    public void setLastName(String initLastName) {
        lastName.set(initLastName);
    }
    
    public String getLastName() {
        return lastName.get();
    }
    
    public StringProperty lastNameProperty() {
        return lastName;
    }
    
    public void setContract(Contract initContract) {
        contract.set(initContract);
    }
    
    public Contract getContract() {
        return (Contract) contract.get();
    }
    
    public ObjectProperty contractProperty() {
        return contract;
    }
    
    public void setMLBTeam(String initMLBTeam) {
        MLBTeam.set(initMLBTeam);
    }
    
    public String getMLBTeam() {
        return MLBTeam.get();
    }
    
    public StringProperty MLBTeamProperty() {
        return MLBTeam;
    }
    
    public void setNOB(String initNOB) {
        NOB.set(initNOB);
    }
    
    public String getNOB() {
        return NOB.get();
    }
    
    public StringProperty NOBProperty() {
        return NOB;
    }
    
    public void setYOB(int initYOB) {
        YOB.set(initYOB);
    }
    
    public int getYOB() {
        return YOB.get();
    }
    
    public IntegerProperty YOBProperty() {
        return YOB;
    }
    
    public void setNotes(String initNotes) {
        notes.set(initNotes);
    }
    
    public String getNotes() {
        return notes.get();
    }
    
    public StringProperty notesProperty() {
        return notes;
    }
    
    public void setAge(int initAge) {
        age.set(initAge);
    }
    
    public int getAge() {
        return age.get();
    }
    
    public IntegerProperty ageProperty() {
        return age;
    }
    
    public void setSalary(int initSalary) {
        salary.set(initSalary);
    }
    
    public int getSalary() {
        return salary.get();
    }
    
    public IntegerProperty salaryProperty() {
        return salary;
    }
    
    public void setEstVal(int initEstVal) {
        estVal.set(initEstVal);
    }
    
    public int getEstVal() {
        return estVal.get();
    }
    
    public IntegerProperty estValProperty() {
        return estVal;
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
    
    public void setROrWStat(int initROrWStat) {
        ROrWStat.set(initROrWStat);
    }
    
    public int getROrWStat() {
        return ROrWStat.get();
    }
    
    public IntegerProperty ROrWStatProperty() {
        return ROrWStat;
    }
  
    public void setHROrSVStat(int initHROrSVStat) {
        HROrSVStat.set(initHROrSVStat);
    }
    
    public int getHROrSVStat() {
        return HROrSVStat.get();
    }
    
    public IntegerProperty HROrSVStatProperty() {
        return HROrSVStat;
    }
 
    public void setRBIOrKStat(int initRBIOrKStat) {
        RBIOrKStat.set(initRBIOrKStat);
    }
    
    public int getRBIOrKStat() {
        return RBIOrKStat.get();
    }
    
    public IntegerProperty RBIOrKStatProperty() {
        return RBIOrKStat;
    }
    
    public void setSBOrERAStat(double initSBOrERAStat) {
        SBOrERAStat.set(initSBOrERAStat);
    }
    
    public double getSBOrERAStat() {
        return SBOrERAStat.get();
    }
    
    public DoubleProperty SBOrERAStatProperty() {
        return SBOrERAStat;
    }
    
    public void setBAOrWHIPStat(double initBAOrWHIPStat) {
        BAOrWHIPStat.set(initBAOrWHIPStat);
    }
    
    public double getBAOrWHIPStat() {
        return BAOrWHIPStat.get();
    }
    
    public DoubleProperty BAOrWHIPStatProperty() {
        return BAOrWHIPStat;
    }
    
}
