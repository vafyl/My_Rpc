package com.zh.rpc.serializer;

/**
 * 通用的序列化反序列化接口
 * @author Space_Pig
 * @date 2020/11/08 23:25
 */
public interface CommonSerializer {
    Integer KRYO_SERIALIZER = 0;
    Integer JSON_SERIALIZER = 1;
    Integer HESSIAN_SERIALIZER = 2;
    Integer PROTOBUF_SERIALIZER = 3;

    Integer DEFAULT_SERIALIZER = KRYO_SERIALIZER;
    /**
     * 系列化
     * @param object
     * @return
     */
    byte[] serialize(Object object);
    /**
     * 反序列化
     * @param bytes
     * @param clazz
     * @return
     */
    Object desSerialize(byte[] bytes,Class<?> clazz);

    /**
     * 获得该序列化的编号
     * @return
     */
    int getCode();

    static CommonSerializer getByCode(int code) {
        switch (code) {
            case 0:
                return new KryoSerializer();
            case 1:
                return new JsonSerializer();
//            case 2:
//                return new HessianSerializer();
//            case 3:
//                return new ProtobufSerializer();
            default:
                return null;
        }
    }
}
