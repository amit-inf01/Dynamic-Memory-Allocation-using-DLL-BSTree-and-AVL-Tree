public class A2DynamicMem extends A1DynamicMem {

    public A2DynamicMem() {  super(); }

    public A2DynamicMem(int size) { super(size); }

    public A2DynamicMem(int size, int dict_type) { super(size, dict_type); }

    // In A2, you need to test your implementation using BSTrees and AVLTrees.
    // No changes should be required in the A1DynamicMem functions.
    // They should work seamlessly with the newly supplied implementation of BSTrees and AVLTrees
    // For A2, implement the Defragment function for the class A2DynamicMem and test using BSTrees and AVLTrees.

    public int Free(int startAddr) {
        Dictionary l = allocBlk.getFirst();
        while (l!=null&&l.address!=startAddr){
            l=l.getNext();
        }
        if (l==null)
        { return -1;}
        else {
            freeBlk.Insert(l.address,l.size,l.key);
            allocBlk.Delete(l);
        }
        return 0;
    }
    //Your BST (and AVL tree) implementations should obey the property that keys in the left subtree <= root.key < keys in the right subtree. How is this total order between blocks defined? It shouldn't be a problem when using key=address since those are unique (this is an important invariant for the entire assignment123 module). When using key=size, use address to break ties i.e. if there are multiple blocks of the same size, order them by address. Now think outside the scope of the allocation problem and think of handling tiebreaking in blocks, in case key is neither of the two.
    public void Defragment() {
        Dictionary T;
        if (type == 2){
            T = new BSTree();
        }
        else {
            T = new AVLTree();
        }
        Dictionary F = freeBlk.getFirst();
        while (F!=null){
            T.Insert(F.address,F.size,F.address);
            F = F.getNext();
        }
        Dictionary d = T.getFirst();
        while (d!=null&&d.getNext()!=null){
            if (d.address+d.size==d.getNext().address){
                Dictionary d1 = new BSTree(d.address,d.size,d.size);
                Dictionary d2 = new BSTree(d.getNext().address,d.getNext().size,d.getNext().size);
                freeBlk.Delete(d1);
                freeBlk.Delete(d2);
                d1 = null;
                d2 = null;
                d.size= d.size+d.getNext().size;
                freeBlk.Insert(d.address,d.size,d.size);
                T.Delete(d.getNext());
            }
            else {
                d = d.getNext();
            }
        }
        T = null;
    }
}