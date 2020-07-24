package de.hsrm.mi.eibo.persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HighscorePersistinator implements DataPersistinator<Integer> {

    private final String dataPath;

    public HighscorePersistinator() {
        this.dataPath = getClass().getResource("/savings/highscores.csv").toExternalForm();
    }

    @Override
    public void saveData(List<Integer> data) {
        StringBuilder sb = new StringBuilder();
        for(int i : data) {
            sb.append(String.valueOf(i)).append("\n");
        }
        
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(dataPath, true)); 
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Integer> loadData() {
        List<Integer> highscores = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(dataPath));
            String line;
            while((line = reader.readLine()) != null) {
                highscores.add(Integer.parseInt(line));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return highscores;
    }
    
}