package org.lfgbot.listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;


public class SlashCmdListener extends ListenerAdapter {

    public static final String CMD_STATS = "stats";
    public static final String STATS_USERNAME = "username";

    public SlashCmdListener() {
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case CMD_STATS: handleStats(event);
            default: return;
        }
    }


    private void handleStats(SlashCommandInteractionEvent event) {

        Member member = event.getMember();
        var opt = event.getOption(STATS_USERNAME);
        if (opt == null || opt.getAsString().isBlank()){
            event.reply("Please provide a username: `/stats <username>`")
                    .setEphemeral(true).queue();
            return;
        }
        String username = event.getOption(STATS_USERNAME).getAsString();
        String link = "https://r6.tracker.network/r6siege/profile/ubi/" + username + "/overview";
        if (event.getMember() != null) {
            event.reply(member.getAsMention() + " Stats for " + username + ": " + link).queue();
        } else {
            var msg = event.reply("ERROR: I cant find who called the function! Check perms...");
            msg.setEphemeral(true).queue();
            msg.queue();
        }
    }


}