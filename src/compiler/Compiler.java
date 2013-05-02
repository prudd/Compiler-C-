package compiler;

import java.text.ParseException;

public interface Compiler {
    public void compile(String filePrefix) throws ParseException;
}