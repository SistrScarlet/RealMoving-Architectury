package net.realmoving.util;

public interface IActionable {

    void setActioning_RealMoving(boolean actioning);

    boolean isActioning_RealMoving();

    void setCrawling_RealMoving(boolean crawling);

    boolean isCrawling_RealMoving();

    boolean isSliding_RealMoving();

    void setClimbing_RealMoving(boolean climbing);

    boolean isClimbing_RealMoving();

    float getClimbHeight_RealMoving();

}
