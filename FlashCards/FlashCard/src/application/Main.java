package application;
	
import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tools.Flashcard;


public class Main extends Application {
	
	private ArrayList<Flashcard> flashcards;
	private int score;
	private int index;
	
	
	@Override
	public void start(Stage primaryStage) {
		this.flashcards= new ArrayList<>();
		this.score = 0;
		this.index = 0;
		
		
		Label questionLabel = new Label("Please enter the question");
		questionLabel.setTextFill(Color.BROWN);
		TextField questionField  = new TextField();
		Label answerLabel = new Label("Please enter the answer");
		answerLabel.setTextFill(Color.BROWN);
		TextField answerField  = new TextField();
		Button addButton = new Button("Add flashcard");
		addButton.setTextFill(Color.CHOCOLATE);
		addButton.setOnAction(event ->{
			String w = questionField.getText();
			String m = answerField.getText();
			addFlashcard(w,m);
			
			questionField.clear();
			answerField.clear();
			showAlert(Alert.AlertType.INFORMATION,"Flashcard added!","you've successfully added the flashcard");
		});
		
		VBox flashCardForm = new VBox(10, questionLabel, questionField, answerLabel, answerField,addButton);
		flashCardForm.setPadding(new Insets(10));
		flashCardForm.setAlignment(Pos.CENTER);
		Label questionText = new Label();
		TextField answerText = new TextField();
		Button nextButton = new Button("Next");
		nextButton.setTextFill(Color.CHOCOLATE);
		nextButton.setOnAction(event ->{
		isCorrect(answerText.getText(), questionText,answerText,nextButton);});
		VBox gameBox = new VBox(10, answerText, nextButton);
		gameBox.setAlignment(Pos.CENTER);
		VBox gameView = new VBox(20,questionText,gameBox);
		gameView.setPadding(new Insets(10));
		gameView.setAlignment(Pos.CENTER);
		
		TabPane tabPane = new TabPane();
		Tab addTab = new Tab("Add Flashcards", flashCardForm);
		Tab gameTab= new Tab("Start Quiz", gameView);
		gameTab.setOnSelectionChanged(event ->{
			  if (index == 0 && questionText.getText().isEmpty()) {
			        updateQuestion(flashcards.get(index).getWord(), questionText, answerText);
			    }
		});
		tabPane.getTabs().addAll(addTab,gameTab);
		tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
		
		BackgroundFill backgroundFill = new BackgroundFill(Color.BEIGE,new CornerRadii(10),new Insets(10));

		Background background = new Background(backgroundFill);
		
		tabPane.setBackground(background);
		
		Scene scene = new Scene(tabPane,400,300);
		primaryStage.setTitle("Flashcard App");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public void addFlashcard(String w, String m) {
			Flashcard f = new Flashcard(w,m);
			flashcards.add(f);

	}
	
	public void updateQuestion(String w, Label q, TextField a) {
		q.setText((index+1)+") The quesion is:\n"+w);
		a.clear();
		
	}
	
	
	public void showAlert(Alert.AlertType alert, String title, String content) {
		Alert alTy = new Alert(alert);
		alTy.setTitle(title);
		alTy.setHeaderText(null);
		alTy.setContentText(content);
		alTy.showAndWait();
	}
	
	
	public void isCorrect(String input, Label q, TextField a, Button b) {
		Flashcard flashcard = flashcards.get(index);
		
		if(input.equalsIgnoreCase(flashcard.getMeaning())) {
			score++;
			showAlert(Alert.AlertType.INFORMATION,"Correct!","Your answer is correct! \n Your score is "+ score+" so far");
		}
		else
			showAlert(Alert.AlertType.INFORMATION,"Wrong!","Your answer is wrong. \n The correct answer is " + flashcard.getMeaning()+ "\n Your score is "+ score+" so far");
		if(index<flashcards.size()-1)	{
			Flashcard nextFlash = flashcards.get(index+1);
			updateQuestion(nextFlash.getWord(),q,a);
		}
		if(index==flashcards.size()-1) {
			showAlert(Alert.AlertType.INFORMATION,"Quiz Completed!","Your score is "+ score+"/"+flashcards.size());
			b.setDisable(true);
			return;
		}
		index++;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
