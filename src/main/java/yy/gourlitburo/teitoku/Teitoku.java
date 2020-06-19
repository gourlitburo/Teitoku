package yy.gourlitburo.teitoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class Teitoku implements CommandExecutor, TabCompleter, Parent {
    /* members */

    private Node root;

    /**
     * Add a child to the root Node.
     * @param child The Node to add as a child to the root.
     */
    public void addChild(Node child) {
        root.addChild(child);
    }

    private Pair<Node, List<String>> getMatchingNode(List<String> argsList) {
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
                return new Pair<>(curNode, argsList);
            } else {
                argsList.remove(0);
                curNode = matchingNode;
            }
        }

        return new Pair<>(curNode, argsList);
    }

    private Pair<Node, String[]> getMatchingNode(String[] args) {
        List<String> argsList = new ArrayList<>(Arrays.asList(args));
        Pair<Node, List<String>> matchResult = getMatchingNode(argsList);

        String[] argsArray = matchResult.second.toArray(new String[matchResult.second.size()]);
        return new Pair<>(matchResult.first, argsArray);
    }

    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        Pair<Node, String[]> matchResult = getMatchingNode(args);
        Node matchingNode = matchResult.first;

        if (matchingNode != null && matchingNode.type == NodeType.EXECUTABLE && matchingNode.acceptsArguments) {
            matchingNode.execute(sender, command, alias, matchResult.second);
            return true;
        } else {
            return false;
        }
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> suggestions = new ArrayList<>();

        String lastArgPartial = args[args.length - 1];
        List<String> prevArgs = new ArrayList<>(Arrays.asList(args));
        prevArgs.subList(0, prevArgs.size() - 1);

        Pair<Node, List<String>> matchResult = getMatchingNode(prevArgs);
        Node matchingNode = matchResult.first;

        if (matchingNode != null) {
            for (Node child : matchingNode.getChildren()) {
                if (child.name.startsWith(lastArgPartial)) {
                    suggestions.add(child.name);
                }
            }
        }
        
        return suggestions;
    }

    /* get/set */

    public Node getRoot() {
        return this.root;
    }

    public void setRoot(Node child) {
        this.root = child;
    }
}
