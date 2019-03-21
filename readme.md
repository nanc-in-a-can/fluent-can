# Fluent Can
Livecoding wrapper for Nanc-in-a-Can/canon-generator

This is still a proof of concept.

## Usage
```
(
a = FluentCan(\can2)
.notes_([30])
.period_(1)
.repeat_(inf);

a.mapNotes(_+20).play; // plays a canon

a.def_(\can3)
.mapNotes(_++[34, 35])// [MidiNote] -> ([MidiNote] -> [MidiNote]) -> [MidiNote]
.play // plays a different canon
)
```