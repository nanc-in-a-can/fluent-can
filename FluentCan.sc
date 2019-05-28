FluentCan {
    var <> prDef;
    var <> prCp;
    var <> prNotes;
	var <> prDurs;
	var <> prTempos;
	var <> prTransps;
    var <> prAmps;
    var <> prPercentagefortempo;
    var <> prNormalize;
    var <> prBasetempo;
    var <> prConvergeonlast;
    var <> prInstruments;
    var <> prPeriod;
    var <> prLen;
    var <> prRepeat;
    var <> prPlayer;
    var <> prOsc;
    var <> prMeta;
    var <> prMelodist;
    var <> prType;

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
        len = nil,
        repeat = (1),
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
        this.len(len);
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
                    melody: melodist.(this.durs, this.notes, this.len),
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
                    melody: melodist.(this.durs, this.notes, this.len),
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

    apply {|fn, def|
        var can = if(def.isNil.not, {this.copy}, {this});
        var maybeNewFluentCan = fn.(can);
        if(maybeNewFluentCan.class != FluentCan, {("[FluentCan] .apply expects the return value of the function to be an instance of FluentCan, received" + maybeNewFluentCan.class).throw});
        ^maybeNewFluentCan;
    }

    mapNotes {|fn|
        this.prNotes = fn.(this.notes)
    }

    mapDurs {|fn|
        this.prDurs = fn.(this.durs)
    }

    mapTempos {|fn|
        this.prTempos = fn.(this.tempos)
    }

    mapTransps {|fn|
        this.prTransps = fn.(this.transps)
    }

    mapPercentageForTempo {|fn|
        this.prPercentagefortempo = fn.(this.percentageForTempo)
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

    // getters and setters mapped to the same names
    def {|val|
        if(val.isNil,
            {^this.prDef},
            {this.prDef = val})
    }

    cp {|val|
        if(val.isNil,
            {^this.prCp},
            {this.prCp= val})
    }

    notes {|val|
        if(val.isNil,
            {^this.prNotes},
            {this.prNotes = val})
    }

    durs {|val|
        if(val.isNil,
            {^this.prDurs},
            {this.prDurs = val})
    }

    tempos {|val|
        if(val.isNil,
            {^this.prTempos},
            {this.prTempos = val})
    }

    transps {|val|
        if(val.isNil,
            {^this.prTransps},
            {this.prTransps = val})
    }

    amps {|val|
        if(val.isNil,
            {^this.prAmps},
            {this.prAmps = val})
    }

    percentageForTempo {|val|
        if(val.isNil,
            {^this.prPercentagefortempo},
            {this.prPercentagefortempo = val})
    }

    normalize {|val|
        if(val.isNil,
            {^this.prNormalize},
            {this.prNormalize = val})
    }

    baseTempo {|val|
        if(val.isNil,
            {^this.prBasetempo},
            {this.prBasetempo = val})
    }

    convergeOnLast {|val|
        if(val.isNil,
            {^this.prConvergeonlast},
            {this.prConvergeonlast = val})
    }

    instruments {|val|
        if(val.isNil,
            {^this.prInstruments},
            {this.prInstruments = val})
    }

    period {|val|
        if(val.isNil,
            {^this.prPeriod},
            {this.prPeriod = val})
    }

    len {|val|
        if(val.isNil,
            {^this.prLen},
            {this.prLen = val})
    }

    repeat {|val|
        if(val.isNil,
            {^this.prRepeat},
            {this.prRepeat = val})
    }

    player {|val|
        if(val.isNil,
            {^this.prPlayer},
            {this.prPlayer = val})
    }

    osc {|val|
        if(val.isNil,
            {^this.prOsc},
            {this.prOsc = val})
    }

    meta {|val|
        if(val.isNil,
            {^this.prMeta},
            {this.prMeta = val})
    }

    melodist {|val|
        if(val.isNil,
            {^this.prMelodist},
            {this.prMelodist = val})
    }

    type {|val|
        if(val.isNil,
            {^this.prType},
            {this.prType = val})
    }
}