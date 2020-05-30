package client.console;

import io.netty.channel.Channel;
import protocol.request.JoinGroupRequestPacket;

import java.util.Scanner;

public class JoinGroupConsoleCommand implements ConsoleCommand {


    @Override
    public void exec(Scanner scanner, Channel channel) {
        JoinGroupRequestPacket packet = new JoinGroupRequestPacket();

        System.out.println("输入groupId,加入群聊：");
        String groupId = scanner.next();

        packet.setGroupId(groupId);
        channel.writeAndFlush(packet);


    }
}
