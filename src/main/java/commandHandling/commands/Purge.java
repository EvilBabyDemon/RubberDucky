package commandHandling.commands;

import commandHandling.CommandContext;
import commandHandling.CommandInterface;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import org.slf4j.Logger;
import services.DiscordLogger;
import services.Miscellaneous;

import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Purge implements CommandInterface {
    private volatile boolean isRunning, stop;
    private final EmbedBuilder purgeCommenced = new EmbedBuilder();
    private final EmbedBuilder busyPurging = new EmbedBuilder();
    private final EmbedBuilder purgeEnded = new EmbedBuilder();

    public Purge(Logger LOGGER) {
        LOGGER.info("Loaded Command Purge");
        embedSetUp();
    }

    @Override
    public void handle(CommandContext ctx) {
        if (isRunning) {
            if (ctx.getArguments().size() == 1 && ctx.getArguments().get(0).equalsIgnoreCase("stop")) {
                stop = true;
            } else {
                ctx.getChannel().sendMessageEmbeds(busyPurging.build())
                        .addFile(new File("resources/purge/busyPurging.png")).queue(
                                msg -> Miscellaneous.deleteMsg(msg, 32)
                );
            }
            DiscordLogger.command(ctx, "purge", true);
            return;
        } else {
            isRunning = true;
        }

        DiscordLogger.command(ctx, "purge", true);

        (new Thread(() -> {
            ctx.getChannel().sendMessageEmbeds(purgeCommenced.build())
                    .addFile(new File("resources/purge/purgeCommenced.jpg")).queueAfter(1, TimeUnit.SECONDS);
            List<Message> messages;
            do {
                messages = ctx.getChannel().getHistory().retrievePast(64).complete();
                for (int i = 0; i < messages.size() && !stop; i++) {
                    messages.get(i).delete().complete();
                    try {
                        Thread.sleep(1024);
                    } catch (Exception ignored) {}
                }
            } while (messages.size() != 0 && !stop);
            ctx.getChannel().sendMessageEmbeds(purgeEnded.build())
                    .addFile(new File("resources/purge/purgeEnded.jpg")).queue(
                    msg -> Miscellaneous.deleteMsg(msg, 32)
            );
            isRunning = stop = false;
        })).start();
    }

    private void embedSetUp() {
        purgeCommenced.setTitle("Happy purging");
        purgeCommenced.setColor(new Color(0xb074ad));
        purgeCommenced.setImage("attachment://purgeCommenced.jpg");
        busyPurging.setTitle("Already busy purging");
        busyPurging.setColor(new Color(0xb074ad));
        busyPurging.setImage("attachment://busyPurging.png");
        purgeEnded.setTitle("Thank you for participating in the purge <3");
        purgeEnded.setColor(new Color(0xb074ad));
        purgeEnded.setImage("attachment://purgeEnded.jpg");
    }

    @Override
    public String getName() {
        return "Purge";
    }

    @Override
    public EmbedBuilder getHelp() {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setDescription("Deletes all messages in the current channel");
        return embed;
    }

    @Override
    public boolean isOwnerOnly() {
        return true;
    }
}
