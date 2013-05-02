package cminuscompiler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import lowlevel.Data;
import lowlevel.CodeItem;
import lowlevel.Function;

/**
 *
 * @author Paul Marshall
 */
public class FunctionDeclaration extends Declaration {
    private int type;
    private String id;
    private LinkedList<Parameter> parameters = new LinkedList<>();
    private CompoundStatement compoundStatement;

    public void addParameter(Parameter parameter) {
        this.parameters.add(parameter);
    }

    public LinkedList<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(LinkedList<Parameter> parameters) {
        this.parameters = parameters;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CompoundStatement getCompoundStatement() {
        return compoundStatement;
    }

    public void setCompoundStatement(CompoundStatement compoundStatement) {
        this.compoundStatement = compoundStatement;
    }

    @Override
    public CodeItem genCode(){
        Function func = new Function(type, id);
        func.createBlock0();
        func.setCurrBlock(func.getFirstBlock());
        return compoundStatement.genCode();
    }
    
    @Override
    public void print(int level) {
        Iterator<Parameter> iterator = this.parameters.iterator();

        for (int i = 0; i < level; i++) {
            System.out.print("\t");
        }

        System.out.print(this.type + " " + this.id + "(");

        if (this.parameters.size() == 0) {
            System.out.print("void");
        } else {
            while (iterator.hasNext()) {
                iterator.next().print();
                if (iterator.hasNext()) {
                    System.out.print(", ");
                }
            }
        }

        System.out.println(")");
        this.compoundStatement.print(level + 1);
    }

    @Override
    public void printFile(BufferedWriter bw, int level) {
        try {
            Iterator<Parameter> iterator = this.parameters.iterator();

            for (int i = 0; i < level; i++) {
                bw.append("\t");
            }

            bw.append(this.type + " " + this.id + "(");

            if (this.parameters.size() == 0) {
                bw.append("void");
            } else {
                while (iterator.hasNext()) {
                    iterator.next().printFile(bw);

                    if (iterator.hasNext()) {
                        bw.append(", ");
                    }
                }
            }
            bw.append(")\n");
            this.compoundStatement.printFile(bw, level + 1);
        } catch (IOException ex) {
            System.err.print("Error in FunDecl printFile");
        }
    }

    @Override
    public void printASTFile(BufferedWriter bw, int level) {
        try {
            Iterator<Parameter> iterator = this.parameters.iterator();
            for (int i = 0; i < level; i++) {
                bw.append("\t");
            }
            bw.append("Function Declaration(" + this.type + " " + this.id + ")\n");
            bw.append("\t" + "Parameters\n");

            if (this.parameters.size() == 0) {
                bw.append("void");
            } else {
                while (iterator.hasNext()) {
                    iterator.next().printASTFile(bw, level + 1);
                }
            }
            for (int i = 0; i < level; i++) {
                bw.append("\t");
            }
            bw.append("Body\n");
            this.compoundStatement.printASTFile(bw, level + 1);
        } catch (IOException ex) {
            System.err.print("Error in FunDecl printASTFile");
        }
    }

    public FunctionDeclaration(int type, String id, LinkedList<Parameter> parameters,
            CompoundStatement compoundStatement) {
        this.type = type;
        this.id = id;
        this.parameters = parameters;
        this.compoundStatement = compoundStatement;
    }

    public FunctionDeclaration(int type, String id,
            CompoundStatement compoundStatement) {
        this.type = type;
        this.id = id;
        this.compoundStatement = compoundStatement;
    }

    public FunctionDeclaration(int type, String id) {
        this(type, id, null);
    }

    public FunctionDeclaration() {
        this(Data.TYPE_VOID, null, null);
    }
}
