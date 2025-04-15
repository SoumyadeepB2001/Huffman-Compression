import java.io.*;
import java.util.*;

public class HuffmanEncoder {

    public static int[] buildFrequencyTable(File inputFile) throws IOException {
        int[] freq = new int[256];
        try (InputStream is = new BufferedInputStream(new FileInputStream(inputFile))) {
            int b;
            while ((b = is.read()) != -1) {
                freq[b]++;
            }
        }
        return freq;
    }

    public static HuffmanNode buildTree(int[] freq) {
        PriorityQueue<HuffmanNode> minHeap = new PriorityQueue<>();
        for (int i = 0; i < 256; i++) {
            if (freq[i] > 0) {
                minHeap.add(new HuffmanNode((byte) i, freq[i]));
            }
        }
    
        while (minHeap.size() > 1) {
            HuffmanNode left = minHeap.poll();
            HuffmanNode right = minHeap.poll();
            minHeap.add(new HuffmanNode(left, right));
        }
    
        return minHeap.poll();
    }
    

    public static void generateCodes(HuffmanNode node, String code, Map<Byte, String> codeMap) {
        if (node.isLeaf()) {
            codeMap.put(node.symbol, code);
            return;
        }
        generateCodes(node.left, code + "0", codeMap);
        generateCodes(node.right, code + "1", codeMap);
    }

    public static byte[] encode(File inputFile, Map<Byte, String> codeMap, int[] paddingOut) throws IOException {
        BitOutputStream bos = new BitOutputStream();
        try (InputStream is = new BufferedInputStream(new FileInputStream(inputFile))) {
            int b;
            while ((b = is.read()) != -1) {
                bos.writeBits(codeMap.get((byte) b));
            }
        }
        paddingOut[0] = bos.flushAndGetPadding();
        return bos.toByteArray();
    }

    public static void writeHuffFile(String path, String extension, int padding, int[] freq,
                                     byte[] encodedData) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path))) {
            bos.write(extension.length());
            bos.write(extension.getBytes());
            bos.write(padding);
            for (int i = 0; i < 256; i++) {
                long f = freq[i];
                bos.write((int) (f >> 56));
                bos.write((int) (f >> 48));
                bos.write((int) (f >> 40));
                bos.write((int) (f >> 32));
                bos.write((int) (f >> 24));
                bos.write((int) (f >> 16));
                bos.write((int) (f >> 8));
                bos.write((int) f);
            }
            bos.write(encodedData);
        }

        System.out.println("Encoding complete. File written: " + path);
    }

    public static void main(String[] args) throws IOException {
        File inputFile = new File("input.wav"); // Replace with your input file
        String fileName = inputFile.getName();

        String baseName;
        String extension;
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            baseName = fileName.substring(0, dotIndex);
            extension = fileName.substring(dotIndex + 1);
        } else {
            baseName = fileName;
            extension = "";
        }

        // Step 1: Build Frequency Table
        int[] freq = buildFrequencyTable(inputFile);

        // Step 2: Build Huffman Tree
        HuffmanNode root = buildTree(freq);

        // Step 3: Generate Codes
        Map<Byte, String> codeMap = new HashMap<>();
        generateCodes(root, "", codeMap);

        // Step 4: Encode
        int[] paddingOut = new int[1];
        byte[] encodedData = encode(inputFile, codeMap, paddingOut);

        // Step 5: Write output
        writeHuffFile(baseName + ".huff", extension, paddingOut[0], freq, encodedData);
    }
}