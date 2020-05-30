package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.response.ListGroupMembersResponsePacket;
import session.Session;

import java.util.List;

public class ListMembersGroupResponseHandler extends SimpleChannelInboundHandler<ListGroupMembersResponsePacket> {

    public static final ListMembersGroupResponseHandler INSTANCE = new ListMembersGroupResponseHandler();

    ListMembersGroupResponseHandler(){

    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersResponsePacket responsePacket) throws Exception {
        System.out.println("群[" + responsePacket.getGroupId() + "]中的人包括：" + responsePacket.getSessionList());
    }
}
