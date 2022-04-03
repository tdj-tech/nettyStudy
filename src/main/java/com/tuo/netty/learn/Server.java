package com.tuo.netty.learn;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author tdj
 * 2022/4/3 0003
 * @desc
 */
public class Server {

    public static ChannelGroup clents = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup(1);// 大管家
        EventLoopGroup workGroup = new NioEventLoopGroup(2); // 服务员
        ServerBootstrap b = new ServerBootstrap();
        try {
            ChannelFuture future = b.group(group, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new ServerChildHandle());
                        }
                    }).bind(8888).sync(); // 不加sync，你就不知道端口是否绑定

            System.out.println("server started");

            future.channel().closeFuture().sync(); // 让线程阻塞，否则server刚起起来就结束了
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workGroup.shutdownGracefully();
            group.shutdownGracefully();
        }
    }

}

class ServerChildHandle extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Server.clents.add(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = null;
        try {
            byteBuf = (ByteBuf) msg;
            byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.getBytes(byteBuf.readerIndex(), bytes);
            System.out.println(new String(bytes));
            String content = new String(bytes);
            if ("_bye_".equals(content)) {
                Server.clents.remove(ctx);
                ctx.close();
            }
            Server.clents.writeAndFlush(msg);
        } finally {
//            if (byteBuf != null) {
//                ReferenceCountUtil.release(byteBuf);
//            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        Server.clents.remove(ctx);
        ctx.close();
    }
}
