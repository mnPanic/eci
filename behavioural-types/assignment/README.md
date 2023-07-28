java -jar checker-framework-3.28.0/checker/dist/checker.jar -classpath jatyc.jar -processor jatyc.JavaTypestateChecker file-server/*.java

javac *.java -classpath ../jatyc.jar

java FileServer
java FileClient