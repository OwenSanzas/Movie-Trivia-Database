import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import file.MovieDB;

class MovieTriviaTest {

	// instance of movie trivia object to test
	MovieTrivia mt;
	// instance of movieDB object
	MovieDB movieDB;

	@BeforeEach
	void setUp() throws Exception {
		// initialize movie trivia object
		mt = new MovieTrivia();

		// set up movie trivia object
		mt.setUp("moviedata.txt", "movieratings.csv");

		// get instance of movieDB object from movie trivia object
		movieDB = mt.movieDB;
	}

	@Test
	void testSetUp() {
		assertEquals(6, movieDB.getActorsInfo().size(),
				"actorsInfo should contain 6 actors after reading moviedata.txt.");
		assertEquals(7, movieDB.getMoviesInfo().size(),
				"moviesInfo should contain 7 movies after reading movieratings.csv.");

		assertEquals("meryl streep", movieDB.getActorsInfo().get(0).getName(),
				"\"meryl streep\" should be the name of the first actor in actorsInfo.");
		assertEquals(3, movieDB.getActorsInfo().get(0).getMoviesCast().size(),
				"The first actor listed in actorsInfo should have 3 movies in their moviesCasted list.");
		assertEquals("doubt", movieDB.getActorsInfo().get(0).getMoviesCast().get(0),
				"\"doubt\" should be the name of the first movie in the moviesCasted list of the first actor listed in actorsInfo.");

		assertEquals("doubt", movieDB.getMoviesInfo().get(0).getName(),
				"\"doubt\" should be the name of the first movie in moviesInfo.");
		assertEquals(79, movieDB.getMoviesInfo().get(0).getCriticRating(),
				"The critics rating for the first movie in moviesInfo is incorrect.");
		assertEquals(78, movieDB.getMoviesInfo().get(0).getAudienceRating(),
				"The audience rating for the first movie in moviesInfo is incorrect.");
	}

	@Test
	void testInsertActor() {

		// try to insert new actor with new movies
		mt.insertActor("test1", new String[] { "testmovie1", "testmovie2" }, movieDB.getActorsInfo());
		assertEquals(7, movieDB.getActorsInfo().size(),
				"After inserting an actor, the size of actorsInfo should have increased by 1.");
		assertEquals("test1", movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getName(),
				"After inserting actor \"test1\", the name of the last actor in actorsInfo should be \"test1\".");
		assertEquals(2, movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getMoviesCast().size(),
				"Actor \"test1\" should have 2 movies in their moviesCasted list.");
		assertEquals("testmovie1",
				movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getMoviesCast().get(0),
				"\"testmovie1\" should be the first movie in test1's moviesCasted list.");

		// try to insert existing actor with new movies
		mt.insertActor("   Meryl STReep      ", new String[] { "   DOUBT      ", "     Something New     " },
				movieDB.getActorsInfo());
		assertEquals(7, movieDB.getActorsInfo().size(),
				"Since \"meryl streep\" is already in actorsInfo, inserting \"   Meryl STReep      \" again should not increase the size of actorsInfo.");

		// look up and inspect movies for existing actor
		// note, this requires the use of properly implemented selectWhereActorIs method
		// you can comment out these two lines until you have a selectWhereActorIs
		// method
		assertEquals(4, mt.selectWhereActorIs("meryl streep", movieDB.getActorsInfo()).size(),
				"After inserting Meryl Streep again with 2 movies, only one of which is not on the list yet, the number of movies \"meryl streep\" appeared in should be 4.");
		assertTrue(mt.selectWhereActorIs("meryl streep", movieDB.getActorsInfo()).contains("something new"),
				"After inserting Meryl Streep again with a new Movie \"     Something New     \", \"somenthing new\" should appear as one of the movies she has appeared in.");

		// insert the actor with no movies
		mt.insertActor("Wenyu", new String[] {},movieDB.getActorsInfo());
		assertEquals(8,movieDB.getActorsInfo().size());
		assertEquals("Wenyu",movieDB.getActorsInfo().get(movieDB.getActorsInfo().size()-1).getName());



	}

	@Test
	void testInsertRating() {

		// try to insert new ratings for new movie
		mt.insertRating("testmovie", new int[] { 79, 80 }, movieDB.getMoviesInfo());
		assertEquals(8, movieDB.getMoviesInfo().size(),
				"After inserting ratings for a movie that is not in moviesInfo yet, the size of moviesInfo should increase by 1.");
		assertEquals("testmovie", movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() - 1).getName(),
				"After inserting a rating for \"testmovie\", the name of the last movie in moviessInfo should be \"testmovie\".");
		assertEquals(79, movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() - 1).getCriticRating(),
				"The critics rating for \"testmovie\" is incorrect.");
		assertEquals(80, movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() - 1).getAudienceRating(),
				"The audience rating for \"testmovie\" is incorrect.");

		// try to insert new ratings for existing movie
		mt.insertRating("doubt", new int[] { 100, 100 }, movieDB.getMoviesInfo());
		assertEquals(8, movieDB.getMoviesInfo().size(),
				"Since \"doubt\" is already in moviesInfo, inserting ratings for it should not increase the size of moviesInfo.");

		// look up and inspect movies based on newly inserted ratings
		// note, this requires the use of properly implemented selectWhereRatingIs
		// method
		// you can comment out these two lines until you have a selectWhereRatingIs
		// method
		assertEquals(1, mt.selectWhereRatingIs('>', 99, true, movieDB.getMoviesInfo()).size(),
				"After inserting a critic rating of 100 for \"doubt\", there should be 1 movie in moviesInfo with a critic rating greater than 99.");
		assertTrue(mt.selectWhereRatingIs('>', 99, true, movieDB.getMoviesInfo()).contains("doubt"),
				"After inserting the rating for \"doubt\", \"doubt\" should appear as a movie with critic rating greater than 99.");

		// insert incorrect number
		mt.insertRating("pig", new int[] {-1,79}, movieDB.getMoviesInfo());
		assertEquals(8,movieDB.getMoviesInfo().size());
		assertEquals("doubt",movieDB.getMoviesInfo().get(0).getName());
		assertEquals(80,movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size()-1).getAudienceRating());

	}

	@Test
	void testSelectWhereActorIs() {
		assertEquals(3, mt.selectWhereActorIs("meryl streep", movieDB.getActorsInfo()).size(),
				"The number of movies \"meryl streep\" has appeared in should be 3.");
		assertEquals("doubt", mt.selectWhereActorIs("meryl streep", movieDB.getActorsInfo()).get(0),
				"\"doubt\" should show up as first in the list of movies \"meryl streep\" has appeared in.");

		// actor exist
		assertEquals(3,mt.selectWhereActorIs("Tom Hanks",movieDB.getActorsInfo()).size());
		assertEquals("catch me if you can",mt.selectWhereActorIs("Tom Hanks", movieDB.getActorsInfo()).get(1));
		//the actor does not exist
		assertEquals(0,mt.selectWhereActorIs("Wenyu", movieDB.getActorsInfo()).size());
		//actor exist,but no movie
		assertEquals(0,mt.selectWhereActorIs("Brandon Krakowsky", movieDB.getActorsInfo()).size());
	}

	@Test
	void testSelectWhereMovieIs() {
		assertEquals(2, mt.selectWhereMovieIs("doubt", movieDB.getActorsInfo()).size(),
				"There should be 2 actors in \"doubt\".");
		assertEquals(true, mt.selectWhereMovieIs("doubt", movieDB.getActorsInfo()).contains("meryl streep"),
				"\"meryl streep\" should be an actor who appeared in \"doubt\".");
		assertEquals(true, mt.selectWhereMovieIs("doubt", movieDB.getActorsInfo()).contains("amy adams"),
				"\"amy adams\" should be an actor who appeared in \"doubt\".");


		//only one actor act in this movie
		assertEquals(1,mt.selectWhereMovieIs("Catch Me If You Can", movieDB.getActorsInfo()).size());
		assertEquals(true,mt.selectWhereMovieIs("Catch Me If You Can", movieDB.getActorsInfo()).contains("tom hanks"));
		assertEquals(false,mt.selectWhereMovieIs("Catch Me If You Can", movieDB.getActorsInfo()).contains("amy adams"));

		//non exist movie
		assertEquals(0,mt.selectWhereMovieIs("911", movieDB.getActorsInfo()).size());

		//not case sensitive
		assertEquals(2,mt.selectWhereMovieIs("the post",movieDB.getActorsInfo()).size());
		assertEquals(true,mt.selectWhereMovieIs("The Post", movieDB.getActorsInfo()).contains("tom hanks"));
		assertEquals(false,mt.selectWhereMovieIs("The Post", movieDB.getActorsInfo()).contains("amy adams"));
	}

	@Test
	void testSelectWhereRatingIs() {
		assertEquals(6, mt.selectWhereRatingIs('>', 0, true, movieDB.getMoviesInfo()).size(),
				"There should be 6 movies where critics rating is greater than 0.");
		assertEquals(0, mt.selectWhereRatingIs('=', 65, false, movieDB.getMoviesInfo()).size(),
				"There should be no movie where audience rating is equal to 65.");
		assertEquals(2, mt.selectWhereRatingIs('<', 30, true, movieDB.getMoviesInfo()).size(),
				"There should be 2 movies where critics rating is less than 30.");


		//non exist comparison
		assertEquals(0,mt.selectWhereRatingIs('?', 0, true, movieDB.getMoviesInfo()).size());

		//a target rating that is out of range
		assertEquals(0,mt.selectWhereRatingIs('>', 1000, true, movieDB.getMoviesInfo()).size());

		//correct comparison
		assertEquals(0,mt.selectWhereRatingIs('>', 98, true, movieDB.getMoviesInfo()).size());
		assertEquals(1,mt.selectWhereRatingIs('=', 95, false, movieDB.getMoviesInfo()).size());
		assertEquals(3,mt.selectWhereRatingIs('<', 80, true, movieDB.getMoviesInfo()).size());
	}

	@Test
	void testGetCoActors() {
		assertEquals(2, mt.getCoActors("meryl streep", movieDB.getActorsInfo()).size(),
				"\"meryl streep\" should have 2 co-actors.");
		assertTrue(mt.getCoActors("meryl streep", movieDB.getActorsInfo()).contains("tom hanks"),
				"\"tom hanks\" was a co-actor of \"meryl streep\".");
		assertTrue(mt.getCoActors("meryl streep", movieDB.getActorsInfo()).contains("amy adams"),
				"\"amy adams\" was a co-actor of \"meryl streep\".");


		//non exist actor
		assertEquals(0,mt.getCoActors("Wenyu", movieDB.getActorsInfo()).size());

		//with leading or trailing whitespace
		assertEquals(2,mt.getCoActors(" meryl streep ", movieDB.getActorsInfo()).size());
		assertTrue(mt.getCoActors("meryl streep ", movieDB.getActorsInfo()).contains("tom hanks"));
		assertTrue(mt.getCoActors(" meryl streep", movieDB.getActorsInfo()).contains("amy adams"));

		//not case sensitive
		assertEquals(2,mt.getCoActors(" Meryl Streep ", movieDB.getActorsInfo()).size());
		assertTrue(mt.getCoActors("meryl streep ", movieDB.getActorsInfo()).contains("tom hanks"));
		assertTrue(mt.getCoActors(" meryl streep", movieDB.getActorsInfo()).contains("amy adams"));
	}

	@Test
	void testGetCommonMovie() {
		assertEquals(1, mt.getCommonMovie("meryl streep", "tom hanks", movieDB.getActorsInfo()).size(),
				"\"tom hanks\" and \"meryl streep\" should have 1 movie in common.");
		assertTrue(mt.getCommonMovie("meryl streep", "tom hanks", movieDB.getActorsInfo()).contains("the post"),
				"\"the post\" should be a common movie between \"tom hanks\" and \"meryl streep\".");


		// non exist actor
		assertEquals(0,mt.getCommonMovie("Wenyu", "tom hanks", movieDB.getActorsInfo()).size());

		//with leading or trailing whitespace
		assertEquals(1,mt.getCommonMovie(" meryl streep ", "tom hanks",movieDB.getActorsInfo()).size());
		assertTrue(mt.getCommonMovie("meryl streep", "tom hanks", movieDB.getActorsInfo()).contains("the post"));

		//not case sensitive
		assertEquals(1,mt.getCommonMovie(" Meryl Streep ", "tom hanks", movieDB.getActorsInfo()).size());
		assertTrue(mt.getCommonMovie("meryl streep", "tom hanks", movieDB.getActorsInfo()).contains("the post"));
	}

	@Test
	void testGoodMovies() {
		assertEquals(3, mt.goodMovies(movieDB.getMoviesInfo()).size(),
				"There should be 3 movies that are considered good movies, movies with both critics and audience rating that are greater than or equal to 85.");
		assertTrue(mt.goodMovies(movieDB.getMoviesInfo()).contains("jaws"),
				"\"jaws\" should be considered a good movie, since it's critics and audience ratings are both greater than or equal to 85.");

		// TODO add additional test case scenarios
		assertEquals(3,mt.goodMovies(movieDB.getMoviesInfo()).size());
		assertTrue(mt.goodMovies(movieDB.getMoviesInfo()).contains("rocky ii"));

		//movie not good
		assertFalse(mt.goodMovies(movieDB.getMoviesInfo()).contains("Doubt"));

		//movie good
		assertTrue(mt.goodMovies(movieDB.getMoviesInfo()).contains("et"));
	}

	@Test
	void testGetCommonActors() {
		assertEquals(1, mt.getCommonActors("doubt", "the post", movieDB.getActorsInfo()).size(),
				"There should be one actor that appeared in both \"doubt\" and \"the post\".");
		assertTrue(mt.getCommonActors("doubt", "the post", movieDB.getActorsInfo()).contains("meryl streep"),
				"The actor that appeared in both \"doubt\" and \"the post\" should be \"meryl streep\".");

		// TODO add additional test case scenarios
		//non exist movie
		assertEquals(0,mt.getCommonActors("911", "the post", movieDB.getActorsInfo()).size());

		//movie 1 and movie 2 same
		assertEquals(2,mt.getCommonActors("doubt", "doubt", movieDB.getActorsInfo()).size());
		assertTrue(mt.getCommonActors("doubt", "doubt", movieDB.getActorsInfo()).contains("meryl streep"));

		//with leading or trailing whitespace
		assertEquals(1,mt.getCommonActors(" doubt","the post " , movieDB.getActorsInfo()).size());
		assertTrue(mt.getCommonActors("doubt "," the post",movieDB.getActorsInfo()).contains("meryl streep"));
	}

	@Test
	void testGetMean() {


		// TODO add ALL test case scenarios!
		assertEquals(67.85, MovieTrivia.getMean(movieDB.getMoviesInfo())[0],0.01);
		assertEquals(65.71,MovieTrivia.getMean(movieDB.getMoviesInfo())[1],0.01);

		//insert 1 with [0,0]
		mt.insertRating("911", new int[] {0,0}, movieDB.getMoviesInfo());
		assertEquals(59.37, MovieTrivia.getMean(movieDB.getMoviesInfo())[0],0.01);
		assertEquals(57.5, MovieTrivia.getMean(movieDB.getMoviesInfo())[1],0.01);

		//insert 1 with [90,90]
		mt.insertRating("cit590", new int[] {90,90}, movieDB.getMoviesInfo());
		assertEquals(62.77,MovieTrivia.getMean(movieDB.getMoviesInfo())[0],0.01);
		assertEquals(61.11,MovieTrivia.getMean(movieDB.getMoviesInfo())[1],0.01);

		//insert 1 with [90,100]
		mt.insertRating("chocolate", new int[] {90,100}, movieDB.getMoviesInfo());
		assertEquals(65.5,MovieTrivia.getMean(movieDB.getMoviesInfo())[0],0.01);
		assertEquals(65,MovieTrivia.getMean(movieDB.getMoviesInfo())[1],0.01);

	}
}
