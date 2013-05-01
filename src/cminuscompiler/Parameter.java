package cminuscompiler;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 *
 * @author Paul Marshall
 */
public class Parameter {

    private String id;
    private boolean isArray;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isIsArray() {
        return isArray;
    }

    public void setIsArray(boolean isArray) {
        this.isArray = isArray;
    }

    public void print() {
        System.out.print("int " + this.id);

        if (this.isArray) {
            System.out.print("[]");
        }
    }

    public void printFile(BufferedWriter bw) {
        try {
            bw.append("int " + this.id);

            if (this.isArray) {
                bw.append("[]");
            }
        } catch (IOException ex) {
            System.err.print("Error in Parameter printFile");
        }
    }

    public void printASTFile(BufferedWriter bw, int level) {
        try {
            String inset = "";
            for (int i = 0; i < level - 1; i++) {
                inset += "\t";
            }

            bw.append(inset + "\t" + "int " + this.id);

            if (this.isArray) {
                bw.append("[]");
            }
            bw.append("\n");
        } catch (IOException ex) {
            System.err.print("Error in Parameter printASTFile");
        }
    }

    public Parameter(String id, boolean isArray) {
        this.id = id;
        this.isArray = isArray;
    }

    public Parameter(String id) {
        this(id, false);
    }

    public Parameter() {
        this(null, false);
    }
}
