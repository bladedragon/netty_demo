package server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import protocol.request.QuitGroupRequestPacket;
import protocol.response.QuitGroupResponsePacket;
import util.SessionUtil;
@ChannelHandler.Sharable
public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket>{

    public static final QuitGroupRequestHandler INSTANCE = new QuitGroupRequestHandler();

    QuitGroupRequestHandler(){

    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupRequestPacket quitGroupRequestPacket) throws Exception {
        String groupId = quitGroupRequestPacket.getGroupId();
        ChannelGroup group  = SessionUtil.getChannelGroup(groupId);

        if(group != null){
            group.remove(ctx.channel());
        }
        if(group.size() == 0){
            SessionUtil.unbindChannelGroup(groupId);
        }

        QuitGroupResponsePacket quitGroupResponsePacket = new QuitGroupResponsePacket();
        quitGroupResponsePacket.setSuccess(true);
        quitGroupResponsePacket.setGroupId(groupId);

        ctx.channel().writeAndFlush(quitGroupResponsePacket);
    }
}
