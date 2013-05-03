package cminuscompiler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import lowlevel.Function;

/**
 *
 * @author Paul Marshall
 */
public class CallExpression extends Expression {

    private String id;
    private LinkedList<Expression> args = new LinkedList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LinkedList<Expression> getArgs() {
        return args;
    }

    public void setArgs(LinkedList<Expression> args) {
        this.args = args;
    }
    
    @Override
    public void genCode(Function func){
        
    }
    
    @Override
    public int getRegNum() {
        return this.tempReg;
    }

    @Override
    public void print() {
        Iterator<Expression> expression = this.args.iterator();
        System.out.print(id + "(");

        while (expression.hasNext()) {
            expression.next().print();

            if (expression.hasNext()) {
                System.out.print(", ");
            }
        }

        System.out.print(")");
    }

    @Override
    public void printFile(BufferedWriter bw) {
        try {
            Iterator<Expression> expression = this.args.iterator();
            bw.append(id + "(");

            while (expression.hasNext()) {
                expression.next().printFile(bw);

                if (expression.hasNext()) {
                    bw.append(", ");
                }
            }

            bw.append(")");
        } catch (IOException ex) {
            System.err.print("Error in CallExp printFile");
        }
    }

    @Override
    public void printASTFile(BufferedWriter bw, int level) {
        try {
            String inset = "";
            for (int i = 0; i < level; i++) {
                inset += "\t";
            }

            bw.append(inset + "CallExpression(" + id + ")\n");
            bw.append(inset + "\t" + "Arguments\n");

            Iterator<Expression> expression = this.args.iterator();
            while (expression.hasNext()) {
                expression.next().printASTFile(bw, level);
            }
        } catch (IOException ex) {
            System.err.print("Error in CallExp printFile");
        }
    }

    public CallExpression(String id, LinkedList<Expression> args) {
        this.id = id;
        this.args = args;
    }

    public CallExpression(String id) {
        this.id = id;
    }

    public CallExpression() {
        this(null);
    }
}
