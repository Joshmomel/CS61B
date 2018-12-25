package byog.lab6;

import byog.Core.Game;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
            "You got this!", "You're a star!", "Go Bears!",
            "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        gameOver = false;
        playerTurn = false;
        round = 1;

        //TODO: Establish Game loop
        while (!gameOver) {
            playerTurn = false;

            drawFrame("Round" + round + "! Good luck");
            StdDraw.pause(1500);

            String s = generateRandomString(round);
            flashSequence(s);

            playerTurn = true;
            String input = solicitNCharsInput(round);

            if (!input.equals(s)) {
                gameOver = true;
                drawFrame("Game Over! Final level: " + round);
            } else  {
                drawFrame("Correct, well done! ");
                StdDraw.pause(500);
                round ++;
            }

        }
    }

    public MemoryGame(int width, int height, long seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        StringBuilder sb = new StringBuilder();
        while (sb.length() < n) {
            sb.append(CHARACTERS[rand.nextInt(CHARACTERS.length)]);
        }
        return sb.toString();
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
        int midWidth = width / 2;
        int midHeight = height / 2;

        StdDraw.clear();
        StdDraw.clear(Color.black);

        if (!gameOver) {
            Font smallFont = new Font("Monaco", Font.BOLD, 20);
            StdDraw.setFont(smallFont);
        }

        StdDraw.setFont(new Font("Monaco", Font.BOLD, 30));
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(midWidth, midHeight, s);
        StdDraw.show();
    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        for (int i = 0; i < letters.length(); i++) {
            drawFrame(letters.substring(i, i + 1));
            StdDraw.pause(550);
            drawFrame(" ");
            StdDraw.pause(550);
        }
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        String i  = "";
        drawFrame(i);

        while (i.length() < n) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                i += String.valueOf(c);
                drawFrame(i);
            }
        }
        StdDraw.pause(500);
        return i;

    }



}
