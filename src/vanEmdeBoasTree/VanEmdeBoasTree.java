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
            if (min == null) {
                min = x;
                max = x;
            } else {
                if (x < min) {
                    min = x;
                } else if (x > max)
                    max = x;
            }
            return;
        }
        if (min == null) {
            min = x;
            max = x;
            return;
        }
        if (x < min) {
            long temp = min;
            min = x;
            x = temp;
        }
        if (x > max)
            max = x;

        long clusterToInsert = high(x);
        long positionToInsert = low(x);

        if (this.clusters[(int) clusterToInsert].min == null) { // if cluster is empty
            this.summary.insert(clusterToInsert);
        }
        this.clusters[(int) clusterToInsert].insert(positionToInsert);
    }

    public long successor(long x) {
        if (universeSize == 2) {
            if (max != null && x < max)
                return max;
            return -1;
        }
        if (min != null && x < min) {
            return min;
        }
        long i = high(x);
        long j = low(x);
        VanEmdeBoasTree clusterOfX = this.clusters[(int) i];

        if (clusterOfX.max != null && j < clusterOfX.max) {
            j = clusterOfX.successor((int) low(x));
        } else {
            i = this.summary.successor(i);
            if (i == -1)
                return -1;
            j = this.clusters[(int) i].min;
        }
        return index(i, j);
    }

    public long predecessor(long x) {
        if (universeSize == 2) {
            if (min != null && min < x)
                return min;
            return -1;
        }
        if (max != null && max < x)
            return max;
        long i = high(x);
        long j = low(x);

        VanEmdeBoasTree clusterOfX = this.clusters[(int) i];
        if (clusterOfX.min != null && j > clusterOfX.min) {
            j = clusterOfX.predecessor(low(x));
            return index(i, j);
        } else {
            i = this.summary.predecessor(i);
            if (i == -1) {
                if (min != null && x > min)
                    return min;
                else
                    return -1;
            } else {
                j = this.clusters[(int) i].max;
                return index(i, j);
            }
        }
    }

    private long high(long x) {
        return x / numberOfClusters;
    }

    private long low(long x) {
        return x % numberOfClusters;
    }

    private long index(long i, long j) {
        if (i == -1 || j == -1)
            return -1;
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
