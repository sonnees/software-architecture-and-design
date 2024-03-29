package com.companyname.parser;

import com.github.javaparser.ast.Node;
/**
 * @author sonnees
 * @since 2024-01-23
 */
public class NodeIterator {
    public interface NodeHandler {
        boolean handle(Node node);
    }
    private final NodeHandler nodeHandler;
    public NodeIterator(NodeHandler nodeHandler) {
        this.nodeHandler = nodeHandler;
    }
    public void explore(Node node) {
        if (nodeHandler.handle(node)) {
            for (Node child : node.getChildNodes()) {
                explore(child);
            }
        }
    }
}
