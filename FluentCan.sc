/*
    Can.converge(sym ? \bombo,
        melody: Can.isomelody(
            durs: [1],
            notes: [[20, 20, 32, 32]],
            len: 8
        ),
        cp: [1,2,3,4].choose,
        repeat: 4,
        voices: Can.convoices(
            tempos: [1, 2, 3/2, 5, 9/7]
            ,
            transps: [
                0,
                0.1,
                0.2,
                _.pipe(_.at(0), _+[38, 0, 45]),
                _.pipe(_.scramble, _.[0..2], _+[48, 0, 47]),
                _.pipe(_.scramble, _.[0..2], _+[48, 0, 47])
            ],
        ),
        instruments: [\grain],
        period: p/2,
        meta: (amp: 8, quant: 1, out: 99),
    );

*/

FluentCan {
    var <> def;
    var <> cp;
    var <> notes;
	var <> durs;
	var <> tempos;
	var <> transps;
    var <> instruments;
    var <> period;
    var <> repeat;
    var <> meta;
    var <> melodist;

	*new {|def, durs, notes,  cp, tempos, transps, instruments, period, repeat, meta, melodist|
		 ^super.new.init(
            def, durs, notes, cp, tempos, transps, instruments, period, repeat, meta, melodist
        )
	}

	init {|
        def = nil,
        durs = ([1]),
        notes = ([]),
        cp = (0),
        tempos = ([60]),
        transps = ([0]),
        instruments = ([\sin]),
        period = nil,
        repeat = (inf),
        meta = nil,
        melodist = (\isomelody)
       |
        this.def = def;
        this.durs = durs;
        this.notes = notes;
        this.cp = cp;
        this.tempos = tempos;
        this.transps = transps;
        this.instruments = instruments;
        this.period = period;
        this.repeat = repeat;
        this.meta = meta;
        this.melodist = \isomelody
	}

    canon {
        ^this.makeCanon()
    }

    makeCanon {
         var melodist = this.getMelodist();
        ^Can.converge(
            symbol: this.def,
            melody: melodist.(this.durs, this.notes),
            cp: this.cp,
            voices: Can.convoices(this.tempos, this.transps),
            instruments: this.instruments,
            period: this.period,
            repeat: this.repeat
        )
    }

    play {
        this.canon = this.makeCanon();
        this.canon.play;
    }

    mapNotes {|fn|
        this.notes = fn.(this.notes)
    }

    mapDurs {|fn|
        this.durs = fn.(this.durs)
    }

    getMelodist {
        ^if(Set[\melody, \isomelody].sect(Set[this.melodist]).size > 0,
            {
                (
                    \melody: {|durs, notes| Can.melody(durs, notes)},
                    \isomelody: {|durs, notes, len| Can.isomelody(durs, notes, len)}
                )[this.melodist]
            },
            {this.melodist});
    }
}