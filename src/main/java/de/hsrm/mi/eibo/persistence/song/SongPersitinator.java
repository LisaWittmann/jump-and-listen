package de.hsrm.mi.eibo.persistence.song;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hsrm.mi.eibo.business.tone.Song;
import de.hsrm.mi.eibo.persistence.DataPersistinator;
import de.hsrm.mi.eibo.persistence.highscore.HighscorePersistinator;

/**
 * Speicherung von Song-Instanzen in einer .txt Datei Laden und Erzeugen von
 * Songs anhand einer .txt Datei Löschen von Einträgen aus der .txt Datei
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public class SongPersitinator implements DataPersistinator<Song> {

    private final String dataPath = "songs.txt";
    private BufferedReader reader = null;
    private BufferedWriter writer = null;
    private HighscorePersistinator highscorePers = new HighscorePersistinator();

    @Override
    public void saveAll(List<Song> data) {
        StringBuilder sb = new StringBuilder();
        for (Song s : data) {
            if (!nameAccepted(s.getName()))
                continue;
            sb.append(s.toString()).append("\n");
        }
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dataPath, true)));
            writer.write(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Song> loadAll() {
        List<Song> loaded = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(dataPath));
            String line;
            while ((line = reader.readLine()) != null) {
                loaded.add(new Song(line));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return loaded;
    }

    @Override
    public void saveData(Song data) {
        if (!nameAccepted(data.getName()))
            return;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dataPath, true)));
            writer.write(data.toString() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Song loadData() {
        Song song = null;
        try {
            reader = new BufferedReader(new FileReader(dataPath));
            String line;
            line = reader.readLine();
            song = new Song(line);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return song;
    }

    /**
     * Löschen eines Songs, es werden auch die Highscores für diesen Song entfernt
     * 
     * @param song zu löschender Song
     * @return überarbeitete Liste aller Songs
     */
    public List<Song> removeData(Song song) {
        List<Song> data = loadAll();
        List<Song> newData = new ArrayList<>();
        for (Song s : data) {
            if (!s.getName().equalsIgnoreCase(song.getName())) {
                newData.add(s);
            } else {
                highscorePers.removeBySong(song);
            }
        }
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dataPath)));
            writer.write("");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        saveAll(newData);
        return newData;
    }

    /**
     * Überschreiben eines Songs (nach Bearbeitung)
     * 
     * @param song neuer Song mit gleichem Namen wie zu überschreibender Song
     * @return überarbeitete Liste aller Songs
     */
    public List<Song> overrideData(Song song) {
        List<Song> data = loadAll();
        List<Song> newData = new ArrayList<>();

        for (Song s : data) {
            if (!s.getName().equalsIgnoreCase(song.getName())) {
                newData.add(s);
            } else {
                highscorePers.removeBySong(s);
                newData.add(song);
            }
        }
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dataPath)));
            writer.write("");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        saveAll(newData);
        return newData;
    }

    public Song loadByName(String name) {
        for (Song s : loadAll()) {
            if (s.getName().equals(name))
                return s;
        }
        return null;
    }

    /**
     * Überprüfung, ob ein Name den Regeln entspricht Namen dürfen nur aus maximal
     * 29 Kleinbuchstaben und Leerzeichen bestehen
     * 
     * @param name zu prüfender Name
     * @return true wenn Name akzeptiert, sonst false
     */
    public boolean nameAccepted(String name) {
        String regex = "^[a-z\\s]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);
        for (Song s : loadAll()) {
            if (s.getName().equals(name)) {
                return false;
            }
        }
        if (name.contains(Song.getLevelSeperator()) || name.contains(Song.getNameSeperator())) {
            return false;
        }
        if (name.length() >= 30) {
            return false;
        }
        if (matcher.matches() == false) {
            return false;
        }
        return true;
    }

}