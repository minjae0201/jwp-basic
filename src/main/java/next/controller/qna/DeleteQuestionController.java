package next.controller.qna;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;

public class DeleteQuestionController implements Controller {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Long questionId = Long.parseLong(req.getParameter("questionId"));
		
		AnswerDao answerDao = new AnswerDao();
		List<Answer> answers = answerDao.findAll(questionId);
		for(Answer answer : answers) {
			answerDao.delete(answer.getAnswerId());
		}
		
		QuestionDao questionDao = new QuestionDao();
		questionDao.delete(questionId);
		
		return "redirect:/";
	}

}
