package yy.gourlitburo.teitoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Teitoku implements CommandExecutor, Parent {
    /* members */

    private Node root;
    public void setRoot(Node child) {
        this.root = child;
    }
    public Node getRoot() {
        return this.root;
    }

    /**
     * Add a child to the root Node.
     * @param child The Node to add as a child to the root.
     */
    public void addChild(Node child) {
        root.addChild(child);
    }

    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        List<String> argsList = new ArrayList<>(Arrays.asList(args));
        Node curNode = root;

        while (!argsList.isEmpty()) {
            String topArg = argsList.get(0);
            Node matchingNode = null;
            for (Node node : curNode.children) {
                if (node.name.equals(topArg)) {
                    matchingNode = node;
                    break;
                }
            }
            if (matchingNode == null) {
                if (curNode.type == NodeType.EXECUTABLE) {
                    String[] argsArray = argsList.toArray(new String[argsList.size()]);
                    curNode.execute(sender, command, alias, argsArray);
                } else {
                    return false;
                }
                break;
            } else {
                argsList.remove(0);
                curNode = matchingNode;
            }
        }

        return true;
    }
}
