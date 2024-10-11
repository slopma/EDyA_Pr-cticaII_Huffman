import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class HuffmanTreePanel extends JPanel {
    private HuffmanTree huffmanTree;
    private String input;
    private boolean isTreeVisible = false; // Para controlar si se debe mostrar el árbol

    public HuffmanTreePanel(HuffmanTree huffmanTree) {
        this.huffmanTree = huffmanTree;
        setPreferredSize(new Dimension(800, 600)); // Tamaño de panel ajustado
    }

    public void setInput(String input) {
        this.input = input;
        repaint(); // redibujar
    }

    public void showStandardTree() {
        isTreeVisible = true; // activar para ver el arbol
        repaint();
    }

    public void showCanonicalTree() {
        isTreeVisible = true;
        repaint();
    }

    public void showAnalysis(String type) {
        Map<Character, String> codes;
        StringBuilder analysis = new StringBuilder();

        if ("standard".equals(type)) {
            codes = huffmanTree.getHuffmanCodes();
            analysis.append("HUFFMAN estándar:\n");
        } else {
            codes = huffmanTree.generateCanonicalHuffmanCodes();
            analysis.append("HUFFMAN canónico:\n");
        }

        // para mostrar los codigos
        for (Map.Entry<Character, String> entry : codes.entrySet()) {
            analysis.append(entry.getKey()).append(" = ").append(entry.getValue()).append("\n");
        }

        // para codificar cadena original
        StringBuilder encodedString = new StringBuilder();
        for (char character : input.toCharArray()) {
            encodedString.append(codes.get(character));
        }

        analysis.append("\nC: ").append(encodedString.toString());
        JOptionPane.showMessageDialog(this, analysis.toString());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isTreeVisible && huffmanTree.getRoot() != null) {
            drawTree(g, huffmanTree.getRoot(), getWidth() / 2, 30, 150); // Aumenté el xOffset para más espacio
        }
    }

    private void drawTree(Graphics g, HuffmanTree.Node node, int x, int y, int xOffset) {
        if (node != null) {

            if (node.character != '\0') {
                g.setColor(new Color(0, 130, 0)); // caracteres
            } else {
                g.setColor(new Color(0, 100, 0)); // raiz
            }

            // nodo con letra y frecuencia
            g.drawRect(x - 20, y, 120, 30);
            String text = node.character == '\0' ? "*" : node.character + " (" + node.frequency + ")";
            g.drawString(text, x - 15, y + 20); // posición del texto para que quepa bien

            g.setColor(Color.BLACK);

            if (node.left != null) {
                g.drawLine(x, y + 30, x - xOffset, y + 60);
                drawTree(g, node.left, x - xOffset, y + 60, xOffset / 2); // subárbol izq
            }
            if (node.right != null) {
                g.drawLine(x, y + 30, x + xOffset, y + 60);
                drawTree(g, node.right, x + xOffset, y + 60, xOffset / 2); // subárbol der
            }
        }
    }
}