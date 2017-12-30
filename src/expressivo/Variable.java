/**
 * 
 */
package expressivo;

import java.util.Map;

/**
 * @author pao214
 *
 */
public class Variable implements Expression {
    private final String variable;

    // Abstraction function
    // :represents a variable
    // Rep invariant
    // :true
    // Safety from rep exposure
    // :all fields are private, final and immutable

    public Variable(String variable) {
        this.variable = variable;
    }

    public String getVariable() {
        return variable;
    }

    @Override
    public String toString() {
        return variable;
    }

    @Override
    public boolean equals(Object thatObject) {
        if (!(thatObject instanceof Variable)) {
            return false;
        }

        Variable thatVariable = (Variable) thatObject;
        return thatVariable.getVariable().equals(variable);
    }

    @Override
    public int hashCode() {
        return variable.hashCode();
    }

    @Override
    public Expression differentiate(String variable) {
        if (this.variable.equals(variable)) {
            return Expression.makeNumber(1);
        } else {
            return Expression.makeNumber(0);
        }
    }

    @Override
    public double simplify(Map<String, Double> environment) throws NotANumberException {
        if (environment.containsKey(variable)) {
            return environment.get(variable);
        } else {
            throw new NotANumberException();
        }
    }
}
