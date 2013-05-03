package cminuscompiler;

import compiler.CMinusCompiler;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import lowlevel.Function;

/**
 *
 * @author Paul Marshall
 */
public class CompoundStatement extends Statement {
    private LinkedList<Declaration> declarations = new LinkedList<>();
    private LinkedList<Statement> statements = new LinkedList<>();
    
    public void addDeclaration(Declaration declaration) {
        this.declarations.add(declaration);
    }
    
    public void addStatement(Statement statement) {
        this.statements.add(statement);
    }

    public LinkedList<Declaration> getDeclarations() {
        return declarations;
    }

    public void setDeclarations(LinkedList<Declaration> declarations) {
        this.declarations = declarations;
    }

    public LinkedList<Statement> getStatements() {
        return statements;
    }

    public void setStatements(LinkedList<Statement> statements) {
        this.statements = statements;
    }
    
    @Override
    public void genCode(Function func){
        HashMap symbolTable = func.getTable();
        for(Declaration d : declarations){
            symbolTable.put(((VariableDeclaration) d).getId(),
                            func.getNewRegNum());
        }
        for(Statement s : statements){
            s.genCode(func);
        }
    }
    
    @Override
    public void print(int level) {
        Iterator<Declaration> declaration = this.declarations.iterator();
        Iterator<Statement> statement = this.statements.iterator();
        String inset = "";
        
        for (int i = 0; i < level - 1; i++) {
            inset += "\t";
        }
        
        System.out.println(inset + "{");
        
        while (declaration.hasNext()) {
            declaration.next().print(level);
            System.out.println();
        }
        
        while (statement.hasNext()) {
            statement.next().print(level);
            System.out.println();
        }
        
        System.out.println(inset + "}");
    }
    
    @Override
    public void printFile(BufferedWriter bw, int level) {
        try {
        Iterator<Declaration> declaration = this.declarations.iterator();
        Iterator<Statement> statement = this.statements.iterator();
            String inset = "";

            for (int i = 0; i < level - 1; i++) {
                inset += "\t";
            }

            bw.append(inset + "{\n");

            while (declaration.hasNext()) {
                declaration.next().printFile(bw, level);
            }

            while (statement.hasNext()) {
                statement.next().printFile(bw, level);
                bw.newLine();
            }

            bw.append(inset + "}\n");
        } catch (IOException ex) {
            System.err.print("Error in CompStmt printFile");
        }
    }
    
    public void printASTFile(BufferedWriter bw, int level) {
        try {
            Iterator<Declaration> declaration = this.declarations.iterator();
            Iterator<Statement> statement = this.statements.iterator();

            String inset = "";

            for (int i = 0; i < level; i++) {
                inset += "\t";
            }

            bw.append(inset + "CompoundStatment\n");
            while (declaration.hasNext()) {
                declaration.next().printASTFile(bw, level + 1);
            }

            while (statement.hasNext()) {
                statement.next().printASTFile(bw, level + 1);
            }

        } catch (IOException ex) {
            System.err.print("Error in CompStmt printFile");
        }
    }
    
    public CompoundStatement(LinkedList<Declaration> declarations,
            LinkedList<Statement> statements) {
        this.declarations = declarations;
        this.statements = statements;
    }
    
    public CompoundStatement() {
        // Do nothing.
    }
}
