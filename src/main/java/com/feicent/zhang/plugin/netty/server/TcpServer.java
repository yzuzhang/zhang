package com.feicent.zhang.plugin.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feicent.zhang.plugin.netty.NettyConstants;

/**
 * 采用NETTY方式
 * TCP启动服务类
 * @author yzuzhang
 * @date 2017年10月29日 下午4:50:36
 */
public class TcpServer {
	public static final Logger log = LoggerFactory.getLogger(TcpServer.class);
	private String IP = NettyConstants.HOST;
	private int PORT = NettyConstants.PORT;
	
	/** 业务出现线程大小*/
	protected static final int BIZTHREADSIZE = 8;
	/**用于分配处理业务线程的线程组个数 */
	protected static final int BIZGROUPSIZE = Runtime.getRuntime().availableProcessors()*2;	//默认 Cpu*2
	
	private final EventLoopGroup bossGroup = new NioEventLoopGroup(BIZGROUPSIZE);
	private final EventLoopGroup workerGroup = new NioEventLoopGroup(BIZTHREADSIZE);
	
	public TcpServer() {
		super();
	}
	
	public static void main(String[] args) {
		TcpServer server = new TcpServer();
		server.startServer();
	}
	
	/**
	 * 启动服务
	 */
	public void startServer(){
		try {
			System.out.println("开始启动TCP服务端...");
			init();
		} catch (Exception e) {
			System.err.println("开始启动TCP服务出现异常..."+e.getMessage());
		}
	}
	
	protected void init() throws Exception {
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(bossGroup, workerGroup);
		try {
			bootstrap.channel(NioServerSocketChannel.class);
			bootstrap.option(ChannelOption.SO_BACKLOG, 1024); //连接数
			bootstrap.option(ChannelOption.TCP_NODELAY, true);//不延迟，消息立即发送
			bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true); //长连接
			
			bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ChannelPipeline pipeline = ch.pipeline();
					pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
					pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
					pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
					pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
					pipeline.addLast("idleStateHandler", new IdleStateHandler(20, 20, 10)); //心跳监测 读超时为20s，写超时为20s 全部空闲时间10s
					
					pipeline.addLast(new TcpServerHandler());
				}
			});
			
			//绑定端口，开始接收进来的连接
			ChannelFuture future = bootstrap.bind(IP, PORT).sync();
			if(future.isSuccess()){
				System.out.println("TCP服务已启动---------"+future.isSuccess());
			}else{
				System.err.println("TCP服务未能正常启动------"+future.isSuccess());
			}
			
			/**
			 * 该方法拥有关闭服务 
			 **/
			future.channel().closeFuture().sync();
			//future.channel().closeFuture().addListener(remover).sync(); //用这种监听来
		} catch(Exception e) {
			System.err.println("TCP服务启动出现异常------"+e.getMessage());
		} finally {
			shutdown();
		}
	}
	
	/**
	 * 关闭服务
	 */
	protected void shutdown() {
		workerGroup.shutdownGracefully();
		bossGroup.shutdownGracefully();
		System.out.println(">>>>>关闭TCP服务>>>>>");
	}
	
	public String getIP() {
		return this.IP;
	}
	public int getPORT() {
		return this.PORT;
	}
	public void setIP(String ip) {
		IP = ip;
	}
	public void setPORT(int port) {
		PORT = port;
	}
	
	/**
	 * 获取TcpServerHandler
	 * @return
	 */
	public TcpServerHandler getTcpServerHandler(){
		return new TcpServerHandler();
	}
	
	 /**
	 * 监听ChannelFuture是否完成
	 */
	protected final ChannelFutureListener remover = new ChannelFutureListener() {
		@Override
        public void operationComplete(ChannelFuture future) throws Exception {
        	future.channel().closeFuture().sync();
        	//shutdown();
        }
    };
}
