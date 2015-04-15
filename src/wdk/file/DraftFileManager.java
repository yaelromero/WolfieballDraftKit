/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wdk.file;

import java.io.IOException;
import java.util.ArrayList;
import wdk.data.Draft;
import wdk.data.Hitter;
import wdk.data.Pitcher;

/**
 * This interface provides an abstraction of what a file manager should do. Note
 * that file managers know how to read and write drafts, hitters, and pitchers,
 * but now how to export sites.
 * 
 * @author Yael
 */

public interface DraftFileManager {
    public void                 saveDraft(Draft draftToSave) throws IOException;
    public void                 loadDraft(Draft draftToLoad, String draftPath) throws IOException;
    public ArrayList<Hitter>    loadHitters(String filePath) throws IOException;
    public ArrayList<Pitcher>   loadPitchers(String filePath) throws IOException;
}
