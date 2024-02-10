package tools;

public class Flashcard {
	private String word;
	private String meaning;

	public Flashcard(String w, String m) {
		this.word=w;
		this.meaning=m;
		}
	public String getWord() {
		return word;
	}

	public String getMeaning() {
		return meaning;
	}

}
