import java.io.Serializable;

public class Node {

    private Object data;
    private Node next;
    private Node prev;

    public Node(Object obj) {
        this.data = obj;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public Node getNext() {
        return this.next;
    }

    public Node getPrev() {
        return this.prev;
    }

    public Object getData() {
        return this.data;
    }
}