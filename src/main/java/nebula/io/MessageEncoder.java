package nebula.io;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.logging.Logger;

public class MessageEncoder extends MessageToByteEncoder<Message> {

    private static Logger logger = Logger.getLogger(MessageDecoder.class.getName());

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        msg.encode(out);
    }
}
