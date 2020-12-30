 /**
 * FamilyTreeOfUniSprouts_CLIENT:
 */
 
import java.util.*;
import java.util.Scanner;
 
public class FamilyTreeOfUniSprouts
{
    static FamilyTreeOfUniSprouts_Node root;
    static String names[][] = {{"Jones","ROOT-Node"},
    {"Bill","Jones"},  {"Katy","Jones"}, {"Mike","Jones"}, {"Tom","Jones"},
    {"Dave1","Bill"},  {"Mary","Bill"}, {"Leo","Katy"}, {"Betty","Mike"}, 
    {"Roger","Mike"}, {"Larry","Mary"}, {"Paul","Mary"}, {"Penny","Mary"},
    {"Don","Betty"}, {"Petter","Paul"}, {"Dave2","Don"}};    
 
 
    // ====================================================================
    // =========================== MAIN ==================================== 
    // ====================================================================
    public static void main(String...args)
    {
        int  playAgain;
        String name, namesList= "\n"; 
        Scanner sc = new Scanner(System.in);
        
        // Build the Family of UniSprouts Tree       
        buildFamilyTreeOfUniSprouts();
        
        do 
        {            
            // Gather names
            for(int r = 0; r < names.length; r++) 
            {
                namesList += "   " + names[r][0] + "     \t" + names[r][1] + "\n";                
            }
            
            // Output namesList         
            System.out.println(" nameList: \n  Parent Child" + namesList);
          
            printFamilyTreeOfUniSprouts(root);            
            
            // Input name + print all relatives
            System.out.print("\n\n Enter a name from which to get GrandParent/Parent/Siblings/Cousins/Children/GrandChildren: ");
            name = sc.next();
            printRelatives(name);
                                   
            // Play Again?
            System.out.print("\n\n Play Again? (1==yes, 2==no): ");
            playAgain = sc.nextInt();            
            
        } while(playAgain == 1);        
    } // main  
    
    public static FamilyTreeOfUniSprouts_Node search(FamilyTreeOfUniSprouts_Node n, String person) 
    {
        if(n == null || n.getName().equals(person)) // Parent
        {
            return n;
        } 
        else if(search(n.getChildren(),person) == null) 
        {
            return search(n.getNext(),person);
        } 
        else 
        {
            return search(n.getChildren(),person);
        }
    }
 
    // ==================== buildFamilyTreeOfUniSprouts ====================== 
    public static void buildFamilyTreeOfUniSprouts()
    {      
        root = new FamilyTreeOfUniSprouts_Node(names[0][0], 0, null); // ASsuming that the root node will ALWAYS be the first element of the array
        
        for(int i = 1; i < names.length; i++) 
        {
            FamilyTreeOfUniSprouts_Node parent = search(root, names[i][1]);
            FamilyTreeOfUniSprouts_Node child;
            FamilyTreeOfUniSprouts_Node currentChild = parent.getChildren();
            
            if(currentChild==null) // So when there is no children in parent (setting the first child)                
            { 
                child = new FamilyTreeOfUniSprouts_Node(names[i][0], parent.getGenerationLevel() + 1, parent);
            } 
            else 
            {
                while(currentChild.getNext()!= null) 
                {
                    currentChild = currentChild.getNext();
                }
                
                child = new FamilyTreeOfUniSprouts_Node(names[i][0], parent.getGenerationLevel() + 1, currentChild);
            }
            
            placeNodeInFamilyTreeOfUniSprouts(parent, child);            
        }        
    }  // buildFamilyTreeOfUniSprouts()
    
    // ============== placeNodeInFamilyTreeOfUniSprouts  ================= 
    public static void placeNodeInFamilyTreeOfUniSprouts(FamilyTreeOfUniSprouts_Node parent, FamilyTreeOfUniSprouts_Node child)
    {    
        if(parent.getChildren() == null) 
        {
            parent.setChildren(child);
        }
        else 
        {
            FamilyTreeOfUniSprouts_Node current = parent.getChildren();
            
            while(current.getNext() != null) 
            {
                current = current.getNext();
            }
            
            current.setNext(child); 
        } 
    } // placeNodeInFamilyTreeOfUniSprouts()
    
    // ==================== printFamilyTreeOfUniSprouts  ======================== 
    public static void printFamilyTreeOfUniSprouts(FamilyTreeOfUniSprouts_Node current)
    {  
        System.out.print(current.getName()+"'s children: ");
        
        if(current.getChildren() == null) 
        {
            System.out.println("None");
        } 
        else 
        {
            FamilyTreeOfUniSprouts_Node children = current.getChildren();
            
            while(children != null) 
            {
                System.out.print(children.getName() + " ");
                children = children.getNext();
            }
            
            System.out.println();
        }
        
        if(current.getNext()!= null) 
        {
            printFamilyTreeOfUniSprouts(current.getNext());
        }
        
        if(current.getChildren()!= null) 
        {
            printFamilyTreeOfUniSprouts(current.getChildren());
        }
    }  // printFamilyTreeOfUniSprouts()
    
    // ========================= printRelatives =============================== 
    public static void printRelatives(String name)
    {    
        //Cousins require recursion
        FamilyTreeOfUniSprouts_Node person = search(root, name);  
        
        //getting the old grandparent! (person.getGenerationLevel()-2==current.getGenerationLevel()); // cant find grandparent if gen level is 1
        FamilyTreeOfUniSprouts_Node Grandparent = findGrandParent(person);
        FamilyTreeOfUniSprouts_Node Parent = findParent(person);
        
        if(Grandparent != null) 
        {
            System.out.println("Grandparent: "+Grandparent.getName());
        } 
        else 
        {
            System.out.println("Grandparent: None");
        }
        
        if(Parent != null) 
        {
            System.out.println("Parent: "+Parent.getName());
        } 
        else 
        {
            System.out.println("Parent: None");
        }
        
        //siblings
        FamilyTreeOfUniSprouts_Node Siblings = Parent.getChildren();
        System.out.print("Siblings: ");
        
        while(Siblings != null) 
        {
            if(!Siblings.getName().equals(name)) 
            {
                System.out.print(Siblings.getName() + " ");
            }
            
            Siblings = Siblings.getNext();
        }
        
        System.out.println();
        System.out.print("Cousins: ");
        
        getCousins(root, person);
        
        System.out.println();
        
        FamilyTreeOfUniSprouts_Node children, gc;
        
        System.out.print("Children: ");
        
        if(hasChildren(person)) 
        {
            children = person.getChildren();
            gc = person.getChildren();
            
            while(children != null) 
            {
                System.out.print(children.getName() + " ");
                children=children.getNext();
            }
            
            System.out.println();
            getGrandChildren(gc);
        } 
        else 
        {
            System.out.print("None");
        }
        
        System.out.println();        
    }  // printRelatives()
    
    public static void getGrandChildren(FamilyTreeOfUniSprouts_Node children) 
    {
        FamilyTreeOfUniSprouts_Node current;
        
        System.out.print("Grand Children: ");
        
        int count = 0;
        
        while(children != null) 
        {
            if (hasChildren(children)) 
            {
                current = children.getChildren();
                
                while(current != null) 
                {
                    System.out.print(current.getName() + " ");
                    current = current.getNext();
                }
                
                count++;
            }
            
            children = children.getNext();
        }
        
        if(count == 0) 
        {
            System.out.print("None");
        }
        
        System.out.println();         
    }
    
    public static void getCousins(FamilyTreeOfUniSprouts_Node c, FamilyTreeOfUniSprouts_Node person) 
    {
        if(c.getGenerationLevel() == person.getGenerationLevel() && findParent(c) != findParent(person)) 
        {
            System.out.print(c.getName() + " ");
        }
        
        if(c.getNext() != null) 
        {
            getCousins(c.getNext(), person);
        }
        
        if (c.getChildren() != null) 
        {
            getCousins(c.getChildren(), person);
        }
    }
    
    public static boolean hasChildren(FamilyTreeOfUniSprouts_Node p) 
    {
        return p.getChildren() != null;
    }
    
    public static FamilyTreeOfUniSprouts_Node findParent(FamilyTreeOfUniSprouts_Node person) 
    {
        FamilyTreeOfUniSprouts_Node current = null;
        
        if(person.getGenerationLevel() >= 1) 
        {
            current = person;
            
            while(current.getPrevious() != null && current.getGenerationLevel() != person.getGenerationLevel() - 1) 
            {
                current = current.getPrevious();
            }
        }
        
        return current;
    } 
    
    public static FamilyTreeOfUniSprouts_Node findGrandParent(FamilyTreeOfUniSprouts_Node person) 
    {
        FamilyTreeOfUniSprouts_Node current;
        
        if(person.getGenerationLevel() <= 1) 
        {
            return null;
        }
        else 
        {
            current = person;
            
            while(current.getPrevious() != null && current.getGenerationLevel() != person.getGenerationLevel() - 2) 
            {
                current = current.getPrevious();                
            }
            
            return current;            
        }
    } 
}  // FamilyTreeOfUniSprouts_CLIENT
 
 
 
 
 
 
 
 
 
 
 
 

