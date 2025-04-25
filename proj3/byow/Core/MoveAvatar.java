package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

/** 对这个世界进行移动
 * */
public class MoveAvatar {
    private int Width = 0;
    private int Height = 0;
    Point Avatar;
    Point End;

    public MoveAvatar(int width, int height, TETile[][] world) {
        this.Width = width;
        this.Height = height;
        this.Avatar = getWorldPos(world, Tileset.AVATAR);
        this.End = getWorldPos(world, Tileset.SAND);
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

        if (world[tx][ty] == Tileset.FLOOR) {
            world[Avatar.x][Avatar.y] = Tileset.FLOOR;
            world[tx][ty] = Tileset.AVATAR;
            Avatar.x = tx;
            Avatar.y = ty;
        }
    }
}
