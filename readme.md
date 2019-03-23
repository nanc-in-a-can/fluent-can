# Fluent Can
Livecoding wrapper for Nanc-in-a-Can/canon-generator

This is still a proof of concept.

## Usage
```supercollider
(
// make a Can.diverge
a = FluentCan(\can2)
.notes_([30])
.period_(1)
.repeat_(inf);

a.mapNotes(_+20).play; // plays a canon
)

(// copies canon `a` and modifies it
b = a.copy 
.def_(\can3)
.mapNotes(_++[34, 35])// [MidiNote] -> ([MidiNote] -> [MidiNote]) -> [MidiNote]
.tempos_([60, 70])
.transps_([0, 7])
.play // will play a different canon
)

(// converts canon `b` to Can.diverge
c = b.copy
.def_(\can4)
.percentageForTempo_([1, 1])
.type_(\diverge)
.play 
)
```
