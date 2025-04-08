package gitlet;

import java.io.File;

/** Blob is the saved contents of files.
 *  @author Skyss7
 */
public class Blob {

    /** The unique identifier of the translation file. */
    private final String uid;
    /** The context of the Blob. */
    private final String context;


    /** initialize the Blob. */
    public Blob(File f) {
        context = Utils.readContentsAsString(f);
        uid = GetBlobName(f);
    }

    /** Return the file of BlobName.
     * This is the static. */
    public static String GetBlobName(File f) {
        return Utils.sha1(Utils.readContentsAsString(f) + f.getName());
    }
}
