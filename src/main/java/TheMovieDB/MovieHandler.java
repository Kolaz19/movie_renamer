package TheMovieDB;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;
import java.io.FileNotFoundException;

public class MovieHandler {
    //Example
    TmdbMovies gr_movies = new TmdbApi(Main.readFromConfig("[API Key]")).getMovies();
    MovieDb movie = gr_movies.getMovie(5353,"en");


    public MovieHandler() throws FileNotFoundException {
    }
}
