package server.handler;

import client.handler.JoinGroupResponseHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import protocol.request.JoinGroupRequestPacket;
import protocol.response.JoinGroupResponsePacket;
import util.SessionUtil;

@ChannelHandler.Sharable
public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {
    public static final JoinGroupRequestHandler INSTANCE = new JoinGroupRequestHandler();
    JoinGroupRequestHandler(){

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupRequestPacket packet) throws Exception {
        String groupId = packet.getGroupId();
        ChannelGroup group = SessionUtil.getChannelGroup(groupId);

        JoinGroupResponsePacket joinGroupResponsePacket = new JoinGroupResponsePacket();
        if(group != null){
            group.add(ctx.channel());

            joinGroupResponsePacket.setGroupId(groupId);
            joinGroupResponsePacket.setSucess(true);
        }else{
            joinGroupResponsePacket.setSucess(false);
            joinGroupResponsePacket.setReason("SessionUtil()获取不到groupId");
        }


        ctx.channel().writeAndFlush(joinGroupResponsePacket);
    }
}
