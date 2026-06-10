package com.epam.rd.autotasks.confbeans.config;

import com.epam.rd.autotasks.confbeans.video.Channel;
import com.epam.rd.autotasks.confbeans.video.Video;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.time.LocalDateTime;

@Configuration
public class ChannelWithPhantomVideoStudioConfig {

    private int index = 9;
    private final LocalDateTime release = LocalDateTime.of(2001, 10, 18, 10, 0);

    @Bean
    @Scope("prototype")
    public Video video() {
        Video video = new Video("Cat & Curious " + index, release.plusYears((index - 1) * 2L));
        index++;

        return video;
    }

    @Bean
    public Channel channel() {
        Channel channel = new Channel();

        for (int i = 1; i <= 8; i++){
            channel.addVideo(new Video("Cat & Curious " + i, release.plusYears((i - 1) * 2L)));
        }

        return channel;
    }
}
