# Class 2 - Type Systems & behavioural types

Prueba: Programming project

- qué es un type system
- ejemplos en varios lenguajes
- working program
    - en research: programa que siempre se sigue, hay nuevos problemas pero el program que se sigue es el mismo.
    - hoy nos cuenta cual es el working program para atacar un problema de investigación
  
## Recap

- no queremos shippear bugged software
- lightweight verification via type systems es un approach viable
  - no tan potente como los demás pero capaz alcanza
  - martin kellogg: tenemos software en todos lados pero no *correct* software
- verification y otros sound approaches dan muchas garantias pero requieren mucho esfuerzo
- specilized typecheckers hacen que la verificación sea más fácil

## Type systems

Es una lógica específica.

### Lógicas

Tiene que permitir probar todos los hechos que son verdaderos (no es posible, teorema de incompletitud de godel). 

un type system es una lógica que solo sirve para probar *una* propiedad: type safety. de robert milner .> well typed programs do not go wrong.

qué es una lógica?

- sintaxis para escribir sentencias
- semántica: a satisfaction relation, given a valuation function
- deductive system
  - hay sentencias sintácticamente distintas que semánticamente dicen lo mismo
  - conjunto de axiomas y reglas para chequear la validez de fórmulas

#### Ejemplo: Lógica proposicional

conjunto A de símbolos (variables proposicionales) compuesta por a, b, ...
Las fórmulas se definen mediante una gramática

    P ::= a | bottom | (P v P) | (P ^ P) | (P -> P)

T = bot -> bot
negación es implicación de bottom (como lógica intuicionista)

da semántica con álgebra booleana (objeto matemático). Le da reglas al universo, significado.

da una demostración que es una fiaca de seguir, se mejora con deductive systems

### Deductive systems (deductive systems)

Natural deduction

A cada producción le asigna una regla

- m es un label que dice que es una hipótesis (es una tecnicalidad, no se pueden usar libremente). Se usan en un world bounded context.
- negación con absurdo
- P -> Q
  - asumo P, hago una deducción lógica correcta (D) y llego a Q
  - I introducción
- Q
  - Derivo P y Derivo P -> Q. Concluyo Q
  - E eliminación.
  - Modus ponens
- ... más reglas
- v
  - es una regla delicada y sutil pero te acostumbrás
  - eliminación tengo que tener cuidado
  - introducción de v oculta información (derivo algo y agrego un O con algo que no se)

> las reglas de tipado son deductive systems? Porque las demostraciones son muy parecidas.
> son proof systems, está más adelante

pruebas: bottom up no es creativo, es burocráctico. top down si

el número de hipótesis solo tiene que ser único en el árbol, no importa cual es

- cuando no es dirigido por sintaxis usas la regla del absurdo (bottom)
  - por esto es que no hay forma canónica en deducción natural para lógica clásica

esto es lógica clásica. Muy criticada porque en demos te deja hacer cualquier cosa. Approach alternativo: lógica intuicionista.

Se puede concluir siempre p v neg p (excluded middle), a partir de bottom
{demo pag 11 diapos}

program:

- syntax
- semantics
- proof systems

### Cálculo lambda (mother of all PLs) - sin tipos

syntax, semantics, proof system.

Usa semántica operacional pero hay diferentes tipos

- denotacional
- axiomatic
- etc.

y la propiedad (type system) se hace con respecto a semántica operacional

Lo define Alonzo Church en 1936 para expresar cualquier función computable

#### Sintaxis

Sintaxis es simple. Los términos son variables, funciones (lambda abstractions) y llamadas a funciones (applications)

    M ::= x | (\x.M) | (M M)

ejemplo: aritmética
identity: \ x. x
constant: \ x. y

zero: \f. \x.x
one: \f. \x. f x
dos: \f \x f (f x)
succ \n . \f . \x . f (n f x)
addition: \m . \n . f . \x . m f (n f x)

> como reducir un problema a otro que ya resolviste, es algo que se hace mucho en assembler

es lo mismo que cero y succ

#### Semántica operacional

cómo asignar variables libres: renombrando las variables sin que haya colisiones (no importa el nombre, el cambio del nombre no tiene que asignar cosas que estaban libres antes)

sustitución acá es challenging. Capture avoiding substitution [x := M]

los dos casos que son tricky son los de las lambdas

relación de reducción:

Beta-reduction rule

(\x.N) M -> N[x := M]

no se substituyen variables con valores, sino con términos (high order, el poder del lambda cálculos)

> hay que dar más reglas, todas las de PLP.

#### Problema: No hay tipos

No se puede limitar la aplicación, siempre se pueden llamar funciones.

Hay cómputos que no terminan (divergencias, diversions). Se usa mucho Omega para representar divergencias.

Omega = (\x.xx)\x.xx

    O ->_beta O ->_beta O ...

Para evitar esto, hay que limitar la aplicación, no se puede llamar cualquier función con cualquier otro término.

Con un proof system que determina como funciona cada función -> type system.

### Cálculo lambda tipado

Considerar B como un conjunto de base types (Bool, Int, String, etc.) que itera T

    tau ::= T | tau -> tau

las funciones tienen un tipo para los argumentos y otro tipo para los resultados

el type system rechaza si se hace una verificación y el proof system dice que no es capaz de demostrarlo.

Sintaxis de términos, a la Church (en vez de a la Alonzo), que tiene constantes

    M ::= c | x | (\x:tau.M) | (M M)

> nota interesante: diferencia con plp, que se usa M, N pero eso es un abuso de notación, no? Porque si es una gramática libre de contexto la forma correcta es esta.

Reglas de tipado

Gamma es un mapa de variables a tipos.

{ diapo 17 } lista las reglas de tipao de lambda cálculo de plp

es un *proof system* como el que vimos para lógica proposicional (deducción natural)

hay un catch: si estás yendo bottom up, por ej. para la regla de la aplicación, quién es tau prima?

> duda: es inferencia de tipos? si, lo dice más adelante

garantías:

- progress: no se traba
- termination: solo una cantidad finita de reducion

catch: no es turing complete. Hay funciones computables pero que no se pueden tipar. Por ej. el halting problem.
Pero se puede vivir con eso

> quiero buscar por qué lambda cálculo simplemente tipado no es turing complete

Problemas algorítmicos a resolver:

- Type checking:

    Dado Gamma y M se puede armar una derivación para probar Gamma |- M?

- Type Inference:

    Es posible dado M derivar un Gamma tal que Gamma |- M?

### Simple functional language

Harper's E language. Expression language. Un paso más allá de lambda cálculo.
No tiene recursión

Profesor en CMU. Tiene un libro "practical foundations in programming languages" (el libro en realidad no es muy práctico, más teórico, pero está muy bien construido)

{ diapo 19 } me fui a hablar con gabi del auto, repasar desde acá
{ diapo 20 } volví (me perdí toda la def de E)

Escuelas de presentación de 

- American school de languages: primero sintaxis, después restringís con type system después executar.

  en realidad los compiladores no compilan cosas que no tipan
  como en plp que se asume que está bien tipado en las reglas semánticas

- UK school (robert milner): primero decís la semántica operacional, después controlas los programas con un type system

  para hacer highlight de las funcionalidades de type system, mostrás qué se podría romper y de ahí inferís los tipos

tiene el constructor *let*

### Simple imperative language

tomado de otro libro de semánticas de lenguajes de programación

el While language. No tiene funciones

{ declaración en diapo 21 }

> ese el mismo que se usaba para WP en algo1?

; puede ser sequential composition o line termination

- termination: cada statement tiene que terminar con ;
  - en c el último se compone con void
- en java el cuerpo de métodos es como un programa en c, pero en una clase no hay orden
  - por eso no es composición secuencial.

tipado:
- nuevos constructores -> nuevas reglas -> más trabajo para demostraciones
  - por eso se piensa bien cada vez que se agrega algún constructor.

- mucho trabajo burocráctico para las reglas de tipado.

> interesante como a diferencia de la extensión con stores para el cálculo lambda tipado de plp, acá en vez de tipar las asignaciones como Unit o Void las tipa como Command (cmd)
> y también la diferencia de producir un nuevo type environment (gamma)

- para tipar sequential composition, el primer statement cambia Gamma.
  - Acá también hay que inferir el tipo de S1

Acá se asume *call by value semantics*, haskell tiene *call by need* (lazy)

> para plp, muy práctico eso de renombrar partes del árbol para los juicios de tipado, para implementar en la práctica
> {diapo 24}

es un *proof system*

## Data type mixing son la única fuente de problemas?

hasta ahora solo vimos data types y scope of variables (let o seq comp)

En lenguajes orientados a objetos hay más que eso. Nos movemos más allá de los data types, que nos da nuevos problemas.

Disclaimer: c, java no son type safe. Tiene muchos constructores, es un quilombo escribir código type safe

> better to not be type safe than to be and be the only one using the language

type safety: programas buenos no van wrong. Pero qué es wrong? en c es memory errors. En java null pointer exceptions (y con generics no es posible)

algunos si lo son, como OCaml, Rust (en la primer versión había un glitch). No hay demostración publicada, es un claim

true -> i tried very hard to prove false and didn't succeed
así es en rust, no pudieron probar que no es type safe

otros problemas:

- dejando de lado cuestiones de funcionalidad
- prevenir runtime errors
  - method not understood
  - null dereference
  - dangling pointers

type safety en general no previene crashes en la mayoría de los lenguajes

- null de-referencing
- memory misusage

para lidiar con esto se necesitan más anotaciones, como en OCaml

en general el problema es llamar métodos

- en el momento incorrecto
- en un orden incorrecto

Ejemplo: Iterator class en Java. Hay que llamar a `hasNext` antes de `next`. 

methods are non uniform in their availability, they depend on their internal state. El protocolo del objeto, pero además asegurar de que en el código el cliente está siguiendo el protocolo.

No respetan **el protocolo** de la clase.

Necesitamos **flow-sensitive types**. Un método de verificación que asegura qeu el código no crashea pero que sea suficientemente lightweight como para no requerir tanto del programador.

!! Esto es básicamente un behavioural type.

## Behavioural types

automata -> flow type -> behavioural type (semánticamente)

ejemplo de protocolo para file. No solo tiene un protocolo con un orden, sino que se tiene que completar. Memory safety -> respetar el protocolo

Como le adjunto el behavioural type a un código? Y armar un type system. Tiene que haber una forma de incluir en la sintaxis una forma de describir esas entidades.

en java como no hay GC explícito, no hay problemas de memoria como use after free sino que null pointer exceptions

**typestate**: un tipo de fsm que en realidad es un tipo. Para asegurar compatibilidad de proocolos.

typestate-editor.github.io

> esto es parecido a los LTSs de ing2 y su model checking.

?? Duda:

- cómo hicieron para meterse con el type checker de java?

los objetos tienen que ser usados linealmente. Usar recursos linealmente da buenas propiedades. Se usa crucialmente en rust. Es un elemento esencial para evitar data races. Es muy restrictivo, pero

    se necesitan restricciones para evitar problemas!

si no querés restricciones usá python, pero ahí se rompe todo.

?? duda: se puede autogenerar client code a partir de un behavioural type?

### Garantías formales

- data safety, as usual
- protocol compliance

    objects will *not* not follow

    > no es lo mismo que objects will follow, diferencia de liveness vs safety. Capaz no llaman a los métodos.

    **safety properties!!**

weak liveness problem: protocol completion

- capaz tiene divergencias, eso no importa (por eso weak). *if the program terminated, then the protocol was completed*


linearity, compliance y completion -> memory safety!