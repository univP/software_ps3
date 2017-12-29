/**
 * 
 */
package expressivo;

/**
 * @author pao214
 * Immutable.
 *
 */
public class Plus implements Expression {
    private final Expression left, right;
    
    // Abstraction function
    //   represents sum of two expressions left+right
    // Rep invariant
    //   true
    // Safety from rep exposure
    //   all fields are private, final and immutable
    
    Plus(Expression left, Expression right)
    {
        this.left = left;
        this.right = right;
    }
    
    public Expression getLeft()
    {
        return left;
    }
    
    public Expression getRight()
    {
        return right;
    }
    
    /**
     * Each expression surrounded by brackets and joined using +
     */
    @Override
    public String toString() {
        return "("+left.toString()+")+("+right.toString()+")";
    }
    
    @Override
    public boolean equals(Object thatObject) {
        if (!(thatObject instanceof Plus))
        {
            return false;
        }
        
        Plus thatPlus = (Plus) thatObject;
        return thatPlus.getLeft().equals(left) && thatPlus.getRight().equals(right);
    }
    
    @Override
    public int hashCode() {
        return left.hashCode()^right.hashCode();
    }
}
