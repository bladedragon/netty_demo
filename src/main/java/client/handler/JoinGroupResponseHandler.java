package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.response.JoinGroupResponsePacket;

public class JoinGroupResponseHandler extends SimpleChannelInboundHandler<JoinGroupResponsePacket> {
    public static final JoinGroupResponseHandler INSTANCE = new JoinGroupResponseHandler();
    JoinGroupResponseHandler(){

    }
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, JoinGroupResponsePacket joinGroupResponsePacket) throws Exception {
        if(joinGroupResponsePacket.isSucess()){
            System.out.println("加入群["+joinGroupResponsePacket.getGroupId()+"]成功！");
        }else{
            System.out.println("加入群["+joinGroupResponsePacket.getGroupId()+"]失败，原因为"+joinGroupResponsePacket.getReason());
        }
    }
}
