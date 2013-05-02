package cminuscompiler;

import java.io.BufferedWriter;
import lowlevel.CodeItem;

/**
 *
 * @author Paul Marshall
 */
abstract public class Statement {
    abstract public CodeItem genCode();
    abstract public void print(int level);
    abstract public void printFile(BufferedWriter bw, int level);
    abstract public void printASTFile(BufferedWriter bw, int level);
}
