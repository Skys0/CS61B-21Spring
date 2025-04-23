package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static byow.Core.RandomCityGenerator.*;


/** 洪水算法，生成迷宫（路）
 * */
public class Flood {
    private int[][] color;
    /** 迷宫的弯曲程度 */
    private final int windingPercent;

    public Flood(int x, int y,int per) {
        color = new int[x][y];
        this.windingPercent = per;
    }

    /** 洪水算法的主程序
     * @param world 地图
     * @param x 横坐标
     * @param y 纵坐标
     * @param r 随机数种子
     * */
    public void FindOneWayFlood(TETile[][] world, int x, int y, Random r) {
        if (color[x][y] != 0 || world[x][y] != Tileset.WALL) {
            return;
        }
        List<Point> vector = new ArrayList<Point>();
        Point lastDir = new Point(0, 0);
        vector.add(new Point(x,y));
        while (!vector.isEmpty()) {
            // 获取到最后一个点
            Point cell = vector.get(vector.size() - 1);

            List<Point> nextPos = new ArrayList<Point>();

            // 四个方向遍历
            for (int i = 0;i < 4;i ++) {
                if (CanMove (world, cell, i)) {
                    nextPos.add(new Point(next[i][0], next[i][1]));
                }
            }
            // 如果存在可生长的方向
            if (!nextPos.isEmpty()) {
                Point dir;
                // 如果上一次在可生长方向上，且随机值大于迷宫的曲折程度
                if (nextPos.contains(lastDir) && r.nextInt(100) > windingPercent) {
                    dir = lastDir;
                } else {
                    int temp = r.nextInt(nextPos.size());
                    dir = nextPos.get(temp);
                }
                // 将所有生成的点加入 kruskal 并查集中
                Move(world, new Point(cell.x + dir.x, cell.y + dir.y));


                Move(world, new Point(cell.x + 2 * dir.x, cell.y + 2 * dir.y));
                vector.add(new Point(cell.x + 2 * dir.x, cell.y + 2 * dir.y));
                lastDir = dir;
            } else {
                vector.remove(vector.size() - 1);
                lastDir = new Point(0, 0);
            }
        }
    }

    /** 返回当前的点对于所给定的方向，能不能走
     * @param world 地图
     * @param x next数组所给的方向
     * @param now 现在所在的位置
     * */
    private boolean CanMove(TETile[][] world, Point now, int x) {
        if (world[now.x + next[x][0]][now.y + next[x][1]] != Tileset.NOTHING) {
            return false;
        }
        int destx = now.x + 2 * next[x][0];
        int desty = now.y + 2 * next[x][1];
        int nowcolor = color[now.x][now.y];
        if (color[destx][desty] == 0 && world[destx][desty] == Tileset.WALL) {
            return true;
        }
        return false;
    }

    /** 相当于将这个点涂上色，给打通
     * @param world 地图
     * @param p 被上色的点
     * */
    private void Move(TETile[][] world, Point p) {
        color[p.x][p.y] = 1;
        world[p.x][p.y] = Tileset.WALL;
    }
}
