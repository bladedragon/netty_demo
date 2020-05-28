package protocol.request;

import lombok.Data;
import protocol.Packet;

import static protocol.Command.LOGIN_REQUEST;

@Data
public class LoginRequestPacket extends Packet {
    //暂不使用
    private String userId;

    private String userName;
    private String password;


    @Override
    public Byte getCommand() {

        return LOGIN_REQUEST;
    }

}
