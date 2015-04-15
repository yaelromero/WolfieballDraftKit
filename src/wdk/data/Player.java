package wdk.data;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
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
    
    public static final String DEFAULT_FIRST_NAME = "<ENTER FIRST NAME>";
    public static final String DEFAULT_LAST_NAME = "<ENTER LAST NAME>";
    public static final Contract DEFAULT_CONTRACT = S1;
    public static final String DEFAULT_MLB_TEAM = "<ENTER MLB_TEAM>";
    public static final String DEFAULT_NOB = "<ENTER NOB>";
    public static final int DEFAULT_YOB = 0;
    public static final String DEFAULT_NOTES = "<ENTER NOTES>";
    public static final int DEFAULT_SALARY = 0;
    public static final int DEFAULT_H_STAT = 0;
    public static final int DEFAULT_AGE = 0;
    public static final int DEFAULT_EST_VAL = 0;
    
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
}
