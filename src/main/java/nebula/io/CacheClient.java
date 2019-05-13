package nebula.io;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.junit.Assert;

import java.util.logging.Logger;


public class CacheClient {
    private Channel channel;
    private ClientHandler clientHandler;
    private EventLoopGroup group;
    private static Logger logger = Logger.getLogger(CacheClient.class.getName());

    CacheClient() {
        clientHandler = new ClientHandler();
    }

    public void connect(String host, int port) throws Exception {
        group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        try {
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        public void initChannel(Channel channel) {
                            ChannelPipeline pipeline = channel.pipeline();
                            pipeline.addLast(new MessageEncoder());
                            pipeline.addLast(new MessageDecoder());
                            pipeline.addLast(clientHandler);
                        }
                    })
                    .option(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture channelFuture = bootstrap.connect(host, port);
            channel = channelFuture.sync().channel();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        channel.closeFuture().sync();
                    } catch (InterruptedException e) {
                        group.shutdownGracefully();
                        e.printStackTrace();
                    }
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
            group.shutdownGracefully();
            throw e;
        }
    }

    String get(String key) {
        clientHandler.sendMessage(new GetRequest(key));
        GetResponse response = (GetResponse) clientHandler.getData();

        if (response.status)
            return response.value;
        else
            return null;

    }

    void put(String key, String value) {
        clientHandler.sendMessage(new PutRequest(key, value));
        PutResponse response = (PutResponse) clientHandler.getData();
        assert (response.status);
    }

    public static void main(String[] args) throws Exception {

        String hostName = args[0];
        int port = Integer.valueOf(args[1]);

        CacheClient rpcClient = new CacheClient();
        rpcClient.connect(hostName, port);

        for (int i = 0; i < 100; i++) {
            String value;
            rpcClient.put("k" + i, "v" + i);
            value = rpcClient.get("k" + i);
            Assert.assertEquals("v" + i, value);
        }

        logger.info("test finished");
    }

}