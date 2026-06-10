package com.epam.rd.autotasks.confbeans.config;

import com.epam.rd.autotasks.confbeans.video.Channel;
import com.epam.rd.autotasks.confbeans.video.Video;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@Configuration
public class ChannelWithInjectedPrototypeVideoConfig {

    private static int index = 0;
    private static final LocalDateTime release = LocalDateTime.of(2020, 1, 1, 0, 0);

    @Bean
    @Scope("prototype")
    public Video video() {
        Video video = new Video("Cat Failure Compilation", release.plusDays(index));
        index++;

        return video;
    }

    @Bean
    public Channel channel(ObjectProvider<Video> videoProvider) {
        return new Channel() {
            @Override
            public Stream<Video> videos() {
                return Stream.generate(videoProvider::getObject);
            }
        };
    }
}
