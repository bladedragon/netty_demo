package protocol.serialize;

import lombok.Data;


public interface Serializer {

    Serializer DEFAULT = new JSONSerializer();


    /**
     * 序列化算法
     */
    byte getSerializerAlgorithm();
    /**
     * java 对象转换成二进制
     */
    byte[] serialize(Object object);
    /**
     * 二进制转换成 java 对象
     */
    <T> T deSerialize(Class<T> clazz, byte[] bytes);
}
