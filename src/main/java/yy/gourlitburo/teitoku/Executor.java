package yy.gourlitburo.teitoku;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface Executor {
    public void run(CommandSender sender, Command command, String alias, String[] args);
}
