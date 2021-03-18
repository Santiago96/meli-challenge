# meli-challenge
Application developed in Spring Boot to resolve the mutants challenge.
## How to run application
1. Clone Repository using `git clone https://github.com/Santiago96/meli-challenge.git`
   
2. Import in the IDE of your preference.
   
3. Download sources to get the libraries needed.
   
4. Set application's profile
    1. **dev** profile
        1. It will use a memory database where will store the records just for
        runtime execution
        2. You could consult the data in http://localhost:8080/h2-console/login.jsp
    2. **int** profile
       1. It will use a local MySQL database, considering this, you must create
       a database with ´sequence´ name in your MySQL engine.
    3. **default** profile
        1. It will use the database located in AWS
5. Once the profile has been set, run the application by running with a spring 
configuration or executing `MutantsApplication.java`.
   
6. The IDE will launch the application in the port 8080, then you will have 
**MutantsApplication running !!! Congrats.**


## How to run tests with coverage
These steps are valid in IntelliJ
1. Right click over the project, find and select `More Run/Debug` option and then
select `Run 'All Test' with Coverage`
2. It will execute 16 tests
3. The current coverage displayed by IntelliJ is **`81%`** it decreases due to some 
test mark as an uncovered in domain objects but services and logic functions are covered.
## How to try out the application




###### Created By: Santiago Collazos Barrera - Java Developer - 2021


