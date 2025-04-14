import java.util.PriorityQueue;

class Node implements Comparable<Node> {
    String name;
    int priority;

    public Node(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    // Lower priority number = higher priority
    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.priority, other.priority);
    }

    @Override
    public String toString() {
        return name + " (priority: " + priority + ")";
    }
}

public class NamedPriorityQueueExample {
    public static void main(String[] args) {
        PriorityQueue<Node> queue = new PriorityQueue<>();

        queue.offer(new Node("A", 3));
        queue.offer(new Node("B", 1));
        queue.offer(new Node("C", 2));

        while (!queue.isEmpty()) {
            Node node = queue.poll();
            System.out.println("Processing: " + node);
        }
    }
}
