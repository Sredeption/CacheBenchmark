package nebula.io;

import io.netty.buffer.ByteBuf;

public class GetRequest extends Message {

    public String key;

    public GetRequest(String key) {
        super();
        type = GET;
        length = key.length();
        this.key = key;
    }

    public GetRequest(int type, int length) {
        super(type, length);
    }


    @Override
    public void decode(ByteBuf in) {
        byte[] bytes = new byte[in.readableBytes()];
        in.readBytes(bytes);
        key = new String(bytes);
    }

    @Override
    public void encode(ByteBuf out) {
        out.writeByte(GET);
        out.writeShort(length);
        out.writeBytes(key.getBytes());
    }
}
