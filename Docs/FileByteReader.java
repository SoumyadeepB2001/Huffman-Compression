import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

public class FileByteReader {
    public static void main(String[] args) {
        try {
            // Read all bytes using Files.readAllBytes
            byte[] data = Files.readAllBytes(new File("input.jpeg").toPath());

            // Print total number of bytes
            System.out.println("Total bytes: " + data.length);

            // Print each byte
            for (int i = 0; i < data.length; i++) {
                // Convert byte to unsigned int to avoid negative values
                int unsignedByte = data[i] & 0xFF;
                System.out.println("Byte " + i + ": " + unsignedByte);

                String binaryString = String.format("%8s", Integer.toBinaryString(unsignedByte)).replace(' ', '0');

                System.out.println("Byte " + i + " (binary): " + binaryString);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
