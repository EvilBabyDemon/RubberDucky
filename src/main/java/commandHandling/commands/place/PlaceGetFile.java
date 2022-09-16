package commandHandling.commands.place;

import commandHandling.CommandContext;
import commandHandling.CommandInterface;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.utils.FileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import assets.CONFIG;
import assets.Objects.Pixel;
import services.BotExceptions;
import services.database.DBHandlerPlace;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static services.discordHelpers.MessageDeleteHelper.deleteMsg;

public class PlaceGetFile implements CommandInterface {
    private final Logger LOGGER = LoggerFactory.getLogger(PlaceGetFile.class);

    public PlaceGetFile(Logger cmdManagerLogger) {
        cmdManagerLogger.info("Loaded Command " + getName());
    }

    @Override
    public void handle(CommandContext ctx) {
        int id;

        try {
            id = Integer.parseInt(ctx.getArguments().get(0));
        } catch (Exception e) {
            BotExceptions.invalidArgumentsException(ctx);
            return;
        }

        if (DBHandlerPlace.getPlaceProjectIDs().contains(id)) {
            try {
                ArrayList<Pixel> pixels = DBHandlerPlace.getProjectPixels(id);
                String output = pixels.stream().map(Objects::toString).collect(Collectors.joining("\n"));

                ctx.getChannel().sendFiles(FileUpload.fromData(output.getBytes(), "RDdraw" + id + ".txt")).queue(
                        msg -> deleteMsg(msg, 64)
                );
            } catch (IllegalArgumentException e) {
                BotExceptions.FileExceedsUploadLimitException(ctx);
            }
        } else {
            BotExceptions.fileDoesNotExistException(ctx);
        }
    }

    @Override
    public String getName() {
        return "PlaceGetFile";
    }

    @Override
    public EmbedBuilder getHelp() {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setDescription("Returns the project with the given ID");
        embed.addField("__Usage__", "```" + CONFIG.Prefix.get() + getName() + " <ID>```", false);
        return embed;
    }

    @Override
    public List<String> getAliases() {
        return List.of("pgf");
    }
}
