/**
 * 
 */
package expressivo;

/**
 * @author pao214
 * Immutable.
 *
 */
public class Into implements Expression {
    private final Expression left, right;
    
    // Abstraction function
    //   represents product of two expressions left*right
    // Rep invariant
    //   true
    // Safety from rep exposure
    //   all fields are private, final and immutable
    
    Into(Expression left, Expression right)
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
     * Each expression surrounded by brackets and joined using into
     */
    @Override
    public String toString() {
        return "("+left.toString()+")*("+right.toString()+")";
    }
    
    @Override
    public boolean equals(Object thatObject) {
        if (!(thatObject instanceof Into))
        {
            return false;
        }
        
        Into thatInto = (Into) thatObject;
        return thatInto.getLeft().equals(left) && thatInto.getRight().equals(right);
    }
    
    @Override
    public int hashCode() {
        return left.hashCode()^right.hashCode();
    }
}
