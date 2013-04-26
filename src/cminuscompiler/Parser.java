package cminuscompiler;

import java.text.ParseException;

/**
 *
 * @author Paul Marshall
 */
abstract public class Parser {
    abstract public Program getProgram();
    abstract public void setProgram(Program newProgram);
    abstract public Program parse() throws ParseException;
    abstract public void printTree(String source);
}
