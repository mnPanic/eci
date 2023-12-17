# Class 1 - Introduction, case for type systems

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

    típicamente prueba functional correctness

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

### Dynamic - Model checking

no chequeamos el código, sino que propiedades sobre un modelo del código

se escriben cosas más abstractas, como "no hay deadlocks"

protocolos de consenso: queremos chequear que se alcanza el consenso eventualmente (o falla).

son difíciles de escribir directamente sobre el código, pero fáciles sobre un modelo (por ej un grafo reachability es fácil)

usualmente propiedades de liveness, safety, etc. más generales que funcionales (como en deductive verification). 

### Rice's theorem

Es un teorema matemático (lo vemos en LyC)

anything interesting one wants to know about an expressive language is undecidable!

si queremos probar propiedades, en general son indecidibles. Hacemos best effort. El sistema a veces no responde. Por ej. halting problem.

### En el mundo real

Después de un bug del pentium, metieron model checking en el hw en intel. Armaron su propio model checker.

Se usa mucho en hardware: avionics, railways, embedded systems

### Model checking de PLs

Go huge or go home. O tu model checker escala o no vale la pena intentar publicarlo.

### Sumarización

Great approaches, great tools need great people!

- Deductive

    No para GI developers (normal staff) coders, even superstars need time (y money)

- Dynamic
    - Ni sound ni complete.
    - check model not code
    - dificil de escalar
    - escribir propiedades es demandante
- Static que onda?

### Static verification

Es el menos interesante

- clousot: usa interpretación abstracta (una forma de razonar en modelos con argumentos de punto fijo)
  - fija un conjunto de propiedades y chequea el código para ellas
- facebook infer: separation logic
  - usa otra técnica, separation logic, que está buena para razonar del heap y referencias.
  - Muy bueno para mem violations y races
  - le das el código a infer y chequea una una lista de propiedades
- mem violations, null pointers, floating point precision, divisions by zero, buffer or array overruns

y el GI coder solo tiene que interpretar el output de la herramienta.

"computer says no- your code no good"

problema: sobre aproximan (para decidir propiedades indecidibles)
dan muchos falsos positivos

solución para los falsos positivos: defensive programming para convencer al analizador. Pero es una fiaca.

computational thinking: Modularity. Break your problem in subproblems (d&q), pensar en condiciones, expected input, output, etc.

### Type systems

y pero que onda con los type systems?! es el least demanding approach. Hablamos de qué trata esto [mañana](class2-type-systems.md).

Identifica una mala situación (data type systems) no asignás tipos incompatibles (no podes asignar un bool a un int). Se definen esas reglas y se verifica el código con respecto a ellas.

El compilador se asegura de que no introducís bugs!

Este es el approach de rust. Que el compilador ayude!

Se puede hacer c + model checking + static analysis.

Dream: well typed programs do not go wrong

- Motto de Robin Milner
  - Theory of Type Polymorphism in Programing
  - El abstract es **todo** lo que se tiene que entender de un type system.
  - rust: macros
  - haskell: core probado correcto, módulos compuestos, composición probada correcta
  - luego las reglas se prueban sobre las reglas de tipado
  - en el 78 se prueba *semánticamente*, sobre las reglas de semántica
    - no es dirigido por la sintaxis (se tiene que hacer por punto fijo)
  - después se hizo syntactic type soundness
    - que si es dirigido por la sintaxis, solamente inducción estructural sobre las reglas

problema de java: null es compatible siempre con cualquier tipo, lo que rompe soundness del java type system (con generics).

Si necesita un tipo Top que herede de cualquier tipo, y ahí está null, entonces se puede asignar null a cualquier cosa.

! Con subtipado no hay dirigido por la sintaxis. Porque con cada regla también se puede usar un subtipado. Se vuelve a pruebas semánticas, con una técnica muy común que se llama *logical relations*. Los conjuntos de los tipos están relacionados y esta relación tiene que ser consistente.

hay back and forth de formas dinámicas o sintácticas de demostrar type soundness.

se hace una sola vez cuando se definen las reglas.

#### Limitaciones

- Para controlar data races? se puede pero es restrictiv