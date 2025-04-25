package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import static java.awt.event.KeyEvent.*;

/**
 * 这个类主要来等待用户在 keyborad 输入，以及处理成对应的数据
  */
public class KeyBoradInput {
    /** 专门处理一个字符的输入的 */
    public static char InputSingleWord() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char x = StdDraw.nextKeyTyped();
                System.out.println(x);
                return x;
            }
        }
    }

    public static Long InputSeed() {
        StringBuilder s = new StringBuilder(new String());
        MainMenu.FlashSeedMenu(0);
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char x = StdDraw.nextKeyTyped();
                if (x == 'S' || x == 's')   break;
                s.append(x);
                MainMenu.FlashSeedMenu(Long.parseLong(s.toString()));
            }
        }
        return Long.parseLong(s.toString());
    }

    public static String GetMousePosition(TETile[][] world) {
        int mousePosX = (int) StdDraw.mouseX();
        int mousePosY = (int) StdDraw.mouseY();
        if (mousePosX >= world.length || mousePosY >= world[0].length) {
            return null;
        }
        if (world[mousePosX][mousePosY] == Tileset.NOTHING)
            return null;
        else if (world[mousePosX][mousePosY] == Tileset.WALL)
            return "WALL";
        else if (world[mousePosX][mousePosY] == Tileset.FLOOR)
            return "FLOOR";
        else if (world[mousePosX][mousePosY] == Tileset.AVATAR)
            return "AVATAR";
        else if (world[mousePosX][mousePosY] == Tileset.SAND)
            return "END Position";
        else
            return "UNDEFINED";
    }

    /** 处理是否有没有上下左右的移动 */
    public static String InputControlWASD() {
        if (StdDraw.isKeyPressed(VK_UP))     return "W";
        else if (StdDraw.isKeyPressed(VK_DOWN))  return "S";
        else if (StdDraw.isKeyPressed(VK_LEFT))  return "A";
        else if (StdDraw.isKeyPressed(VK_RIGHT)) return "D";
        if (StdDraw.hasNextKeyTyped()) {
            return String.valueOf(StdDraw.nextKeyTyped());
        }
        return null;
    }
}
