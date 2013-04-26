package cminuscompiler;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;

/**
 *
 *
 * @author Paul Marshall
 * @version 1.0 File: CMinusCompiler.java Created: Feb 3, 2012 Summery of
 * Modifications: - None
 *
 * Description:
 *
 */
public class CMinusCompiler {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ParseException {
        String[] filename = {"ComprehensiveTest.c", "SelectionSort.c", "testcode.c", "test5.c", "ben.c"};
        //PrintWriter outFile = new PrintWriter(new FileWriter("output.txt"));
        CMinusParser parser;

        for (int i = 0; i < filename.length; i++) {
            //outFile.println(filename[i]);
            //outFile.println();

            parser = new CMinusParser(filename[i]);
            parser.parse();
            parser.printTree(filename[i]);

            //outFile.println();
        }

        //outFile.close();
    }
}
