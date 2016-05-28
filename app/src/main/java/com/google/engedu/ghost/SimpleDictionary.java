package com.google.engedu.ghost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

public class SimpleDictionary implements GhostDictionary {
    private ArrayList<String> words;

    public SimpleDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        words = new ArrayList<>();
        String line = null;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
              words.add(line.trim());
        }
    }

    @Override
    public boolean isWord(String word) {
        return words.contains(word);
    }

    @Override
    public String getAnyWordStartingWith(String prefix)
    {
        String temp;
        int l=0,mid;
        int h=words.size();

        while(l<=h)
        {
            mid=(l+h)/2;
            if(words.get(mid).toLowerCase().startsWith(prefix.toLowerCase())||(words.get(mid).toLowerCase().compareTo(prefix.toLowerCase())==0))
            {
                return words.get(mid);
            }
            else if(words.get(mid).toLowerCase().compareTo(prefix.toLowerCase())<0)
            {
                l=mid+1;
            }
            else
            {
                h=mid-1;
            }
        }
        return null;
    }

    @Override
    public String getGoodWordStartingWith(String prefix) {
        return null;
    }
}
