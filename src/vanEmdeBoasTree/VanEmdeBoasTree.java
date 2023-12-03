package vanEmdeBoasTree;

import java.util.HashMap;

public class VanEmdeBoasTree {
    private HashMap<Long, VanEmdeBoasTree> clusters;
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
            this.clusters = new HashMap<>();
        }
    }

    public boolean contains(long x) {
        if (this.min != null && this.max != null && (x == this.min || x == this.max))
            return true;
        else if (this.universeSize == 2)
            return false;
        else
            return this.clusters.get(high(x)).contains(low(x));
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

        if (!this.clusters.containsKey(clusterToInsert)) {
            this.clusters.put(clusterToInsert, new VanEmdeBoasTree(numberOfClusters));
        }
        if (this.clusters.get(clusterToInsert).min == null) { // if cluster is empty - this.clusters[(int) clusterToInsert].min == null
            this.summary.insert(clusterToInsert); // updatinu summary struktura
        }
        this.clusters.get(clusterToInsert).insert(positionToInsert); // insertinu i atitinkama clusterio pozicija
    }

    public long successor(long x) {
        if (universeSize == 2) {
            if (max != null && x < max) {
                return max;
            }
            return -1;
        }
        if (this.min == null) {
            return -1;
        }
        if (x < min) {
            return min;
        }
        long i = high(x);
        long j = low(x);

        VanEmdeBoasTree clusterOfX = null;
        if (this.clusters.containsKey(i)) {
            clusterOfX = this.clusters.get(i);
        }

        if (clusterOfX != null && clusterOfX.max != null && j < clusterOfX.max) {
            j = clusterOfX.successor(j);
        } else {
            i = this.summary.successor(i);
            if (i == -1)
                return -1;
            j = this.clusters.get(i).min;
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

        VanEmdeBoasTree clusterOfX = null;
        clusterOfX = this.clusters.get(i);

        if (clusterOfX != null && clusterOfX.min != null && j > clusterOfX.min) {
            j = clusterOfX.predecessor(j);
            return index(i, j);
        } else {
            i = this.summary.predecessor(i);
            if (i == -1) {
                if (min != null && x > min)
                    return min;
                else
                    return -1;
            } else {
                j = this.clusters.get(i).max;
                return index(i, j);
            }
        }
    }

    public void delete(long x) {
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
                long minValue = this.clusters.get(minIndex).min;
                this.min = index(minIndex, minValue);
                x = min;
            }
        }

        long indexOfClusterToDeleteXFrom = high(x);
        VanEmdeBoasTree clusterToDeleteXFrom = clusters.get(indexOfClusterToDeleteXFrom);
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
                VanEmdeBoasTree maxCluster = clusters.get(indexMaxCluster);
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
