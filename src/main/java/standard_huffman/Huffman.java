package standard_huffman;
import standard_huffman.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileInputStream;

@SuppressWarnings("ALL")
public class Huffman {
    private Node root;
    private final String text;
    private Map<Character, Integer> charFrequencies;
    private Map<Character, String> huffmanCodes;

    public Huffman(String fileName) {
        this.text = readOriginalFile(fileName);
        charFrequencies = new HashMap<>();
        huffmanCodes = new HashMap<>();
        calcFrequencies();
    }

    private void calcFrequencies() {
        for (char character : text.toCharArray()) {
            Integer freq = charFrequencies.get(character);
            charFrequencies.put(character, freq != null ? freq + 1 : 1);
        }
    }


    public void compress() {
        // Create priority queue for tree to get the smallest two pairs
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(Node::getFrequency));
        // Create all nodes as leaves first with their frequencies
        charFrequencies.forEach((character, frequency) ->
                pq.add(new Leaf(character, frequency))
        );
        // while loop for combining each smallest pair and putting them in one Node
        while (pq.size() > 1) {
            Node right = pq.poll();
            pq.add(new Node(pq.poll(), right));
        }
        // Finally, the only last node in the queue will be the root (the total number of frequencies)
        this.root = pq.poll();
        generateHuffmanCodes(root, "");
        String compresed = getEncodedText();
        System.out.println(compresed);

        try {
            FileOutputStream compressedOutput = new FileOutputStream("compressed.bin");
            DataOutputStream dataOutputStream = new DataOutputStream(compressedOutput);

            // Write the size
            dataOutputStream.writeByte(charFrequencies.size());

            // Write the frequencies of each character
            for (char c : charFrequencies.keySet()) {
                dataOutputStream.writeByte(c);
                dataOutputStream.writeByte(charFrequencies.get(c));
            }

            // Write the compressed size
            int bits = compresed.length();
            dataOutputStream.writeByte(bits);

            // Write the Huffman codes to the compressed file
            int i = 0;
            while (i < compresed.length()) {
                int number = 0;
                for (int j = 0; j < 8; j++) {
                    number <<= 1;
                    if (i < compresed.length() && compresed.charAt(i) == '1') {
                        number |= 1;
                    }
                    i++;
                }
                dataOutputStream.write(number);
            }

            dataOutputStream.close();

            System.out.println("Compressed done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void decompress(String fileName) {
        try {
            FileOutputStream out = new FileOutputStream("decompressed.txt");

            String compressedBytes = readCompressedFile(fileName);

            ArrayList<String> encodedTxt = new ArrayList<>();

            PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(Node::getFrequency));

            // Create all nodes as leaves first with their frequencies
            charFrequencies.forEach((character, frequency) ->
                    pq.add(new Leaf(character, frequency))
            );
            // while loop for combining each smallest pair and putting them in one Node
            while (pq.size() > 1) {
                Node right = pq.poll();
                pq.add(new Node(pq.poll(), right));
            }
            // Finally, the only last node in the queue will be the root (the total number of frequencies)
            this.root = pq.poll();

            huffmanCodes = new HashMap<>();

            generateHuffmanCodes(root, "");

            HashMap<String, Character> revHuffmanCodes = new HashMap<>();
            for (char c : huffmanCodes.keySet()) {
                String value = huffmanCodes.get(c);
                revHuffmanCodes.put(value, c);
            }

            for (int i = 0; i < compressedBytes.length(); i++) {
                encodedTxt.add(String.valueOf(compressedBytes.charAt(i)));
            }

            StringBuilder decompressedText = new StringBuilder();
            StringBuilder pattern = new StringBuilder();

            for (int i = 0; i < encodedTxt.size(); i++) {
                pattern.append(encodedTxt.get(i));
                Character ch = revHuffmanCodes.get(pattern.toString());
                if (ch != null) {
                    decompressedText.append(ch);
                    System.out.println(pattern + " " + ch);
                    pattern = new StringBuilder();
                }
            }
            System.out.println(decompressedText.toString());
            out.write(decompressedText.toString().getBytes());

            out.close();
            System.out.println("Decompressed done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateHuffmanCodes(Node node, String code) {
        if (node instanceof Leaf) {
            huffmanCodes.put(((Leaf) node).getCharacter(), code);
            return;
        }
        generateHuffmanCodes(node.getLeftNode(), code.concat("0"));
        generateHuffmanCodes(node.getRightNode(), code.concat("1"));
    }

    private String getEncodedText() {
        String str = "";
        for (char character : text.toCharArray()) {
            str += huffmanCodes.get(character);
        }
        return str;
    }

    private String readOriginalFile(String fileName) {
        String txt = "";
        try {
            File file = new File(fileName);
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                txt += sc.nextLine();
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        return txt;
    }

    private String readCompressedFile(String fileName) {
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            int size = fileInputStream.read();
            for (int i = 0; i < size; i++) {
                char c = (char) fileInputStream.read();
                int frequency = fileInputStream.read();
                charFrequencies.put(c, frequency);
            }
            int bits = fileInputStream.read();
            String data = "";
            int i = 0;
            while (true) {
                int number = fileInputStream.read();
                if (number == -1) {
                    break;
                }
                for (int j = 0; j < 8; j++) {
                    if (i == bits) {
                        break;
                    }
                    if ((number & 128) == 128) {
                        data += "1";
                    } else {
                        data += "0";
                    }
                    number <<= 1;
                    i++;
                }
            }
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}