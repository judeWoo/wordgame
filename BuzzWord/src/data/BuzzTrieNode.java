package data;

/**
 * Created by Jude Hokyoon Woo on 11/28/2016.
 */
//Based on http://www.shuati123.com/blog/2014/08/29/Boggle-solver/
public class BuzzTrieNode {
        private BuzzTrieNode[] children;
        private boolean isWord = false;

        public BuzzTrieNode() {
            this.children = new BuzzTrieNode[26];
        }

        public BuzzTrieNode addChild(char child) {
            if (child < 'a' || child > 'z')
                return null;

            int offset = child - 'a';
            if (this.children[offset] == null) {
                this.children[offset] = new BuzzTrieNode();
            }
            return this.children[offset];
        }

        public boolean isWord() {
            return isWord;
        }

        public void setWord(boolean isWord) {
            this.isWord = isWord;
        }

        public BuzzTrieNode get(char c) {
            int offset = c - 'a';
            return this.children[offset];
        }

}
