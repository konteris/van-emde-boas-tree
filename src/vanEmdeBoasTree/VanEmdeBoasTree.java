package vanEmdeBoasTree;

public class VanEmdeBoasTree {
    private VanEmdeBoasTree clusters[];
    private VanEmdeBoasTree summary;
    private Long min;
    private Long max;
    private long universeSize;
    private long numberOfClusters;

    public VanEmdeBoasTree(long universeSize) {
        this.universeSize = universeSize;
        if (universeSize > 2) {
            this.numberOfClusters = (long) Math.sqrt(universeSize);
            this.summary = new VanEmdeBoasTree(numberOfClusters);
            this.clusters = new vanEmdeBoasTree.VanEmdeBoasTree[(int) numberOfClusters];
            for (long i = 0; i < numberOfClusters; i++)
                this.clusters[(int) i] = new vanEmdeBoasTree.VanEmdeBoasTree(numberOfClusters);
        }
    }

    public void insert(long x) {
        if (universeSize == 2) {
            if (this.min == null)
                this.min = this.max = x;
            else {
                if (x < this.min)
                    this.min = x;
                else if (x > this.max)
                    this.max = x;
            }
            return;
        }
        if (this.min == null) {
            this.min = this.max = x;
            return;
        }
        if (this.min > x) {
            long temp = this.min;
            this.min = x;
            x = temp;
        }
        if (this.max < x)
            this.max = x;
        long clusterToInsert = high(x);
        long positionToInsert = low(x);

        if (this.clusters[(int) clusterToInsert].min == null) // if cluster is empty
            this.summary.insert(clusterToInsert);
        else
            this.clusters[(int) clusterToInsert].insert(positionToInsert);
    }

    private long high(long x) {
        return x / numberOfClusters;
    }

    private long low(long x) {
        return x % numberOfClusters;
    }

    private long index(long i, long j) {
        return i * numberOfClusters + j;
    }

    public void printTree() {
        printTree(this, 0, "");
    }

    private void printTree(VanEmdeBoasTree tree, int depth, String indent) {
        if (tree == null) {
            return;
        }

        for (long i = 0; i < tree.numberOfClusters; i++) {
            printTree(tree.clusters[(int) i], depth + 1, indent + "  |");
        }

        if (depth == 0) {
            if (tree.min != null) {
                System.out.println(indent + "Min: " + tree.min);
            }

            if (tree.max != null) {
                System.out.println(indent + "Max: " + tree.max);
            }
        } else {
            System.out.println(indent + String.valueOf(tree.min));
        }
    }


}
