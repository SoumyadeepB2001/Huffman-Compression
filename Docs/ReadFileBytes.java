import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ReadFileBytes {
    public static void main(String[] args) {
        try {
            // Read all bytes using Files.readAllBytes
            byte[] data = Files.readAllBytes(new File("input.jpeg").toPath());

            // Print total number of bytes
            System.out.println("Total bytes: " + data.length);

            // Print each byte

            System.out.print("{");
            System.out.print(data[0]);

            for (int i = 1; i < data.length; i++)                 
                System.out.print(", " + data[i]);

                System.out.print("}");
            

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
