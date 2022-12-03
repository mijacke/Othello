package othello;

/**
 * Through the Main class we can run the game even outside BlueJ.
 * It contains one static main method (core method of the program
 * which calls all others, and it also can't return values). This
 * method can be invoked by Java Virtual Machine without instantiating
 * the class.
 */
public class OthelloStarter {
    public static void main(String[] args) {
        new Othello();
    }
}
