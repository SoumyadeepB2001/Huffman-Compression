import java.io.*;
import java.util.*;

public class HuffmanDecoder {

    public static Object[] readHeader(InputStream is) throws IOException {
        int extLen = is.read();
        byte[] extBytes = is.readNBytes(extLen);
        String extension = new String(extBytes);

        int padding = is.read();

        int[] freq = new int[256];
        for (int i = 0; i < 256; i++) {
            long f = 0;
            for (int j = 0; j < 8; j++) {
                f = (f << 8) | (is.read() & 0xFF);
            }
            freq[i] = (int) f;
        }

        return new Object[] { extension, padding, freq };
    }

    public static File decode(File inputFile) throws IOException {
        String baseName = inputFile.getName();
        int dotIndex = baseName.lastIndexOf('.');
        if (dotIndex > 0) {
            baseName = baseName.substring(0, dotIndex);
        }

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(inputFile))) {
            // Step 1: Read metadata
            Object[] header = readHeader(bis);
            String extension = (String) header[0];
            int padding = (int) header[1];
            int[] freq = (int[]) header[2];

            // Step 2: Rebuild Huffman tree
            HuffmanNode root = HuffmanEncoder.buildTree(freq);

            // Step 3: Decode content
            File outputFile = new File(inputFile.getParent(), baseName + "_decoded." + extension);
            try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputFile))) {
                BitInputStream bitInput = new BitInputStream(bis);
                HuffmanNode current = root;

                long totalSymbols = Arrays.stream(freq).asLongStream().sum();
                long symbolsDecoded = 0;

                while (symbolsDecoded < totalSymbols) {
                    int bit = bitInput.readBit();
                    if (bit == -1)
                        break;

                    current = (bit == 0) ? current.left : current.right;

                    if (current.isLeaf()) {
                        bos.write(current.symbol);
                        current = root;
                        symbolsDecoded++;
                    }
                }

                bitInput.close();
            }

            return outputFile;
        }
    }
}