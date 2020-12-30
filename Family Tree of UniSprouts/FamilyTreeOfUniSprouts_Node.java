// -----------------------------------------------------------------------------------------------------------------
//              FamilyTreeOfUniSprouts_Node
// -----------------------------------------------------------------------------------------------------------------
public class FamilyTreeOfUniSprouts_Node
{
    private int generationLevel;
    private String name;
    private FamilyTreeOfUniSprouts_Node previous, next, children;

    public FamilyTreeOfUniSprouts_Node()
    {
        generationLevel =- 1;
        name = "";
        previous = next = children = null;
    }
    
    public FamilyTreeOfUniSprouts_Node(String n, int gl, FamilyTreeOfUniSprouts_Node p)
    {
        name = n;
        generationLevel = gl;
        previous = p;
        children = null;
        next = null;
    }
    
    //==================Accessor Methods================== 
    public int getGenerationLevel()
    {
        return generationLevel;        
    }
    
    public String getName()
    {
        return name;
    }
    
    public FamilyTreeOfUniSprouts_Node getNext()
    {
        return next;
    }
    
    public FamilyTreeOfUniSprouts_Node getPrevious()
    {
        return previous;
    }
    
    public FamilyTreeOfUniSprouts_Node getChildren()
    {
        return children;
    }
    
    //==================Mutator Methods==================   
    public void setChildren(FamilyTreeOfUniSprouts_Node child)
    {
        children = child;
    }
    
    public void setNext(FamilyTreeOfUniSprouts_Node n)
    {
        next = n;
    }    
}  // FamilyTreeOfUniSprouts_Node
 