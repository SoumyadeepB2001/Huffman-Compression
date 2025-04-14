import java.util.*;

public class HuffmanEncoder {

    static class Node implements Comparable<Node> {
        byte symbol;
        int freq;
        String name; // New field to store node name
        Node left, right;

        // Leaf node constructor
        Node(byte symbol, int freq) {
            this.symbol = symbol;
            this.freq = freq;
            this.name = Character.toString((char) symbol); // Convert byte to character string
        }

        // Internal node constructor
        Node(Node left, Node right) {
            this.left = left;
            this.right = right;
            this.freq = left.freq + right.freq;
            this.name = mergeNamesLexically(left.name, right.name);
        }

        // Merge names in lexical order
        private String mergeNamesLexically(String a, String b) {
            return (a.compareTo(b) <= 0) ? a + b : b + a;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        @Override
        public int compareTo(Node other) {
            if (this.freq != other.freq)
                return this.freq - other.freq;
            return this.name.compareTo(other.name); // Tie-breaker: lexical order
        }
    }

    // Step 1: Build frequency map
    public static Map<Byte, Integer> buildFrequencyTable(byte[] data) {
        Map<Byte, Integer> freqMap = new HashMap<>();
        for (byte b : data)
            freqMap.put(b, freqMap.getOrDefault(b, 0) + 1);
        return freqMap;
    }

    // Step 2: Build Huffman Tree
    public static Node buildTree(Map<Byte, Integer> freqMap) {
        PriorityQueue<Node> heap = new PriorityQueue<>();
        for (Map.Entry<Byte, Integer> entry : freqMap.entrySet()) {
            heap.add(new Node(entry.getKey(), entry.getValue()));
        }

        while (heap.size() > 1) {
            Node left = heap.poll();
            Node right = heap.poll();
            heap.add(new Node(left, right));
        }

        return heap.poll(); // root
    }

    // Step 3: Generate codes
    public static void generateCodes(Node node, String code, Map<Byte, String> codeMap) {
        if (node.isLeaf()) {
            codeMap.put(node.symbol, code);
            return;
        }
        generateCodes(node.left, code + "0", codeMap);
        generateCodes(node.right, code + "1", codeMap);
    }

    // Step 4: Encode input using code map
    public static String encode(byte[] data, Map<Byte, String> codeMap) {
        StringBuilder sb = new StringBuilder();
        for (byte b : data) {
            sb.append(codeMap.get(b));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        byte[] data = "ABBACCDDD".getBytes();

        // Step 1
        Map<Byte, Integer> freqMap = buildFrequencyTable(data);

        // Step 2
        Node root = buildTree(freqMap);

        // Step 3
        Map<Byte, String> codeMap = new HashMap<>();
        generateCodes(root, "", codeMap);

        // Step 4
        String encoded = encode(data, codeMap);

        // Print results
        System.out.println("Huffman Codes:");
        for (Map.Entry<Byte, String> entry : codeMap.entrySet()) {
            System.out.println((char) entry.getKey().byteValue() + " -> " + entry.getValue());
        }

        System.out.println("\nEncoded bit string: " + encoded);
    }
}