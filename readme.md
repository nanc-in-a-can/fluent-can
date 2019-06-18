# Fluent Can <!-- omit in toc -->
Live coding wrapper for Nanc-in-a-Can/canon-generator

- [Objectives](#Objectives)
- [Usage](#Usage)
- [The two ways to create a `FluentCan`](#The-two-ways-to-create-a-FluentCan)
- [Constructor](#Constructor)
- [Setters](#Setters)
  - [Getters](#Getters)
- [Making modifications to a FluentCan](#Making-modifications-to-a-FluentCan)
- [Copying (cloning) a Fluent Can](#Copying-cloning-a-Fluent-Can)
- [Modifying a single parameter of a canon](#Modifying-a-single-parameter-of-a-canon)
  - [Mappers](#Mappers)

## Objectives
The main purpose of `FluentCan` is to allow for a concise approach to creating and transforming temporal canons in a live coding context. This is achieved by providing sensible defaults and efficient mechanisms to deal with the data structure provieded by Nanc-in-a-Can/canon-generator.

## Usage
```supercollider
(
// make a Can.converge
a = FluentCan().notes([30]).period(1).play;
)


(// copies canon `a` and modifies it
b = a.copy 
.def(\can2)
.mapNotes(_++[34, 35])// [MidiNote] -> ([MidiNote] -> [MidiNote]) -> [MidiNote]
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


## The two  ways to create a `FluentCan`
Both methods will yield the same canon.

Using the `FluentCan` constructor.
```supercollider
FluentCan(notes: [80, 85], period: 1, repeat: 1).play;
```

Using the instance setters.
```supercollider
FluentCan().notes([80, 85]).period(1).repeat(2).play;
```

## Constructor
A `FluentCan` can be created with the following required and optional properties (most required properties provide defaults).

```supercollider
FluentCan(
  //Canon configuration
  durs: [Number], // defaults to [1], required
  notes: [Number (midi note)], // defaults to [], required, must be other than [] to produce anything
  tempos: [Number], // defaults to [60], required, each number will create a new voice, provided there are as many transps as tempos
  transps: [Number], // defaults to [0], required, each number will create a new voice, provided there are as many tempos as transps
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

See [Copying (cloning) a Fluent Can](#Copying-cloning-a-Fluent-Can) for relevant examples.

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

## Copying (cloning) a Fluent Can
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

### Mappers
Methods prefaced with the word `map`, such as `mapNotes` and `mapDurs`, all work the same way. They receive a function that should take as its argument the value of the property (i.e. `notes` for `mapNotes`, etc.) and should return a new value for that property.

```supercollider
c = FluentCan().notes([40, 50]);
c.notes; // [40, 50]

d = c.mapNotes({|notes| notes + 20});
d.notes; // [60, 70]

e = d.mapNotes(_ + 7}); // remember this is a shorthand, where _ stands for the value of notes, that is, [60, 70] as it was previously mapped.
e.notes // [67, 77]
```