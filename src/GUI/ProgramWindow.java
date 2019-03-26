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

public class ProgramWindow extends JFrame {

    private final int screenSizeW;
    private final int screenSizeH;
    private final int btnPercent, marginPercent;
    private final int smallbtnSizeW, smallbtnSizeH;
    private final int smallSquareBtnSize, smallMarginX, smallMarginY;

    private final JFrame parent;

    private final ImageIcon bg, ex, hi;

    private final JButton exitBtn;
    private final JButton hideBtn;

    private final Cursor pointer;

    private final JLabel background;


    private final int percent = 23;

    public ProgramWindow(final int width, final int height, final JFrame parent) {

        this.screenSizeW = width;
        this.screenSizeH = height;
        this.parent = parent;

        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setUndecorated(true);

        this.bg = new ImageIcon(getClass().getResource("/Img/Background/4. Program (1).png"));
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

        this.add(this.background);
        this.background.add(this.exitBtn);
        this.background.add(this.hideBtn);
    }

    private void hideBtnActionPerformed() {
        this.setState(JFrame.ICONIFIED);
    }
    
    
}
