import javax.swing.*;
import java.awt.*;

public class NavBar {
    public static JPanel createNavBar(HuffmanCodec compApp, String activePanel) {

        JPanel navBar = new JPanel(new GridLayout(1, 4));
        navBar.setBounds(0, 0, 400, 28);

        JButton btnImageEncode = new JButton("Compress");
        JButton btnImageDecode = new JButton("Extract");

        btnImageEncode.setBackground(Color.WHITE);
        btnImageDecode.setBackground(Color.WHITE);

        Color activeTabColor = new Color(0, 24, 46);

        if (activePanel.equals("Compress")) {
            btnImageEncode.setBackground(activeTabColor);
            btnImageEncode.setEnabled(false);
        } else {
            btnImageEncode.addActionListener(e -> compApp.switchPanel(new CompressionPanel(compApp)));
        }

        if (activePanel.equals("Extract")) {
            btnImageDecode.setBackground(activeTabColor);
            btnImageDecode.setEnabled(false);
        } else {
            btnImageDecode.addActionListener(e -> compApp.switchPanel(new ExtractionPanel(compApp)));
        }

        navBar.add(btnImageEncode);
        navBar.add(btnImageDecode);

        return navBar;
    }
}