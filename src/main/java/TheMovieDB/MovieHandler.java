package TheMovieDB;

import info.movito.themoviedbapi.*;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import javafx.scene.image.Image;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class MovieHandler {

    private static TmdbSearch gr_searchEntity;
    private final List<MovieDb> ma_movieList;
    private int mv_currentMovieCounter;
    private final String mv_correspondingFolder;

    static {
        try {
            gr_searchEntity = new TmdbApi(Main.readFromConfig("[API Key]")).getSearch();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public MovieHandler(String iv_folderName) throws FileNotFoundException {
        mv_correspondingFolder = iv_folderName;
        int lv_nameLength = iv_folderName.length();

        if ((lv_nameLength > 7) && mv_correspondingFolder.substring(lv_nameLength-6,lv_nameLength).matches("\\(\\d{4}\\)") )  {
            iv_folderName = iv_folderName.substring(0,lv_nameLength-7);
        }


        MovieResultsPage ma_movieResults = gr_searchEntity.searchMovie(iv_folderName,null,"de",true,1);
        ma_movieList = ma_movieResults.getResults();
        Iterator<MovieDb> la_movieIterator = ma_movieList.iterator();
        //Delete movies without info / poster
        while(la_movieIterator.hasNext()) {
            MovieDb lr_currentMovie = la_movieIterator.next();
            if ((lr_currentMovie.getPosterPath() == null) || (lr_currentMovie.getReleaseDate() == null)) {
                la_movieIterator.remove();
            }
        }

        mv_currentMovieCounter = 0;
    }

    public boolean hasEntries() {
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

    public void previousMovie() {
        if (mv_currentMovieCounter == 0) {
            mv_currentMovieCounter = ma_movieList.size() -1;
        } else {
            mv_currentMovieCounter--;
        }
    }

    public String getMovieName() {
        MovieDb lr_movie =  ma_movieList.get(mv_currentMovieCounter);
        String lv_fullNameYear = lr_movie.getTitle();
        if (lr_movie.getReleaseDate().length() >= 4) {
            lv_fullNameYear+= " (" + lr_movie.getReleaseDate().substring(0,4) + ")";
        }
        return lv_fullNameYear;
    }

    public Image getMoviePoster() {
        String lv_pathToPoster = "https://image.tmdb.org/t/p/original" + ma_movieList.get(mv_currentMovieCounter).getPosterPath();
        return new Image(lv_pathToPoster, 210, 280, false, true);
    }

    public boolean isCorrectlyNamed () {
        int lv_nameLength = mv_correspondingFolder.length();
        String lv_potMovieName;
        String lv_originalName;
        String lv_potYear;
        //Check if year is given
        if (!(lv_nameLength > 7) || !mv_correspondingFolder.substring(lv_nameLength-6,lv_nameLength).matches("\\(\\d{4}\\)") )  {
            return false;
        }
        lv_potYear = mv_correspondingFolder.substring(lv_nameLength-5,lv_nameLength-1);
        //Check if movies exist with this name
        if (!this.hasEntries()) {
            return false;
        }
        // check all movies in list if name is given AND year is correct
        lv_potMovieName = mv_correspondingFolder.substring(0,lv_nameLength-7);
        String[] la_chars = {"<",">",":","\"","/","\\","|","?","*"};

        for (MovieDb lr_movie: ma_movieList) {
            lv_originalName = lr_movie.getTitle();
            for (String lv_char : la_chars) {
                lv_originalName = lv_originalName.replace(" " + lv_char + " "," ");
                lv_originalName = lv_originalName.replace(lv_char+ " "," ");
                lv_originalName = lv_originalName.replace(lv_char," ");
            }
            if (lv_potMovieName.equals(lv_originalName)) {
                if (lr_movie.getReleaseDate().substring(0,4).equals(lv_potYear)) {
                    return true;
                }
            }
        }
        return false;
    }



}
