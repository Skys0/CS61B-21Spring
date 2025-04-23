package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class RandomCityGenerator {
    public static final int Width = 71;
    public static final int Height = 51;
    public static final double Contain = 0.7;
    public static final int[][] next = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}, {1, 1}, {-1, -1}, {-1, 1}, {1, -1}};
    public static final int bottomX =  (int) Math.round(Width / 2 - Width*Contain / 2) / 2 * 2 + 1;
    public static final int topX = (int) Math.round(Width / 2 + Width*Contain / 2) / 2 * 2 + 1;
    public static final int bottomY = (int) Math.round(Height / 2 - Height*Contain / 2) / 2 * 2 + 1;
    public static final int topY = (int) Math.round(Height / 2 + Height*Contain / 2) / 2 * 2 + 1;

    /** 随机生成2D世界（第二版）
     * */
    public static TETile[][] RandomGenerator(Random r) {
        TETile[][] city = new TETile[Width][Height];
        for (int i = 0; i < Width; i++) {
            for (int j = 0; j < Height; j++) {
                city[i][j] = Tileset.NOTHING;
            }
        }

        // 第一部分：在整个地图中生成一些盒子，就相当于房间
        // 生成随机盒子的大小以及数量，不能特别大
        int cnt = 0;
        int amountBox = r.nextInt(10, 15);
        List<Box> boxes = new ArrayList<Box>();
        for (int i = bottomX ;i <= topX;i += 2) {
            for (int j = bottomY;j <= topY;j += 2) {
                city[i][j] = Tileset.WALL;
                // 我们先将所有奇数变成墙，其实这些就是可联通的路
            }
        }

        // 生成直到达到我们随机的数量
        while (cnt < amountBox) {
            Box b = new Box(r);
            if (Box.checkOverlap(boxes, b)) {
                boxColor(city, b, Tileset.FLOOR);
                boxes.add(b);
                // 注意我们这里为了识别把 box 变成其他属性，之后我们在翻转一下即可
                cnt ++;
            }
        }

        // 第二部分：用洪水算法生成随机生成迷宫（路）
        Flood model = new Flood(Width, Height, 15);
        for (int i = bottomX;i <= topX;i += 2) {
            for (int j = bottomY;j <= topY;j += 2) {
                model.FindOneWayFlood(city, i, j, r);
            }
        }

        // 第三部分：将生成的迷宫和我们的盒子连接起来
        // 首先，我们先将盒子的所有可能连接的点加入一个集合
        List<List<Point>> edgeBox = new ArrayList<List<Point>>();
        edgeBox = AddBoxEdge(city, boxes);

        for (List<Point> edges : edgeBox) {
            int cnt1 = 1;
            for (int i = 0;i < cnt1;i ++) {
                int t = r.nextInt(edges.size() - 1);
                DrawOneCell(city, edges.get(t), Tileset.WALL);
            }
        }


        // 最后的优化：就是将一些死胡同给删掉
        for (int i = bottomX;i < topX;i ++) {
            for (int j = bottomY;j < topY;j ++) {
                Point p = new Point(i, j);
                if (city[i][j] == Tileset.NOTHING)
                    continue;
                TryFindDeadEnd(city, p);
            }
        }

        ReverseWall(city);
        GenerateOtherWall(city);
        return city;
    }

    /** 查找边界并全部上墙*/
    public static void GenerateOtherWall(TETile[][] city) {
        for (int x = 0;x < Width;x ++) {
            for (int y = 0;y < Height;y ++) {
                if (city[x][y] == Tileset.FLOOR) {
                    for (int i = 0;i < 8;i++) {
                        if (CheckOutOfCity(x, y, i) && city[x + next[i][0]][y + next[i][1]] == Tileset.NOTHING) {
                            city[x + next[i][0]][y + next[i][1]] = Tileset.WALL;
                        }
                    }
                }
            }
        }
    }

    /** 查找有没有过边界
     * @param x 横坐标
     * @param y 纵坐标
     * @param i 对应next的方向
     * */
    public static boolean CheckOutOfCity(int x,int y,int i) {
        if (x + next[i][0] < 0 || x + next[i][0] >= Width){
            return false;
        }
        if (y + next[i][1] < 0 || y + next[i][1] >= Height){
            return false;
        }
        return true;
    }


    /** 一整块盒子涂色
     * @param city 地图
     * @param color 颜色，都在 Tileset 文件中
     * */
    public static void boxColor(TETile[][] city,Box rc, TETile color) {
        int sz1 = rc.getx() + rc.getWidth(),sz2 = rc.gety() - rc.getHeight();
        for (int i = rc.getx();i < sz1;i ++) {
            for (int j = rc.gety();j > sz2;j --) {
                city[i][j] = color;
            }
        }
    }

    /** 将所有的墙变成迷宫，就相当于路一样
     * @param city 地图
     * */
    public static void ReverseWall(TETile[][] city) {
        for (int i = 0;i < Width;i ++) {
            for (int j = 0;j < Height;j ++) {
                if (city[i][j] == Tileset.WALL)
                    city[i][j] = Tileset.FLOOR;
            }
        }
    }

    /** 生成房间边缘中可连接的点
     * @param city 地图
     * @param boxes 各个房间
     * */
    public static List<List<Point>> AddBoxEdge(TETile[][] city, List<Box> boxes) {
        List<List<Point>> edgeBox = new ArrayList<List<Point>>();
        for (Box b : boxes) {
            // 先找上边和下边
            List<Point> points = new ArrayList<Point>();
            int top = b.gety() + 1;
            int bottom = b.gety() - b.getHeight();
            for (int i = b.getx();i < b.getx() + b.getWidth();i ++) {
                if (CheckConnection(city, i, top)) {
                    points.add(new Point(i, top));
                }
            }
            for (int i = b.getx();i < b.getx() + b.getWidth();i ++) {
                if (CheckConnection(city, i, bottom)) {
                    points.add(new Point(i, bottom));
                }
            }
            // 再来找左边和右边
            top = b.getx() - 1;
            bottom = b.getx() + b.getWidth();
            for (int i = b.gety();i > b.gety() - b.getHeight();i --) {
                if (CheckConnection(city, top, i)) {
                    points.add(new Point(top, i));
                }
            }
            for (int i = b.gety();i > b.gety() - b.getHeight();i --) {
                if (CheckConnection(city, bottom, i)) {
                    points.add(new Point(bottom, i));
                }
            }
            edgeBox.add(points);
        }
        return edgeBox;
    }

    /** 检查是否能成为连接点
     * @param city 地图
     * @param x,y 横纵坐标
     * */
    public static boolean CheckConnection(TETile[][] city, int x, int y) {
        int cnt = 0;
        for (int i = 0;i < 4;i ++) {
            int tx = x + next[i][0];
            int ty = y + next[i][1];
            if (city[tx][ty] == Tileset.NOTHING) {
                cnt ++;
            }
        }
        return cnt == 2;
    }


    /** 对于空的单个点点去涂色
     * @param city 地图
     * @param color 颜色
     * @param p 坐标点
     * */
    public static void DrawOneCell(TETile[][] city, Point p, TETile color) {
            city[p.x][p.y] = color;
    }

    public static void TryFindDeadEnd(TETile[][] city, Point p) {
        int count = 0;
        if (IsDeadEnd(city, p)) {
            // 限制删除死胡同的个数
            Stack<Point> vector = new Stack<Point>();
            vector.add(p);
            while (!vector.isEmpty()) {
                if (count > 50)     break;
                count ++;
                Point temp = vector.pop();
                DrawOneCell(city, temp, Tileset.NOTHING);

                // 四个方向去搜寻，找到是死胡同的
                for (int i = 0;i < 4;i ++) {
                    Point tp = new Point(temp.x + next[i][0], temp.y + next[i][1]);
                    if (city[tp.x][tp.y] == Tileset.WALL && IsDeadEnd(city, tp)) {
                        vector.push(tp);
                    }
                }
            }
        }
        return;
    }


    /** 判断是否为死胡同，是死胡同则返回 True */
    public static boolean IsDeadEnd(TETile[][] city, Point p) {
        int cnt = 0;
        for (int i = 0;i < 4;i ++) {
            int tx = p.x + next[i][0];
            int ty = p.y + next[i][1];
            if (city[tx][ty] == Tileset.NOTHING)
                cnt ++;
        }

        return cnt == 3;
    }



    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(Width, Height);

        Random r = new Random(80);
        TETile[][] city = RandomGenerator(r);
        String s = TETile.toString(city);
        ter.renderFrame(city);
    }
}