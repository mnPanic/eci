# ECI 2023 N2 - Behavioural Types - Assignment

- Name: Manuel Panichelli
- L.U: 72/18
- Email: panicmanu@gmail.com

## Usage instructions

To run jatyc and compile the classes, from the root of the assignment do:

```bash
java -jar checker-framework-3.28.0/checker/dist/checker.jar -classpath jatyc.jar -processor jatyc.JavaTypestateChecker file-server/*.java
```

You should get no output.

To run the classes, go to the `file-server` directory and run the `FileServer` and `FileClient` classes in different terminals like so

```bash
java FileServer
java FileClient
java FileClient2
```
