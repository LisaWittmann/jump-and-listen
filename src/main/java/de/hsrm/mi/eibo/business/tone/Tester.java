package de.hsrm.mi.eibo.business.tone;

import de.hsrm.mi.eibo.persistence.song.SongPersitinator;

public class Tester {

    public static void main(String [] args) {
        SongPersitinator songpers = new SongPersitinator();

        Song s1 = new Song("intermediate-false;freude schoener goetterfunken:E E F G G F E D C C D E E D D E E F G G F E D C C D E D C C D D E C D E F E C D E F E D C D E E E F G G F E D C C D E D C C ");
        Song s2 = new Song("beginner-false;alle meine entchen:C D E F G G A A A A G A A A A G F F F F E E D D D D C ");
        Song s3 = new Song("expert-false;hedwigs theme:H E G FS E H A FS E G FS DS E H H E G FS E H D CS C GS C H AS FS G E G H G H G C H AS FS G H AS AS H H G H G H G D CS C GS C H AS FS G E ");
        Song s4 = new Song("expert-false;vivaldi:C E E E D C G G F E E E D C G G F E F G F E D E C E E E D C G G F E E E D C G G F E F G F E D E G F E F G A G E G F E F G A G F E D C D C E G F E F G A G E G F E F G A G E A G F E D C D C C E E E D E F F E D D D C D E E F G G G G F E E E E F G G G F E D E G F E F G A G E G F E F G A G E A G F E D C D C ");
        Song s5 = new Song("intermediate-false;he is a pirate:C C D D D E F F F G E E D C C D C C D D D E F F F G E E D C D ");
        Song s6 = new Song("beginner-false;jingle bells:E E E E E E E G C D E F F F F F E E E E D D E D ");
        songpers.saveData(s1);
        songpers.saveData(s2);
        songpers.saveData(s3);
        songpers.saveData(s4);
        songpers.saveData(s5);
        songpers.saveData(s6);
    }
    
}