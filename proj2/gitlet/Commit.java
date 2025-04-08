package gitlet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/** Represents a gitlet commit object.
 *
 *  @author Skyss7
 */
public class Commit implements Serializable {
    /** List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.*/

    /** The message of this Commit. */
    private String message;
    /** The Time of Commit. */
    private Date date;
    /** The list of Blobs.*/
    private ArrayList<String> Blobs;
    /** The pointer of the previous Commit. */
    private Commit Pre;

    /** Create the Commit. */
    public Commit(String m, Date t,Commit pre) {
        message = m;
        date = t;
        this.Pre = pre;
        Blobs = new ArrayList<String>();
    }

    public String GetCommitName() {
        return Utils.sha1(date, message, Blobs);
    }

    public void MakeCommit() {

    }
}
