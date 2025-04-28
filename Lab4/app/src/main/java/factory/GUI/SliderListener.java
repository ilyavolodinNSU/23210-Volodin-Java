package factory.GUI;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderListener implements ChangeListener {
    private String sliderName;

    public SliderListener(String sliderName) {
        this.sliderName = sliderName;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        int value = source.getValue();
        System.out.println(sliderName + " value: " + value);
    }
}
