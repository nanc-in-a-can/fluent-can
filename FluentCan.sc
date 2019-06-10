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
        this.currentCanonInstance = switch(this.type,
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
        this.prNotes = fn.(this.notes)
    }

    compNotes {|fnArr|
        this.prNotes = PrFluentCan.composeFnArrays(this.notes, fnArr);
    }


    mapDurs {|fn|
        this.prDurs = fn.(this.durs)
    }

    compDurs {|fnArr|
        this.prDurs = PrFluentCan.composeFnArrays(this.durs, fnArr);
    }

    mapTempos {|fn|
        this.prTempos = fn.(this.tempos)
    }

    compTempos {|fnArr|
        this.prTempos = PrFluentCan.composeFnArrays(this.tempos, fnArr);
    }

    mapTransps {|fn|
        this.prTransps = fn.(this.transps)
    }

    compTransps {|fnArr|
        this.prTransps = PrFluentCan.composeFnArrays(this.transps, fnArr);
    }

    mapPercentageForTempo {|fn|
        this.prPercentagefortempo = fn.(this.percentageForTempo)
    }

    compPercentageForTempo {|fnArr|
        this.prPercentagefortempo = PrFluentCan.composeFnArrays(this.percentageForTempo, fnArr);
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