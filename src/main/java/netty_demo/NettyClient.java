package netty_demo;

import client.FirstClientHandler;
import client.handler.LoginResponseHandler;
import client.handler.MessageResponseHandler;
import codec.PacketDecoder;
import codec.PacketEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;


import protocol.PacketCodeC;
import protocol.request.MessageRequestPacket;
import protocol.response.MessageResponsePacket;
import server.handler.LoginRequestHandler;
import util.LoginUtil;


import java.util.Date;
import java.util.Scanner;
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
//                        ch.pipeline().addLast(new ClientHandler());

                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginResponseHandler());
                        ch.pipeline().addLast(new MessageResponseHandler());
                        ch.pipeline().addLast(new PacketEncoder());

//                        ch.pipeline().addLast(new FirstClientHandler());

                    }
                });

        System.out.println("客户端处理中...");
//        Channel channel = connect(bootstrap,HOST,PORT,MAX_ERTRY);
        connect(bootstrap,HOST,PORT,MAX_ERTRY);

//        while (true) {
//            channel.writeAndFlush(new Date() + ": hello world!");
//            Thread.sleep(2000);
//        }
    }

    public static void connect(Bootstrap bootstrap,String host,int port,int retry){

        bootstrap.connect(host,port).addListener(future -> {
            if(future.isSuccess()){
                System.out.println("连接成功");

                Channel channel = ((ChannelFuture)future).channel();
                // 连接成功之后，启动控制台线程
                startConsoleThread(channel);
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
        });

    }

    public static void startConsoleThread(Channel channel){
        new Thread(()-> {
            while(!Thread.interrupted()){
                if(LoginUtil.hasLogin(channel)){
                    System.out.println("输入消息发送到服务端");
                    Scanner scanner = new Scanner(System.in);
                    String line = scanner.nextLine();

                    MessageRequestPacket packet = new MessageRequestPacket();
                    packet.setMessage(line);
                    ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(channel.alloc(),packet);
                    channel.writeAndFlush(byteBuf);
                }
            }
        }).start();
    }
}