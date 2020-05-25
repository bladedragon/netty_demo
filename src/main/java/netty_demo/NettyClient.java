package netty_demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;


import java.util.Date;
import java.util.concurrent.TimeUnit;

public class NettyClient {

    private final static int MAX_ERTRY = 5;
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8000;

    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,5000)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) {
                        ch.pipeline().addLast(new FirstClientHandler());
                    }
                });

        System.out.println("客户端处理中...");
        Channel channel = connect(bootstrap,HOST,PORT,MAX_ERTRY);

        while (true) {
            channel.writeAndFlush(new Date() + ": hello world!");
            Thread.sleep(2000);
        }
    }

    public static Channel connect(Bootstrap bootstrap,String host,int port,int retry){
        Channel channel = bootstrap.connect(host,port).addListener(future -> {
            if(future.isSuccess()){
                System.out.println("连接成功");
            }else if(retry == 0){
                System.err.println("重试次数已用完，放弃连接");
            }else{
                //指数退避算法
                int order = (MAX_ERTRY - retry) + 1;

                int delay = 1 << order;
                System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay,
                        TimeUnit.SECONDS);
            }
        }).channel();
        return channel;
    }
}