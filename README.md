# plc-jott-14
Create the project structure:
src - the top level folder of the project
    src/testers - tester files, provided and not updated.
    src/provided - provided files, including the updated JottTokenizer.java and JottParser.java files.
    src/parser - parser files, including all nodes and SyntaxException.java files.

To compile (in "provided" folder):
javac JottTokenizer.java Token.java TokenType.java JottTree.java JottParser.java ../testers/JottTokenizerTester.java ../parser/*.java

To run (in working directory that has test cases):
java src.testers.JottTokenizerTester

java src.testers.JottParserTester
