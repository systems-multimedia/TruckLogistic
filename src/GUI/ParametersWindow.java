package GUI;

import Controller.BankerAlgorithm;
import Controller.state;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import javafx.beans.value.ObservableValue;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ParametersWindow extends JFrame {

    private final int screenSizeW;
    private final int screenSizeH;
    private final int btnPercent, marginPercent;
    private final int smallbtnSizeW, smallbtnSizeH;
    private final int smallSquareBtnSize, smallMarginX, smallMarginY;

    private final ImageIcon bg, ex, hi, checked, unchecked, oN_able, oN_disable, s;
    private final JButton exitBtn;
    private final JButton hideBtn;
    private final JButton orderNowBtn;

    private final Cursor pointer;
    private final JLabel background;

    /*
    private final int btnSizeW;
    private final int btnSizeH;

    private final int btnMarginX;
    private final int btnMarginY;
     */
    private final JButton routesNumberCheck, ordersNumberCheck, trucksPerRouteCheck, ordersPerTruckCheck, save, saveMatrixValue;
    private final JTextField number_ROUTES, number_ORDERS, trucks_p_ROUTE_value, trucks_p_ROUTE_truck;
    private int n_Routes, n_Orders, trucks_Route, orders_Truck;
    private boolean nRoutes_PROVIDED = false, nOrders_PROVIDED = false, trucks_pRoute_PROVIDED = false, orders_pTruck_PROVIDED = false;
    private final JLabel ordersCounterLbl, message;
    private final JSpinner trucks_p_ROUTE_route, trucks_p_ROUTE_order, trucks_p_ROUTE;

    private final ProgramWindow programWindow;
    private BankerAlgorithm banker;
    private int[][] max;
    private int[] resources;
    private int i = 0, j = 0, resIndex = 0;
    private boolean indexChanged = false;

    public ParametersWindow(final int width, final int height) {

        Font labelFont = new Font("Tahoma", 1, 36);
        this.screenSizeW = width;
        this.screenSizeH = height;

        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setUndecorated(true);

        this.bg = new ImageIcon(getClass().getResource("/Img/Background/3. Parameters.png"));
        this.background = new JLabel();
        this.background.setSize(new Dimension(width, height));
        this.background.setIcon(new ImageIcon(this.bg.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH)));
        this.background.setFocusable(true);

        this.pointer = new Cursor(Cursor.HAND_CURSOR);

        this.btnPercent = 2;
        this.marginPercent = 7;
        this.smallSquareBtnSize = (this.screenSizeW * this.btnPercent) / 100;
        this.smallbtnSizeH = this.smallSquareBtnSize + 20;
        this.smallbtnSizeW = (this.smallbtnSizeH * 386) / 144;
        this.smallMarginX = (this.screenSizeW * this.marginPercent + 3) / 100;
        this.smallMarginY = (this.screenSizeH * this.marginPercent) / 100;

        /*
        //  Setting Buttons
        this.btnSizeW = (this.screenSizeW * 16) / 100;  //Regla de 3 con la imagen de fondo respecto a la imagen del boton
        this.btnSizeH = (this.btnSizeW * 147) / 505;    //Regla de 3 del tamaño de la imagen
        this.btnMarginX = this.screenSizeW - ((this.screenSizeW * 25) / 100);
        this.btnMarginY = this.screenSizeH * 59 / 100;
        System.out.println("Btn Res: " + this.btnSizeW + "x" + this.btnSizeH);/*/
        this.ex = new ImageIcon(getClass().getResource("/Img/Buttons/1. Close (Black).png"));
        this.exitBtn = new JButton();
        this.exitBtn.setSize(new Dimension(this.smallSquareBtnSize, this.smallSquareBtnSize));
        this.exitBtn.setIcon(new ImageIcon(this.ex.getImage().getScaledInstance(this.smallSquareBtnSize, this.smallSquareBtnSize, Image.SCALE_SMOOTH)));
        this.exitBtn.setLocation(this.screenSizeW - this.smallSquareBtnSize - 10, 10);
        this.exitBtn.setContentAreaFilled(false);
        this.exitBtn.setCursor(pointer);
        this.exitBtn.setBorder(null);
        this.exitBtn.addActionListener((ActionEvent ae) -> {
            if (JOptionPane.showConfirmDialog(null, "Do you want to close the app?", "ATENTION", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        this.hi = new ImageIcon(getClass().getResource("/Img/Buttons/3. Minimize (Black).png"));
        this.hideBtn = new JButton();
        this.hideBtn.setSize(new Dimension(this.smallSquareBtnSize, this.smallSquareBtnSize));
        this.hideBtn.setIcon(new ImageIcon(this.hi.getImage().getScaledInstance(this.smallSquareBtnSize, this.smallSquareBtnSize, Image.SCALE_SMOOTH)));
        this.hideBtn.setLocation(this.screenSizeW - (this.smallSquareBtnSize * 2) - 20, 10);
        this.hideBtn.setContentAreaFilled(false);
        this.hideBtn.setCursor(pointer);
        this.hideBtn.setBorder(null);
        this.hideBtn.addActionListener((ActionEvent ae) -> {
            hideBtnActionPerformed();
        });

        this.oN_disable = new ImageIcon(getClass().getResource("/Img/Buttons/15. OrderNow (Disable).png"));
        this.oN_able = new ImageIcon(getClass().getResource("/Img/Buttons/16. orderNow (Enable).png"));
        this.orderNowBtn = new JButton();
        this.orderNowBtn.setBounds((this.screenSizeW * 85) / 100, (this.screenSizeH * 66) / 100, (this.screenSizeW * 12) / 100, (this.screenSizeH * 10) / 100);
        this.orderNowBtn.setIcon(new ImageIcon(this.oN_disable.getImage().getScaledInstance((this.screenSizeW * 12) / 100, (this.screenSizeH * 10) / 100, Image.SCALE_SMOOTH)));
        this.orderNowBtn.setContentAreaFilled(false);
        this.orderNowBtn.setCursor(pointer);
        this.orderNowBtn.setBorder(null);
        this.orderNowBtn.setEnabled(false);
        this.orderNowBtn.addActionListener((ActionEvent ae) -> {
            orderNowBtnActionPerformed();
        });

        this.unchecked = new ImageIcon(getClass().getResource("/Img/Buttons/13.-Check-(Unchecked).png"));
        this.checked = new ImageIcon(getClass().getResource("/Img/Buttons/14.-Check-(Checked).png"));

        this.ordersCounterLbl = new JLabel();
        this.ordersCounterLbl.setSize(new Dimension(94, (this.screenSizeH * 5) / 100));
        this.ordersCounterLbl.setLocation((this.screenSizeW * 26) / 100, (this.screenSizeH * 9) / 100);
        this.ordersCounterLbl.setFont(new Font("Tahoma", 0, (this.screenSizeH * 5) / 100));
        this.ordersCounterLbl.setForeground(new Color(79, 181, 193));
        this.ordersCounterLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        this.ordersCounterLbl.setText("" + 0);

        this.routesNumberCheck = new JButton();
        this.routesNumberCheck.setBounds((this.screenSizeW * 6) / 100, (this.screenSizeH * 41) / 100, (this.screenSizeW * 5) / 100, (this.screenSizeW * 5) / 100);
        this.routesNumberCheck.setFocusPainted(false);
        this.routesNumberCheck.setContentAreaFilled(false);
        this.routesNumberCheck.setFocusPainted(false);
        this.routesNumberCheck.setCursor(pointer);
        this.routesNumberCheck.setBorder(null);
        this.routesNumberCheck.setIcon(new ImageIcon(this.unchecked.getImage().getScaledInstance((this.screenSizeW * 5) / 100, (this.screenSizeW * 5) / 100, Image.SCALE_SMOOTH)));
        this.routesNumberCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                routesNumberCheckButtonActionPerformed();
            }
        });

        this.ordersNumberCheck = new JButton();
        this.ordersNumberCheck.setBounds((this.screenSizeW * 6) / 100, (this.screenSizeH * 67) / 100, (this.screenSizeW * 5) / 100, (this.screenSizeW * 5) / 100);
        this.ordersNumberCheck.setFocusPainted(false);
        this.ordersNumberCheck.setContentAreaFilled(false);
        this.ordersNumberCheck.setFocusPainted(false);
        this.ordersNumberCheck.setCursor(pointer);
        this.ordersNumberCheck.setBorder(null);
        this.ordersNumberCheck.setIcon(new ImageIcon(this.unchecked.getImage().getScaledInstance((this.screenSizeW * 5) / 100, (this.screenSizeW * 5) / 100, Image.SCALE_SMOOTH)));
        this.ordersNumberCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                ordersNumberCheckButtonActionPerformed();
            }
        });

        this.trucksPerRouteCheck = new JButton();
        this.trucksPerRouteCheck.setBounds((this.screenSizeW * 42) / 100, (this.screenSizeH * 11) / 100, (this.screenSizeW * 5) / 100, (this.screenSizeW * 5) / 100);
        this.trucksPerRouteCheck.setFocusPainted(false);
        this.trucksPerRouteCheck.setContentAreaFilled(false);
        this.trucksPerRouteCheck.setFocusPainted(false);
        this.trucksPerRouteCheck.setCursor(pointer);
        this.trucksPerRouteCheck.setBorder(null);
        this.trucksPerRouteCheck.setEnabled(false);
        this.trucksPerRouteCheck.setIcon(new ImageIcon(this.unchecked.getImage().getScaledInstance((this.screenSizeW * 5) / 100, (this.screenSizeW * 5) / 100, Image.SCALE_SMOOTH)));
        this.trucksPerRouteCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                trucksPerRouteCheckButtonActionPerformed();
            }
        });

        this.ordersPerTruckCheck = new JButton();
        this.ordersPerTruckCheck.setBounds((this.screenSizeW * 42) / 100, (this.screenSizeH * 41) / 100, (this.screenSizeW * 5) / 100, (this.screenSizeW * 5) / 100);
        this.ordersPerTruckCheck.setFocusPainted(false);
        this.ordersPerTruckCheck.setContentAreaFilled(false);
        this.ordersPerTruckCheck.setFocusPainted(false);
        this.ordersPerTruckCheck.setCursor(pointer);
        this.ordersPerTruckCheck.setBorder(null);
        this.ordersPerTruckCheck.setEnabled(false);
        this.ordersPerTruckCheck.setIcon(new ImageIcon(this.unchecked.getImage().getScaledInstance((this.screenSizeW * 5) / 100, (this.screenSizeW * 5) / 100, Image.SCALE_SMOOTH)));
        this.ordersPerTruckCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                ordersPerTruckCheckButtonActionPerformed();
            }
        });

        this.number_ROUTES = new JTextField();
        this.number_ROUTES.setBounds((this.screenSizeW * 26) / 100, (this.screenSizeH * 42) / 100, (this.screenSizeW * 5) / 100, (this.screenSizeH * 5) / 100);
        this.number_ROUTES.setBorder(null);
        this.number_ROUTES.setBackground(null);
        this.number_ROUTES.setHorizontalAlignment(SwingConstants.RIGHT);
        this.number_ROUTES.setFont(new Font("Tahoma", 0, (this.screenSizeH * 5) / 100));
        this.number_ROUTES.setForeground(Color.GRAY);
        this.number_ROUTES.setText("1");
        this.number_ROUTES.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (number_ROUTES.getText().equals("1")) {
                    number_ROUTES.setText("");
                    number_ROUTES.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (number_ROUTES.getText().isEmpty()) {
                    number_ROUTES.setForeground(Color.GRAY);
                    number_ROUTES.setText("1");
                }
            }
        });

        this.number_ORDERS = new JTextField();
        this.number_ORDERS.setBounds((this.screenSizeW * 26) / 100, (this.screenSizeH * 68) / 100, (this.screenSizeW * 5) / 100, (this.screenSizeH * 5) / 100);
        this.number_ORDERS.setBorder(null);
        this.number_ORDERS.setBackground(null);
        this.number_ORDERS.setHorizontalAlignment(SwingConstants.RIGHT);
        this.number_ORDERS.setFont(new Font("Tahoma", 0, (this.screenSizeH * 5) / 100));
        this.number_ORDERS.setForeground(Color.GRAY);
        this.number_ORDERS.setText("1");
        this.number_ORDERS.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (number_ORDERS.getText().equals("1")) {
                    number_ORDERS.setText("");
                    number_ORDERS.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (number_ORDERS.getText().isEmpty()) {
                    number_ORDERS.setForeground(Color.GRAY);
                    number_ORDERS.setText("1");
                }
            }
        });

        /**
         * ************************************
         * ARRAY * ***********************************
         */
        s = new ImageIcon(getClass().getResource("/Img/Buttons/19. Save Button.png"));
        save = new JButton();
        save.setBounds((this.screenSizeW * 66) / 100, (this.screenSizeH * 13) / 100, (this.screenSizeH * 2) / 100, (this.screenSizeH * 3) / 100);
        save.setIcon(new ImageIcon(s.getImage().getScaledInstance((this.screenSizeH * 2) / 100, (this.screenSizeH * 2) / 100, Image.SCALE_SMOOTH)));
        save.setBorder(null);
        save.setContentAreaFilled(false);
        save.setCursor(pointer);
        save.setFocusPainted(false);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (!trucks_p_ROUTE_value.getText().isEmpty()) {
                    try {
                        resources[resIndex] = Integer.parseInt(trucks_p_ROUTE_value.getText());
                        System.out.println("\n***************** Resources *********************\n");
                        System.out.println(Arrays.toString(resources));
                        message.setText(Arrays.toString(resources));
                        //for (int k = 0; k < n_Routes; k++) {
                        //    System.out.append("| " + resources[k] + " |");
                        //}
                    } catch (Exception e) {
                        if (e instanceof NumberFormatException) {
                            JOptionPane.showMessageDialog(null, "Format Invalid", "ALERT", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please Provide a value greater than zero", "ALERT", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        save.setEnabled(false);
        save.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                save.setContentAreaFilled(true);
            }

            @Override
            public void mouseExited(MouseEvent me) {
                save.setContentAreaFilled(false);
            }
        });

        this.trucks_p_ROUTE_value = new JTextField();
        this.trucks_p_ROUTE_value.setBounds((this.screenSizeW * 60) / 100, (this.screenSizeH * 13) / 100, (this.screenSizeW * 5) / 100, (this.screenSizeH * 3) / 100);
        this.trucks_p_ROUTE_value.setBorder(null);
        this.trucks_p_ROUTE_value.setBackground(null);
        this.trucks_p_ROUTE_value.setHorizontalAlignment(SwingConstants.RIGHT);
        this.trucks_p_ROUTE_value.setFont(new Font("Tahoma", 0, (this.screenSizeH * 3) / 100));
        this.trucks_p_ROUTE_value.setForeground(Color.GRAY);
        this.trucks_p_ROUTE_value.setText("1");
        this.trucks_p_ROUTE_value.setEditable(false);
        this.trucks_p_ROUTE_value.setEnabled(false);
        this.trucks_p_ROUTE_value.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (trucks_p_ROUTE_value.getText().equals("1")) {
                    trucks_p_ROUTE_value.setText("");
                    trucks_p_ROUTE_value.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (trucks_p_ROUTE_value.getText().isEmpty()) {
                    trucks_p_ROUTE_value.setForeground(Color.GRAY);
                    trucks_p_ROUTE_value.setText("" + resources[resIndex]);
                }
            }
        });
        this.trucks_p_ROUTE_value.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (!trucks_p_ROUTE_value.getText().isEmpty()) {
                        resources[resIndex] = Integer.parseInt(trucks_p_ROUTE_value.getText());
                        System.out.println("\n***************** Resources *********************\n");
                        System.out.println(Arrays.toString(resources));
                        message.setText(Arrays.toString(resources));
                        //for (int k = 0; k < n_Routes; k++) {
                        //    System.out.append("| " + resources[k] + " |");
                        //}
                    }
                } catch (Exception e) {
                    if (e instanceof NumberFormatException) {
                        JOptionPane.showMessageDialog(null, "Invalid Format", "ALERT", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        this.trucks_p_ROUTE = new JSpinner();
        this.trucks_p_ROUTE.setBounds((this.screenSizeW * 60) / 100, (this.screenSizeH * 17) / 100, (this.screenSizeW * 5) / 100, (this.screenSizeH * 3) / 100);
        this.trucks_p_ROUTE.setFont(new Font("Tahoma", 0, (this.screenSizeH * 3) / 100));
        this.trucks_p_ROUTE.setAlignmentX(RIGHT_ALIGNMENT);
        this.trucks_p_ROUTE.setBorder(null);
        this.trucks_p_ROUTE.setEnabled(false);
        this.trucks_p_ROUTE.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {
                resIndex = Integer.parseInt(trucks_p_ROUTE.getValue().toString());
                trucks_p_ROUTE_value.setText("" + resources[resIndex]);
            }
        });

        /**
         * ************************************
         * MATRIX * ***********************************
         */
        saveMatrixValue = new JButton();
        saveMatrixValue.setBounds((this.screenSizeW * 69) / 100, (this.screenSizeH * 42) / 100, (this.screenSizeH * 2) / 100, (this.screenSizeH * 3) / 100);
        saveMatrixValue.setIcon(new ImageIcon(s.getImage().getScaledInstance((this.screenSizeH * 2) / 100, (this.screenSizeH * 2) / 100, Image.SCALE_SMOOTH)));
        saveMatrixValue.setBorder(null);
        saveMatrixValue.setContentAreaFilled(false);
        saveMatrixValue.setCursor(pointer);
        saveMatrixValue.setFocusPainted(false);
        saveMatrixValue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (!trucks_p_ROUTE_truck.getText().isEmpty()) {
                    try {
                        if (!trucks_p_ROUTE_truck.getText().isEmpty()) {
                            max[i][j] = Integer.parseInt(trucks_p_ROUTE_truck.getText());
                            if (max[i][j] > resources[i]) {
                                JOptionPane.showMessageDialog(null, "Value needs to be less or equal to " + resources[i] + " (resource[" + i + "])");
                                max[i][j] = 0;
                                trucks_p_ROUTE_truck.setText("" + max[i][j]);
                            }
                            //System.out.println("max[" + (i) + "][" + (j) + "] = " + max[i][j]);
                            message.setText("resource[" + (i + 1) + "] = " + resources[i] + " || \nmax[" + (i + 1) + "][" + (j + 1) + "] = " + max[i][j]);
                            //System.out.println(Arrays.toString(max[i]));
                            System.out.println("\n***************** Max *********************\n");
                            for (int l = 0; l < n_Routes; l++) {
                                System.out.println(Arrays.toString(max[l]));
                            }
                        }
                    } catch (Exception ex) {
                        if (ex instanceof NumberFormatException) {
                            JOptionPane.showMessageDialog(null, "Invalid Format", "ALERT", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please Provide a value greater than zero", "ALERT", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        saveMatrixValue.setEnabled(false);
        saveMatrixValue.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                saveMatrixValue.setContentAreaFilled(true);
            }

            @Override
            public void mouseExited(MouseEvent me) {
                saveMatrixValue.setContentAreaFilled(false);
            }
        });

        this.trucks_p_ROUTE_truck = new JTextField();
        this.trucks_p_ROUTE_truck.setBounds((this.screenSizeW * 63) / 100, (this.screenSizeH * 42) / 100, (this.screenSizeW * 5) / 100, (this.screenSizeH * 3) / 100);
        this.trucks_p_ROUTE_truck.setBorder(null);
        this.trucks_p_ROUTE_truck.setBackground(null);
        this.trucks_p_ROUTE_truck.setHorizontalAlignment(SwingConstants.RIGHT);
        this.trucks_p_ROUTE_truck.setFont(new Font("Tahoma", 0, (this.screenSizeH * 3) / 100));
        this.trucks_p_ROUTE_truck.setForeground(Color.GRAY);
        this.trucks_p_ROUTE_truck.setText("1");
        this.trucks_p_ROUTE_truck.setEditable(false);
        this.trucks_p_ROUTE_truck.setEnabled(false);
        this.trucks_p_ROUTE_truck.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (trucks_p_ROUTE_truck.getText().equals("1") && trucks_p_ROUTE_truck.isEnabled()) {
                    trucks_p_ROUTE_truck.setText("");
                    trucks_p_ROUTE_truck.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (trucks_p_ROUTE_truck.getText().isEmpty()) {
                    trucks_p_ROUTE_truck.setForeground(Color.GRAY);
                    trucks_p_ROUTE_truck.setText("" + max[i][j]);
                }
            }
        });
        this.trucks_p_ROUTE_truck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (!trucks_p_ROUTE_truck.getText().isEmpty()) {
                        max[i][j] = Integer.parseInt(trucks_p_ROUTE_truck.getText());
                        if (max[i][j] > resources[i]) {
                            JOptionPane.showMessageDialog(null, "Value needs to be less or equal to " + resources[i] + " (resource[" + i + "])");
                            max[i][j] = 0;
                            trucks_p_ROUTE_truck.setText("" + max[i][j]);
                        }
                        //System.out.println("max[" + (i) + "][" + (j) + "] = " + max[i][j]);
                        message.setText("resource[" + (i + 1) + "] = " + resources[i] + " || \nmax[" + (i + 1) + "][" + (j + 1) + "] = " + max[i][j]);
                        System.out.println("\n***************** Max *********************\n");
                        for (int l = 0; l < n_Routes; l++) {
                            System.out.println(Arrays.toString(max[l]));
                        }
                    }
                } catch (Exception ex) {
                    if (ex instanceof NumberFormatException) {
                        JOptionPane.showMessageDialog(null, "Invalid Format", "ALERT", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        this.trucks_p_ROUTE_route = new JSpinner();
        //this.trucks_p_ROUTE_route.setModel(new SpinnerNumberModel(0, 0, null, 1));
        this.trucks_p_ROUTE_route.setBounds((this.screenSizeW * 60) / 100, (this.screenSizeH * 46) / 100, (this.screenSizeW * 5) / 100, (this.screenSizeH * 3) / 100);
        this.trucks_p_ROUTE_route.setFont(new Font("Tahoma", 0, (this.screenSizeH * 3) / 100));
        this.trucks_p_ROUTE_route.setAlignmentX(RIGHT_ALIGNMENT);
        this.trucks_p_ROUTE_route.setBorder(null);
        this.trucks_p_ROUTE_route.setEnabled(false);
        this.trucks_p_ROUTE_route.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {
                i = Integer.parseInt(trucks_p_ROUTE_route.getValue().toString());
                if (i != resIndex) {
                    indexChanged = true;
                }
                if (i < n_Routes && j < n_Orders) {
                    trucks_p_ROUTE_truck.setText("" + max[i][j]);
                }

            }
        });

        this.trucks_p_ROUTE_order = new JSpinner();
        //this.trucks_p_ROUTE_route.setModel(new SpinnerNumberModel(0, 0, null, 1));
        this.trucks_p_ROUTE_order.setBounds((this.screenSizeW * 60) / 100, (this.screenSizeH * 50) / 100, (this.screenSizeW * 5) / 100, (this.screenSizeH * 3) / 100);
        this.trucks_p_ROUTE_order.setFont(new Font("Tahoma", 0, (this.screenSizeH * 3) / 100));
        this.trucks_p_ROUTE_order.setAlignmentX(RIGHT_ALIGNMENT);
        this.trucks_p_ROUTE_order.setBorder(null);
        this.trucks_p_ROUTE_order.setEnabled(false);
        this.trucks_p_ROUTE_order.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {
                j = Integer.parseInt(trucks_p_ROUTE_order.getValue().toString());
                if (i < n_Routes && j < n_Orders) {
                    trucks_p_ROUTE_truck.setText("" + max[i][j]);
                }
            }
        });

        message = new JLabel();
        message.setBounds((this.screenSizeW * 43) / 100, (this.screenSizeH * 60) / 100, (this.screenSizeW * 20) / 100, (this.screenSizeH * 3) / 100);
        message.setBorder(null);
        message.setFont(new Font("Tahoma", 0, (this.screenSizeH * 2) / 100));
        message.setText("");

        /**
         * ************************************
         * MATRIX / END * ***********************************
         */
        JButton queue = new JButton();
        queue.setBounds((this.screenSizeW * 80) / 100, (this.screenSizeH * 9) / 100, (this.screenSizeH * 5) / 100, (this.screenSizeH * 37) / 100);
        queue.setContentAreaFilled(false);
        //queue.setCursor(pointer);
        queue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JFrame queueWindow = new JFrame();
                queueWindow.setSize(new Dimension(screenSizeW, screenSizeH));
                queueWindow.setLocationRelativeTo(null);
                queueWindow.setUndecorated(true);
                queueWindow.setResizable(false);

                ImageIcon qBgImage = new ImageIcon(getClass().getResource("/Img/Buttons/hehe.png"));

                JLabel qBg = new JLabel();
                qBg.setSize(new Dimension(screenSizeW, screenSizeH));
                qBg.setIcon(new ImageIcon(qBgImage.getImage().getScaledInstance(screenSizeW, screenSizeH, Image.SCALE_SMOOTH)));

                queueWindow.add(qBg);
                qBg.add(exitBtn);
                queueWindow.setVisible(true);
                hideWindow();
            }
        });

        this.add(this.background);
        this.background.add(this.exitBtn);
        this.background.add(this.hideBtn);
        this.background.add(this.ordersCounterLbl);
        this.background.add(this.routesNumberCheck);
        this.background.add(this.ordersNumberCheck);
        this.background.add(this.trucksPerRouteCheck);
        this.background.add(this.ordersPerTruckCheck);
        this.background.add(this.number_ROUTES);
        this.background.add(this.number_ORDERS);
        this.background.add(this.save);
        this.background.add(this.saveMatrixValue);
        this.background.add(this.trucks_p_ROUTE);
        this.background.add(this.trucks_p_ROUTE_value);
        this.background.add(this.trucks_p_ROUTE_truck);
        this.background.add(this.trucks_p_ROUTE_order);
        this.background.add(this.trucks_p_ROUTE_route);
        this.background.add(message);
        this.background.add(this.orderNowBtn);
        this.background.add(queue);

        this.programWindow = new ProgramWindow(this.screenSizeW, this.screenSizeH, this);
    }

    private void hideBtnActionPerformed() {
        this.setState(JFrame.ICONIFIED);
    }

    private void routesNumberCheckButtonActionPerformed() {

        this.nRoutes_PROVIDED = !nRoutes_PROVIDED;

        try {
            if (nRoutes_PROVIDED == true) {
                if (!this.number_ROUTES.getText().isEmpty()) {
                    this.n_Routes = Integer.parseInt(this.number_ROUTES.getText());
                    if (this.n_Routes <= 0) {
                        JOptionPane.showMessageDialog(this, "Values need to be greater than zero", "ALERT", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    this.number_ROUTES.setEditable(false);
                    this.routesNumberCheck.setIcon(new ImageIcon(this.checked.getImage().getScaledInstance((this.screenSizeW * 5) / 100, (this.screenSizeW * 5) / 100, Image.SCALE_SMOOTH)));
                    if (this.nOrders_PROVIDED == true) {
                        this.trucks_p_ROUTE_route.setEnabled(true);
                        this.trucks_p_ROUTE_route.setModel(new SpinnerNumberModel(0, 0, this.n_Routes - 1, 1));
                        this.trucks_p_ROUTE_order.setEnabled(true);
                        this.trucks_p_ROUTE_order.setModel(new SpinnerNumberModel(0, 0, this.n_Orders - 1, 1));
                        this.trucks_p_ROUTE_truck.setEditable(true);
                        this.trucks_p_ROUTE_truck.setEnabled(true);
                        save.setEnabled(true);
                        this.resources = new int[n_Routes];
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Please provide a value", "ALERT", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                this.number_ROUTES.setEditable(true);
                this.routesNumberCheck.setIcon(new ImageIcon(this.unchecked.getImage().getScaledInstance((this.screenSizeW * 5) / 100, (this.screenSizeW * 5) / 100, Image.SCALE_SMOOTH)));
                this.trucks_p_ROUTE_route.setEnabled(false);
                this.trucks_p_ROUTE_order.setEnabled(false);
                this.trucks_p_ROUTE_value.setEditable(false);
                this.trucks_p_ROUTE_value.setEnabled(false);
                this.trucksPerRouteCheck.setEnabled(false);
                this.ordersPerTruckCheck.setEnabled(false);
                save.setEnabled(false);
            }

            if (this.nOrders_PROVIDED == true && this.nRoutes_PROVIDED == true && this.trucks_pRoute_PROVIDED == true && this.orders_pTruck_PROVIDED == true) {
                this.orderNowBtn.setIcon(new ImageIcon(this.oN_able.getImage().getScaledInstance((this.screenSizeW * 12) / 100, (this.screenSizeH * 10) / 100, Image.SCALE_SMOOTH)));
                this.orderNowBtn.setEnabled(true);
            } else {
                this.orderNowBtn.setIcon(new ImageIcon(this.oN_disable.getImage().getScaledInstance((this.screenSizeW * 12) / 100, (this.screenSizeH * 10) / 100, Image.SCALE_SMOOTH)));
                this.orderNowBtn.setEnabled(false);
            }

        } catch (Exception e) {
            if (e instanceof NumberFormatException) {
                JOptionPane.showMessageDialog(this, "Invalid format provided", "ALERT", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    private void hideWindow() {
        this.setVisible(false);
    }

    private void ordersNumberCheckButtonActionPerformed() {
        this.nOrders_PROVIDED = !nOrders_PROVIDED;

        try {
            if (nOrders_PROVIDED == true) {
                if (!this.number_ORDERS.getText().isEmpty()) {
                    this.n_Orders = Integer.parseInt(this.number_ORDERS.getText());
                    if (this.n_Orders <= 0) {
                        JOptionPane.showMessageDialog(this, "Values need to be greater than zero", "ALERT", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    this.number_ORDERS.setEditable(false);
                    this.ordersNumberCheck.setIcon(new ImageIcon(this.checked.getImage().getScaledInstance((this.screenSizeW * 5) / 100, (this.screenSizeW * 5) / 100, Image.SCALE_SMOOTH)));

                    if (this.nRoutes_PROVIDED == true) {
                        this.trucksPerRouteCheck.setEnabled(true);
                        this.trucks_p_ROUTE_value.setEditable(true);
                        this.trucks_p_ROUTE_value.setEnabled(true);
                        this.trucks_p_ROUTE.setEnabled(true);
                        this.trucks_p_ROUTE.setModel(new SpinnerNumberModel(0, 0, this.n_Routes - 1, 1));
                        save.setEnabled(true);
                        this.max = new int[this.n_Routes][this.n_Orders];
                        this.resources = new int[n_Routes];

                        initializeArray();
                        this.trucks_p_ROUTE_value.setText("" + this.resources[resIndex]);
                        this.trucks_p_ROUTE_truck.setText("" + this.max[i][j]);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Please provide a value", "ALERT", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                this.number_ORDERS.setEditable(true);
                this.ordersNumberCheck.setIcon(new ImageIcon(this.unchecked.getImage().getScaledInstance((this.screenSizeW * 5) / 100, (this.screenSizeW * 5) / 100, Image.SCALE_SMOOTH)));
                this.trucks_p_ROUTE_route.setEnabled(false);
                this.trucks_p_ROUTE_order.setEnabled(false);
                this.trucks_p_ROUTE_value.setEditable(false);
                this.trucks_p_ROUTE_value.setEnabled(false);
                this.trucksPerRouteCheck.setEnabled(false);
                this.ordersPerTruckCheck.setEnabled(false);
                save.setEnabled(false);
            }

            if (this.nOrders_PROVIDED == true && this.nRoutes_PROVIDED == true && this.trucks_pRoute_PROVIDED == true && this.orders_pTruck_PROVIDED == true) {
                this.orderNowBtn.setIcon(new ImageIcon(this.oN_able.getImage().getScaledInstance((this.screenSizeW * 12) / 100, (this.screenSizeH * 10) / 100, Image.SCALE_SMOOTH)));
                this.orderNowBtn.setEnabled(true);
            } else {
                this.orderNowBtn.setIcon(new ImageIcon(this.oN_disable.getImage().getScaledInstance((this.screenSizeW * 12) / 100, (this.screenSizeH * 10) / 100, Image.SCALE_SMOOTH)));
                this.orderNowBtn.setEnabled(false);
            }

        } catch (Exception e) {
            if (e instanceof NumberFormatException) {
                JOptionPane.showMessageDialog(this, "Invalid format provided", "ALERT", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    private void trucksPerRouteCheckButtonActionPerformed() {

        this.trucks_pRoute_PROVIDED = !trucks_pRoute_PROVIDED;

        try {
            if (trucks_pRoute_PROVIDED == true) {
                this.trucks_p_ROUTE_value.setEnabled(false);
                this.trucks_p_ROUTE_value.setEditable(false);
                this.trucks_p_ROUTE_truck.setEditable(true);
                this.trucks_p_ROUTE_truck.setEnabled(true);
                this.ordersPerTruckCheck.setEnabled(true);
                this.trucks_p_ROUTE_route.setEnabled(true);
                this.trucks_p_ROUTE_route.setModel(new SpinnerNumberModel(0, 0, this.n_Routes - 1, 1));
                this.trucks_p_ROUTE_order.setEnabled(true);
                this.trucks_p_ROUTE_order.setModel(new SpinnerNumberModel(0, 0, this.n_Orders - 1, 1));
                this.saveMatrixValue.setEnabled(true);
                this.trucksPerRouteCheck.setIcon(new ImageIcon(this.checked.getImage().getScaledInstance((this.screenSizeW * 5) / 100, (this.screenSizeW * 5) / 100, Image.SCALE_SMOOTH)));
            } else {
                this.trucks_p_ROUTE_value.setEnabled(true);
                this.trucks_p_ROUTE_value.setEditable(true);
                this.trucks_p_ROUTE_truck.setEnabled(false);
                this.trucks_p_ROUTE_truck.setEditable(false);
                this.trucksPerRouteCheck.setIcon(new ImageIcon(this.unchecked.getImage().getScaledInstance((this.screenSizeW * 5) / 100, (this.screenSizeW * 5) / 100, Image.SCALE_SMOOTH)));
                this.saveMatrixValue.setEnabled(false);
            }

            if (this.nOrders_PROVIDED == true && this.nRoutes_PROVIDED == true && this.trucks_pRoute_PROVIDED == true && this.orders_pTruck_PROVIDED == true) {
                this.orderNowBtn.setIcon(new ImageIcon(this.oN_able.getImage().getScaledInstance((this.screenSizeW * 12) / 100, (this.screenSizeH * 10) / 100, Image.SCALE_SMOOTH)));
                this.orderNowBtn.setEnabled(true);
            } else {
                this.orderNowBtn.setIcon(new ImageIcon(this.oN_disable.getImage().getScaledInstance((this.screenSizeW * 12) / 100, (this.screenSizeH * 10) / 100, Image.SCALE_SMOOTH)));
                this.orderNowBtn.setEnabled(false);
            }

        } catch (Exception e) {
            if (e instanceof NumberFormatException) {
                JOptionPane.showMessageDialog(this, "Invalid format provided", "ALERT", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    private void ordersPerTruckCheckButtonActionPerformed() {
        this.orders_pTruck_PROVIDED = !orders_pTruck_PROVIDED;

        try {
            if (orders_pTruck_PROVIDED == true) {
                this.ordersPerTruckCheck.setIcon(new ImageIcon(this.checked.getImage().getScaledInstance((this.screenSizeW * 5) / 100, (this.screenSizeW * 5) / 100, Image.SCALE_SMOOTH)));
                this.trucks_p_ROUTE_truck.setEditable(false);
                this.trucks_p_ROUTE_truck.setEditable(false);
            } else {
                this.ordersPerTruckCheck.setIcon(new ImageIcon(this.unchecked.getImage().getScaledInstance((this.screenSizeW * 5) / 100, (this.screenSizeW * 5) / 100, Image.SCALE_SMOOTH)));
                this.trucks_p_ROUTE_truck.setEnabled(true);
                this.trucks_p_ROUTE_truck.setEditable(true);
            }

            if (this.nOrders_PROVIDED == true && this.nRoutes_PROVIDED == true && this.trucks_pRoute_PROVIDED == true && this.orders_pTruck_PROVIDED == true) {
                this.orderNowBtn.setIcon(new ImageIcon(this.oN_able.getImage().getScaledInstance((this.screenSizeW * 12) / 100, (this.screenSizeH * 10) / 100, Image.SCALE_SMOOTH)));
                this.orderNowBtn.setEnabled(true);
            } else {
                this.orderNowBtn.setIcon(new ImageIcon(this.oN_disable.getImage().getScaledInstance((this.screenSizeW * 12) / 100, (this.screenSizeH * 10) / 100, Image.SCALE_SMOOTH)));
                this.orderNowBtn.setEnabled(false);
            }

        } catch (Exception e) {
            if (e instanceof NumberFormatException) {
                JOptionPane.showMessageDialog(this, "Invalid format provided", "ALERT", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void orderNowBtnActionPerformed() {
        if (this.nOrders_PROVIDED == true && this.nRoutes_PROVIDED == true && this.trucks_pRoute_PROVIDED == true && this.orders_pTruck_PROVIDED == true) {
            //JOptionPane.showMessageDialog(this, "App Initialized", "MESSAGE", JOptionPane.PLAIN_MESSAGE);
            //this.programWindow.setVisible(true);
            //this.programWindow.setEnabled(true);
            /**
             *
             * @param max
             * @param res
             * @param n_Routes
             * @param n_Orders
             */
            this.banker = new BankerAlgorithm(max, resources, n_Routes, n_Orders);
            this.setVisible(false);
            this.setEnabled(false);
        } else {
            JOptionPane.showMessageDialog(this, "All checkbox need to be checked", "ALERT", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initializeArray() {
        for (int i = 0; i < this.n_Routes; i++) {
            resources[i] = 0;
            for (int j = 0; j < this.n_Orders; j++) {
                max[i][j] = 0;
                if (i == j) {
                    max[i][j] = 1;
                }
            }
        }
    }

}
