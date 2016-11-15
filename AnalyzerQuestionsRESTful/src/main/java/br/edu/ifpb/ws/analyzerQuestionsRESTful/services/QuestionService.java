package br.edu.ifpb.ws.analyzerQuestionsRESTful.services;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpb.ws.analyzerQuestionsRESTful.entities.Question;
import br.edu.ifpb.ws.analyzerQuestionsRESTful.util.data.ReaderQuestions;

/**
 * 
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 *
 */
public class QuestionService {
	
	private final String CSV_READ = "perguntas.csv"; 
	private final String CSV_300_QUESTION = "perguntasOrder.csv"; 

	private AnalyzerQuestionSuggestion analyzer;
	private AnalyzerQuestion analyzerQuestion;

	public QuestionService() {
		analyzer = new AnalyzerQuestionSuggestion();
		analyzerQuestion = new AnalyzerQuestion();
	}

	public List<Question> getQuestions() {
		ReaderQuestions rq = new ReaderQuestions(CSV_300_QUESTION);
		List<Question> questions = rq.readCsvFile();

		return questions;
	}
	
	public List<Question> ordenedQuestions(){
		
		List<Question> goodQuestions = new ArrayList<>();
		List<Question> badQuestions = new ArrayList<>();
		int cont = 0;
		
		ReaderQuestions rq = new ReaderQuestions(CSV_READ);
		List<Question> questions = rq.readCsvFile();

		for (Question q : questions) {
			if(goodQuestion(q)==1){
				goodQuestions.add(q);
			}else{
				badQuestions.add(q);
			}
		}
		
		questions = new ArrayList<>();
		
		for(int i = 0 ; i<300 ;i++){
			if(cont==11){
				cont =0;
			}
			
			if(cont<6){
				questions.add(goodQuestions.get(i));
			}else if(cont>5){
				questions.add(badQuestions.get(i));
			}
			
			cont++;
		}

		return questions;
	}
	
	public Integer goodQuestionAverage(){
		ReaderQuestions rq = new ReaderQuestions(CSV_300_QUESTION);
		List<Question> questions = rq.readCsvFile();

		int cont = 0;
		for (Question q : questions) {
			if(goodQuestion(q)==1){
				cont++;
			}
		}

		return ((cont*100)/questions.size());
	}

	private Integer goodQuestion(Question question) {
		return analyzerQuestion.getGoodQuestionAnalyzer(question);
	}

	public List<String> getAnalize(Question question) {
		return analyzer.getSuggestions(question);
	}
	
	
	public static void main(String[] args) {
		QuestionService qu = new QuestionService();
		System.out.println(qu.goodQuestionAverage());
	}
}
