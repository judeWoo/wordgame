package data;

import wgcomponents.StdRandom;

import java.util.Random;

/**
 * This code is from http://coursera.cs.princeton.edu/algs4/assignments/boggle.html
 */

public class BuzzBoard {
// the 16 Boggle dice (1992 version)
    private static final String[] BOGGLE_1992 = {
            "LRYTTE", "VTHRWE", "EGHWNE", "SEOTIS",
            "ANAEEG", "IDSYTT", "OATTOW", "MTOICU",
            "AFPKFS", "XLDERI", "HCPOAS", "ENSIEU",
            "YLDEVR", "ZNRNHL", "NMIQHU", "OBBAOJ"
    };

    // the 16 Boggle dice (1983 version)
    private static final String[] BOGGLE_1983 = {
            "AACIOT", "ABILTY", "ABJMOQ", "ACDEMP",
            "ACELRS", "ADENVZ", "AHMORS", "BIFORX",
            "DENOSW", "DKNOTU", "EEFHIY", "EGINTV",
            "EGKLUY", "EHINPS", "ELPSTU", "GILRUW",
    };

    // the 25 Boggle Master / Boggle Deluxe dice
    private static final String[] BOGGLE_MASTER = {
            "AAAFRS", "AAEEEE", "AAFIRS", "ADENNN", "AEEEEM",
            "AEEGMU", "AEGMNN", "AFIRSY", "BJKQXZ", "CCNSTW",
            "CEIILT", "CEILPT", "CEIPST", "DDLNOR", "DHHLOR",
            "DHHNOT", "DHLNOR", "EIIITT", "EMOTTT", "ENSSSU",
            "FIPRSY", "GORRVW", "HIPRRY", "NOOTUW", "OOOTTU"
    };

    // the 25 Big Boggle dice
    private static final String[] BOGGLE_BIG = {
            "AAAFRS", "AAEEEE", "AAFIRS", "ADENNN", "AEEEEM",
            "AEEGMU", "AEGMNN", "AFIRSY", "BJKQXZ", "CCENST",
            "CEIILT", "CEILPT", "CEIPST", "DDHNOT", "DHHLOR",
            "DHLNOR", "DHLNOR", "EIIITT", "EMOTTT", "ENSSSU",
            "FIPRSY", "GORRVW", "IPRRRY", "NOOTUW", "OOOTTU"
    };


    // letters and frequencies of letters in the English alphabet
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final double[] FREQUENCIES = {
            0.08167, 0.01492, 0.02782, 0.04253, 0.12703, 0.02228,
            0.02015, 0.06094, 0.06966, 0.00153, 0.00772, 0.04025,
            0.02406, 0.06749, 0.07507, 0.01929, 0.00095, 0.05987,
            0.06327, 0.09056, 0.02758, 0.00978, 0.02360, 0.00150,
            0.01974, 0.00074
    };

    private final int m;        // number of rows
    private final int n;        // number of columns
    private char[][] board;     // the m-by-n array of characters
    private static Random random;
    private static long seed;        // pseudo-standard number generator seed

    /**
     * Initializes a standard 4-by-4 board, by rolling the Hasbro dice.
     */
    public BuzzBoard() {
        seed = System.currentTimeMillis();
        random = new Random(seed);
        m = 4;
        n = 4;
        shuffle(BOGGLE_1992);
        board = new char[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                String letters = BOGGLE_1992[n*i+j];
                int r = uniform(letters.length());
                board[i][j] = letters.charAt(r);
            }
        }
    }

    public BuzzBoard(String test) {
        m =4;
        n =4;
        board = new char[][]{{'A', 'P', 'P', 'L'},
                {'D', 'D', 'D', 'E'},
                {'D', 'D', 'D', 'D'},
                {'D', 'D', 'D', 'D'},
        };
    }

    /**
     * Initializes a random m-by-n board, according to the frequency
     * of letters in the English language.
     * @param m the number of rows
     * @param n the number of columns
     */
//    public BuzzBoard(int m, int n) {
//        this.m = m;
//        this.n = n;
//        if (m <= 0) throw new IllegalArgumentException("number of rows must be a positive integer");
//        if (n <= 0) throw new IllegalArgumentException("number of columns must be a positive integer");
//        board = new char[m][n];
//        for (int i = 0; i < m; i++) {
//            for (int j = 0; j < n; j++) {
//                int r = StdRandom.discrete(FREQUENCIES);
//                board[i][j] = ALPHABET.charAt(r);
//            }
//        }
//    }

    /**
     * Returns the number of rows.
     * @return number of rows
     */
    public int rows() {
        return m;
    }

    /**
     * Returns the number of columns.
     * @return number of columns
     */
    public int cols() {
        return n;
    }

    /**
     * Returns the letter in row i and column j,
     * with 'Q' representing the two-letter sequence "Qu".
     * @param i the row
     * @param j the column
     * @return the letter in row i and column j
     *    with 'Q' representing the two-letter sequence "Qu".
     */
    public char getLetter(int i, int j) {
        return board[i][j];
    }

    /**
     * Returns a string representation of the board, replacing 'Q' with "Qu".
     * @return a string representation of the board, replacing 'Q' with "Qu"
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(board[i][j]);
                if (board[i][j] == 'Q') sb.append("u ");
//                else sb.append("  ");
            }
//            sb.append("\n");
        }
        return sb.toString().trim();
    }

    public static void shuffle(Object[] a) {
        if (a == null) throw new IllegalArgumentException("argument array is null");
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int r = i + uniform(n-i);     // between i and n-1
            Object temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    public static int uniform(int n) {
        if (n <= 0) throw new IllegalArgumentException("argument must be positive");
        return random.nextInt(n);
    }
}
