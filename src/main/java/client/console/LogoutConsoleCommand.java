package client.console;

import io.netty.channel.Channel;
import protocol.request.LogoutRequestPacket;

import java.util.Scanner;

public class LogoutConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("执行到注销");
        LogoutRequestPacket packet = new LogoutRequestPacket();
        channel.writeAndFlush(packet);

    }
}
