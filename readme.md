# Fluent Can <!-- omit in toc -->
Live coding wrapper for `Nanc-in-a-Can/canon-generator`

- [Objectives](#Objectives)
- [Installation](#Installation)
- [Using git (recommended)](#Using-git-recommended)
  - [Manual download](#Manual-download)
  - [Updating the library](#Updating-the-library)
- [Usage](#Usage)
- [The two ways to create a `FluentCan`](#The-two-ways-to-create-a-FluentCan)
- [Constructor](#Constructor)
- [Setters](#Setters)
  - [Getters](#Getters)
- [Making modifications to a FluentCan](#Making-modifications-to-a-FluentCan)
- [Copying (cloning) a FluentCan](#Copying-cloning-a-FluentCan)
- [Modifying a single parameter of a canon](#Modifying-a-single-parameter-of-a-canon)
- [Advanced functionality](#Advanced-functionality)
  - [Mappers](#Mappers)
    - [Note on mappers](#Note-on-mappers)
  - [Applying custom transformations to a canon](#Applying-custom-transformations-to-a-canon)
    - [Sample implementation of a function for `.apply`](#Sample-implementation-of-a-function-for-apply)
  - [.compTransps](#compTransps)
    - [Composition works like this:](#Composition-works-like-this)
    - [Example use case](#Example-use-case)
- [Creating and accessing a Canon instance](#Creating-and-accessing-a-Canon-instance)
- [Printing Canon data](#Printing-Canon-data)
- [Further learning](#Further-learning)

## Objectives
The main purpose of `FluentCan` is to allow for a concise approach to creating and transforming [temporal canons](https://github.com/nanc-in-a-can/canon-generator#temporal-canons) in a live coding context. This is achieved by providing sensible defaults and efficient mechanisms to deal with the data structure required by [Nanc-in-a-Can/canon-generator](https://github.com/nanc-in-a-can/canon-generator).

## Installation
`FluentCan` provides a set of classes, and thus the need to be installed in specific folder to be available.

## Using git (recommended)
If you have `git` installed (highly recommended, https://git-scm.com/downloads), the easiest way to install this software is by compiling this lines in the SuperCollider IDE:

```supercollider
// If you have not already, first you need to install nanc-in-a-can/canon-generator
Quarks.install("https://github.com/nanc-in-a-can/canon-generator.git");

// Then do the same for FluentCan
Quarks.install("https://github.com/nanc-in-a-can/fluent-can.git");
```

Then you need to recompile the class library. In the menu bar: `Language > Recompile Class Library`


### Manual download
Otherwise you can follow this guide to find the paths in which you may install this software: http://doc.sccode.org/Guides/UsingExtensions.html

For that you will need to download this repository.
[Click here](https://github.com/nanc-in-a-can/fluent-can/archive/master.zip) and save the zip file wherever you want.

If you do not have `nanc-in-a-can/canon-generator` installed, you will have to the same with [this repo](https://github.com/nanc-in-a-can/canon-generator/archive/master.zip) .


### Updating the library
With `git` it is as simple as 
```supercollider
(
Quarks.update("https://github.com/nanc-in-a-can/canon-generator.git");
Quarks.update("https://github.com/nanc-in-a-can/fluent-can.git");
)
```

## Usage
The best way to learn how `FluentCan` works if to follow this readme and play around with the examples provided.

```supercollider
(
Can.init;
s.boot;
)

(
// make a Can.converge
a = FluentCan().def(\can1).notes([30]).period(1).play;
)


(// copies canon `a` and modifies it
b = a.copy 
.def(\can2)
.mapNotes(_++[34, 35])// concatenates two new notes to the notes of canon `a` aka \can1
.tempos([60, 70])
.transps([0, 7])
.play // will play a different canon
)

(// converts canon `b` to Can.diverge
c = b.copy
.def(\can4)
.percentageForTempo([1, 1])
.type(\diverge)
.play 
)
```

## The two ways to create a `FluentCan`
Both methods will yield the same canon.

Using the `FluentCan` constructor.
```supercollider
FluentCan(notes: [80, 85], period: 1, repeat: 1).play;
```

Using the instance setters.
```supercollider
FluentCan().notes([80, 85]).period(1).repeat(2).play;
```

The second approach is recommended and much more flexible, as will become clear here: [Copying (cloning) a FluentCan](#Copying-cloning-a-FluentCan)


## Constructor
A `FluentCan` can be created with the following required and optional properties (most required properties provide defaults).

```supercollider
FluentCan(
  //Canon configuration
  durs: [Number], // defaults to [1], required
  notes: [Number (midi note)], // defaults to [\rest], required, must be other than [\rest] to produce any sound
  tempos: [Number], // defaults to [60], required, each number will create a new voice, provided there are as many transps as tempos
  transps: [Number] || [Number] -> [Number], // defaults to [0], required, each number will create a new voice, provided there are as many tempos as transps. 
  // For transps, functions that take and number array and return a number array may be used instead
  amps: [Number], // requried, defaults to [1]
  period: Number, // defaults to nil
  len: Integer, // defaults to nil
  melodist: Symbol, // defaults to \isomelody, may also be \melody
  type: Symbol, // defaults to \converge, may also be \diverge
  
  // Convergence canon specific configuration
  cp: Integer, // defaults to 0, required for type \converge

  // Divergence canon specific configuration
  percentageForTempo: [Number], // requried, defaults to [1]
  normalize: Boolean, // defaults to false
  baseTempo: Number, // required, defaults to 60
  convergeOnLast: Boolean, // requried, defaults to false

  // Default Player configuration
  instruments: [Symbol], // defaults to [\sin]
  repeat: Number, // defaults to inf
  def: Symbol, //defaults to nil, symbol that will be used to refer to the pattern player. i.e. Pdef(\mycan).stop, will stop a Canon defined with \mycan
  player: CanonPlayer, // defaults to Can.pPlayer, to be documented, if questions, please rise an issue
  osc: OSCConfig, // defaults to nil, to be documented, if questions, please rise an issue
  meta: EventObj, //defaults to nil, to be documented, if questions, please rise an issue
)
```

## Setters
Every property that can be set in the constructor can be used as a setter.

The main advantage of using setters over the constructor is that they can be used at any time and in any order.

See [Copying (cloning) a FluentCan](#Copying-cloning-a-FluentCan) for relevant examples.

```supercollider
  //Canon configuration
  FluentCan()
  .durs([Number])
  .notes([Number (midi note)])
  .tempos([Number],new voice)
  .transps([Number],new voice)
  .amps([Number])
  .period(Number)
  .len(Integer)
  .melodist(Symbol)
  .type(Symbol)
  
  // Convergence canon specific configuration
  .cp(Integer)

  // Divergence canon specific configuration
  .percentageForTempo([Number])
  .normalize(Boolean)
  .baseTempo(Number)
  .convergeOnLast(Boolean)

  // Default Player configuration
  .instruments([Symbol])
  .repeat(Number)
  .def(Symbol,pattern player. i.e. Pdef(\mycan).stop)
  .player(CanonPlayer)
  .osc(OSCConfig)
  .meta(EventObj)
```

### Getters
Every setter is a getter when no argument is provided. In this case it will return the value of the property instead of the `FluentCan` instance. Thus they can not be chained.

## Making modifications to a FluentCan
```supercollider
(
c = FluentCan().notes([80, 85]).period(1).repeat(2); //defining a FluentCan and saving the new instance to variable `c`
c.durs([1, 2, 1.5]).len(10).period(8); // using the instance to modify the list of durations (durs), the quantity of events (len) and the overall duration (period).
c.play;
)
```

## Copying (cloning) a FluentCan
Calling `.copy` will create a new instance of a canon with the same configuration. This configuration can then be modified.

```supercollider
(
c = FluentCan().notes([80, 85]).durs([1, 2, 1.5]).len(10).period(8);
d = c.copy.tempos([3, 4]).transps([-24, -12]).cp(5); // copying the configuration of canon `c`, while adding a  second voice and transposing both voices two and one octaves down respectively. Also setting the converge point for the canon at event 5.

c.play;
d.play;
)
```

## Modifying a single parameter of a canon
While copying is helpful we often want to make changes to the canon we sourced must of our config from.

```supercollider
c = FluentCan().notes([80, 85]).durs([1, 2, 1.5]).len(10).period(8);

(
d = c.copy.tempos([3, 4]).transps([-5, -12]).cp(5).period(7) // add stuff or overwritting it (i.e. period)
  .mapNotes({|notes| notes++[83]}) // modifying notes, here by adding another note to the sequence 
  // .mapNotes(_++[83]) // this is a a shorthand form for the same function
  .mapDurs(_[0..1]);// selecting only the first two durs, i.e. [1, 2], shorthand form
)

c.play;
d.play;
d.stop;
```

## Advanced functionality
### Mappers
Methods prefaced with the word `map`, such as `mapNotes` and `mapDurs`, all work the same way. They receive a function that should take as its argument the value of the property (i.e. `notes` for `mapNotes`, etc.) and should return a new value for that property.

```supercollider
(
c.def(\mycan)
.mapNotes({|notes| notes + 20})
.mapDurs(_ + [1, 2])
.cp(2)
.period(4)
.transps([0, 7])
.tempos([1,2])
.play;
)
```
#### Note on mappers
Mappers return a new instance of a `FluentCan` instead o modifying the existing one. This makes it easier to reason about this functions and makes their output predictable.

You would not be calling canons this way, but this serves to illustrate what we mean.
```supercollider
c = FluentCan().notes([40, 50]);
c.notes; // [40, 50]

d = c.mapNotes({|notes| notes + 20});
d.notes; // [60, 70]
c.notes; // still [40, 50]

d.mapNotes(_+[2, -7]).mapNotes(_*0.8).notes; // mappers may be called as many times as desired and they will compose all their transformations into one.

```

### Applying custom transformations to a canon
The instance method `.apply` exists as a powerful tool to add custom user generated functionality to `FluentCan`.

The method receives a function that takes the `FluentCan` instance and should return an instance of `FluentCan` (new or not). Whatever happens in this method is up to the user.

The library [iso-fluent](https://github.com/nanc-in-a-can/iso-fluent) is under development to provide such functions, but any other function may be added as long as it receives and returns a `FluentCan` instance.

The following example is taken from the `iso-fluent` docs.

```supercollider
(
a = FluentCan(\can)
.notes([61,62,63,64])
.period(10)
.tempos([1,2])
.len(20)
.apply(IsoFluent.xo("oxxxo")) // for every five notes, the first and last notes will be rests, inner three notes sound. Index numbers, for each voices may be used as well: "o10xo" in which case, first note is rest, second will only appear in voice at index 1 (other voices will have rests), third sound be like the second but for voice at index 0, fourth note will appear on all voices and fifth note will be a rest.
.play
)
```

#### Sample implementation of a function for `.apply`
```supercollider
(
// this function may be inlined inside of .apply, but here we use it outside to show that such functions could be easily switched.
~customFunc= {|fluentCan| 
  // This function takes the fluentCan notes and makes them tempos.
  // Then every voice will be transposed by a fifth above the last one.
  var notes = fluentCan.notes;
  fluentCan.tempos(notes).transps(Array.series(notes.size - 1, 0 ,7));
};


a = FluentCan(\aplycan).notes([60, 50 , 55, 57, 62]).period(10)
.apply(~customFunc)
.postCanon
.play
)
```

Note that `.apply` may be used as many times as desired.

### .compTransps
Because `.transps` may take an array of functions as well as an array of numbers (or a mix of the two), the instance methods `.compTransps` may be created.

This two methods take an array a functions and will compose this functions with the functions in  `.transps`. (If `.transps` were to hold numbers, they will be turned into functions).

This allow for incremental addition of functions to  `.transps`.

```supercollider
(
a = FluentCan()
.def(\a)
.notes([60, 50, 50, 57, 62]).period(10)
.cp(4)
.transps([_+[6, 7], -7, _*2, 0])
.tempos([1,2,3, 4]);

b = a.def(\compy)
.compTransps([_+1, _*0.8, _+[1, -2]])
.play

// b will preserve a transps and will add it's own
// resulting transps for b will be [{|notes| (notes + [6, 7]) + 1}, {|notes| (notes - 7) * 0.8}, {|notes| (notes * 2) + [1, 2]}, {|notes| notes + 0}]
)
```

Notice in the example (index for of `.compTransps`), that if no index exists in one of either the array at transps or the array at `.compTransps`, nothing will happen and the existing value for either array will be preserved.

#### Composition works like this:
Function of `transps` array at index 1 will be composed with function of `compTransps` at index 1 and so on for every index...

Then each note of the melody is fed into this function pipeline as follows:
OriginalNote -> transpsFn -> compTranspsFn -> NewNote. Or in code:
`compTranspsFn.(transpsFn.(note))`.

Any number of functions can be composed this way.

#### Example use case
A sample use case for this function can be found in [IsoFluent.xo](https://github.com/nanc-in-a-can/iso-fluent/blob/master/xo.sc#L63).  Here `.compTransps` allow `.xo` to preseve the input `FluentCan` tranpositions and at the time apply its own transposition functions (a series of _*\rest and _*1 that mutes or not note events) to it.  If `.transps` or `.mapTransps` were used instead, then the original transpositions would have been overwritten, definitely and in the case of `.transps`  and probably in the case of `.mapTransps` (unless of course `.mapTransps` would have used some sort of composing function).


## Creating and accessing a Canon instance
To create a canon one simply needs to call `.canon`. This returns the instance of a generated [canon by Nanc-in-a-Can/canon-generator](https://github.com/nanc-in-a-can/canon-generator/blob/master/Canon/Canon.sc).

Calling `.play` will also trigger the generation of a canon.


## Printing Canon data
The following methods can send to the post window data form a rendered canon.
`.postCanon`, `.postNotes`, `.postDurs`. 

Note that this methods will render a canon and thus are somewhat expensive.


## Further learning
As FluentCan is a wrapper on top of `Nanc-in-a-Can/canon-generator` you may want to have a look at the [readme](https://github.com/nanc-in-a-can/canon-generator/) of that repository to learn more about the features provided by these set of classes.