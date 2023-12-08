# plc-jott-14
Create the project structure:
src - the top level folder of the project
    Jott.java - main file for running the program.
    src/testers - tester files, provided and not updated.
    src/provided - provided files, including the updated JottTokenizer.java and JottParser.java files.
    src/parser - parser files, including all nodes and SyntaxException.java files.

To compile (in src directory):
javac Jott.java provided/*.java parser/*.java

To run (in src directory):
java Jott (input file) (output file) (language)