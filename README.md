# ENSF409 Final Project, W2021, Group 74

## Group members:  

> ### Beau McCartney
> ### Apostolos Scondriannis
> ### Quentin Jennings
> ### Jacob Lansang

## Video presentation:

.mp4 file at https://drive.google.com/file/d/1QV1uTl8IJdxF5Pdo7RQfIA2rg9ob5FGb/view?usp=sharing
## What's included
- `uml.pdf` - uml diagram

- `src` - package edu/ucalgary/ensf409 containing our source code.

- `src/lib` -  folder containing all the needed dependencies (junit and mysql connector). Our unit tests 
are in the same package as the source code.

- `vid` - folder containing our video presentation.

## Instructions for running the program: 

Note: these instructions are verified to work in a *windows command prompt only* - other terminals/machines **may require slightly adjusted commands**. Additionally, the database must be intialized and accesible to the program. 

1. `cd` to the `src` folder in your terminal.

2. Run the command `javac -cp .;lib/mysql-connector-java-8.0.23.jar;. edu/ucalgary/ensf409/Program.java`

3. Run the command `java -cp .;lib/mysql-connector-java-8.0.23.jar;. edu/ucalgary/ensf409/Program`.

4. Input the username, password, and url to match those of your inventory database.

## Instructions for running unit tests:

Note: similar to above, these instructions are verified to work in a *windows command prompt only* - other terminals/machines **may require slightly adjusted commands**. Additionally, the database must be intialized and accesible to the program.

1. Change the username, password, and url to match those of your inventory database.

2. `cd` to the `src` folder in your terminal.

3. Run the command `javac -cp .;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar;mysql-connector-java-8.0.23.jar;. edu/ucalgary/ensf409/UnitTest.java`

4. Run the command `java -cp .;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar;mysql-connector-java-8.0.23.jar;. org.junit.runner.JUnitCore edu.ucalgary.ensf409.UnitTest`
