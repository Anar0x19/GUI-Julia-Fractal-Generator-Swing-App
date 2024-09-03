import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JuliaFractalSwing extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int MAX_ITER = 1000;

    private JTextField realField;
    private JTextField imagField;
    private JPanel canvasPanel;
    private BufferedImage image;

    public JuliaFractalSwing() {
        setTitle("Julia Fractal Generator");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        realField = new JTextField("Real", 10);
        imagField = new JTextField("Complex", 10);
        JButton generateButton = new JButton("Generate");
        inputPanel.add(realField);
        inputPanel.add(imagField);
        inputPanel.add(generateButton);


        canvasPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (image != null) {
                    g.drawImage(image, 0, 0, null);
                }
            }
        };


        add(inputPanel, BorderLayout.NORTH);
        add(canvasPanel, BorderLayout.CENTER);


        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateFractal();
                canvasPanel.repaint();
            }
        });
    }

    private void generateFractal() {
        double real = Double.parseDouble(realField.getText());
        double imag = Double.parseDouble(imagField.getText());
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

        int red = (int) (Math.random() * 256);
        int green = (int) (Math.random() * 256);
        int blue = (int) (Math.random() * 256);

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                double zx = (x - WIDTH / 2.0) * 4.0 / WIDTH;
                double zy = (y - HEIGHT / 2.0) * 4.0 / HEIGHT;
                int iter = 0;
                while (zx * zx + zy * zy < 4 && iter < MAX_ITER) {
                    double temp = zx * zx - zy * zy + real;
                    zy = 2.0 * zx * zy + imag;
                    zx = temp;
                    iter++;
                }
                int colorValue = 255 * iter / MAX_ITER;
                int rgb = (red * colorValue / 255 << 16) | (green * colorValue / 255 << 8) | (blue * colorValue / 255);
                image.setRGB(x, y, rgb);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JuliaFractalSwing frame = new JuliaFractalSwing();
            frame.setVisible(true);
        });
    }
}
