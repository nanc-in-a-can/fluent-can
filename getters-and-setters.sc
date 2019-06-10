+ FluentCan {
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