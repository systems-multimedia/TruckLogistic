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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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

        for (int i = 0; i < n_Routes; i++) {
            for (int j = 0; j < n_Orders; j++) {
                modelArray[i][j] = max[i][j];
                titles[i] = "Route (" + i + ")";
            }
        }

        System.out.println(Arrays.toString(res));

        JLabel title1 = new JLabel("Max");
        title1.setBounds(0, 0, (Toolkit.getDefaultToolkit().getScreenSize().width * 75) / 100, 20);
        title1.setFont(new Font("Tahoma", 0, ((Toolkit.getDefaultToolkit().getScreenSize().height * 10) / 100) * 2 / 100));
        title1.setHorizontalAlignment(SwingConstants.CENTER);

        JScrollPane scroll = new JScrollPane();
        JTable table = new JTable(modelArray, titles);
        //table.setBounds(0,20,(Toolkit.getDefaultToolkit().getScreenSize().width * 75) / 100, (Toolkit.getDefaultToolkit().getScreenSize().height * 10) / 100);
        table.setPreferredScrollableViewportSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width * 75 / 100, (Toolkit.getDefaultToolkit().getScreenSize().height * 10) / 100));
        table.setFillsViewportHeight(true);
        scroll.setViewportView(table);
        scroll.setLocation(0, 20);

        JButton button = new JButton("try");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                asign();
            }
        });
        JFrame w = new JFrame("Table");
        w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        w.setSize(new Dimension((Toolkit.getDefaultToolkit().getScreenSize().width * 75) / 100, (Toolkit.getDefaultToolkit().getScreenSize().height * 75) / 100));
        w.setResizable(false);
        w.add(title1);
        w.add(scroll);
        w.add(button, BorderLayout.SOUTH);
        w.setVisible(true);

        ProgramWindow.table.setModel(new DefaultTableModel(modelArray, titles));

    }

    private void asign() {
        for (int i = 0; i < n_Routes; i++) {
            available[i] = 0;
            finish[i] = false;
            for (int j = 0; j < n_Orders; j++) {
                need[i][j] = 0;
                lockedP[i][j] = 0;
            }
        }

        for (int i = 0; i < n_Routes; i++) {
            for (int j = 0; j < n_Orders; j++) {
                if (max[i][j] != 0) {
                    allocated[i][j] = random.nextInt(max[i][j]) % max[i][j];
                } else {
                    allocated[i][j] = 0;
                }
                need[i][j] = max[i][j] - allocated[i][j];
            }
        }

        System.out.println("************** ALLOCATED *************");
        for (int i = 0; i < n_Routes; i++) {
            System.out.println(Arrays.toString(allocated[i]));
        }

        if (safe()) {
            System.out.println("***************************");
            System.out.println("*          SAFE           *");
            System.out.println("***************************");
        }
    }

    /**
     * public boolean safe() { finish = new boolean[n_Routes]; for (int i = 0; i
     * < finish.length; i++) { finish[i] = false; } work = available; int cont =
     * 0; for (int i = 0; i < n_Routes; i++) { for (int j = 0; j < n_Orders; j++) {
     * if (finish[i] == false) {
     * if (need[i][j] > work[j]) { j = n_Orders; blockedProcesses++;
     * System.out.println("Process " + i + " Blocked!!"); } else { cont++; if
     * (cont == n_Orders) { addWork(need[i]); if (blockedProcesses > 0) {
     * blockedProcesses--; } finish[i] = true; } } } } if (blockedProcesses > 0)
     * { i = 0; } }
     *
     * for (int i = 0; i < finish.length; i++) { if (finish[i] == false) {
     * return false; } } return true; }
     */
    private boolean safe() {
        boolean locked = false;
        boolean possible = true;
        int[][] rest = allocated;
        work = available;
        while (possible) {
            for (int i = 0; i < n_Routes; i++) {
                if (finish[i] == false) {
                    for (int j = 0; j < lockedP[i].length; j++) {
                        if (lockedP[i][j] != 0) {
                            locked = true;
                        }
                    }

                    if (!locked) {
                        boolean allow = true;
                        for (int j = 0; j < n_Orders; j++) {
                            if (need[i][j] > available[j]) {
                                allow = false;
                            }
                        }

                        if (allow) {
                            for (int j = 0; j < n_Orders; j++) {
                                work[j] += allocated[i][j];
                                endProcess(i);
                            }
                        } else {
                            //lockProcess(i);
                            possible = false;
                        }
                    }
                }
            }
        }

        int cont = 0;
        for (int i = 0; i < finish.length; i++) {
            if (finish[i] == true) {
                cont++;
            }
        }

        return cont == finish.length;
    }

    private void endProcess(int index) {
        boolean done = true;
        for (int i = 0; i < allocated[index].length; i++) {
            if (allocated[index][i] != max[index][i]) {
                done = false;
            }
        }

        if (done) {
            System.out.println("Process " + index + " done");
            finish[index] = true;
        }
    }

    private void lockProcess(int index) {
        for (int i = 0; i < allocated[index].length; i++) {
            lockedP[index][i] = 1;
        }

        System.out.println("Process " + index + " locked");
    }

    private void addWork(int[] array) {
        for (int i = 0; i < work.length; i++) {
            work[i] += array[i];
        }
    }

}
