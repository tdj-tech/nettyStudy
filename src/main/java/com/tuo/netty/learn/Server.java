package com.tuo.netty.learn;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * @author tdj
 * 2022/4/3 0003
 * @desc
 */
public class Server {

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup(2);
        ServerBootstrap b = new ServerBootstrap();
        b.group(group, workGroup);
    }

}
