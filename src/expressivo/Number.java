/**
 * 
 */
package expressivo;

/**
 * @author pao214
 * Immutable.
 * 
 */
public class Number implements Expression {
    double number;
    
    Number(double number)
    {
        this.number = number;
    }
    
    public double getNumber()
    {
        return number;
    }
    
    @Override
    public String toString() {
        return String.valueOf(number);
    }
    
    @Override
    public boolean equals(Object thatObject) {
        if (!(thatObject instanceof Number))
        {
            return false;
        }
        
        Number thatNumber = (Number) thatObject;
        return number == thatNumber.getNumber();
    }
    
    @Override
    public int hashCode() {
        return Double.valueOf(number).hashCode();
    }
}
