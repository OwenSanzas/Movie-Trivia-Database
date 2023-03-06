import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import file.MovieDB;
import movies.Actor;
import movies.Movie;

/**
 * made by Ze Sheng
 */


/**
 * Movie trivia class providing different methods for querying and updating a movie database.
 */
public class MovieTrivia {
	
	/**
	 * Create instance of movie database
	 */
	MovieDB movieDB = new MovieDB();
	
	
	public static void main(String[] args) {
		
		//create instance of movie trivia class
		MovieTrivia mt = new MovieTrivia();
		
		//setup movie trivia class
		mt.setUp("moviedata.txt", "movieratings.csv");
	}
	
	/**
	 * Sets up the Movie Trivia class
	 * @param movieData .txt file
	 * @param movieRatings .csv file
	 */
	public void setUp(String movieData, String movieRatings) {
		//load movie database files
		movieDB.setUp(movieData, movieRatings);
		
		//print all actors and movies
		this.printAllActors();
		this.printAllMovies();		
	}
	
	/**
	 * Prints a list of all actors and the movies they acted in.
	 */
	public void printAllActors () {
		System.out.println(movieDB.getActorsInfo());
	}
	
	/**
	 * Prints a list of all movies and their ratings.
	 */
	public void printAllMovies () {
		System.out.println(movieDB.getMoviesInfo());
	}


	// TODO add additional methods as specified in the instructions PDF

	public String changeLetters(String name) {
		return name.trim().toLowerCase();
	}


	/**
	 * Inserts (or updates) given actor and his/her movies into database
	 * @param actorsInfo is the ArrayList that is to be inserted into/updated
	 * @param actor is the actor name as a string
	 * @param movies is a String array of movie names that the actor has acted in
	 */
	public void insertActor(String actor, String[] movies, ArrayList<Actor> actorsInfo) {
		// change the name to lower case letters
		String actorName = changeLetters(actor);

		// create an instance of Actor
		Actor newActor = new Actor(actor);

		// insert the info
		for (Actor actor1 : actorsInfo) {
			// if this actor is in the list, add all different movies:
			if (actor1.getName().equals(actorName)) {
				for (String movie : movies) {
					if (!actor1.getMoviesCast().contains(changeLetters(movie))) {
						actor1.getMoviesCast().add(changeLetters(movie));
					}
				}
				return;
			}
		}

		// if this actor is not in the list, add all movies and add the actor in the Info:
		for (String movie : movies) {
			newActor.getMoviesCast().add(changeLetters(movie));
		}
		actorsInfo.add(newActor);
	}


	/**
	 * Inserts given ratings for given movie into database
	 * @param moviesInfo is the ArrayList that is to be inserted into/updated
	 * @param movie is the movie name as a string
	 * @param ratings is an integer array with 2 elements: the critics rating at index 0 and the audience rating at index 1
	 */
	public void insertRating (String movie, int [] ratings, ArrayList <Movie> moviesInfo) {
		// check whether the input are legal:
		if (ratings == null || ratings.length != 2) {
			return;
		}

		if ((ratings[0] < 0 || ratings[0] > 100) || (ratings[1] < 0 || ratings[1] > 100)) {
			return;
		}

		// if the inputs are legal:
		movie = changeLetters(movie);

		// create a new instance of Movie
		Movie newMovie = new Movie(movie, ratings[0], ratings[1]);

		// insert the info:
		for (Movie movie1 : moviesInfo) {
			if (movie1.getName().equals(movie)) {
				movie1.setCriticRating(ratings[0]);
				movie1.setAudienceRating(ratings[1]);
				return;
			}
		}

		// if the movie is not in the list:
		moviesInfo.add(newMovie);
	}


	/**
	 * Given an actor, returns the list of all movies
	 * @param actor is the name of an actor as a String
	 * @param actorsInfo is the ArrayList to get the data from
	 * @return movieList is a list of all movies
	 */
	public ArrayList <String> selectWhereActorIs (String actor, ArrayList <Actor> actorsInfo) {
		// create a list of the output movies:
		ArrayList<String> movieList = new ArrayList<>();
		actor = changeLetters(actor);

		// find this actor:
		for (Actor actor1 : actorsInfo) {
			if (actor1.getName().equals(actor)) {
				movieList.addAll(actor1.getMoviesCast());
			}
		}
		return movieList;
	}


	/**
	 * Given a movie, returns the list of all actors in that movie
	 * @param movie is the name of a movie as a String
	 * @param actorsInfo is the ArrayList to get the data from
	 * @return actorList is a list of all actors
	 */
	public ArrayList <String> selectWhereMovieIs (String movie, ArrayList <Actor> actorsInfo) {
		// create a list of the output actors:
		ArrayList<String> actorList = new ArrayList<>();
		movie = changeLetters(movie);

		// find this movie:
		for (Actor actor : actorsInfo) {
			if (actor.getMoviesCast().contains(movie)) {
				actorList.add(actor.getName());
			}
		}
		return actorList;
	}

	/**
	 * @param comparison is either ‘=’, ‘>’, or ‘< ‘ and is passed in as a char
	 * @param isCritic is a boolean that represents whether we are interested in the critics’ rating or the audience rating. true = critic ratings, false = audience ratings.
	 * @param targetRating is an integer
	 * @return movieList is a list of movies that satisfy an inequality or equality, based on the comparison argument and the targeted rating argument
	 */
	public ArrayList <String> selectWhereRatingIs (char comparison, int targetRating, boolean isCritic,
												   ArrayList <Movie> moviesInfo) {
		// create a list of all movies satisfying the requirements
		ArrayList<String> movieList = new ArrayList<>();

		// corner cases:
		if (comparison != '<' && comparison != '>' && comparison != '=') {
			return movieList;
		}

		if (targetRating > 100 || targetRating < 0) {
			return movieList;
		}

		// for critical score:
		if (isCritic) {
			for (Movie movie : moviesInfo) {
				if (comparison == '>') {
					if (movie.getCriticRating() > targetRating) {
						movieList.add(movie.getName());
					}
				} else if (comparison == '=') {
					if (movie.getCriticRating() == targetRating) {
						movieList.add(movie.getName());
					}
				} else {
					if (movie.getCriticRating() < targetRating) {
						movieList.add(movie.getName());
					}
				}
			}
		} else { // for audience score:
			for (Movie movie : moviesInfo) {
				if (comparison == '>') {
					if (movie.getAudienceRating() > targetRating) {
						movieList.add(movie.getName());
					}
				} else if (comparison == '=') {
					if (movie.getAudienceRating() == targetRating) {
						movieList.add(movie.getName());
					}
				} else {
					if (movie.getAudienceRating() < targetRating) {
						movieList.add(movie.getName());
					}
				}
			}
		}
		return movieList;
	}


	/**
	 *  Returns a list of all actors that the given actor has ever worked with in any movie except the actor herself/himself.
	 *  @param actor is the name of an actor as a String
	 *  @param actorsInfo is the ArrayList to search through
	 *  @return coActorList is a list of all actors that the given actor has ever worked with in any movie except the actor herself/himself.
	 */
	public ArrayList <String> getCoActors (String actor, ArrayList <Actor> actorsInfo) {
		// create coActorList:
		ArrayList<String> coActorList = new ArrayList<>();
		actor = changeLetters(actor);
		ArrayList<String> movies = new ArrayList<>();
		boolean flag = false;

		// find this actor his/her movies:
		for (Actor actor1 : actorsInfo) {
			if (actor1.getName().equals(actor)) {
				// now get all movies he/she casts:
				movies = actor1.getMoviesCast();
				flag = true;
			}
		}

		if (!flag) {
			// if this actor is not in the list:
			return coActorList;
		}

		// if other actor has a same movie, add into the list:
		for (Actor actor1 : actorsInfo) {
			if (!actor1.getName().equals(actor)) {
				for (String movie : actor1.getMoviesCast()) {
					if (movies.contains(movie)) {
						coActorList.add(actor1.getName());
						break;
					}
				}
			}
		}

		return coActorList;
	}


	/**
	 * Returns a list of movie names where both actors were cast
	 * @param actor1 and actor2 are actor names as Strings
	 * @param actorsInfo is the ArrayList to search through
	 * @return movieList is a list of movie names where both actors were cast
	 */
	public ArrayList <String> getCommonMovie (String actor1, String actor2, ArrayList <Actor> actorsInfo) {
		// create the ArrayList for movies:
		ArrayList<String> movieList = new ArrayList<>();
		actor1 = changeLetters(actor1);
		actor2 = changeLetters(actor2);

		// get two lists of their movies:
		ArrayList <String> movies1 = selectWhereActorIs(actor1, actorsInfo);
		ArrayList <String> movies2 = selectWhereActorIs(actor2, actorsInfo);

		// corner case:
		if (movies1 == null || movies2 == null) {
			return movieList;
		}

		// find common movies:
		for (String m : movies1) {
			if (movies2.contains(m)) {
				movieList.add(m);
			}
		}
		return movieList;
	}


	/**
	 * Returns a list of movie names that both critics and the audience have rated above 85
	 * @param moviesInfo is a list of all movies
	 * @return goodMovieList is a list of movie names that both critics and the audience have rated above 85
	 */
	public ArrayList <String> goodMovies (ArrayList <Movie> moviesInfo) {
		ArrayList<String> goodMovieList = new ArrayList<>();

		for (Movie movie : moviesInfo) {
			if (movie.getAudienceRating() >= 85 && movie.getCriticRating() >= 85) {
				goodMovieList.add(movie.getName());
			}
		}

		return goodMovieList;
	}


	/**
	 * 	Given a pair of movies, this method returns a list of actors that acted in both movies.
	 *  @param movie1 and movie2 are the names of movies as Strings
	 *  @param actorsInfo is the actor ArrayList
	 *  @return actorList is a list of all common actors
	 */
	public ArrayList <String> getCommonActors (String movie1, String movie2,
											   ArrayList <Actor> actorsInfo) {
		// create a list for all common actors
		ArrayList<String> actorList = new ArrayList<>();
		movie1 = changeLetters(movie1);
		movie2 = changeLetters(movie2);

		ArrayList <String> actors1 = selectWhereMovieIs(movie1, actorsInfo);
		ArrayList <String> actors2 = selectWhereMovieIs(movie2, actorsInfo);

		// corner case:
		if (actors1 == null || actors2 == null) {
			return actorList;
		}

		// find common actors:
		for (String a : actors1) {
			if (actors2.contains(a)) {
				actorList.add(a);
			}
		}

		return actorList;
	}


	/**
	 * Given the moviesInfo DB, this static method returns the mean value of the critics’ ratings and the audience ratings.
	 * @param moviesInfo is the ArrayList of all movies
	 * @return meanArray is an array of mean critics' score and mean audience's score
	 */
	public static double [] getMean (ArrayList <Movie> moviesInfo) {
		// meanArray:
		double[] meanArray = new double[2];
		double criticMean = 0;
		double audienceMean = 0;

		// Calculate the mean values:
		for (Movie movie : moviesInfo) {
			criticMean += movie.getCriticRating();
			audienceMean += movie.getAudienceRating();
		}

		meanArray[0] = criticMean/moviesInfo.size();
		meanArray[1] = audienceMean/moviesInfo.size();

		return meanArray;
	}
}
