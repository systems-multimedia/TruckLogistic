/*
 ------------------------------------------------------------------------------
 |       Metropolitan University, Caracas - Venezuela                         |
 |       Systems Engineering                                                  |
 |       Operating Systems                                                    |
 |       * Professor:   Luis García                                           |
 |       * Creators:    Samuel Boada, Marcos De Andrade                       |
 |       Proyect  N°2 - Algoritmo del Banquero                               |
 |       Period  1819-2 (mach 2019)                                           |
 ------------------------------------------------------------------------------
 */
package GUI;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Menu extends javax.swing.JFrame {

    private final int screenSizeW;
    private final int screenSizeH;
    private final int btnSizeW;
    private final int btnSizeH;
    private final int smallSquareBtnSize;
    private final int percent = 15, smallBtnPercent;
    private final int btnMarginX;
    private final int btnMarginY;

    private final int btnSize2W;
    private final int btnSize2H;
    private final int smallSquareBtnSize2;
    private final int smallBtnPercent2;
    private final int btnMargin2X;
    private final int btnMargin2Y;

    private final ImageIcon bg, ex, hi, st, cr, hm;
    private ImageIcon hoverIcon;
    private final JButton exitBtn;
    private final JButton hideBtn;
    private final JButton startBtn;
    private final JButton homeBtn;
    private final JButton credBtn;
    private final JLabel background;
    private final Cursor pointer;

    //Windows
    private final CreditsWindow creditsWindow;
    private final ParametersWindow parametersWindow;

    public Menu() {
        initComponents();

        this.screenSizeW = Toolkit.getDefaultToolkit().getScreenSize().width - (Toolkit.getDefaultToolkit().getScreenSize().width * this.percent) / 100;
        this.screenSizeH = (this.screenSizeW * 886) / 1800;
        this.setState(JFrame.NORMAL);
        this.setSize(dimension(0, 0));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        System.out.println("Res: " + this.screenSizeW + "x" + this.screenSizeH);

        //  Setting Background Label        
        this.bg = new ImageIcon(getClass().getResource("/Img/Background/1. Menu.png"));
        this.background = new JLabel();
        this.background.setSize(dimension(0, 0));
        this.background.setIcon(new ImageIcon(this.bg.getImage().getScaledInstance(this.screenSizeW, this.screenSizeH, Image.SCALE_SMOOTH)));
        this.background.setLocation(0, 0);

        //  Setting Buttons
        this.pointer = new Cursor(Cursor.HAND_CURSOR);

        this.btnSizeW = (this.screenSizeW * 16) / 100;  //Regla de 3 con la imagen de fondo respecto a la imagen del boton
        this.btnSizeH = (this.btnSizeW * 147) / 505;    //Regla de 3 del tamaño de la imagen
        this.btnMarginX = this.screenSizeW - ((this.screenSizeW * 25) / 100);
        this.btnMarginY = this.screenSizeH * 59 / 100;
        this.smallBtnPercent = 2;
        this.smallSquareBtnSize = (this.screenSizeW * this.smallBtnPercent) / 100;
        System.out.println("Btn Res: " + this.btnSizeW + "x" + this.btnSizeH);

        //Setting Button Home
        this.btnSize2W = (this.screenSizeW * 5) / 100;  //Regla de 3 con la imagen de fondo respecto a la imagen del boton
        this.btnSize2H = this.btnSize2W;    //Regla de 3 del tamaño de la imagen
        this.btnMargin2X = this.screenSizeW - ((this.screenSizeW * 19) / 100);
        this.btnMargin2Y = this.screenSizeH * 64 / 100;
        this.smallBtnPercent2 = 2;
        this.smallSquareBtnSize2 = (this.screenSizeW * this.smallBtnPercent2) / 100;
        System.out.println("Btn2 Res: " + this.btnSize2W + "x" + this.btnSize2H);

        this.ex = new ImageIcon(getClass().getResource("/Img/Buttons/1. Close (Black).png"));
        this.exitBtn = new JButton();
        this.exitBtn.setSize(new Dimension(this.smallSquareBtnSize, this.smallSquareBtnSize));
        this.exitBtn.setIcon(new ImageIcon(this.ex.getImage().getScaledInstance(this.smallSquareBtnSize, this.smallSquareBtnSize, Image.SCALE_SMOOTH)));
        this.exitBtn.setLocation(this.screenSizeW - this.smallSquareBtnSize - 10, 10);
        this.exitBtn.setContentAreaFilled(false);
        this.exitBtn.setCursor(pointer);
        this.exitBtn.setBorder(null);
        this.exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (JOptionPane.showConfirmDialog(null, "Do you want to close the app?", "ATENTION", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
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
        this.hideBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                hideBtnActionPerformed();
            }
        });

        this.st = new ImageIcon(getClass().getResource("/Img/Buttons/8. Start (Disable).png"));
        this.startBtn = new JButton();
        this.startBtn.setSize(new Dimension(this.btnSizeW, this.btnSizeH));
        this.startBtn.setIcon(new ImageIcon(this.st.getImage().getScaledInstance(this.btnSizeW, this.btnSizeH, Image.SCALE_SMOOTH)));
        this.startBtn.setLocation(this.btnMarginX, this.btnMarginY);
        this.startBtn.setContentAreaFilled(false);
        this.startBtn.setFocusPainted(false);
        this.startBtn.setCursor(pointer);
        this.startBtn.setBorder(null);
        this.startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                startBtnActionPerformed();
            }
        });
        this.startBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evtMouseEvent) {
                changeStartBtnBackground(true);
            }

            @Override
            public void mouseExited(MouseEvent evtMouseEvent) {
                changeStartBtnBackground(false);
            }
        });

        this.cr = new ImageIcon(getClass().getResource("/Img/Buttons/10. Credits (Disable).png"));
        this.credBtn = new JButton();
        this.credBtn.setSize(new Dimension(this.btnSizeW, this.btnSizeH));
        this.credBtn.setIcon(new ImageIcon(this.cr.getImage().getScaledInstance(this.btnSizeW, this.btnSizeH, Image.SCALE_SMOOTH)));
        this.credBtn.setLocation(this.btnMarginX, this.btnMarginY + (this.btnSizeH * 1) + ((this.screenSizeH * 2) / 100));
        this.credBtn.setContentAreaFilled(false);
        this.credBtn.setFocusPainted(false);
        this.credBtn.setCursor(pointer);
        this.credBtn.setBorder(null);
        this.credBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                credBtnActionPerformed();
            }
        });
        this.credBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evtMouseEvent) {
                changeCreditsBackground(true);
            }

            @Override
            public void mouseExited(MouseEvent evtMouseEvent) {
                changeCreditsBackground(false);
            }
        });

        this.hm = new ImageIcon(getClass().getResource("/Img/Buttons/12. Home (Disable).png"));
        this.homeBtn = new JButton();
        this.homeBtn.setSize(new Dimension(this.btnSize2W, this.btnSize2H));
        this.homeBtn.setIcon(new ImageIcon(this.hm.getImage().getScaledInstance(this.btnSize2W, this.btnSize2H, Image.SCALE_SMOOTH)));
        this.homeBtn.setLocation(this.btnMargin2X, this.btnMargin2Y + (this.btnSize2H * 2) + ((this.screenSizeH * 2) / 100));
        this.homeBtn.setContentAreaFilled(false);
        this.homeBtn.setFocusPainted(false);
        this.homeBtn.setCursor(pointer);
        this.homeBtn.setBorder(null);
        /*this.homeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                credBtnActionPerformed();
            }
        });*/
        this.homeBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evtMouseEvent) {
                changeHomeBackground(true);
            }

            @Override
            public void mouseExited(MouseEvent evtMouseEvent) {
                changeHomeBackground(false);
            }
        });

        this.setLayout(null);
        this.add(this.background);
        this.background.add(this.exitBtn);
        this.background.add(this.hideBtn);
        this.background.add(this.startBtn);
        this.background.add(this.credBtn);
        this.background.add(this.homeBtn);

        // Setting Windows
        this.creditsWindow = new CreditsWindow(this.screenSizeW, this.screenSizeH, this);
        this.parametersWindow = new ParametersWindow(this.screenSizeW, this.screenSizeH);

    }

    private Dimension dimension(int x, int y) {
        return new Dimension(this.screenSizeW + x, this.screenSizeH + y);
    }

    private void changeStartBtnBackground(boolean entered) {
        if (entered) {
            this.hoverIcon = new ImageIcon(getClass().getResource("/Img/Buttons/7. Start (Enable).png"));
            this.startBtn.setIcon(new ImageIcon(this.hoverIcon.getImage().getScaledInstance(this.btnSizeW, this.btnSizeH, Image.SCALE_SMOOTH)));
        } else {
            this.startBtn.setIcon(new ImageIcon(this.st.getImage().getScaledInstance(this.btnSizeW, this.btnSizeH, Image.SCALE_SMOOTH)));
        }
    }

    private void changeCreditsBackground(boolean entered) {
        if (entered) {
            this.hoverIcon = new ImageIcon(getClass().getResource("/Img/Buttons/9. Credits (Enable).png"));
            this.credBtn.setIcon(new ImageIcon(this.hoverIcon.getImage().getScaledInstance(this.btnSizeW, this.btnSizeH, Image.SCALE_SMOOTH)));
        } else {
            this.credBtn.setIcon(new ImageIcon(this.cr.getImage().getScaledInstance(this.btnSizeW, this.btnSizeH, Image.SCALE_SMOOTH)));
        }
    }

    private void changeHomeBackground(boolean entered) {
        if (entered) {
            this.hoverIcon = new ImageIcon(getClass().getResource("/Img/Buttons/11. Home (Enable).png"));
            this.homeBtn.setIcon(new ImageIcon(this.hoverIcon.getImage().getScaledInstance(this.btnSize2W, this.btnSize2H, Image.SCALE_SMOOTH)));
        } else {
            this.homeBtn.setIcon(new ImageIcon(this.hm.getImage().getScaledInstance(this.btnSize2W, this.btnSize2H, Image.SCALE_SMOOTH)));
        }
    }

    private void credBtnActionPerformed() {
        this.creditsWindow.setVisible(true);
        this.creditsWindow.setEnabled(true);

        this.setVisible(false);
        this.setEnabled(false);
    }

    private void startBtnActionPerformed() {
        this.parametersWindow.setVisible(true);
        this.parametersWindow.setEnabled(true);

        this.setVisible(false);
        this.setEnabled(false);
    }

    private void hideBtnActionPerformed() {
        this.setState(JFrame.ICONIFIED);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
