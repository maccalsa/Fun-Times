package funtimes.henry.groceries;

import funtimes.henry.groceries.data.Database;

public class Main {

    public static void main(String[] args) {
        Database database = new Database();
        new Console(database).run();
    }
}
