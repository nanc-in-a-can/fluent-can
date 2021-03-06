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

    var <> currentCanonInstance = nil;

	*new {|def, durs, notes, cp, tempos, transps, amps, percentageForTempo, normalize, baseTempo, convergeOnLast, instruments, period, len, repeat, player, osc, meta, melodist, type|
		 ^super.new.init(
            def, durs, notes, cp, tempos, transps, amps, percentageForTempo, normalize, baseTempo, convergeOnLast, instruments, period,len, repeat, player, osc, meta, melodist, type
        )
	}

	init {|
        def = nil,
        durs = ([1]),
        notes = ([\rest]),
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
        repeat = (inf),
        player = nil,
        osc = nil,
        meta = (Event.new),
        melodist = (\isomelody),
        type = (\converge)
       |
        this.prDef = def;
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
        this.currentCanonInstance = switch(this.type,
            \converge, {
                Can.converge(
                    symbol: this.def,
                    melody: melodist.(this.durs, this.notes, this.amps, this.len),
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
                    melody: melodist.(this.durs, this.notes, this.amps, this.len),
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
        );

        ^this.currentCanonInstance;
    }

    play {
        this.canon.play;
    }

    stop {
        this.currentCanonInstance.stop;
    }

    pause {
        this.currentCanonInstance.pause;
    }

    resume {
        this.currentCanonInstance.resume;
    }


    apply {|fn, def|
        var can = if(def.isNil.not, {this.copy}, {this});
        var maybeNewFluentCan = fn.(can);
        if(maybeNewFluentCan.class != FluentCan, {("[FluentCan] .apply expects the return value of the function to be an instance of FluentCan, received" + maybeNewFluentCan.class).throw});
        ^maybeNewFluentCan;
    }

    mapNotes {|fn|
        var newCan = this.copy;
        newCan.prNotes = fn.(newCan.notes);
        ^newCan;
    }

    mapDurs {|fn|
        var newCan = this.copy;
        newCan.prDurs = fn.(newCan.durs)
        ^newCan;
    }

    mapTempos {|fn|
        var newCan = this.copy;
        newCan.prTempos = fn.(newCan.tempos)
        ^newCan;
    }

    mapTransps {|fn|
        var newCan = this.copy;
        newCan.prTransps = fn.(newCan.transps)
        ^newCan;
    }

    compTransps {|fnArr|
        var newCan = this.copy;
        newCan.prTransps = PrFluentCan.composeFnArrays(newCan.transps, fnArr);
        ^newCan;
    }

    mapPercentageForTempo {|fn|
        var newCan = this.copy;
        newCan.prPercentagefortempo = fn.(newCan.percentageForTempo)
        ^newCan;
    }

    getMelodist {
        ^if(Set[\melody, \isomelody].sect(Set[this.melodist]).size > 0,
            {
                (
                    \melody: {|durs, notes, amps| Can.melody(durs, notes, amps)},
                    \isomelody: {|durs, notes, amps, len| Can.isomelody(durs, notes, amps, len)}
                )[this.melodist]
            },
            {this.melodist});
    }
}
