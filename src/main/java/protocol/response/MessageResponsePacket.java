package protocol.response;

import lombok.Data;
import protocol.Packet;

import static protocol.Command.MESSAGE_RESPONSE;

@Data
public class MessageResponsePacket extends Packet {

    private String message;
    private String fromUserId;
    private String fromUserName;

    @Override
    public Byte getCommand() {

        return MESSAGE_RESPONSE;
    }
}
