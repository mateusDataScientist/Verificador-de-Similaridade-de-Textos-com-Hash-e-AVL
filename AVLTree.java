import java.util.ArrayList;

public class AVLTree {

    class Node {

        double key;
        ArrayList<Resultado> resultados;

        Node left;
        Node right;

        int height;

        Node(double key, Resultado r) {

            this.key = key;

            resultados = new ArrayList<>();
            resultados.add(r);

            height = 1;
        }
    }

    private Node root;

    private RotationStats stats = new RotationStats();

    public RotationStats getRotationStats() {
        return stats;
    }

    private int height(Node n) {

        if (n == null) {
            return 0;
        }

        return n.height;
    }

    private int balance(Node n) {

        if (n == null) {
            return 0;
        }

        return height(n.left) - height(n.right);
    }

    private Node rotateRight(Node y) {

        stats.right++;

        Node x = y.left;
        Node t2 = x.right;

        x.right = y;
        y.left = t2;

        y.height =
                Math.max(height(y.left), height(y.right)) + 1;

        x.height =
                Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    private Node rotateLeft(Node x) {

        stats.left++;

        Node y = x.right;
        Node t2 = y.left;

        y.left = x;
        x.right = t2;

        x.height =
                Math.max(height(x.left), height(x.right)) + 1;

        y.height =
                Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    public void insert(double key, Resultado r) {

        root = insert(root, key, r);
    }

    private Node insert(Node node, double key, Resultado r) {

        if (node == null) {
            return new Node(key, r);
        }

        if (key < node.key) {

            node.left = insert(node.left, key, r);

        } else if (key > node.key) {

            node.right = insert(node.right, key, r);

        } else {

            node.resultados.add(r);
            return node;
        }

        node.height =
                1 + Math.max(height(node.left),
                height(node.right));

        int balance = balance(node);

        // Left Left
        if (balance > 1 && key < node.left.key) {
            return rotateRight(node);
        }

        // Right Right
        if (balance < -1 && key > node.right.key) {
            return rotateLeft(node);
        }

        // Left Right
        if (balance > 1 && key > node.left.key) {

            stats.doubleRight++;

            node.left = rotateLeft(node.left);

            return rotateRight(node);
        }

        // Right Left
        if (balance < -1 && key < node.right.key) {

            stats.doubleLeft++;

            node.right = rotateRight(node.right);

            return rotateLeft(node);
        }

        return node;
    }

    public void printAboveThreshold(
            double threshold,
            StringBuilder sb
    ) {

        printAboveThreshold(root, threshold, sb);
    }

    private void printAboveThreshold(
            Node node,
            double threshold,
            StringBuilder sb
    ) {

        if (node == null) {
            return;
        }

        printAboveThreshold(node.right, threshold, sb);

        if (node.key >= threshold) {

            for (Resultado r : node.resultados) {

                sb.append(r)
                        .append("\n");
            }
        }

        printAboveThreshold(node.left, threshold, sb);
    }

    public void reverseOrder(ArrayList<Resultado> lista) {

        reverseOrder(root, lista);
    }

    private void reverseOrder(
            Node node,
            ArrayList<Resultado> lista
    ) {

        if (node == null) {
            return;
        }

        reverseOrder(node.right, lista);

        lista.addAll(node.resultados);

        reverseOrder(node.left, lista);
    }
}