package lab2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import lab2.commands.Print;

public class FactoryTest {
    @Test
    void testFactoryPush() throws Exception {
        CommandFactory factory = new CommandFactory("/commands.properties");
        Command command = factory.createCommand("PRINT");

        assertInstanceOf(Print.class, command);
    }
}
