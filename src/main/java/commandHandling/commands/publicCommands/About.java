package commandHandling.commands.publicCommands;

import commandHandling.CommandContext;
import commandHandling.CommandInterface;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDAInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.Miscellaneous.TimeFormat;
import services.logging.EmbedHelper;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;

public class About implements CommandInterface {
    private final Logger LOGGER = LoggerFactory.getLogger(About.class);

    public About(Logger cmdManagerLogger) {
        cmdManagerLogger.info("Loaded Command " + getName());
    }

    @Override
    public void handle(CommandContext ctx) {
        RuntimeMXBean rmb = ManagementFactory.getRuntimeMXBean();
        EmbedBuilder embed = EmbedHelper.embedBuilder("About RubberDucky");

        embed.setThumbnail(ctx.getSelfUser().getAvatarUrl());
        embed.setDescription("[GitHub](https://github.com/4ndriH/RubberDucky)");
        embed.addField("**JDA version:**", JDAInfo.VERSION_MAJOR + "."
                + JDAInfo.VERSION_MINOR + "." + JDAInfo.VERSION_REVISION, true);
        embed.addField("**Uptime:**", TimeFormat.timeFormat((int)(rmb.getUptime() / 1000)), true);

        EmbedHelper.sendEmbed(ctx, embed, 32);
    }

    @Override
    public String getName() {
        return "About";
    }

    @Override
    public EmbedBuilder getHelp() {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setDescription("Provides some information about me");
        return embed;
    }

    @Override
    public List<String> getAliases() {
        return List.of("info", "source");
    }
}
