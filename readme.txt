Database System: assignment 2
Name: Cen Shen
Operating System: macOS 10.13.4
Data generating by python 3.6 (using Anaconda)
Database created and queries by Java : Netbeans

DATA GENERATING:

1. In total, I generate 4 labels and 5 relationships and save each part in one .txt file. The four labels are project, members, task and file. The five relations are members in project, task on project, file attached to project and task assigned to members, member knows member.
  
2. Member's email, project name, task name, file name have no duplicates.
Members can in multiple projects, and they are randomly chose to projects. Each member is randomly assigned tasks from randomly projects. 

3. Difference between assignment 1 and 2:
Comparing with assignment 1, I get rid of one label called major and relatively relationships as I think it is not the core function of this database. And I add some attributes to each label and relationship of member knows member to the database to traverse.

4. I totally create 1200 members and 100 projects. In each project, there are about 24 members in and up to 100 tasks and up to 100 files attached. So my node number is up to 10,000 level. Members in each project know others. And randomly chose to be assigned tasks in the project. Multiple members in the same project can be assigned the same task. So my relation level is up to 100,000 level.

Database generated by java connected with Neo4j:

1. Read nine of the .txt files that generated by python and import all the data.

2. Create nodes and relationships using the data.

3. Create 5 query functions: 
	getAllPros(): get randomly one member that in all the projects.
        getAllTask(): get all tasks in a randomly project
        getAllFile(): get all files in a randomly project
        getDFSKnows(): traverse one randomly member that knows other member in limited depth by DFS
        getBFSKnows(): traverse one randomly member that knows other member in limited depth by BFS

4. Runtime: 
	Average of it is above three minutes. As each time I randomly choose node, so the runtime sometimes maybe less.
	(Pay attention: if the randomly member chosen to traverse not in any of the project, then he knows nobody, then it will be really fast.)

5. Reference:
	neo4j tutorial:
		https://neo4j.com/docs/java-reference/3.3/tutorial-traversal/#tutorial-traversal-java-api
	neo4j embedded example:
		https://github.com/neo4j/neo4j-documentation/blob/3.3/embedded-examples/src/main/java/org/neo4j/examples/EmbeddedNeo4j.java

 