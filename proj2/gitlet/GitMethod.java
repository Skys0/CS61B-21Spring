package gitlet;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import static gitlet.Repository.*;

/**
 * Represents gitlet commands.
 * @author Skyss7
 */
public class GitMethod {


    /** Usage: java gitlet.Main init.
     * Initialize the Gitlet.
     * */
    public static void init(String[] args) {
        if (!GITLET_DIR.exists()) {
            GITLET_DIR.mkdir();
        } else{
            System.err.println("A Gitlet version-control system already exists in the current directory.");
            System.exit(0);
        }
        Repository.CreateRepository();

        Commit First_Commit = new Commit("Initial commit", new Date(0), null);
        First_Commit.MakeCommit();
    }


    /** Usage: java gitlet.Main add [file name].
     *  add the file to Gitlet System.
     * */
    public static void add(String[] args) {
        File infile = Utils.join(CWD, args[1]);
        if (!infile.exists()) {
            System.err.println("File does not exist.");
            System.exit(0);
        }
        Track t = Utils.readObject(TrackFile, Track.class);
        t.add(infile);
        t.SaveTrack();
    }

    /** Usage: java gitlet.Main rm [file name].
     * Remove the file to Gitlet System.
     * */
    public static void rm(String[] args) {
        File infile = Utils.join(CWD, args[1]);
        Track t = Utils.readObject(TrackFile, Track.class);
        if (!t.remove(infile)) {
            System.err.println("No reason to remove the file.");
            System.exit(0);
        }
    }
}
