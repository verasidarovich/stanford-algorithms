import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class TaskLauncher {

    private static HashMap<Integer, Node> initialGraph = new HashMap();

    private static ArrayList<Node> nodes;

    private static Node biggestNode;

    public static void main(String... args) {
        scanFile();
        int greatestElement = initialGraph.size();
        biggestNode = initialGraph.get(greatestElement);
        exploreNode(biggestNode);
    }

    private static void scanFile() {
        File file = new File("c:\\example.txt");

        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String row = scanner.nextLine();
                fillArray(row, initialGraph);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }

        nodes =  new ArrayList(initialGraph.values());
    }

    private static void fillArray(String row, HashMap<Integer, Node> initialGraph) {
        String[] rowArray = row.split(" ", row.length() - 1);
        Integer headEdgeValue = Integer.parseInt(rowArray[0]);
        Integer tailEdgeValue = Integer.parseInt(rowArray[1]);

        Node headNode = getNodeByValue(headEdgeValue);
        Node tailNode = getNodeByValue(tailEdgeValue);

        headNode.getOngoingNodes().add(tailNode);
    }

    private static Node getNodeByValue(Integer value) {
        Node node = initialGraph.get(value);

        if (node == null) {
            node = new Node(value);
            initialGraph.put(value, node);
        }
        return node;
    }

    private static void exploreNode(Node node) {
    //    System.out.println("Current Node: " + node);
        if (!node.isExploredNode()) {
            node.setExploredNode(true);
            List<Node> onGoingNodes = node.getOngoingNodes();
            for (Node onGoingNode : onGoingNodes) {
                if (!onGoingNode.isExploredNode()) {
                    exploreNode(onGoingNode);
                }
            }

            if (node.equals(biggestNode)) {
                findNextBiggestNode();
                exploreNode(biggestNode);
            }

            System.out.println("Explored Node: " + node);
        }
    }

    private static void findNextBiggestNode() {
        Integer nodeValue = biggestNode.getValue();
        System.out.println("Node Value = " + nodeValue);

        Node newBiggest = initialGraph.get(nodeValue);

        System.out.println("New Biggest = " + newBiggest);

        while (newBiggest.isExploredNode()) {
            nodeValue--;
            newBiggest = initialGraph.get(nodeValue);
        }
        biggestNode = newBiggest;
    }
}
