package byow.Core;

import java.util.List;
import java.util.Random;
import static byow.Core.RandomCityGenerator.*;

/** 一个房间盒子的类
 *  记录左上角，高度和宽度
 *  注意这个盒子的坐标符合笛卡尔坐标系
 *  通过上面的参数可以算出右下角的坐标，y坐标是下降的，x坐标是上升的
 */
public class Box {
    private int x;
    private int y;
    private int height;
    private int width;

    public Box(Random r) {
        this.x = r.nextInt(bottomX / 2, topX / 2) * 2 + 1;
        this.y = r.nextInt(bottomY / 2, topY / 2) * 2 + 1;
        this.height = r.nextInt(1,4) * 2 + 3;
        this.width = r.nextInt(1, 4) * 2 + 3;
    }

    /** 在这个盒子中随机得到一个边缘点，返回一个int数组，只有两个元素，x，y
     * @param r 所给的随机数
     * */
    public int[] RandomPoint(Random r) {
        int x = 0, y = 0;
        int t = r.nextInt(2);
        int[] temp = {x, y};
        return temp;
    }

    /** 判断盒子直接有没有重叠,还有有没有超越边界
     *  @param boxes 已存在的房间
     * @param rc2 需要判断的房间
     * */
    public static boolean checkOverlap(List<Box> boxes, Box rc2) {
        if (rc2.x + rc2.width  >= topX
                || rc2.y - rc2.height <= bottomY) {
            return false;
        }

        for (Box rc1 : boxes) {
            if (rc1.x + rc1.width > rc2.x && rc2.x + rc2.width > rc1.x
            && rc1.y - rc1.height < rc2.y && rc2.y - rc2.height < rc1.y) {
                return false;
            }
        }
        return true;
    }


    public int getx() {
        return x;
    }

    public int gety() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
