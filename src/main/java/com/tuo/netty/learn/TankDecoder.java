package com.tuo.netty.learn;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author tdj
 * 2022/4/3 0003
 * 将buf转换为tank
 */
public class TankDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("字节数" + in.readableBytes());
        if (in.readableBytes() < 8) {
            return;
        }
        int x = in.readInt();
        int y = in.readInt();
        out.add(new TankMsg(x, y));
    }
}
