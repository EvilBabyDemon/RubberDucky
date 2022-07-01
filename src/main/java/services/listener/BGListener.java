package services.listener;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static services.database.DBHandlerConfig.getConfig;
import static services.database.DBHandlerConfig.updateConfig;

public class BGListener extends ListenerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(BGListener.class);
    private static int nextNotification = 0;

    @Override
    public void onGuildMessageUpdate(@NotNull GuildMessageUpdateEvent event) {
        Message message = event.getMessage();

        if (event.getAuthor().getId().equals("778731540359675904") && message.getContentRaw().contains("button")) {
            int buttonScore = Integer.parseInt(message.getActionRows().get(0).getButtons().get(0).getLabel());
            LOGGER.info("Button score updated: " + buttonScore);
            int myCurrentScore = Integer.parseInt(getConfig().get("ButtonScore"));

            if (buttonScore > myCurrentScore) {
                if (--nextNotification <= 0) {
                    LOGGER.error("New notification sent " + "[\" + myCurrentScore + \" -> \" + buttonScore + \"]");
                    event.getJDA().openPrivateChannelById("155419933998579713").complete().sendMessage(
                            "Button is ready [" + myCurrentScore + " -> " + buttonScore + "]\n" +
                                "https://discord.com/channels/747752542741725244/" + event.getChannel().getId() +
                                "/" + event.getMessage().getId()).queue();
                    nextNotification = 10;
                }
            }
        }
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if (event.getAuthor().getId().equals("778731540359675904")) {
            String messageContent = event.getMessage().getContentRaw();

            if (messageContent.contains("155419933998579713")) {
                updateConfig("Button Score", messageContent.split(" ")[3]);
                LOGGER.error("I claimed points and it got updated in the db: " + getConfig().get("ButtonScore"));
            }

            if (messageContent.contains("has claimed")) {
                LOGGER.error("someone else has claimed the button");
                nextNotification = 0;
            }
        }
    }
}
