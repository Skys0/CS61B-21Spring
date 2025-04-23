package byow.Core;

/** 简单的实现一个点，或者向量 */
public class Point {
    public int x;
    public int y;

    public Point(int x,int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof Point)) {
            return false;
        }
        Point obj = (Point) o;

        return (obj.x == this.x && obj.y == this.y);
    }

    @Override
    public String toString(){
        return "(" + this.x + "," + this.y + ")";
    }
}
