package org.lfgbot.listeners;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;


public class EventListener extends ListenerAdapter {

    public final String lfgChannelId;

    public EventListener(String lfgChannelId){
        this.lfgChannelId = lfgChannelId;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        Message message = event.getMessage();
        String channelID = event.getChannel().getId();

        if (!channelID.equals(lfgChannelId)) return;

        System.out.println(message);

    }


    public String getCHANNEL(Dotenv config) {
        String channel = config.get("CHANNEL");
        return channel;
    }
}