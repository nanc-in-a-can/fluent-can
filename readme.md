# Fluent Can <!-- omit in toc -->
Livecoding wrapper for Nanc-in-a-Can/canon-generator

This is still a proof of concept.
- [Usage](#Usage)
- [Constructor](#Constructor)
- [Different ways to create a `FluentCan`](#Different-ways-to-create-a-FluentCan)
- [Making modifications to a FluentCan](#Making-modifications-to-a-FluentCan)
- [Copying (cloning) a Fluent Can](#Copying-cloning-a-Fluent-Can)
- [Modifying a single parameter of a canon](#Modifying-a-single-parameter-of-a-canon)



## Usage
```
(
// make a Can.diverge
a = FluentCan(\can2)
.notes([30])
.period(1)
.repeat(inf);

a.mapNotes(_+20).play; // plays a canon
)

(// copies canon `a` and modifies it
b = a.copy 
.def(\can3)
.mapNotes(_++[34, 35])// [MidiNote] -> ([MidiNote] -> [MidiNote]) -> [MidiNote]
.tempos([60, 70])
.transps([0, 7])
.play // will play a different canon
)

(// converts canon `b` to Can.diverge
c = b.copy
.def(\can4)
.percentageForTempo_([1, 1])
.type(\diverge)
.play 
)
```

## Constructor
A `FluentCan` can be created with the following required and optional properties.

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
  melodist: Symbol, / defaults to \isomelody, may also be \melody
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
  player: A Canon Player, defaults to Can.pPlayer
  osc: OSCConfig, // defaults to nil
  meta: EventObj, //defaults to nil
)
```


## Different ways to create a `FluentCan`
Both methods will yield the same canon.

Using the `FluentCan` constructor.
```supercollider
FluentCan(notes: [80, 85], period: 1, repeat: 1).play;
```

Using the instance setters.
```supercollider
FluentCan().notes([80, 85]).period(1).repeat(2).play;
```

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

```
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