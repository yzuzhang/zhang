package com.feicent.zhang.plugin.netty;

import org.apache.log4j.Logger;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * http://www.tuicool.com/articles/fQrAJbA
 * @author yzuzhang
 * @date 2017年2月20日
 */
public class NettyServer {

	private static Logger logger = Logger.getLogger(NettyServer.class);
	
	private int port;
	
	public NettyServer(int port) {
		this.port = port;
	}	

	private void bind() {
		
		EventLoopGroup boss = new NioEventLoopGroup();
		EventLoopGroup worker = new NioEventLoopGroup();

		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			
			bootstrap.group(boss, worker);
			bootstrap.channel(NioServerSocketChannel.class);
			bootstrap.option(ChannelOption.SO_BACKLOG, 1024); //连接数
			bootstrap.option(ChannelOption.TCP_NODELAY, true);  //不延迟，消息立即发送
			bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true); //长连接
			
			bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel socketChannel)
						throws Exception {
					ChannelPipeline p = socketChannel.pipeline();
					p.addLast(new NettyServerHandler());
				}
			});
			ChannelFuture f = bootstrap.bind(port).sync();
			if (f.isSuccess()) {
				logger.debug("启动Netty服务成功，端口号：" + this.port);
				System.out.println("启动Netty服务成功，端口号：" + this.port);
			}
			// 关闭连接
			f.channel().closeFuture().sync();
			
		} catch (Exception e) {
			logger.error("启动Netty服务异常，异常信息：" + e.getMessage());
			e.printStackTrace();
		} finally {
			boss.shutdownGracefully();
			worker.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) {
		NettyServer server = new NettyServer(9999);
		server.bind();
	}
}
