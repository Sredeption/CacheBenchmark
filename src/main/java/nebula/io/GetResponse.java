package nebula.io;

import io.netty.buffer.ByteBuf;

public class GetResponse extends Message {
    public boolean status;
    public String value;

    public GetResponse(String value) {
        super();
        type = GET_RESPONSE;
        length = 1;
        if (value != null) {
            status = true;
            length += value.length();
        } else {
            status = false;
        }
        this.value = value;
    }

    public GetResponse(int type, int length) {
        super(type, length);
    }

    @Override
    public void decode(ByteBuf in) {
        status = in.readBoolean();
        if (status) {
            byte[] bytes = new byte[in.readableBytes()];
            in.readBytes(bytes);
            value = new String(bytes);
        }
    }

    @Override
    public void encode(ByteBuf out) {
        out.writeByte(GET_RESPONSE);
        out.writeShort(length);
        out.writeBoolean(status);
        if (value != null)
            out.writeBytes(value.getBytes());
    }
}
