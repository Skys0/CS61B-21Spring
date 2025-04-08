package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import static gitlet.Repository.*;

/**
 * Tracking add operation the file.
 * @author Skyss7
 */
public class Track implements Serializable {

    /**Record the file in Working dict of the tracker.*/
    private HashMap<String, String> Working;
    /** Record the files added. */
    private HashMap<String, String> Added;
    /** Record the files deleted.  */
    private HashMap<String, String> Deleted;

    public Track() {
        Added = new HashMap<String, String>();
        Deleted = new HashMap<String, String>();
        Working = new HashMap<String, String>();
    }

    /** Adds a copy of the file as it currently exists to the staging area.
     * If it is currently in the (Deleted), remove it from (Deleted) and added it to (Added).
     * */
    public void add(File f) {
        String id = GetValueWorking(f.getName());
        if (id == null || !id.equals(Blob.GetBlobName(f))) {
            AddFileToAdded(f);
        }

        String de = GetValueDeleted(f.getName());
        if (id != null) {
           Deleted.remove(f.getName());
        }
    }

    /** Stage the file to (Deleted).
     * If it is currently staged Unstage it.
     * */
    public boolean remove(File f) {
        return false;
    }

    /** Get the Cracking file the version. */
    private String GetValueWorking(String key) {
        return Working.get(key);
    }

    /** Get the Added file the version. */
    private String GetValueAdd(String key) {
        return Added.get(key);
    }

    /** Get the Deleted file the version. */
    private String GetValueDeleted(String key) {
        return Deleted.get(key);
    }

    /** Add the file to (Added).
     * Copy the cracker file to staging.*/
    private void AddFileToAdded(File f) {
        File stage = Utils.join(staging_DIR, f.getName());
        Utils.writeContents(stage, Utils.readContentsAsString(f));
        Added.put(f.getName(), Blob.GetBlobName(f));
    }

    /** Clear the Track dict. */
    private void clear() {
        Added.clear();
        Deleted.clear();
        Working.clear();
    }

    /**Save the Track Object to file. */
    public void SaveTrack() {
        Utils.writeObject(TrackFile, this);
    }
}
