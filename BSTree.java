public class BSTree extends Tree {

    private BSTree left, right;     // Children.
    private BSTree parent;          // Parent pointer.

    public BSTree(){
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node!.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
    }

    public BSTree(int address, int size, int key){
        super(address, size, key);
    }

    public BSTree Insert(int address, int size, int key) {
        BSTree T = new BSTree(address, size, key);
        if (this.parent == null) {
            if (this.right == null) {
                this.right = T;
                T.parent = this;
            } else {
                BSTree a = null;
                BSTree b = this.right;
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
            return T;
        }
        else {
            BSTree n = this;
            while (n.parent!=null){
                n = n.parent;
            }
            return n.Insert(address,size,key);
        }
    }

    public boolean Delete(Dictionary e) {
        if (this.parent == null) {
            if (e == null) {
                return true;
            }
            BSTree r = this.right;
            while (r != null && (r.address != e.address || r.size != e.size || r.key != e.key)) {
                if (e.key > r.key) {
                    r = r.right;
                } else if (e.key<r.key){
                    r = r.left;
                }else {
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
                    r.parent = null;
                } else if (r.left == null) {
                    r.right.parent = r.parent;
                    if (r.parent.left == r) {
                        r.parent.left = r.right;
                    } else {
                        r.parent.right = r.right;
                    }
                    r.right = null;
                    r.parent = null;
                } else if (r.right == null) {
                    r.left.parent = r.parent;
                    if (r.parent.left == r) {
                        r.parent.left = r.left;
                    } else {
                        r.parent.right = r.left;
                    }
                    r.left = null;
                    r.parent = null;
                } else {
                    BSTree T = r.getNext();
                    r.address = T.address;
                    r.size = T.size;
                    r.key = T.key;
                    if (T.right == null) {
                        if (T.parent.left == T) {
                            T.parent.left = null;
                        } else {
                            T.parent.right = null;
                        }
                        T.parent = null;
                    } else {
                        T.right.parent = T.parent;
                        if (T.parent.left == T) {
                            T.parent.left = T.right;
                        } else {
                            T.parent.right = T.right;
                        }
                        T.right = null;
                        T.parent = null;
                    }
                }
            }
            return true;
        }
        else {
            BSTree n = this;
            while (n.parent!=null){
                n = n.parent;
            }
            return n.Delete(e);
        }
    }

    public BSTree Find(int key, boolean exact) {
        if (this.parent == null) {
            if (exact) {
                BSTree b = this.right;
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
                BSTree b = this.getFirst();
                while (b != null && key > b.key) {
                    b = b.getNext();
                }
                return b;
            }
        }
        else {
            BSTree n = this;
            while (n.parent!=null){
                n = n.parent;
            }
            return n.Find(key,exact);
        }
    }
    public BSTree getFirst() {
        if (this.parent==null){
            BSTree a = null;
            BSTree r = this.right;
            while (r!= null){
                a = r;
                r = r.left;
            }
            return a;
        }
        else {
            BSTree n = this;
            while (n.parent!=null){
                n = n.parent;
            }
            return n.getFirst();
        }
    }

    public BSTree getNext()
    {
        if(this.right!=null){
            BSTree a = this;
            BSTree b = this.right;
            while (b!=null){
                a = b;
                b = b.left;
            }
            return a;
        }
        else {
            BSTree a = this;
            BSTree b = this.parent;
            while (b!=null&& b.left!=a){
                a = b;
                b = b.parent;
            }
            return b;
        }
    }
    private BSTree min(BSTree T){
        BSTree a = T;
        BSTree b = T.right;
        while (b!= null){
            a = b;
            b = b.left;
        }
        return a;
    }
    private BSTree max(BSTree T){
        BSTree a = T;
        BSTree b = T.left;
        while (b!= null){
            a = b;
            b = b.right;
        }
        return a;
    }
    private boolean check(BSTree T) {
        if (T == null)
            return true;
        BSTree m = min(T);
        BSTree M = max(T);
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
        if (!check(T.left)||!check(T.right)){
            return false;
        }
        return true;
    }
    public boolean sanity(){
        BSTree n = this;
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