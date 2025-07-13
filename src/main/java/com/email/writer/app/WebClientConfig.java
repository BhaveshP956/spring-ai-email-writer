package com.email.writer.app;

import com.email.writer.CustomDnsResolverGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.resolver.AddressResolverGroup;
import java.net.InetSocketAddress;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

public class WebClientConfig {

    public WebClient createWebClientWithCustomDns() {
        // Create your custom resolver group (built earlier)
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        AddressResolverGroup<InetSocketAddress> resolverGroup = new CustomDnsResolverGroup(eventLoopGroup);

        // Create Reactor Netty HTTP client with custom resolver
        HttpClient httpClient = HttpClient.create()
                .resolver(resolverGroup);

        // Build WebClient
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
