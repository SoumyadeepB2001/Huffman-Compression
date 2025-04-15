import javax.swing.*;
import java.awt.*;

public class HuffmanCodec {
    private JFrame frame;

    public HuffmanCodec() {
        frame = new JFrame("Huffman Codec");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 280);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        // Start with CompressionPanel
        frame.add(new CompressionPanel(this), BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public void switchPanel(JPanel panel) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HuffmanCodec::new);
    }
}
