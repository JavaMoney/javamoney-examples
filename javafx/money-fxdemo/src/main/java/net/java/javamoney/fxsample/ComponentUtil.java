package net.java.javamoney.fxsample;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Accordion;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;

public final class ComponentUtil {

	private ComponentUtil() {
		// Singleton
	}

	public static Node lookup(Node root, String id) {
		if (id.startsWith("#")) {
			id = id.substring(1);
		}
		Node node = root.lookup('#'+id);
		if(node == null){
			return lookupInternal(root, id);
		}
		return node;
	}

	private static Node lookupInternal(Node node, String id) {
		if (id.equals(node.getId())) {
			return node;
		}
		if (node instanceof Parent) {
			for (Node child : ((Parent) node).getChildrenUnmodifiable()) {
				Node result = lookupInternal(child, id);
				if (result != null) {
					return result;
				}
			}
			if (node instanceof SplitPane) {
				SplitPane sp = (SplitPane) node;
				Node result = null;
				for(Node child:sp.getItems()){
					result = lookupInternal(child, id);
					if (result != null) {
						return result;
					}
				}
			}
			if (node instanceof ScrollPane) {
				ScrollPane sp = (ScrollPane) node;
				Node result = null;
				if (sp.getContent() != null) {
					result = lookupInternal(sp.getContent(), id);
					if (result != null) {
						return result;
					}
				}
			}
			if (node instanceof BorderPane) {
				BorderPane sp = (BorderPane) node;
				Node result = null;
				if (sp.getBottom() != null) {
					result = lookupInternal(sp.getBottom(), id);
					if (result != null) {
						return result;
					}
				}
				if (sp.getTop() != null) {
					result = lookupInternal(sp.getTop(), id);
					if (result != null) {
						return result;
					}
				}
				if (sp.getLeft() != null) {
					result = lookupInternal(sp.getLeft(), id);
					if (result != null) {
						return result;
					}
				}
				if (sp.getRight() != null) {
					result = lookupInternal(sp.getRight(), id);
					if (result != null) {
						return result;
					}
				}
				if (sp.getCenter() != null) {
					result = lookupInternal(sp.getCenter(), id);
					if (result != null) {
						return result;
					}
				}
			}
			if (node instanceof TitledPane) {
				TitledPane sp = (TitledPane) node;
				Node result = null;
				if (sp.getContent() != null) {
					result = lookupInternal(sp.getContent(), id);
					if (result != null) {
						return result;
					}
				}
			}
			if (node instanceof Accordion) {
				Accordion sp = (Accordion) node;
				for (Node child : ((Accordion) node).getPanes()) {
					Node result = lookupInternal(child, id);
					if (result != null) {
						return result;
					}
				}
			}
			if (node instanceof TabPane) {
				TabPane bp = (TabPane) node;
				Node result = null;
				for (Tab tab : bp.getTabs()) {
					result = lookupInternal(tab.getContent(), id);
					if (result != null) {
						return result;
					}
				}
			}
		}
		return null;
	}

}
