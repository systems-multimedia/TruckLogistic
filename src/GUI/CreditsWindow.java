package GUI;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class CreditsWindow extends JFrame {

    private final int screenSizeW;
    private final int screenSizeH;
    private final int btnPercent, marginPercent;
    private final int smallbtnSizeW, smallbtnSizeH;
    private final int smallSquareBtnSize, smallMarginX, smallMarginY;

    private final JFrame parent;

    private final ImageIcon bg, ex, hi, st, cr, hm;
    private ImageIcon hoverIcon;

    private final JButton exitBtn;
    private final JButton hideBtn;

    private final Cursor pointer;

    private final JLabel background;

    //private final int screenSizeW;
    //private final int screenSizeH;
    private final int btnSizeW;
    private final int btnSizeH;
    private final int percent = 23;

    private final int btnSize2W;
    private final int btnSize2H;

    private final int btnMarginX;
    private final int btnMarginY;
    private final int btnMargin2X;
    private final int btnMargin2Y;
    
    private final JButton startBtn;
    private final JButton homeBtn;
    private final JButton credBtn;

    public CreditsWindow(final int width, final int height, final JFrame parent) {

        this.screenSizeW = width;
        this.screenSizeH = height;
        this.parent = parent;

        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setUndecorated(true);

        this.bg = new ImageIcon(getClass().getResource("/Img/Background/2. Credits.png"));
        this.background = new JLabel();
        this.background.setSize(new Dimension(width, height));
        this.background.setIcon(new ImageIcon(this.bg.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH)));

        this.pointer = new Cursor(Cursor.HAND_CURSOR);

        this.btnPercent = 2;
        this.marginPercent = 7;
        this.smallSquareBtnSize = (this.screenSizeW * this.btnPercent) / 100;
        this.smallbtnSizeH = this.smallSquareBtnSize + 20;
        this.smallbtnSizeW = (this.smallbtnSizeH * 386) / 144;
        this.smallMarginX = (this.screenSizeW * this.marginPercent + 3) / 100;
        this.smallMarginY = (this.screenSizeH * this.marginPercent) / 100;

        //  Setting Buttons
        this.btnSizeW = (this.screenSizeW * 16) / 100;  //Regla de 3 con la imagen de fondo respecto a la imagen del boton
        this.btnSizeH = (this.btnSizeW * 147) / 505;    //Regla de 3 del tamaño de la imagen
        this.btnMarginX = this.screenSizeW - ((this.screenSizeW * 25) / 100);
        this.btnMarginY = this.screenSizeH * 59 / 100;
        System.out.println("Btn Res: " + this.btnSizeW + "x" + this.btnSizeH);

        //Setting Button Home
        this.btnSize2W = (this.screenSizeW * 5) / 100;  //Regla de 3 con la imagen de fondo respecto a la imagen del boton
        this.btnSize2H = this.btnSize2W;    //Regla de 3 del tamaño de la imagen
        this.btnMargin2X = this.screenSizeW - ((this.screenSizeW * 19) / 100);
        this.btnMargin2Y = this.screenSizeH * 64 / 100;
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
        this.homeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                homeBtnActionPerformed();
            }
        });
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
        

        this.add(this.background);
        this.background.add(this.exitBtn);
        this.background.add(this.hideBtn);
        this.background.add(this.startBtn);
        this.background.add(this.credBtn);
        this.background.add(this.homeBtn);
    }

    private void hideBtnActionPerformed() {
        this.setState(JFrame.ICONIFIED);
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
    
    private void homeBtnActionPerformed(){
        this.setVisible(false);
        this.setEnabled(false);
        this.parent.setEnabled(true);
        this.parent.setVisible(true);
    }
    
}
