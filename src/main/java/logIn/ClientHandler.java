package logIn;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import protocol.LoginRequestPacket;
import protocol.Packet;
import protocol.PacketCodeC;

import java.util.Date;
import java.util.UUID;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + "客户端开始登录");

        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUsername("zzz");
        loginRequestPacket.setPassword("passwd");

        ByteBuf buffer = PacketCodeC.INSTANCE.encode(ctx.alloc(),loginRequestPacket);

        ctx.channel().writeAndFlush(buffer);
    }
}
