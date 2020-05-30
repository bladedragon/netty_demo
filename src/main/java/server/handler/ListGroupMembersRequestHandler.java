package server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import protocol.request.ListGroupMembersRequestPacket;
import protocol.response.ListGroupMembersResponsePacket;
import session.Session;
import util.SessionUtil;

import java.util.ArrayList;
import java.util.List;

@ChannelHandler.Sharable
public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {

    public static final ListGroupMembersRequestHandler INSTANCE = new ListGroupMembersRequestHandler();

    ListGroupMembersRequestHandler(){

    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersRequestPacket listGroupMembersRequestPacket) throws Exception {
        String groupId = listGroupMembersRequestPacket.getGroupId();

        ChannelGroup group = SessionUtil.getChannelGroup(groupId);
        List<Session> list = new ArrayList<>();
        for(Channel channel : group){
            Session session = SessionUtil.getSession(channel);
            list.add(session);
        }

        ListGroupMembersResponsePacket responsePacket = new ListGroupMembersResponsePacket();
        responsePacket.setGroupId(groupId);
        responsePacket.setSessionList(list);

        ctx.channel().writeAndFlush(responsePacket);
    }
}
