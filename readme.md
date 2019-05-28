# Fluent Can
Livecoding wrapper for Nanc-in-a-Can/canon-generator

This is still a proof of concept.

## Usage
```supercollider
(
Can.init;
s.boot;
)

(
// make a Can.converge
a = FluentCan(\can2)
.notes([61,62,63,64])
.period(10)
.tempos([1,2])
.repeat(inf)
.play
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
.percentageForTempo([1, 1])
.type(\diverge)
.play
)
```
