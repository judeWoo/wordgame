package data;

/**
 * Created by Jude Hokyoon Woo on 11/28/2016.
 */
//Based on http://www.shuati123.com/blog/2014/08/29/Boggle-solver/
public class BuzzTrie {
        private BuzzTrieNode root;

        public BuzzTrie() {
            this.root = new BuzzTrieNode();
        }

        public void addWord(String word) {
            BuzzTrieNode node = this.root;
            for (char c : word.toCharArray()) {
                node = node.addChild(c);
                if (node == null)
                    return;
            }
            node.setWord(true);
        }

        public BuzzTrieNode match(String s) {
            BuzzTrieNode node = this.root;
            for (char c : s.toCharArray()) {
                node = node.get(c);
                if (node == null)
                    return null;
            }
            return node;
        }
}
