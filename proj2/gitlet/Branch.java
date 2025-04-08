package gitlet;

import java.io.Serializable;

/** Provide a branch.
 *  @author Skyss7
 */
public class Branch implements Serializable {
    /** This class is used to store various branches.*/

    /** The branch of name.*/
    private String name;
    /** The branch of SHA-1 called uid. This is unique.*/
    public String uid;
    /** This branch of the Pointer of the commit.
     * Always is the Head.*/
    private Commit Point;


    /** Create the branch.*/
    public Branch(String name, Commit p) {
        this.name = name;
        this.Point = p;
        this.uid = GetBrachName(name, p);
    }

    /** Return the SHA-1. */
    public String GetBrachName(String name,Commit c) {
        return Utils.sha1(name, c);
    }
}
