import java.io.Serializable;

public class MyQueue {
    int count;
    Node head;
    Node tail;

    public MyQueue() {
        head = new Node(null);
        tail = new Node(null);
        count = 0;
        head.setNext(tail);
    }


    public void add(Object o) {
        if(o == null)
            return;
        Node temp = new Node(o);
        if (head.getData() == null) {
            head = new Node(o);
            count++;
            head.setNext(tail);
        }
        else if(head.getNext().getData() != null) {
            Node ptr = head.getNext();
            while(ptr.getNext() != null) {
                if(ptr.getNext().getData() == null) {
                    ptr.setNext(temp);
                    temp.setNext(tail);
                    count++;
                    break;
                }
                ptr = ptr.getNext();
            }
        }
        else {
            head.setNext(temp);
            temp.setNext(tail);
            count++;
        }
    }

    public Node peek() {
        if(isEmpty()) return null;
        return head;
    }

    public synchronized Node remove() { //CHANGELOG: added synchronized
        if(isEmpty())
            return null;
        else {
            Node toReturn = head;
            head = head.getNext();
            count--;
            //System.out.println(toReturn.getData());
            return toReturn;
            }
        }

    public boolean isEmpty() {
        return count == 0;
    }

    public int size() {
        return count;
    }

}