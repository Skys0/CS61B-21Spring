package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static gitlet.Utils.*;

/** Represents a gitlet repository.
 *  @author Skyss7
 */
public class Repository implements Serializable {
    /**
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    /** The Folder of the objects. */
    public static final File Object_DIR = join(GITLET_DIR, "objects");
    /** The Folder of the refs. */
    public static final File Refs_DIR = join(GITLET_DIR, "refs");
    /** he Folder of the branch. */
    public static final File heads_DIR = join(Refs_DIR, "heads");

    /** Head of Branch. */
    public static final File HEAD = join(GITLET_DIR, "HEAD");

    /**The index object which stored refs of added files and removed files and staging file. */
    public static final File staging_DIR = join(GITLET_DIR, "staging");
    public static final File TrackFile = join(GITLET_DIR, "Track");

    /** Create the Repository. */
    public static void CreateRepository() {
        ArrayList<File> Dirs = new ArrayList<File>(Arrays.asList(Refs_DIR, Object_DIR, heads_DIR, staging_DIR));
        for (File f : Dirs) {
            if(!f.exists()) {
                f.mkdir();
            }
        }
        try {
            HEAD.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Branch master = new Branch("master", null);

        /* Save the Initial Branch-master. */
        File masterFile = join(Refs_DIR, master.uid);
        Utils.writeObject(masterFile, master);

        /* Point out the HEAD to master.*/
        Utils.writeContents(HEAD, master.uid);
    }
}
