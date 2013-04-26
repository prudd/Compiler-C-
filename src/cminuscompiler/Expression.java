package cminuscompiler;

import java.io.BufferedWriter;

/**
 *
 * @author Paul Marshall
 */
abstract public class Expression {
    abstract public void print();
    abstract public void printFile(BufferedWriter bw);
    abstract public void printASTFile(BufferedWriter bw, int level);
}
