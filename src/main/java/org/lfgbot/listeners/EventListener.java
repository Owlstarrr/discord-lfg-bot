package org.lfgbot.listeners;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.lfgbot.LfgUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Event Listeners for Discord Bot
public class EventListener extends ListenerAdapter {

    public final String lfgChannelId;
    public final String colorAsHex;

    public EventListener(String lfgChannelId, String colorAsHex) {
        this.lfgChannelId = lfgChannelId;
        this.colorAsHex = colorAsHex;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        if (event.getAuthor().isBot() || event.isWebhookMessage()) return;

        Message message = event.getMessage();
        String messageAsString = event.getMessage().getContentRaw();
        MessageChannel channel = event.getChannel();
        String channelID = channel.getId();
        Member messageSender = event.getMember();

        ArrayList<String> ids = parseChannelsToArrayByComma(lfgChannelId);
        if (!ids.contains(channelID)) return;
        if (!messageAsString.toLowerCase().startsWith("!lfg ")) return;

        String trimmedMessage = messageAsString.substring(5);

        System.out.println("USER: " + messageSender.getEffectiveName() + " MSG: " + trimmedMessage);

        event.getMessage().delete().queue();
        LfgUtil.handleLfg(trimmedMessage, channel, messageSender, colorAsHex);

    }

    public static ArrayList<String> parseChannelsToArrayByComma(String lfgChannelId){
        return new ArrayList<>(Arrays.asList(lfgChannelId.split(",")));
    }

}