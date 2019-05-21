FluentCan {
/*    var <> def;
    var <> cp;
    var <> notes;
	var <> durs;
	var <> tempos;
	var <> transps;
    var <> amps;
    var <> percentageForTempo;
    var <> normalize;
    var <> baseTempo;
    var <> convergeOnLast;
    var <> instruments;
    var <> period;
    var <> repeat;
    var <> player;
    var <> osc;
    var <> meta;
    var <> melodist;
    var <> type;*/

	*new {|def, durs, notes, cp, tempos, transps, amps, percentageForTempo, normalize, baseTempo, convergeOnLast, instruments, period, repeat, player, osc, meta, melodist, type|
		 ^super.new.init(
            def, durs, notes, cp, tempos, transps, amps, percentageForTempo, normalize, baseTempo, convergeOnLast, instruments, period, repeat, player, osc, meta, melodist, type
        )
	}

	init {|
        def = nil,
        durs = ([1]),
        notes = ([]),
        cp = (0),
        tempos = ([60]),
        transps = ([0]),
        amps = ([1]),
        percentageForTempo = ([1]),
        normalize = (true),
        baseTempo = (60),
        convergeOnLast = (false),
        instruments = ([\sin]),
        period = nil,
        repeat = (inf),
        player = nil,
        osc = nil,
        meta = nil,
        melodist = (\isomelody),
        type = (\converge)
       |
        this.def(def);
        this.durs(durs);
        this.notes(notes);
        this.cp(cp);
        this.tempos(tempos);
        this.transps(transps);
        this.amps(amps);
        this.percentageForTempo(percentageForTempo);
        this.normalize(normalize);
        this.baseTempo(baseTempo);
        this.convergeOnLast(convergeOnLast);
        this.instruments(instruments);
        this.period(period);
        this.repeat(repeat);
        this.player(player);
        this.osc(osc);
        this.meta(meta);
        this.melodist(melodist);
        this.type(type);
	}

    canon {
        ^this.makeCanon()
    }

    makeCanon {
         var melodist = this.getMelodist();
        ^switch(this.type,
            \converge, {
                Can.converge(
                    symbol: this.def,
                    melody: melodist.(this.durs, this.notes),
                    cp: this.cp,
                    voices: Can.convoices(this.tempos, this.transps, this.amps),
                    instruments: this.instruments,
                    period: this.period,
                    repeat: this.repeat,
                    player: this.player,
                    osc: this.osc,
                    meta: this.meta
                )
            },
            \diverge, {
                Can.diverge(
                    symbol: this.def,
                    melody: melodist.(this.durs, this.notes),
                    tempos: Can.divtempos(this.tempos, this.percentageForTempo, this.normalize),
                    voices: Can.divoices(this.transps, this.amps),
                    baseTempo: this.baseTempo,
                    convergeOnLast: this.convergeOnLast,
                    instruments: this.instruments,
                    period: this.period,
                    repeat: this.repeat,
                    player: this.player,
                    osc: this.osc,
                    meta: this.meta
                )
            }
        )
    }

    play {
        this.canon.play;
    }

    mapNotes {|fn|
        this.notes = fn.(this.notes)
    }

    mapDurs {|fn|
        this.durs = fn.(this.durs)
    }

    mapTempos {|fn|
        this.tempos = fn.(this.tempos)
    }

    mapTransps {|fn|
        this.transps = fn.(this.transps)
    }

    mapPercentageForTempo {|fn|
        this.percentageForTempo = fn.(this.percentageForTempo)
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

    def {|val|
        if(val.isNil,
            {^this.def},
            {this.def = val})
    }

    cp {|val|
        if(val.isNil,
            {^this.cp},
            {this.cp = val})
    }

    notes {|val|
        if(val.isNil,
            {^this.notes},
            {this.notes = val})
    }

    durs {|val|
        if(val.isNil,
            {^this.durs},
            {this.durs = val})
    }

    tempos {|val|
        if(val.isNil,
            {^this.tempos},
            {this.tempos = val})
    }

    transps {|val|
        if(val.isNil,
            {^this.transps},
            {this.transps = val})
    }

    amps {|val|
        if(val.isNil,
            {^this.amps},
            {this.amps = val})
    }

    percentageForTempo {|val|
        if(val.isNil,
            {^this.percentageForTempo},
            {this.percentageForTempo = val})
    }

    normalize {|val|
        if(val.isNil,
            {^this.normalize},
            {this.normalize = val})
    }

    baseTempo {|val|
        if(val.isNil,
            {^this.baseTempo},
            {this.baseTempo = val})
    }

    convergeOnLast {|val|
        if(val.isNil,
            {^this.convergeOnLast},
            {this.convergeOnLast = val})
    }

    instruments {|val|
        if(val.isNil,
            {^this.instruments},
            {this.instruments = val})
    }

    period {|val|
        if(val.isNil,
            {^this.period},
            {this.period = val})
    }

    repeat {|val|
        if(val.isNil,
            {^this.repeat},
            {this.repeat = val})
    }

    player {|val|
        if(val.isNil,
            {^this.player},
            {this.player = val})
    }

    osc {|val|
        if(val.isNil,
            {^this.osc},
            {this.osc = val})
    }

    meta {|val|
        if(val.isNil,
            {^this.meta},
            {this.meta = val})
    }

    melodist {|val|
        if(val.isNil,
            {^this.melodist},
            {this.melodist = val})
    }

    type {|val|
        if(val.isNil,
            {^this.type},
            {this.type = val})
    }
}