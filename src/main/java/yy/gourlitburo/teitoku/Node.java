package yy.gourlitburo.teitoku;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class Node implements Parent {
    NodeType type;
    String name;
    Executor executor = null;
    List<Node> children = new ArrayList<>(8);

    /* constructors */

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

    /* methods */

    public void execute(CommandSender sender, Command command, String alias, String[] args) throws UnsupportedOperationException {
        if (this.type != NodeType.EXECUTABLE) throw new UnsupportedOperationException();

        this.executor.run(sender, command, alias, args);
    }

    public void addChild(Node child) {
        children.add(child);
    }

    /* get/set */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Executor getExecutor() {
        return executor;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    public List<Node> getChildren() {
        return children;
    }
}
