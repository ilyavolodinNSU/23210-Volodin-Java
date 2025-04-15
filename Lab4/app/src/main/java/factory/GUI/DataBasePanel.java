package factory.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DataBasePanel extends JPanel {
    private final Image activeStateImage;
    private final Image deactiveStateImage;
    private Image curImage;
    private long size;
    private boolean isAnimating = false;
    private boolean isHovered = false;

    public DataBasePanel(String pathActiveState, String pathDeactiveState) {
        this.activeStateImage = new ImageIcon(getClass().getResource(pathActiveState)).getImage();
        this.deactiveStateImage = new ImageIcon(getClass().getResource(pathDeactiveState)).getImage();

        this.curImage = deactiveStateImage;

        setPreferredSize(new Dimension(100, 100));
        setLayout(null);
        setBackground(Color.WHITE);
        setOpaque(true); 

        size = 0;

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

        if (!isHovered) {
            g2.drawImage(curImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            
            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, getWidth(), getHeight());

            String text = String.valueOf(size);
            g2.setFont(new Font("Arial", Font.BOLD, 20));
            g2.setColor(Color.BLACK);

            FontMetrics fm = g2.getFontMetrics();
            int textWidth = fm.stringWidth(text);
            int textHeight = fm.getHeight();

            int x = (getWidth() - textWidth) / 2;
            int y = (getHeight() + textHeight) / 2 - fm.getDescent();

            g2.drawString(text, x, y);
        }

        g2.dispose();
    }

    public void setLabelValue(long value) {
        if (size != value) {
            size = value;
            runAnimation();
            repaint();
        }
    }

    public void runAnimation() {
        if (isAnimating) return;

        isAnimating = true;
        curImage = activeStateImage;
        repaint();

        Timer timer = new Timer(1000, e -> {
            curImage = deactiveStateImage;
            isAnimating = false;
            repaint();
        });

        timer.setRepeats(false);
        timer.start();
    }
}
