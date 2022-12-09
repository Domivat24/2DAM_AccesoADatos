package AccesoAFicheros;

public class Book {
	private String author;
	private String title;
	private String genre;
	private String price;
	private String publish_date;
	private String description;


	@Override
	public String toString() {
		return String.format("Title: %s by the author:%s. A novel released on the %s, priced at %s and from the genre of %s. Short description:%s\n", title, author, publish_date, price, genre, description);
	}

	public Book(String[] values) {
		author = values[0];
		title = values[1];
		genre = values[2];
		price = values[3];
		publish_date = values[4];
		description = values[5];
	}
}
