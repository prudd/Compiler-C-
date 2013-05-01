package cminuscompiler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.LinkedList;

/**
 *
 * @author Paul Marshall
 */
public class CMinusParser extends Parser {

    private CMinusScanner scanner;
    private Program program;

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program newProgram) {
        this.program = newProgram;
        
    }
    public Program parse() throws ParseException {
        setProgram(parseProgram());
        return program;
    }

    private Program parseProgram() throws ParseException {
        Program program = new Program();
        switch (scanner.viewToken().getType()) {
            //First set INT_TOKEN, VOID_TOKEN        
            case INT_TOKEN:
            case VOID_TOKEN:
                //Being parsing by parsing Decl-List
                program.setDeclarationList(parseDeclList());
                match(Token.TokenType.EOF_TOKEN);
                return program;
            default:
                String message = "Expected an INT_TOKEN or VOID_TOKEN. Got " + scanner.viewToken().toString() + " in parseProgram";
                throw new ParseException(message, 0);
        }
    }

    private LinkedList<Declaration> parseDeclList() throws ParseException {
        LinkedList<Declaration> declList = new LinkedList<>();
        switch (scanner.viewToken().getType()) {
            //First set INT_TOKEN, VOID_TOKEN
            case INT_TOKEN:
            case VOID_TOKEN:
                declList.add(parseDecl());
            //Follow set EOF_TOKEN
            case EOF_TOKEN:
                break;
            default:
                String message = "Expected an INT_TOKEN, VOID_TOKEN or EOF_TOKEN. Got " + scanner.viewToken().toString() + " in parseDeclList";
                throw new ParseException(message, 0);
        }
        if (scanner.viewToken().getType() != Token.TokenType.EOF_TOKEN) {
            declList.addAll(parseDeclList());
        }
        return declList;
    }

    private Declaration parseDecl() throws ParseException {
        String id;
        Declaration decl;
        switch (scanner.viewToken().getType()) {
            //First Set INT_TOKEN, VOID_TOKEN
            case INT_TOKEN:
                //Goes to Decl'
                match(Token.TokenType.INT_TOKEN);
                id = (String) scanner.viewToken().getData();
                match(Token.TokenType.ID_TOKEN);
                decl = parseDecl1(id);
                break;
            case VOID_TOKEN:
                //Goes to Fun-Decl
                match(Token.TokenType.VOID_TOKEN);
                id = (String) scanner.viewToken().getData();
                match(Token.TokenType.ID_TOKEN);
                decl = parseFunDecl();
                ((FunctionDeclaration) decl).setId(id);
                ((FunctionDeclaration) decl).setType("void");
                break;
            //Not in the First Set
            default:
                String message = "Expected an INT_TOKEN, VOID_TOKEN. Got " + scanner.viewToken().toString() + " in parseDecl";
                throw new ParseException(message, 0);
        }
        switch (scanner.viewToken().getType()) {
            //Follow set INT_TOKEN, VOID_TOKEN, EOF_TOKEN
            case INT_TOKEN:
            case VOID_TOKEN:
            case EOF_TOKEN:
                return decl;
            default:
                String message = "Expected an INT_TOKEN, VOID_TOKEN or EOF_TOKEN. Got " + scanner.viewToken().toString() + " in parseDecl";
                throw new ParseException(message, 0);
        }
    }

    private Declaration parseDecl1(String id) throws ParseException {
        Declaration decl;
        switch (scanner.viewToken().getType()) {
            //First set RPAREN_TOKEN, RBRACKET_TOKEN, END_STATMENT_TOKEN
            case END_STATEMENT_TOKEN:
            case LBRACKET_TOKEN:
                //Goes to Var-Decl
                decl = parseVarDecl();
                ((VariableDeclaration) decl).setId(id);
                break;
            case LPAREN_TOKEN:
                decl = parseFunDecl();
                ((FunctionDeclaration) decl).setId(id);
                ((FunctionDeclaration) decl).setType("int");
                break;
            default:
                String message = "Expected an INT_TOKEN, VOID_TOKEN. Got " + scanner.viewToken().toString() + " in parseDecl1";
                throw new ParseException(message, 0);
        }
        switch (scanner.viewToken().getType()) {
            //Follow set INT_TOKEN, VOID_TOKEN, EOF_TOKEN
            case INT_TOKEN:
            case VOID_TOKEN:
            case EOF_TOKEN:
                return decl;
            default:
                String message = "Expected an INT_TOKEN, VOID_TOKEN or EOF_TOKEN. Got " + scanner.viewToken().toString() + " in parseDecl1";
                throw new ParseException(message, 0);
        }
    }

    private VariableDeclaration parseVarDecl() throws ParseException {
        VariableDeclaration varDecl = new VariableDeclaration();
        switch (scanner.viewToken().getType()) {
            //First set LBRACKET_TOKEN, END_STATEMENT_TOKEN
            case LBRACKET_TOKEN:
                //Parses [NUM];
                match(Token.TokenType.LBRACKET_TOKEN);
                varDecl.setArraySize(((Integer) scanner.viewToken().getData()).intValue());
                match(Token.TokenType.NUM_TOKEN);
                match(Token.TokenType.RBRACKET_TOKEN);
            case END_STATEMENT_TOKEN:
                //Parses ;
                match(Token.TokenType.END_STATEMENT_TOKEN);
                break;
            default:
                String message = "Expected an INT_TOKEN, VOID_TOKEN. Got " + scanner.viewToken().toString() + " in parseVarDecl";
                throw new ParseException(message, 0);
        }
        switch (scanner.viewToken().getType()) {
            //Follow set INT_TOKEN, VOID_TOKEN, END_STATEMENT_TOKEN, IF_TOKEN, WHILE_TOKEN, NUM_TOKEN, RPAREN_TOKEN, ID_TOKEN, RBRACE_TOKEN, LBRACE_TOKEN
            case INT_TOKEN:
            case VOID_TOKEN:
            case END_STATEMENT_TOKEN:
            case IF_TOKEN:
            case WHILE_TOKEN:
            case NUM_TOKEN:
            case RPAREN_TOKEN:
            case ID_TOKEN:
            case RBRACE_TOKEN:
            case LBRACE_TOKEN:
                return varDecl;
            default:
                String message = "Expected an INT_TOKEN, VOID_TOKEN, END_STATEMENT_TOKEN, IF_TOKEN, WHILE_TOKEN, NUM_TOKEN, RPAREN_TOKEN, ID_TOKEN, RBRACE_TOKEN or LBRACE_TOKEN. Got " + scanner.viewToken().toString() + " in parseVarDecl";
                throw new ParseException(message, 0);
        }
    }

    private FunctionDeclaration parseFunDecl() throws ParseException {
        FunctionDeclaration funDecl = new FunctionDeclaration();
        //First set RPAREN_TOKEN
        if (scanner.viewToken().getType() == Token.TokenType.LPAREN_TOKEN) {
            //Goes to parseParams
            match(Token.TokenType.LPAREN_TOKEN);
            funDecl.setParameters(parseParams());
            match(Token.TokenType.RPAREN_TOKEN);
            funDecl.setCompoundStatement(parseCompStmt());
        } else {
            String message = "Expected a LPAREN_TOKEN. Got " + scanner.viewToken().toString() + " in parseFunDecl";
            throw new ParseException(message, 0);
        }
        switch (scanner.viewToken().getType()) {
            //Follow set INT_TOKEN, VOID_TOKEN, EOF_TOKEN
            case INT_TOKEN:
            case VOID_TOKEN:
            case EOF_TOKEN:
                return funDecl;
            default:
                String message = "Expected an INT_TOKEN, VOID_TOKEN, EOF_TOKEN. Got " + scanner.viewToken().toString() + " in parseFunDecl";
                throw new ParseException(message, 0);
        }
    }

    private LinkedList<Parameter> parseParams() throws ParseException {
        LinkedList<Parameter> paramList = new LinkedList<>();
        switch (scanner.viewToken().getType()) {
            //First set INT_TOKEN, VOID_TOKEN
            case INT_TOKEN:
                //Goes to parseParamList
                paramList = parseParamList();
                break;
            case VOID_TOKEN:
                //Parse VOID_TOKEN
                match(Token.TokenType.VOID_TOKEN);
                break;
            default:
                String message = "Expected an INT_TOKEN, VOID_TOKEN. Got " + scanner.viewToken().toString() + " in parseParams";
                throw new ParseException(message, 0);
        }
        switch (scanner.viewToken().getType()) {
            case RPAREN_TOKEN:
                return paramList;
            default:
                String message = "Expected a RPAREN_TOKEN. Got " + scanner.viewToken().toString() + " in parseParams";
                throw new ParseException(message, 0);
        }
    }

    private LinkedList<Parameter> parseParamList() throws ParseException {
        LinkedList<Parameter> paramList = new LinkedList<>();
        switch (scanner.viewToken().getType()) {
            case INT_TOKEN:
                //Goes to parseParam
                paramList.add(parseParam());
                break;
            default:
                String message = "Expected an INT_TOKEN. Got " + scanner.viewToken().toString() + " in parseParamList";
                throw new ParseException(message, 0);
        }
        switch (scanner.viewToken().getType()) {
            //Follow set of Parameter SEQUENCING_TOKEN, RPAREN_TOKEN
            case SEQUENCING_TOKEN:
                //Add more parameters
                match(Token.TokenType.SEQUENCING_TOKEN);
                paramList.addAll(parseParamList());
            case RPAREN_TOKEN:
                //Done
                return paramList;
            default:
                String message = "Expected a SEQUENCING_TOKEN OR RPAREN_TOKEN. Got " + scanner.viewToken().toString() + " in parseParamList";
                throw new ParseException(message, 0);
        }
    }

    private Parameter parseParam() throws ParseException {
        //Parses the regex intID([])?
        Parameter param = new Parameter();
        match(Token.TokenType.INT_TOKEN);
        param.setId((String) scanner.viewToken().getData());
        match(Token.TokenType.ID_TOKEN);
        if (scanner.viewToken().getType() == Token.TokenType.LBRACKET_TOKEN) {
            param.setIsArray(true);
            match(Token.TokenType.LBRACKET_TOKEN);
            match(Token.TokenType.RBRACKET_TOKEN);
        }
        switch (scanner.viewToken().getType()) {
            //Follow set RPAREN_TOKEN, SEQUENCING_TOKEN
            case RPAREN_TOKEN:
            case SEQUENCING_TOKEN:
                return param;
            default:
                String message = "Expected a RPAREN_TOKEN, SEQUENCING_TOKEN. Got " + scanner.viewToken().toString() + " in parseParam";
                throw new ParseException(message, 0);
        }
    }

    private CompoundStatement parseCompStmt() throws ParseException {
        CompoundStatement compStmt = new CompoundStatement();
        match(Token.TokenType.LBRACE_TOKEN);
        //First set of LocalDecl INT_TOKEN
        if (scanner.viewToken().getType() == Token.TokenType.INT_TOKEN) {
            //Goes to parseLocalDecl
            compStmt.setDeclarations(parseLocalDecl());
        }

        switch (scanner.viewToken().getType()) {
            //First set of StatementList NUM_TOKEN, LPAREN_TOKEN, ID_TOKEN, END_STATEMENT_TOKEN, LBRACE_TOKEN, IF_TOKEN, WHILE_TOKEN, RETURN_TOKEN
            case NUM_TOKEN:
            case LPAREN_TOKEN:
            case ID_TOKEN:
            case END_STATEMENT_TOKEN:
            case LBRACE_TOKEN:
            case IF_TOKEN:
            case WHILE_TOKEN:
            case RETURN_TOKEN:
                //Goes to parseStmtList
                compStmt.setStatements(parseStmtList());
                break;
        }
        match(Token.TokenType.RBRACE_TOKEN);
        switch (scanner.viewToken().getType()) {
            //Follow set EOF_TOKEN, INT_TOKEN, VOID_TOKEN, END_STATEMENT_TOKEN, IF_TOKEN, ELSE_TOKEN, WHILE_TOKEN, RETURN_TOKEN, NUM_TOKEN, RPAREN_TOKEN, ID_TOKEN, RBRACE_TOKEN, LBRACE_TOKEN
            case EOF_TOKEN:
            case INT_TOKEN:
            case VOID_TOKEN:
            case END_STATEMENT_TOKEN:
            case IF_TOKEN:
            case ELSE_TOKEN:
            case WHILE_TOKEN:
            case RETURN_TOKEN:
            case NUM_TOKEN:
            case RPAREN_TOKEN:
            case ID_TOKEN:
            case RBRACE_TOKEN:
            case LBRACE_TOKEN:
                return compStmt;
            default:
                String message = "Expected an EOF_TOKEN, INT_TOKEN, VOID_TOKEN, END_STATEMENT_TOKEN, IF_TOKEN, ELSE_TOKEN, WHILE_TOKEN, RETURN_TOKEN, NUM_TOKEN, RPAREN_TOKEN, ID_TOKEN, RBRACE_TOKEN, or LBRACE_TOKEN. Got " + scanner.viewToken().toString() + " in parseCompStmt";
                throw new ParseException(message, 0);
        }
    }

    private LinkedList<Declaration> parseLocalDecl() throws ParseException {
        LinkedList<Declaration> declList = new LinkedList<>();
        VariableDeclaration varDecl;
        String id;
        match(Token.TokenType.INT_TOKEN);
        id = (String) scanner.viewToken().getData();
        match(Token.TokenType.ID_TOKEN);
        varDecl = (VariableDeclaration) parseVarDecl();
        varDecl.setId(id);
        declList.add(varDecl);
        switch (scanner.viewToken().getType()) {
            //First set of LocalDecl
            case INT_TOKEN:
                //Add more LocalDecl
                declList.addAll(parseLocalDecl());
                break;
            //Follow set END_STATEMENT_TOKEN, IF_TOKEN, ELSE_TOKEN, WHILE_TOKEN, RETURN_TOKEN, NUM_TOKEN, RPAREN_TOKEN, ID_TOKEN, RBRACKET_TOKEN, LBRACKET_TOKEN
            case END_STATEMENT_TOKEN:
            case IF_TOKEN:
            case ELSE_TOKEN:
            case WHILE_TOKEN:
            case RETURN_TOKEN:
            case NUM_TOKEN:
            case RPAREN_TOKEN:
            case ID_TOKEN:
            case RBRACKET_TOKEN:
            case LBRACKET_TOKEN:
                //Done
                break;
            default:
                String message = "Expected an END_STATEMENT_TOKEN, IF_TOKEN, ELSE_TOKEN, WHILE_TOKEN, RETURN_TOKEN, NUM_TOKEN, RPAREN_TOKEN, ID_TOKEN, RBRACKET_TOKEN, or LBRACKET_TOKEN. Got " + scanner.viewToken().toString() + " in parseLocalDecl";
                throw new ParseException(message, 0);
        }
        return declList;
    }

    private LinkedList<Statement> parseStmtList() throws ParseException {
        LinkedList<Statement> stmtList = new LinkedList<>();
        switch (scanner.viewToken().getType()) {
            //First set NUM_TOKEN, LPAREN_TOKEN, ID_TOKEN, END_STATEMENT_TOKEN, IF_TOKEN, WHILE_TOKEN, RETURN_TOKEN, LBRACE_TOKEN
            case NUM_TOKEN:
            case LPAREN_TOKEN:
            case ID_TOKEN:
            case END_STATEMENT_TOKEN:
            case LBRACE_TOKEN:
            case IF_TOKEN:
            case WHILE_TOKEN:
            case RETURN_TOKEN:
                //Goes to parseStatement
                stmtList.add(parseStatement());
                break;
            default:
                String message = "Expected an NUM_TOKEN, LPAREN_TOKEN, ID_TOKEN, END_STATEMENT_TOKEN, IF_TOKEN, WHILE_TOKEN, RETURN_TOKEN, LBRACE_TOKEN. Got " + scanner.viewToken().toString() + " in parseStmtList";
                throw new ParseException(message, 0);
        }
        switch (scanner.viewToken().getType()) {
            case RBRACE_TOKEN:
                break;
            default:
                stmtList.addAll(parseStmtList());
        }
        return stmtList;
    }

    private Statement parseStatement() throws ParseException {
        Statement stmt = null;
        switch (scanner.viewToken().getType()) {
            //First set ID_TOKEN, LPAREN_TOKEN, NUM_TOKEN, END_STATEMENT_TOKEN, LBRACE_TOKEN, IF_TOKEN, WHILE_TOKEN, RETURN_TOKEN
            case ID_TOKEN:
            case LPAREN_TOKEN:
            case NUM_TOKEN:
            case END_STATEMENT_TOKEN:
                //Goes to parseExprStmt
                stmt = parseExprStmt();
                break;
            case LBRACE_TOKEN:
                //Goes to parseCompStmt
                stmt = parseCompStmt();
                break;
            case IF_TOKEN:
                //Goes to parseSelectStmt
                stmt = parseSelectStmt();
                break;
            case WHILE_TOKEN:
                //Goes to parseIterateStmt
                stmt = parseIterateStmt();
                break;
            case RETURN_TOKEN:
                //Goes to parseReturnStmt
                stmt = parseReturnStmt();
                break;
            default:
                String message = "Expected an ID_TOKEN, LPAREN_TOKEN, NUM_TOKEN, END_STATEMENT_TOKEN, LBRACE_TOKEN, IF_TOKEN, WHILE_TOKEN, RETURN_TOKEN. Got " + scanner.viewToken().toString() + " in parseStatement";
                throw new ParseException(message, 0);
        }
        switch (scanner.viewToken().getType()) {
            //Follow set ELSE_TOKEN, RBRACE_TOKEN, ID_TOKEN, LPAREN_TOKEN, NUM_TOKEN, END_STATEMENT_TOKEN, LBRACE_TOKEN, IF_TOKEN, WHILE_TOKEN, RETURN_TOKEN
            case ELSE_TOKEN:
            case RBRACE_TOKEN:
            case ID_TOKEN:
            case LPAREN_TOKEN:
            case NUM_TOKEN:
            case END_STATEMENT_TOKEN:
            case LBRACE_TOKEN:
            case IF_TOKEN:
            case WHILE_TOKEN:
            case RETURN_TOKEN:
                return stmt;
            default:
                String message = "Expected an ELSE_TOKEN, RBRACE_TOKEN, ID_TOKEN, LPAREN_TOKEN, NUM_TOKEN, END_STATEMENT_TOKEN, LBRACE_TOKEN, IF_TOKEN, WHILE_TOKEN, RETURN_TOKEN. Got " + scanner.viewToken().toString() + " in parseStatement";
                throw new ParseException(message, 0);
        }
    }

    private ExpressionStatement parseExprStmt() throws ParseException {
        ExpressionStatement exStmt = new ExpressionStatement();
        switch (scanner.viewToken().getType()) {
            //First set END_STATEMENT_TOKEN, ID_TOKEN, LPAREN_TOKEN, ID_TOKEN
            case ID_TOKEN:
            case LPAREN_TOKEN:
            case NUM_TOKEN:
                //Goes to parseExpr
                exStmt.setExpression(parseExpr());
            case END_STATEMENT_TOKEN:
                //Parses END_STATEMENT_TOKEN
                match(Token.TokenType.END_STATEMENT_TOKEN);
                break;
            default:
                String message = "Expected an ID_TOKEN, LPAREN_TOKEN, NUM_TOKEN, END_STATEMENT_TOKEN. Got " + scanner.viewToken().toString() + " in parseExprStmt";
                throw new ParseException(message, 0);
        }
        switch (scanner.viewToken().getType()) {
            //Follow set RBRACE_TOKEN, END_STATEMENT_TOKEN, IF_TOKEN, WHILE_TOKEN, RETURN_TOKEN, NUM_TOKEN, LPAREN_TOKEN, ID_TOKEN, ELSE_TOKEN, LBRACE_TOKEN
            case RBRACE_TOKEN:
            case END_STATEMENT_TOKEN:
            case IF_TOKEN:
            case WHILE_TOKEN:
            case RETURN_TOKEN:
            case NUM_TOKEN:
            case LPAREN_TOKEN:
            case ID_TOKEN:
            case ELSE_TOKEN:
            case LBRACE_TOKEN:
                return exStmt;
            default:
                String message = "Expected a RBRACE_TOKEN, END_STATEMENT_TOKEN, IF_TOKEN, WHILE_TOKEN, RETURN_TOKEN, NUM_TOKEN, LPAREN_TOKEN, ID_TOKEN, ELSE_TOKEN, LBRACE_TOKEN. Got " + scanner.viewToken().toString() + " in parseExprStmt";
                throw new ParseException(message, 0);
        }
    }

    private SelectionStatement parseSelectStmt() throws ParseException {
        SelectionStatement sStmt = new SelectionStatement();
        //Parse regex if(expr)stmt(else stmt)?
        match(Token.TokenType.IF_TOKEN);
        match(Token.TokenType.LPAREN_TOKEN);
        sStmt.setExpression(parseExpr());
        match(Token.TokenType.RPAREN_TOKEN);
        sStmt.setStatement(parseStatement());
        if (scanner.viewToken().getType() == Token.TokenType.ELSE_TOKEN) {
            //Parse optional ELSE_TOKEN
            match(Token.TokenType.ELSE_TOKEN);
            sStmt.setElseStatement(parseStatement());
        }
        switch (scanner.viewToken().getType()) {
            //Follow set RBRACE_TOKEN, END_STATEMENT_TOKEN, IF_TOKEN, WHILE_TOKEN, RETURN_TOKEN, NUM_TOKEN, LPAREN_TOKEN, ID_TOKEN, ELSE_TOKEN, LBRACE_TOKEN
            case RBRACE_TOKEN:
            case END_STATEMENT_TOKEN:
            case IF_TOKEN:
            case WHILE_TOKEN:
            case RETURN_TOKEN:
            case NUM_TOKEN:
            case LPAREN_TOKEN:
            case ID_TOKEN:
            case ELSE_TOKEN:
            case LBRACE_TOKEN:
                return sStmt;
            default:
                String message = "Expected a RBRACE_TOKEN, END_STATEMENT_TOKEN, IF_TOKEN, WHILE_TOKEN, RETURN_TOKEN, NUM_TOKEN, LPAREN_TOKEN, ID_TOKEN, ELSE_TOKEN, LBRACE_TOKEN. Got " + scanner.viewToken().toString() + " in parseSelectStmt";
                throw new ParseException(message, 0);
        }
    }

    private IterationStatement parseIterateStmt() throws ParseException {
        //Parse while(expr)stmt
        IterationStatement iStmt = new IterationStatement();
        match(Token.TokenType.WHILE_TOKEN);
        match(Token.TokenType.LPAREN_TOKEN);
        iStmt.setExpression(parseExpr());
        match(Token.TokenType.RPAREN_TOKEN);
        iStmt.setStatement(parseStatement());
        switch (scanner.viewToken().getType()) {
            //Follow set RBRACE_TOKEN, END_STATEMENT_TOKEN, IF_TOKEN, WHILE_TOKEN, RETURN_TOKEN, NUM_TOKEN, LPAREN_TOKEN, ID_TOKEN, ELSE_TOKEN, LBRACE_TOKEN
            case RBRACE_TOKEN:
            case END_STATEMENT_TOKEN:
            case IF_TOKEN:
            case WHILE_TOKEN:
            case RETURN_TOKEN:
            case NUM_TOKEN:
            case LPAREN_TOKEN:
            case ID_TOKEN:
            case ELSE_TOKEN:
            case LBRACE_TOKEN:
                return iStmt;
            default:
                String message = "Expected a RBRACE_TOKEN, END_STATEMENT_TOKEN, IF_TOKEN, WHILE_TOKEN, RETURN_TOKEN, NUM_TOKEN, LPAREN_TOKEN, ID_TOKEN, ELSE_TOKEN, LBRACE_TOKEN. Got " + scanner.viewToken().toString() + " in parseIterationStmt";
                throw new ParseException(message, 0);
        }
    }

    private ReturnStatement parseReturnStmt() throws ParseException {
        ReturnStatement rStmt = new ReturnStatement();
        match(Token.TokenType.RETURN_TOKEN);
        ExpressionStatement exStmt = parseExprStmt();
        if (exStmt != null) {
            rStmt.setExpression(exStmt.getExpression());
        }
        switch (scanner.viewToken().getType()) {
            //Follow set RBRACE_TOKEN, END_STATEMENT_TOKEN, IF_TOKEN, WHILE_TOKEN, RETURN_TOKEN, NUM_TOKEN, LPAREN_TOKEN, ID_TOKEN, ELSE_TOKEN, LBRACE_TOKEN
            case RBRACE_TOKEN:
            case END_STATEMENT_TOKEN:
            case IF_TOKEN:
            case WHILE_TOKEN:
            case RETURN_TOKEN:
            case NUM_TOKEN:
            case LPAREN_TOKEN:
            case ID_TOKEN:
            case ELSE_TOKEN:
            case LBRACE_TOKEN:
                return rStmt;
            default:
                String message = "Expected a RBRACE_TOKEN, END_STATEMENT_TOKEN, IF_TOKEN, WHILE_TOKEN, RETURN_TOKEN, NUM_TOKEN, LPAREN_TOKEN, ID_TOKEN, ELSE_TOKEN, LBRACE_TOKEN. Got " + scanner.viewToken().toString() + " in parseReturnStmt";
                throw new ParseException(message, 0);
        }
    }

    private Expression parseExpr() throws ParseException {
        Expression ex;
        switch (scanner.viewToken().getType()) {
            //First set NUM_TOKEN, LPAREN_TOKEN, ID_TOKEN
            case NUM_TOKEN:
                //Parse NUMsimple-exp
                NumExpression numEx = new NumExpression(((Integer) scanner.viewToken().getData()).intValue());
                match(Token.TokenType.NUM_TOKEN);
                ex = parseSimpleExpr(numEx);
                break;
            case LPAREN_TOKEN:
                //Parse (expr)simp-expr
                match(Token.TokenType.LPAREN_TOKEN);
                Expression ex2 = parseExpr();
                match(Token.TokenType.RPAREN_TOKEN);
                ex = parseSimpleExpr(ex2);
                break;
            case ID_TOKEN:
                //Parses IDexpr'
                String id = (String) scanner.viewToken().getData();
                match(Token.TokenType.ID_TOKEN);
                ex = parseExpr1(id);
                break;
            default:
                String message = "Expected an NUM_TOKEN, LPAREN_TOKEN, ID_TOKEN. Got " + scanner.viewToken().toString() + " in parseExpr";
                throw new ParseException(message, 0);
        }
        switch (scanner.viewToken().getType()) {
            //Follow END_STATEMENT_TOKEN, RPAREN_TOKEN, RBRACKET_TOKEN, SEQUENCING_TOKEN
            case END_STATEMENT_TOKEN:
            case RPAREN_TOKEN:
            case RBRACKET_TOKEN:
            case SEQUENCING_TOKEN:
                return ex;
            default:
                String message = "Expected an NUM_TOKEN, LPAREN_TOKEN, ID_TOKEN. Got " + scanner.viewToken().toString() + " in parseExpr";
                throw new ParseException(message, 0);
        }
    }

    private Expression parseExpr1(String id) throws ParseException {
        Expression ex;
        switch (scanner.viewToken().getType()) {
            //First set LPAREN_TOKEN, ASSIGN_TOKEN, NULL, ADD_TOKEN, SUBTRACT_TOKEN, MULTIPLY_TOKEN, DIVIDE_TOKEN, LE_TOKEN, LEQ_TOKEN, GE_TOKEN, GEQ_TOKEN, EQUAL_TOKEN, NEQ_TOKEN, LBRACKET_TOKEN
            case ASSIGN_TOKEN:
                //Parse =expr
                AssignmentExpression aEx = new AssignmentExpression();
                match(Token.TokenType.ASSIGN_TOKEN);
                aEx.setExpression(parseExpr());
                aEx.setVariable(new VarExpression(id));
                ex = aEx;
                break;
            case LPAREN_TOKEN:
                //Parse (args)simple_expr
                CallExpression cEx = new CallExpression();
                cEx.setId(id);
                match(Token.TokenType.LPAREN_TOKEN);
                cEx.setArgs(parseArgs());
                match(Token.TokenType.RPAREN_TOKEN);
                ex = parseSimpleExpr(cEx);
                break;
            case LBRACKET_TOKEN:
                //Parse [expr]expr''
                VarExpression varEx = new VarExpression();
                varEx.setId(id);
                match(Token.TokenType.LBRACKET_TOKEN);
                varEx.setExpression(parseExpr());
                match(Token.TokenType.RBRACKET_TOKEN);
                ex = parseExpr2(varEx);
                break;
            case MULTIPLY_TOKEN:
            case DIVIDE_TOKEN:
            case ADD_TOKEN:
            case SUBTRACT_TOKEN:
            case LE_TOKEN:
            case LEQ_TOKEN:
            case GE_TOKEN:
            case GEQ_TOKEN:
            case EQUAL_TOKEN:
            case NOT_EQUAL_TOKEN:
            case END_STATEMENT_TOKEN:
            case RPAREN_TOKEN:
            case RBRACKET_TOKEN:
            case SEQUENCING_TOKEN:
                //Parse simple-expr\
                varEx = new VarExpression();
                varEx.setId(id);
                ex = parseSimpleExpr(varEx);
                break;
            default:
                String message = "Expected an LPAREN_TOKEN, ASSIGN_TOKEN, NULL, ADD_TOKEN, SUBTRACT_TOKEN, MULTIPLY_TOKEN, DIVIDE_TOKEN, LE_TOKEN, LEQ_TOKEN, GE_TOKEN, GEQ_TOKEN, EQUAL_TOKEN, NEQ_TOKEN, LBRACKET_TOKEN. Got " + scanner.viewToken().toString() + " in parseExpr1";
                throw new ParseException(message, 0);
        }
        switch (scanner.viewToken().getType()) {
            //Follow END_STATEMENT_TOKEN, RPAREN_TOKEN, RBRACKET_TOKEN, SEQUENCING_TOKEN
            case END_STATEMENT_TOKEN:
            case RPAREN_TOKEN:
            case RBRACKET_TOKEN:
            case SEQUENCING_TOKEN:
                return ex;
            default:
                String message = "Expected an NUM_TOKEN, LPAREN_TOKEN, ID_TOKEN. Got " + scanner.viewToken().toString() + " in parseExpr1";
                throw new ParseException(message, 0);
        }
    }

    private Expression parseExpr2(VarExpression exp) throws ParseException {
        Expression ex;
        switch (scanner.viewToken().getType()) {
            //First set NULL, ASSIGN_TOKEN, ADD_TOKEN, SUBTRACT_TOKEN, MULTIPLY_TOKEN, DIVIDE_TOKEN, LE_TOKEN, LEQ_TOKEN, GE_TOKEN, GEQ_TOKEN, EQUAL_TOKEN, NEQ_TOKEN
            case ASSIGN_TOKEN:
                //Parse =expr
                AssignmentExpression aEx = new AssignmentExpression();
                match(Token.TokenType.ASSIGN_TOKEN);
                aEx.setExpression(parseExpr());
                aEx.setVariable(exp);
                ex = aEx;
                break;
            case ADD_TOKEN:
            case SUBTRACT_TOKEN:
            case MULTIPLY_TOKEN:
            case DIVIDE_TOKEN:
            case LE_TOKEN:
            case LEQ_TOKEN:
            case GE_TOKEN:
            case GEQ_TOKEN:
            case EQUAL_TOKEN:
            case NOT_EQUAL_TOKEN:
            case END_STATEMENT_TOKEN:
            case RPAREN_TOKEN:
            case RBRACKET_TOKEN:
            case SEQUENCING_TOKEN:
                ex = parseSimpleExpr(exp);
                break;
            default:
                String message = "Expected an NULL, ASSIGN_TOKEN, ADD_TOKEN, SUBTRACT_TOKEN, MULTIPLY_TOKEN, DIVIDE_TOKEN, LE_TOKEN, LEQ_TOKEN, GE_TOKEN, GEQ_TOKEN, EQUAL_TOKEN, NEQ_TOKEN. Got " + scanner.viewToken().toString() + " in parseExpr2";
                throw new ParseException(message, 0);
        }
        switch (scanner.viewToken().getType()) {
            //Follow END_STATEMENT_TOKEN, RPAREN_TOKEN, RBRACKET_TOKEN, SEQUENCING_TOKEN
            case END_STATEMENT_TOKEN:
            case RPAREN_TOKEN:
            case RBRACKET_TOKEN:
            case SEQUENCING_TOKEN:
                return ex;
            default:
                String message = "Expected an NUM_TOKEN, LPAREN_TOKEN, ID_TOKEN. Got " + scanner.viewToken().toString() + " in parseExpr2";
                throw new ParseException(message, 0);
        }
    }

    private Expression parseSimpleExpr(Expression exp) throws ParseException {
        Expression ex = parseAddExpr1(exp);
        switch (scanner.viewToken().getType()) {
            //First set NULL, ADD_TOKEN, SUBTRACT_TOKEN, MULTIPLY_TOKEN, DIVIDE_TOKEN, LE_TOKEN, LEQ_TOKEN, GE_TOKEN, GEQ_TOKEN, EQUAL_TOKEN, NEQ_TOKEN
            case LE_TOKEN:
            case LEQ_TOKEN:
            case GE_TOKEN:
            case GEQ_TOKEN:
            case EQUAL_TOKEN:
            case NOT_EQUAL_TOKEN:
                //Parse regex add-expr'(relop add-expr)?
                BinaryExpression binEx = new BinaryExpression();
                binEx.setLeftExpression(ex);
                binEx.setOperator(parseRelop());
                binEx.setRightExpression(parseAddExpr());
                ex = binEx;
                break;
        }
        switch (scanner.viewToken().getType()) {
            //Follow END_STATEMENT_TOKEN, RPAREN_TOKEN, RBRACKET_TOKEN, SEQUENCING_TOKEN
            case END_STATEMENT_TOKEN:
            case RPAREN_TOKEN:
            case RBRACKET_TOKEN:
            case SEQUENCING_TOKEN:
                return ex;
            default:
                String message = "Expected an NUM_TOKEN, LPAREN_TOKEN, ID_TOKEN. Got " + scanner.viewToken().toString() + " in parseSimpleExpr";
                throw new ParseException(message, 0);
        }
    }

    private Expression parseAddExpr() throws ParseException {
        //Parse regex term(addop term)*
        Expression ex = parseTerm();
        switch (scanner.viewToken().getType()) {
            case ADD_TOKEN:
            case SUBTRACT_TOKEN:
                BinaryExpression binEx = new BinaryExpression();
                binEx.setLeftExpression(ex);
                binEx.setOperator(parseAddop());
                binEx.setRightExpression(parseAddExpr());
                ex = binEx;
        }
        switch (scanner.viewToken().getType()) {
            //Follow END_STATEMENT_TOKEN, RPAREN_TOKEN, RBRACKET_TOKEN, SEQUENCING_TOKEN
            case END_STATEMENT_TOKEN:
            case RPAREN_TOKEN:
            case RBRACKET_TOKEN:
            case SEQUENCING_TOKEN:
            case LE_TOKEN:
            case LEQ_TOKEN:
            case GE_TOKEN:
            case GEQ_TOKEN:
            case EQUAL_TOKEN:
            case NOT_EQUAL_TOKEN:
                return ex;
            default:
                String message = "Expected an NUM_TOKEN, LPAREN_TOKEN, ID_TOKEN. Got " + scanner.viewToken().toString() + " in parseAddExpr";
                throw new ParseException(message, 0);
        }
    }

    private Expression parseAddExpr1(Expression exp) throws ParseException {
        //Parse regex term'(addop term)?
        Expression ex = parseTerm1(exp);
        switch (scanner.viewToken().getType()) {
            case ADD_TOKEN:
            case SUBTRACT_TOKEN:
                BinaryExpression binEx = new BinaryExpression();
                binEx.setLeftExpression(ex);
                binEx.setOperator(parseAddop());
                binEx.setRightExpression(parseAddExpr());
                ex = binEx;
                break;
        }
        switch (scanner.viewToken().getType()) {
            //Follow END_STATEMENT_TOKEN, RPAREN_TOKEN, RBRACKET_TOKEN, SEQUENCING_TOKEN, LE_TOKEN, LEQ_TOKEN, GE_TOKEN, GEQ_TOKEN, EQUAL_TOKEN, NEQ_TOKEN
            case END_STATEMENT_TOKEN:
            case RPAREN_TOKEN:
            case RBRACKET_TOKEN:
            case SEQUENCING_TOKEN:
            case LE_TOKEN:
            case LEQ_TOKEN:
            case GE_TOKEN:
            case GEQ_TOKEN:
            case EQUAL_TOKEN:
            case NOT_EQUAL_TOKEN:
                return ex;
            default:
                String message = "Expected an NUM_TOKEN, LPAREN_TOKEN, ID_TOKEN. Got " + scanner.viewToken().toString() + " in parseAddExpr1";
                throw new ParseException(message, 0);
        }
    }

    private Expression parseTerm() throws ParseException {
        //Parse regex factor(mulop factor)*
        Expression ex = parseFactor();
        switch (scanner.viewToken().getType()) {
            case MULTIPLY_TOKEN:
            case DIVIDE_TOKEN:
                BinaryExpression binEx = new BinaryExpression();
                binEx.setLeftExpression(ex);
                binEx.setOperator(parseMulop());
                binEx.setRightExpression(parseTerm());
                ex = binEx;
                break;
        }
        switch (scanner.viewToken().getType()) {
            //Follow set END_STATEMENT_TOKEN, ADD_TOKEN, SUBTRACT_TOKEN, RPAREN_TOKEN, LE_TOKEN, LEQ_TOKEN, GE_TOKEN, GEQ_TOKEN, EQUAL_TOKEN, NEQ_TOKEN, RBRACKET_TOKEN, SEQUENCING_TOKEN
            case END_STATEMENT_TOKEN:
            case ADD_TOKEN:
            case SUBTRACT_TOKEN:
            case LE_TOKEN:
            case LEQ_TOKEN:
            case GE_TOKEN:
            case GEQ_TOKEN:
            case EQUAL_TOKEN:
            case NOT_EQUAL_TOKEN:
            case RBRACKET_TOKEN:
            case SEQUENCING_TOKEN:
            case RPAREN_TOKEN:
                return ex;
            default:
                String message = "Expected an END_STATEMENT_TOKEN, ADD_TOKEN, SUBTRACT_TOKEN, RPAREN_TOKEN, LE_TOKEN, LEQ_TOKEN, GE_TOKEN, GEQ_TOKEN, EQUAL_TOKEN, NEQ_TOKEN, RBRACKET_TOKEN, SEQUENCING_TOKEN. Got " + scanner.viewToken().toString() + " in parseTerm";
                throw new ParseException(message, 0);
        }
    }

    private Expression parseTerm1(Expression exp) throws ParseException {
        switch (scanner.viewToken().getType()) {
            //Parse regex (mulop factor)?
            //First set NULL, MULTIPY_TOKEN, DIVIDE_TOKEN
            case MULTIPLY_TOKEN:
            case DIVIDE_TOKEN:
                BinaryExpression binEx = new BinaryExpression();
                binEx.setLeftExpression(exp);
                binEx.setOperator(parseMulop());
                binEx.setRightExpression(parseFactor());
                exp = binEx;
                break;
        }
        switch (scanner.viewToken().getType()) {
            //Follow set END_STATEMENT_TOKEN, ADD_TOKEN, SUBTRACT_TOKEN, RPAREN_TOKEN, LE_TOKEN, LEQ_TOKEN, GE_TOKEN, GEQ_TOKEN, EQUAL_TOKEN, NEQ_TOKEN, RBRACKET_TOKEN, SEQUENCING_TOKEN
            case END_STATEMENT_TOKEN:
            case ADD_TOKEN:
            case SUBTRACT_TOKEN:
            case LE_TOKEN:
            case LEQ_TOKEN:
            case GE_TOKEN:
            case GEQ_TOKEN:
            case EQUAL_TOKEN:
            case NOT_EQUAL_TOKEN:
            case RBRACKET_TOKEN:
            case SEQUENCING_TOKEN:
            case RPAREN_TOKEN:
                return exp;
            default:
                String message = "Expected an END_STATEMENT_TOKEN, ADD_TOKEN, SUBTRACT_TOKEN, RPAREN_TOKEN, LE_TOKEN, LEQ_TOKEN, GE_TOKEN, GEQ_TOKEN, EQUAL_TOKEN, NEQ_TOKEN, RBRACKET_TOKEN, SEQUENCING_TOKEN. Got " + scanner.viewToken().toString() + " in parseTerm1";
                throw new ParseException(message, 0);
        }
    }

    private Expression parseFactor() throws ParseException {
        //Parse regex ID(factor')?|"("expr")"|NUM
        Expression ex;
        switch (scanner.viewToken().getType()) {
            //First set ID_TOKEN, LPAREN_TOKEN, NUM_TOKEN
            case ID_TOKEN:
                String id = (String) scanner.viewToken().getData();
                match(Token.TokenType.ID_TOKEN);
                switch (scanner.viewToken().getType()) {
                    case LBRACKET_TOKEN:
                    case LPAREN_TOKEN:
                        ex = parseFactor1(id);
                        break;
                    default:
                        VarExpression varEx = new VarExpression();
                        varEx.setId(id);
                        ex = varEx;
                }
                break;
            case LPAREN_TOKEN:
                match(Token.TokenType.LPAREN_TOKEN);
                ex = parseExpr();
                match(Token.TokenType.RPAREN_TOKEN);
                break;
            case NUM_TOKEN:
                int num = ((Integer) scanner.viewToken().getData()).intValue();
                match(Token.TokenType.NUM_TOKEN);
                ex = new NumExpression(num);
                break;
            default:
                String message = "Expected an ID_TOKEN, LPAREN_TOKEN, NUM_TOKEN. Got " + scanner.viewToken().toString() + " in parseFactor";
                throw new ParseException(message, 0);
        }
        switch (scanner.viewToken().getType()) {
            //Follow set MULTIPLY_TOKEN, DIVIDE_TOKEN, END_STATEMENT_TOKEN, ADD_TOKEN, SUBTRACT_TOKEN, RPAREN_TOKEN, LE_TOKEN, LEQ_TOKEN, GE_TOKEN, GEQ_TOKEN, EQUAL_TOKEN, NEQ_TOKEN, RBRACKET_TOKEN, SEQUENCING_TOKEN
            case END_STATEMENT_TOKEN:
            case ADD_TOKEN:
            case SUBTRACT_TOKEN:
            case LE_TOKEN:
            case LEQ_TOKEN:
            case GE_TOKEN:
            case GEQ_TOKEN:
            case EQUAL_TOKEN:
            case NOT_EQUAL_TOKEN:
            case RBRACKET_TOKEN:
            case SEQUENCING_TOKEN:
            case RPAREN_TOKEN:
            case MULTIPLY_TOKEN:
            case DIVIDE_TOKEN:
                return ex;
            default:
                String message = "Expected an MULTIPLY_TOKEN, DIVIDE_TOKEN, END_STATEMENT_TOKEN, ADD_TOKEN, SUBTRACT_TOKEN, RPAREN_TOKEN, LE_TOKEN, LEQ_TOKEN, GE_TOKEN, GEQ_TOKEN, EQUAL_TOKEN, NEQ_TOKEN, RBRACKET_TOKEN, SEQUENCING_TOKEN. Got " + scanner.viewToken().toString() + " in parseFactor";
                throw new ParseException(message, 0);
        }
    }

    private Expression parseFactor1(String id) throws ParseException {
        //Parse regex "["expr"]"|"("args")"
        Expression ex;
        switch (scanner.viewToken().getType()) {
            //First set LBRACKET_TOKEN, LPAREN_TOKEN
            case LBRACKET_TOKEN:
                VarExpression varEx = new VarExpression();
                varEx.setId(id);
                match(Token.TokenType.LBRACKET_TOKEN);
                varEx.setExpression(parseExpr());
                match(Token.TokenType.RBRACKET_TOKEN);
                ex = varEx;
                break;
            case LPAREN_TOKEN:
                CallExpression callEx = new CallExpression();
                callEx.setId(id);
                match(Token.TokenType.LPAREN_TOKEN);
                callEx.setArgs(parseArgs());
                match(Token.TokenType.RPAREN_TOKEN);
                ex = callEx;
                break;
            default:
                String message = "Expected an ID_TOKEN, LPAREN_TOKEN, NUM_TOKEN. Got " + scanner.viewToken().toString() + " in parseFactor";
                throw new ParseException(message, 0);
        }
        switch (scanner.viewToken().getType()) {
            //Follow set MULTIPLY_TOKEN, DIVIDE_TOKEN, END_STATEMENT_TOKEN, ADD_TOKEN, SUBTRACT_TOKEN, RPAREN_TOKEN, LE_TOKEN, LEQ_TOKEN, GE_TOKEN, GEQ_TOKEN, EQUAL_TOKEN, NEQ_TOKEN, RBRACKET_TOKEN, SEQUENCING_TOKEN
            case END_STATEMENT_TOKEN:
            case ADD_TOKEN:
            case SUBTRACT_TOKEN:
            case LE_TOKEN:
            case LEQ_TOKEN:
            case GE_TOKEN:
            case GEQ_TOKEN:
            case EQUAL_TOKEN:
            case NOT_EQUAL_TOKEN:
            case RBRACKET_TOKEN:
            case SEQUENCING_TOKEN:
            case RPAREN_TOKEN:
            case MULTIPLY_TOKEN:
            case DIVIDE_TOKEN:
                return ex;
            default:
                String message = "Expected an MULTIPLY_TOKEN, DIVIDE_TOKEN, END_STATEMENT_TOKEN, ADD_TOKEN, SUBTRACT_TOKEN, RPAREN_TOKEN, LE_TOKEN, LEQ_TOKEN, GE_TOKEN, GEQ_TOKEN, EQUAL_TOKEN, NEQ_TOKEN, RBRACKET_TOKEN, SEQUENCING_TOKEN. Got " + scanner.viewToken().toString() + " in parseFactor";
                throw new ParseException(message, 0);
        }
    }

    private LinkedList<Expression> parseArgs() throws ParseException {
        //Parse regex (arg-list)?
        LinkedList<Expression> args = new LinkedList<>();
        switch (scanner.viewToken().getType()) {
            //First set NUM_TOKEN, LPAREN_TOKEN, ID_TOKEN, NULL
            case NUM_TOKEN:
            case ID_TOKEN:
            case LPAREN_TOKEN:
                args = parseArgsList();
                break;
        }
        switch (scanner.viewToken().getType()) {
            //Follow set RPAREN_TOKEN
            case RPAREN_TOKEN:
                return args;
            default:
                String message = "Expected an RPAREN_TOKEN. Got " + scanner.viewToken().toString() + " in parseArgs";
                throw new ParseException(message, 0);
        }
    }

    private LinkedList<Expression> parseArgsList() throws ParseException {
        //Parse regex expr("," expr)*
        LinkedList<Expression> args = new LinkedList<>();
        args.add(parseExpr());
        switch (scanner.viewToken().getType()) {
            case SEQUENCING_TOKEN:
                match(Token.TokenType.SEQUENCING_TOKEN);
                args.addAll(parseArgsList());
                break;
        }
        switch (scanner.viewToken().getType()) {
            //Follow set RPAREN_TOKEN
            case RPAREN_TOKEN:
                return args;
            default:
                String message = "Expected an RPAREN_TOKEN. Got " + scanner.viewToken().toString() + " in parseArgsList";
                throw new ParseException(message, 0);
        }
    }

    private String parseRelop() throws ParseException {
        String temp = null;
        switch (scanner.viewToken().getType()) {
            //First set LE_TOKEN, LEQ_TOKEN, GE_TOKEN, GEQ_TOKEN, EQUAL_TOKEN, NEQ_TOKEN
            case LE_TOKEN:
            case LEQ_TOKEN:
            case GE_TOKEN:
            case GEQ_TOKEN:
            case EQUAL_TOKEN:
            case NOT_EQUAL_TOKEN:
                temp = (String) scanner.viewToken().getData();
                try {
                    scanner.getToken();
                } catch (IOException ex) {
                    String message = "Error in scanner.getToken()";
                    throw new ParseException(message, 0);
                }
                break;
            default:
                String message = "Expected an LE_TOKEN, LEQ_TOKEN, GE_TOKEN, GEQ_TOKEN, EQUAL_TOKEN, NEQ_TOKEN. Got " + scanner.viewToken().toString() + " in parseRelop";
                throw new ParseException(message, 0);
        }
        switch (scanner.viewToken().getType()) {
            //Follow set ID_TOKEN, LPAREN_TOKEN, NUM_TOKEN
            case ID_TOKEN:
            case LPAREN_TOKEN:
            case NUM_TOKEN:
                return temp;
            default:
                String message = "Expected an ID_TOKEN, LPAREN_TOKEN, NUM_TOKEN. Got " + scanner.viewToken().toString() + " in parseRelop";
                throw new ParseException(message, 0);
        }
    }

    private String parseAddop() throws ParseException {
        String temp = null;
        switch (scanner.viewToken().getType()) {
            //First set ADD_TOKEN, SUBTRACT_TOKEN
            case ADD_TOKEN:
            case SUBTRACT_TOKEN:
                temp = (String) scanner.viewToken().getData();
                try {
                    scanner.getToken();
                } catch (IOException ex) {
                    String message = "Error in scanner.getToken()";
                    throw new ParseException(message, 0);
                }
                break;
            default:
                String message = "Expected an ADD_TOKEN, SUBTRACT_TOKEN. Got " + scanner.viewToken().toString() + " in parseAddop";
                throw new ParseException(message, 0);
        }
        switch (scanner.viewToken().getType()) {
            //Follow set ID_TOKEN, LPAREN_TOKEN, NUM_TOKEN
            case ID_TOKEN:
            case LPAREN_TOKEN:
            case NUM_TOKEN:
                return temp;
            default:
                String message = "Expected an ID_TOKEN, LPAREN_TOKEN, NUM_TOKEN. Got " + scanner.viewToken().toString() + " in parseAddop";
                throw new ParseException(message, 0);
        }
    }

    private String parseMulop() throws ParseException {
        String temp = null;
        switch (scanner.viewToken().getType()) {
            //First set MULTIPY_TOKEN, DIVIDE_TOKEN
            case MULTIPLY_TOKEN:
            case DIVIDE_TOKEN:
                temp = (String) scanner.viewToken().getData();
                try {
                    scanner.getToken();
                } catch (IOException ex) {
                    String message = "Error in scanner.getToken()";
                    throw new ParseException(message, 0);
                }
                break;
            default:
                String message = "Expected an ID_TOKEN, LPAREN_TOKEN, NUM_TOKEN. Got " + scanner.viewToken().toString() + " in parseMulop";
                throw new ParseException(message, 0);
        }
        switch (scanner.viewToken().getType()) {
            //Follow set ID_TOKEN, LPAREN_TOKEN, NUM_TOKEN
            case ID_TOKEN:
            case LPAREN_TOKEN:
            case NUM_TOKEN:
                return temp;
            default:
                String message = "Expected an ID_TOKEN, LPAREN_TOKEN, NUM_TOKEN. Got " + scanner.viewToken().toString() + " in parseMulop";
                throw new ParseException(message, 0);
        }
    }

    private void match(Token.TokenType tokenType) throws ParseException {
        if (scanner.viewToken().getType() == tokenType) {
            try {
                scanner.getToken();
            } catch (IOException ex) {
                String message = "Error in scanner.getToken()";
                throw new ParseException(message, 0);
            }
        } else {
            String message = "Expected " + tokenType + ". Got " + scanner.viewToken().toString() + " in match";
            throw new ParseException(message, 0);
        }
    }

    public void printTree(String source) {
        this.program.print(0);
        this.program.printFile(source, 0);
        this.program.printASTFile(source, 0);
    }

    public CMinusParser(String source) throws FileNotFoundException, IOException {
        this.scanner = new CMinusScanner(source);
    }

    public static void main(String[] args) throws IOException, ParseException {
        //String[] filename = {"ComprehensiveTest.c", "SelectionSort.c", "CrappyCode.c"};
        String[] filename = {"ComprehensiveTest.c", "SelectionSort.c"};
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