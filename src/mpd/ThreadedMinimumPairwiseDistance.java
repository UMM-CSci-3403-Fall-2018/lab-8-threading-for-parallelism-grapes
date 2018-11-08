package mpd;

public class ThreadedMinimumPairwiseDistance implements MinimumPairwiseDistance {
    public long globalResult = Integer.MAX_VALUE;

    @Override
    public long minimumPairwiseDistance(int[] values) {
        Thread lowerLeftMPD = new Thread(new LowerLeftMPD(values));
        Thread bottomRightMPD = new Thread(new BottomRightMPD(values));
        Thread topRightMPD = new Thread(new TopRightMPD(values));
        Thread centerMPD = new Thread(new CenterMPD(values));

        lowerLeftMPD.start();
        bottomRightMPD.start();
        topRightMPD.start();
        centerMPD.start();

        try {
            lowerLeftMPD.join();
            bottomRightMPD.join();
            topRightMPD.join();
            centerMPD.join();
        } catch (InterruptedException e) {
            System.exit(1);
        }

        return globalResult;
    }

    public void updateGlobalResult(long localResult) {
        if(localResult < globalResult) {
            globalResult = localResult;
        }
    }


    public class LowerLeftMPD implements Runnable {
        public int[] values;

        public LowerLeftMPD (int[] values) {
            this.values = values;
        }

        public void run() {
            long localResult = Integer.MAX_VALUE;
            for (int i = 0; i < values.length/2; ++i) {
                for (int j = 0; j < i; ++j) {
                    long diff = Math.abs(values[i] - values[j]);
                    if (diff < localResult) {
                        localResult = diff;
                    }
                }
            }

            updateGlobalResult(localResult);
        }
    }

    public class BottomRightMPD implements Runnable {
        public int[] values;

        public BottomRightMPD (int[] values) {
            this.values = values;
        }

        public void run() {
            long localResult = Integer.MAX_VALUE;
            for (int i = values.length/2; i < values.length; ++i) {
                for (int j = 0; j < i - (values.length/2); ++j) {
                    long diff = Math.abs(values[i] - values[j]);
                    if (diff < localResult) {
                        localResult = diff;
                    }
                }
            }

            updateGlobalResult(localResult);
        }
    }

    public class TopRightMPD implements Runnable {
        public int[] values;

        public TopRightMPD (int[] values) {
            this.values = values;
        }

        public void run() {
            long localResult = Integer.MAX_VALUE;
            for (int i = values.length/2; i < values.length; ++i) {
                for (int j = values.length/2; j < i; ++j) {
                    long diff = Math.abs(values[i] - values[j]);
                    if (diff < localResult) {
                        localResult = diff;
                    }
                }
            }

            updateGlobalResult(localResult);
        }
    }

    public class CenterMPD implements Runnable {
        public int[] values;

        public CenterMPD (int[] values) {
            this.values = values;
        }

        public void run() {
            long localResult = Integer.MAX_VALUE;
            for (int i = values.length/2; i < values.length; ++i) {
                for (int j = i - (values.length/2); j < i; ++j) {
                    long diff = Math.abs(values[i] - values[j]);
                    if (diff < localResult) {
                        localResult = diff;
                    }
                }
            }

            updateGlobalResult(localResult);
        }
    }

}
