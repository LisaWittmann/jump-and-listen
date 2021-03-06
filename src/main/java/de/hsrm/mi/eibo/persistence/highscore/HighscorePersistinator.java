package de.hsrm.mi.eibo.persistence.highscore;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import de.hsrm.mi.eibo.business.tone.Song;
import de.hsrm.mi.eibo.persistence.DataPersistinator;

/**
 * Speicherung von Highscore-Instanzen in einer .txt Datei Laden und Erzeugen
 * von Highscores anhand einer .txt Datei Löschen von Einträgen aus der .txt
 * Datei
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public class HighscorePersistinator implements DataPersistinator<Highscore> {

    private final String dataPath = "highscores.txt";
    private BufferedReader reader = null;
    private BufferedWriter writer = null;

    /**
     * Speichert alle Daten einer Liste in der Datei ab Überschreibt vorherige
     * Einträge nicht
     */
    @Override
    public void saveAll(List<Highscore> data) {
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dataPath, true)));
            for (Highscore h : data) {
                writer.write(h.toString() + "\n");
            }
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

    /**
     * Lädt alle gespeicherten Highscores aus Datei
     * 
     * @return Liste mit allen Einträgen
     */
    @Override
    public List<Highscore> loadAll() {
        List<Highscore> loaded = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(dataPath));
            String line;
            while ((line = reader.readLine()) != null) {
                loaded.add(new Highscore(line));
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

    /**
     * Speichert einzelnen Highscore in Datei ab Überschreibt vorherige Einträge
     * nicht
     */
    @Override
    public void saveData(Highscore data) {
        List<Highscore> all = loadAll();
        for (Highscore highscore : all) {
            if (highscore.equals(data))
                return;
        }
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

    /**
     * Lädt den ersten Eintrag aus der Datei
     * 
     * @return erster Eintrag als Highscore-Objekt
     */
    @Override
    public Highscore loadData() {
        Highscore highscore = null;
        try {
            reader = new BufferedReader(new FileReader(dataPath));
            highscore = new Highscore(reader.readLine());
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
        return highscore;
    }

    /**
     * Lädt alle Einträge zu einem bestimmten Song
     * 
     * @param song Song, nach dem gefiltert werden soll
     * @return Liste gefilterter Daten
     */
    public List<Highscore> loadBySong(Song song) {
        List<Highscore> levelScores = new ArrayList<>();
        for (Highscore h : loadAll()) {
            if (h.getSong() != null && h.getSong().getName().equals(song.getName())) {
                levelScores.add(h);
            }
        }
        return levelScores;
    }

    /**
     * Löscht alle Einträge zu einem Song, wenn dieser gelöscht oder verändert wurde
     * 
     * @param song entfernter Song
     */
    public void removeBySong(Song song) {
        List<Highscore> data = loadAll();
        List<Highscore> newData = new ArrayList<>();

        for (Highscore highscore : data) {
            if (!highscore.getSong().equals(song)) {
                newData.add(highscore);
            }
        }

        // Datei leeren
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
    }

}