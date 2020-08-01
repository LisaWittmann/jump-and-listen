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
    private BufferedReader reader = null;
    private BufferedWriter writer = null;

    @Override
    public void saveAll(List<Song> data) {
        StringBuilder sb = new StringBuilder();
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
    public List<Song> loadAll() {
        List<Song> loaded = new ArrayList<>();
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

    @Override
    public void saveData(Song data) {
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
                if(reader != null) reader.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        return song;
    }

    public List<Song> loadByLevel(Level level) {
        List<Song> levelSongs = new ArrayList<>();
        for(Song s : loadAll()) {
            if(s.getLevel().equals(level)) {
                levelSongs.add(s);
            }
        }
        return levelSongs;
    }
    
    public Song loadByName(String name) {
        for(Song s : loadAll()) {
            if(s.getName().equals(name)) return s;
        }
        return null;
    }

    public boolean nameAccepted(String name) {
        for(Song s : loadAll()) {
            if(s.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }
    
}