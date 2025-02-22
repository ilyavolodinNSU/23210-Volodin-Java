package lab3.viewer;

import lab3.model.Model;

public class View {
    public void render(Model model) {
        for (int i = 0; i < model.getField().getHeight(); i++) {
            for (int j = 0; j < model.getField().getWidth(); j++) {
                System.out.print(model.getField().getCell(j, i) + " ");
            }
            System.out.println();
        }
        System.out.println("\n");
    }
}
