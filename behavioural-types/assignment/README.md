java -jar checker-framework-3.28.0/checker/dist/checker.jar -classpath jatyc.jar -processor jatyc.JavaTypestateChecker file-server/*.java

javac *.java -classpath ../jatyc.jar

java FileServer
java FileClient

te deja leer byte por byte O por línea. si arrancaste a leer byte por byte, así hasta el final de la línea.