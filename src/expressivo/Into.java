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
    Expression left, right;
    
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
