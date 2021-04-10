package commandHandling.commands;

import commandHandling.CommandContext;
import commandHandling.CommandInterface;
import net.dv8tion.jda.api.EmbedBuilder;
import services.database.dbHandlerSpokesPeople;

import java.awt.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SemesterSpokesPeople implements CommandInterface {
    @Override
    public void handle(CommandContext ctx) {
        EmbedBuilder embed = new EmbedBuilder();
        String firstYear = dbHandlerSpokesPeople.getPeople(1);
        String secondYear = dbHandlerSpokesPeople.getPeople(2);

        embed.setTitle("Semester Spokes People");
        embed.setColor(new Color(0xb074ad));
        embed.addField("__First Year__", firstYear, true);
        embed.addBlankField(true);
        embed.addField("__Second Year__", secondYear, true);

        ctx.getChannel().sendMessage(embed.build()).queue(msg -> msg.delete().queueAfter(32, TimeUnit.SECONDS));
    }

    @Override
    public String getName() {
        return "SemesterSpokesPeople";
    }

    @Override
    public EmbedBuilder getHelp() {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Help - SpokesPeople");
        embed.setColor(new Color(0xb074ad));
        embed.setDescription("Returns a list of all subjects and the corresponding spokes people");
        embed.addField("Aliases", "```drdssp, drdSemesterSpokesPeople```", false);
        return embed;
    }

    @Override
    public List<String> getAliases() {
        return List.of("ssp", "spokespeople");
    }
}
