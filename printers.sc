+ FluentCan {
    // note this functions will trigger the creation of a Canon, user for debugging purposes only
    postCanon {this.canon.canon.postln}
    postNotes {this.canon.canon.collect(_.notes).postln}
    postDurs {this.canon.canon.collect(_.durs).postln}
}