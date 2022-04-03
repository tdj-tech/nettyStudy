package com.tuo.netty.learn;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author tdj
 * 2022/4/3 0003
 * @desc
 */
public class TankEncoderTest {



    @Test
    public void TankEncoderTest1() {
        TankMsg tankMsg = new TankMsg(10, 10);
        EmbeddedChannel ch = new EmbeddedChannel(new TankEncoder());
        ch.writeOutbound(tankMsg);

        ByteBuf buf = (ByteBuf)ch.readOutbound();
        int x = buf.readInt();
        int y = buf.readInt();
        Assert.assertTrue(x == 10 && y == 10);
        buf.release();
    }

    @Test
    public void TankEncoderAndDecoderTest() {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(9);
        buf.writeInt(9);
//        TankMsg tankMsg = new TankMsg(9, 9);
        EmbeddedChannel ch = new EmbeddedChannel(new TankEncoder(), new TankDecoder());
        ch.writeInbound(buf.duplicate());

        TankMsg tankMsg = (TankMsg)ch.readInbound();
//        System.out.println(tankMsg1.getClass());
        Assert.assertTrue(tankMsg.x == 9 && tankMsg.y == 9);
    }

}
