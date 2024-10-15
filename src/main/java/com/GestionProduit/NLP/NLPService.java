package com.GestionProduit.NLP;

import opennlp.tools.tokenize.SimpleTokenizer;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class NLPService {

  public List<String> extractKeywords(String input) {
    SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
    String[] tokens = tokenizer.tokenize(input);
    return Arrays.asList(tokens);
  }

  public String analyzeSentiment(String review) {
    List<String> positiveWords = Arrays.asList("good", "great", "excellent", "amazing", "love", "fantastic");
    List<String> negativeWords = Arrays.asList("bad", "terrible", "awful", "hate", "worst", "disappointed");

    int score = 0;

    for (String word : review.toLowerCase().split("\\s+")) {
      if (positiveWords.contains(word)) {
        score++;
      } else if (negativeWords.contains(word)) {
        score--;
      }
    }

    if (score > 0) {
      return "Positive";
    } else if (score < 0) {
      return "Negative";
    } else {
      return "Neutral";
    }
  }

}
