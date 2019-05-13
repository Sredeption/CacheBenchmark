package nebula.io;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;
import java.util.logging.Logger;

public class MessageDecoder extends ByteToMessageDecoder {
    private static Logger logger = Logger.getLogger(MessageDecoder.class.getName());

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 3)
            return;
        in.markReaderIndex();
        byte type = in.readByte();
        short length = in.readShort();
        if (in.readableBytes() < length) {
            in.resetReaderIndex();
            return;
        }

        Message message = Message.create(type, length);
        message.decode(in.readSlice(length));
        out.add(message);
    }
}
