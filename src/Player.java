public enum Player {
    NONE('_'),
    PLAYER1('1'),
    PLAYER2('2');

    private final char value;

    // Constructor to initialize custom values
    Player(char value) {
        this.value = value;
    }

    // Method to access custom value
    public char getValue() {
        return value;
    }
}

