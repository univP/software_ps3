/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.antlr.v4.gui.Trees;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

import expressivo.parser.ExpressionLexer;
import expressivo.parser.ExpressionListener;
import expressivo.parser.ExpressionParser;
import expressivo.parser.ExpressionParser.PrimitiveContext;
import expressivo.parser.ExpressionParser.ProductContext;
import expressivo.parser.ExpressionParser.RootContext;
import expressivo.parser.ExpressionParser.SumContext;

/**
 * An immutable data type representing a polynomial expression of: + and *
 * nonnegative integers and floating-point numbers variables (case-sensitive
 * nonempty strings of letters)
 * 
 * <p>
 * PS3 instructions: this is a required ADT interface. You MUST NOT change its
 * name or package or the names or type signatures of existing methods. You may,
 * however, add additional methods, or strengthen the specs of existing methods.
 * Declare concrete variants of Expression in their own Java source files.
 */
public interface Expression {

    // Data Type definition
    // Expression = Number(n:int) +
    // Plus(left:Expression, right:Expression) +
    // Into(left:Expression, right:Expression)

    /**
     * Parse an expression.
     * 
     * @param input
     *            expression to parse, as defined in the PS3 handout.
     * @return expression AST for the input
     * @throws IllegalArgumentException
     *             if the expression is invalid
     */
    public static Expression parse(String input) {
        // Create a stream of characters from the string
        CharStream stream = new ANTLRInputStream(input);

        // Make a parser
        ExpressionParser parser = makeParser(stream);

        // Generate parse tree using starter rule root
        ParseTree tree = parser.root();

        // Debugging option #2: show tree in a window
        Trees.inspect(tree, parser);

        // Return abstract data type
        MakeExpression expressionMaker = new MakeExpression();
        new ParseTreeWalker().walk(expressionMaker, tree);
        return expressionMaker.getExpression();
    }

    /**
     * 
     * @param stream
     *            stream of characters
     * @return a parser that is ready to parse the stream
     */
    static ExpressionParser makeParser(CharStream stream) {
        // Make a lexer that converts a stream of characters to a stream of
        // tokens
        // Does not start reading the character stream yet
        ExpressionLexer lexer = new ExpressionLexer(stream);
        lexer.reportErrorsAsExceptions();
        TokenStream tokens = new CommonTokenStream(lexer);

        // Make parser whose input comes from token stream produced by the lexer
        ExpressionParser parser = new ExpressionParser(tokens);
        parser.reportErrorsAsExceptions();
        return parser;
    }

    /**
     * @return a parsable representation of this expression, such that for all
     *         e:Expression, e.equals(Expression.parse(e.toString())).
     */
    @Override
    public String toString();

    /**
     * @param thatObject
     *            any object
     * @return true if and only if this and thatObject are structurally-equal
     *         Expressions, as defined in the PS3 handout.
     */
    @Override
    public boolean equals(Object thatObject);

    /**
     * @return hash code value consistent with the equals() definition of
     *         structural equality, such that for all e1,e2:Expression,
     *         e1.equals(e2) implies e1.hashCode() == e2.hashCode()
     */
    @Override
    public int hashCode();

    // static methods
    public static Expression makeNumber(double number) {
        return new Number(number);
    }

    public static Expression makeVariable(String variable) {
        return new Variable(variable);
    }

    public static Expression makePlus(Expression left, Expression right) {
        return new Plus(left, right);
    }

    public static Expression makeInto(Expression left, Expression right) {
        return new Into(left, right);
    }
    
    // Required methods
    /**
     * 
     * @return differentiation of expression
     */
    public Expression differentiate(String variable);
    
    public double simplify(Map<String, Double> environment) throws NotANumberException;
}

class MakeExpression implements ExpressionListener {
    private Stack<Expression> stack = new Stack<>();

    public Expression getExpression() {
        return stack.get(0);
    }

    @Override
    public void exitRoot(RootContext ctx) {
        // do nothing
    }

    @Override
    public void exitSum(SumContext ctx) {
        // matched the product ('*' product) rule
        List<ProductContext> addends = ctx.product();
        assert stack.size() >= addends.size();

        assert addends.size() > 0;
        Expression sum = stack.pop();

        for (int i = 1; i < addends.size(); ++i) {
            sum = Expression.makePlus(stack.pop(), sum);
        }

        stack.push(sum);
    }

    @Override
    public void exitProduct(ProductContext ctx) {
        // matched the product ('*' product) rule
        List<PrimitiveContext> addends = ctx.primitive();
        assert stack.size() >= addends.size();

        assert addends.size() > 0;
        Expression product = stack.pop();

        for (int i = 1; i < addends.size(); ++i) {
            product = Expression.makeInto(stack.pop(), product);
        }

        stack.push(product);
    }

    @Override
    public void exitPrimitive(PrimitiveContext ctx) {
        if (ctx.NUMBER() != null) {
            // matched NUMBER alternative
            double number = Double.valueOf(ctx.NUMBER().getText());
            Expression expression = Expression.makeNumber(number);
            stack.push(expression);
        } else if (ctx.VARIABLE() != null) {
            // matched VARIABLE alternative
            String variable = ctx.VARIABLE().getText();
            Expression expression = Expression.makeVariable(variable);
            stack.push(expression);
        } else {
            // do nothing
        }
    }

    // below functions not required
    @Override
    public void enterEveryRule(ParserRuleContext arg0) {
    }

    @Override
    public void exitEveryRule(ParserRuleContext arg0) {
    }

    @Override
    public void visitErrorNode(ErrorNode arg0) {
    }

    @Override
    public void visitTerminal(TerminalNode arg0) {
    }

    @Override
    public void enterRoot(RootContext ctx) {
    }

    @Override
    public void enterSum(SumContext ctx) {
    }

    @Override
    public void enterProduct(ProductContext ctx) {
    }

    @Override
    public void enterPrimitive(PrimitiveContext ctx) {
    }
}
