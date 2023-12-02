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
        tree.insert(6);
        tree.insert(5);
        tree.insert(7);
        tree.insert(4);
        tree.insert(8);
        tree.insert(13);
        tree.insert(14);
        System.out.print(tree.successor(1));
        System.out.print("\n");
        System.out.print(tree.predecessor(7));
    }
}

