package standard_huffman;

public class Leaf extends Node {
    private final char character;

    public char getCharacter() {
        return character;
    }

    public Leaf(char character, int frequency) {
        super(frequency);
        this.character = character;
    }
}
