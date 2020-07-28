package de.hsrm.mi.eibo.persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hsrm.mi.eibo.business.tone.Song;

public class SongPersitinator implements DataPersistinator<Song> {

    private final String dataPath = "songs.txt";

    @Override
    public void saveData(List<Song> data) {
        StringBuilder sb = new StringBuilder();
        BufferedWriter writer = null;
        for(Song s : data) {
            sb.append(s.toString()).append("\n");
        }
        try {   
            writer = new BufferedWriter(new FileWriter(dataPath));
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
            writer = new BufferedWriter(new FileWriter(dataPath));
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
    public List<Song> loadData() {
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
    
}