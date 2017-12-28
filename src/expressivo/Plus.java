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
    Expression left, right;
    
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
