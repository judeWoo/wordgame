package data;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

/**
 * Created by bilash, Jude Hokyoon Woo on 11/27/2016.
 */
public class BuzzWordSolver {
    private static final int N = 4;
    private static char[][] board = new char[N][N];
    private static BuzzWordTrie root;
    private static int counter = 0;

    private static class BuzzWordTrie {
        public final char letter;
        public BuzzWordTrie[] nextNodes = new BuzzWordTrie[26]; //26 letters in alphabet
        public boolean wordEnd;

        public BuzzWordTrie(final char letter) {
            this.letter = letter;
        }

        public void insert(final String word) {
            BuzzWordTrie node = root;
            char[] letters = word.toCharArray();

            for (int i = 0; i < letters.length; i++) {
                if (node.nextNodes[letters[i] - 'a'] == null) {
                    node.nextNodes[letters[i] - 'a'] = new BuzzWordTrie(letters[i]);

                    if (i == letters.length - 1) {
                        node.nextNodes[letters[i] - 'a'].wordEnd = true;
                    }
                }

                node = node.nextNodes[letters[i] - 'a'];
            }
        }

        public boolean contains(final String word) {
            BuzzWordTrie node = root;
            char[] letters = word.toCharArray();
            int i = 0;
            while (i < letters.length && node.nextNodes[letters[i] - 'a'] != null) {
                node = node.nextNodes[letters[i] - 'a'];
                i++;
            }

            return (i == letters.length) && node.wordEnd;

        }
    }

    public static boolean isInBoard(final String word) {
        int[] dx = {1, 1, 0, -1, -1, -1, 0, 1};
        int[] dy = {0, 1, 1, 1, 0, -1, -1, -1};

        boolean[][][] dp = new boolean[N*N+1][N][N];
        char[] letters = word.toCharArray();

        for (int k = 0; k < letters.length; k++) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (k == 0) {
                        dp[k][i][j] = true;
                    } else {
                        for (int l = 0; l < 8; l++) {
                            int x = i + dx[l];
                            int y = j + dy[l];

                            if ((x >= 0) && (x < N) && (y >= 0) && (y < N)
                                    && (dp[k - 1][x][y]) && (board[i][j] == letters[k])) {
                                dp[k][i][j] = true;
                                if (k == letters.length - 1) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    public static void buzzTrieDynamic(BuzzWordTrie node, char[] currentBranch, int currentHeight) {

        if (node == null) {
            return;
        }

        if (node.wordEnd && currentHeight > 3) {
            String word = new String(currentBranch, 0, currentHeight - 1); // Take out words from Trie
            boolean inBoard = isInBoard(word);
            if (inBoard && node.contains(word)) {
                System.out.println(word);
                counter++;
            }
        }

        for (int i = 0; i < node.nextNodes.length; i++) {
            if (node.nextNodes[i] != null) {
                currentBranch[currentHeight] = (char) (i + 'a');
                buzzTrieDynamic(node.nextNodes[i], currentBranch, currentHeight + 1);
            }
        }
    }

    public static void readBoard(final BuzzBoard boardFile) throws IOException {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                board[i][j] = Character.toLowerCase(boardFile.getLetter(i, j));
            }
        }
    }

    public void readDict() throws IOException, URISyntaxException {
        URL wordsResource = getClass().getClassLoader().getResource("words/words.txt");
        assert wordsResource != null;
        List<String> words = Files.readAllLines(Paths.get(wordsResource.toURI()), StandardCharsets.UTF_8);

        root = new BuzzWordTrie('\0');
        for (String word : words) {
            root.insert(word);
        }
    }

    public void solveBoard(){
        long start = System.currentTimeMillis();
        buzzTrieDynamic(root, new char[50], 0);
        long end = System.currentTimeMillis();
        System.out.println("Total time spent = " + (end - start) + " milli seconds.");
        System.out.println("Done...");
        System.out.println(counter);
    }

}
