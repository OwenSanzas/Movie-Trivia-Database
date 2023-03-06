# Movie-Trivia-Database
Made by Ze Sheng


## All Source Files:
### Database Setup File 
* MovieDB.java

### Database Query Behavior File
* MovieTrivia.java

### Movie-related File
* Actor.java
* Movie.java

### Unit Test Files
* MovieTriviaTest.java
* MovieDBTest.java

## Progress
* ✓ - Finished
* O - In progess
* ? - Have problem
* blank - Not started

Section | State
:----: |:----:
Database Setup |   ✓
Construct Movie| ✓
Construct Actor | ✓
All Functions Test | ✓
Corner cases check | ✓

## Overview of work accomplished
* This is a special database built in JAVA to practice unit test and coding skills.
* It has following functions:
* ````
    public void setUp() // Database setup
    public void printAllActors()
    public void printAllMovies()
    public String changeLetters(String name)
    public void insertActor(String actor, String[] movies, ArrayList<Actor> actorsInfo)
    public void insertRating (String movie, int [] ratings, ArrayList <Movie> moviesInfo)
    public ArrayList <String> selectWhereActorIs (String actor, ArrayList <Actor> actorsInfo) // Given an actor, returns the list of all movies
    public ArrayList <String> selectWhereMovieIs (String movie, ArrayList <Actor> actorsInfo) // Given a movie, returns the list of all actors in that movie
    public ArrayList <String> selectWhereRatingIs (char comparison, int targetRating, boolean isCritic, ArrayList <Movie> moviesInfo)
    public ArrayList <String> getCoActors (String actor, ArrayList <Actor> actorsInfo)
    public ArrayList <String> getCommonMovie (String actor1, String actor2, ArrayList <Actor> actorsInfo)
    public ArrayList <String> goodMovies (ArrayList <Movie> moviesInfo) // return movies whose rating > 85
    public ArrayList <String> getCommonActors (String movie1, String movie2, ArrayList <Actor> actorsInfo)
    public static double [] getMean (ArrayList <Movie> moviesInfo)
    ````
* It passes all unit tests on all functions!
