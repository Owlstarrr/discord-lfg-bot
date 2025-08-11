package org.lfgbot;


import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;


import javax.security.auth.login.LoginException;

public class LfgBot {

     final Dotenv config;

    public LfgBot() throws LoginException {
        config = Dotenv.configure().ignoreIfMissing().load();
        String token = config.get("TOKEN");

        JDABuilder jda = JDABuilder.createDefault(token);
        jda.setStatus(OnlineStatus.ONLINE);
        jda.setActivity(Activity.watching("lfg chat"));
        jda.build();

    }


    public Dotenv getConfig(){
        return this.config;
    }



    public static void main(String[] args) {


        try {
            LfgBot bot = new LfgBot();
        } catch (LoginException e) {
            System.out.println("bot token invalid");
        }
    }
}
