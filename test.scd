(
Can.init;
s.boot;
)

(
~iso = IsoFluent;
a = FluentCan(\can2)
.notes([61,62,63,64])
.period(10)
.tempos([1,2])
.len(20)
.transps([_+0, _+5, _+7])
.apply(~iso.xo("xoo"))
.repeat(inf)
//.canon.canon
// .play
)
a.postCanon
a.pause
a.stop
a.resume
a.repeat

b = a.copy.mapNotes(_+20); // toca un canon

// c = a.copy.def_(\can3).type_(\diverge).play

// .mapNotes(_++[34, 35]) // otro distinto canon
)

b.play;
c.play;
a.canon.pause



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
