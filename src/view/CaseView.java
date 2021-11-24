package view;

import java.awt.Graphics;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class CaseView extends JPanel {
    private JPanel parent;
    private Image image;
    private JLabel robot;
    private JLabel target;
    private JLabel selection;
    private boolean state;//l'etat de la case
    private int posX, posY, robotNumber;

    public CaseView(Image image, JPanel parent, int x, int y) {
        this.parent = null;
        this.posX = x;
        this.posY = y;
        this.image = image;
        state = false;

        setLayout(new BorderLayout());

    }

    @Override
    protected void paintComponent(Graphics arg0) {
        // TODO Auto-generated method stub
        super.paintComponent(arg0);
        arg0.drawImage(image, 0, 0, parent);
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void addWallY() {
        JLabel wallY = new JLabel(new ImageIcon("./images/MurHonrizontal.png"));
        wallY.setBorder(new EmptyBorder(-3, 0, 0, 0));

        this.add(wallY, BorderLayout.PAGE_END);
    }

    public void addWallX() {
        JLabel wallX = new JLabel(new ImageIcon("./images/MurVertictal.png"));
        wallX.setBorder(new EmptyBorder(0, -3, 0, 0));
        this.add(wallX, BorderLayout.WEST);
    }

    public void addRobot(String image, int robotNumber) {
        if (target == null) {
            robot = new JLabel(new ImageIcon(image));
            this.robotNumber = robotNumber;
            this.add(robot, BorderLayout.EAST);
        } else {
            if (this.robot != null) {
                remove(robot);
            }
            this.robot = null;
        }
    }

    public void addTarget(String image) {
        target = new JLabel(new ImageIcon(image));
        this.add(target, BorderLayout.EAST);
    }

    public void addWallPlein() {
        JLabel wallX = new JLabel(new ImageIcon("./images/CasePlein.png"));
        this.add(wallX, BorderLayout.WEST);
    }

    public void clean() {
        if (robot != null) {
            this.remove(robot);
            robot = null;
        }
        if (target != null) {
            this.remove(target);
            target = null;
        }
        if (selection != null) {
            remove(selection);
            selection = null;
        }
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean newstate) {
        this.state = newstate;
    }

    @Override
    public JPanel getParent() {
        return parent;
    }

    public void setParent(JPanel parent) {
        this.parent = parent;
    }

    public Image getImage() {
        return image;
    }

    public JLabel getRobot() {
        return robot;
    }

    public void setRobot(JLabel robot) {
        this.robot = robot;

    }

    public JLabel getTarget() {
        return target;
    }

    public void setTarget(JLabel target) {
        this.target = target;
    }

    public boolean isState() {
        return state;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getRobotNumber() {
        return robotNumber;
    }

    public void setRobotNumber(int robotNumber) {
        this.robotNumber = robotNumber;
    }

    public void addSelection() {
        selection = new JLabel(new ImageIcon("./images/Selection1.png"));
        this.add(selection, BorderLayout.EAST);
        repaint();
    }

    public void cleanSelection() {
        if (selection != null) {
            remove(selection);
            selection = null;
        }
    }

    public JLabel getSelection() {
        return selection;
    }

    public void setSelection(JLabel selection) {
        this.selection = selection;
    }
}
