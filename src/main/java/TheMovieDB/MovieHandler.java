package TheMovieDB;

import info.movito.themoviedbapi.*;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import java.io.FileNotFoundException;
import java.util.List;

public class MovieHandler {

    private static TmdbSearch gr_searchEntity;
    private final List<MovieDb> ma_movieList;
    private int mv_currentMovieCounter;

    static {
        try {
            gr_searchEntity = new TmdbApi(Main.readFromConfig("[API Key]")).getSearch();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public MovieHandler(String iv_folderName) throws FileNotFoundException {
        MovieResultsPage ma_movieResults = gr_searchEntity.searchMovie(iv_folderName,null,"de",true,1);
        ma_movieList = ma_movieResults.getResults();
        mv_currentMovieCounter = 0;
    }

    private boolean hasEntries() {
        boolean lv_hasEntries;
        return !ma_movieList.isEmpty();
    }

    public void nextMovie() {
        if (mv_currentMovieCounter+1 < ma_movieList.size()) {
            mv_currentMovieCounter++;
        } else {
            mv_currentMovieCounter = 0;
        }
    }

    public String getMovieName() {
        MovieDb lr_movie =  ma_movieList.get(mv_currentMovieCounter);
        String lv_fullNameYear = lr_movie.getTitle() + " " + lr_movie.getReleaseDate();
        return lv_fullNameYear;
    }

}
