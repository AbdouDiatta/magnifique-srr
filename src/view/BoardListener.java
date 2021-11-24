package view;

interface BoardListener {
    void onRobotClicked(int x, int y, int robotNumber);

    void onDestinationClicked(int robot, int destX, int destY);
}
