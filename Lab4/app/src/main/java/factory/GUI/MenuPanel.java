package factory.GUI;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import factory.infrastructure.FactoryManager;

public class MenuPanel extends JPanel {
    private JSlider sliderM;
    private JSlider sliderB;
    private JSlider sliderA;
    private JPanel sliderPanel = new JPanel();
    private FactoryManager factoryManager;

    public MenuPanel(FactoryManager factoryManager) {
        this.factoryManager = factoryManager;
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

        sliderM.addChangeListener(e -> {
            if (!sliderM.getValueIsAdjusting()) {
                factoryManager.changeMotorSuppsDelay(sliderM.getValue());
            }
        });

        sliderB.addChangeListener(e -> {
            if (!sliderB.getValueIsAdjusting()) {
                factoryManager.changeBodySuppsDelay(sliderB.getValue());
            }
        });

        sliderA.addChangeListener(e -> {
            if (!sliderA.getValueIsAdjusting()) {
                factoryManager.changeAccessorySuppsDelay(sliderA.getValue());
            }
        });

        sliderPanel.add(labelM);
        sliderPanel.add(sliderM);
        sliderPanel.add(labelB);
        sliderPanel.add(sliderB);
        sliderPanel.add(labelA);
        sliderPanel.add(sliderA);

        this.add(sliderPanel, BorderLayout.WEST);
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
}