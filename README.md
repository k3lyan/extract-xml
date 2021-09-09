# forecast1
 
## Requirements
 As a business owner, I want to be able to read in a XML file through command line that contains data about my suppliers so that I can query it.
 
 
## Short Description
A MVC architecture has been used to seperate the different layers of the application. The user can only put 2 arguments in the command line: the path to the XML file to be extracted and the elapsed time (as an convertible integer value only). The class Validator will handle all these prerequisites. If the arguments are not compatible, the application will automatically exit with the list of the errors responsible for this failure.    

To be able to print the list of the errors I made use of ValidatedNel a pre-build [Validated type](https://typelevel.org/cats/datatypes/validated.html) from [cats](https://typelevel.org/cats/) that uses NonEmptyList as its error type. I handled the potential errors with ValidatedNel (from wrong arguments to xml extraction) and isolated the side effects using [IO data type](https://typelevel.org/cats-effect/docs/2.x/datatypes/io)  from [cats-effect](https://typelevel.org/cats-effect/). This is not necessarily a requirement for our use case but I wanted to make this application as pure as possible, following the functionnal programming paradigms.   

The architecture of the app has been made this way to enable future development easily: depending on the XML to extract new Models data can be implemented and new kind of load can be added in our Validator. Also the commands that can be used by the user can be easily extended.

Once the CLI has started the user can choose between 5 options summarized in the help command:
* r                : print raw data extracted from the XML    
* e                : print data calculated with elapsed time T
* j                : print json formatted body for REST API
* h                : show this help text
* q                : quit


## Model
The model layers presents 3 classes:
1. SupplierInfo: gathering the data for one supplier
2. Suppliers: gathering the list of all extracted SupplierInfo
3. Forecast: gathering the Suppliers and the elapsedTime entered by the user

## Prerequesites
The program has been implemented using the following versions:
* sbt 1.5.5
* Java 11.0.12
* Scala 2.13.6.

## Tests
All the potential errors have been tested through the ValidatorSPec test class.
To run the tests, run the command ```sbt test``` from the root directory of this program.

## How to use
1. Get the absolute path where is located your XML file
2. From the root directory of the project, run the following command: ```sbt "run xml_path elapsed_time"```  

where, xml_path is replace by your xml absolute file path and the elapsed time by its targeted value> 

Cautious! The "" are necessary in the former command.

As a running example, some text files have been placed in the directory src/main/resources/, you can run the program for this directory by running ``` sbt "run /home/my_user/Desktop/suppliers.xml 13"```.   

You can also run a wrong command and see how the errors are handled in the console, as an exaample: ``` sbt "run /home/my_user/Desktop/suppliers t 13"```.   



