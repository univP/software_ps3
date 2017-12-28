/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for the Expression abstract data type.
 */
public class ExpressionTest {

    // Testing strategy
    //   toString():
    //      number, plus, into
    //   equals():
    //      number, plus, into
    //   hashCode():
    //      number, plus, into
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // tests for Expression
    @Test
    public void testToString()
    {
        // candidates
        double number1 = 1.0;
        double number2 = 1;
        double number3 = 2.0;
        String string1 = String.valueOf(number1);
        String string2 = String.valueOf(number2);
        String string3 = String.valueOf(number3);
        Expression expression1 = Expression.makeNumber(number1);
        Expression expression2 = Expression.makeNumber(number2);
        Expression expression3 = Expression.makeNumber(number3);
        Expression expression4 = Expression.makeInto(expression1, Expression.makePlus(expression2, expression3));
        Expression expression5 = Expression.makePlus(Expression.makeInto(expression1, expression2), expression3);
        
        // tests
        assertEquals(string1, Expression.makeNumber(number1).toString());
        assertEquals("("+string1+")*(("+string2+")+("+string3+"))", expression4.toString());
        assertEquals("(("+string1+")*("+string2+"))+("+string3+")", expression5.toString());
    }
    
    @Test
    public void testEquals()
    {
        // candidates
        double number1 = 1.0;
        double number2 = 1;
        double number3 = 2.0;
        Expression expression1 = Expression.makeNumber(number1);
        Expression expression2 = Expression.makeNumber(number2);
        Expression expression3 = Expression.makeNumber(number3);
        Expression expression4 = Expression.makeInto(expression1, Expression.makeInto(expression2, expression3));
        Expression expression5 = Expression.makeInto(Expression.makeInto(expression1, expression2), expression3);
        Expression expression6 = Expression.makeInto(expression2, Expression.makeInto(expression1, expression3));
        Expression expression7 = Expression.makeInto(expression2, Expression.makeInto(expression3, expression1));
        
        // tests
        assertTrue(expression1.equals(expression2));
        assertTrue(expression1.hashCode() == expression2.hashCode());
        assertFalse(expression4.equals(expression5));
        assertTrue(expression4.equals(expression6));
        assertTrue(expression4.hashCode() == expression6.hashCode());
        assertFalse(expression6.equals(expression7));
    }
}
