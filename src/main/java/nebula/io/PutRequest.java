package nebula.io;

import io.netty.buffer.ByteBuf;

public class PutRequest extends Message {
    public String key;
    public String value;

    public PutRequest(String key, String value) {
        super();
        type = PUT;
        length = 4 + key.length() + value.length();
        this.key = key;
        this.value = value;
    }

    public PutRequest(int type, int length) {
        super(type, length);
    }

    @Override
    public void decode(ByteBuf in) {
        short keyLength = in.readShort();
        byte[] keyBytes;
        keyBytes = new byte[keyLength];
        in.readBytes(keyBytes);
        key = new String(keyBytes);

        short valueLength = in.readShort();
        byte[] valueBytes = new byte[valueLength];
        in.readBytes(valueBytes);
        value = new String(valueBytes);
    }

    @Override
    public void encode(ByteBuf out) {
        out.writeByte(PUT);
        out.writeShort(length);

        out.writeShort(key.length());
        out.writeBytes(key.getBytes());

        out.writeShort(value.length());
        out.writeBytes(value.getBytes());
    }
}
