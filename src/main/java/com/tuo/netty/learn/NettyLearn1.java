package com.tuo.netty.learn;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author tdj
 * 2022/4/1 0001
 * netty client
 */
public class NettyLearn1 {

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup(1);
        Bootstrap b = new Bootstrap();
        try {
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            System.out.println("init....");
                        }
                    }).connect("localhost", 8888)
                    .sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

}
