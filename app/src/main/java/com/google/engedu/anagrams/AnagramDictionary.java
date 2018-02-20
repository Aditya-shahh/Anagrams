/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Arrays;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    ArrayList<String> wordlist=new ArrayList<>();
    HashSet<String> wordset = new HashSet<String>();
    HashMap<String, ArrayList<String>> lettersToWord = new HashMap<>();
    HashMap<Integer,ArrayList<String>> sizeToWords=new HashMap<>();
        int wordlength=DEFAULT_WORD_LENGTH;
    String word;

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;


        while((line = in.readLine()) != null) {
            word = line.trim();
            wordlist.add(line);
            wordset.add(word);
            int key=word.length();
            if(sizeToWords.containsKey(key))
            {
                ArrayList<String> sizewords=sizeToWords.get(key);
                sizewords.add(word);
                sizeToWords.put(key,sizewords);

            }
            else
            {
                ArrayList<String> sizewords=new ArrayList<>();
                sizewords.add(word);
                sizeToWords.put(key,sizewords);
            }

            if(lettersToWord.containsKey(sortLetters(word)))
            {

                ArrayList<String> anagram =lettersToWord.get(sortLetters(word));
                anagram.add(word);
                lettersToWord.put(sortLetters(word),anagram);
            }

            else
            {
                ArrayList<String> anagram=new ArrayList<>();
                anagram.add(word);
                lettersToWord.put(sortLetters(word),anagram);
            }

        }



    }

    public boolean isGoodWord(String word, String base) {

        String check=base;

        if(wordset.contains(word) && !word.contains(base))
        return true;
        else
        {
            return false;

        }


    }

  /*  public List<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        for(int i=0;i<wordlist.size();i++)
        {
            if(wordlist.get(i).length()==targetWord.length())
            {
                String sortedword=sortLetters(wordlist.get(i));
                String sortedTargetWord=sortLetters(targetWord);
                if(sortedTargetWord.equals(sortedword))
                {
                    result.add(wordlist.get(i));

                }

            }

        }

        return result;
    }

    */
    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        for(char letter='a';letter<='z';letter++)
        {
            if(lettersToWord.containsKey(sortLetters(word+letter)))
            {
                ArrayList<String>listanagrams=lettersToWord.get(sortLetters(word+letter));

                        for(int i=0;i<listanagrams.size();i++)
                        {

                            if(isGoodWord(word,listanagrams.get(i)))
                                result.add(listanagrams.get(i));
                        }

            }


        }
        return result;
    }

    public String pickGoodStarterWord() {

        Random r= new Random();
        int i=r.nextInt(wordlist.size());
        String goodword;

        ArrayList<String> anagram;

        for(int j=i;j<wordlist.size();j++)

        {
         if(lettersToWord.containsKey(sortLetters(wordlist.get(j))))
         {
                 anagram = lettersToWord.get(sortLetters(wordlist.get(j)));
                 if (anagram.size() >= MIN_NUM_ANAGRAMS) {
                     goodword = wordlist.get(i);
                     if(wordlength<MAX_WORD_LENGTH)
                         wordlength++;

                     return goodword;
                 }
             }
         }




        return "stop";
    }


    public String sortLetters(String word)
    {

    char c[]=word.toCharArray();
    Arrays.sort(c);
    String sortedword=new String(c);
    return sortedword;

    }
}
