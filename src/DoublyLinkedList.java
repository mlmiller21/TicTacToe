import java.util.Iterator;
import java.util.NoSuchElementException;

// Doubly linked list
// Used over a singly linked list to account for removal of an entry in the middle of a linkedlist
public class DoublyLinkedList {

    public static class Node {
        private Record record;
        private Node next;
        private Node prev;

        public Node(Record record, Node next, Node prev) {
            this.record = record;
            this.next = next;
            this.prev = prev;
        }

        public Node getNext(){
            return this.next;
        }

        public Node getPrev(){
            return this.prev;
        }

        public void setNext(Node next){
            this.next = next;
        }

        public void setPrev(Node prev){
            this.prev = prev;
        }

        public Record getRecord(){
            return record;
        }

    }

    private Node header = null;
    private Node trailer = null;
    private int size = 0;

    public DoublyLinkedList(){
        header = new Node(null, null, null);
        trailer = new Node(null, null, header);
        header.setNext(trailer);
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public Node first(){
        return header.getNext();
    }

    public void addLast(Record record){
        addBetween(record, trailer, trailer.getPrev());
    }

    // Add element between two given nodes
    private void addBetween(Record record, Node successor,  Node predecessor){
        Node node = new Node(record, successor, predecessor);
        predecessor.setNext(node);
        successor.setPrev(node);
        size++;
    }

    private Record remove(Node node){
        Node predecessor = node.getPrev();
        Node successor = node.getNext();
        predecessor.setNext(successor);
        successor.setPrev(predecessor);

        size--;
        return node.getRecord();
    }
    // Iterator object for node type
    private class nodeIterator implements Iterator<Node> {

        private Node cursor = first();
        private Node recent = null;

        @Override
        public boolean hasNext() {
            return (cursor != null && cursor != trailer);
        }

        @Override
        public Node next() throws NoSuchElementException{
            if (cursor == null)
                throw new NoSuchElementException("End of list");
            recent = cursor;
            cursor = cursor.getNext();
            return recent;
        }

        @Override
        public void remove() throws NoSuchElementException {
            if (recent == null)
                throw new NoSuchElementException("End of list");
            DoublyLinkedList.this.remove(recent);
            recent = null;
        }
    }
    // Iterator object for Record, utilizes nodeIterator
    private class recordIterator implements Iterator<Record> {
        Iterator<Node> nodeIterator = new nodeIterator();

        @Override
        public boolean hasNext() {
            return nodeIterator.hasNext();
        }

        @Override
        public Record next() {
            return nodeIterator.next().getRecord();
        }

        @Override
        public void remove() {
            nodeIterator.remove();
        }
    }

    public Iterator<Record> iterator(){
        return new recordIterator();
    }

}
