package yy.gourlitburo.teitoku;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class Node implements Parent {
    public NodeType type;
    public String name;
    public Executor executor = null;
    public List<Node> children = new ArrayList<>(8);

    public Node() {
        this.type = NodeType.INNER;
        this.name = "";
    }

    public Node(Executor executor) {
        this.type = NodeType.EXECUTABLE;
        this.name = "";
        this.executor = executor;
    }

    public Node(String name) {
        this.type = NodeType.INNER;
        this.name = name;
    }

    public Node(String name, Executor executor) {
        this.type = NodeType.EXECUTABLE;
        this.name = name;
        this.executor = executor;
    }

    public void execute(CommandSender sender, Command command, String alias, String[] args) throws UnsupportedOperationException {
        if (this.type != NodeType.EXECUTABLE) throw new UnsupportedOperationException();

        this.executor.run(sender, command, alias, args);
    }

    public void addChild(Node child) {
        children.add(child);
    }
}
