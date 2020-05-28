package protocol.request;

import lombok.Data;
import protocol.Packet;

import static protocol.Command.MESSAGE_REQUEST;

@Data
public class MessageRequestPacket extends Packet {

    private String toUserId;
    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }

    public MessageRequestPacket(String toUserId, String message) {
        this.toUserId = toUserId;
        this.message = message;
    }
    public MessageRequestPacket(){

    }

}
