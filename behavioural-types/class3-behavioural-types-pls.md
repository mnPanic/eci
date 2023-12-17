# Clase 3 - Behavioural types and PLs

26/7/23

Hoy vamos a hacer foundational

Hoy vamos a ver los detalles de cómo controlar el comportamiento de los programas.

## Recap

Proof systems for one property: Type safety.

Para proveer garantías formales hay que armar un sistema de razonamiento formal y demostrar propiedades sobre él. Todos los reasoning systems tienen 3 componentes

- sintaxis: lenguaje para describir
- semántica: tiene que tener un meaning
- proof system / axiomatic view: axiomas o reglas. No son arbitrarios, tienen que tener sentido
  - el proof system es consistente con la semántica.
  - no le podemos pedir que sea completo. (godel). Pero si o si tiene que ser sound.

## Hoy

Hoy: Presentar typestates according to el programa, y un OOL que funciona con typestates.

Libro de referencia: the formal semantics of programming languages.

> como es un libro del sigo 20, es todo lapiz y papel. En los del siglo 21, en principio arrancás con proof assistants (coq, anabelle, agda) y cuando te ganan vas a pen and paper

Roadmap

1. Typestates 
2. .
3. .

mecanismo mu X . u (punto fijo, recursion) es facil de presentar pero una fiaca de escribir.

> Letters to the editor: go o statement considered harmful

el X es medio como un goto

sintaxis más flexible pero más difícil de usar.

## Usage types

Posibles computos de un sistema reactivo.

Un objeto en un OOL es un sistema reactivo. El object ofrece input y da output (retorna output). Son árboles que llamamos procesos. Queremos un lenguaje que represente este tipo de comportamiento.

{ diagrama informal en diapo 7 }

> predicción: fija que está yendo a CSP

aspectos importantes:

- input y output. No fuerza una arquitectura. (un patrón de distribuidos es client-server). Acá hay un proceso que envía y acepta requests.
- choices: external e internal.
  - si un obj ofrece muchos métodos, es externo
  - hay outputs producidos por el proceso que dependen de su estado interno.

se pueden mergear.

- Input idem a external choice. **branching**

    {mi;wi | i in I}
    {read;...;write;...}

- Output idem external choice. **selection**

    <li;ui | i in I>
    <ok;...;ko;...>

compromiso: usa ecuaciones en vez de gotos

> acá se empieza a ir un poco, es medio falopa esta sintaxis que está introduciendo para usage types.

la semántica la da como un labelled transition system (LTS!!)

> esto es aburrido porque es cómo LTS de ing2 pero con otra sintaxis

equivalencia de automatas no es buena para sistemas reactivos -> bisimulación (todo esto ing2!)

> en concurrencia es un área entera la investigación de bisimulación
> isomorfismo distingue demasiado, como un loop en a y dos estados que van y vienen en a. Bisimulan pero isomorfismo los distingue.

## Mungo

> hecho por él

An object oriented language with usage types

Es un subset chiquito de la sintaxis java

es un proceso iterativo
das una sintaxis, intentás de dar una semántica.

> Verifiedsoft
> website: verifiedsoftware.com

### Semántica operacional

Es una relación entre las configuraciones (tleng?)

una tremenda fiaca estas reglas

### Tipado

Subject reduction:
No perdes tipado en el proceso de ejecución de un programa
En cada paso tenés algo que es tipable.