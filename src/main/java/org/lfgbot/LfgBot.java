package org.lfgbot;


import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.lfgbot.listeners.EventListener;
import org.lfgbot.listeners.SlashCmdListener;


import javax.security.auth.login.LoginException;

public class LfgBot {

    final Dotenv config;


    public LfgBot() throws LoginException {

        config = Dotenv.configure().ignoreIfMissing().load();
        String token = config.get("TOKEN");

        JDABuilder jda = JDABuilder.createDefault(token);

        jda.enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT);
        jda.setStatus(OnlineStatus.ONLINE);
        jda.setActivity(Activity.watching("lfg chat"));

        EventListener events = new EventListener(config.get("LFG_CHANNEL"), config.get("COLOR_AS_HEX"));
        jda.addEventListeners(events);

        SlashCmdListener slashCmds = new SlashCmdListener();
        jda.addEventListeners(slashCmds);

        var build = jda.build();
        try {
            build.awaitReady();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
        registerSlashCmds(build);
    }


    public Dotenv getConfig(){
        return this.config;
    }

    private void registerSlashCmds(JDA build){
        Guild guild = build.getGuildById(config.get("GUILD_ID"));
        if (guild != null) {
            SlashCommandData newCommand = Commands.slash("stats", "Get R6 stats for a username");
            newCommand.addOption(OptionType.STRING, "username", "R6 username", true);
            guild.updateCommands().addCommands(newCommand).queue();
        }
    }

    public static void main(String[] args) {


        try {
            LfgBot bot = new LfgBot();
        } catch (net.dv8tion.jda.api.exceptions.InvalidTokenException e) {
            System.out.println("Please check your .env file");
            System.out.println(e.getMessage());
        } catch (javax.security.auth.login.LoginException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
        System.out.println("Configuration error: " + e.getMessage());
        System.out.println("Make sure TOKEN and other environment variables are set.");
        e.printStackTrace();
    }
    }
}
