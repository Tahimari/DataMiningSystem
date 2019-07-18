public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World");
        String input = "/Users/kamilmisiak/Desktop/Praca Dyplomowa/Poker-data/poker-hand-testing.data.txt";
        String output = "/Users/kamilmisiak/Desktop/Praca Dyplomowa/arff/PokerDataTest.arff";

        DataTextToArff pokerDataTextToArff = new DataTextToArff();
        try {
            pokerDataTextToArff.textToArff(input, output);
        } catch (Exception e){
            System.out.println(e);
        }
    }
}
