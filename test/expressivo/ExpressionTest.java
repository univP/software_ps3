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
    // toString():
    // -> number, variable, plus, into
    // equals():
    // -> number, variable, plus, into
    // hashCode():
    // -> number, variable, plus, into

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    // tests for Expression
    @Test
    public void testToString() {
        // candidates
        double number1 = 1.0;
        double number2 = 1;
        double number3 = 2.0;
        String string1 = String.valueOf(number1);
        String string2 = String.valueOf(number2);
        String string3 = String.valueOf(number3);
        String variable1 = "x";
        Expression expression1 = Expression.makeNumber(number1);
        Expression expression2 = Expression.makeNumber(number2);
        Expression expression3 = Expression.makeNumber(number3);
        Expression expression4 = Expression.makeVariable(variable1);
        Expression expression5 = Expression.makeInto(expression1, Expression.makePlus(expression2, expression3));
        Expression expression6 = Expression.makePlus(Expression.makeInto(expression1, expression2), expression3);
        Expression expression7 = Expression.makeInto(expression4, Expression.makePlus(expression2, expression3));
        Expression expression8 = Expression.makePlus(Expression.makeInto(expression4, expression2), expression3);

        // tests
        assertEquals(string1, expression1.toString());
        assertEquals(variable1, expression4.toString());
        assertEquals("(" + string1 + ")*((" + string2 + ")+(" + string3 + "))", expression5.toString());
        assertEquals("((" + string1 + ")*(" + string2 + "))+(" + string3 + ")", expression6.toString());
        assertEquals("(" + variable1 + ")*((" + string2 + ")+(" + string3 + "))", expression7.toString());
        assertEquals("((" + variable1 + ")*(" + string2 + "))+(" + string3 + ")", expression8.toString());
    }

    @Test
    public void testEquals() {
        // candidates
        double number1 = 1.0;
        double number2 = 1;
        double number3 = 2.0;
        String variable1 = "x";
        Expression expression1 = Expression.makeNumber(number1);
        Expression expression2 = Expression.makeNumber(number2);
        Expression expression3 = Expression.makeNumber(number3);
        Expression expression4 = Expression.makeVariable(variable1);
        Expression expression5 = Expression.makeInto(expression1, Expression.makeInto(expression2, expression3));
        Expression expression6 = Expression.makeInto(Expression.makeInto(expression1, expression2), expression3);
        Expression expression7 = Expression.makeInto(expression2, Expression.makeInto(expression1, expression3));
        Expression expression8 = Expression.makeInto(expression2, Expression.makeInto(expression3, expression1));
        Expression expression9 = Expression.makeInto(expression4, Expression.makeInto(expression2, expression3));
        Expression expression10 = Expression.makeInto(Expression.makeInto(expression4, expression2), expression3);
        Expression expression11 = Expression.makeInto(expression2, Expression.makeInto(expression4, expression3));
        Expression expression12 = Expression.makeInto(expression2, Expression.makeInto(expression3, expression4));

        // tests
        assertTrue(expression1.equals(expression2));
        assertTrue(expression1.hashCode() == expression2.hashCode());
        assertFalse(expression5.equals(expression6));
        assertTrue(expression5.equals(expression7));
        assertTrue(expression5.hashCode() == expression7.hashCode());
        assertFalse(expression7.equals(expression8));
        assertFalse(expression9.equals(expression10));
        assertFalse(expression9.equals(expression11));
        assertFalse(expression11.equals(expression12));
    }

    @Test
    public void testParse() {
        // candidates
        double number1 = 1.0;
        double number2 = 1.5;
        double number3 = 2.0;
        String string1 = String.valueOf(number1);
        String string2 = String.valueOf(number2);
        String string3 = String.valueOf(number3);
        String variable1 = "X";
        Expression expression1 = Expression.makeNumber(number1);
        Expression expression2 = Expression.makeNumber(number2);
        Expression expression3 = Expression.makeNumber(number3);
        Expression expression4 = Expression.makeVariable(variable1);
        Expression expression5 = Expression.makeInto(expression1, Expression.makePlus(expression2, expression3));
        Expression expression6 = Expression.makePlus(Expression.makeInto(expression1, expression2), expression3);
        Expression expression7 = Expression.makeInto(expression4, Expression.makePlus(expression2, expression3));
        Expression expression8 = Expression.makePlus(Expression.makeInto(expression4, expression2), expression3);
        String input1 = string1 + "*(" + string2 + "+" + string3 + ")";
        String input2 = string1 + "*" + string2 + "+" + string3;
        String input3 = variable1 + "*(" + string2 + "+" + string3 + ")";
        String input4 = variable1 + "*" + string2 + "+" + string3;

        // tests
        assertEquals(expression1, Expression.parse(string1));
        assertEquals(expression5, Expression.parse(input1));
        assertEquals(expression6, Expression.parse(input2));
        assertEquals(expression7, Expression.parse(input3));
        assertEquals(expression8, Expression.parse(input4));
    }
}
