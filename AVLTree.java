public class AVLTree extends BSTree {

    private AVLTree left, right;     // Children.
    private AVLTree parent;          // Parent pointer.
    private int height;  // The height of the subtree

    public AVLTree() {
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node !.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
    }

    public AVLTree(int address, int size, int key) {
        super(address, size, key);
        this.height = 0;
    }

    // Implement the following functions for AVL Trees.
    // You need not implement all the functions.
    // Some of the functions may be directly inherited from the BSTree class and nothing needs to be done for those.
    // Remove the functions, to not override the inherited functions.
    private int setHeight(AVLTree T){
        if (T==null){
            return-1;
        }
        if (T.left!=null){
            T.left.height=setHeight(T.left);
        }
        if (T.right!=null){
            T.right.height=setHeight(T.right);
        }
        if (T.left!=null&&T.right!=null){
            T.height = 1+Math.max(T.left.height,T.right.height);
        }
        else if (T.left!=null){
            T.height = 1+Math.max(-1,T.left.height);
        }
        else if (T.right!= null){
            T.height = 1+Math.max(-1,T.right.height);
        }
        else {
            T.height=0;
        }
        return T.height;
    }
    private int height(AVLTree T){
        if (T == null){
            return -1;
        }
        else {
            return 1 + Math.max(height(T.left),height(T.right));
        }
    }
    public AVLTree Insert(int address, int size, int key) {
        AVLTree T = new AVLTree(address, size, key);
        if (this.parent == null) {
            if (this.right == null) {
                this.right = T;
                T.parent = this;
            } else {
                AVLTree a = null;
                AVLTree b = this.right;
                while (b != null) {
                    a = b;
                    if (key > b.key) {
                        b = b.right;
                    }
                    else if (key<b.key) {
                        b = b.left;
                    }
                    else {
                        if (address<b.address){
                            b = b.left;
                        }
                        else {
                            b=b.right;
                        }
                    }
                }
                if (key > a.key) {
                    a.right = T;
                }
                else if (key<a.key){
                    a.left = T;
                }
                else {
                    if (address<a.address){
                        a.left = T;
                    }
                    else {
                        a.right=T;
                    }
                }
                T.parent = a;
            }
            checkbal(T);
            setHeight(this);
            return T;
        }
        else {
            AVLTree n = this;
            while (n.parent!=null){
                n = n.parent;
            }
            return n.Insert(address,size,key);
        }
    }
    private void checkbal(AVLTree T) {
        if (T.parent != null) {
            if(Math.abs(height(T.left) - height(T.right)) > 1) {
                rebalence(T);
            }
            if (T.parent.parent != null) {
                checkbal(T.parent);
            }
        }
    }
    private void rebalence(AVLTree T){
        if (height(T.left)>height(T.right)){
            if (height(T.left.left)>height(T.left.right)){
                if (T.parent.left==T){
                    T.parent.left = r_rotate(T);
                }
                else {
                    T.parent.right = r_rotate(T);
                }
            }
            else {
                if (T.parent.left==T){
                    T.parent.left = lr_rotate(T);
                }
                else {
                    T.parent.right = lr_rotate(T);
                }
            }
        }
        else {
            if (height(T.right.left)>height(T.right.right)){
                if (T.parent.left==T){
                    T.parent.left = rl_rotate(T);
                }
                else {
                    T.parent.right = rl_rotate(T);
                }
            }
            else {
                if (T.parent.left==T){
                    T.parent.left = l_rotate(T);
                }
                else {
                    T.parent.right = l_rotate(T);
                }
            }
        }
    }
    private AVLTree r_rotate(AVLTree T){
        AVLTree t = T.left;
        T.left = T.left.right;
        t.parent= T.parent;
        t.right = T;
        if (T.left!=null) {
            T.left.parent = T;
        }
        T.parent = t;
        return t;
    }
    private AVLTree l_rotate(AVLTree T){
        AVLTree t = T.right;
        T.right = T.right.left;
        t.parent= T.parent;
        t.left = T;
        if (T.right!=null) {
            T.right.parent = T;
        }
        T.parent = t;
        return t;
    }
    private AVLTree lr_rotate(AVLTree T){
        T.left = l_rotate(T.left);
        return r_rotate(T);
    }
    private AVLTree rl_rotate(AVLTree T){
        T.right = r_rotate(T.right);
        return l_rotate(T);
    }


    public boolean Delete(Dictionary e) {
        if (this.parent == null) {
            if (e == null) {
                return true;
            }
            AVLTree r = this.right;
            while (r != null && (r.address != e.address || r.size != e.size || r.key != e.key)) {
                if (e.key > r.key) {
                    r = r.right;
                } else if (e.key<r.key){
                    r = r.left;
                }
                else {
                    if (e.address>r.address){
                        r = r.right;
                    }
                    else {
                        r = r.left;
                    }
                }
            }
            if (r == null) {
                return false;
            } else {
                if (r.left == null && r.right == null) {
                    if (r.parent.left == r) {
                        r.parent.left = null;
                    } else {
                        r.parent.right = null;
                    }
                    checkbal(r.parent);
                    r.parent = null;

                } else if (r.left == null) {
                    r.right.parent = r.parent;
                    if (r.parent.left == r) {
                        r.parent.left = r.right;
                    } else {
                        r.parent.right = r.right;
                    }
                    r.right = null;
                    checkbal(r.parent);
                    r.parent = null;
                } else if (r.right == null) {
                    r.left.parent = r.parent;
                    if (r.parent.left == r) {
                        r.parent.left = r.left;
                    } else {
                        r.parent.right = r.left;
                    }
                    r.left = null;
                    checkbal(r.parent);
                    r.parent = null;
                } else {
                    AVLTree T = (AVLTree) r.getNext();
                    r.address = T.address;
                    r.size = T.size;
                    r.key = T.key;
                    if (T.right == null) {
                        if (T.parent.left == T) {
                            T.parent.left = null;
                        } else {
                            T.parent.right = null;
                        }
                        checkbal(T.parent);
                        T.parent = null;
                    } else {
                        T.right.parent = T.parent;
                        if (T.parent.left == T) {
                            T.parent.left = T.right;
                        } else {
                            T.parent.right = T.right;
                        }
                        T.right = null;
                        checkbal(T.parent);
                        T.parent = null;
                    }
                }
            }
            setHeight(this);
            return true;

        }
        else {
            AVLTree n = this;
            while (n.parent!=null){
                n = n.parent;
            }
            return n.Delete(e);
        }
    }
    public AVLTree Find(int key, boolean exact) {
        if (this.parent == null) {
            if (exact) {
                AVLTree b = this.right;
                while (b != null && b.key != key) {
                    if (key > b.key) {
                        b = b.right;
                    } else {
                        b = b.left;
                    }
                }
                if (b == null) {
                    return null;
                } else {
                    return b;
                }
            } else {
                AVLTree b = (AVLTree) this.getFirst();
                while (b != null && key > b.key) {
                    b = (AVLTree) b.getNext();
                }
                return b;
            }
        }
        else {
            AVLTree n = this;
            while (n.parent!=null){
                n = n.parent;
            }
            return n.Find(key,exact);
        }
    }
    public AVLTree getFirst() {
        if (this.parent==null){
            AVLTree a = null;
            AVLTree r = this.right;
            while (r!= null){
                a = r;
                r = r.left;
            }
            return a;
        }
        else {
            AVLTree n = this;
            while (n.parent!=null){
                n = n.parent;
            }
            return n.getFirst();
        }
    }

    public AVLTree getNext()
    {
        if(this.right!=null){
            AVLTree a = this;
            AVLTree b = this.right;
            while (b!=null){
                a = b;
                b = b.left;
            }
            return a;
        }
        else {
            AVLTree a = this;
            AVLTree b = this.parent;
            while (b!=null&& b.left!=a){
                a = b;
                b = b.parent;
            }
            return b;
        }
    }

    private AVLTree min(AVLTree T){
        AVLTree a = T;
        AVLTree b = T.right;
        while (b!= null){
            a = b;
            b = b.left;
        }
        return a;
    }
    private AVLTree max(AVLTree T){
        AVLTree a = T;
        AVLTree b = T.left;
        while (b!= null){
            a = b;
            b = b.right;
        }
        return a;
    }
    private boolean check(AVLTree T) {
        if (T == null)
            return true;
        AVLTree m = min(T);
        AVLTree M = max(T);
        if (T.key > m.key || T.key < M.key) {
            return false;
        }
        if (T.key == m.key) {
            if (T.address > m.address) {
                return false;
            }
        }
        if (T.key == M.key) {
            if (T.address < M.address)
                return false;
        }
        if (T.parent != null) {
            if (!(T.parent.left == T || T.parent.right == T)) {
                return false;
            }
        }
        if (T.left != null && T.right != null) {
            if (T.right.parent != T || T.left.parent != T) {
                return false;
            }
        } else if (T.left != null) {
            if (T.left.parent != T)
                return false;
        } else if (T.right != null) {
            if (T.right.parent != T)
                return false;
        }
        if (T.left != null && T.right != null) {
            if (Math.abs(T.left.height - T.right.height) > 1) {
                return false;
            }
        }
        if (T.left == null && T.right != null) {
            if (T.right.height > 0) {
                return false;
            }
        }
        if (T.right == null && T.left != null) {
            if (T.left.height > 0)
                return false;
        }
        if (!check(T.left)||!check(T.right)){
            return false;
        }
        return true;
    }
    public boolean sanity(){
        AVLTree n = this;
        while (n.parent!=null) {
            if (!(n.parent.left == n || n.parent.right == n)) {
                return false;
            }
            n = n.parent;
        }
        if (n.address!=-1||n.size!=-1||n.key!=-1){
            return false;
        }
        return check(n.right);
    }
}