package lab2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import lab2.commands.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class CommandsTest {

    private Context context;

    @BeforeEach
    void setUp() {
        context = new Context();
    }

    // DEFINE

    @Test // работоспособность
    void testDefine() throws CommandException {
        Command command = new Define();
        command.execute(context, "a", "10");

        assertEquals(10.0, context.getVars().get("a"));
    }

    @Test // количество аргументов
    void testDefine_NumArgs() {
        Command command = new Define();
        assertThrows(CommandException.class, () ->
                command.execute(context, "a", "b", "c")
        );
    }

    @Test // значения аргументов
    void testDefine_ValueArgs() {
        Command command = new Define();
        assertThrows(CommandException.class, () ->
                command.execute(context, "10", "a")
        );
    }

    // PUSH
    
    @Test // работоспособность
    void testPush() throws CommandException {
        context.getVars().put("a", 10.0);

        Command commandPush = new Push();
        commandPush.execute(context, "a");
        assertEquals(1, context.getStack().size());
        assertEquals(10.0, context.getStack().peek());
    }

    @Test // количество аргументов
    void testPush_NumArgs() {
        Command command = new Push();
        assertThrows(CommandException.class, () ->
                command.execute(context, "a", "b")
        );
    }

    @Test // существование переменной
    void testPush_ValueArgs() {
        Command command = new Push();
        assertThrows(CommandException.class, () ->
                command.execute(context, "a")
        );
    }

    // POP

    @Test // работоспособность
    void testPop() throws CommandException {
        context.getStack().push(10.0);
        context.getStack().push(20.0);

        Command command = new Pop();
        command.execute(context);

        assertEquals(1, context.getStack().size());
        assertEquals(10.0, context.getStack().peek());
    }

    @Test // количество аргументов
    void testPop_NumArgs() {
        Command command = new Pop();
        assertThrows(CommandException.class, () ->
                command.execute(context, "a")
        );
    }

    @Test // пустой стек
    void testPop_EmptygetStack() {
        Command command = new Pop();
        assertThrows(CommandException.class, () ->
                command.execute(context)
        );
    }

    // PRINT

    @Test // работоспособность
    void testPrint() throws CommandException {
        context.getStack().push(10.0);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(bos));

        try {
            Command command = new Print();
            command.execute(context);

            String output = bos.toString().trim();
            assertEquals("10.0", output);
        } finally {
            System.setOut(oldOut);
        }
    }

    @Test // количество аргументов
    void testPrint_NumArgs() {
        Command command = new Print();
        assertThrows(CommandException.class, () ->
                command.execute(context, "a")
        );
    }

    @Test // пустой стек
    void testPrint_EmptygetStack() throws CommandException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(bos));

        try {
            Command command = new Print();
            command.execute(context);

            String output = bos.toString().trim();
            assertEquals("Стек пуст", output);
        } finally {
            System.setOut(oldOut);
        }
    }

    // PLUS

    @Test
    void testPlus() throws CommandException {
        context.getStack().push(2.0);
        context.getStack().push(3.0);

        Command command = new Plus();
        command.execute(context);
        assertEquals(5.0, context.getStack().peek());
    }

    @Test
    void testPlus_NotEnough() {
        context.getStack().push(10.0);
        Command command = new Plus();
        assertThrows(CommandException.class, () ->
                command.execute(context)
        );
    }

    @Test
    void testPlus_NumArgs() {
        Command command = new Plus();
        assertThrows(CommandException.class, () ->
            command.execute(context, "a")
        );
    }

    // MINUS

    @Test
    void testMinus() throws CommandException {
        context.getStack().push(2.0);
        context.getStack().push(3.0);

        Command command = new Minus();
        command.execute(context);
        assertEquals(1.0, context.getStack().peek());
    }

    @Test
    void testMinus_NotEnough() {
        context.getStack().push(10.0);
        Command command = new Minus();
        assertThrows(CommandException.class, () ->
                command.execute(context)
        );
    }

    @Test
    void testMinus_NumArgs() {
        Command command = new Minus();
        assertThrows(CommandException.class, () ->
            command.execute(context, "a")
        );
    }

    // DIV

    @Test
    void testDiv() throws CommandException {
        context.getStack().push(5.0);
        context.getStack().push(10.0);

        Command command = new Div();
        command.execute(context);
        assertEquals(2.0, context.getStack().peek());
    }

    @Test
    void testDiv_NotEnough() {
        context.getStack().push(10.0);
        Command command = new Div();
        assertThrows(CommandException.class, () ->
                command.execute(context)
        );
    }

    @Test
    void testDiv_ByZero() {
        context.getStack().push(0.0);
        context.getStack().push(10.0);
        Command command = new Div();
        assertThrows(CommandException.class, () ->
                command.execute(context)
        );
    }

    @Test
    void testDiv_NumArgs() {
        Command command = new Div();
        assertThrows(CommandException.class, () ->
            command.execute(context, "a")
        );
    }

    // MULT

    @Test
    void testMult() throws CommandException {
        context.getStack().push(2.0);
        context.getStack().push(3.0);

        Command command = new Mult();
        command.execute(context);
        assertEquals(6.0, context.getStack().peek());
    }

    @Test
    void testMult_NotEnough() {
        context.getStack().push(10.0);
        Command command = new Mult();
        assertThrows(CommandException.class, () ->
                command.execute(context)
        );
    }

    @Test
    void testMult_NumArgs() {
        Command command = new Mult();
        assertThrows(CommandException.class, () ->
            command.execute(context, "a")
        );
    }

    // SQRT

    @Test
    void testSqrt() throws CommandException {
        context.getStack().push(16.0);
        Command command = new Sqrt();
        command.execute(context);
        assertEquals(4.0, context.getStack().peek());
    }

    @Test
    void testSqrt_Negative() {
        context.getStack().push(-9.0);
        Command command = new Sqrt();
        assertThrows(CommandException.class, () ->
            command.execute(context)
        );
    }

    @Test
    void testSqrt_EmptyStack() {
        Command command = new Sqrt();
        assertThrows(CommandException.class, () ->
            command.execute(context)
        );
    }

    @Test
    void testSqrt_NumArgs() {
        Command command = new Sqrt();
        assertThrows(CommandException.class, () ->
            command.execute(context, "a")
        );
    }

}