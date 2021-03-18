# Mercadolibre - Challenge
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

###**POST Method (mutant)**
Consume using Postman for example

*   **POST** -> `ec2-3-20-203-95.us-east-2.compute.amazonaws.com:8080/mutant/`

*   The body format is based on the requirements specified in the document received by 
email.

*   You must send as a body with JSON structure like this: 

`{
"dna":["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
} `

It will return one of these possible status:

Http Status | 200 | 403 |
--- | --- | --- |
isMutant | True | False | 



###**GET Method (stats)**
Consume using Postman for example

*   **GET** - > `ec2-3-20-203-95.us-east-2.compute.amazonaws.com:8080/stats`

It will return a json element like this: 
`{
"count_mutant_dna": 2,
"count_human_dna": 2,
"ratio": 1.0
}`


## Swagger Section
You will be able to use `http://ec2-3-20-203-95.us-east-2.compute.amazonaws.com:8080/swagger-ui.html#/`
in order to check the different endpoints in the mutant-controller, there you will add 
the body and try out the endpoint.


###### Created By: Santiago Collazos Barrera - Java Developer - 2021


