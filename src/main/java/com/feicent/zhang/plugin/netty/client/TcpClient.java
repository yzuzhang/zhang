package com.feicent.zhang.plugin.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.feicent.zhang.plugin.netty.NettyConstants;

/**
 * Netty客户端
 * @author yzuzhang
 * @date 2017年10月29日 下午4:50:36
 */
@Component
public class TcpClient {
	private static final Logger log = LoggerFactory.getLogger(TcpClient.class);
	
	private String userName;

	public static String HOST = "127.0.0.1";
	public static int PORT = NettyConstants.PORT;
	private Channel channel;
	private EventLoopGroup group = null;
	private Bootstrap bootstrap = getBootstrap();
	
	public TcpClient (String userName) {
		super();
		this.userName = userName;
		this.channel = getChannel(HOST, PORT);
	}
	
	/**
	 * 初始化Bootstrap
	 * @return
	 */
	public final Bootstrap getBootstrap(){
	    group = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(group).channel(NioSocketChannel.class);
		
		b.handler(new ChannelInitializer<Channel>() {
			@Override
			protected void initChannel(Channel ch) throws Exception {
				ChannelPipeline pipeline = ch.pipeline();
				pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
				pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
				pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
				pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
				pipeline.addLast("idleStateHandler", new IdleStateHandler(20, 20, 10)); //心跳监测 读超时为10s，写超时为10s 全部空闲时间100s
				pipeline.addLast("handler", new TcpClientHandler(userName));
			}
		});
		b.option(ChannelOption.SO_KEEPALIVE, true);
		return b;
	}

	public final Channel getChannel(String host,int port){
		Channel channel = null;
		try {
			channel = bootstrap.connect(host, port).sync().channel();
		} catch (Exception e) {
			log.info(String.format("连接Server(IP[%s], PORT[%s])失败", host, port));
			return null;
		}
		return channel;
	}

	public void sendMsg(String msg) throws Exception {
		if(channel==null && !channel.isActive()){
			System.out.println("重新连接....");
			channel = getChannel(HOST,PORT);
		}
		channel.writeAndFlush(msg).sync();
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

    public static void main(String[] args) throws Exception {
    	TcpClient client = new TcpClient("张三");
		new MyThread(client).start();
		
		TcpClient client2 = new TcpClient("李四");
		new MyThread(client2).start();
    }
    
}
