package de.hsrm.mi.eibo.persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import de.hsrm.mi.eibo.business.gamelogic.Level;
import de.hsrm.mi.eibo.business.tone.Song;

public class SongPersitinator implements DataPersistinator<Song> {

    private final String dataPath = "songs.txt";

    public void saveData(List<Song> data) {
        StringBuilder sb = new StringBuilder();
        BufferedWriter writer = null;
        for(Song s : data) {
            sb.append(s.toString()).append("\n");
        }
        try {   
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dataPath, true)));
            writer.write(sb.toString());
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
    public void saveData(Song data) {
        BufferedWriter writer = null;
        try {   
            writer =  new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dataPath, true)));
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

    public List<Song> loadListData() {
        List<Song> loaded = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(dataPath));
            String line;
            while((line = reader.readLine()) != null) {
                loaded.add(new Song(line));
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

    public Song loadByName(String name) {
        for(Song s : loadListData()) {
            if(s.getName().equals(name)) return s;
        }
        return null;
    }

    public List<Song> loadByLevel(Level level) {
        List<Song> levelSongs = new ArrayList<>();
        for(Song s : loadListData()) {
            if(s.getLevel().equals(level)) {
                levelSongs.add(s);
            }
        }
        return levelSongs;
    }

    @Override
    public Song loadData() {
        BufferedReader reader = null;
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
                if(reader != null) reader.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        return song;
    }
    
}