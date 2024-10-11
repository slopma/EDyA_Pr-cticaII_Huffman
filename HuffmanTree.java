import java.util.*;

public class HuffmanTree {
    private Node root;

    public static class Node {
        int frequency;
        char character;
        Node left;
        Node right;

        Node(char character, int frequency) {
            this.character = character;
            this.frequency = frequency;
            this.left = null;
            this.right = null;
        }
    }

    public void buildTree(String input) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char character : input.toCharArray()) {
            frequencyMap.put(character, frequencyMap.getOrDefault(character, 0) + 1);
        }

        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(node -> node.frequency));
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            priorityQueue.add(new Node(entry.getKey(), entry.getValue()));
        }

        while (priorityQueue.size() > 1) {
            Node left = priorityQueue.poll();
            Node right = priorityQueue.poll();
            Node parent = new Node('\0', left.frequency + right.frequency);
            parent.left = left;
            parent.right = right;
            priorityQueue.add(parent);
        }

        root = priorityQueue.poll();
    }

    public void generateHuffmanCodes(Node node, StringBuilder prefix, Map<Character, String> huffmanCodes) {
        if (node != null) {
            if (node.left == null && node.right == null) {
                huffmanCodes.put(node.character, prefix.toString());
            }
            generateHuffmanCodes(node.left, prefix.append('0'), huffmanCodes);
            prefix.setLength(prefix.length() - 1);
            generateHuffmanCodes(node.right, prefix.append('1'), huffmanCodes);
            prefix.setLength(prefix.length() - 1);
        }
    }

    public Map<Character, String> getHuffmanCodes() {
        Map<Character, String> huffmanCodes = new HashMap<>();
        generateHuffmanCodes(root, new StringBuilder(), huffmanCodes);
        return huffmanCodes;
    }

    public Map<Character, String> generateCanonicalHuffmanCodes() {
        Map<Character, String> huffmanCodes = getHuffmanCodes();
        List<Map.Entry<Character, String>> entries = new ArrayList<>(huffmanCodes.entrySet());

        // ordenar por long
        entries.sort(Comparator.comparingInt(a -> a.getValue().length()));

        Map<Character, String> canonicalCodes = new LinkedHashMap<>();
        int currentCode = 0;
        int codeLength = 1;

        for (Map.Entry<Character, String> entry : entries) {
            while (currentCode < (1 << codeLength) - 1) {
                String code = Integer.toBinaryString(currentCode);
                while (code.length() < codeLength) {
                    code = "0" + code;
                }
                canonicalCodes.put(entry.getKey(), code);
                currentCode++;
                break;
            }
            if (currentCode >= (1 << codeLength) - 1) {
                codeLength++;
            }
        }

        return canonicalCodes;
    }

    public Node getRoot() {
        return root;
    }
}
