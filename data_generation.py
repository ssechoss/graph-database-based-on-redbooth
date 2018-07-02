from faker import Factory
import random,sys
#Constants that to manage number of records generated
PROJECT_PER_MEM = 4 # One member is in at most 2 projects.
NUM_PROJECT = 100 # number of total projects
NUM_TASK = 100 # maximum task number in one project
NUM_MEM = 1200# number of total members
NUM_FILE = 100 # maximum file number in one project
MAX_FILE = sys.maxsize # maximum file size
MAX_MEM_ONE_TASK = 10
MIN_AGE = 20
MAX_AGE = 55
mems = [] # save members information to a list

fake = Factory.create()
#check duplicate of the indexes of project and member
check_duplicate = set()

#output files 
project_file = open('/Users/censhen/Desktop/data/project_file.txt','w')
task_file = open('/Users/censhen/Desktop/data/task_file.txt','w')
members_file = open('/Users/censhen/Desktop/data/members_file.txt','w')
files = open('/Users/censhen/Desktop/data/files.txt','w')
mem_in_pro = open('/Users/censhen/Desktop/data/mem_in_pro.txt','w')
task_on_pro = open('/Users/censhen/Desktop/data/task_on_pro.txt','w')
mem_assign_task = open('/Users/censhen/Desktop/data/mem_assign_task.txt','w')
file_attach_to_project = open('/Users/censhen/Desktop/data/file_attach_to_project.txt','w')
mem_know_mem = open('/Users/censhen/Desktop/data/mem_know_mem.txt','w')

class Members(object):
    def __init__(self, email, mname, age):
        self.email = email
        self.mname = mname
        self.age = age
    def __str__(self):
        return self.email + '\t' + self.mname + '\t' +str(self.age)


class Task(object):
    def __init__(self, tname, content, due_time):
        self.tname = tname
        self.content = content
        self.due_time = due_time
    def __str__(self):
        return str(self.tname) + '\t' + str(self.content) + '\t' + str(self.due_time)

class Project(object):
    def __init__(self, pname,start_date):
        self.pname = pname
        self.start_date = start_date
    def __str__(self):
        return self.pname + '\t' + str(self.start_date)
    
class File(object):
    def __init__(self, fname, fsize):
        self.fname = fname
        self.fsize = fsize
    def __str__(self):
        return str(self.fname) + '\t' + str(self.fsize)
    

    
class Mem_in_pro(object):
    def __init__(self, mem_email, project_name):
        self.mem_email = mem_email
        self.project_name = project_name
    def __str__(self):
        return self.mem_email + '\t' + "IN" + '\t' + self.project_name

class Task_on_pro(object):
    def __init__(self, task_name, project_name):
        self.task_name = task_name
        self.project_name = project_name
    def __str__(self):
        return self.task_name + '\t' + "ON" + '\t' + self.project_name
    
class File_attached_pro(object):
    def __init__(self, file_name, project_name):
        self.file_name = file_name
        self.project_name = project_name
    def __str__(self):
        return self.file_name + '\t' + "ATTACHED" + '\t' + self.project_name
    
class Task_assign_mem(object):
    def __init__(self,task_name, mem_email):
        self.mem_email = mem_email
        self.task_name = task_name
    def __str__(self):
        return self.task_name + '\t' + "ASSIGNED" + '\t' + self.mem_email
class Mem_know_mem(object):
    def __init__(self,m1_email, m2_email):
        self.m1_email = m1_email;
        self.m2_email = m2_email;
    def __str__(self):
        return self.m1_email + '\t' + "KNOWS" + '\t' + self.m2_email
    
        
    
def generate_members(NUM_MEM):
    count = 0
    
    
    while count < NUM_MEM:
        new_email = fake.email()
    
        # generate members randomly
        # save member information to file.
        if new_email not in check_duplicate:
            one_mem = []
            new_name = fake.name()
            new_age = random.randint(MIN_AGE,MAX_AGE)
            
            check_duplicate.add(new_email)
            new_mem = Members(new_email, new_name, new_age) 
            members_file.write(str(new_mem) + '\n') 
  
            one_mem.append(new_email)
            one_mem.append(new_name)
            one_mem.append(new_age)
            mems.append(one_mem)
            
            count += 1
    members_file.close()   
            
def generate_project(NUM_PROJECT):
    count = 0
    checker = set() # check duplicate file name and task name 
    generate_members(NUM_MEM)
    while count < NUM_PROJECT:
        new_pro = Project(fake.pystr(min_chars= 5, max_chars = 30),fake.date())
        if new_pro.pname not in check_duplicate:
            project_file.write(str(new_pro) + '\n')
            check_duplicate.add(new_pro.pname)
            count += 1
            mem_task = []
            check_ran = set()# eliminate same member generated
            
            #randomly select members to one project ane create relations
            for i in range(int(2*NUM_MEM/NUM_PROJECT)):
                
                index = random.randint(0,NUM_MEM-1)    
                if index not in check_ran:
                    mem_task.append(mems[index])
                    new_relation = Mem_in_pro(mems[index][0],new_pro.pname)
                    mem_in_pro.write(str(new_relation) + '\n')
                    check_ran.add(index)
                
            count_f = 0
            count_t = 0
        
            while count_f < random.randint(1,NUM_FILE):
                #generate files attached to the project
                new_file = File(fake.file_name(),random.randint(0,MAX_FILE))
                if new_file.fname not in checker:
                    files.write(str(new_file) + '\n')
                    #generate relationship between project and file
                    new_relation = File_attached_pro(new_file.fname, new_pro.pname)
                    file_attach_to_project.write(str(new_relation) + '\n')
                    checker.add(new_file.fname)
                    count_f += 1
            
            while count_t < random.randint(1,NUM_TASK):
                #generate tasks on the project
                new_task = Task(fake.text(max_nb_chars=20),fake.sentences(nb = 10),fake.date())
                if new_task.tname not in checker:
                    task_file.write(str(new_task) + '\n' + '\n')
                    #generate ralationship between task and project
                    new_relation = Task_on_pro(new_task.tname, new_pro.pname)
                    task_on_pro.write(str(new_relation) + '\n')
                    checker.add(new_task.tname)
                    count_t += 1
                    #generate relationship between task and member in one project
                    #randomly choose members assign to task
                    count_mem = random.randint(1,MAX_MEM_ONE_TASK)                   
                    for i in range(count_mem):
                        index =random.randint(0,len(mem_task)-1)
                        relation_mem_task = Task_assign_mem(new_task.tname,mem_task[index][0])
                        mem_assign_task.write(str(relation_mem_task) + '\n')
            #check_mem = set()
            for each in mem_task:
                for j in mem_task:
                    if each[0] != j[0]:
                        new_re = Mem_know_mem(each[0],j[0])
                        mem_know_mem.write(str(new_re) + '\n')
    project_file.close()
    task_file.close()
    files.close()
    mem_in_pro.close()
    mem_know_mem.close()
    mem_assign_task.close()
    task_on_pro.close()
    file_attach_to_project.close()
    
            
                
       

generate_project(NUM_PROJECT)


  
            
        
    
    
