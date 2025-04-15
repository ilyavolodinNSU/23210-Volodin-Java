package factory.GUI;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ImagePanel extends JPanel {
    private Image image;
    private boolean state;
    private boolean isHovered = false;
    private JSlider slider;
    private String text;

    public ImagePanel(String path) {
        setImage(path);
        setPreferredSize(new Dimension(100, 100));
        setLayout(null);
        this.state = false;

        slider = new JSlider(0, 100, 50);
        slider.setMajorTickSpacing(0);
        slider.setMinorTickSpacing(0);
        slider.setPaintTicks(false);
        slider.setPaintLabels(false);

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = slider.getValue();
                System.out.println("Значение ползунка: " + value);
            }
        });

        add(slider, BorderLayout.SOUTH);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        if (isHovered) {
            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, getWidth(), getHeight());

            if (text != null && !text.isEmpty()) {
                g2.setFont(new Font("Arial", Font.BOLD, 20));
                g2.setColor(Color.BLACK);

                FontMetrics fm = g2.getFontMetrics();
                int textWidth = fm.stringWidth(text);
                int textHeight = fm.getHeight();

                int x = (getWidth() - textWidth) / 2;
                int y = (getHeight() + textHeight) / 2 - fm.getDescent();

                g2.drawString(text, x, y);
            }
        } else {
            g2.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }

        g2.dispose();
    }

    public void setText(String text) {
        this.text = text;
        repaint();
    }

    public void setImage(String path) {
        this.image = new ImageIcon(getClass().getResource(path)).getImage();
        repaint();
    }

    public void changeState(boolean activeState, String path) {
        if (activeState != state) {
            setImage(path);
            this.state = activeState;
        }
    }
}
