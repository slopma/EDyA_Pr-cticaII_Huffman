import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        HuffmanTree huffmanTree = new HuffmanTree();
        HuffmanTreePanel huffmanTreePanel = new HuffmanTreePanel(huffmanTree);

        // jframe para el panel
        JFrame frame = new JFrame("Huffman Tree Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(huffmanTreePanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true); // pa que se vea la ventana

        String option;

        do {
            option = JOptionPane.showInputDialog(null, "1. Ingresar palabra\n2. Salir", "Menú", JOptionPane.QUESTION_MESSAGE);
            if ("1".equals(option)) {
                String input = JOptionPane.showInputDialog("Ingrese la palabra:");
                huffmanTree.buildTree(input); // hacer el arbol
                huffmanTreePanel.setInput(input); // redibujar 

                String treeType = JOptionPane.showInputDialog(null, "1. Ver árbol estándar\n2. Ver árbol canónico", "Seleccionar tipo de árbol", JOptionPane.QUESTION_MESSAGE);

                if ("1".equals(treeType)) {
                    huffmanTreePanel.showStandardTree();
                    huffmanTreePanel.showAnalysis("standard");
                } else if ("2".equals(treeType)) {
                    huffmanTreePanel.showCanonicalTree();
                    huffmanTreePanel.showAnalysis("canonical");
                } else {
                    JOptionPane.showMessageDialog(null, "Opción no válida. Por favor, seleccione 1 o 2.");
                }
            }
        } while (!"2".equals(option));

        System.exit(0);
    }
}
