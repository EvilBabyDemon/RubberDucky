package services.listener;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.ThreadChannel;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class CountThreadListener extends ListenerAdapter {

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        if (!event.getJDA().getSelfUser().getId().equals("817846061347242026")) {
            return;
        }

        ThreadChannel thread = event.getJDA().getGuildById("747752542741725244").getThreadChannelById("996746797236105236");

        for (Message message : thread.getHistory().retrievePast(64).complete()) {
            try {
                if (message.getAuthor().getId().equals("817846061347242026")) {
                    break;
                } else if (message.getAuthor().getId().equals("838098002844844032")) {
                    thread.sendMessage("" + (Integer.parseInt(message.getContentRaw()) + 1)).queue();
                    break;
                } else if (!message.getAuthor().getId().equals("817846061347242026")) {
                    message.delete().queue();
                }
            } catch (Exception ignored) {}
        }
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getChannel().getId().equals("996746797236105236")) {
            if (!event.getAuthor().getId().equals("838098002844844032") && !event.getAuthor().getId().equals("817846061347242026")) {
                event.getMessage().delete().queue();
            } else if (event.getAuthor().getId().equals("838098002844844032")) {
                try {

                    event.getThreadChannel().sendMessage("" + (Integer.parseInt(event.getMessage().getContentRaw()) + 1)).queue();
                } catch (Exception ignored) {}
            }
        }
    }
}
