package com.email.writer;

import io.netty.channel.EventLoop;
import io.netty.util.concurrent.EventExecutor;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.resolver.AddressResolver;
import io.netty.resolver.AddressResolverGroup;
import io.netty.resolver.InetSocketAddressResolver;
import io.netty.resolver.dns.DnsNameResolver;
import io.netty.resolver.dns.DnsNameResolverBuilder;
import io.netty.resolver.dns.DnsServerAddressStreamProviders;
import java.net.InetSocketAddress;

public class CustomDnsResolverGroup extends AddressResolverGroup<InetSocketAddress> {

    private final NioEventLoopGroup eventLoopGroup;

    public CustomDnsResolverGroup(NioEventLoopGroup eventLoopGroup) {
        this.eventLoopGroup = eventLoopGroup;
    }

    @Override
    protected AddressResolver<InetSocketAddress> newResolver(EventExecutor executor) throws Exception {
        EventLoop eventLoop = (EventLoop) executor;

        DnsNameResolver dnsResolver = new DnsNameResolverBuilder(eventLoop)
                .channelType(NioDatagramChannel.class)
                .nameServerProvider(DnsServerAddressStreamProviders.platformDefault())
                .maxQueriesPerResolve(2)
                .build();

        return new InetSocketAddressResolver(eventLoop, dnsResolver);
    }
}
