package byow.lab13;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;
import java.util.Stack;

public class MemoryGame {
    /** The width of the window of this game. */
    private int width;
    /** The height of the window of this game. */
    private int height;
    /** The current round the user is on. */
    private int round;
    /** The Random object used to randomly generate Strings. */
    private Random rand;
    /** Whether or not the game is over. */
    private boolean gameOver;
    /** Whether or not it is the player's turn. Used in the last section of the
     * spec, 'Helpful UI'. */
    private boolean playerTurn;
    /** The characters we generate random Strings from. */
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    /** Encouraging phrases. Used in the last section of the spec, 'Helpful UI'. */
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        long seed = Long.parseLong(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, long seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 25);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        StdDraw.setPenColor(Color.white);
        rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        int cnt = 0;
        String RamdomString = "";
        while (cnt < n) {
            char x = CHARACTERS[rand.nextInt(26)];
            RamdomString = RamdomString + x;
            cnt += 1;
        }
        return RamdomString;
    }

    public void drawFrame(String s) {
        StdDraw.clear(Color.BLACK);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 上面的固定的文字
        String roundText = "Round: " + String.valueOf(round);
        StdDraw.text((double) roundText.length() / 2, this.width - 1, roundText);
        StdDraw.text((double) this.height / 2, this.width - 1, "Watch!");


        String encourage = ENCOURAGEMENT[rand.nextInt(6)];
        StdDraw.text(this.height - (double) encourage.length() / 2 + 1, this.width - 1, encourage);
        StdDraw.line(0,this.width - 2,this.height, this.width - 2);

        StdDraw.text((double) this.width / 2, (double) this.height / 2, s);
        StdDraw.show();
    }

    public void flashSequence(String letters) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        char[] letter = letters.toCharArray();
        for (char t : letter) {
            String temp = String.valueOf(t);
            drawFrame(temp);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public String solicitNCharsInput(int n) {
        StringBuilder letters = new StringBuilder();
        int cnt = 0;
        while (cnt < n) {
            if (StdDraw.hasNextKeyTyped()) {
                char x = StdDraw.nextKeyTyped();
                letters.append(x);
                drawFrame(letters.toString());
                cnt += 1;
            }
        }
        return letters.toString();
    }

    public void startGame() {
        round = 1;
        while (true) {
            String s = "Round " + round + "!";
            drawFrame(s);
            String showString = generateRandomString(round);
            flashSequence(showString);
            drawFrame("");
            String inputString = solicitNCharsInput(round);
            if (inputString.equals(showString)) {
                round += 1;
            } else {
                drawFrame("You lose! You made it to Round : " + round);
                break;
            }
        }
    }
}
