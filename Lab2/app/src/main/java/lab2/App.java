package lab2;


public class App {
    public static void main(String[] args) {
        Context context = new Context();
        CommandFactory factory = new CommandFactory();

        Command pushCommand = factory.createCommand("PUSH", "10.0");
        pushCommand.execute(context);  // Добавляет 10.0 в стек

        Command popCommand = factory.createCommand("POP");
        popCommand.execute(context);   // Удаляет верхний элемент стека
    }
}
