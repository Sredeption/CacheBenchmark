package nebula.io;

import io.netty.buffer.ByteBuf;

public class PutResponse extends Message {
    public boolean status;

    public PutResponse(boolean status) {
        super();
        this.status = status;
        type = PUT_RESPONSE;
        length = 1;
    }

    public PutResponse(int type, int length) {
        super(type, length);
    }

    @Override
    public void decode(ByteBuf in) {
        status = in.readBoolean();
    }

    @Override
    public void encode(ByteBuf out) {
        out.writeByte(PUT_RESPONSE);
        out.writeShort(length);
        out.writeBoolean(status);
    }
}
