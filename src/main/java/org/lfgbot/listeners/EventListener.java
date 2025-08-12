package org.lfgbot.listeners;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.lfgbot.LfgBot;

import java.awt.*;
import java.util.HexFormat;
import java.util.concurrent.TimeUnit;

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


        if (!channelID.equals(lfgChannelId)) return;
        if (!messageAsString.toLowerCase().startsWith("!lfg ")) return;

        String trimmedMessage = messageAsString.substring(5);

        System.out.println("MSG: " + trimmedMessage);

        event.getMessage().delete().queue();
        handleLfg(trimmedMessage, channel, messageSender);

    }

    private void handleLfg(String message, MessageChannel channel, Member messageAuthor) {

        // TEMP message for testing purposes
        channel.sendMessage("LFG Message Recieved, Content: " + message).queue(sentMessage -> {
            sentMessage.delete().queueAfter(3, TimeUnit.SECONDS);
        });

        // IF USER IS IN VC
        if (messageAuthor != null
                && messageAuthor.getVoiceState() != null
                && messageAuthor.getVoiceState().inAudioChannel()) {

            AudioChannel vc = messageAuthor.getVoiceState().getChannel();

            //TODO: integrate embed creation
            //buildEmbedForLfg();

        // User is not in a VC
        } else if (messageAuthor != null) {
                channel.sendMessage(messageAuthor.getAsMention() +
                        " you must be in a voice channel to use this command.").queue(sentMessage -> {
                            sentMessage.delete().queueAfter(5, TimeUnit.SECONDS);
                        });
        }
    }

    private EmbedBuilder buildEmbedForLfg(Member messageAuthor, MessageChannel channel, Message message, String msgAsStr, AudioChannel vc) {
        EmbedBuilder builder = new EmbedBuilder();

        builder.setAuthor(messageAuthor.getEffectiveName(),
                null, messageAuthor.getEffectiveAvatarUrl());
        builder.setColor(Color.decode(colorAsHex));
        builder.setTitle(messageAuthor.getAsMention() + " is Looking for Group");
        builder.setDescription(message.getContentRaw());
        builder.addField("Join here", "[" + vc.getJumpUrl() + "]", false);

        return builder;
    }


}