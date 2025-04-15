public class HuffmanNode implements Comparable<HuffmanNode> {
    byte symbol;
    int freq;
    String name;
    HuffmanNode left, right;

    public HuffmanNode(byte symbol, int freq) {
        this.symbol = symbol;
        this.freq = freq;
        this.name = Character.toString((char) (symbol & 0xFF));
    }

    public HuffmanNode(HuffmanNode left, HuffmanNode right) {
        this.left = left;
        this.right = right;
        this.freq = left.freq + right.freq;
        this.name = mergeNamesLexically(left.name, right.name);
    }

    private String mergeNamesLexically(String a, String b) {
        return (a.compareTo(b) <= 0) ? a + b : b + a;
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }

    @Override
    public int compareTo(HuffmanNode other) {
        if (this.freq != other.freq)
            return this.freq - other.freq;
        return this.name.compareTo(other.name);
    }
}
