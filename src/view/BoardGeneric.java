package view;

import java.util.ArrayList;

public abstract class BoardGeneric {
    ArrayList<BoardListener> listeners;

    public BoardGeneric() {
        this.listeners = new ArrayList<>();
    }

    public ArrayList<BoardListener> getListeners() {
        return listeners;
    }

    public void addBoardListener(BoardListener listener) {
        this.listeners.add(listener);
    }

    public void fireClickedRobots(int x, int y, int robotNumber) {
        for (int v = 0; v < listeners.size(); v++) {
            BoardListener listener = listeners.get(v);
            listener.onRobotClicked(x, y, robotNumber);
        }
    }

    public void fireClickedDestination(int robot, int destX, int destY) {
        for (int v = 0; v < listeners.size(); v++) {
            BoardListener listener = listeners.get(v);
            listener.onDestinationClicked(robot, destX, destY);
        }
    }
}
