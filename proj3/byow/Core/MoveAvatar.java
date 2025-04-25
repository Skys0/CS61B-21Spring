package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

/** 对这个世界进行移动
 * */
public class MoveAvatar {
    private int Width = 0;
    private int Height = 0;
    private final Point Avatar;
    private final Point End;
    private boolean flag;

    public MoveAvatar(int width, int height, TETile[][] world) {
        this.Width = width;
        this.Height = height;
        this.Avatar = getWorldPos(world, Tileset.AVATAR);
        this.End = getWorldPos(world, Tileset.SAND);
        this.flag = true;
    }

    public Point getWorldPos(TETile[][] world,TETile o) {
        for (int i = 0;i < Width;i ++) {
            for (int j = 0;j < Height;j ++) {
                if (world[i][j] == o)
                    return new Point(i, j);
            }
        }
        return null;
    }

    private Point getDir(String dir) {
        if (dir.equals("W"))    return new Point(0, 1);
        else if (dir.equals("S"))   return new Point(0, -1);
        else if (dir.equals("D"))   return new Point(1, 0);
        else if (dir.equals("A"))   return new Point(-1, 0);
        return new Point(0, 0);
    }

    public void Move(TETile[][] world, String direction) {
        Point dir = getDir(direction);
        int tx = Avatar.x + dir.x;
        int ty = Avatar.y + dir.y;
        TETile[][] printWorld = new TETile[Width][Height];

        if (world[tx][ty] != Tileset.WALL) {
            world[Avatar.x][Avatar.y] = Tileset.FLOOR;
            world[tx][ty] = Tileset.AVATAR;
            Avatar.x = tx;
            Avatar.y = ty;
        }
        if (CheckAvatarIsEnd()) {
            System.exit(0);
        }
    }

    public boolean CheckAvatarIsEnd() {
        return (Avatar.equals(End));
    }

    public void changeHidden() {
        flag = !flag;
    }

    public TETile[][] GetPrintWorld(TETile[][] world) {
            if (flag == false)  return world;
            TETile[][] printWorld = new TETile[Width][Height];
        for (int i = 0;i < Width;i ++)
            for (int j = 0;j < Height;j ++)
                printWorld[i][j] = Tileset.NOTHING;

        for (int i = Avatar.x - 3;i < Avatar.x + 3;i ++)
            for (int j = Avatar.y - 3;j < Avatar.y + 3;j ++)
                printWorld[i][j] = world[i][j];

        return printWorld;
    }

    public void Save(TETile[][] world) {

    }
}
