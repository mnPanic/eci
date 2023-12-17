# Class 4 - JaTyC, subtipado

Para mañana:

typestates no es lo más famoso, sino que es session types.
Ese se usa más para un computational model communciation based
Pero acá se piensa más en imperative, sequential programming. Los typestates representan a las entidades. Session types representan las interacciones con otras entidades.

Ayer: memory y thread safety en mungo, en detlle

hoy:

- cómo usarlo, casos concretos del código
- detalle en up/down casting (subtipado)
- otros tools para rust y para un functional language
- mostrar un poco de cómo es trabajar con proof assistants, a ver si realmente es más cumbersome

## JaTyC: Java Typestate Checker

Después de haber compilado con java, corrés jaytic (es otro programa). La herramienta asume que es código typechecked. Se usan los ASTs generados por el compilador (no hace falta saberlo para usarlo igual)

Garantías:

- Protocol compliance as completion
- Absence of null pointer exceptions
- Subclasses instanes respect the protocol of their superclasses

También está funcionando subtyping pero no está demostrado todavía.

- cada regla va a tener que tener en cuenta subtyping, es una fiaca.
- los resultados de ayer se demuestran por inducción en la derivación (o en un step de reducción de la semántica operacional). Con subtipado ya no es una técnica de demostración. Se usa una técnica diferente, lógical relations.

contract oriented programming: pre y post condiciones

Dependent types: int es un tipo. Si quiero un int positivo, es un tipo dependiente. asocia una propiedad a un tipo. Permiten hacer verificación de programas. Hay un lenguaje que se puede definir que es decidible. Liquid types. es un área muy activa de research

### session types

son cosas diferentes

- un typestate anuncia el protocolo hacia afuera (y tenés clientes que te usan)
  - el cliente puede tener otro protocolo
- un session type describe el communication protocol entre **dos entidades**

