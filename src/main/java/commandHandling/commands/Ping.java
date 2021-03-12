package commandHandling.commands;

import commandHandling.CommandContext;
import commandHandling.CommandInterface;
import net.dv8tion.jda.api.JDA;
import org.slf4j.Logger;

public class Ping implements CommandInterface {
    public Ping(Logger LOGGER) {
        LOGGER.info("Ping Command loaded");
    }

    @Override
    public void handle(CommandContext ctx) {
        JDA jda = ctx.getJDA();

        jda.getRestPing().queue(
                (ping) -> ctx.getChannel().sendMessageFormat("Reset ping: %sms", ping).queue()
        );
    }

    @Override
    public String getName() {
        return "ping";
    }
}
