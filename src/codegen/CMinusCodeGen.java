package codegen;

import cminuscompiler.Program;
import lowlevel.CodeItem;
import lowlevel.Data;
import cminuscompiler.Declaration;
import java.util.List;
import java.util.Scanner;

public class CMinusCodeGen implements CodeGen {
    private String astFileName;
    
    public CMinusCodeGen(String astFileName){
        this.astFileName = astFileName;
    }
    
    public CodeItem generateLLCode(){
        Scanner scan = new Scanner(astFileName);
        String term = scan.next();
        
        return new Data();
    }
    
    private CodeItem genCodeProgram(){
        
    }
    
    private CodeItem genCodeDeclaration(){
        
    }
    
    private CodeItem genCodeVariableDeclaration(){
        
    }
    
    private CodeItem genCodeFunctionDeclaration(){
        
    }
    
    private CodeItem genCodeCompoundStatement(){
        
    }
    
    private CodeItem genCodeVarStatement(){
        
    }
    
    private CodeItem genCodeExpressionStatement(){
        
    }
    
    private CodeItem genCodeReturnStatement(){
        
    }
    
    private CodeItem genCodeIterationStatement(){
        
    }
    
    private CodeItem genCodeSelectionStatement(){
        
    }
    
    private CodeItem genCodeExpression(){
        
    }
    
    private CodeItem genCodeBinaryExpression(){
        
    }
    
    private CodeItem genCodeCallExpression(){
        
    }
    
    private CodeItem genCodeAssignmentExpression(){
        
    }
    
    private CodeItem genCodeNumExpression(){
        
    }
    
    private CodeItem genCodeVarExpression(){
        
    }
}
