package com.feicent.zhang.plugin.netty.client;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 客户端处理类
 * @author yzuzhang
 * @date 2017年10月29日 下午4:50:36
 */
public class TcpClientHandler extends SimpleChannelInboundHandler<Object> {
	private static final Logger log = LoggerFactory.getLogger(TcpClientHandler.class);
	
	public static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
	
	private String userName;
	
	public TcpClientHandler(String userName) {
		super();
		this.userName = userName;
	}
	/**
	 * channel被激活时调用(Client连接时调用)
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		Runnable runnable = new Runnable() { 		
		    public void run() {
		    	ctx.channel().writeAndFlush(userName + "发送心跳...");
		    }  
		};
		
		executorService.scheduleAtFixedRate(
				runnable, 
				1, //延迟时间2S
				5, //心跳间隔
				TimeUnit.SECONDS //间隔单位
		);
		
	}
	
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		//log.info("client接收到服务器返回的消息: "+msg);
		System.out.println("client接收到服务器返回的消息:"+ msg.toString());
	}
	
	@Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state().equals(IdleState.READER_IDLE)) {
            	log.info("READER_IDLE");
                // 超时关闭channel
                ctx.close();
            } else if (event.state().equals(IdleState.WRITER_IDLE)) {
            	log.info("WRITER_IDLE");
                // 超时关闭channel
                ctx.close();
            } else if (event.state().equals(IdleState.ALL_IDLE)) {
            	log.info("ALL_IDLE");
                // 发送心跳
                ctx.channel().writeAndFlush("bobo...");
            }
        }
        super.userEventTriggered(ctx, evt); 
    }
	
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("client messageReceived---->"+ msg.toString());
	}
	
}