public class Data {
    private String input = "";
    private int classValIndex = 0;

    public void setInputFromConsole() {
        this.input = IOHelper.readInput("Please insert data input path:");
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getInput() {
        return this.input;
    }

    public void setClassValIndexFromConsole() {
        this.classValIndex = IOHelper.tryParse(IOHelper.readInput("Please insert class value index:"));
    }

    public void setClassValIndex(Integer classValIndex) {
        this.classValIndex = classValIndex;
    }

    public Integer getClassValIndex() {
        return this.classValIndex;
    }
}
