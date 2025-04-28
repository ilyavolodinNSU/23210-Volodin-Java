package lab2;

import java.util.Stack;
import java.util.Map;
import java.util.HashMap;

public class Context {
    private Stack<Double> stack = new Stack<>();
    private Map<String, Double> vars = new HashMap<>();

    public Stack<Double> getStack() {
        return stack;
    }

    public Map<String, Double> getVars() {
        return vars;
    }
}
