package nebula.io;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    private ChannelHandlerContext ctx;
    private AtomicBoolean received;
    private Message data;
    private static Logger logger = Logger.getLogger(ClientHandler.class.getName());

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.ctx = ctx;
        received = new AtomicBoolean();
    }

    public void sendMessage(Object message) {
        if (ctx == null)
            throw new IllegalStateException();
        received.set(false);
        ctx.writeAndFlush(message).channel();
    }

    public Message getData() {
        while (!received.get()) ;
        return data;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        data = (Message) msg;
        received.set(true);
    }
}
