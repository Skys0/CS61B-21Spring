package gitlet;

import java.io.File;
import static gitlet.Repository.*;
import static gitlet.Utils.*;

/** 提供一个分支，基本都是静态方法
 *  @author Skyss7
 */
public class Branch {


    /** 保存一个 Branch，内容为它所指的 Commit 的 id
     *  @param branchName 分支名
     *  @param commitId 保存的内容
     * */
    public static void saveBranch(String branchName, String commitId) {
        File f = join(Branch_DIR, branchName);
        writeContents(f, commitId);
    }


    /** 保存 HEAD 中有的分支和所指的 Commit,中间用 : 保存
     * @param HEADToCommitID HEAD 指向的 Commit 的文件名
     * @param branch 分支名
     * */
    public static void SaveHead(String branch, String HEADToCommitID) {
        writeContents(HEAD, branch + ":" +HEADToCommitID);
    }


    /** 从 HEAD 中获取分支名字
     *  @return 直接返回一个文件
     * */
    public static File getHeadBranch() {
        String[] temp = readContentsAsString(HEAD).split(":", 2);
        File branch = join(Branch_DIR, temp[0]);
        return branch;
    }


    /** 从这个分支里读取所指文件
     *  @return 返回 Commit 文件
     *  @param branch 分支文件
     * */
    public static File getBranchCommit(File branch) {
        if (!branch.exists())
            return null;
        String com = readContentsAsString(branch);
        File Point = join(Commit_DIR, com);
        return Point;
    }
}
