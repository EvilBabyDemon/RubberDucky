package commandHandling.commands.ownerCommands;

import commandHandling.CommandContext;
import commandHandling.CommandInterface;
import net.dv8tion.jda.api.EmbedBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.CommandManager;
import services.database.DatabaseHandler;
import services.logging.EmbedHelper;

import java.awt.*;

public class SQL implements CommandInterface {
    private final Logger LOGGER = LoggerFactory.getLogger(SQL.class);

    public SQL(Logger cmdManagerLogger) {
        cmdManagerLogger.info("Loaded Command " + getName());
    }

    @Override
    public void handle(CommandContext ctx) {
        StringBuilder sb = new StringBuilder();

        for(String s : ctx.getArguments()) {
            sb.append(s).append(" ");
        }

        int ret = DatabaseHandler.sqlExecuteUpdate(sb.toString());

        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Rows affected: " + ret);
        embed.setDescription("rdsql " + sb);

        if (ret > 0) {
            CommandManager.commandLogger(getName(), ctx, true);
            embed.setColor(new Color(0x009608));
        } else {
            CommandManager.commandLogger(getName(), ctx, false);
            if (ret == 0) {
                embed.setColor(new Color(0xe3d800));
            } else {
                embed.setColor(new Color(0xbf0000));
            }
        }

        EmbedHelper.sendEmbed(ctx, embed, 32);
    }

    @Override
    public String getName() {
        return "SQL";
    }

    @Override
    public EmbedBuilder getHelp() {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setDescription("Allows editing of values in the database");
        embed.addField("Supported SQL Commands", "- `delete`\n- `insert`\n- `update`", false);
        return embed;
    }

    @Override
    public int getRestrictionLevel() {
        return 0;
    }

    @Override
    public boolean requiresFurtherChecks() {
        return true;
    }
}
