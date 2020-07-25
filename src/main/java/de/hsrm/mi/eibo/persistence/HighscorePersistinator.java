package de.hsrm.mi.eibo.persistence;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HighscorePersistinator implements DataPersistinator<Integer> {

    private final String dataPath = System.getProperty("user.home") + "/highscores.dat";

    @Override
    public void saveData(List<Integer> data) {
        DataOutputStream output = null;
        try {
            output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(dataPath)));
            for(int i : data) {
                output.writeInt(i);
            }
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(output != null) output.close();
            } catch(IOException e) {
                e.printStackTrace(); 
            }
        }
    }

    @Override
    public List<Integer> loadData() {
        List<Integer> loaded = new ArrayList<>();
        DataInputStream input = null;
        try {
            input = new DataInputStream(new BufferedInputStream(new FileInputStream(dataPath)));
            while(input.available() > 0) {
                loaded.add(input.readInt());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(input != null) input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return loaded;
    }

    @Override
    public void saveData(Integer data) {
        DataOutputStream output = null;
        try {
            output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(dataPath)));
            output.writeInt(data);
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(output != null)output.close();
            } catch(IOException e) {
                e.printStackTrace(); 
            }
        }
    }
    
}