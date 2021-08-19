package net.realmoving.util;

public class ClimbBlockData {
    public static final ClimbBlockData DUMMY = new ClimbBlockData();
    private final int y;
    private final float up;
    private final float down;

    public ClimbBlockData(int y, float up, float down) {
        this.y = y;
        this.up = y + up;
        this.down = y + down;
    }

    private ClimbBlockData() {
        this.up = 0;
        this.down = 0;
        this.y = 0;
    }

    public int getY() {
        return y;
    }

    public float getUp() {
        return up;
    }

    public float getDown() {
        return down;
    }

    public boolean isEmpty() {
        return this == DUMMY;
    }
}
