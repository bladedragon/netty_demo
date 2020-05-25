package NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Server {

    public static void main(String[] args) throws IOException {
        Selector serverSelector = Selector.open();
        Selector clientSelector = Selector.open();

        new Thread(()->{
            try {
                ServerSocketChannel listenChannel = ServerSocketChannel.open();
                listenChannel.socket().bind(new InetSocketAddress(8000));
                listenChannel.configureBlocking(false);
                listenChannel.register(serverSelector, SelectionKey.OP_ACCEPT);

                while(true){
                    if(serverSelector.select(1) > 0){
                        Set<SelectionKey> set = serverSelector.keys();
                        Iterator<SelectionKey> iterator = set.iterator();

                        while(iterator.hasNext()){
                            SelectionKey key = iterator.next();

                            if(key.isAcceptable()) {
                                try {
                                    SocketChannel clientChannel = ((ServerSocketChannel) key.channel()).accept();
                                    clientChannel.configureBlocking(false);
                                    clientChannel.register(clientSelector, SelectionKey.OP_READ);
                                }finally {
//                                    iterator.remove();
                                }
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();

        new Thread(()->{
            try {
            while(true){

                    if(clientSelector.select(1) > 0){
                        Set<SelectionKey> set = clientSelector.keys();
                        Iterator<SelectionKey> iterator = set.iterator();

                        while(iterator.hasNext()){
                            SelectionKey key = iterator.next();
                            if(key.isReadable()){
                                try{
                                    SocketChannel clientChannel = (SocketChannel) key.channel();
                                    ByteBuffer bytebuffer = ByteBuffer.allocate(1024);

                                    clientChannel.read(bytebuffer);
                                    bytebuffer.flip();
                                    System.out.println(Charset.defaultCharset().newDecoder().decode(bytebuffer).toString());
                                }finally{
//                                    iterator.remove();
                                    key.interestOps(SelectionKey.OP_READ);
                                }
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }
}
