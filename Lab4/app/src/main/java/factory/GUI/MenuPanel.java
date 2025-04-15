package factory.GUI;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    private JSlider sliderM;
    private JSlider sliderB;
    private JSlider sliderA;
    private JPanel sliderPanel = new JPanel();

    public MenuPanel() {
        sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.Y_AXIS));
        setLayout(new BorderLayout());

        setBackground(Color.WHITE);
        sliderPanel.setBackground(Color.WHITE);

        sliderM = createMinimalSlider();
        sliderB = createMinimalSlider();
        sliderA = createMinimalSlider();

        JLabel labelM = new JLabel("SuppM delay:");
        JLabel labelB = new JLabel("SuppB delay:");
        JLabel labelA = new JLabel("SuppsA delay:");

        sliderM.setBackground(Color.WHITE);
        sliderB.setBackground(Color.WHITE);
        sliderA.setBackground(Color.WHITE);

        sliderPanel.add(labelM);
        sliderPanel.add(sliderM);
        sliderPanel.add(labelB);
        sliderPanel.add(sliderB);
        sliderPanel.add(labelA);
        sliderPanel.add(sliderA);

        this.add(sliderPanel, BorderLayout.WEST);

        // Добавление слушателей
        sliderM.addChangeListener(new SliderListener("SuppM delay"));
        sliderB.addChangeListener(new SliderListener("SuppB delay"));
        sliderA.addChangeListener(new SliderListener("SuppsA delay"));
    }

    private JSlider createMinimalSlider() {
        JSlider slider = new JSlider(0, 10, 5);
        slider.setMajorTickSpacing(0);
        slider.setMinorTickSpacing(0);
        slider.setPaintTicks(false);
        slider.setPaintLabels(false);
        slider.setOpaque(false);
        return slider;
    }

    public JSlider getSliderM() {
        return sliderM;
    }

    public JSlider getSliderB() {
        return sliderB;
    }

    public JSlider getSliderA() {
        return sliderA;
    }
}
