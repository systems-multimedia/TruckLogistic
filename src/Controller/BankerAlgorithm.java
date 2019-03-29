package Controller;

import java.util.Random;

public class BankerAlgorithm {

    private int[][] max, allocated, need;
    private int[] resources, available;
    private int n_Routes, n_Orders;
    private Random random = new Random();

    public BankerAlgorithm(int[][] max, int[] res, int n_Routes, int n_Orders) {
        this.max = max;
        this.resources = res;
        this.allocated = new int[n_Routes][n_Orders];
        this.need = new int[n_Routes][n_Orders];
        this.available = new int[n_Routes];
    }

    private void asign() {
        for (int i = 0; i < n_Routes; i++) {
            resources[i] = 0;
            available[i] = 0;
            for (int j = 0; j < n_Orders; j++) {
                need[i][j] = 0;
            }
        }
        
        for (int i = 0; i < n_Routes; i++) {
            for (int j = 0; j < n_Orders; j++) {
                allocated[i][j] = random.nextInt() & max[i][j];
                need[i][j] = max[i][j] - allocated[i][j];
            }
        }
    }

}
