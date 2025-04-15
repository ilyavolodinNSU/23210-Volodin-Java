package factory.GUI;

import javax.swing.*;
import java.awt.*;
import factory.infrastructure.FactoryStateData;

public class MainFrame {
    private final JFrame frame;
    private final JLabel soldCarsLabel;
    private final ImagePanel suppMPanel;
    private final ImagePanel suppBPanel;
    private final ImagePanel suppsAPanel;
    private final ImagePanel workersPanel;
    private final ImagePanel dealersPanel;
    private final DataBasePanel storageMPanel;
    private final DataBasePanel storageBPanel;
    private final DataBasePanel storageAPanel;
    private final DataBasePanel storageCPanel;

    public MainFrame() {
        frame = new JFrame("Фабрика");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 700);
        frame.setLocationRelativeTo(null);
        frame.setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        mainPanel.setBackground(Color.WHITE);

        JPanel menuPanel = new MenuPanel();
        // menuPanel.setPreferredSize(new Dimension(200, 600));

        JPanel factoryPanel = new JPanel(new GridBagLayout());
        factoryPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        factoryPanel.setBorder(BorderFactory.createLineBorder(new Color(5, 42, 117), 5, true));
        factoryPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        soldCarsLabel = new JLabel("Продано машин: 0");
        soldCarsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        menuPanel.add(soldCarsLabel, BorderLayout.CENTER);

        suppMPanel = new ImagePanel("/supplier.png");
        suppMPanel.setText("SuppM");
        suppBPanel = new ImagePanel("/supplier.png");
        suppBPanel.setText("SuppB");
        suppsAPanel = new ImagePanel("/supplier.png");
        suppsAPanel.setText("SuppsA");
        workersPanel = new ImagePanel("/worker.png");
        workersPanel.setText("W");
        dealersPanel = new ImagePanel("/dealer.png");
        dealersPanel.setText("D");

        storageMPanel = new DataBasePanel("/storage.gif", "/storage.png");
        storageBPanel = new DataBasePanel("/storage.gif", "/storage.png");
        storageAPanel = new DataBasePanel("/storage.gif", "/storage.png");
        storageCPanel = new DataBasePanel("/big_storage.gif", "/big_storage.png");

        gbc.gridx = 0;
        gbc.gridy = 1;
        factoryPanel.add(suppMPanel, gbc);
        gbc.gridy = 2;
        factoryPanel.add(suppBPanel, gbc);
        gbc.gridy = 3;
        factoryPanel.add(suppsAPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        factoryPanel.add(storageMPanel, gbc);
        gbc.gridy = 2;
        factoryPanel.add(storageBPanel, gbc);
        gbc.gridy = 3;
        factoryPanel.add(storageAPanel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        factoryPanel.add(workersPanel, gbc);

        gbc.gridx = 3;
        factoryPanel.add(storageCPanel, gbc);

        gbc.gridx = 4;
        factoryPanel.add(dealersPanel, gbc);

        mainPanel.add(menuPanel, BorderLayout.NORTH);
        mainPanel.add(factoryPanel, BorderLayout.CENTER);

        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    }

    public void render(FactoryStateData dto) {
        soldCarsLabel.setText("Продано машин: " + dto.getSoldCarsCounter());

        storageMPanel.setLabelValue(dto.getMotorStorageSize());
        storageBPanel.setLabelValue(dto.getBodyStorageSize());
        storageAPanel.setLabelValue(dto.getAccessoryStorageSize());
        storageCPanel.setLabelValue(dto.getCarStorageSize());

        suppMPanel.changeState(
            dto.isActiveSuppsMotors(),
            dto.isActiveSuppsMotors() ? "/supplier.gif" : "/supplier.png"
        );
        suppBPanel.changeState(
            dto.isActiveSuppsBodies(),
            dto.isActiveSuppsBodies() ? "/supplier.gif" : "/supplier.png"
        );
        suppsAPanel.changeState(
            dto.isActiveSuppsAccessories(),
            dto.isActiveSuppsAccessories() ? "/supplier.gif" : "/supplier.png"
        );
        workersPanel.changeState(
            dto.isActiveWorkers(),
            dto.isActiveWorkers() ? "/worker.gif" : "/worker.png"
        );
        dealersPanel.changeState(
            dto.isActiveDealers(),
            dto.isActiveDealers() ? "/dealer.gif" : "/dealer.png"
        );

        frame.repaint();
    }
}
