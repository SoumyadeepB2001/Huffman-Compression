import java.awt.Color;
import java.io.File;
import javax.swing.*;

class CompressionPanel extends JPanel {

    private JLabel lblFileLocation;
    private JLabel lblNewFileLocation;
    private File selectedFile;
    private final HuffmanCodec compApp;

    public CompressionPanel(HuffmanCodec compApp) {
        this.compApp = compApp;
        setLayout(null);

        JPanel navBar = NavBar.createNavBar(compApp, "Compress");
        add(navBar);

        JPanel contentPanel = createContentPanel();
        contentPanel.setBounds(0, 28, 400, 280);
        add(contentPanel);
    }

    private JPanel createContentPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.LIGHT_GRAY);

        // Top margin: 70px
        JButton btnFile = new JButton("Select file");
        btnFile.setBounds(50, 40, 100, 30);
        btnFile.setBackground(new Color(206, 225, 242));
        btnFile.addActionListener(e -> selectFile());
        panel.add(btnFile);

        lblFileLocation = new JLabel("Select any file to compress");
        lblFileLocation.setBounds(170, 40, 200, 30);
        lblFileLocation.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.add(lblFileLocation);

        JButton btnEncode = new JButton("Compress");
        btnEncode.setBounds(50, 120, 100, 30);
        btnEncode.setBackground(new Color(206, 225, 242));
        btnEncode.addActionListener(e -> compressFile());
        panel.add(btnEncode);

        lblNewFileLocation = new JLabel();
        lblNewFileLocation.setBounds(170, 120, 200, 30);
        lblNewFileLocation.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.add(lblNewFileLocation);
        
        return panel;
    }

    private void selectFile() {
        JFileChooser j = new JFileChooser();
        j.setAcceptAllFileFilterUsed(true);

        int r = j.showOpenDialog(null);

        if (r == JFileChooser.APPROVE_OPTION) {
            selectedFile = j.getSelectedFile();
            lblFileLocation.setText(selectedFile.getName());
        }
    }

    private void compressFile() {

        String inputFile = selectedFile.getName();
        String outputFile = "";

        String baseName;
        String extension;
        int dotIndex = inputFile.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < inputFile.length() - 1) {
            baseName = inputFile.substring(0, dotIndex);
            extension = inputFile.substring(dotIndex + 1);
        } else {
            baseName = inputFile;
            extension = "";
        }

        outputFile = baseName + ".huff";

        boolean success = HuffmanEncoder.compressFile(selectedFile, outputFile);

        if (success) {
            lblNewFileLocation.setText(outputFile);
        } else {
            lblNewFileLocation.setForeground(Color.RED);
            lblNewFileLocation.setText("Compression failed!");
        }
    }
}