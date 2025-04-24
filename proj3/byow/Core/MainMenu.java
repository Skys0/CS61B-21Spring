package byow.Core;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;

/** 创建Main Menu */
public class MainMenu {

    private static int Width;
    private static int Height;
    private static final String title = "CS61B: THE GAME";
    private static final String[] options = {"New Game (N)", "Load Game (L)", "Quit (Q)"};
    public static final Font titleFont = new Font("Consolas", Font.BOLD, 30);
    public static final Font littleFont = new Font("Consolas", Font.PLAIN, 20);

    public static void ShowMainMenu(int width, int height) {
        Width = width;
        Height = height;
        StdDraw.setCanvasSize(width * 16, height * 16);
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenColor(Color.white);

        StdDraw.setFont(titleFont);
        StdDraw.text(width / 2.0, height / 4.0 * 3, title);

        StdDraw.setFont(littleFont);
        double ty = height / 2.0;
        for (int i = 0;i < 3;i ++) {
            StdDraw.text(width / 2.0, ty, options[i]);
            ty -= 2;
        }

        StdDraw.show();
    }

    public static void FlashSeedMenu(long seed) {
        StdDraw.clear(Color.BLACK);
        StdDraw.setFont(littleFont);
        String st = "Please input your Seed(End with a Letter S)";
        StdDraw.text(st.length() / 2, Height / 4.0 * 3, st);
        StdDraw.setFont(littleFont);
        if (seed != 0)
            StdDraw.text(Width / 2.0, Height / 2.0, String.valueOf(seed));

        StdDraw.show();
    }

    public static void main(String[] args) {
        Engine e = new Engine();
        e.interactWithKeyboard();
    }
}
