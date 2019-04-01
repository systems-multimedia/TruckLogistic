package Controller;

import GUI.ProgramWindow;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class BankerAlgorithm {

    public static Integer[][] modelArray;
    public static String[] titles;
    private int[][] max, allocated, need, lockedP;
    private int[] resources, available, work;
    private boolean[] finish;
    private int n_Routes, n_Orders;
    private int blockedProcesses = 0;
    private Random random = new Random();

    /**
     *
     * @param max
     * @param res
     * @param n_Routes
     * @param n_Orders
     */
    public BankerAlgorithm(int[][] max, int[] res, int n_Routes, int n_Orders) {
        this.max = max;
        this.resources = res;
        this.allocated = new int[n_Routes][n_Orders];
        this.need = new int[n_Routes][n_Orders];
        this.lockedP = new int[n_Routes][n_Orders];
        this.available = new int[n_Routes];
        this.finish = new boolean[n_Routes];
        BankerAlgorithm.modelArray = new Integer[n_Routes][n_Orders];
        BankerAlgorithm.titles = new String[n_Routes];
        this.n_Routes = n_Routes;
        this.n_Orders = n_Orders;

        for (int i = 0; i < n_Routes; i++) {
            System.out.println(Arrays.toString(max[i]));
        }

        boolean safe = false;

        while (!safe) {

            this.allocated = max;
            this.max = max;
            this.need = allocated;
            this.resources = res;
            this.available = res;

            System.out.println("Values");
            System.out.println("max " + Arrays.deepToString(this.max));
            System.out.println("allocated " + Arrays.deepToString(allocated));
            System.out.println("need " + Arrays.deepToString(need));
            System.out.println("resources " + Arrays.toString(resources));
            System.out.println("available " + Arrays.toString(available));

            asign();
            updAvailable();
            calcNeed();

            int count = 0;

            for (int i = 0; i < n_Orders; i++) {
                for (int j = 0; j < n_Routes; j++) {

                    if (available[j] < need[j][i]) {
                        count++;
                    }
                }
            }

            safe = count < ((n_Routes / 2) + 1);
            System.out.println("safe => " + safe);
        }

        System.out.println("Values Safe");
        System.out.println("max " + Arrays.deepToString(this.max));
        System.out.println("allocated " + Arrays.deepToString(allocated));
        System.out.println("need " + Arrays.deepToString(need));
        System.out.println("resources " + Arrays.toString(resources));
        System.out.println("available " + Arrays.toString(available));

        runSafe();
        if (JOptionPane.showConfirmDialog(null, "Can I close?", "Closing Program", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void asign() {
        //Variables para llenar la matriz asignacion
        int maxVal, disp, random;

        for (int i = 0; i < n_Routes; i++) {
            disp = available[i];
            for (int j = 0; j < n_Orders; j++) {

                if (disp > 0) {

                    maxVal = max[i][j];
                    if (maxVal > disp) {
                        maxVal = disp;
                    }

                    random = ThreadLocalRandom.current().nextInt(0, maxVal + 1);
                    allocated[i][j] = random;

                    if (random < 0) {
                        allocated[i][j] = allocated[i][j] * -1;
                    }

                    disp = disp - allocated[i][j];
                    System.out.println("disp: " + disp);
                } else {
                    allocated[i][j] = disp;
                }
                System.out.println("allocated[" + i + "][" + j + "] = " + allocated[i][j]);
            }
        }
    }

    private void updAvailable() {
        for (int j = 0; j < n_Routes; j++) {
            for (int i = 0; i < n_Orders; i++) {
                available[j] = resources[j] - allocated[j][i];
            }
        }
    }

    private void calcNeed() {
        for (int i = 0; i < n_Routes; i++) {
            for (int j = 0; j < n_Orders; j++) {
                need[i][j] = max[i][j] - allocated[i][j];
            }
        }
    }

    public void runSafe() {
        boolean done[] = new boolean[max.length];
        int temp = 0;
        String msg = "  ";

        while (temp < max.length) {
            System.out.println("\n\nWhile\n\n");
            for (int i = 0; i < n_Orders; i++) {
                if (!done[i] && check(i)) {
                    for (int k = 0; k < n_Routes; k++) {
                        System.out.println("Allocated Before");
                        System.out.println("--Available--");
                        System.out.println(Arrays.toString(available));
                        System.out.println("--Allocated--");
                        System.out.println(Arrays.deepToString(allocated));
                        System.out.println("--Need--");
                        System.out.println(Arrays.deepToString(need));

                        allocated[k][i] = allocated[k][i] + need[k][i];
                        available[k] = available[k] - need[k][i];
                        need[k][i] = need[k][i] - need[k][i];

                        System.out.println("Allocated after");
                        System.out.println("--Available--");
                        System.out.println(Arrays.toString(available));
                        System.out.println("--Allocated--");
                        System.out.println(Arrays.deepToString(allocated));
                        System.out.println("--Need--");
                        System.out.println(Arrays.deepToString(need));

                    }
                    for (int j = 0; j < n_Routes; j++) {
                        available[j] = available[j] + max[j][i];
                        allocated[j][i] = allocated[j][i] - allocated[j][i];
                    }
                    System.out.println("Assigned : " + i);
                    msg = msg + "Order " + (i + 1) + "  ";
                    System.out.println(msg);
                    done[i] = true;
                    temp++;
                }
            }
        }

        System.out.println(msg);

        if (temp == max.length) //si todos los procesos estan asignados
        {
            System.out.println("\nSafe!");
            JOptionPane.showMessageDialog(null, "Safe Status", "NOTIFICATION", JOptionPane.INFORMATION_MESSAGE);
        } else {
            System.out.println("not");
        }

    }

    private boolean check(int i) {
        for (int j = 0; j < n_Routes; j++) {
            if (available[j] < need[j][i]) {
                return false;
            }
        }

        return true;
    }

}
