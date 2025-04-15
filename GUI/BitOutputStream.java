import java.io.ByteArrayOutputStream;

public class BitOutputStream {
    private ByteArrayOutputStream output = new ByteArrayOutputStream();
    private int currentByte = 0;
    private int numBitsFilled = 0;

    public void writeBit(int bit) {
        currentByte = (currentByte << 1) | bit;
        numBitsFilled++;
        if (numBitsFilled == 8) {
            output.write(currentByte);
            numBitsFilled = 0;
            currentByte = 0;
        }
    }

    public void writeBits(String bits) {
        for (char c : bits.toCharArray()) {
            writeBit(c == '1' ? 1 : 0);
        }
    }

    public int flushAndGetPadding() {
        int padding = 0;
        if (numBitsFilled > 0) {
            currentByte <<= (8 - numBitsFilled);
            output.write(currentByte);
            padding = 8 - numBitsFilled;
        }
        return padding;
    }

    public byte[] toByteArray() {
        return output.toByteArray();
    }
}