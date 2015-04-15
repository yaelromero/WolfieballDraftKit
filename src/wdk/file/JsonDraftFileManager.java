package wdk.file;

import static wdk.WDK_StartupConstants.PATH_DRAFTS;
import wdk.data.Contract;
import wdk.data.Draft;
import wdk.data.Hitter;
import wdk.data.Pitcher;
import wdk.data.Player;
import wdk.data.Team;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.collections.ObservableList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.json.JsonValue;

/**
 * This is a DraftFileManager that uses the JSON file format to 
 * implement the necessary functions for loading and saving different
 * data for our drafts, hitters and pitchers.
 * 
 * @author Yael
 */

public class JsonDraftFileManager implements DraftFileManager {
    // JSON FILE READING AND WRITING CONSTANTS
    
    String JSON_DRAFT_NAME = "draftName";
    String JSON_LIST_OF_TEAMS = "listOfTeams";
    String JSON_FREE_AGENTS = "freeAgents";
    String JSON_MONEY_LEFT = "moneyLeft";
    String JSON_STARTING_LINEUP = "startingLineup";
    String JSON_TEAM_NAME = "teamName";
    String JSON_TEAM_OWNER = "teamOwner";
    String JSON_TAXI_SQUAD = "taxiSquad";
    
    String JSON_HITTERS = "Hitters";
    String JSON_PITCHERS = "Pitchers";
    
    // PLAYER
    String JSON_AGE = "AGE";
    String JSON_EST_VAL = "EST_VAL";
    String JSON_FIRST_NAME = "FIRST_NAME";
    String JSON_LAST_NAME = "LAST_NAME";
    String JSON_MLB_TEAM = "TEAM";
    String JSON_NOB = "NATION_OF_BIRTH";
    String JSON_YOB = "YEAR_OF_BIRTH";
    String JSON_NOTES = "NOTES";
    String JSON_SALARY = "SALARY";
    String JSON_H_STAT = "H";
    String JSON_CONTRACT = "CONTRACT";
    
    // HITTER
    String JSON_POSITION = "POSITION";
    String JSON_GAMES_PLAYED = "GAMES_PLAYED";
    String JSON_R_STAT = "R";
    String JSON_HR_STAT = "HR";
    String JSON_RBI_STAT = "RBI";
    String JSON_AB_STAT = "AB";
    String JSON_SB_STAT = "SB";
    String JSON_BA_STAT = "BA";
    String JSON_QP = "QP";
    
    // PITCHER
    String JSON_ROLE = "ROLE";
    String JSON_W_STAT = "W";
    String JSON_K_STAT = "K";
    String JSON_SV_STAT = "SV";
    String JSON_ERA_STAT = "ERA";
    String JSON_ER_STAT = "ER";
    String JSON_BB_STAT = "BB";
    String JSON_WHIP_STAT = "WHIP";
    String JSON_IP_STAT = "IP";
   
    String JSON_EXT = ".json";
    String SLASH = "/";
    
    
    @Override
    public void saveDraft(Draft draftToSave) throws IOException { /*
        // BUILD THE FILE PATH
        String draftListing = "" + draftToSave.getDraftName();
        String jsonFilePath = PATH_DRAFTS + SLASH + draftListing + JSON_EXT;
        
        // INIT THE WRITER
        OutputStream os = new FileOutputStream(jsonFilePath);
        JsonWriter jsonWriter = Json.createWriter(os);
        
        // MAKE A JSON ARRAY FOR THE LIST OF TEAMS
        JsonArray teamsJsonArray = makeTeamsJsonArray(draftToSave.getListOfTeams());
        
        // MAKE A JSON ARRAY FOR THE LIST OF FREE AGENTS
        JsonArray freeAgentsJsonArray = makeFreeAgentsJsonArray(draftToSave.getFreeAgents());
        
        JsonObject draftJsonObject = Json.createObjectBuilder()
                                       .add(JSON_DRAFT_NAME, draftToSave.getDraftName())
                                       .add(JSON_LIST_OF_TEAMS, teamsJsonArray)
                                       .add(JSON_FREE_AGENTS, freeAgentsJsonArray)
                .build();
        
        // AND SAVE EVERYTHING AT ONCE
        
        jsonWriter.writeObject(draftJsonObject); */    
    }
   
    /**
     * Loads the draftToLoad argument using the data found in the json file.
     * 
     * @param draftToLoad Draft to load.
     * @param jsonFilePath File containing the data to load.
     * 
     * @throws IOException Thrown when IO fails.
     */
    
    
    @Override
    public void loadDraft(Draft draftToLoad, String jsonFilePath) throws IOException {/*
        JsonObject json = loadJSONFile(jsonFilePath);
        
        // NOW LOAD THE COURSE
        draftToLoad.setDraftName(json.getString(JSON_DRAFT_NAME));
        
        // GET THE LIST OF TEAMS 
        draftToLoad.clearListOfTeams();
        JsonArray jsonTeamsArray = json.getJsonArray(JSON_LIST_OF_TEAMS);
        for (int i = 0; i < jsonTeamsArray.size(); i++) {
            JsonObject jso = jsonTeamsArray.getJsonObject(i);
            Team t = new Team();
            t.setMoneyLeft(jso.getInt(JSON_MONEY_LEFT));
            t.setStartingLineup((List<Player>) jso.getJsonObject(JSON_STARTING_LINEUP));
            t.setTaxiSquad((List<Player>) jso.getJsonObject(JSON_TAXI_SQUAD));
            t.setTeamName(jso.getString(JSON_TEAM_NAME));
            t.setTeamOwner(jso.getString(JSON_TEAM_OWNER));
            
            draftToLoad.addTeam(t);
        }
        
        // GET THE FREE AGENTS
        draftToLoad.clearFreeAgents();
        JsonArray jsonFreeAgentsArray = json.getJsonArray(JSON_FREE_AGENTS);
        for(int i = 0; i < jsonFreeAgentsArray.size(); i++) {
            JsonObject jso = jsonFreeAgentsArray.getJsonObject(i);
            Player p = new Player();
            p.setAge(jso.getInt(JSON_AGE));
            p.setContract(Contract.valueOf(jso.getString(JSON_CONTRACT)));
            p.setEstVal(jso.getInt(JSON_EST_VAL));
            p.setFirstName(jso.getString(JSON_FIRST_NAME));
            p.setLastName(jso.getString(JSON_LAST_NAME));
            p.setMLBTeam(jso.getString(JSON_MLB_TEAM));
            p.setNOB(jso.getString(JSON_NOB));
            p.setNotes(jso.getString(JSON_NOTES));
            p.setSalary(jso.getInt(JSON_SALARY));
            
            draftToLoad.addFreeAgent(p);
        }
            */
    }
    
    /**
     * Loads hitters from the JSON file.
     * @param jsonFilePath JSON file containing the hitters.
     * @return List full of Hitters loaded from the file.
     * @throws IOException Thrown when I/O fails.
     */
    
    @Override
    public ArrayList<Hitter> loadHitters(String jsonFilePath) throws IOException {
        ArrayList<Hitter> hittersArray = loadHitterArrayFromJSONFile(jsonFilePath, JSON_HITTERS);
        return hittersArray;
    }
    
    /**
     * Loads pitchers from the JSON file.
     * @param jsonFilePath JSON file containing the pitchers.
     * @return List full of Pitchers loaded from the file.
     * @throws IOException Thrown when I/O fails.
     */
    
    @Override
    public ArrayList<Pitcher> loadPitchers(String jsonFilePath) throws IOException {
        ArrayList<Pitcher> pitchersArray = loadPitcherArrayFromJSONFile(jsonFilePath, JSON_PITCHERS);
        return pitchersArray;
    }
    
    // AND HERE ARE THE PRIVATE HELPER METHODS TO HELP THE PUBLIC ONES
    
    // LOADS A JSON FILE AS A SINGLE OBJECT AND RETURNS IT
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
        InputStream is = new FileInputStream(jsonFilePath);
        JsonReader jsonReader = Json.createReader(is);
        JsonObject json = jsonReader.readObject();
        jsonReader.close();
        is.close();
        return json;
    } 
    
    // LOADS THE PITCHER ARRAY OF A SPECIFIC NAME FROM A JSON FILE AND
    // RETURNS IT AS AN ArrayList FULL OF THE DATA FOUND
    private ArrayList<Pitcher> loadPitcherArrayFromJSONFile(String jsonFilePath, String arrayName) throws IOException {
        JsonObject json = loadJSONFile(jsonFilePath);
        ArrayList<Pitcher> items = new ArrayList<>();
        JsonArray jsonArray = json.getJsonArray(arrayName);
        for (JsonValue jsV : jsonArray) {
            Pitcher p = new Pitcher();
            p.setMLBTeam(json.getString(JSON_MLB_TEAM).replaceAll("\"", ""));
            p.setLastName(json.getString(JSON_LAST_NAME).replaceAll("\"", ""));
            p.setFirstName(json.getString(JSON_FIRST_NAME).replaceAll("\"", ""));
            p.setIPStat(Double.parseDouble(json.getString(JSON_IP_STAT)));
            p.setERStat(json.getInt(JSON_ER_STAT));
            p.setWStat(json.getInt(JSON_W_STAT));
            p.setSVStat(json.getInt(JSON_SV_STAT));
            p.setHStat(json.getInt(JSON_H_STAT));
            p.setBBStat(json.getInt(JSON_BB_STAT));
            p.setKStat(json.getInt(JSON_K_STAT));
            p.setNotes(json.getString(JSON_NOTES).replaceAll("\"", ""));
            p.setYOB(json.getInt(JSON_YOB));
            p.setNOB(json.getString(JSON_NOB).replaceAll("\"", ""));
            items.add(p);
        }
        return items;
    }
    
    // LOADS THE HITTER ARRAY OF A SPECIFIC NAME FROM A JSON FILE AND
    // RETURNS IT AS AN ArrayList FULL OF THE DATA FOUND
    
    private ArrayList<Hitter> loadHitterArrayFromJSONFile(String jsonFilePath, String arrayName) throws IOException {
        JsonObject json = loadJSONFile(jsonFilePath);
        ArrayList<Hitter> items = new ArrayList<>();
        JsonArray jsonArray = json.getJsonArray(arrayName);
        for(JsonValue jsV : jsonArray) {
            Hitter h = new Hitter();
            h.setMLBTeam(json.getString(JSON_MLB_TEAM).replaceAll("\"", ""));
            h.setLastName(json.getString(JSON_LAST_NAME).replaceAll("\"", ""));
            h.setFirstName(json.getString(JSON_FIRST_NAME).replaceAll("\"", ""));
            h.setQP(json.getString(JSON_QP).replaceAll("\"", ""));
            h.setABStat(json.getInt(JSON_AB_STAT));
            h.setRStat(json.getInt(JSON_R_STAT));
            h.setHStat(json.getInt(JSON_H_STAT));
            h.setHRStat(json.getInt(JSON_HR_STAT));
            h.setRBIStat(json.getInt(JSON_RBI_STAT));
            h.setSBStat(json.getInt(JSON_SB_STAT));
            h.setNotes(json.getString(JSON_NOTES).replaceAll("\"", ""));
            h.setYOB(json.getInt(JSON_YOB));
            h.setNOB(json.getString(JSON_NOB).replaceAll("\"", ""));
            items.add(h);
        }
        return items;
    }
    
    
    // MAKES AND RETURNS A JSON OBJECT FOR THE PROVIDED HITTER
    private JsonObject makeHitterJsonObject(Hitter hitter) {
        JsonObject jso = Json.createObjectBuilder().add(JSON_FIRST_NAME, hitter.getFirstName())
                                                   .add(JSON_LAST_NAME, hitter.getLastName())
                                                   .add(JSON_CONTRACT, hitter.getContract().toString())
                                                   .add(JSON_MLB_TEAM, hitter.getMLBTeam())
                                                   .add(JSON_SALARY, hitter.getSalary())
                                                   .add(JSON_AGE, hitter.getAge())
                                                   .add(JSON_NOB, hitter.getNOB())
                                                   .add(JSON_YOB, hitter.getYOB())
                                                   .add(JSON_H_STAT, hitter.getHStat())
                                                   .add(JSON_EST_VAL, hitter.getEstVal())
                                                   .add(JSON_NOTES, hitter.getNotes())
                                                   .add(JSON_POSITION, hitter.getPosition())
                                                   .add(JSON_GAMES_PLAYED, hitter.getGamesPlayed())
                                                   .add(JSON_R_STAT, hitter.getRStat())
                                                   .add(JSON_HR_STAT, hitter.getHRStat())
                                                   .add(JSON_RBI_STAT, hitter.getRBIStat())
                                                   .add(JSON_AB_STAT, hitter.getABStat())
                                                   .add(JSON_SB_STAT, hitter.getSBStat())
                                                   .add(JSON_BA_STAT, hitter.getBAStat())
                                                   .add(JSON_QP, hitter.getQP())
                                                   .build();
        return jso;
    }
    
    // MAKES AND RETURNS A JSON OBJECT FOR THE PROVIDED PITCHER
    
    private JsonObject makePitcherJsonObject(Pitcher pitcher) {
        JsonObject jso = Json.createObjectBuilder().add(JSON_FIRST_NAME, pitcher.getFirstName())
                                                   .add(JSON_LAST_NAME, pitcher.getLastName())
                                                   .add(JSON_CONTRACT, pitcher.getContract().toString())
                                                   .add(JSON_MLB_TEAM, pitcher.getMLBTeam())
                                                   .add(JSON_SALARY, pitcher.getSalary())
                                                   .add(JSON_AGE, pitcher.getAge())
                                                   .add(JSON_NOB, pitcher.getNOB())
                                                   .add(JSON_YOB, pitcher.getYOB())
                                                   .add(JSON_H_STAT, pitcher.getHStat())
                                                   .add(JSON_EST_VAL, pitcher.getEstVal())
                                                   .add(JSON_NOTES, pitcher.getNotes())
                                                   .add(JSON_ROLE, pitcher.getRole())
                                                   .add(JSON_W_STAT, pitcher.getWStat())
                                                   .add(JSON_K_STAT, pitcher.getKStat())
                                                   .add(JSON_SV_STAT, pitcher.getSVStat())
                                                   .add(JSON_ER_STAT, pitcher.getERStat())
                                                   .add(JSON_H_STAT, pitcher.getHStat())
                                                   .add(JSON_BB_STAT, pitcher.getBBStat())
                                                   .add(JSON_ERA_STAT, pitcher.getERAStat())
                                                   .add(JSON_WHIP_STAT, pitcher.getWHIPStat())
                                                   .add(JSON_IP_STAT, pitcher.getIPStat())
                                                   .build();
        return jso;
    }
}