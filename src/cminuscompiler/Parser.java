package cminuscompiler;

import java.text.ParseException;

public interface Parser {
    public Program parse() throws ParseException;
    public void printTree(String source);
}
