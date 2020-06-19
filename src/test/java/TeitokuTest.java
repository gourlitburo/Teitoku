import static org.junit.jupiter.api.Assertions.assertEquals;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import yy.gourlitburo.teitoku.Teitoku;
import yy.gourlitburo.teitoku.Node;
import yy.gourlitburo.teitoku.Executor;

public class TeitokuTest {
    static int state;

    @BeforeEach
    void setup() {
        state = 0;
    }

    @Test
    void testRoot() {
        Teitoku t = new Teitoku();
        Node root = new Node(new Executor() {
            public void run(CommandSender sender, Command command, String alias, String[] args) {
                if (args[0].equals("hello") && args[1].equals("world")) {
                    TeitokuTest.state = 1;
                }
            }
        });
        t.setRoot(root);

        String[] args = { "hello", "world" };
        t.onCommand(null, null, null, args);
        
        assertEquals(1, state);
    }

    @Test
    void testTree1() {
        Teitoku t = new Teitoku();
        t.setRoot(new Node());

        t.addChild(new Node("hello", new Executor() {
            public void run(CommandSender sender, Command command, String alias, String[] args) {
                if (args[0].equals("world")) {
                    TeitokuTest.state = 1;
                }
            }
        }));

        t.addChild(new Node("bye", new Executor() {
            public void run(CommandSender sender, Command command, String alias, String[] args) {
                TeitokuTest.state = 2;
            }
        }));

        String[] args0 = { "hello", "world" };
        t.onCommand(null, null, null, args0);
        assertEquals(1, state);

        String[] args1 = { "bye", "world" };
        t.onCommand(null, null, null, args1);
        assertEquals(2, state);
    }

    @Test
    void testTree2() {
        Teitoku t = new Teitoku();
        t.setRoot(new Node());

        Node greet = new Node("greet");
        greet.addChild(new Node("morning", new Executor() {
            public void run(CommandSender sender, Command command, String alias, String[] args) {
                if (args[0].equals("world")) {
                    TeitokuTest.state = 100;
                }
            }
        }));
        greet.addChild(new Node("afternoon", new Executor() {
            public void run(CommandSender sender, Command command, String alias, String[] args) {
                if (args[0].equals("sekai")) {
                    TeitokuTest.state = 200;
                }
            }
        }));
        t.addChild(greet);

        t.addChild(new Node("goodbye", new Executor() {
            public void run(CommandSender sender, Command command, String alias, String[] args) {
                if (args[0].equals("sunlight")) {
                    TeitokuTest.state = 2;
                }
            }
        }));

        String[] args0 = { "greet", "morning", "world" };
        t.onCommand(null, null, null, args0);
        assertEquals(100, state);

        String[] args1 = { "greet", "afternoon", "sekai" };
        t.onCommand(null, null, null, args1);
        assertEquals(200, state);

        String[] args2 = { "goodbye", "sunlight" };
        t.onCommand(null, null, null, args2);
        assertEquals(2, state);
    }
}
