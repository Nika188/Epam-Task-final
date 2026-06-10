package com.epam.rd.autotasks.confbeans.config;

import com.epam.rd.autotasks.confbeans.video.Channel;
import com.epam.rd.autotasks.confbeans.video.Video;
import com.epam.rd.autotasks.confbeans.video.VideoStudio;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class ChannelWithVideoStudioConfig {

    @Bean
    public VideoStudio videoStudio() {
        return new VideoStudio() {
            private int index = 1;
            private LocalDateTime release = LocalDateTime.of(2001, 10, 18, 10, 0);

            @Override
            public Video produce() {
                Video video = new Video("Cat & Curious " + index, release);
                index++;
                release = release.plusYears(2);
                return video;
            }
        };
    }

    @Bean
    public Channel channel(VideoStudio studio) {
        Channel channel = new Channel();

        for (int i = 0; i < 8; i++){
            channel.addVideo(studio.produce());
        }

        return channel;
    }
}
