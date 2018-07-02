package assignment2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Uniqueness;
import org.neo4j.io.fs.FileUtils;

public class Database {

    // save the generated files' path
    public static final String membersFile = "/Users/censhen/Desktop/data/members_file.txt";
    public static final String projectFile = "/Users/censhen/Desktop/data/project_file.txt";
    public static final String taskFile = "/Users/censhen/Desktop/data/task_file.txt";
    public static final String files = "/Users/censhen/Desktop/data/files.txt";
    public static final String mem_in_pro = "/Users/censhen/Desktop/data/mem_in_pro.txt";
    public static final String task_on_pro = "/Users/censhen/Desktop/data/task_on_pro.txt";
    public static final String mem_assign_task = "/Users/censhen/Desktop/data/mem_assign_task.txt";
    public static final String file_attach_to_project = "/Users/censhen/Desktop/data/file_attach_to_project.txt";
    public static final String mem_know_mems = "/Users/censhen/Desktop/data/mem_know_mem.txt";

    // save the generated files' data information
    public String member;
    public String file;
    public String project;
    public String task;
    public String re_mem_in_pro;
    public String re_task_on_pro;
    public String re_mem_assign_task;
    public String re_file_attach_pro;
    public String re_mem_know_mems;
    public File databaseFile = new File("/Users/censhen/Desktop/data/databaseGraph.db");
    GraphDatabaseService db;
    Map<String, Node> map ;

    private enum RelTypes implements RelationshipType {
        IN,
        ON,
        ATTACHED,
        ASSIGNED,
        KNOWS
    }

    private enum LabelTypes implements Label {
        Members,
        Project,
        Task,
        File
    }
            
    //read file that saves members information and get all of them
    private String getMembers() throws FileNotFoundException, IOException {
        try (
                BufferedReader br = new BufferedReader(new FileReader(membersFile))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            return sb.toString();
        }
    }

    // read file that saves files information and get all of them
    private String getFile() throws FileNotFoundException, IOException {
        try (
                BufferedReader br = new BufferedReader(new FileReader(files))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            return sb.toString();
        }
    }

    // read file that saves project information and get all of them
    private String getProject() throws FileNotFoundException, IOException {
        try (
                BufferedReader br = new BufferedReader(new FileReader(projectFile))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            return sb.toString();
        }
    }

    //read file that saves task information and get all of them
    private String getTask() throws FileNotFoundException, IOException {
        try (
                BufferedReader br = new BufferedReader(new FileReader(taskFile))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            return sb.toString();
        }
    }

    // read file that saves all the relations: Members in Project
    private String getMemInPro() throws FileNotFoundException, IOException {
        try (
                BufferedReader br = new BufferedReader(new FileReader(mem_in_pro))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            return sb.toString();
        }
    }

    // read file that save all the relations: task on project
    private String getTaskOnPro() throws FileNotFoundException, IOException {
        try (
                BufferedReader br = new BufferedReader(new FileReader(task_on_pro))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            return sb.toString();
        }
    }

    // read file that save all the relations: members assign task
    private String getMemAssignTask() throws FileNotFoundException, IOException {
        try (
                BufferedReader br = new BufferedReader(new FileReader(mem_assign_task))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            return sb.toString();
        }
    }

    //read file that save all the relations: file attach to project
    private String getFileAttachPro() throws FileNotFoundException, IOException {
        try (
                BufferedReader br = new BufferedReader(new FileReader(file_attach_to_project))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            return sb.toString();
        }
    }
    
    //read file that save all the relations: member knows member
    private String getMemKnowMem() throws FileNotFoundException, IOException {
        try (
                BufferedReader br = new BufferedReader(new FileReader(mem_know_mems))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            return sb.toString();
        }
    }

    // build dababase model
    public void generateDatabase() throws IOException {
        FileUtils.deleteRecursively( databaseFile );
        
        member = getMembers();
        file = getFile();
        project = getProject();
        task = getTask();
        re_mem_in_pro = getMemInPro();
        re_task_on_pro = getTaskOnPro();
        re_mem_assign_task = getMemAssignTask();
        re_file_attach_pro = getFileAttachPro();
        re_mem_know_mems = getMemKnowMem();
        
        map = new HashMap<>();
        
        
        GraphDatabaseFactory dbFactory = new GraphDatabaseFactory();
        //databaseFile = new File("/Users/censhen/Desktop/data/databaseGraph");
        db = dbFactory.newEmbeddedDatabase(databaseFile);
        registerShutdownHook(db);
        try (Transaction tx = db.beginTx()) {
            //build node members
            String[] mems = member.split("\n");
            int count = 0;
            for (String each : mems) {
                String[] splits = each.split("\t");
                Node mem = db.createNode(LabelTypes.Members);
                mem.setProperty("email", splits[0]);
                mem.setProperty("mname", splits[1]);
                mem.setProperty("age", splits[2]);
                map.put(splits[0], mem);
                //count++;
                //System.out.println(count);
                //System.out.println(mem);
            }
            //build node files
            String[] fs = file.split("\n");
            for (String each : fs) {
                String[] splits = each.split("\t");
                Node f = db.createNode(LabelTypes.File);
                f.setProperty("fname", splits[0]);
                f.setProperty("fsize", splits[1]);
                map.put(splits[0], f);
                //System.out.println(f);
            }
            //build node projects
            String[] pros = project.split("\n");
            for (String each : pros) {
                String[] splits = each.split("\t");
                Node pro = db.createNode(LabelTypes.Project);
                pro.setProperty("pname", splits[0]);
                pro.setProperty("star_date", splits[1]);
                map.put(splits[0], pro);
                //System.out.println(pro);
                //System.out.println(pro.getAllProperties());
            }
            //build node task
            String[] ts = task.split("\n\n");
            for (String each : ts) {
                String[] splits = each.split("\t");
                Node t = db.createNode(LabelTypes.Task);
                t.setProperty("tname", splits[0]);
                t.setProperty("content", splits[1]);
                t.setProperty("due_time", splits[2]);
                map.put(splits[0], t);
                //System.out.println(splits[0]);
                //System.out.println(splits[2]);
            }

            //build relation member in project
            String[] reMemInPros = re_mem_in_pro.split("\n");
            for (String each : reMemInPros) {
                String[] splits = each.split("\t");
                Relationship r = map.get(splits[0]).createRelationshipTo(map.get(splits[2]), RelTypes.IN);
            }
            //build relation task on project 
            String[] reTaskOnPro = re_task_on_pro.split("\n");
            for (String each : reTaskOnPro) {
                String[] splits = each.split("\t");
                Relationship r = map.get(splits[0]).createRelationshipTo(map.get(splits[2]), RelTypes.ON);
            }
            //build relation task assign to member
            String[] reTaskAssignedMem = re_mem_assign_task.split("\n");
            for (String each : reTaskAssignedMem) {
                String[] splits = each.split("\t");
               // System.out.println(map.get(splits[0]));
                Relationship r = map.get(splits[0]).createRelationshipTo(map.get(splits[2]), RelTypes.ASSIGNED);
            }
            //build relation file attach to project
            String[] reFileAttachPro = re_file_attach_pro.split("\n");
            for (String each : reFileAttachPro) {
                String[] splits = each.split("\t");
               // System.out.println(map.get(splits[0]));
                Relationship r = map.get(splits[0]).createRelationshipTo(map.get(splits[2]), RelTypes.ATTACHED);
            }
            //build relation member knows member
            String[] reMemKnowMems = re_mem_know_mems.split("\n");
            for (String each : reMemKnowMems) {
                String[] splits = each.split("\t");
                //System.out.println(map.get(splits[0]));
                //System.out.println(splits[2]);
                Relationship r = map.get(splits[0]).createRelationshipTo(map.get(splits[2]), RelTypes.KNOWS);
            }
            
            tx.success();

        }
    }

    public void getAllPros() {// randomly get one member and get all projects he is in.
        String[] mems = member.split("\n");
        Random rand = new Random();
        int index = rand.nextInt(mems.length);
        String  s = mems[index];
        String[] splits = s.split("\t");
        int count = 0;
        //Set<String> set = new HashSet<>();
        //System.out.println(splits[1]);
        System.out.println("---------------------------------------------");
        System.out.println("output all projects one member in. Member email: " +splits[0]);
        StringBuilder rows = new StringBuilder();
        try (Transaction ignored = db.beginTx();
             Result result = db.execute(
                     "MATCH (m:Members {email:'" + splits[0]+ "'})" 
                             +"MATCH (p:Project)"
                             +"MATCH(m)-[IN]->(p)"
                             + " RETURN m.mname,p.pname"
                     
             )) {
            while (result.hasNext()) {
                Map<String, Object> row = result.next();          
                rows.append("member name: ");
                rows.append(row.get("m.mname"));
                rows.append("\t");
                rows.append("project name:");
                rows.append(row.get("p.pname"));                
                rows.append("\n\n");        
                count++;
            }
        }
        if(rows.length() == 0){
            System.out.println(splits[0] + " not in any project.");
        }
        else {
            System.out.print(rows.toString());
            System.out.println(splits[0]+" in projects number: "+count);
        }
    }
    
    public void getAllTask(){// get all tasks in one project
        String[] pros = project.split("\n");
        Random rand = new Random();
        int index = rand.nextInt(pros.length);
        String  s = pros[index];
        String[] splits = s.split("\t");
        int count = 0;
        //Set<String> set = new HashSet<>();
        //System.out.println(splits[0]);
        System.out.println("---------------------------------------------");
        System.out.println("output all tasks in the project: " + splits[0]);
        System.out.println();
        StringBuilder rows = new StringBuilder();
        try (Transaction ignored = db.beginTx();
             Result result = db.execute(
                     "MATCH (p:Project {pname:'" + splits[0]+ "'})" 
                             +"MATCH (t:Task)"
                             +"MATCH (t)-[ON]->(p)"
                             + " RETURN p.pname,t.tname,t.due_time"
                     
             )) {
            while (result.hasNext()) {
                Map<String, Object> row = result.next();          
                rows.append("project name: ");
                rows.append(row.get("p.pname"));
                rows.append("\t");
                rows.append("task name: ");
                rows.append(row.get("t.tname"));  
                rows.append("\t");
                rows.append("due time: ");
                rows.append(row.get("t.due_time"));
                rows.append("\n\n");     
                count++;
            }
        }
        System.out.print(rows.toString());
        System.out.println(splits[0] + " has " + count + " tasks in total.");
        
    }
    
    public void getAllFile(){// get all files attached to a rondomly project
        String[] pros = project.split("\n");
        Random rand = new Random();
        int index = rand.nextInt(pros.length);
        String  s = pros[index];
     
        String[] splits = s.split("\t");
        int count = 0;
        //Set<String> set = new HashSet<>();
        System.out.println("---------------------------------------------");
        System.out.println("output all files in the project: " + splits[0]);
        System.out.println();
        StringBuilder rows = new StringBuilder();
        try (Transaction ignored = db.beginTx();
             Result result = db.execute(
                     "MATCH (p:Project {pname:'" + splits[0]+ "'})" 
                             +"MATCH (f:File)"
                             +"MATCH (f)-[ATTACHED]->(p)"
                             + " RETURN p.pname,f.fname"
                     
             )) {
            while (result.hasNext()) {
                Map<String, Object> row = result.next();          
                rows.append("project name: ");
                rows.append(row.get("p.pname"));
                rows.append("\t");
                rows.append("file name: ");
                rows.append(row.get("f.fname"));
                rows.append("\n\n");   
                count++;
            }
        }
     
        System.out.print(rows.toString());
        System.out.println(splits[0] + " has " + count + " files in total.");
        
    }
    
    public void getDFSKnows(){ // get a randoml member that and traverse by DFS
        String[] mems = member.split("\n");
        Random rand = new Random();
        int index1 = rand.nextInt(mems.length);
        String[] mem1 = mems[index1].split("\t");
        System.out.println("--------------------------------------------");
        
        try (Transaction tx= db.beginTx()) {
            String path = "";
            TraversalDescription tp = db.traversalDescription()
                            .depthFirst()
                            .relationships(RelTypes.KNOWS)
                            .uniqueness(Uniqueness.RELATIONSHIP_GLOBAL);                            
            for(Path each : tp.evaluator( Evaluators.toDepth( 8 ) )
                    .traverse(map.get(mem1[0]))){
                path += each + "\n";
            }
            System.out.println(path);
            tx.success();
        }    
    }
    public void getBFSKnows(){ // get a randoml member that and traverse by BFS
        String[] mems = member.split("\n");
        Random rand = new Random();
        int index1 = rand.nextInt(mems.length);
        String[] mem1 = mems[index1].split("\t");
        System.out.println("--------------------------------------------");
        
        try (Transaction tx= db.beginTx()) {
            String path = "";
            TraversalDescription tp = db.traversalDescription()
                            .breadthFirst()
                            .relationships(RelTypes.KNOWS)
                            .uniqueness(Uniqueness.RELATIONSHIP_GLOBAL);                            
            for(Path each : tp.evaluator( Evaluators.toDepth( 6 ) )
                    .traverse(map.get(mem1[0]))){
                path += each + "\n";
            }
            System.out.println(path);
            tx.success();
        }    
    }
    void shutDown()
    {
        System.out.println();
        System.out.println( "Shutting down database ..." );
        // START SNIPPET: shutdownServer
        db.shutdown();
        // EN SNIPPET: shutdownServer
    }

    // START SNIPPET: shutdownHook
    private static void registerShutdownHook( final GraphDatabaseService db )
    {
        // Registers a shutdown hook for the Neo4j instance so that it
        // shuts down nicely when the VM exits (even if you "Ctrl-C" the
        // running application).
        Runtime.getRuntime().addShutdownHook( new Thread()
        {
            @Override
            public void run()
            {
                db.shutdown();
            }
        } );
    }
    // END SNIPPET: shutdownHook   
}
