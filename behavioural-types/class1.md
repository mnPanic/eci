# intro

## cronograma

- hoy: make a case for type systems
- martes: type systems 101 y behavioural types. Ver las foundations
- miercoels: behavioural types para PLs. Panorama y detalles
- jueves: Java typestate checker
  - herramienta para el viernes, presentación de un tutorial
- friday: Lab session, an assignment
  - en java, pero puede ser otro
  - sistemas que queremos armar con behavioural types
  - java tiene una herramienta copada

## Qué motiva la investigación?

cómo podemos hacer que los programas funcionen bien?

- tests
  - dijkstra: tests pueden mostrar presencia pero no ausencia de bugs
- pero dar garantías más fuertes

- approach sistemático para evitar fallas catastróficas
  - evitar "anda ahora, prob está ok"

## Qué sabemos?

Qué hace un programa? -> spec (por ej. con una función matemática)

! mutability is dangerous

rust: by default las variables no son mutables

diferencia esencial: **crees** en algo o **demostrás** algo?

objetivo: demostrar matemáticamente que un programa es correcto

### ej factorial

la spec está incompleta, está para naturales pero no lo especifica. Entonces si la implementación usa int, cuando se pasa un negativo tiene recursión "infinita"

-> puede haber problemas en ambos lados, spec e implementación

hay herramientas para mostrar que un código cumple una spec, pero **no hay forma conocida** de ver que una spec sea buena para un programa.

### binary search

> casi todo binary search y merge sort están rotos
versión recursiva está OK, pero en iterativa hay un posible integer overflow:

    mid = (high + low) // 2

## métodos formales

dijsktra: tests muestran presencia y no ausencia de bugs!

observación clave para métodos formales:

- si debugging es remover bugs, programar es introducir bugs!

undesired program behavioiur

- functional incorrectness: el código no hace lo que debería
- memory violations, seg-faults, memory leaks (dangling pointers)
- data races: inconsistencies on shared state

    ejemplo clásico:

    ```
    x = 0
    y = 0

    y = x + 1 || y = x + 1
            y = ? // al menos 4  opciones
    ```

- blockages: deadlock, livelock, divergency (ej factorial con negativos)
- protocols not respected: operations should be invoked according to a prescribed order

**behavioural types**: método formal *lightweight*

### ejemplo protocolos (jedis)

solución: **defensive programming**. Chequear que estás en un estado en el que es correcto hacer algo.

Es una fiaca hacer defensive programming. El código se hace muy largo y te podés olvidar de ponerlo en algún lado.

## Make programs do better

En critical systems, ya se usan métodos formales. Porque hay cosas que **no** pueden fallar.

Ejs: avionics, health, space

producir el código es lento, tarda mucho, cuesta mucho.

Peero el boening 737 max.

### Primer approach

Turing: primer approach a program verification (no es sistemático, lo hizo a mano)

ideas:

- especificar el objetivo de forma rigurosa, precisa, formal.
- chequear la implementación es sound (correcta) y completa (no hacés más) con respecto a la spec

> me hace acordar a algo1, en donde chequeabas que cumpla y que no haya demás

turing award: como un premio nobel pero para la informática

### Verified software initiative

*we propose an ambitious and long-term research program toward the construction of error-free software systems*

- Hoare & Misra: we envision a world in which computer programs are always the most reliable component of any sustem or device that contains them.

kataOS: rust on top of sel4

- rust: compile-time memory and thread safety (no null deref, use after free, double free, no races)
  - "writing code is a pain"
  - las variables mutables son de un solo thread, y otros threads hacen burrow
- sel4: the world's most highly assured os kernel

## Program Verification

lo esencial de la realidad es el **modelo**. Una petri net es una forma útil de armar un modelo de un sistema concurrente.

pasa mucho en protocolos de consenso, probas que el protocolo es correcto en un lenguaje de especificación (modelo) pero no probás que el código implementa el modelo.
Pero al menos te deja corregir el razonamiento.

Tres approaches a program verification

1. **Deductive**: Program logics. Si esta línea de código se ejecuta, deducimos que algo pasa (en memoria por ejemplo). Razonamos en el código, inferimos el estado del sistema y tratamos de mostrar propiedades en el estado.

    Typical approach: Automatic or assisted theorem-proving

    Agda, coq, isabelle

    Cameleer (de ocaml) + why3 (SMT: satisfiaiblity modulo theory)

    en quien se confía?

    - engine de coq
    - en engine de smt de why3

    siempre hay un kernel en el que hay que confiar, hacemos una prueba pero delegamos en un core que se haya probado como correcto.

    Dafny: escribís código, probás que es correcto, y hacés output de código en algún lenguaje target (c) que está certificado (está probada la traducción)

    permite introducir *partes* de sistemas que estén demostradas como correctas.

    Hay otros proof assistants como KeY.

    Idea interesante: coq te da un proof tree, pero problema: qué falta?

2. **Dynamic**: runtime verification or model checking

    Log analysis, tests, monitoring, logical property checking in a model of the system.

    Suele no ser ni sound ni complete. No se corren todas las posibilidades (suelen ser infinitas). Pero igual es útil

    > las herramientas tienen ventajas y desventajas, hay que saber cuando usar cada una. No silver bullet, once you have a hammer you see everything as a nail.

3. **Static**: analyze source code, at compile-time (o bytecode, ASTs, etc.)

    Typical approach: type systems
    
    sound pero incomplete: se deciden propiedades indecidibles (teorema de Rice).

    No todo es decidible. Si un problema se traduce a una query a algun software, cómo hace el static analysis? sobre aproxima, si decidis un problema que no es decidible: cauteloso. En dynamic analysis no se puede, no tenés todas las ejecuciones.

    da falsos positivos: mi análisis detectó un problema pero en realidad no hay ninguno (molesto)

    hay veces que es unsound, por ej. java con generics (incluso type checked crashea)