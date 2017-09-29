package com.feicent.zhang.plugin.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * http://blog.csdn.net/tjbsl/article/details/51038947
 * @author yzuzhang
 * @date 2017年2月20日
 */
public class NettyClient {
	
	private int port;//服务器端口号
	
	private String host;//服务器IP
	
	public NettyClient(int port, String host){
		this.port = port;
		this.host = host;
	}
	
	private void start() throws InterruptedException {
		//
		EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
		try {			
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.channel(NioSocketChannel.class);
			bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
			bootstrap.group(eventLoopGroup);
			bootstrap.remoteAddress(host, port);
			
			bootstrap.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel socketChannel)
						throws Exception {					
					socketChannel.pipeline().addLast(new NettyClientHandler());
				}
			});
			
			ChannelFuture future = bootstrap.connect(host, port).sync();
			if (future.isSuccess()) {
				SocketChannel socketChannel = (SocketChannel) future.channel();
				System.out.println(">>>>>>> NettyClient connect server success >>>>>>>"+socketChannel.isOpen());
			}
			future.channel().closeFuture().sync();
		} finally {
			eventLoopGroup.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		NettyClient client = new NettyClient(9999, "localhost");
		client.start();
	}
}