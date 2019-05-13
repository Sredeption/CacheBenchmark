package nebula.io;

import io.netty.buffer.ByteBuf;

public abstract class Message {

    public static final int GET = 0;
    public static final int GET_RESPONSE = 1;
    public static final int PUT = 2;
    public static final int PUT_RESPONSE = 3;

    protected int type;
    protected int length;

    public Message() {

    }

    public Message(int type, int length) {
        this.type = type;
        this.length = length;
    }

    public abstract void decode(ByteBuf in);

    public abstract void encode(ByteBuf out);

    public static Message create(int type, int length) throws Exception {
        switch (type) {
            case GET:
                return new GetRequest(type, length);
            case GET_RESPONSE:
                return new GetResponse(type, length);
            case PUT:
                return new PutRequest(type, length);
            case PUT_RESPONSE:
                return new PutResponse(type, length);
        }
        throw new Exception(String.format("unknown message type:%d, length:%d", type, length));
    }

}
