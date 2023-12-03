package vanEmdeBoasTree;

import vanEmdeBoasTree.VanEmdeBoasTree;

public class Main {
    public static void main(String[] args) {
        final long universeSize = 16;
        testInsert(universeSize);
    }

    private static void testInsert(long universeSize) {
        System.out.println("Testing insert method:");
        VanEmdeBoasTree tree = new VanEmdeBoasTree(universeSize);
        tree.insert(5);
        tree.insert(7);
        tree.insert(2);
        tree.insert(3);
        tree.insert(4);
        tree.insert(15);
        tree.insert(14);
        tree.delete(7);
        System.out.print(tree.predecessor(1000));
//        System.out.print("\n");
//        System.out.print(tree.predecessor(6));
//        System.out.print("\n");
        //System.out.print(tree.contains(5));
    }
}

