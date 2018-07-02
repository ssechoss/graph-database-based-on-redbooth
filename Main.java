
package assignment2;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Database database = new Database();
        database.generateDatabase();
        database.getAllPros();//get randomly one member that in all the projects.
        database.getAllTask();//get all tasks in a randomly project
        database.getAllFile();//get all files in a randomly project
        database.getDFSKnows();//traverse one randomly member that knows other member in limited depth by dfs
        database.getBFSKnows();//traverse one randomly member that knows other member in limited depth by bfs
        
    }
}
