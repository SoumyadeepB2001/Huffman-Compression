import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

class ExtractionPanel extends JPanel {

    private JLabel lblFileLocation;
    private JLabel lblNewFileLocation;
    private File selectedFile;
    private JProgressBar progressBar;
    private final HuffmanCodec compApp;

    public ExtractionPanel(HuffmanCodec compApp) {
        this.compApp = compApp;
        setLayout(null);

        JPanel navBar = NavBar.createNavBar(compApp, "Extract");
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

        lblFileLocation = new JLabel("Select a .huff file to extract");
        lblFileLocation.setBounds(170, 40, 200, 30);
        lblFileLocation.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.add(lblFileLocation);

        // Second row: Y = 160 (bottom margin matches top)
        JButton btnDecode = new JButton("Extract");
        btnDecode.setBounds(50, 120, 100, 30);
        btnDecode.setBackground(new Color(206, 225, 242));
        btnDecode.addActionListener(e -> extractFile());
        panel.add(btnDecode);

        lblNewFileLocation = new JLabel();
        lblNewFileLocation.setBounds(170, 120, 200, 30);
        lblNewFileLocation.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.add(lblNewFileLocation);

        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setBounds(50, 170, 300, 15);
        progressBar.setVisible(false);
        panel.add(progressBar);

        return panel;
    }

    private void selectFile() {
        JFileChooser j = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Huffman Encoding Files (*.huff)", "huff");
        j.setFileFilter(filter);
        j.setAcceptAllFileFilterUsed(false);

        int r = j.showOpenDialog(null);

        if (r == JFileChooser.APPROVE_OPTION) {
            selectedFile = j.getSelectedFile();
            String fileName = selectedFile.getName().toLowerCase();

            if (fileName.endsWith(".huff")) {
                lblFileLocation.setText(selectedFile.getName());
            } else {
                JOptionPane.showMessageDialog(null, "Invalid file type! Please select a .huff file.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                selectedFile = null;
            }
        }
    }

    private void extractFile() {
    if (selectedFile == null) {
        JOptionPane.showMessageDialog(this, "Please select a file first.");
        return;
    }

    progressBar.setVisible(true);
    progressBar.repaint(); // Ensure it shows immediately
    lblNewFileLocation.setText("");
    lblNewFileLocation.setForeground(Color.BLACK);

    new Thread(() -> {
        File outputFile = null;

        try {
            outputFile = HuffmanDecoder.decode(selectedFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File finalOutputFile = outputFile;
        SwingUtilities.invokeLater(() -> {
            progressBar.setVisible(false);

            if (finalOutputFile != null && finalOutputFile.exists() && finalOutputFile.length() > 0) {
                lblNewFileLocation.setForeground(Color.BLACK);
                lblNewFileLocation.setText(finalOutputFile.getName());
            } else {
                lblNewFileLocation.setForeground(Color.RED);
                lblNewFileLocation.setText("Extraction failed!");
            }
        });
    }).start();
    }
}
