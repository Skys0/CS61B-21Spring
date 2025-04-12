package gitlet;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static gitlet.Repository.*;
import static gitlet.Utils.*;

/**
 * 表示 Gitlet 命令
 * @author Skyss7
 */
public class GitletMethod {


    /** 对应着 init 命令
     * Usage: java gitlet.Main init
     * */
    public static void init() {
        if (!GITLET_DIR.exists()) {
            GITLET_DIR.mkdir();
        } else{
            System.err.println("A Gitlet version-control system already exists in the current directory.");
            System.exit(0);
        }

        Repository.CreateRepository();
    }


    /** 对应着 add 命令
     * Usage: java gitlet.Main add [file name]
     * */
    public static void add(String[] args) {
        if (args.length != 2) {
            System.err.println("Incorrect operands.");
            System.exit(0);
        }

        File infile = Utils.join(CWD, args[1]);
        if (!infile.exists()) {
            System.err.println("File does not exist.");
            System.exit(0);
        }

        Track.add(infile);
    }


    /** 对应 rm命令
     * Usage: java gitlet.Main rm [file name].
     * */
    public static void rm(String[] args) {
        if (args.length != 2) {
            System.err.println("Incorrect operands.");
            System.exit(0);
        }

        File infile = Utils.join(CWD, args[1]);

        if (!Track.remove(infile)) {
            System.err.println("No reason to remove the file.");
            System.exit(0);
        }
    }


    /**对应 Commit 命令
     * Usage: java gitlet.Main commit [message]
     * */
    public static void commit(String[] args) {
        if (args.length != 2) {
            System.err.println("Incorrect operands.");
            System.exit(0);
        }

        // 获取*现在*的 Commit 与现在要提交的 Commit 逐一比较再对比更改
        Commit oldCommit = Commit.GetHeadToCommit();
        Commit newCommit = new Commit(oldCommit);
        newCommit.setMessage(args[1]);
        newCommit.setTimeStamp(new Date());
        newCommit.setPreCommitID(oldCommit.GetCommitSHA());

        // 读取两个暂存区文件，并比对
        List<String> StagingFile = plainFilenamesIn(Staging_DIR);
        List<String> RemoveFile = plainFilenamesIn(Remove_DIR);

        if (StagingFile != null) {
            for (String stag : StagingFile) {
                File temp = join(Staging_DIR, stag);
                String idFile = readContentsAsString(temp);
                newCommit.addBlobMap(stag, idFile);
                temp.delete();
            }
        }

        if (RemoveFile != null) {
            for (String rem : RemoveFile) {
                File temp = join(rem, rem);
                String idFile = readContentsAsString(temp);
                newCommit.removeBlobMap(rem, idFile);
                temp.delete();
            }
        }

        // 保存至 HEAD
        newCommit.SaveCommit();
        Branch.SaveHead("master", newCommit.GetCommitSHA());
        Branch.saveBranch("master", newCommit.GetCommitSHA());
        // 这里两个都得保存，不然在移动分支的时候会回到 init 的时候
    }


    /** 对应着 log 命令
     * Usage: java gitlet.Main log
     * */
    public static void printLog() {
        Commit points = Commit.GetHeadToCommit();
        while (points != null) {
            System.out.println("===");
            System.out.println("commit " + points.GetCommitSHA());
            System.out.println("Date: " + points.DateToString());
            System.out.println(points.getMessage());
            System.out.print("\n");

            // 找下一个 Commit
            if (points.getPreCommitID().isEmpty())      break;

            File nextFile = join(Commit_DIR, points.getPreCommitID());
            points = readObject(nextFile, Commit.class);
        }
    }

    /** 对应着 global-log 命令
     * 区别于 log 命令，它是输出所有的 Commit
     * Usage: java gitlet.Main global-log
     * */
    public static void printGlobalLog() {
        List<String> allCommitFile = plainFilenamesIn(Commit_DIR);

        // 查找所有 Commit 文件
        if (allCommitFile != null) {
            for (String its : allCommitFile) {
                File temp = join(Commit_DIR, its);
                Commit t = readObject(temp, Commit.class);

                System.out.println("===");
                System.out.println("commit " + t.GetCommitSHA());
                System.out.println("Date: " + t.DateToString());
                System.out.println(t.getMessage());
                System.out.print("\n");
            }
        }
    }


    /** 对应 Find 命令
     *  找到相应 message 的 Commit，并且输出 id
     *  Usage: java gitlet.Main find [commit message]
     * */
    public static void Find(String[] args) {
        List<String> allCommit = plainFilenamesIn(Commit_DIR);
        String checkMessage = args[1];
        int cnt = 0;


        // 在文件夹中找有没有对应的
        if (allCommit != null) {
            for (String its : allCommit) {
                File temp = join(Commit_DIR, its);
                Commit c = readObject(temp, Commit.class);

                if(checkMessage.equals(c.getMessage())) {
                    System.out.println(c.GetCommitSHA());
                    cnt += 1;
                }
            }
        }

        // 如果没有，就报错
        if (cnt == 0) {
            System.err.println("Found no commit with that message.");
            System.exit(0);
        }
    }


    /** 对应 status 命令
     *  显示当前存在的分支，暂存添加，暂存删除的文件
     *  ToDo: 我们先实现不完整的，后面在加
     *  Usage: java gitlet.Main status
     * */
    public static void status() {
        System.out.println("=== Branches ===");
        File Head = Branch.getHeadBranch();
        System.out.println("*" + Head.getName());

        // 查找所有不为当前所在的分支
        List<String> checkBranch = plainFilenamesIn(Branch_DIR);
        if (checkBranch != null) {
            for (String s : checkBranch) {
                File temp = join(Branch_DIR, s);
                if (!temp.equals(Head)) {
                    System.out.println(temp.getName());
                }
            }
        }
        System.out.print("\n");
        System.out.println("=== Staged Files ===");

        // 输出所有被暂存添加的文件
        List<String> stagingFiles = plainFilenamesIn(Staging_DIR);
        if (stagingFiles != null) {
            for (String s : stagingFiles) {
                System.out.println(s);
            }
        }
        System.out.print("\n");
        System.out.println("=== Removed Files ===");

        // 输出所有被暂存删除的文件
        List<String> removeFiles = plainFilenamesIn(Remove_DIR);
        if (removeFiles != null) {
            for (String s : removeFiles) {
                System.out.println(s);
            }
        }
        System.out.print("\n");
        System.out.println("=== Modifications Not Staged For Commit ===");
        // 剩下的没有实现

        System.out.print("\n");
        System.out.println("=== Untracked Files ===");

        System.out.print("\n");
    }

    /**对应着 checkout 命令
     * 一共有三种可能，分别是，直接有 --，中间接着一个 --，没有 --
     * Usages:
     * 1. java gitlet.Main checkout -- [file name]
     * 2. java gitlet.Main checkout [commit id] -- [file name]
     * 3. java gitlet.Main checkout [branch name]
     * */
    public static void checkout(String[] args) {
        // 对应第一种参数
        // 获取 head commit 中存在的文件版本，并将其放入工作目录中，覆盖已经存在的文件版本（如果有）。文件的新版本不会暂存。
        if (args.length == 3) {
            if (!args[1].equals("--")) {
                System.err.println("Incorrect operands.");
                System.exit(0);
            }

            Commit headCommit = Commit.GetHeadToCommit();
            HashMap<String, String> perviousBlobs = headCommit.getCommitBlobs();
            File recoverFile = join(CWD, args[2]);
            // 如果我们恢复的文件存在
            if (perviousBlobs.containsKey(args[2])) {
                String id = perviousBlobs.get(args[2]);
                File remain = join(Object_DIR, id);
                Blob temp = readObject(remain, Blob.class);
                writeContents(recoverFile, temp.getContext());
            } else {
                System.err.println("File does not exist in that commit.");
                System.exit(0);
            }
        }

        // 对应第二种参数
        // 获取提交中具有给定 ID 的文件版本，并将其放入工作目录中，覆盖已经存在的文件版本（如果有）。文件的新版本不会暂存。
        if (args.length == 4) {
            if (!args[2].equals("--")) {
                System.err.println("Incorrect operands.");
                System.exit(0);
            }

            // 首先找到 Commit 存不存在
            File checkFile = Commit.CheckCommit(args[1]);
            if (checkFile == null) {
                System.err.println("No commit with that id exists.");
                System.exit(0);
            }

            // 如果存在，那么查找对应文件恢复
            File recoverFile = join(CWD, args[3]);
            Commit checkCommit = readObject(checkFile, Commit.class);
            HashMap<String, String> Blobs = checkCommit.getCommitBlobs();

            if (Blobs.containsKey(args[3])) {
                File t = join(Object_DIR, Blobs.get(args[3]));
                Blob temp = readObject(t, Blob.class);
                writeContents(recoverFile, temp.getContext());
            } else {
                System.err.println("File does not exist in that commit.");
                System.exit(0);
            }
        }

        if (args.length == 2) {
            checkoutBranch(args[1]);
        }
    }

    /**针对 checkout branch 的处理
     * @param branchName 分支的名字
     * */
    public static void checkoutBranch(String branchName) {
        File branch = join(Branch_DIR, branchName);
        File branchFile = Branch.getBranchCommit(branch);

        // 如果不存在具有该名称的分支
        if (branchFile == null) {
            System.err.println("No such branch exists.");
            System.exit(0);
        }

        // 如果该分支是当前分支
        if (branchFile.equals(Branch.getHeadBranch())) {
            System.err.println("No need to checkout the current branch.");
            System.exit(0);
        }

        Commit headCommit = readObject(branchFile, Commit.class);
        HashMap<String, String> Blobs = headCommit.getCommitBlobs();

        // 如果有文件没被追踪，报错
        if (Track.CheckUntrackFile()) {
            System.err.println("There is an untracked file in the way; delete it, or add and commit it first.");
            System.exit(0);
        }

        removeCWDFiles();

        // 每一个文件恢复
        for (String s : Blobs.keySet()) {
            File recoverFile = join(CWD, s);
            Blob temp = readObject(join(Object_DIR, Blobs.get(s)), Blob.class);
            writeContents(recoverFile, temp.getContext());
        }

        Branch.SaveHead(branchName, headCommit.GetCommitSHA());
    }


    /**对应 Branch 命令
     * 创建一个引用，创建一个新分支，指向新 HEAD
     * Usage: java gitlet.Main branch [branch name]
     * */
    public static void CreateBranch(String[] args) {
        String name = args[1];
        File newBranch = join(Branch_DIR, name);

        if (newBranch.exists()) {
            System.err.println("A branch with that name already exists.");
            System.exit(0);
        }

        Branch.saveBranch(name, Commit.GetHeadToCommit().GetCommitSHA());
    }

    /** 对应 rm-branch 命令
     *  删除具有给定名称的分支
     *  Usage: java gitlet.Main rm-branch [branch name]
     * */
    public static void rmBranch(String[] args) {
        File branch = join(Branch_DIR, args[1]);
        if (!branch.exists()) {
            System.err.println("A branch with that name does not exist.");
            System.exit(0);
        }

        if (branch.equals(Branch.getHeadBranch())) {
            System.err.println("Cannot remove the current branch.");
            System.exit(0);
        }

        restrictedDelete(branch);
    }

    /** 对应 reset 命令
     *  Usage: java gitlet.Main reset [commit id]
     * */
    public static void reset(String[] args) {
        File f = Commit.CheckCommit(args[1]);
        if (f == null) {
            System.err.println("No commit with that id exists.");
            System.exit(0);
        }

        if (Track.CheckUntrackFile()) {
            System.err.println("There is an untracked file in the way; delete it, or add and commit it first.");
            System.exit(0);
        }

        removeCWDFiles();

        // 遍历整个 Commit 中的文件，一个个恢复
        Commit commit = readObject(f, Commit.class);
        HashMap<String, String> Blobs = commit.getCommitBlobs();

        for (String recoverFileName : Blobs.keySet()) {
            File recoverFile = join(CWD, recoverFileName);
            Blob temp = readObject(join(Object_DIR, Blobs.get(recoverFileName)), Blob.class);

            writeContents(recoverFile, temp.getContext());
        }

        // 同时将其 branchHEAD 指向 commit
        Branch.saveBranch(Branch.getHeadBranch().getName(), commit.GetCommitSHA());
        // 将目前给定的 HEAD 指针指向这个 commit
        Branch.SaveHead(Branch.getHeadBranch().getName(), commit.GetCommitSHA());
    }



}
