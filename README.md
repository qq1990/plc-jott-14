# plc-jott-14
Create the project structure:
src - the top level folder of the project
    src/testers - tester files, provided and not updated.
    src/provided - provided files, including the updated JottTokenizer.java and JottParser.java files.
    src/parser - parser files, including all nodes and SyntaxException.java files.

To compile (in src directory):
javac RunTranslator.java

To run (in src directory) (the 0 argument denotes "translate to Jott"):
java RunTranslator [filename] 0