package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Public extends Controller {
	
	public static Result index() {
		return ok(index.render("Howdy"));
	}
	
}
