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
            this.clusters = new VanEmdeBoasTree[(int) numberOfClusters];
            for (long i = 0; i < numberOfClusters; i++)
                this.clusters[(int) i] = new VanEmdeBoasTree(numberOfClusters);
        }
    }

    public boolean contains(long x) {
        if (x >= universeSize)
            throw new IllegalArgumentException("Element must be in the set of the universe {0, 1, 2, ..., u-1}");
        if (this.min != null && this.max != null && (x == this.min || x == this.max))
            return true;
        else if (this.universeSize == 2)
            return false;
        else
            return this.clusters[(int) high(x)].contains(low(x));
    }

    public void insert(long x) {
        if (x >= universeSize)
            throw new IllegalArgumentException("Element must be in the set of the universe {0, 1, 2, ..., u-1}");
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
        if (x >= universeSize)
            throw new IllegalArgumentException("Element must be in the set of the universe {0, 1, 2, ..., u-1}");
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

    public void delete(long x) {
        if (x >= universeSize)
            throw new IllegalArgumentException("Element must be in the set of the universe {0, 1, 2, ..., u-1}");
        if (universeSize == 2) {
            if (this.min != null) {
                if (this.min.longValue() == this.max.longValue()) {
                    this.min = null;
                    this.max = null;
                } else {
                    if (x == this.min) {
                        this.min = this.max;
                    } else {
                        this.max = this.min;
                    }
                }
            }
            return;
        }

        if (x == min) {
            if (this.summary.min == null) {
                this.min = null;
                this.max = null;
                return;
            } else {
                long minIndex = this.summary.min;
                long minValue = this.clusters[(int) minIndex].min;
                this.min = index(minIndex, minValue);
                x = min;
            }
        }

        long indexOfClusterToDeleteXFrom = high(x);
        VanEmdeBoasTree clusterToDeleteXFrom = clusters[(int) indexOfClusterToDeleteXFrom];
        long positionToDelete = low(x);

        clusterToDeleteXFrom.delete(positionToDelete);
        if (clusterToDeleteXFrom.min == null) {
            summary.delete(indexOfClusterToDeleteXFrom);
        }

        if (x == this.max) {
            if (summary.max == null) {
                this.max = null;
                if (this.min != null)
                    this.max = this.min;
            } else {
                long indexMaxCluster = summary.max;
                VanEmdeBoasTree maxCluster = clusters[(int) indexMaxCluster];
                this.max = index(indexMaxCluster, maxCluster.max);
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

}
