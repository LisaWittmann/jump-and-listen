package de.hsrm.mi.eibo.persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import de.hsrm.mi.eibo.business.tone.Song;

public class HighscorePersistinator implements DataPersistinator<Highscore> {

    private final String dataPath = "highscores.txt";
    private BufferedReader reader = null;
    private BufferedWriter writer = null;

    @Override
    public void saveAll(List<Highscore> data) {
        try {   
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dataPath, true)));
            for(Highscore h : data) {
                writer.write(h.toString() + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(writer != null) writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Highscore> loadAll() {
        List<Highscore> loaded = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(dataPath));
            String line;
            while((line = reader.readLine()) != null) {
                loaded.add(new Highscore(line));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(reader != null) reader.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        return loaded;
    }

    @Override
    public void saveData(Highscore data) {
        try {   
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dataPath, true)));
            writer.write(data.toString() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(writer != null) writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

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
                if(reader != null) reader.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        return highscore;
    }

    public List<Highscore> loadBySong(Song song) {
        List<Highscore> levelScores = new ArrayList<>();
        for(Highscore h : loadAll()) {
            if(h.getSong() != null && h.getSong().getName().equals(song.getName())) {
                levelScores.add(h);
            }
        }
        return levelScores;
    }

    public void removeBySong(Song song) {
        List<Highscore> data = loadAll();
        try {   
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dataPath)));
            for(Highscore h : data) {
                if(h.getSong().getName() != null  && !h.getSong().getName().equals(song.getName())) {
                    writer.write(h.toString() + "\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(writer != null) writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
}