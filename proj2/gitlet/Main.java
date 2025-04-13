package gitlet;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author Skyss7
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        if (args == null) {
            System.err.println("Please enter a command.");
            System.exit(0);
        }

        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                GitletMethod.init();
                break;
            case "add":
                GitletMethod.add(args);
                break;
            case "rm":
                GitletMethod.rm(args);
                break;
            case "commit":
                GitletMethod.commit(args);
                break;
            case "log":
                GitletMethod.printLog();
                break;
            case "global-log":
                GitletMethod.printGlobalLog();
                break;
            case "find":
                GitletMethod.Find(args);
                break;
            case "status":
                GitletMethod.status();
                break;
            case "checkout":
                GitletMethod.checkout(args);
                break;
            case "branch":
                GitletMethod.CreateBranch(args);
                break;
            case "rm-branch":
                GitletMethod.rmBranch(args);
                break;
            case "reset":
                GitletMethod.reset(args);
                break;
            case "merge":
                GitletMethod.merge(args);
                break;
        }
    }
}
