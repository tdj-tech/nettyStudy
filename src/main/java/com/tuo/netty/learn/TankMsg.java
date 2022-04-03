package com.tuo.netty.learn;

/**
 * @author tdj
 * 2022/4/3 0003
 * @desc
 */
public class TankMsg {

    int x;
    int y;


    public TankMsg() {
    }

    public TankMsg(int x, int y) {
        this.x = x;
        this.y = y;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TankMsg{");
        sb.append("x=").append(x);
        sb.append(", y=").append(y);
        sb.append('}');
        return sb.toString();
    }
}
