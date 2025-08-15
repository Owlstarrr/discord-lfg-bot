package org.lfgbot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class LfgUtil {

    public static void handleLfg(String messageAsString, MessageChannel channel, Member messageAuthor, String colorAsHex) {

        // TEMP message for testing purposes
        /* channel.sendMessage("LFG Message Recieved, Content: " + messageAsString).queue(sentMessage -> {
            sentMessage.delete().queueAfter(3, TimeUnit.SECONDS);
        }); */

        // IF USER IS IN VC
        if (messageAuthor != null
                && messageAuthor.getVoiceState() != null
                && messageAuthor.getVoiceState().inAudioChannel()) {

            AudioChannel vc = messageAuthor.getVoiceState().getChannel();
            if (vc == null) throw new NullPointerException("VC Cannot be null");

            //Create invite to channel
            MessageEmbed embed = buildEmbedForLfg(messageAuthor, messageAsString, vc, colorAsHex);
            channel.sendMessageEmbeds(embed).queue();


            // User is not in a VC
        } else if (messageAuthor != null) {
            channel.sendMessage(messageAuthor.getAsMention() +
                    " you must be in a voice channel to use this command.").queue(sentMessage -> {
                sentMessage.delete().queueAfter(5, TimeUnit.SECONDS);
            });
        }
    }

    public static MessageEmbed buildEmbedForLfg(Member messageAuthor, String trimmedMsg, AudioChannel vc, String colorAsHex) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(messageAuthor.getEffectiveName(),
                null, messageAuthor.getEffectiveAvatarUrl());
        builder.setColor(Color.decode(colorAsHex));
        builder.setTitle("Looking for Group");
        builder.addField("User message: ", trimmedMsg, false);
        builder.addField("Join here ", vc.getAsMention(), false);
        return builder.build();
    }
}
