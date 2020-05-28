package util;

import attribute.Attributes;
import io.netty.channel.Channel;
import io.netty.util.Attribute;

/**
 * 已被SessoinUtil重写
 */
public class LoginUtil {
    public static void markAsLogin(Channel channel){
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);

        return loginAttr.get() != null;
    }
}
