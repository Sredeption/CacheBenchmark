package nebula.io;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.logging.Logger;

public class CacheService extends ChannelInboundHandlerAdapter {
    private static Logger logger = Logger.getLogger(CacheService.class.getName());
    private static Cache cache = new Cache(100);


    CacheService() {
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message) msg;

        switch (message.type) {
            case Message.GET:
                get(ctx, (GetRequest) message);
                break;
            case Message.PUT:
                put(ctx, (PutRequest) message);
                break;

        }
    }

    public void get(ChannelHandlerContext ctx, GetRequest msg) {
        ctx.channel().writeAndFlush(new GetResponse(cache.get(msg.key)));
    }

    public void put(ChannelHandlerContext ctx, PutRequest msg) {
        cache.put(msg.key, msg.value);
        ctx.channel().writeAndFlush(new PutResponse(true));
    }

}
