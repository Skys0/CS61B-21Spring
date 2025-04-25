package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.util.Random;

import static byow.Core.RandomCityGenerator.*;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        MainMenu.ShowMainMenu(40, 40);
        char x = KeyBoradInput.InputSingleWord();
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        TETile[][] showWorld = new TETile[WIDTH][HEIGHT];
        for (int i = 0;i < WIDTH;i ++)
            for (int j = 0;j < HEIGHT;j ++)
                showWorld[i][j] = Tileset.NOTHING;
        if (x == 'N' || x == 'n') {
            long seed = 0;
            seed = KeyBoradInput.InputSeed();
            Random r = new Random(seed);
            TERenderer ter = new TERenderer();
            ter.initialize(WIDTH, HEIGHT);
            world = RandomGenerator(r);
        } else if (x == 'L' || x == 'l') {

        } else if (x == 'Q' || x == 'q') {
            System.exit(0);
        }
        MoveAvatar Avatar = new MoveAvatar(WIDTH, HEIGHT, world);

        // 游戏开始：一次次的循环
        while (true) {
            try {
                Thread.sleep(20);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 设置刷新率，现在是一秒刷新 50 次
            String HUD = KeyBoradInput.GetMousePosition(world);
            String move = KeyBoradInput.InputControlWASD();
            if (move != null) {
                if (move.equals("W") || move.equals("w"))   Avatar.Move(world, "W");
                if (move.equals("S") || move.equals("s"))   Avatar.Move(world, "S");
                if (move.equals("A") || move.equals("a"))   Avatar.Move(world, "A");
                if (move.equals("D") || move.equals("d"))   Avatar.Move(world, "D");
                if (move.equals("V") || move.equals("v"))   Avatar.changeHidden();
                if (move.equals(":")) {
                    char optQ = KeyBoradInput.InputSingleWord();
                    if (optQ == 'q' || optQ == 'Q') {
                        Avatar.Save(world);
                        System.exit(0);
                    }
                }
            }
            ter.renderFrame(Avatar.GetPrintWorld(world), HUD);
        }
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        Long seed = Long.parseLong(input.substring(1, input.length() - 1));
        Random r = new Random(seed);
        return RandomGenerator(r);
    }
}
